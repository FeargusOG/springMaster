CREATE TABLE members(
	userid serial PRIMARY KEY,
	userName varchar(255) NOT NULL,
	email varchar(255) NOT NULL,
	salt char(40) NOT NULL,
	pswrd char(40) NOT NULL,
	createtime timestamp WITH TIME ZONE DEFAULT current_timestamp
);

CREATE TABLE members(userid serial PRIMARY KEY, userName varchar(255) NOT NULL, email varchar(255) NOT NULL, salt char(40) NOT NULL, pswrd char(40) NOT NULL, createtime timestamp WITH TIME ZONE DEFAULT current_timestamp);