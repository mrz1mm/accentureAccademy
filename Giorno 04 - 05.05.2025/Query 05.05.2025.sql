##########################################
## Corso Java SQL - 28.04.2025
##
## Query del 05.05.2025 
##
##########################################

-- risoluzione esercizio DQL del 30.04.2025
/*
1. Trovare tutti i film con un rental_rate superiore a 2.99 e una 
durata (length) inferiore a 60 minuti. 
*/
select *
  from film
where rental_rate > 2.99 
  and length < 60;


/*
2. Elencare i primi 10 attori per cognome in ordine alfabetico. 
*/

select *
  from actor
order by last_name desc
limit 10; 

/*
3. Calcolare il numero di noleggi (rental_count) per ogni 
classificazione dei film (rating). 
*/


select f.rating
     , count(r.rental_id) as rental_count     
from rental r 
	 inner join inventory  i on r.inventory_id = i.inventory_id
	 inner join film f on i.film_id = f.film_id
group by f.rating;

 
  

/*
4. Aggiungere alla query anche la durata media del tempo di 
noleggio  dei film per quel rating 
*/

select f.rating
     , count(r.rental_id) as rental_count     
     , avg( datediff(return_date,rental_date) ) as avg_duration
from rental r 
	 inner join inventory  i on r.inventory_id = i.inventory_id
	 inner join film f on i.film_id = f.film_id
-- where return_date is not null
group by f.rating;
 
###############################################################
##    le funzioni di aggregazione ignorano i valor i null    ##
###############################################################

/*
x1 c2
a  5
b  7
d 
e  6
f 
g  6

avg(c2) = 6
*/

select null + 12;
 
 ## operatore is per il null   is null oppure is not null
 
select * from  rental where return_date is null;
 

/*
5. Determinare il numero totale di film (total_films) per ogni 
categoria di film (category_id) che hanno un rental_rate di 
0.99.
*/


-- NON VA FATTO COSI' (filtro nell'having)
select c.category_id, c.name, f.rental_rate
     , count( f.film_id ) as tot_films
  from film f 
  inner join film_category fc on f.film_id = fc.film_id  
  inner join category c on fc.category_id = c.category_id   
 group by  c.category_id,  f.rental_rate
 having f.rental_rate =  0.99;                      -- NOOOOOO!!!
     
     
-- VA FATTO COSI' (filtro nel WHERE)     
select c.category_id, c.name
     , count( f.film_id ) as tot_films
  from film f 
  inner join film_category fc on f.film_id = fc.film_id  
  inner join category c on fc.category_id = c.category_id   
 where f.rental_rate =  0.99 
 group by  c.category_id
 order by c.category_id;

-- ###########################################
-- FULL OUTER JOIN

select * from A full join B  on A.A5 = B.B1;

A1 A2 A3 A4 A5 
10  g  3 44  1
20  g 23  2  
30  k 33 11  2

B1 b2 b3
1   A  B
2   C  D
3   E  D
4   X  Y
5   K  C

A1 A2 A3 A4 A5 B1 B2 B3     -- INNER JOIN
10  g  3 44  1  1  A  B 
30  k 33 11  2  2  C  D


A1 A2 A3 A4 A5 B1 B2 B3     -- FULL OUTER JOIN
10  g  3 44  1  1  A  B
30  k 33 11  2  2  C  D
20  g 23  2  
                3  E  D
                4  X  Y 
                5  K  C 
 */
 
 
 ## #######################################################
 -- CROSS JOIN : PRODOTTO CARTESIANO

select * from staff;

select * from store;

select * from staff cross join store;   

select * from staff cross join customer;
-- equivale a 
select * from staff , customer;

select * from staff inner join customer;    -- anche questo fa il cartesiano anche se dovrebbe dare errore

 
####################################################################
##    scrivere SEMPRE inner / cross / left / full prima di join   ##
####################################################################
 
-- query senza inner join
select c.category_id, c.name
     , count( f.film_id ) as tot_films
  from film f , film_category fc , category c 
  where f.film_id = fc.film_id  
     and fc.category_id = c.category_id 
     and f.rental_rate =  0.99 
 group by  c.category_id
 order by c.category_id;
 

-- ###############################################################################


select rental_id, rental_date, inventory_id 
 from rental where rental_id < 10
 union
select first_name, last_name, last_update from actor where actor_id < 5; 



select * from rental ;    -- rental_id,  rental_date, customer_id, staff_id, inventory_id

select * from payment ;   -- payment_id, payment_date, customer_id, staff_id, amount


-- creiamo un diario  delle attività

select "NOLEGGIO" as tipo_operazione
	 , rental_id as id 
     , rental_date as data_operazione, customer_id, staff_id
     , concat("film:",f.title) as note
from rental r 
inner join inventory  i on r.inventory_id = i.inventory_id
inner join film f on i.film_id = f.film_id
-- where rental_id < 10
union  -- in questo caso equivale a union all
select "PAGAMENTO", payment_id, payment_date, customer_id, staff_id, concat("Importo pagato: ",p.amount," EUR")
from payment  p
-- where payment_id < 10
order by  data_operazione, tipo_operazione;    -- solo del risultato



/* differenza fra union e union all

    SELECT 1
       a
       b
       c
       d
  UNION (ALL)
     SELECT 2
       b
       c
       d
       e
       f
       
union all > a b c d b c d e f

union     > a b c d e f
	
*/

## union all molto più efficiente di union



#########################################################
##   union (all) si puà fare per n tabelle con n>=2    ##
#########################################################

/*

select * from a
union  
select * from b
limit y
union
select * from c
union 
select * from a
order by x                   -- si mette solo una volta ed è uguale a tutto il raggruppamento
limit k
*/

################################################################
## -  ORDER BY solo per la query totale                       ##
## -  limit sia per la query totale che per la singola query  ##
################################################################



-- #############################################################

-- operatore in

select * from film where rating in ('G','PG-13');
-- equivale

select * from film 
 where rating = 'G' or rating = 'PG-13';
 
-- una query che ritorna una colonna è un insieme 
 
 
select *                   -- meno efficiente
 from film 
 where film_id in (select film_id from film_category where category_id = 11);  
 -- equivale a
select *                   -- più efficiente
 from film inner join film_category  on  film.film_id = film_category.film_id
where film_category.category_id = 11;


-- #########################################################
-- exists

-- exists ( query) -> true se la query ritorna almeno una riga 
 
select *                   -- efficienza di mezzo
 from film f
 where exists (select film_id from film_category where category_id = 11 and film_id = f.film_id);  
 
 
-- between   apparteenenza a un range


select * from film where rental_rate between 0.50 and 1.20;

-- equivale a:

select * from film where rental_rate>=0.50 and rental_rate<= 1.20;
 
 
 
 -- #########################################################
 
 -- sottoquery nella clausola from
 
 select count(*) from  (
 
 	 select "NOLEGGIO" as tipo_operazione
		 , rental_id as id 
		 , rental_date as data_operazione, customer_id, staff_id
		 , concat("film:",f.title) as note
	from rental r 
	inner join inventory  i on r.inventory_id = i.inventory_id
	inner join film f on i.film_id = f.film_id
	-- where rental_id < 10
	union  -- in questo caso equivale a union all
	select "PAGAMENTO", payment_id, payment_date, customer_id, staff_id, concat("Importo pagato: ",p.amount," EUR")
	from payment  p
 ) q;
 
 

 select d.tipo_operazione, d.id, d.data_operazione
	  , concat(c.first_name, " ", c.last_name) as customer_name 
      , concat(s.first_name, " ", s.last_name) as staff_name 
      , note
 from (
	 select "NOLEGGIO" as tipo_operazione
		 , rental_id as id 
		 , rental_date as data_operazione, customer_id, staff_id
		 , concat("film:",f.title) as note
	from rental r 
	inner join inventory  i on r.inventory_id = i.inventory_id
	inner join film f on i.film_id = f.film_id
	-- where rental_id < 10
	union  -- in questo caso equivale a union all
	select "PAGAMENTO", payment_id, payment_date, customer_id, staff_id, concat("Importo pagato: ",p.amount," EUR")
	from payment  p
	-- where payment_id < 10
) d 
  inner join customer c on  d.customer_id =  c.customer_id
  inner join staff s on d.staff_id = s.staff_id
	order by  d.data_operazione, d.tipo_operazione  ;  -- solo del risultato
    
    
    
-- ##########################################
-- viste

create view operations as
 select "NOLEGGIO" as tipo_operazione
		 , rental_id as id 
		 , rental_date as data_operazione, customer_id, staff_id
		 , concat("film:",f.title) as note
	from rental r 
	inner join inventory  i on r.inventory_id = i.inventory_id
	inner join film f on i.film_id = f.film_id
	-- where rental_id < 10
	union  -- in questo caso equivale a union all
	select "PAGAMENTO", payment_id, payment_date, customer_id, staff_id, concat("Importo pagato: ",p.amount," EUR")
	from payment  p;
    
select * from operations;    studente
    
    
-- sostituisco la sottoclausola precedente con la vista    
    select d.tipo_operazione, d.id, d.data_operazione
	  , concat(c.first_name, " ", c.last_name) as customer_name 
      , concat(s.first_name, " ", s.last_name) as staff_name 
      , note
 from operations d 
  inner join customer c on  d.customer_id =  c.customer_id
  inner join staff s on d.staff_id = s.staff_id
	order by  d.data_operazione, d.tipo_operazione  ;  -- solo del risultato
    
    
    -- errore
    insert into operations 
    values ("ciao", 11 ,null,"giovannI","ulberto","kljlkjlKJ");
    
  -- eliminare una vista
  drop view operations; 
  
  ####################################################################
  ##   attenzione che una operazione DDL su una tabella inclusa in  ##
  ##   una vista, potrebbe rendere invalida la vista stessa         ##
  ##   (ad esempio se viene eliminata una colonna dalla tabella     ##
  ##    inclusa nella vista)                                        ##
  ####################################################################
  
  
  
  
-- ##################################################################    
    

create temporary table operations as
 select "NOLEGGIO" as tipo_operazione
		 , rental_id as id 
		 , rental_date as data_operazione, customer_id, staff_id
		 , concat("film:",f.title) as note
	from rental r 
	inner join inventory  i on r.inventory_id = i.inventory_id
	inner join film f on i.film_id = f.film_id
	-- where rental_id < 10
	union  -- in questo caso equivale a union all
	select "PAGAMENTO", payment_id, payment_date, customer_id, staff_id, concat("Importo pagato: ",p.amount," EUR")
	from payment  p;
    
    
   select * from operations; 
   
   update operations set staff_id = 99 where staff_id = 2;
    
    
    