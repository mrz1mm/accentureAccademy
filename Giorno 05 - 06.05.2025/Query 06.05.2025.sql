##########################################
## Corso Java SQL - 28.04.2025
##
## Query del 06.05.2025 
##
##########################################

## query: determinare i film più noleggiati per ogni categoria:

-- 1. creo una vista in cui segno il totale di noleggi di ogni film per ogni categoria

drop view cat_film_tot;

create view cat_film_tot as
select
  c.category_id,
  c.name as categoria,
  f.film_id,
  f.title,
  count(r.rental_id) as totale_noleggi
from category c
inner join film_category fc on c.category_id = fc.category_id
inner join film f on fc.film_id = f.film_id
inner join inventory i on f.film_id = i.film_id
inner join rental r on i.inventory_id = r.inventory_id
group by c.category_id, f.film_id
order by category_id asc, totale_noleggi desc;

select * from cat_film_tot;

-- 2. creo una vista che determina per ogni categoria qual è max di totale noleggi di un film
create view cat_film_max
as
select category_id, max(totale_noleggi) as max_noleggi
  from cat_film_tot
group by category_id;

select * from cat_film_max;


-- 3. query finale, filtro la prima vista per i film che hanno il totale noleggi uguale al max
select t.*
  from cat_film_tot t
  inner join cat_film_max m on  t.category_id = m.category_id
  where t.totale_noleggi = m.max_noleggi
order by t.category_id;
  

## soluzione alternativa: faccio tutto con una query utilizzando il rank()

select * from (
	select t.*, 
	  -- row_number() over (partition by category_id order by totale_noleggi desc) as film_row,
	  rank() over (partition by category_id order by totale_noleggi desc) as film_rank 
	  from ( 
         select
		  c.category_id,
		  c.name as categoria,
		  f.film_id,
		  f.title,
		  count(r.rental_id) as totale_noleggi
		from category c
		inner join film_category fc on c.category_id = fc.category_id
		inner join film f on fc.film_id = f.film_id
		inner join inventory i on f.film_id = i.film_id
		inner join rental r on i.inventory_id = r.inventory_id
		group by c.category_id , f.film_id 
	  ) t
	) q
where q.film_rank = 1;
   
## --------------------------------------------------------------------------------------

-- utilizzo delle CTE

with
cat_film_tot as  (
	select
	  c.category_id,
	  c.name as categoria,
	  f.film_id,
	  f.title,
	  count(r.rental_id) as totale_noleggi
	from category c
	inner join film_category fc on c.category_id = fc.category_id
	inner join film f on fc.film_id = f.film_id
	inner join inventory i on f.film_id = i.film_id
	inner join rental r on i.inventory_id = r.inventory_id
	group by c.category_id, f.film_id 
),
cat_film_max  as (
	select category_id, max(totale_noleggi) as max_noleggi
	  from cat_film_tot
	group by category_id
)
select t.*
  from cat_film_tot t
  inner join cat_film_max m on  t.category_id = m.category_id
  where t.totale_noleggi = m.max_noleggi
order by t.category_id;







select * from (
	select t.*, 
	  -- row_number() over (partition by category_id order by totale_noleggi desc) as film_row,
	  rank() over (partition by category_id order by totale_noleggi desc) as film_rank 
	  from ( 
         select
		  c.category_id,
		  c.name as categoria,
		  f.film_id,
		  f.title,
		  count(r.rental_id) as totale_noleggi
		from category c
		inner join film_category fc on c.category_id = fc.category_id
		inner join film f on fc.film_id = f.film_id
		inner join inventory i on f.film_id = i.film_id
		inner join rental r on i.inventory_id = r.inventory_id
		group by c.category_id , f.film_id 
	  ) t
	) q
where q.film_rank = 1;
  

with t as (
	select
	  c.category_id,
	  c.name as categoria,
	  f.film_id,
	  f.title,
	  count(r.rental_id) as totale_noleggi
	from category c
	inner join film_category fc on c.category_id = fc.category_id
	inner join film f on fc.film_id = f.film_id
	inner join inventory i on f.film_id = i.film_id
	inner join rental r on i.inventory_id = r.inventory_id
	group by c.category_id , f.film_id 
	),
q as (
 	select t.*, 
	  -- row_number() over (partition by category_id order by totale_noleggi desc) as film_row,
	  rank() over (partition by category_id order by totale_noleggi desc) as film_rank 
      from t    
)  
select category_id, categoria, film_id, title, totale_noleggi 
from q where q.film_rank = 1;
 

with
cat_film_tot as  (
	select
	  c.category_id,
	  c.name as categoria,
	  f.film_id,
	  f.title,
	  count(r.rental_id) as totale_noleggi
	from category c
	inner join film_category fc on c.category_id = fc.category_id
	inner join film f on fc.film_id = f.film_id
	inner join inventory i on f.film_id = i.film_id
	inner join rental r on i.inventory_id = r.inventory_id
	group by c.category_id  ,f.film_id 
),
cat_film_max  as (
	select category_id, max(totale_noleggi) as max_noleggi
	  from cat_film_tot
	group by category_id
),
cat_film_rank as (
 	select t.*, 
	  -- row_number() over (partition by category_id order by totale_noleggi desc) as film_row,
	  rank() over (partition by category_id order by totale_noleggi desc) as film_rank 
      from cat_film_tot t    
),
soluzione_1 as (  
	select category_id, categoria, film_id, title, totale_noleggi  from cat_film_rank q where q.film_rank = 1 
),
soluzione_2 as (
select t.*
  from cat_film_tot t
  inner join cat_film_max m on  t.category_id = m.category_id
  where t.totale_noleggi = m.max_noleggi
order by t.category_id
)
select c.category_id, c.name as categoria, group_concat(title) as films
  from soluzione_2 s
  right join category c on s.category_id = c.category_id
  group by c.category_id;
  
  
  /* ho aggiunto una categoria che non esiste */
  select * from category;
  
  insert into category (name) values ("Videogame");

##############################################################################
##  Quando si fanno le cte, evitare di includere le descrizioni nelle cte   ##
##  includere solo i campi chiave e le funzioni di raggruppamento.          ##
##  Aggiungere le descrizioni solo nella query finale                       ##
##############################################################################

with
cat_film_tot as  (
	select
	  fc.category_id, 
	  f.film_id, 
	  count(r.rental_id) as totale_noleggi
	from  film_category fc  
	inner join film f on fc.film_id = f.film_id
	inner join inventory i on f.film_id = i.film_id
	inner join rental r on i.inventory_id = r.inventory_id
	group by fc.category_id,  f.film_id 
),
cat_film_max  as (
	select category_id
         , max(totale_noleggi) as max_noleggi
	  from cat_film_tot
	group by category_id
),
draft as (
select *
  from cat_film_tot t
  inner join cat_film_max m on  t.category_id = m.category_id
  where t.totale_noleggi = m.max_noleggi
) 
select c.category_id, c.name as categoria, group_concat(f.title) as films
  from draft s
  inner join film f on  s.film_id = f.film_id
  right join category c on s.category_id = c.category_id
  group by c.category_id;
  
  #################################################################################
  ## Esempio: Determinare il genere più popolare per ogni store

 -- Assunzioni
 -- 1. il genere  è la categoria
 -- 2. il più popolare intendo il più noleggiato
 -- 3. lo store è relativo all'inventory e non allo staff
  
 -- Determinare la categoria più noleggiata
 
 
 select
    i.store_id, fc.category_id, count(r.rental_id) as tot_rents
	from  film_category fc  
	inner join film f on fc.film_id = f.film_id
	inner join inventory i on f.film_id = i.film_id
	inner join rental r on i.inventory_id = r.inventory_id
  group by i.store_id, fc.category_id
  order by i.store_id asc, tot_rents desc;
 
 
 
-- aggiungo il nome dello store alla tabella store

ALTER TABLE store 
ADD COLUMN store_name VARCHAR(45);
 
 update store set store_name = "Centro commerciale Paolino" where store_id = 1;
 update store set store_name = "Piazza Duomo" where store_id = 2;  
 
with
store_cat_tot as  (
	select i.store_id, fc.category_id, count(r.rental_id) as tot_rents
	from  film_category fc  
	inner join film f on fc.film_id = f.film_id
	inner join inventory i on f.film_id = i.film_id
	inner join rental r on i.inventory_id = r.inventory_id
  group by i.store_id, fc.category_id
),
store_cat_max  as (
	select store_id
         , max(tot_rents) as max_rents
	  from store_cat_tot
	group by store_id
),
draft as (
select t.*
  from store_cat_tot t
  inner join store_cat_max m on  t.store_id = m.store_id
  where t.tot_rents = m.max_rents
) 
select s.store_id, s.store_name as categoria, group_concat(c.name) as cats
  from draft d
  inner join category c on d.category_id = c.category_id
  right join store s on  d.store_id = s.store_id
  group by s.store_id;



select i.store_id, fc.category_id, count(r.rental_id) as tot_rents
	from  film_category fc  
	inner join film f on fc.film_id = f.film_id
	inner join inventory i on f.film_id = i.film_id
	inner join rental r on i.inventory_id = r.inventory_id
  group by i.store_id, fc.category_id
  order by store_id asc, tot_rents desc;
  
  
  select * from category;