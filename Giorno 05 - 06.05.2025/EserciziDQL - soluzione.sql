/*Esercizio DQL n.6:
 Trovare le categorie con i film meno noleggiati.
 Descrizione:
 Utilizzare una CTE per calcolare il numero totale di noleggi per ciascuna categoria e identificare le categorie con il
 minor numero di noleggi. Mostrare anche il totale dei noleggi per ogni categoria.
*/
-- Assunzioni:
-- 1. le categorie con minor numero di noleggi sono le 3 che hanno meno noleggi

with
cat_film_tot as  (
	select
	  fc.category_id,  
	  count(r.rental_id) as totale_noleggi
	from  film_category fc  
	inner join film f on fc.film_id = f.film_id
	inner join inventory i on f.film_id = i.film_id
	inner join rental r on i.inventory_id = r.inventory_id
	group by fc.category_id 
)
select c.category_id, c.name as categoria, t.totale_noleggi
  from cat_film_tot t
  inner join category c on t.category_id = c.category_id
  order by t.totale_noleggi    
  limit 3;


/* 
 Esercizio DQL n.7:
 Analizzare i noleggi di un particolare cliente e il suo impatto sul totale.
 Descrizione:
 Utilizzare una CTE per calcolare il numero totale di noleggi e il totale speso da ciascun cliente. Calcolare inoltre la
 percentuale del totale speso rispetto alla somma di tutti i clienti
 */
 
 -- Assunzioni:
 -- 1. il totale speso da ciascun cliente non è riferito  solo ai noleggi
 -- 2. mostrare anche i clienti che non hanno noleggi o pagamenti
 
 -- todo:
 -- 1. calcolare il totale dei noleggi per cliente
 -- 2. calcolare ll totale dei pagamenti per cliente
 -- 3. calcolare il totale dei pagamenti di tutti i clienti  
 -- 4. mostrare l'elenco dei clienti con il totale speso e la percentuale rispetto al totale
 
 -- 1. calcolare il totale dei noleggi per cliente
 with
 rents_count as (
	 select customer_id, count(*) as tot_noleggi
	  from rental
	 group by customer_id
 ),
 pays_sum as (
	 select customer_id, sum(amount) as tot_pagamenti
	  from payment
	 group by customer_id
 ),
 pays_tot as (
	 select sum(amount) as tot_pagamenti_clienti
	  from payment
 ) 
 select c.customer_id
      , c.first_name
      , c.last_name
      , ifnull(r.tot_noleggi, 0) as tot_noleggi
      , ifnull(p.tot_pagamenti, 0) as sum_pagamenti
      , ifnull(100*p.tot_pagamenti/t.tot_pagamenti_clienti, 0) as prc_pagamenti
   from customer c
   left join rents_count r on r.customer_id = c.customer_id
   left join pays_sum p on p.customer_id = c.customer_id
   cross join pays_tot t;
 
 /*
 if(r.tot_noleggi is null, 0, r.tot_noleggi) 
 
 equivale a 
 
 ifnull(r.tot_noleggi,0) 
 */
 
 
 -- assumendo che si usano solo i pagamenti relativi ai  noleggi dei clienti:
 with
 rents_count as (
	 select r.customer_id, count(r.rental_id) as tot_noleggi, sum(p.amount) as tot_pagamenti
	  from rental r
      inner join payment p on r.rental_id = p.rental_id
	 group by r.customer_id
),     
 pays_tot as (
	 select sum(tot_pagamenti) as tot_pagamenti_clienti
	  from rents_count
 ) 
 select c.customer_id
      , c.first_name
      , c.last_name
      , coalesce(r.tot_noleggi, 0) as tot_noleggi
      , coalesce(r.tot_pagamenti, 0) as sum_pagamenti
      , coalesce(100*r.tot_pagamenti/t.tot_pagamenti_clienti, 0) as prc_pagamenti
   from customer c
   left join rents_count r on r.customer_id = c.customer_id 
   cross join pays_tot t;
   
/*   
Esercizio DQL n.8
 Analizzare la spesa media dei clienti in relazione alla popolarità degli attori.
 Descrizione:
 Determinare quali attori appaiono nei film che generano la maggiore spesa media per cliente.
 La query deve calcolare la spesa media associata ai clienti per ciascun attore, considerando i guadagni
 provenienti dai noleggi dei film in cui gli attori hanno recitato.
 */
 
 -- Assunzioni
 
 -- 1. spesa media del cliente è la media per noleggio: somma spese cliente per noleggio / tot noleggi  
 -- 2. maggiore spesa per  cliente è l'importo massimo medio generato da un cliente
  
  
 -- 1. calcolare la spesa media di ogni cliente
with avg_cust_pay as (
	 select r.customer_id, avg(p.amount) as avg_pay
	  from rental r
	  inner join payment p on r.rental_id = p.rental_id
	 group by r.customer_id
),

 -- 2. calcolare la spesa massima media di ogni cliente
avg_cust_max as (
	select max(avg_pay) as max_pay
	  from avg_cust_pay
),
 -- 3. calcolare i clienti che generano la spesa massima
cust_max as (
	select customer_id
	 from avg_cust_pay cross join avg_cust_max
	where avg_pay = max_pay
),
-- 4. calcolare i film noleggiati da questi clienti
film_cust_max as  (
	select distinct i.film_id
	from inventory i 
	inner join rental r on i.inventory_id = r.inventory_id
	inner join cust_max c on r.customer_id = c.customer_id
)
-- 5. calcolare gli attori che recitano in questi film
select distinct a.*
 from film_cust_max f
 inner join film_actor fa on f.film_id = fa.film_id
 inner join actor a  on fa.actor_id = a.actor_id;
 
 
 -- Assunzioni
 -- 1. i clienti che hanno generato la massima spesa sono i primi 3 che hanno speso mediamente di piùalter
 
 --  1,2,3
with cust_max as (
	 select r.customer_id
	  from rental r
	  inner join payment p on r.rental_id = p.rental_id
	 group by r.customer_id
     order by avg(p.amount) desc
     limit 3
),  
-- 4. calcolare i film noleggiati da questi clienti
film_cust_max as  (
	select distinct i.film_id
	from inventory i 
	inner join rental r on i.inventory_id = r.inventory_id
	inner join cust_max c on r.customer_id = c.customer_id
)
-- 5. calcolare gli attori che recitano in questi film
select distinct a.*
 from film_cust_max f
 inner join film_actor fa on f.film_id = fa.film_id
 inner join actor a  on fa.actor_id = a.actor_id;
  
 
/* 
 Esercizio DQL n.9
 Determinare la popolarità dei generi di film in base alla fascia oraria dei noleggi.
 Descrizione:
 Analizzare la distribuzione dei noleggi in base alle fasce orarie della giornata (mattina, pomeriggio, sera, notte, in
 base a un range arbitrario di fasce) e identificare il genere di film più popolare in ciascuna fascia oraria.
 La query deve collegare le informazioni sulle categorie dei film con i dati relativi ai noleggi, segmentandoli per
 orario.
 */
 
 -- Assunzioni:
 -- fasce:  06 - 11 mattina
 --         12 - 17 pomeriggio
 --         18 - 23 sera
 --         00 - 05 notte
 -- il genere più popolare è la categoria di film più noleggiata  
  
  
with cat_period_rents as (  
  select 
		case  
		  when hour(r.rental_date) between 6 and 11 then "1. Mattina"
		  when hour(r.rental_date) between 12 and 17 then "2. Pomeriggio"
		  when hour(r.rental_date) between 18 and 23 then "3. Sera"
		  when hour(r.rental_date) between 0 and 5 then "4. Notte"		  
		end as period,
        fc.category_id,
        count(r.rental_id) as tot_rents
  from  film_category fc
  inner join film f on fc.film_id = f.film_id
  inner join inventory i on f.film_id = i.film_id
  inner join rental r on i.inventory_id = r.inventory_id
  group by period, fc.category_id
),
period_max as (
	select  period, max(tot_rents) as max_rents
	  from cat_period_rents
	group by period
),
draft as (
	select c.*
	from cat_period_rents c
	inner join period_max p on c.period =  p.period
	where c.tot_rents = p.max_rents
)  
select d.period, c.category_id, c.name,  d.tot_rents 
  from category  c
 inner join draft d on d.category_id = c.category_id
 order by period;
 
  
  
 
 /*
 Esercizio DQL n.10
 Individuare i dipendenti più influenti sulle vendite totali per store.
 Descrizione:
 Determinare l'influenza di ogni dipendente sulle vendite totali di ciascuno store.
 La query deve calcolare le vendite totali attribuite a ciascun dipendente e confrontarle con il totale complessivo
 dello store per evidenziare il contributo percentuale di ogni dipendente.*/   
 
 -- Assunzioni:
 -- 1. lo staff_id è quello relativo al noleggio non al pagamento (l'attribuzione è al dipendente che effettua il noleggio)
 -- 2. lo store è quello dove è assegnato lo staff che effettua il noleggio
 -- 3. per ogni vendita consideriamo sia le unità noleggiate che gli incassi, 
 --    considerando sia il prezzo consigliato che quello effettivo pagato
 
 
 
   
   
/* aggiungo staff */  
insert into staff ( first_name, last_name, address_id, email, store_id, active, username, password)
values ('Jessi' , 'Garcia', 5,'Jgend@sakilastaff.com',1,1, 'Jg', 'Ciao a tutti'),
 ('Charls', 'Beker', 6,'Chfuckbek@sakilastaff.com', 2, 1, 'cb', 'ciao tutti') ,
('Cosimo' , 'Renzoni', 5,'cr@sakilastaff.com',1,1, 'cr4g', 'Ciao a tutti');

update rental set staff_id=5 where (rental_id+2) mod 13 = 0; 
update rental set staff_id=4 where (rental_id+3) mod 5= 0; 
update rental set staff_id=3 where (rental_id+1) mod 3= 0;
  
   
   select * from staff;
 select staff_id, count(*) from rental group by staff_id;
  
with sales as (  
  select s.store_id, s.staff_id, count(r.rental_id) as tot_rents, sum(p.amount) as tot_pays, sum(f.rental_rate) as tot_rates
  from rental r   
  inner join staff s on r.staff_id = s.staff_id 
  inner join payment p on r.rental_id = p.rental_id
  inner join inventory i on r.inventory_id = i.inventory_id
  inner join  film f on i.film_id = f.film_id
  group by s.staff_id, s.store_id
),
sales_tot as (
 select store_id, sum(tot_rents) as gtot_rents, sum(tot_pays) as gtot_pays, sum(tot_rates) as gtot_rates
  from sales
  group by store_id 
),
draft as (
select s.*
     , 100 * tot_rents/ gtot_rents as prc_rents
     , 100 * tot_pays/ gtot_pays as prc_pays
     , 100 * tot_rates/ gtot_rates as prc_rates
  from sales s inner join sales_tot t on s.store_id = t.store_id
)
select s.store_name
	 , concat(f.first_name, " ", f.last_name) as staff_name
     , tot_rents, tot_pays, tot_rates, prc_rents, prc_pays, prc_rates
 from draft d
 inner join staff f on d.staff_id = f.staff_id
 inner join store s on d.store_id = s.store_id
order by s.store_id;   
   