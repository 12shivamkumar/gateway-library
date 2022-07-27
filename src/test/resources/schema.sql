CREATE TABLE employee (
      	id varchar(10),
	name varchar(20) NOT NULL,
	email varchar(30) NOT NULL UNIQUE,
	is_deleted boolean NOT NULL DEFAULT FALSE,
	office_id tinyint,
	created_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
 	auto_update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (id),
INDEX(office_id,name)
);

CREATE TABLE office(
	id tinyint AUTO_INCREMENT,
	office_name varchar(30) NOT NULL,
	location varchar(100) NOT NULL UNIQUE,
	created_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (id)
);

CREATE TABLE meeting_room(
id int AUTO_INCREMENT,
	room_name varchar(15) NOT NULL UNIQUE,
	office_id tinyint,
	is_open boolean NOT NULL DEFAULT TRUE,
created_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY(id),
INDEX(office_id, room_name)
);

