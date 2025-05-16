##########################################
## Corso Java SQL - 28.04.2025
##
## Query del 29.04.2025 
##
##########################################

use studenti;

-- CREATE

CREATE TABLE studente (
      id INT AUTO_INCREMENT PRIMARY KEY,
      nome VARCHAR(50) NOT NULL,
      cognome VARCHAR(50) NOT NULL,
      email VARCHAR(100) UNIQUE NOT NULL
    );

-- ALTER
  ALTER TABLE studenti.studente        -- posso accedere alla tabella utilizzando come prefisso il selettore di schema
  ADD COLUMN data_nascita DATETIME;
  
  ALTER TABLE studente
  ADD COLUMN attivo int;
  
  
 # ALTER TABLE studente
 # DROP COLUMN data_nascita;
  
  ALTER TABLE studente
  MODIFY COLUMN email VARCHAR(100) UNIQUE NULL;
  
  -- ALTER
  ALTER TABLE studente
  MODIFY COLUMN data_nascita varchar(20);
  
  drop table studente;
  
  
  -- --------------------------------------------------------------------
  -- DML
  
  INSERT INTO studente (nome, cognome, email)
VALUES ("Mario", "Rossi", "mario.rossi@email.com");   -- id = 1

-- va in errore in quanto nome non può essere null
  INSERT INTO studente ( cognome, email)
VALUES ("Rossi", "NULLO.rossi@email.com");             -- ERRORE non inserito
   
ALTER TABLE studente
MODIFY COLUMN  nome VARCHAR(50) NOT NULL DEFAULT "NON INSERITO";

 INSERT INTO studente ( cognome, email)
VALUES ("Rossi", "NULLO.rossi@email.com");             -- inserisce la riga e valorizza nome con "NON INSERITO"

   
  
  INSERT INTO studente (nome, cognome, email)
VALUES ("Valerio", "Rossi", "valerio.rossi@email.com");   -- id = 3


-- ERRORE email esistente  
  INSERT INTO studente (nome, cognome, email)
VALUES ("Valerio", "Rossi", "valerio.rossi@email.com");  -- non inserito   ma stacca un nuocvo 
 

  INSERT INTO studente (nome, cognome, email)
VALUES ("Massimo", "Rossi", "massimo.rossi@email.com");   -- id = 5
  
  

  INSERT INTO studente (id,  nome, cognome, email)
VALUES (34,  "Marina", "Rossi", "ma.rossi@email.com");     -- id = 34


INSERT INTO studente (nome, cognome, email)
VALUES ("Claudia", "Rossi", "cl.rossi@email.com");       -- id = 35


  INSERT INTO studente (id,  nome, cognome, email)
VALUES (7,  "Valentina", "Rossi", "va.rossi@email.com");     -- id = 7


INSERT INTO studente (nome, cognome, email)
VALUES ("Paola", "Rossi", "pao.rossi@email.com");       -- id =  36
-- riprovo 20 volte e dà errore duplicate key

INSERT INTO studente (nome, cognome, email)
VALUES ("Paola", "Rossini", "pao.rossini@email.com");       -- id =  57


-- inserimento multiplo
INSERT INTO studente (nome, cognome, email)
VALUES ("Giacomo", "Rossini", "jack.rossini@email.com"),
        ("Gianluca", "Rossini", "gia.rossini@email.com"),
       ("Ferdinando", "Rossini", "fefe.rossini@email.com");

-- se voglio inserire un'altra colonna, devo valorizzarla per ogni riga dell'insert multiplo
INSERT INTO studente (nome, cognome, email,data_nascita)
VALUES ("Rossana", "Rossini", "jrss.rossini@email.com", null),
        ("Rosanna", "Rossini", "rnn.rossini@email.com","11/10/1956"),
       ("AnnaRosa", "Rossini", "ar.rossini@email.com", null);


-- MODIFICA CON UPDATE
-- 

-- 62	Rosanna	Rossini	rnn.rossini@email.com	11/10/1956

UPDATE studente
SET email =  "rsn.rossini@email.com"
WHERE id = 65;


update  studente
 set data_nascita = "10/10/2000"
   , attivo = 1
where  cognome = "Rossi";          -- verifica la condizione per ogni riga     

update  studente
 set data_nascita = "10/10/2010"
where  cognome = "asdljhasdlòkjhasd" ;   -- 0 rows affected nessun errore


update studente
set attivo = 1
where id > 0;


####################################################################################
## UPDATE e DELETE andrebbero sempre usata puntualmente con condizione solo su PK ##
####################################################################################
-- DELETE 
DELETE from studente
WHERE ID = 61;


delete from studente 
where  cognome = "Rosertertertetsini";        


-- senza where

update studente
set attivo = 0;

-- DML
delete from studente;   -- elimina tutte le righe della tabella e i contatori id rimangono al valore precedente

-- DDL
truncate table studente;  -- svuota la tabella   e i contatori id ripartono da 1

-- nella realtà truncate table è una DDL, equivale a DROP + CREATE


-- con questo comando disattiva SAFE updates e deletes
set sql_safe_updates = 0;     

update studente set attivo = 0;  -- ok

set sql_safe_updates = 1;     

update studente set attivo = 0;  -- errore
-- mysql

update studente set attivo = 11
where id > 0;
 
update studente set attivo = 12
where email= "jack.rossini@email.com";
 
 INSERT INTO studente (id,  nome, cognome, email)
VALUES (-34,  "Marina", "Rossi", "ma.rossi@email.com");     -- id = 34

#############################################################
##  le PK AUTOINCREMENT DOVREBBERO SEMPRE ESSERE UNSIGNED  ##
#############################################################
 
/*
 note su byte e unsigned

byte = 8 bit


11111111 

quante combinazioni? 2 ^ 8 = 256

posso rappresentare i numeri da 0 a 255

ma se uso un bit per il segno

0 se positivo e 1 se negativo
con  segno da -128 a 127


   
 1 tinyint 
 2 smallint
 3 mediumint
 4 int     = valore max  -2^31 a 2^31-1
 unsigned int    =       0 a 2^32-1
 
 8  bigint  
 
 --------------------
 
 10101010.00101010101
 
 virgola mobile
 
 
 float, double
 -------
 23456,1
 0,38123
 
 99999,9
 0,00001
*/ 
 
 
 
 
 
 
 









