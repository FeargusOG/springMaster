CREATE TABLE members_roles(
	userEmail text NOT NULL references members(userNameEmail),
	role text NOT NULL,
	PRIMARY KEY(userEmail, role)
);

CREATE TABLE members_roles(userEmail varchar(255) NOT NULL references members(email),role varchar(10) NOT NULL,PRIMARY KEY(userEmail, role));