CREATE TABLE members(
	userid serial PRIMARY KEY,
	userName varchar(255) NOT NULL,
	email varchar(255) NOT NULL UNIQUE,
	salt text NOT NULL,
	pswrd char(64) NOT NULL,
	createtime timestamp WITH TIME ZONE DEFAULT current_timestamp,
	active BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE members(userid serial PRIMARY KEY, userName varchar(255) NOT NULL, email varchar(255) NOT NULL UNIQUE, salt char(40) NOT NULL, pswrd char(64) NOT NULL, createtime timestamp WITH TIME ZONE DEFAULT current_timestamp);

ALTER TABLE members DROP CONSTRAINT "members_username_key";
ALTER TABLE members ADD UNIQUE (email);
ALTER TABLE members ALTER COLUMN salt TYPE text;
ALTER TABLE members ALTER COLUMN userName TYPE text;
ALTER TABLE members ALTER COLUMN email TYPE text;
ALTER TABLE members ADD COLUMN "active" BOOLEAN NOT NULL DEFAULT FALSE;

select * from information_schema.table_constraints;
select constraint_name from information_schema.table_constraints where table_name='members';