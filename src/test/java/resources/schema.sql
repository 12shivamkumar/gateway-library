CREATE TABLE Employee (
      	EmployeeId varchar(10),
	Name varchar(20) NOT NULL,
	Email varchar(30) NOT NULL UNIQUE,
	IsDeleted boolean NOT NULL DEFAULT FALSE,
	OfficeId tinyint REFERENCES Office(OfficeId),
	Log timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (EmployeeID),
INDEX(OfficeId, Name)
);

CREATE TABLE Office(
	OfficeId tinyint AUTO_INCREMENT,
	OfficeName varchar(30) NOT NULL,
	Location varchar(100) NOT NULL UNIQUE,
	Log timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (OfficeId)
);

CREATE TABLE MeetingRoom(
	RoomId int AUTO_INCREMENT,
	RoomName varchar(15) NOT NULL UNIQUE,
	OfficeId tinyint REFERENCES Office(OfficeId),
	IsOpen boolean NOT NULL DEFAULT TRUE,
Log timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (RoomId),
INDEX(OfficeId, RoomName)
);

CREATE TABLE Meeting(
	MeetId varchar(10),
	Description varchar(200),
	Agenda varchar(100) NOT NULL,
	OwnerId varchar(10) NOT NULL,
	DateOfMeeting date NOT NULL,
	StartTime time NOT  NULL,
	EndTime time NOT NULL,
	IsAvailable boolean NOT NULL DEFAULT TRUE,
	Log timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	RoomId int REFERENCES MeetingRoom(RoomId),
	PRIMARY KEY (MeetId),
	INDEX (RoomId, DateOfMeeting, IsAvailable)
);

CREATE TABLE EmployeeMeeting(
  EmployeeId varchar(10) REFERENCES Employee(EmployeeId),
  MeetingId varchar(10) REFERENCES Meeting(MeetingId),
  Status varchar(10) NOT NULL,
  Date date NOT NULL,
  PRIMARY KEY (EmployeeId, MeetingId),
  INDEX(Date, Status)
);
