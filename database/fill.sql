/*

root
    dir1
    dir2
        dir2.1
            pic10
            pic11
            pic12
        dir2.2
            dir1
                pic10
                pic6
            pic7
        pic8
        pic9
    dir3
        pic4
        pic5
    pic1
    pic2
    pic3
    
    
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

-- root\
INSERT INTO pictures(ID, CAT, NAME, URL, DESCR)
    VALUES(NULL,1,'pic1','http://i355.photobucket.com/albums/r460/yansa4real1/nature.jpg','');
INSERT INTO pictures(ID, CAT, NAME, URL, DESCR)
    VALUES(NULL,1,'pic2','http://beautyofnature2009.files.wordpress.com/2009/03/normal_yellowstone-river-in-hayden-valley-yellowstone-national-park.jpg?w=400&h=300','');
INSERT INTO pictures(ID, CAT, NAME, URL, DESCR)
    VALUES(NULL,1,'pic3','http://beautyofnature2009.files.wordpress.com/2009/03/nature-desktop-wallpaper.jpg','');
-- root\dir3
INSERT INTO pictures(ID, CAT, NAME, URL, DESCR)
    VALUES(NULL,7,'pic4','http://itpeoplenet.com/popup/images/Nature-Beauty.jpg','');
INSERT INTO pictures(ID, CAT, NAME, URL, DESCR)
    VALUES(NULL,7,'pic5','http://itpeoplenet.com/popup/images/Sunset.jpg','');
-- root\dir2
INSERT INTO pictures(ID, CAT, NAME, URL, DESCR)
    VALUES(NULL,3,'pic8','http://itpeoplenet.com/popup/images/Water%20lilies.jpg','');
INSERT INTO pictures(ID, CAT, NAME, URL, DESCR)
    VALUES(NULL,3,'pic9','http://itpeoplenet.com/popup/images/maple-trees-12.3.jpg','');
-- root\dir2\dir2.1
INSERT INTO pictures(ID, CAT, NAME, URL, DESCR)
    VALUES(NULL,4,'pic10','http://www.destination360.com/central-america/costa-rica/images/monteverde-cloud-forest.jpg','');
INSERT INTO pictures(ID, CAT, NAME, URL, DESCR)
    VALUES(NULL,4,'pic11','http://scienceblogs.com/startswithabang/upload/2010/01/the_greatest_story_ever_told_-/forest-1.jpg','');
INSERT INTO pictures(ID, CAT, NAME, URL, DESCR)
    VALUES(NULL,4,'pic12','http://www.africaodyssey.com/images/indian-ocean_indian-ocean_top_865_1.jpg','');
-- root\dir2\dir2.2
INSERT INTO pictures(ID, CAT, NAME, URL, DESCR)
    VALUES(NULL,5,'pic7','http://media-cdn.tripadvisor.com/media/photo-s/00/18/69/99/grea-ocean-road.jpg','');
-- root\dir2\dir2.2\dir1
INSERT INTO pictures(ID, CAT, NAME, URL, DESCR)
    VALUES(NULL,6,'pic10','http://www.destination360.com/north-america/us/washington/images/s/washington-ocean-shores.jpg','');
INSERT INTO pictures(ID, CAT, NAME, URL, DESCR)
    VALUES(NULL,6,'pic6','http://i.treehugger.com/images/2007/10/24/indian%20ocean-jj-001.jpg','');
commit;