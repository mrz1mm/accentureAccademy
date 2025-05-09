-- ESERCIZIO DQL N. 6
-- Trovare le categorie con i film meno noleggiati.
/*
Descrizione:
Utilizzare una CTE per calcolare il numero totale di noleggi per ciascuna categoria e identificare le categorie con il
minor numero di noleggi. Mostrare anche il totale dei noleggi per ogni categoria.
*/

-- tabella temporanea con il numero totale di noleggi per ciascuna categoria
with total_rental_category AS (
select c.category_id, c.name, count(rental_id) as total_rentals
from category c
inner join film_category fc on c.category_id = fc.category_id
inner join film f on fc.film_id = f.film_id
inner join inventory i on f.film_id = i.film_id
inner join rental r on i.inventory_id = r.inventory_id
group by c.category_id
order by total_rentals
)
-- identificazione delle prime 5 categorie con il minor numero di noleggi
select *
from total_rental_category
limit 5;



-- ESERCIZIO DQL N. 7
-- Analizzare i noleggi di un particolare cliente e il suo impatto sul totale.
/*
Descrizione:
Utilizzare una CTE per calcolare il numero totale di noleggi e il totale speso da ciascun cliente. Calcolare inoltre la
percentuale del totale speso rispetto alla somma di tutti i clienti.
*/

-- ASSUNZIONI
-- 1. ogni cliente può noleggiare più di un film per pagamento

with
-- numero totale di noleggi per cliente
customer_total_rent as (
select cu.customer_id, concat(cu.first_name, ' ', cu.last_name) as customer_full_name, count(rental_id) as total_rent_spent
from customer cu
inner join rental r on cu.customer_id = r.customer_id
group by cu.customer_id
order by customer_full_name
),
-- totale spesa dei noleggi per cliente
customer_total_rent_spent as (
select cu.customer_id, concat(cu.first_name, ' ', cu.last_name) as customer_full_name, sum(p.amount) as total_rent_spent
from customer cu
inner join rental r on cu.customer_id = r.customer_id
inner join payment p on r.rental_id = p.rental_id
group by cu.customer_id
order by customer_full_name
),
-- il totale incassato dai noleggi
total_rent_spent as (
select sum(p.amount) as total_rent_spent_all_customers
from payment p
)
-- la percentuale del totale speso da ciascun cliente rispetto alla somma totale della spesa di tutti i clienti
-- customer_total_rent_spent / total_rent_spent * 100
-- unione
select ctrs.customer_id, ctrs.customer_full_name, ctr.total_rent_spent, ctrs.total_rent_spent,
round((ctrs.total_rent_spent / trs.total_rent_spent_all_customers) * 100, 2) as percentage_of_total_spent
from customer_total_rent_spent ctrs
inner join customer_total_rent ctr on ctrs.customer_id = ctr.customer_id
cross join total_rent_spent trs
order by percentage_of_total_spent desc