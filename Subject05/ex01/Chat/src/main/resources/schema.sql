CREATE TABLE User (
	id BIGSERIAL NOT NULL PRIMARY KEY,
	Login varchar[100],
	password varchar[100],
	own_chatrooms BIGINT[] NOT NULL,
	Chatrooms BIGINT[] NOT NULL
);


CREATE TABLE Chatroom (
	id BIGSERIAL NOT NULL PRIMARY KEY,
	name varchar[50],
	owner BIGINT REFERENCES User(id),
	messages BIGINT[] NOT NULL
);



CREATE TABLE Message (
	id BIGSERIAL NOT NULL PRIMARY KEY,
	author BIGINT REFERENCES User(id),
	room BIGINT REFERENCES Chatroom(id),
	text text NOT NULL,
	date text NOT NULL
);
