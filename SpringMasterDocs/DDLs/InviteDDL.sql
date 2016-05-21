CREATE TABLE invitetokens(
	token text PRIMARY KEY NOT NULL,
	email text NOT NULL UNIQUE,
	createtime timestamp WITH TIME ZONE DEFAULT current_timestamp
);

CREATE TABLE invitetokens(token char(40) PRIMARY KEY NOT NULL, email varchar(255) NOT NULL, createtime timestamp WITH TIME ZONE DEFAULT current_timestamp);
ALTER TABLE invitetokens ADD UNIQUE (email);