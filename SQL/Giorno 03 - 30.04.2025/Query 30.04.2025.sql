##########################################
## Corso Java SQL - 28.04.2025
##
## Query del 30.04.2025 
##
##########################################



CREATE TABLE movie_recommendations (
  recommendation_id INT AUTO_INCREMENT PRIMARY KEY,
  film_id SMALLINT UNSIGNED,
  staff_id TINYINT UNSIGNED,
  recommendation_text TEXT,
  last_update TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE movie_recommendations
  ADD CONSTRAINT fk_film_id
    FOREIGN KEY (film_id) REFERENCES film(film_id);

ALTER TABLE movie_recommendations
  ADD CONSTRAINT fk_staff_id
    FOREIGN KEY (staff_id) REFERENCES staff(staff_id);


ALTER TABLE movie_recommendations
  ADD COLUMN rating INT
    CHECK (rating BETWEEN 1 AND 5);


-- 1) Prima fase di inserimenti
INSERT INTO movie_recommendations (film_id, staff_id, recommendation_text, rating)
VALUES
  (100, 1, 'Ottimo film drammatico.',    5),
  (200, 2, 'Commedia divertente.',       4);


-- 2) Svuotamento della tabella utilizzando DML
delete from  movie_recommendations;


-- 3) Seconda fase di inserimenti
INSERT INTO movie_recommendations (film_id, staff_id, recommendation_text, rating)
VALUES
  (11, 1, 'Thriller avvincente.',      5),
  (22, 2, 'Documentario interessante.', 4);


-- 4) Modifica della data (last_update) della prima review
UPDATE movie_recommendations
SET last_update = STR_TO_DATE('12102023','%d%m%Y')
WHERE recommendation_id = 3;


-- 5) Eliminazione della seconda review appena inserita
DELETE FROM movie_recommendations
WHERE recommendation_id = 4;


-- 6) Mostrare l'elenco dei record risultante
SELECT * FROM movie_recommendations;
 
 /*
 3	11	1	Thriller avvincente.	2023-10-12 00:00:00	5
					
n.1 record
*/
                    
 
#############################################################################
## DQL
#############################################################################

use sakila; 

select title from film;    -- tutte le righe della tabella film e per ogni riga mostra la colonna title

select title, description 
  from film;      --  colobnne separate da virgola 
  
select *
  from film;      -- tutti le colobnne della tabella
 
 -- distinct
 
 select rental_duration from film;
 
 select rating from film;
 
 select distinct rental_duration from film;
 
 select distinct rating from film;
 
 select distinct rental_duration from film;
 
 -- select distinct COLONNA from TABELLA rappresnta il DOMINIO dei valori di COLONNA
 
 /*
 select distinct PK from tabella
 
 è uguale a
 
 select PK from tabella
 */
 
 select film_id from film;
 
 select distinct film_id from film;
 
 select distinct rental_duration, rating, rental_rate from film;
 
 
 select * from film;
 
 select distinct * from film;
 
 

-- se è presente una pk , select * coincide con select distinct * 
 

create table test (
campo1 varchar(45),
campo2 varchar(45)
);

 
insert into test
values ("A1","B1")
     , ("A2","B1")
     , ("A3","B1")
     , ("A2","B1")
     , ("A3","B1")
     , ("B2","C1");
     
select * from test;     

select distinct * from test;   

-- in questo caso sono diversi, infatto non è presente una pk
 

###################################################################################
## where

select * from film 
where rating = 'PG-13';   -- booleana

select * from film 
where rating = 'PG-13' and rental_rate = 4.99;   -- booleana

     
select * from film 
where  rating = 'PG-13' and rental_rate = 4.99 
   or  length > 120;       -- AND, OR , NOT       &&   ||   !
     
select * from film 
where ( rating = 'PG-13' && rental_rate = 4.99 )
   ||  length > 120;       -- AND, OR , NOT       &&   ||   !
 
-- PRECEDENZA () NOT AND OR

############################################################
##   PREFERIBILE USARE AND, OR, NOT                       ##
############################################################

-- operatori matematici, +, - , *, mod, 

select * from film 
 where film_id mod 2 = 0;    -- resto della divisione intera

 -- posso filtrare per campi che non vengono visualizzati
 
 select film_id, title from film 
where  rating = 'PG-13' and rental_rate = 4.99 
   or  length > 120;       -- AND, OR , NOT       &&   ||   !

-- quindi:
/*
3.  select film_id, title 
1.   from film 
2.  where  rating = 'PG-13' and rental_rate = 4.99  or  length > 120;       
*/

#############################################################
## order by
 
 select *
   from film
  where  rating = 'PG-13' and rental_rate = 4.99 or  length > 120
  order by rental_duration, description;   -- se sono tutti asc

 select *
   from film
  where  rating = 'PG-13' and rental_rate = 4.99 or  length > 120
  order by description desc; -- descending

select *
   from film
  where  rating = 'PG-13' and rental_rate = 4.99 or  length > 120
  order by description asc; -- ascending     (o non inserito)
 
 select *
   from film
  where  rating = 'PG-13' and rental_rate = 4.99 or  length > 120
  order by rental_duration desc, length asc, replacement_cost desc;  -- se c'è almeno un desc



 select *
   from film
  where  rating = 'PG-13' and rental_rate = 4.99 or  length > 120
  order by rental_duration desc, length, replacement_cost desc;

###########################################################
##  se almeno uno è desc, allora esplicitare sempre asc  ##
##  se nessuno è desc, non esplicitare asc               ##
###########################################################


/*
4.  select film_id, title 
1.   from film 
2.  where  rating = 'PG-13' and rental_rate = 4.99 or  length > 120
3.  order by description desc;      
*/


#############################################################
##   funzioni di aggregazione
##   funzione che dato un insieme di valori, ritorna un unico valore

select count(film_id) from film; 

select count(*) from film;    -- 1000

select count(*) from inventory;    -- 4581

select count(*) from rental;  -- 16044


select "1", count(*), max(rental_duration), min(rental_duration) , avg(rental_rate)
 from film;

select count(*), description  -- errore, posso usare SOLO funzioni di raggruppamento se ne uso alkeno una*
 from film;

-- * a meno che ci sia la clausola group by

-- passiamo a studenti

use studenti;


select * from studente;

select count(*) from studente;
select count(id) from studente;    

#################################################################################
## count(pk) è SEMPRE DA USARE Se la clausola from contiene più di una tabella ##
#################################################################################

select count(data_nascita) from studente ; -- where id < 60;

select count(attivo) from studente;

-- count(pk) coincide con count(*) SEMPRE

-- count(colonna) calcola quanti valori NOT NULL ci sono per quella colonna (sempre dopo il filtro)

use sakila;

select distinct rating from film;         -- dominio dei valori di rating

select count(rating) from film;           -- quante righe di film hanno rating valorizzato (not null)

select count(distinct rating) from film;  -- cardinalità di rating

select count(distinct rating), count(rating) , 100 * count(distinct rating) / count(rating)  from film;   
 


## alias

select count(distinct rating) as  cardinalita
     , count(rating) as totale
     , 100 * count(distinct rating) / count(rating)  as prc from film;   

 select film_id as id, title as titolo from film 
where  rating = 'PG-13' and rental_rate = 4.99 
   or  length > 120;       -- AND, OR , NOT       &&   ||   !


#############################################################
##   raggruppamenti

select rating, count(film_id) as tot 
  from film
  group by rating;


select distinct nomecolonna from tabella;
-- equivale a 
select nomecolonna from tabella group by nomecolonna;
-- solo se c'è una unica colonna



select rating, count(*), max(rental_duration), min(rental_duration) , avg(rental_rate)  
 from film
group by rating;  

###########################################################################
##   nella select (e order by) posso mettere: (se ho la clausola group by)
##    - colonne della clausola group by
##    - funzioni di aggregazione
###########################################################################

select rental_duration    
     , rating
     , rental_rate
     , avg(rental_rate) as rental_avg
	 , count(*) as tot
     , avg(length)  as avg_length
     , group_concat(title) as films
 from film
where film_id < 100 
group by rating, rental_duration, rental_rate
order by max(film_id)  ;

/*
5.	select rental_duration
		 , rating
		 , rental_rate
		 , count(*) as tot
		 , avg(length)  as avg_length
		 , group_concat(title) as films
1.	 from film
2.	where film_id < 100 
3.	group by rating, rental_duration, rental_rate
4.	order by max(film_id) 
*/

 
#############################################################
##   having

select rental_duration
     , rating
     , rental_rate
	 , count(*) as tot
     , avg(length)  as avg_length
     , group_concat(title) as films
 from film
where film_id < 100 
group by rating, rental_duration, rental_rate
 having count(*)  > 2
order by count(*)  ;


##########################################################
##  usare solo funzioni di raggruppamento nell'having*  ##
##########################################################
-- * questo perché se volessi mettere una condizione su una colonna singola, la posso mettere anche nella clausola where
--   che consuma meno risorse rispetto alla having

 
select rental_duration
     , rating
     , rental_rate
	 , count(*) as tot
     , avg(length)  as avg_length
     , group_concat(title) as films
 from film
where film_id < 100 
group by rating, rental_duration, rental_rate
having count(*)  > 2 and rating <> 'PG'             -- è preferibile filtrare prima 
order by count(*)  ;


select rental_duration
     , rating
     , rental_rate
	 , count(*) as tot
     , avg(length)  as avg_length
     , group_concat(title) as films
 from film
where film_id < 100 and rating <> 'PG'             -- è preferibile filtrare prima in questo modo
group by rating, rental_duration, rental_rate    
having count(*)  > 2 
order by count(*)  ;



/*
6.		select rental_duration
			 , rating
			 , rental_rate
			 , count(*) as tot
			 , avg(length)  as avg_length
			 , group_concat(title) as films
1.		 from film
2.		where film_id < 100 
3.		group by rating, rental_duration, rental_rate
4.		having count(*)  > 2
5.		order by count(*)  ;
*/


########################################################
##   un meccanismo simile a quello del linter (preprocessore)  
## permette di scrivere la query così:
 
		select rental_duration
			 , rating
			 , rental_rate
			 , count(*) as tot
			 , avg(length)  as avg_length
			 , group_concat(title) as films
		from film
		where film_id < 500 
		group by rating, rental_duration, rental_rate
		having tot > 1
		order by tot ; 


# e la traduce in:
 
		select rental_duration
			 , rating
			 , rental_rate
			 , count(*) as tot
			 , avg(length)  as avg_length
			 , group_concat(title) as films
		 from film
		where film_id < 500
		group by rating, rental_duration, rental_rate
		having count(*)  > 1
		order by count(*)  ;

 
#############################################################
##   limit

select rental_duration
			 , rating
			 , rental_rate
			 , count(*) as tot
			 , avg(length)  as avg_length
			 , group_concat(title) as films
		 from film
		where film_id < 500
		group by rating, rental_duration, rental_rate
		having count(*)  > 1
		order by count(*)  
        limit 5,10;               -- ritorna 10 righe dopo le prime 5
  
  select rental_duration
			 , rating
			 , rental_rate
			 , count(*) as tot
			 , avg(length)  as avg_length
			 , group_concat(title) as films
		 from film
		where film_id < 500
		group by rating, rental_duration, rental_rate
		having count(*)  > 1
		order by count(*)  
        limit 0,10;               -- ritorna le prime 10 righe   , si può anche scrivere limit 10;
  
  
/*  
6.     select rental_duration
			 , rating
			 , rental_rate
			 , count(*) as tot
			 , avg(length)  as avg_length
			 , group_concat(title) as films
1.		 from film
2.       where film_id < 500
3.		group by rating, rental_duration, rental_rate
4.		having count(*)  > 1
5.		order by count(*)  
7.      limit 0,10;   -- oppure limit 10, ritorna le prime 10 righe 
*/  
  
  
  
#############################################################
##   like
  
  
-- LIKE %  
  
select * 
  from film
where title like "WI%"; -- tutti i film dove il titolo inizia per "WI"

select * 
  from film
where title like "%TER"; -- tutti i film dove il titolo finisce per "TER"

  
select * 
  from film
where title like "%ED%"; -- tutti i film dove il titolo contiene "ED"


select * 
  from film
where title like "WE%"
  AND title like "%PO%";


-- LIKE _



select * 
  from film
where title like "D______A____"; -- LA PRIMA è UNA D, L'8AVA è UNA A LUNGHEZZA 12

Select * 
  from film
where title like "D______A%"; -- LA PRIMA è UNA D, L'8AVA è UNA A LUNGHEZZA QUALSIASI


select * 
  from film
where title like "%ED%"; -- tutti i film dove il titolo contiene "ED" MA NON INIZIA NE' FINISCE CON ED


select * 
  from film
where title like "_%ED%_";

select * 
  from film
where title like "_%A%A%A%";

-- ------------------------------------------------------------------------
-- if

-- selziono un valore costante
select "CIAO" as saluto, film.* from film;

select title
     , length
     , if (rental_rate<1,"Economico","Caro") as prezzo
     , if (length< 100,"Corto",if (length< 150,"Normale","Lungo")) as durata
  from  film
 order by durata ;


select 
      case
			when length< 80 then "Corto"
			when length< 115 then "Normale"
			when length< 150 then "Lungo"
			else "Mappazzone"
       end as durata
       , count(film_id) as tot
  from  film
  group by durata;
 


##########################################################################################
## inner join

select * from rental;

select  * from customer where customer_id = 130;

select  * from inventory where  inventory_id = 367;

select  * from staff where  staff_id = 1;


select rental.rental_id
     , rental.rental_date
     , rental.inventory_id
     , rental.customer_id
     , rental.return_date
     , rental.staff_id
     , rental.last_update
 from rental
 -- inner join customer on rental.customer_id = customer.customer_id
 order by rental_id;
 
 select * 
 from rental
	 inner join customer on rental.customer_id = customer.customer_id
	 inner join staff on rental.staff_id = staff.staff_id
	 inner join inventory on rental.inventory_id = inventory.inventory_id
	 inner join film on inventory.film_id = film.film_id
 order by rental_id;    

select rental.rental_id
     , rental.rental_date
     , film.title
     , customer.last_name as customer_name -- , rental.customer_id
     , rental.return_date
     , staff.last_name as staff_name -- rental.staff_id
     , rental.last_update
from rental
	 inner join customer on rental.customer_id = customer.customer_id
	 inner join staff on rental.staff_id = staff.staff_id
	 inner join inventory on rental.inventory_id = inventory.inventory_id
	 inner join film on inventory.film_id = film.film_id
 order by rental_id;    
     
     
select film.rating, avg(rental_rate), count(rental_id) as tot
 from rental
	 inner join customer on rental.customer_id = customer.customer_id
	 inner join staff on rental.staff_id = staff.staff_id
	 inner join inventory on rental.inventory_id = inventory.inventory_id
	 inner join film on inventory.film_id = film.film_id
 group by film.rating;

 

--
 
select *
from rental
	 inner join customer on rental.customer_id = customer.customer_id
	 inner join staff on rental.staff_id = staff.staff_id
	 inner join inventory on rental.inventory_id = inventory.inventory_id
	 inner join film on inventory.film_id = film.film_id
 order by rental_id;    
 
 
 ###################################################
 ##
 ##  as è facoltativo sia nella from che nella select
 ##
 ## as si usa nella select
 ## as NON si usa nella from
 ###################################################
 
 -- alias per tabelle

###########################################################################################

select
     c.customer_id
     , r.rental_id
     , r.rental_date
     , f.title
     , concat(c.first_name, " ", c.last_name) as customer_name -- , rental.customer_id
     , r.return_date
     , concat(s.first_name, " ", s.last_name) as staff_name -- rental.staff_id
     , r.last_update
from rental r 
	 inner join customer c on r.customer_id = c.customer_id
	 inner join staff s on r.staff_id = s.staff_id
	 inner join inventory  i on r.inventory_id = i.inventory_id
	 inner join film f on i.film_id = f.film_id
 order by r.rental_id;    
 
 ###########################################################################################
 
 
 
 select 2*3-4 as formula;
 
 
 select concat ("Vincenzo"," ","Errante",".....","Ciao") as nome;
 
 #################################################################
 ## left - right outer join
 
 
select count(distinct customer_id) 
from rental r ;


select count(*) from customer;                     -- 599
 
 
 select count(distinct customer_id) from rental;   -- 599  -> Vuol dire che tutti i clienti hanno fatto almeno un noleggio
 
 
  insert into customer (store_id, first_name, last_name, address_id)
  values(1,"VINCENZO", "ERRANTE", 1);
  
  select count(*) from customer;                     -- 600 , abbiamo un nuovo cliente (appena inserito) che non ha ancora fatto noleggi
  
###############################################################################################
## Quando raggruppo per PK, posso usare tutte le colonne della tabella della PK nella select ##  
###############################################################################################

############################################################################
##       Usare SEMPRE le PK nei raggruppamenti   - se possibile -         ##  
############################################################################


-- quantità di noleggi per cliente
select c.customer_id, c.first_name, c.last_name , count(rental_id)    -- qui possiamo mettere tutti i campi della tabella della pk nel raggruppamento
from rental r 
inner join customer c on r.customer_id = c.customer_id
group by c.customer_id      -- raggruppiamo per chiave primaria
order by c.customer_id desc;
   
  select * from customer order by customer_id desc;
  
-- lista dei clienti con il totale noleggi  
select c.customer_id, c.first_name, c.last_name , count(rental_id)    -- qui possiamo mettere tutti i campi della tabella della pk nel raggruppamento
from rental r right join customer c on r.customer_id = c.customer_id
 group by c.customer_id      -- raggruppiamo per chiave primaria
 order by c.customer_id desc;
 
-- usando left join


-- lista dei clienti con il totale noleggi    (LEFT)
select c.customer_id, c.first_name, c.last_name , count(rental_id)    -- qui possiamo mettere tutti i campi della tabella della pk nel raggruppamento
from customer c left join  rental r  on r.customer_id = c.customer_id
 group by c.customer_id      -- raggruppiamo per chiave primaria
 order by c.customer_id desc;     
  
  
  
select c.* , count(r.rental_id) as tot_rents
from customer c left join rental r  
on r.customer_id = c.customer_id
group by customer_id
order by c.customer_id desc;
 
select c.* , count(r.rental_id) as tot_rents
from rental r right join customer c
on r.customer_id = c.customer_id
group by customer_id
order by c.customer_id desc;
 
 

-- lista dei clienti con il totale noleggi    (LEFT)
select *
from customer c left join  rental r  on r.customer_id = c.customer_id 
 order by c.customer_id desc;     
  
 
 
 
  
  