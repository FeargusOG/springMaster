CREATE TABLE members_roles(
	userEmail text NOT NULL references members(userNameEmail),
	role text NOT NULL,
	PRIMARY KEY(userEmail, role)
);

CREATE TABLE members_roles(userEmail text NOT NULL references members(userNameEmail), role text NOT NULL, PRIMARY KEY(userEmail, role));

INSERT INTO members_roles(userEmail, role) VALUES (?,?)