/*
Creare una stored procedure che consenta l'aggiornamento di un noleggio, permettendo la modifica del film
associato nella tabella rental e registrando le modifiche nella tabella log.
Requisiti:
• utilizzare la tabella di log fornita
la procedura ha come parametri di input:
o rental_id (noleggio da modificare)
o film_id (il film da noleggiare corretto)
•

l'inventory deve essere recuperato dal film che si trova nello stesso store dove lavora lo staff che ha
effettuato il noleggio
•
La procedura deve utilizzare transazioni per garantire l'integrità dei dati: se una delle operazioni fallisce,
nessun cambiamento deve essere applicato.

Assunzioni: Se cerco di aggiornare un inventory come faccio a sapere se quel film non è stato già noleggiato? O se è effettivamente nello store? 
UTILIZZO LA RETURN_DATE o la RENTAL_DATE se è null vuol dire che o che non è stato ancora restituito o che è libero per il noleggio
Assunzioni: ma il film esiste?

*/

drop procedure if exists rental_update;

delimiter $$

create procedure rental_update(
    in p_rental_id int,
    in p_film_id int
)
begin
    declare v_inventory_id int;
    declare v_staff_id int;
    declare v_store_id int; 
	declare v_old_film_id int;
	declare v_rows int;
	
    declare exit handler for sqlexception
    begin
    get diagnostics condition 1
            @err_msg = message_text,
            @sql_state = returned_sqlstate;
        rollback;
        
        insert into log (type,description) values ("Errore",@err_msg);
    end;
    
#############################################################################
## CHECK
############

    -- recupero staff_id e inventory originale
    select staff_id, inventory_id
    into v_staff_id, v_old_inventory_id
    from rental
    where rental_id = p_rental_id;

    -- recupero lo store_id associato allo staff
    select store_id
    into v_store_id
    from staff
    where staff_id = v_staff_id;

	
-- controllo se il noleggio esiste
	select  film_id into v_old_film_id 
	  from rental r inner join inventory i on r.inventory_id = i.inventory_id 
	  where rental_id = p_rental_id) ;
 	  
	if v_old_film_id is null then 
		signal sqlstate "45000"
			set message_text = "errore: rental inesistente";
	end if; 

    if v_old_film_id  = p_film_id  is null then 
		signal sqlstate "45000"
			set message_text = "errore: il film da noleggiare è già quello indicato";
	end if;

-- controllo se il film esiste
    if not exists (select 1 from film where film_id = p_film_id) then 
		signal sqlstate "45000"
			set message_text = "errore: film inesistente";
	end if;


    -- cerco una copia disponibile del nuovo film nello stesso store
    select i.inventory_id
    into v_inventory_id
    from inventory i
    where i.film_id = p_film_id
      and i.store_id = v_store_id
      and i.inventory_id not in (
          select r.inventory_id
          from rental r
          where r.return_date is null
      )
    limit 1;

    -- se non trovo una copia disponibile, interrompo
    if v_inventory_id is null then 
        signal sqlstate '45000'
            set message_text = "errore: nessuna copia disponibile per questo film nello store";
    end if;
	
#############################################################################	

    -- aggiorno il noleggio con la nuova copia
    start transaction;


#############################################################################	
## update 
#########

    update rental
    set inventory_id = v_inventory_id
    where rental_id = p_rental_id;
	
	select row_count() into rows;   -- restituisce il numero di righe modificate 
	
    if rows != 1 then 
        signal sqlstate '45000'
            set message_text = "errore: l'update non ha modificato una riga";	 
    end if;
	
	

#############################################################################	
    commit;
	
    -- registro il cambiamento nella tabella di log
    insert into log (type, description)
    values ("Aggiornamento table rental", concat("noleggio ", p_rental_id," con film_id: ",p_film_id,' aggiornato con successo da inventory ', v_old_inventory_id,' a ', v_inventory_id)
    );
end$$

delimiter ;
-- ERRORE RENTAL INESISTENTE NEL LOG

call rental_update(16050,1000);

-- ERRORE FILM INESISTENTE NEL LOG
call rental_update(1000, 2500);

-- AGGIORNAMENTO CON SUCCESSO
call rental_update(10,10);

select * from log;

 select * from film where film_id = 1500;
 select * from rental order by rental_id desc
 

-- VE: molto bene. il procedimento e la soluzione. Ma:
--     - start transaction va messo subito prima la prima DML
--     - commit va messo PRIMA della scrittura su LOG
------------------------------------------------------------------------ 

