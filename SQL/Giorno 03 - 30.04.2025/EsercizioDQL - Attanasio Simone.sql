-- 1. Trovare tutti i film con un rental_rate superiore a 2.99 e una durata (length) inferiore a 60 minuti.
SELECT *
FROM film f
WHERE f.rental_rate > 2.99
	AND f.length < 60;


-- 2. Elencare i primi 10 attori per cognome in ordine alfabetico.
SELECT *
FROM actor a
ORDER BY a.last_name ASC
LIMIT 10;


-- 3. Calcolare il numero di noleggi (rental_count) per ogni classificazione dei film (rating).
SELECT f.rating, COUNT(r.rental_id) rc
FROM film f
INNER JOIN inventory i ON f.film_id = i.film_id
INNER JOIN rental r ON i.inventory_id = r.inventory_id
GROUP BY f.rating;


-- 4. Aggiungere alla query anche la durata media del tempo di noleggio dei film per quel rating
SELECT f.rating, COUNT(r.rental_id) rc, AVG(f.rental_duration)
FROM film f
INNER JOIN inventory i ON f.film_id = i.film_id
INNER JOIN rental r ON i.inventory_id = r.inventory_id
GROUP BY f.rating;


-- 5. Determinare il numero totale di film (total_films) per ogni categoria di film (category_id) che hanno un rental_rate di 0.99.
SELECT c.name, COUNT(f.film_id)
FROM film f
INNER JOIN film_category fc ON f.film_id = fc.film_id
INNER JOIN category c ON fc.category_id = c.category_id
WHERE f.rental_rate = 0.99
GROUP BY c.name;
