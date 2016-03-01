CREATE TABLE members(
	userid serial PRIMARY KEY,
	userName varchar(255) NOT NULL,
	email varchar(255) NOT NULL UNIQUE,
	salt char(40) NOT NULL,
	pswrd char(40) NOT NULL,
	createtime timestamp WITH TIME ZONE DEFAULT current_timestamp
);

CREATE TABLE members(userid serial PRIMARY KEY, userName varchar(255) NOT NULL, email varchar(255) NOT NULL UNIQUE, salt char(40) NOT NULL, pswrd char(40) NOT NULL, createtime timestamp WITH TIME ZONE DEFAULT current_timestamp);

ALTER TABLE members ADD UNIQUE (userName);
ALTER TABLE members DROP CONSTRAINT "members_username_key";
ALTER TABLE members ADD UNIQUE (email);

select * from information_schema.table_constraints;
select constraint_name from information_schema.table_constraints where table_name='members';