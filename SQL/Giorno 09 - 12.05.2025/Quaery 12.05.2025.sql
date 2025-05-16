##########################################
## Corso Java SQL - 28.04.2025
##
## Query del 12.05.2025 
##
##########################################

use studente;


  delimiter $
  CREATE TRIGGER check_data_before_insert
  BEFORE INSERT ON studente
  FOR EACH ROW 
  BEGIN    
     IF NEW.data_nascita IS NULL THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Data di nascita deve essere diversa da null';
    END IF;
  END;
  $
  delimiter ;
  
  
  insert into studente (nome,cognome,email,data_nascita) 
  values ("Massimo","Rossetti","m@r.ikt","11/10/2028");
  
	--	 ("Claudio","Longhu","cl@r.ikt", null);
         
         
select * from studente;         




  delimiter $
  CREATE TRIGGER check_data_before_delete
  BEFORE DELETE ON studente
  FOR EACH ROW 
  BEGIN    
     IF OLD.cognome = "Rossi" THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'I signori Rossi sono INCANCELLABILI!!!';
    END IF;
  END;
  $
  delimiter ;
  
  
  DELETE FROM studente where id = 2;
  
  
  
  

  delimiter $
  CREATE TRIGGER check_data_before_update
  BEFORE UPDATE ON studente
  FOR EACH ROW 
  BEGIN    
     IF OLD.email != NEW.email THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = "Impossibile modificare l'email";
    END IF;
  END;
  $
  delimiter ;
  
  select * from studente;
  
  update studente
  set nome = "Rosa"
    , cognome = "Rossi"
    -- , email = "rosarossi@rossi.com"
  where id = 2;
  
  
  CREATE TABLE `log` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `type` varchar(45) NOT NULL,
  `description` text NOT NULL,
  PRIMARY KEY (`id`)
)  ;




  delimiter $
  CREATE TRIGGER do_after_insert
  AFTER INSERT ON studente
  FOR EACH ROW 
  BEGIN    
     INSERT INTO log (type,description) values ( "INFO", concat("Inserisco studente con id:", NEW.id));
  END;
  $
  delimiter ;


  insert into studente (nome,cognome,email,data_nascita) 
  values ("Massimo","Rossetti","m@r.ikt","11/10/2028");
  
  select * from log;