CREATE TABLE User (
	id BIGSERIAL NOT NULL PRIMARY KEY,
	Login varchar[100],
	password varchar[100],
	own_chatrooms int NOT NULL,
	Chatrooms int NOT NULL
);


CREATE TABLE Chatroom (
	id BIGSERIAL NOT NULL PRIMARY KEY,
	name varchar[50],
	owner BIGINT REFERENCES User(id)
);



CREATE TABLE Message (
	id BIGSERIAL NOT NULL PRIMARY KEY,
	author BIGINT REFERENCES User(id),
	room BIGINT REFERENCES Chatroom(id),
	text text NOT NULL,
	date DATE NOT NULL
);
