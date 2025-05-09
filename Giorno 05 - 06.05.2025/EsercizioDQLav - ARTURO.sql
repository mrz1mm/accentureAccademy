-- ESERCIZIO DQL N. 8
-- Analizzare la spesa media dei clienti in relazione alla popolarità degli attori.
/*
Descrizione:
Determinare quali attori appaiono nei film che generano la maggiore spesa media per cliente.
La query deve calcolare la spesa media associata ai clienti per ciascun attore, considerando i guadagni
provenienti dai noleggi dei film in cui gli attori hanno recitato.
*/

-- ASSUNZIONI
-- Attori popolari = gli attori con il maggior numero di incassi
-- Ogni pagamento è legato ad un film e quindi agli attori che hanno partecipato a quel film
-- L'incasso di ogni film viene equamente ripartito tra gli attori
-- Il pagamento può coinvolgere più di un film


with
-- 1. Tutti i pagamenti di un dato film effettuati da un cliente
rental_film_payments as (
select
	p.customer_id,
	p.payment_id, 
    f.film_id, 
    f.title, 
    p.amount
from payment p
inner join rental r on p.rental_id = r.rental_id
inner join inventory i on r.inventory_id = i.inventory_id
inner join film f on i.film_id = f.film_id
),
-- 2. Associazione di ogni film ai suoi attori
actors_in_film as (
select 
	fa.film_id, a.actor_id, 
    CONCAT(a.first_name, ' ', a.last_name) as actor_full_name
from film_actor fa
inner join actor a on fa.actor_id = a.actor_id
),
-- 3. Distribuzione dei pagamenti per ogni attore
customer_actor_payment as (
select 
	rfp.customer_id, 
    aif.actor_id, aif.actor_full_name, 
    rfp.amount / count(*) over (partition by rfp.payment_id) as amount_per_actor_for_payment
from rental_film_payments rfp
inner join actors_in_film aif on rfp.film_id = aif.film_id
),
-- 4. Calcolo della spesa totale per attore e della spesa media per cliente per ogni attore
actor_avg_spent as (
select 
	cap.actor_id, 
    cap.actor_full_name, 
    round(sum(cap.amount_per_actor_for_payment), 2) as total_earned,
	round(sum(cap.amount_per_actor_for_payment) / count(distinct cap.customer_id), 2) as avg_spent_per_customer_for_actor
from customer_actor_payment cap
group by cap.actor_id
)
-- 5. Output finale ordinato per spesa media
select
	aas.actor_id,
    aas.actor_full_name,
    aas.total_earned,
    aas.avg_spent_per_customer_for_actor
from actor_avg_spent aas
order by avg_spent_per_customer_for_actor DESC;



-- ESERCIZIO DQL N. 9
-- Determinare la popolarità dei generi di film in base alla fascia oraria dei noleggi.
/*
Descrizione:
Analizzare la distribuzione dei noleggi in base alle fasce orarie della giornata (mattina, pomeriggio, sera, notte, in
base a un range arbitrario di fasce) e identificare il genere di film più popolare in ciascuna fascia oraria.
La query deve collegare le informazioni sulle categorie dei film con i dati relativi ai noleggi, segmentandoli per
orario.
*/

-- ASSUNZIONI
-- Generi di film popolari = Categorie di film con i maggior numeri di noleggi
-- Per l'ora va preso solo il valore assoluto dell'ora, troncato per difetto a 0 zero cifre decimali
-- Notte = [0 - 5]
-- Mattina = [6 - 11]
-- Pomeriggio = [12 - 17]
-- Sera = [18 - 23]

select *
from rental

with
-- 1. Assegnazione della categoria e dell'ora ad ogni noleggio
rental_category_time as (
select 
	r.rental_id,
    c.name as category_name,
    extract(hour from r.rental_date) as rental_hour
from rental r
inner join inventory i on r.inventory_id = i.inventory_id
inner join film f on i.film_id = f.film_id
inner join film_category fc on f.film_id = fc.film_id
inner join category c on fc.category_id = c.category_id
),
-- 2. Assegnazione della fascia oraria ad ogni noleggio
rentals_with_timeslot as (
select 
	rct.category_name,
    case
		when rct.rental_hour >= 0 and rct.rental_hour < 6 then 'Notte'
		when rct.rental_hour >= 6 and rct.rental_hour < 12 then 'Mattina'
        when rct.rental_hour >= 12 and rct.rental_hour < 18 then 'Pomeriggio'
        when rct.rental_hour >= 18 and rct.rental_hour < 24 then 'Sera'
        else 'undefined'
	end as time_slot
from rental_category_time rct
),
-- 3. Quantificazione dei noleggi per categoria e fascia oraria
category_counts_per_timeslot as (
select
	rwt.time_slot,
    rwt.category_name,
    count(*) as rental_count
from rentals_with_timeslot rwt
where rwt.time_slot != 'undefined'
group by rwt.time_slot, rwt.category_name
),
-- 4. Classificazione delle categorie all'interno di ogni fascia oraria
ranked_category_by_timeslot as (
select
	ccpt.time_slot,
    ccpt.category_name,
    ccpt.rental_count,
    rank() over (partition by ccpt.time_slot order by ccpt.rental_count desc) as popularity_rank
from category_counts_per_timeslot ccpt
)
-- 5. Selezione della categoria più popolare per ogni fascia oraria
select
	rcbt.time_slot,
    rcbt.category_name,
    rcbt.rental_count
from ranked_category_by_timeslot rcbt
where rcbt.popularity_rank = 1
order by
	case rcbt.time_slot
		when 'Notte' then 1
        when 'Mattina' then 2
        when 'Pomeriggio' then 3
        when 'Sera' then 4
		else 5
	end;




-- ESERCIZIO DQL N. 10
-- Individuare i dipendenti più influenti sulle vendite totali per store.
/*
Descrizione:
Determinare l'influenza di ogni dipendente sulle vendite totali di ciascuno store.
La query deve calcolare le vendite totali attribuite a ciascun dipendente e confrontarle con il totale complessivo
dello store per evidenziare il contributo percentuale di ogni dipendente.
*/

-- ASSUNZIONI
-- Dipendenti più influenti = dipendenti con il maggior numero di vendite effettuate



with
-- vendite totali effettuate da quel dipendente in quel negozio;
staff_sales as (
select
	s.staff_id,
    concat(s.first_name, ' ', s.last_name) as staff_full_name,
    s.store_id,
    sum(p.amount) as total_sales_by_staff
from payment p
inner join staff s on p.staff_id = s.staff_id
group by s.staff_id
),
-- vendite totali di quel negozio;
store_total_sales as (
select
	s.store_id,
    sum(p.amount) as total_sales_for_store
from payment p
inner join staff s on p.staff_id = s.staff_id
group by s.store_id
)
-- percentuale delle vendite del dipendente sulle vendite totali del negozio;
select
	ss.store_id,
    ss.staff_full_name,
    ss.total_sales_by_staff,
    sts.total_sales_for_store,
    round((ss.total_sales_by_staff / sts.total_sales_for_store) * 100, 2) as percentage_staff_on_store
from staff_sales ss
inner join store_total_sales sts on ss.store_id = sts.store_id
group by ss.store_id, ss.staff_full_name, ss.total_sales_by_staff
order by ss.staff_full_name