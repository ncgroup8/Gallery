/*
Created		16.04.2010
Modified		16.04.2010
Project		NC lab3
Model		gallery
Company		
Author		Buzov Andrey
Version		
Database		Oracle 10g 
*/

-- removing objects if exists
BEGIN
  EXECUTE IMMEDIATE 'DROP TABLE catalogues CASCADE CONSTRAINTS';
EXCEPTION
  WHEN OTHERS THEN
    IF sqlcode != -0942 THEN RAISE; 
    END IF;
END;
/

BEGIN
  EXECUTE IMMEDIATE 'DROP TABLE pictures CASCADE CONSTRAINTS';
EXCEPTION
  WHEN OTHERS THEN
    IF sqlcode != -0942 THEN RAISE; 
    END IF;
END;
/

BEGIN
  EXECUTE IMMEDIATE 'DROP Sequence cat_id_seq';
EXCEPTION
  WHEN OTHERS THEN
    IF sqlcode != -02289 THEN RAISE; 
    END IF;
END;
/

BEGIN
  EXECUTE IMMEDIATE 'DROP Sequence pic_id_seq';
EXCEPTION
  WHEN OTHERS THEN
    IF sqlcode != -02289 THEN RAISE; 
    END IF;
END;
/

Create Sequence cat_id_seq
/

Create Sequence pic_id_seq
/

-- Create Tables section

Create table catalogues (
	id Integer NOT NULL ,
	parent Integer,
	name Varchar2(255) NOT NULL ,
	descr Varchar2(255),
primary key (id) 
) 
/

Create table pictures (
	id Integer NOT NULL ,
	cat Integer NOT NULL ,
	name Varchar2(255) NOT NULL ,
	url Varchar2(1024) NOT NULL ,
    descr Varchar2(255) ,
primary key (id) 
) 
/


-- Create Foreign keys section

Alter table catalogues add  foreign key (parent) references catalogues (id)  on delete cascade
/

Alter table pictures add  foreign key (cat) references catalogues (id)  on delete cascade
/


 
/* Trigger for sequence cat_id_seq for table catalogues attribute id */
Create or replace trigger t_cat_id_seq before insert
on catalogues for each row
begin
	SELECT cat_id_seq.nextval INTO :new.id FROM dual;
end;
/

/* Trigger for sequence pic_id_seq for table pictures attribute id */
Create or replace trigger t_pic_id_seq before insert
on pictures for each row
begin
	SELECT pic_id_seq.nextval INTO :new.id FROM dual;
end;
/


-- Create Triggers from referential integrity section
-- Update trigger for "catalogues"
Create Or Replace Trigger tu_catalogues
after update of id,parent on catalogues
referencing new as new_upd old as old_upd
for each row
declare numrows integer;
begin
-- Cascade child "catalogues" update when parent "catalogues" changed
if (:old_upd.id != :new_upd.id)  then
	begin
	update catalogues
	set 	parent = :new_upd.id
	where 	catalogues.parent = :old_upd.id;
	end;
end if;
-- Cascade child "pictures" update when parent "catalogues" changed
if (:old_upd.id != :new_upd.id)  then
	begin
	update pictures
	set 	cat = :new_upd.id
	where 	pictures.cat = :old_upd.id;
	end;
end if;
end;
/

-- insert ROOT catalogue
INSERT INTO catalogues(ID, PARENT,NAME,DESCR) VALUES(1,NULL,'root','root cat');

commit;


