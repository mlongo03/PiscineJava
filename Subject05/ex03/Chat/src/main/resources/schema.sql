DROP SCHEMA IF EXISTS chat CASCADE;

CREATE SCHEMA IF NOT EXISTS chat;


CREATE TABLE IF NOT EXISTS chat.user (
	id BIGSERIAL NOT NULL PRIMARY KEY,
	Login varchar(100),
	password varchar(100)
);


CREATE TABLE IF NOT EXISTS chat.chatroom (
	id BIGSERIAL NOT NULL PRIMARY KEY,
	name varchar(50),
	owner BIGINT REFERENCES chat.user(id)
);



CREATE TABLE IF NOT EXISTS chat.message (
	id BIGSERIAL NOT NULL PRIMARY KEY,
	author BIGINT REFERENCES chat.user(id),
	room BIGINT REFERENCES chat.chatroom(id),
	text text NOT NULL,
	date text NOT NULL
);
