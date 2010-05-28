/*

root
    dir1
    dir2
        dir2.1
        dir2.2
            dir1
    dir3
    
*/

DELETE FROM catalogues;
DELETE FROM pictures;

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

INSERT INTO catalogues(ID, PARENT,NAME,DESCR) VALUES(1,NULL,'root','root cat');
INSERT INTO catalogues(ID, PARENT,NAME,DESCR) VALUES(2,1,'dir1','level 1');
INSERT INTO catalogues(ID, PARENT,NAME,DESCR) VALUES(3,1,'dir2','level 1');
INSERT INTO catalogues(ID, PARENT,NAME,DESCR) VALUES(4,2,'dir2.1','level 2');
INSERT INTO catalogues(ID, PARENT,NAME,DESCR) VALUES(5,2,'dir2.2','level 2');
INSERT INTO catalogues(ID, PARENT,NAME,DESCR) VALUES(6,5,'dir1','level 3');
INSERT INTO catalogues(ID, PARENT,NAME,DESCR) VALUES(7,1,'dir3','level 1');

commit;