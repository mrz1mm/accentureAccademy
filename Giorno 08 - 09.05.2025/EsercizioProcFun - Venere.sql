/*
# Esercizio: Creazione di una Stored Procedure con Funzioni

## Obiettivo
L’obiettivo è sviluppare una stored procedure che utilizza funzioni personalizzate per gestire una serie di operazioni complesse nel database `sakila`, con particolare attenzione alla gestione delle eccezioni, all'uso delle transazioni e alla registrazione delle operazioni nella tabella `log`.

## Scenario
La società di noleggio film vuole automatizzare il processo di registrazione di un noleggio, includendo verifiche avanzate e gestione dei dati. La procedura deve:
1. Utilizzare diverse funzioni personalizzate.
2. Registrare eventuali errori e operazioni riuscite nella tabella `log`.
3. Gestire tutto in una transazione per garantire l'integrità dei dati.

## Requisiti
### Modifica delle Tabelle
1. `customer :` Aggiungi una colonna `store_credit`(decimale) per memorizzare il credito disponibile per ogni cliente. Valorizza la tabella con un valore iniziale di due unità di  per ogni noleggio effettuato e resituito dal cliente OPPURE con un valore fisso per tutti
2. `rental :`  Aggiungi una colonna  `late_fee` per memorizzare  la penale associata a un noleggio nella tabella `rental` 

### **Funzioni da Creare**
1. **Funzione 1: Calcolo della Penale per Ritardo**    
    Dato un `rental_id`, calcola la penale per ritardo se il film è stato restituito dopo la data di scadenza. Restituisce 0 se non c’è ritardo, altrimenti applica una penale di 2 unità per ogni giorno di ritardo  
2. **Funzione 2: Calcolo del Costo Totale del Noleggio per un Cliente**    
    Dato un `customer_id`, calcola il costo totale di tutti i noleggi effettuati dal cliente.
3. **Funzione 3: Numero di Film in Noleggio Attivo per un Cliente**    
    Dato un `customer_id`, restituisce il numero di film attualmente in noleggio per il cliente.    
4. **Funzione 4: Controllo Credito Cliente**    
    Dato un `customer_id` e un importo, verifica se il cliente ha credito sufficiente per il noleggio. Restituisce TRUE o FALSE (oppure 1 /0 ).
    
### **Stored Procedure da creare**
La Stored procedure effettua noleggio. pagamento e aggiornamento penale contestuale in una transazione 
La stored procedure deve:
1. Accettare come parametri: `customer_id`, `film_id` e `rental_date`.
2. Utilizzare le funzioni definite per :
    1. Calcolare eventuali penali per ritardi precedenti   (per calcolare   `late_fee`  che poi verrà inserita nel rental)
    2. Verificare che il cliente può effettuare il noleggio:
        - deve avere credito sufficiente (comprese le penali)
        - non deve avere noleggi attivi
3. Operare all'interno di una transazione:
    - Aggiornare i dati nelle tabelle `rental` e `payment`.
    - Registrare un messaggio di log nella tabella `log`.
4. L’importo dell’operazione di noleggio deve comprendere il saldo di tutte le penali del cliente, se il cliente non può pagare la penale, non può effettuare il noleggio.
5. Se noleggiando un film il cliente paga alcune penali per dei noleggi passati, queste penali non dovranno più essere pagate per i noleggi futuri.
6.  Gestire eventuali errori utilizzando `SIGNAL` per generare eccezioni personalizzate
*/

-- MODIFICA DELLE TABELLE --
ALTER TABLE customer
ADD COLUMN store_credit DECIMAL (10, 2) NOT NULL DEFAULT 0.00;

ALTER TABLE rental
ADD COLUMN late_fee DECIMAL (10, 2) NOT NULL DEFAULT NULL;



-- FUNCTION --
-- 1. Calcolo della Penale per Ritardo
DROP FUNCTION IF EXISTS getLateFee;
DELIMITER $$
CREATE FUNCTION getLateFee(p_rental_id INT) RETURNS DECIMAL(10,2)
READS SQL DATA
BEGIN
    declare v_giorni_trattenuto int;
    declare v_penale decimal(5,2);
    declare v_durata_noleggio int;

    select f.rental_duration
    , datediff(return_date, rental_date) into v_durata_noleggio , v_giorni_trattenuto
    from rental r
    inner join inventory i on r.inventory_id = i.inventory_id
    inner join film f on i.film_id = f.film_id
    where r.rental_id = p_rental_id;

    if v_giorni_trattenuto is not null and v_giorni_trattenuto > v_durata_noleggio then
        set v_penale = (v_giorni_trattenuto - v_durata_noleggio ) * 2;
    else 
        set v_penale = 0;
    end if;
    return v_penale;
END $$
DELIMITER ;


-- 2. Calcolo del Costo Totale del Noleggio per un Cliente
DROP FUNCTION IF EXISTS getCustomerTotalPayments;
DELIMITER $$
CREATE FUNCTION getCustomerTotalPayments(p_customer_id INT) RETURNS DECIMAL(10,2)
READS SQL DATA
BEGIN
    declare totale decimal(10,2);

    select sum(p.amount) into totale
    from payment p
    where p.customer_id = p_customer_id;

    return ifnull(totale, 0);
END $$
DELIMITER ;


-- 3. Numero di Film in Noleggio Attivo per un Cliente
DROP FUNCTION IF EXISTS getCustomerActiveRentalsCount;
DELIMITER $$
CREATE FUNCTION getCustomerActiveRentalsCount(p_customer_id INT)
RETURNS INT
DETERMINISTIC
READS SQL DATA
BEGIN
    DECLARE v_count INT;

    SELECT COUNT(*)
    INTO v_count
    FROM rental r
    WHERE r.customer_id = p_customer_id
      AND r.return_date IS NULL;

    RETURN v_count;
END$$
DELIMITER ;


-- 4. Controllo Credito Cliente
DROP FUNCTION IF EXISTS checkCustomerCredit;
DELIMITER $$
CREATE FUNCTION checkCustomerCredit(p_customer_id INT, p_amount DECIMAL(10,2))
RETURNS BOOLEAN
DETERMINISTIC
READS SQL DATA
BEGIN
    DECLARE v_credit DECIMAL(10,2);

    SELECT store_credit
    INTO v_credit
    FROM customer
    WHERE customer_id = p_customer_id;

    RETURN v_credit >= p_amount;
END$$
DELIMITER ;



-- STORE PROCEDURE --
DROP PROCEDURE IF EXISTS rentalProcess;
DELIMITER $$
CREATE PROCEDURE rentalProcess (
    IN p_customer_id INT,
    IN p_film_id INT,
    IN p_rental_date DATETIME
)
BEGIN
	DECLARE v_customer_found BOOLEAN DEFAULT false;
	DECLARE v_film_found BOOLEAN DEFAULT false;
    DECLARE v_actual_rental_date DATETIME;
    DECLARE v_film_rental_rate DECIMAL(5, 2);
	DECLARE v_current_film_cost DECIMAL(5, 2);
    DECLARE v_total_unpaid_late_fees DECIMAL(5, 2) DEFAULT 0.00;
    DECLARE v_total_amount_to_charge DECIMAL(5, 2) DEFAULT 0.00;
    DECLARE v_new_rental_id INT;
    DECLARE v_new_inventory_id MEDIUMINT UNSIGNED;
    DECLARE v_new_payment_id SMALLINT UNSIGNED;
    DECLARE v_staff_id TINYINT UNSIGNED;
    DECLARE v_inventory_id MEDIUMINT UNSIGNED;
    DECLARE v_customer_store_id TINYINT UNSIGNED;

    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        DECLARE err_msg_handler TEXT;
        DECLARE err_code_handler INT;
        DECLARE sql_state_handler VARCHAR(5);

        GET DIAGNOSTICS CONDITION 1
            err_msg_handler = MESSAGE_TEXT,
            err_code_handler = MYSQL_ERRNO,
            sql_state_handler = RETURNED_SQLSTATE;

        ROLLBACK;

        INSERT INTO log (type, description)
        VALUES ('PROC_SQL_ERR',
                CONCAT(
                        'Errore SQL in rentalProcess. ',
                        'Msg: ', IFNULL(err_msg_handler, 'N/A'),
                        ', Cod: ', IFNULL(err_code_handler, 'N/A'),
                        ', SQLSTATE: ', IFNULL(sql_state_handler, 'N/A'),
                        '. customer_id=', IFNULL(p_customer_id, 'NULL'),
                        ', film_id=', IFNULL(p_film_id, 'NULL'),
                        ', rental_date=', IFNULL(p_rental_date, 'NULL')
                      )
               );
    END;
    
    
    START TRANSACTION;  
    -- Verifica se p_customer_id esiste in customer.
    SELECT EXISTS (
		SELECT 1
        FROM customer
        WHERE customer_id = p_customer_id
        )
        INTO v_customer_found;
        -- se non esiste lancio l'errore
        IF NOT v_customer_found THEN
			SIGNAL SQLSTATE '45000'
				SET MESSAGE_TEXT = 'ERROR: Cliente non trovato.';
		END IF;
        
    -- Verifica se p_film_id esiste in film.
    SELECT EXISTS (
		SELECT 1
        FROM film
        WHERE film_id = p_film_id
        )
        INTO v_film_found;
    IF NOT v_film_found THEN
		SIGNAL SQLSTATE '45001'
			SET MESSAGE_TEXT = 'ERROR: Film non trovato.';
	END IF;
    
    -- Set di p_rental_date
    IF p_rental_date IS NULL THEN
		SET v_actual_rental_date = NOW();
	ELSE
		SET v_actual_rental_date = p_rental_date;
	END IF;

    
-- 1. Calcolare eventuali penali per ritardi precedenti (per calcolare `late_fee` che poi verrà inserita nel rental)  
	-- calcolare il costo totale del film
	SELECT rental_rate
    INTO v_film_rental_rate
    FROM film
    WHERE film_id = p_film_id;
    
	-- verifica su rental_rate
    IF v_film_rental_rate IS NULL THEN
		SIGNAL SQLSTATE '45002' SET MESSAGE_TEXT = 'ERROR: Impossibile recuperare il costo del film.';
    END IF;
    
    SET v_current_film_cost = v_film_rental_rate;
    
    -- calcolare la sommatoria delle penali che il customer deve pagare #########################################################################################################################################
    SELECT IFNULL(SUM(getLateFee(r.rental_id)), 0.00)
    INTO v_total_unpaid_late_fees
    FROM rental r
    WHERE r.customer_id = p_customer_id
		AND r.return_date IS NOT NULL
        AND r.late_fee IS NULL;
        
        #########################################################################################################################################
      
	UPDATE rental r
	SET r.late_fee = getLateFee(r.rental_id)
    WHERE r.customer_id = p_customer_id
		AND r.return_date IS NOT NULL
        AND r.late_fee IS NULL;
        
        #########################################################################################################################################
        
    -- calcolare l'importo totale che il customer deve pagare
    SET v_total_amount_to_charge = v_current_film_cost + v_total_unpaid_late_fees;
    
-- 2. Verificare che il cliente può effettuare il noleggio:       
    -- verificare che il customer abbia credito sufficiente (comprensivo delle penali)
    IF NOT checkCustomerCredit(p_customer_id, v_total_amount_to_charge) THEN
		SIGNAL SQLSTATE '45003'
			SET MESSAGE_TEXT = 'ERROR: Il cliente non ha credito sufficiente';
	END IF;
    
	-- verificare che il customer non abbia noleggi attivi
    IF getCustomerActiveRentalsCount(p_customer_id) > 0 THEN
		SIGNAL SQLSTATE '45004'
			SET MESSAGE_TEXT = 'ERROR: Il cliente ha già noleggi attivi. Deve restituire prima i film.';
	END IF;
    
-- recupero dello store_id
    SELECT store_id
    INTO v_customer_store_id
    FROM customer
    WHERE customer_id = p_customer_id;
    
-- recupero dello staff_id
	SELECT staff_id
    INTO v_staff_id
    FROM staff
    WHERE store_id = v_customer_store_id
		AND active = 1
	LIMIT 1;
    
    IF v_staff_id IS NULL THEN
		SELECT staff_id
        INTO v_staff_id
        FROM staff
        WHERE active = 1
        LIMIT 1;
	END IF;
    
    IF v_staff_id IS NULL THEN
		SIGNAL SQLSTATE '45005' SET MESSAGE_TEXT = 'ERROR: Nessuno staff attivo disponibile.';
	END IF;
    
	SELECT i.inventory_id 
	INTO v_inventory_id
    FROM inventory i
    LEFT JOIN rental r_check ON i.inventory_id = r_check.inventory_id
		AND r_check.return_date IS NULL
    WHERE i.film_id = p_film_id
		AND i.store_id = v_customer_store_id
        AND r_check.rental_id IS NULL LIMIT 1;
        
    IF v_inventory_id IS NULL THEN
        SELECT i.inventory_id INTO v_inventory_id
        FROM inventory i
        LEFT JOIN rental r_check ON i.inventory_id = r_check.inventory_id
			AND r_check.return_date IS NULL
        WHERE i.film_id = p_film_id
			AND r_check.rental_id IS NULL LIMIT 1;
    END IF;
    IF v_inventory_id IS NULL THEN
        SIGNAL SQLSTATE '45006' SET MESSAGE_TEXT = 'ERROR: Nessuna copia del film disponibile.';
    END IF;

    
-- aggiornare i dati nella tabella `rental`.    
    INSERT INTO rental (
		rental_date,
        inventory_id,
        customer_id,
        return_date,
        staff_id,
        last_update,
        late_fee
	) VALUES (
        v_actual_rental_date, 
        v_inventory_id,
        p_customer_id,
        NULL,
        v_staff_id,
        NOW(),
        NULL
	);
    
    SET v_new_rental_id = LAST_INSERT_ID();
    
	-- verifica sul v_new_rental_id apppena settato
    IF v_new_rental_id IS NULL OR v_new_rental_id = 0 THEN
		SIGNAL SQLSTATE '45007'
			SET MESSAGE_TEXT = 'ERROR: Il noleggio non è stato creato.';
	END IF;
    
	-- aggiornare i dati nella tabella `payment`. ####
    IF v_total_amount_to_charge > 0.00 THEN
		INSERT INTO payment (
			customer_id,
			staff_id,
			rental_id,
			amount,
			payment_date,
			last_update
		) VALUES (
			p_customer_id,
			v_staff_id,
			v_new_rental_id,
			v_total_amount_to_charge,
			v_actual_rental_date,
			NOW()
		);
    
		SET v_new_payment_id = LAST_INSERT_ID();
		
		-- verifica sul v_new_payment_id apppena settato
		IF v_new_payment_id IS NULL OR v_new_payment_id = 0 THEN
			SIGNAL SQLSTATE '45008'
				SET MESSAGE_TEXT = 'ERROR: Il pagamento non è stato creato.';
		END IF;
	END IF;
    
    -- aggiornamento del credito di customer
    UPDATE customer
    SET store_credit = store_credit - v_total_amount_to_charge
    WHERE customer_id = p_customer_id;
    
    COMMIT;
    
	-- registrare un messaggio di log nella tabella `log`.
	INSERT INTO log (type, description)
		VALUES (
			'NEW_RENTAL_OK', -- Un tipo breve per "Nuovo Noleggio OK"
			CONCAT(
				'Nuovo noleggio creato con successo. RentalID: ', IFNULL(v_new_rental_id, 'N/A'),
				', customer_id: ', IFNULL(p_customer_id, 'N/A'),
				', film_id: ', IFNULL(p_film_id, 'N/A'), ' (InvID: ', IFNULL(v_inventory_id, 'N/A'), ')',
				', staff_id: ', IFNULL(v_staff_id, 'N/A'),
				', Data: ', DATE_FORMAT(v_actual_rental_date, '%Y-%m-%d %H:%i:%s'),
				'. Costo Film: ', FORMAT(v_current_film_cost, 2),
				', Penali: ', FORMAT(v_total_unpaid_late_fees, 2),
				', Totale Addebitato: ', FORMAT(v_total_amount_to_charge, 2),
				'. Credito Residuo: ', FORMAT((SELECT store_credit FROM customer WHERE customer_id = p_customer_id), 2)
			)
		);
END $$
DELIMITER ;

CALL rentalProcess(1, 1, '2005-05-24 22:53:30');

SELECT *
FROM log