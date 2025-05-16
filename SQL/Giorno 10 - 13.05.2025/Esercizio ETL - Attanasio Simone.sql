/*
Esercizio: Analisi Complessa di Dati di Noleggio
Obiettivo:
Utilizzare una combinazione di comandi ETL per analizzare i dati di noleggio nel database sakila.
*/

/*
1.	Analisi dei Noleggi per Categoria di Film: (Extract)
	Creare una query che calcoli il numero totale di noleggi per ciascuna categoria di film.
	Salvare il risultato in una tabella temporanea.
*/
DROP TEMPORARY TABLE IF EXISTS temp_rental_count;
CREATE TEMPORARY TABLE temp_rental_count AS
SELECT
    c.category_id,
    c.name AS category_name,
    COUNT(r.rental_id) AS total_rentals
FROM
    category c
JOIN
    film_category fc ON c.category_id = fc.category_id
JOIN
    film f ON fc.film_id = f.film_id
JOIN
    inventory i ON f.film_id = i.film_id
JOIN
    rental r ON i.inventory_id = r.inventory_id
GROUP BY
    c.category_id;
    
SELECT *
FROM temp_rental_count;


/*
2. 	Creazione di una Tabella di Report:
	Creare una nuova tabella che conterrà un report finale (Target). Questa tabella deve includere colonne
	per la categoria di film, il numero totale di noleggi, e la durata media del noleggio per cliente.
*/
DROP TABLE IF EXISTS report_category_rentals;
CREATE TABLE report_category_rentals (
	category_id TINYINT UNSIGNED PRIMARY KEY,
    category_name VARCHAR(25) NOT NULL,
    total_rentals INT,
    avg_rental_duration_per_customer DECIMAL (10, 2),
    CONSTRAINT fk_report_category FOREIGN KEY (category_id) REFERENCES category(category_id) -- ho collegato le due tabelle in quanto mi sembrava logico che avessero una corrispondenza
);


/*
3. 	Popolamento della Tabella di Report: ( Transform e Load )
	Calcolare il noleggio medio per cliente per ciascuna categoria di film e aggiornare la tabella di report con
	questi valori.
*/
-- TRANSFORM
DROP TABLE IF EXISTS customer_avg_duration_in_category;

CREATE TEMPORARY TABLE customer_avg_duration_in_category AS
SELECT
c.category_id, r.customer_id, AVG(DATEDIFF(r.return_date, r.rental_date)) AS avg_duration_for_customer_in_category
FROM rental r
INNER JOIN inventory i ON r.inventory_id = i.inventory_id
INNER JOIN film_category fc ON i.film_id = fc.film_id
INNER JOIN category c ON fc.category_id = c.category_id
WHERE r.return_date IS NOT NULL
GROUP BY c.category_id, r.customer_id;

SELECT * 
FROM customer_avg_duration_in_category;


DROP TABLE IF EXISTS temp_avg_duration_per_customer_category;

CREATE TEMPORARY TABLE temp_avg_duration_per_customer_category AS
SELECT 
	cadic.category_id,
    AVG(cadic.avg_duration_for_customer_in_category) AS overall_avg_rental_duration_per_customer
FROM customer_avg_duration_in_category cadic
GROUP BY category_id;

SELECT * 
FROM temp_avg_duration_per_customer_category;


-- LOAD
START TRANSACTION;

	INSERT INTO report_category_rentals (category_id, category_name, total_rentals)
	SELECT
		trc.category_id,
		trc.category_name,
		trc.total_rentals
	FROM temp_rental_count trc;

	UPDATE report_category_rentals rcr
	INNER JOIN temp_avg_duration_per_customer_category tadpcc ON rcr.category_id = tadpcc.category_id
	SET rcr.avg_rental_duration_per_customer = tadpcc.overall_avg_rental_duration_per_customer;

COMMIT;


SELECT *
FROM report_category_rentals