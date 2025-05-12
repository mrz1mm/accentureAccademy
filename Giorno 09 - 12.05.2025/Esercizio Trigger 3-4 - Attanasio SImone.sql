/*
Esercizio: Creazione e Test di Trigger nel Database Sakila
Obiettivo:
Creare diversi trigger nel database Sakila per gestire e monitorare le operazioni sulle tabelle film e rental.
*/

/*
3.	Creare un trigger che impedisca la cancellazione di un record dalla tabella rental se il noleggio non è
	stato ancora restituito (la colonna return_date è NULL).
*/
  DROP TRIGGER IF EXISTS checkInventoryIsBackFromRentalBeforeDelete;
  delimiter $$
  CREATE TRIGGER checkInventoryIsBackFromRentalBeforeDelete
  BEFORE DELETE ON rental
  FOR EACH ROW 
  BEGIN
    IF
		OLD.return_date IS NULL
	THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = "ERROR: non puoi eliminare un film che ancora non è stato restituito!.";
	END IF;
  END 
  $$ 
  delimiter ;

-- TEST
SELECT *
FROM rental
WHERE return_date IS NULL
LIMIT 1;

DELETE FROM rental
WHERE rental_id = 11496;


/*
4.	Implementare un trigger che, dopo l'inserimento di un nuovo noleggio, aggiorni un contatore nella
	tabella film per tenere traccia del numero totale di volte in cui ciascun film è stato noleggiato.
	(Va aggiunto un campo counter unsigned int, inizializzato a 10 oppure con query che conta i noleggi
	effettivi).
*/
  ALTER TABLE film
  ADD COLUMN rental_count INT NOT NULL;
  
  SELECT *
  FROM film;
  
  -- Error Code: 1175. You are using safe update mode and you tried to update a table without a WHERE that uses a KEY column.  To disable safe mode, toggle the option in Preferences -> SQL Editor and reconnect.
  -- Disabilito la Safe Update Mode
  SET SQL_SAFE_UPDATES = 0; 
  
  UPDATE film f
  SET f.rental_count = (
	  SELECT COUNT(r.rental_id)
	  FROM inventory i
	  JOIN rental r ON i.inventory_id = r.inventory_id
	  WHERE i.film_id = f.film_id
  );
  
  -- Riabilito la Safe Update Mode
  SET SQL_SAFE_UPDATES = 1; 
  
  SELECT *
  FROM film;
  

  DROP TRIGGER IF EXISTS updateFilmRentalCount;
  delimiter $$
  CREATE TRIGGER updateFilmRentalCount
  AFTER INSERT ON rental
  FOR EACH ROW 
  BEGIN
  DECLARE v_film_id_to_update INT;
  
  	SELECT film_id
    INTO v_film_id_to_update
    FROM inventory
    WHERE inventory_id = NEW.inventory_id;
  
  IF v_film_id_to_update IS NULL
	THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = "ERROR: impossibile determinare il film associato al noleggio.!.";
	ELSE
		UPDATE film
        SET rental_count = rental_count + 1
        WHERE film_id = v_film_id_to_update;
  END IF;
  END 
  $$ 
  delimiter ;
  
-- TEST
  SELECT film_id, rental_count
  FROM film
  WHERE film_id = 1;
  
  SELECT inventory_id
  FROM inventory
  WHERE film_id = 1
  LIMIT 1;
  
  INSERT INTO rental (
	rental_date, inventory_id, customer_id, return_date, staff_id
  )
  VALUES (
  NOW(),
  1,
  1,
  NULL,
  1
  );
  
  SELECT film_id, rental_count
  FROM film
  WHERE film_id = 1;