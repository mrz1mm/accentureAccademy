##########################################
## Corso Java SQL - 28.04.2025
##
## Query del 13.05.2025 
##
##########################################


use etl ;

create table customer_bkp like sakila.customer;           -- 1

insert into customer_bkp 
select * from sakila.customer where customer_id >500;

select * from customer_bkp;


insert into customer_bkp 
select * from sakila.customer where store_id =2;

##############################################################################
## Caso 1 - inserisci la nuova riga e se trovi la elimini prima di reinserila
##          NO TARGET PRODUZIONE
replace into customer_bkp 
select * from sakila.customer where store_id =2;
 
##############################################################################
## Caso 2 - inserisci la nuova riga e se trovi la modifichi
##          SI' TARGET PRODUZIONE 
insert into customer_bkp
select * from sakila.customer as new_row 
 where store_id =2
on duplicate key    -- PRIMARY
  update first_name = new_row.first_name 
       , address_id = new_row.address_id
       , active = new_row.active;  
       
       
##############################################################################
## Caso 3 - inserisci la nuova riga e se esiste, ignora       
##          n.b. ignora l'errore sulla riga e va avanti. 
##                va in errore in caso di duplicato poiché essite la PK
##          NO TARGET PRODUZIONE
insert ignore into customer_bkp
select * from sakila.customer as new_row 
 where last_name like 'A%'; 

-- se non ho la pk replace into , upsert e ignore mi duplicheranno le righe;

create table customer_data as
   select * from sakila.customer where customer_id between 100 and 150;


replace into customer_data
select * from sakila.customer where customer_id between 90 and 110;

select * from customer_data; 
-- le righe con id da 100 a 110 sono duplicate

## #########################################################
## ESTRACT, TRANSFORM E LOAD

-- LOAD

-- 1. STRUTTURA  CHE CONTIENE TUTTE SOLE LE RIGHE CHE DEVO CARICARICARE/MODIFICARE

-- Caso d'uso:
-- Rimuovere l'asterisco dal nome delle righe della tabella  customer

select * from sakila.customer;



-- EXTRACT

-- estraggo le righe da bonificare e lo porto nel mio ambiente di lavoro

-- 1. identifico le righe
select * from sakila.customer where first_name like '*%';


-- 2. creo la tabella di lavoro
create table cust like sakila.customer;

-- 3. popolo la tabella   (l'estrazione vera e propria av  viene qua)
insert into cust
select * from sakila.customer where first_name like '*%';


-- TRANSFORM
-- faccio la bonifica

-- 1. verifico la funzione per la bonifica
select first_name, substr(first_name,2) from cust; 

-- 2. effettuo la bonifica
update cust
   set first_name =  substr(first_name,2);

select * from cust;

-- LOAD

/*  IL CARICAMENTO IN CASO DI BONIFICA AVVIENE SEMPRE CON UNA QUERY DEL GENERE */
update target t
inner join tabella_trasformazione  d
  on t.pk  = d.pk
  set [ t.campo1 = d.campo1, t.campo2 = d.campo2 .... ]  -- senza logica
*/

update sakila.customer t
 inner join cust d on t.customer_id = d.customer_id
   set t.first_name = d.first_name;     
   
-- si poteva fare tutto in unica query   
   update customer 
     set first_name =  substr(first_name,2)
     where first_name like '*%';
   






 


