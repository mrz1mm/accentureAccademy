/*
Esercizio: Creazione e Test di Trigger nel Database Sakila
Obiettivo:
Creare diversi trigger nel database Sakila per gestire e monitorare le operazioni sulle tabelle film e rental.


Trigger da creare:

1. 	Creare un trigger che impedisca l'inserimento di un nuovo film se il titolo è già presente nel database. Il
	trigger deve controllare se esiste un film con lo stesso titolo e, in caso affermativo, annullare
	l'operazione.
2. 	Creare un trigger che registri in una tabella di log ogni volta che un film viene modificato. Il
	campo description del log deve includere l'ID del film, i dettagli prima e dopo la modifica e la data e
	l'ora dell'aggiornamento (scegliere i dettagli a piacimento, ad esempio descrizione, rating e titolo del flm).
3.	Creare un trigger che impedisca la cancellazione di un record dalla tabella rental se il noleggio non è
	stato ancora restituito (la colonna return_date è NULL).
4.	Implementare un trigger che, dopo l'inserimento di un nuovo noleggio, aggiorni un contatore nella
	tabella film per tenere traccia del numero totale di volte in cui ciascun film è stato noleggiato.
	(Va aggiunto un campo counter unsigned int, inizializzato a 10 oppure con query che conta i noleggi
	effettivi).
*/

/*
1.	Creare un trigger che impedisca l'inserimento di un nuovo film se il titolo è già presente nel database. Il
	trigger deve controllare se esiste un film con lo stesso titolo e, in caso affermativo, annullare
	l'operazione.
*/
  delimiter $
  CREATE TRIGGER checkTitleBeforeInsert
  BEFORE INSERT ON film
  FOR EACH ROW 
  BEGIN    
     IF OLD.title != NEW.title THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = "ERROR: film già presente nella collezione.";
    END IF;
  END;
  $
  delimiter ;

/*
2. 	Creare un trigger che registri in una tabella di log ogni volta che un film viene modificato. Il
	campo description del log deve includere l'ID del film, i dettagli prima e dopo la modifica e la data e
	l'ora dell'aggiornamento (scegliere i dettagli a piacimento, ad esempio descrizione, rating e titolo del flm).
*/
  delimiter $
  CREATE TRIGGER insertLogAfterUpdateFilm
  AFTER UPDATE ON film
  FOR EACH ROW 
  BEGIN    
     IF OLD.film = NEW.film THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = "ERROR: log non registrato.";
    END IF;
    
    INSERT INTO log (type, description)
    VALUES (
        'film_update_ok', -- Tipo di log come da tua richiesta
        CONCAT(
            'Aggiornamento film. film_id: ', OLD.film_id,
            ', title(OLD): ', IFNULL(OLD.title, 'NULL'), ', title(NEW): ', IFNULL(NEW.title, 'NULL'),
            ', description(OLD): ', LEFT(IFNULL(OLD.description, 'NULL'), 50), ', description(NEW): ', LEFT(IFNULL(NEW.description, 'NULL'), 50),
            ', release_year(OLD): ', IFNULL(OLD.release_year, 'NULL'), ', release_year(NEW): ', IFNULL(NEW.release_year, 'NULL'),
            ', language_id(OLD): ', IFNULL(OLD.language_id, 'NULL'), ', language_id(NEW): ', IFNULL(NEW.language_id, 'NULL'),
            ', original_language_id(OLD): ', IFNULL(OLD.original_language_id, 'NULL'), ', original_language_id(NEW): ', IFNULL(NEW.original_language_id, 'NULL'),
            ', rental_duration(OLD): ', IFNULL(OLD.rental_duration, 'NULL'), ', rental_duration(NEW): ', IFNULL(NEW.rental_duration, 'NULL'),
            ', rental_rate(OLD): ', FORMAT(IFNULL(OLD.rental_rate, 0), 2), ', rental_rate(NEW): ', FORMAT(IFNULL(NEW.rental_rate, 0), 2),
            ', length(OLD): ', IFNULL(OLD.length, 'NULL'), ', length(NEW): ', IFNULL(NEW.length, 'NULL'),
            ', replacement_cost(OLD): ', FORMAT(IFNULL(OLD.replacement_cost, 0), 2), ', replacement_cost(NEW): ', FORMAT(IFNULL(NEW.replacement_cost, 0), 2),
            ', rating(OLD): ', IFNULL(OLD.rating, 'NULL'), ', rating(NEW): ', IFNULL(NEW.rating, 'NULL'),
            ', special_features(OLD): ', IFNULL(OLD.special_features, 'NULL'), ', special_features(NEW): ', IFNULL(NEW.special_features, 'NULL'),
            ', last_update(OLD): ', DATE_FORMAT(OLD.last_update, '%Y-%m-%d %H:%i:%s'), ', last_update(NEW): ', DATE_FORMAT(NEW.last_update, '%Y-%m-%d %H:%i:%s')
        )
    );
  END;
  $
  delimiter ;

/*
3.	Creare un trigger che impedisca la cancellazione di un record dalla tabella rental se il noleggio non è
	stato ancora restituito (la colonna return_date è NULL).
*/

/*
4.	Implementare un trigger che, dopo l'inserimento di un nuovo noleggio, aggiorni un contatore nella
	tabella film per tenere traccia del numero totale di volte in cui ciascun film è stato noleggiato.
	(Va aggiunto un campo counter unsigned int, inizializzato a 10 oppure con query che conta i noleggi
	effettivi).
*/