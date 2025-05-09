-- ESERCIZIO DQL n. 1
/*
Utilizzare JOIN e funzioni aggregate per determinare i film più noleggiati per ogni categoria.

Descrizione:
Scrivere una query per elencare la categoria di ogni film e il numero totale di noleggi, ordinati per categoria e
numero di noleggi in ordine decrescente.
*/
SELECT 
	c.name AS category_name,
	COUNT(r.rental_id) AS total_rentals
FROM
	category c
INNER JOIN
	film_category fc ON c.category_id = fc.category_id
INNER JOIN
	film f ON fc.film_id = f.film_id
INNER JOIN
	inventory i ON f.film_id = i.film_id
INNER JOIN
	rental r ON i.inventory_id = r.inventory_id
GROUP BY
	c.name
ORDER BY
	c.name ASC, total_rentals DESC;



-- ESERCIZIO DQL n. 2
/*
Identificare clienti che hanno noleggiato film di specifici generi.

Descrizione:
Scrivere una query che trovi i clienti che hanno noleggiato film nei generi 'Action' e 'Comedy'
*/
SELECT
	CONCAT(cu.first_name, ' ', cu.last_name) AS full_name
FROM
    category c
INNER JOIN
    film_category fc ON c.category_id = fc.category_id
INNER JOIN
    film f ON fc.film_id = f.film_id
INNER JOIN
    inventory i ON f.film_id = i.film_id
INNER JOIN
    rental r ON i.inventory_id = r.inventory_id
INNER JOIN
    customer cu ON r.customer_id = cu.customer_id
WHERE
    c.name IN ('Action', 'Comedy')
GROUP BY
    full_name
HAVING
    COUNT(DISTINCT c.name) = 2
ORDER BY
    full_name;



-- ESERCIZIO DQL n. 3
/*
Creare una vista per analizzare i noleggi e usarla in una query con JOIN.

Descrizione:
Creare una vista che memorizzi l'ID del cliente e il numero totale di noleggi. Utilizzare questa vista in una query
con JOIN per trovare i nomi dei clienti con più di 30 noleggi.
*/
CREATE VIEW
	rental_analysis AS
SELECT
    r.customer_id,
    COUNT(r.rental_id) AS total_rentals
FROM
    rental r
GROUP BY
    r.customer_id;
    


SELECT
	CONCAT(cu.first_name, ' ', cu.last_name) AS full_name
FROM
	customer cu
INNER JOIN
	rental_analysis ra ON cu.customer_id = ra.customer_id
WHERE
	ra.total_rentals > 30
ORDER BY
	ra.total_rentals;
    




-- ESERCIZIO DQL n. 4
/*
Creare una query complessa che incroci i dati di diverse tabelle per fornire insights approfonditi su un aspetto
specifico del database

Descrizione:
Utilizzare varie tabelle come film, actor, rental, e customer ed altre per determinare quali attori appaiono nei
film più noleggiati da un particolare insieme di clienti, basandosi su una Vista che identifica i clienti che hanno
noleggiato dai componenti dello staff di nome 'Mike'
*/
CREATE VIEW
	mike_rental AS
SELECT
    cu.customer_id,
	CONCAT(cu.first_name, ' ', cu.last_name) AS customer_full_name
FROM
	customer cu
INNER JOIN
    rental r ON cu.customer_id = r.customer_id
INNER JOIN
    staff s ON r.staff_id = s.staff_id
WHERE
	s.first_name = "Mike"
GROUP BY
    cu.customer_id, customer_full_name
ORDER BY
    customer_full_name;

    
    
SELECT
    a.actor_id,
    CONCAT(a.first_name, ' ', a.last_name) AS actor_full_name,
    COUNT(r.rental_id) AS rental_count
FROM
    actor a
INNER JOIN
    film_actor fa ON a.actor_id = fa.actor_id
INNER JOIN
    film f ON fa.film_id = f.film_id
INNER JOIN
    inventory i ON f.film_id = i.film_id
INNER JOIN
    rental r ON i.inventory_id = r.inventory_id
INNER JOIN
    mike_rental mr ON r.customer_id = mr.customer_id
GROUP BY
    a.actor_id, actor_full_name
ORDER BY
    rental_count DESC;