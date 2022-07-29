INSERT INTO office(id, office_name, location) VALUES(1,"XYZ Banglore","hsr layout");
INSERT INTO office(id, office_name, location) VALUES(2,"XYZ Hyderabad","Madhapur");

INSERT INTO employee(id,name,email,office_id) VALUES("abc-10" , "abc0" , "abc0@xyz.com" , 1);
INSERT INTO employee(id,name,email,office_id) VALUES("abc-11" , "abc1" , "abc1@xyz.com" , 1);
INSERT INTO employee(id,name,email,office_id) VALUES("abc-12" , "abc2" , "abc2@xyz.com" , 1);
INSERT INTO employee(id,name,email,office_id) VALUES("abc-13" , "abc3", "abc3@xyz.com" , 1);
INSERT INTO employee(id,name,email,office_id) VALUES("abc-14" , "abc4" , "abc4@xyz.com" , 1);
INSERT INTO employee(id,name,email,office_id) VALUES("abc-15" , "abc5" , "abc5@xyz.com" , 1);
INSERT INTO employee(id,name,email,office_id) VALUES("abc-16" , "abc6" , "abc6@xyz.com" , 1);

INSERT INTO meeting_room(id ,room_name ,office_id ,is_open) VALUES (1 , "Room1" , 1 , true);
INSERT INTO meeting_room(id ,room_name ,office_id ,is_open) VALUES (2 , "Room2" , 1 , true);