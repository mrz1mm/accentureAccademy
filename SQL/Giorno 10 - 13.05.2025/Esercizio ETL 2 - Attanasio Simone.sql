/*
Esercizi ETL: Aggiornamento report
*/

/*
1.	Identificazione dei Top Film per Categoria:
	Identificazione film più noleggiati per ogni categoria e memorizzare l’outuput su una tabella di report
	Modificare la struttura della tabella di report esistente (o creare una tabella nuova)
*/

-- ASSUNZIONI
-- 1. top film = top 3 film

DROP TABLE IF EXISTS report_top_films_by_category;
CREATE TABLE report_top_films_by_category (
    category_id TINYINT UNSIGNED,
    film_id SMALLINT UNSIGNED,
    rank_in_category TINYINT,
    PRIMARY KEY (category_id, film_id),
    CONSTRAINT fk_rtfbc_cat FOREIGN KEY (category_id) REFERENCES category(category_id),
    CONSTRAINT fk_rtfbc_film FOREIGN KEY (film_id) REFERENCES film(film_id)
);

INSERT INTO report_top_films_by_category (
	category_id,
	film_id,
	rank_in_category
)
WITH film_rental_rank AS (
	SELECT
		fc.category_id,
        f.film_id,
        f.rental_count,
        RANK() OVER(PARTITION BY category_id ORDER BY rental_count DESC) AS calculated_rank_value
	FROM film f
	INNER JOIN inventory i ON f.film_id = i.film_id
	INNER JOIN rental r ON i.inventory_id = r.inventory_id
	INNER JOIN film_category fc  ON i.film_id = fc.film_id
	GROUP BY f.film_id, fc.category_id
)
SELECT
	category_id,
    film_id,
    calculated_rank_value
FROM film_rental_rank
WHERE calculated_rank_value <= 3
ORDER BY category_id, calculated_rank_value;

-- TEST
SELECT *
FROM report_top_films_by_category
ORDER BY category_id, rank_in_category;


/*
2.	Aggiornamento dei Dati di Vendita:
	Inserire nella tabella di report, gli aggiornamenti dei dati relativi alle vendite (con valori inventati) per tre
	categorie di film, di cui una nuova
	Se una categoria è già presente nella tabella, sostituire i dati esistenti con i nuovi dati.
*/
ALTER TABLE report_top_films_by_category
ADD COLUMN total_sales_amount DECIMAL (10, 2) DEFAULT NULL;


INSERT INTO category (
    name,
    last_update
)
VALUES (
    'Fantasy',
    NOW()
    );


START TRANSACTION; 
   
INSERT INTO report_top_films_by_category (
	category_id,
    film_id,
    rank_in_category,
    total_sales_amount
)
VALUES 
    (1, 1000, 4.5, 1250.00),
    (5, 800,  4.2, 980.00),
    (17, 75,   5.1, 450.00)
    ON DUPLICATE KEY
    UPDATE
        rank_in_category = VALUES(rank_in_category),
        total_sales_amount = VALUES(total_sales_amount);
        
COMMIT;    


-- TEST    
SELECT * 
FROM report_top_films_by_category;


/*
3.	Calcolo dei Ricavi Totali:
	Calcolare i ricavi totali per ciascuna categoria di film basati sul numero totale di noleggi e sul prezzo di
	noleggio medio per film.
	Aggiungere una colonna nuova alla tabella del report
*/
ALTER TABLE report_top_films_by_category
ADD COLUMN total_revenues_amount_by_category DECIMAL (10, 2) DEFAULT NULL;

DROP TEMPORARY TABLE IF EXISTS temp_avg_film_rental_rate_per_category;
CREATE TEMPORARY TABLE temp_avg_film_rental_rate_per_category AS
SELECT
    fc.category_id,
    AVG(f.rental_rate) AS avg_category_film_rental_rate
FROM
    film f
INNER JOIN
    film_category fc ON f.film_id = fc.film_id
GROUP BY
    fc.category_id;


START TRANSACTION;

UPDATE report_top_films_by_category rtfc
JOIN 
	report_category_rentals rcr ON rtfc.category_id = rcr.category_id
JOIN 
	temp_avg_film_rental_rate_per_category tariffa_cat ON rtfc.category_id = tariffa_cat.category_id
SET
    rtfc.total_revenues_amount_by_category = rcr.total_rentals * tariffa_cat.avg_category_film_rental_rate
WHERE
    rcr.total_rentals IS NOT NULL 
	AND rcr.total_rentals > 0;

COMMIT;


-- TEST
SELECT *
FROM report_top_films_by_category;