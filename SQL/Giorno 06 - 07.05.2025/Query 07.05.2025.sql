##########################################
## Corso Java SQL - 28.04.2025
##
## Query del 07.05.2025 
##
##########################################
 /*
 ifnull  (a, b)          se a è nullo ritorna b altrimenti a
 coalesce   (x1, x2,[ .. xn]) parametri   
                 se x1 è nullo ritorna x2 se non è nullo altrimenti ritorna x3 se non è nulla
 
 select coalesce (a, b,c)
 
 coalesce con due parametri è lo stesso di ifnull
 
 coalesce (a,b)
 
 equivale a
 
 ifnull(a,b)
  
 */
 
 
 use studenti;
 select * from studente;
 
 select   s.*
     ,  ifnull(attivo,"non attivo") as s_ifnull
     
     
     , coalesce(data_nascita,attivo, "non attivo") as s_coalesce
 from
 studente s;
 
 update studente set attivo = null where id<10;
 
 
  DROP PROCEDURE update_title;
 
 use sakila;
 
 delimiter $$
 create procedure update_title ( p_film_id  int, p_title varchar(255))
 BEGIN
 
   update film 
      set title = p_title 
	where film_id = p_film_id;
    
 END
 $$
 delimiter ;
 
  
 call update_title (1, "PRIX UNDEFEATED");
 
 call update_title (1,"ACADEMY DINOSAUR");
 
 select * from film;

 
set @@autocommit= 1;


DROP PROCEDURE IF EXISTS add_new_film;
 delimiter $$
CREATE PROCEDURE add_new_film( p_title VARCHAR(255)
                             , p_description TEXT
                             , p_release_year YEAR 
                             , p_rental_duration INT
                             , p_rental_rate DECIMAL(4,2)
                             , p_length INT
                             , p_replacement_cost DECIMAL(5,2)
                             , p_category_id INT
                             ) 
BEGIN

	DECLARE new_id int;

         
    DECLARE EXIT HANDLER FOR sqlexception
    BEGIN      
    
            get     diagnostics condition 1
        
            @err_msg = message_text,
            @err_code = mysql_errno,
            @sql_state = returned_sqlstate,
            @tab  = table_name;
    
		ROLLBACK;
        
        insert into log (type,description)  values
        ("ERROR",concat("Errore ",@err_msg," durante l'inserimento del film con titolo: ",p_title, "- tabella : ", @tab));
    END;
    

	IF p_release_year < 2000 THEN
     
		
		START TRANSACTION; 

		insert into film ( title 
						 , description 
						 , release_year 
						 , rental_duration 
						 , rental_rate 
						 , length 
						 , replacement_cost
						 , language_id
						 , original_language_id)
				  values ( p_title 
						   , p_description 
						   , p_release_year 
						   , p_rental_duration 
						   , p_rental_rate 
						   , p_length 
						   , p_replacement_cost
						   , 2
						   , 1);
		  
		  select last_insert_id() into new_id;  
		  
		  -- select count(*), sum(p) into var1, var2  from tabella where...  solo se ritorna una riga
						   
		  insert into film_category (category_id, film_id)
				values  (p_category_id,  new_id);
			-- SE VA IN ERRORE L'istruzione precedente, la procedura viene interrotta  
			  
		  COMMIT;    
		  
		  insert into log (type,description)  values ("INFO",concat("Inserito nuovo film  con id:", new_id));
		ELSE
           insert into log (type,description)  values ("ERROR","Si è provato a inserire un titolo dal 2000 in poi");
        END IF;
END                             
$$
delimiter ;                             


  
select * from category;
 
call add_new_film ("Reservoir Dogs","Best of tarantino",1992,5,7.99,170,1.40,1);

-- where...  solo se ritorna una riga
                       
	  insert into film_category (category_id, film_id)
            values  (80,  1);


call add_new_film ("Pulp Fiction 3","The Very 3 Best of tarantino",1999,5,7.99,170,1.40,230);



select * from film_category order by film_id desc;



select * from film order by film_id desc ;
 
 select * from log order by id desc; 
 
 rollback;
 truncate table log;
 
 -- versione con signal
 DROP PROCEDURE IF EXISTS add_new_film;
 delimiter $$
CREATE PROCEDURE add_new_film( p_title VARCHAR(255)
                             , p_description TEXT
                             , p_release_year YEAR 
                             , p_rental_duration INT
                             , p_rental_rate DECIMAL(4,2)
                             , p_length INT
                             , p_replacement_cost DECIMAL(5,2)
                             , p_category_id INT
                             ) 
BEGIN

	DECLARE new_id int;
 
    DECLARE EXIT HANDLER FOR sqlexception
    BEGIN        
        DECLARE err_msg TEXT;
        DECLARE err_code INT;
        DECLARE sql_state VARCHAR(5);
        DECLARE tab VARCHAR(1000); 
    
        get stacked diagnostics condition 1
            err_msg = message_text,
			err_code = mysql_errno,
            sql_state = returned_sqlstate,
            tab  = table_name;
    
		ROLLBACK;
        
        
        insert into log (type,description)  values
        ("INFO","Log Prima");
        
        insert into log (type,description)  values
        ("ERROR",concat("Errore ",err_msg," durante l'inserimento del film "));
        
        
        insert into log (type,description)  values
        ("INFO","Log Dopo");
    END;  
    
	if p_release_year >=  2000 then
		signal sqlstate "45000"
               set message_text = "Si è provato a inserire un titolo dal 2000 in poi", 
                    mysql_errno = 8000;
	end if	; 
    
	START TRANSACTION; 

	insert into film ( title 
					 , description 
					 , release_year 
					 , rental_duration 
					 , rental_rate 
					 , length 
					 , replacement_cost
					 , language_id
					 , original_language_id)
			  values ( p_title 
					   , p_description 
					   , p_release_year 
					   , p_rental_duration 
					   , p_rental_rate 
					   , p_length 
					   , p_replacement_cost
					   , 2
					   , 1);
	  
	  select last_insert_id() into new_id; 
      
      -- equivale a   set new_id = last_insert_id();  ma non  è ANSI
      
	  
	  -- select count(*), sum(p) into var1, var2  from tabella where...  solo se ritorna una riga
					   
	  insert into film_category (category_id, film_id)
			values  (p_category_id,  new_id);
		-- SE VA IN ERRORE L'istruzione precedente, la procedura viene interrotta  
		  
	  COMMIT;    
	  
	  insert into log (type,description)  values ("INFO",concat("Inserito nuovo film  con id:", new_id)); 
END                             
$$
delimiter ;    

1064, 


show variables;
set autocommit = 0;