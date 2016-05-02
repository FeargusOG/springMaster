CREATE TABLE members_roles(
	userEmail varchar(255) NOT NULL references members(email),
	role varchar(10) NOT NULL,
	PRIMARY KEY(userName, role)
);

CREATE TABLE members_roles(userEmail varchar(255) NOT NULL references members(email),role varchar(10) NOT NULL,PRIMARY KEY(userEmail, role));