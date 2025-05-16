/*
Aggiornamento di un Noleggio con Modifica del Film e Logging

Obiettivo:
Creare una stored procedure che consenta l'aggiornamento di un noleggio, permettendo la modifica del film
associato nella tabella rental e registrando le modifiche nella tabella log.

Requisiti:
	• utilizzare la tabella di log fornita
	• la procedura ha come parametri di input:
		o rental_id (noleggio da modificare)
		o film_id (il film da noleggiare corretto)
	• l'inventory deve essere recuperato dal film che si trova nello stesso store dove lavora lo staff che ha
	effettuato il noleggio
	• la procedura deve utilizzare transazioni per garantire l'integrità dei dati: se una delle operazioni fallisce,
	nessun cambiamento deve essere applicato.
*/
DROP TABLE IF EXISTS log;

CREATE TABLE log (
  id int unsigned NOT NULL AUTO_INCREMENT,
  type varchar(45) NOT NULL,
  description text NOT NULL,
  PRIMARY KEY (`id`)
);

DROP PROCEDURE IF EXISTS UpdateRentalFilm;

DELIMITER $$
CREATE PROCEDURE UpdateRentalFilm (
    IN p_rental_id INT,         -- Noleggio da modificare
    IN p_new_film_id INT        -- Il film da noleggiare corretto
)
BEGIN
    DECLARE v_old_inventory_id INT;
    DECLARE v_old_film_id INT;
    DECLARE v_staff_id TINYINT UNSIGNED;
    DECLARE v_store_id TINYINT UNSIGNED;
    DECLARE v_new_inventory_id INT;
    DECLARE v_log_description TEXT;

    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        DECLARE err_msg_handler TEXT;
        DECLARE err_code_handler INT;
        DECLARE sql_state_handler VARCHAR(5);
        DECLARE table_name_handler VARCHAR(1000);

        GET DIAGNOSTICS CONDITION 1
            err_msg_handler = MESSAGE_TEXT,
            err_code_handler = MYSQL_ERRNO,
            sql_state_handler = RETURNED_SQLSTATE,
            table_name_handler = TABLE_NAME;
            
        ROLLBACK; 

        INSERT INTO log (type, description)
        VALUES ('PROCEDURE_SQLEXCEPTION',
					CONCAT(
							'Errore SQL in UpdateRentalFilm',
							'Messaggio: ', err_msg_handler,
							', Codice: ', err_code_handler,
							', SQLSTATE: ', sql_state_handler,
							'. Input: rental_id=', IFNULL(p_rental_id, 'NULL'),
							', new_film_id=', IFNULL(p_new_film_id, 'NULL'),
							', Tabella: ', IFNULL(table_name_handler, 'N/A')
						  )
               );
        RESIGNAL;
    END;

    -- Controllo se il nuovo film_id esiste prima di iniziare
    IF NOT EXISTS (SELECT 1 FROM film WHERE film_id = p_new_film_id) THEN
		SIGNAL SQLSTATE '45000'
			-- SET MESSAGE_TEXT = CONCAT('Il film_id ', p_new_film_id, ' non esiste.');
            SET MESSAGE_TEXT = 'ERRORE: Il nuovo film_id specificato non esiste.';
	END IF;


    START TRANSACTION;

    -- 1. Recupero staff_id e inventory_id del noleggio originale, film_id dell'inventario originale, e store_id dello staff
    SELECT
        r.staff_id,
        r.inventory_id,
        i.film_id,
        s.store_id
    INTO
        v_staff_id,
        v_old_inventory_id,
        v_old_film_id,
        v_store_id
    FROM
        rental r
    JOIN
        inventory i ON r.inventory_id = i.inventory_id
    JOIN
        staff s ON r.staff_id = s.staff_id
    WHERE
        r.rental_id = p_rental_id;

    -- 2. Controllo se il noleggio originale e i dati associati sono stati trovati
    IF v_staff_id IS NULL THEN
        SIGNAL SQLSTATE '45000'
            -- SET MESSAGE_TEXT = CONCAT('Noleggio con rental_id ', p_rental_id, ' non trovato.');
            SET MESSAGE_TEXT = 'ERRORE: Noleggio specificato non trovato.';
    END IF;

    -- 3. Trovo un inventory_id per il nuovo film nello stesso store
    SELECT
        inventory_id
    INTO
        v_new_inventory_id
    FROM
        inventory
    WHERE
        film_id = p_new_film_id AND store_id = v_store_id
    LIMIT 1;

    -- 4. Controllo se è stato trovato un inventario per il nuovo film nello store corretto
    IF v_new_inventory_id IS NULL THEN
        SIGNAL SQLSTATE '45000'
            -- SET MESSAGE_TEXT = CONCAT('Nessun inventario disponibile per il film_id ', p_new_film_id, ' nello store_id ', v_store_id, '.');
            SET MESSAGE_TEXT = 'ERRORE: Nessun inventario disponibile per il nuovo film nello store corretto.';
    END IF;

    -- 5. Aggiorno la tabella rental con il nuovo inventory_id
    UPDATE rental
    SET
        inventory_id = v_new_inventory_id,
        last_update = NOW()
    WHERE
        rental_id = p_rental_id;

    -- 6. Registro la modifica avvenuta con successo nella tabella log
    SET v_log_description = CONCAT(
        'Aggiornamento film noleggio rental id: ', p_rental_id,
        ', Da film_id: ', v_old_film_id, ' (old_inventory_id: ', v_old_inventory_id, ')',
        ', A film_id: ', p_new_film_id, ' (new_inventory_id: ', v_new_inventory_id, ').',
        ', Eseguito da staff_id: ', v_staff_id,
        ', Nello store_id ', v_store_id, ').'
    );

    INSERT INTO log (type, description)
    VALUES ('RENTAL_FILM_MODIFIED', v_log_description);

    COMMIT;
END
$$
DELIMITER ;





-- TEST

-- 1 Trovare un noleggio, il suo staff, store e film attuale
SELECT
    r.rental_id, r.customer_id, r.staff_id, s.store_id AS staff_store_id,
    r.inventory_id AS old_inventory_id, i.film_id AS old_film_id, f.title AS old_film_title
FROM rental r
JOIN staff s ON r.staff_id = s.staff_id
JOIN inventory i ON r.inventory_id = i.inventory_id
JOIN film f ON i.film_id = f.film_id
WHERE r.return_date IS NULL
ORDER BY r.rental_id LIMIT 1;
-- rental_id 11496
-- customer_id 155
-- staff_id 1
-- staff_store_id 1
-- old_inventory_id 2047
-- old_film_id 445
-- old_film_title HYDE DOCTOR


-- 3. Trovare un new_film_id diverso dall'old_film_id che abbia inventory nello stesso staff_store_id
SET @target_store_id = 1;
SET @current_film_id_for_rental = 445;
SELECT
    f.film_id AS new_film_id,
    f.title AS new_film_title,
    i.inventory_id
FROM film f
JOIN inventory i ON f.film_id = i.film_id
WHERE i.store_id = @target_store_id
	AND f.film_id != @current_film_id_for_rental
LIMIT 1;
-- new_film_id 1
-- new_film_title ACADEMY DINOSAUR
-- inventory_id 1


-- 4. Chiamare la store procedure
SET @p_rental_id_test = 11496;
SET @p_new_film_id_test = 1;
CALL UpdateRentalFilm(@p_rental_id_test, @p_new_film_id_test);

-- 5. Verifica della tabella rental
SELECT
	rental_id,
    inventory_id,
    last_update
FROM rental
WHERE rental_id = @p_rental_id_test;
-- rental_id 11496
-- inventory_id 1
-- last_update 2025-05-08 12:53:27


-- 6. Verifica del nuovo film associato
SELECT i.film_id, f.title FROM rental r
JOIN inventory i ON r.inventory_id = i.inventory_id
JOIN film f ON i.film_id = f.film_id
WHERE r.rental_id = @p_rental_id_test;
-- film_id 1
-- title ACADEMY DINOSAUR


-- 7. Verifica dela tabella log
SELECT * FROM log ORDER BY id DESC LIMIT 1;
-- id 1
-- type RENTAL_FILM_MODIFIED
-- description '1', 'RENTAL_FILM_MODIFIED', 'Aggiornamento film noleggio rental id: 11496, Da film_id: 445 (old_inventory_id: 2047), A film_id: 1 (new_inventory_id: 1)., Eseguito da staff_id: 1, Nello store_id 1).'



-- VE: ok, ottimo il test con le query, ma:
--     1. perché il RESIGNAL? non era specificato che andasse in errore la SP
--     2. lo start transaction va prima del primo DML 
--     3. il commit va prima dell'insert into log
---------------------------------------------------------------------