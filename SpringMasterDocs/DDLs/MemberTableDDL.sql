CREATE TABLE members(
	userId serial PRIMARY KEY,
	userHandle text NOT NULL UNIQUE,
	userNameEmail text NOT NULL UNIQUE,
	salt text NOT NULL,
	pswrd text NOT NULL,
	createTime timestamp WITH TIME ZONE DEFAULT current_timestamp,
	accActive BOOLEAN NOT NULL DEFAULT FALSE,
	accNonExpired BOOLEAN NOT NULL DEFAULT FALSE,
	accNonLocked BOOLEAN NOT NULL DEFAULT FALSE,
	credNonExpired BOOLEAN NOT NULL DEFAULT FALSE
);


ALTER TABLE members DROP CONSTRAINT "members_username_key";
ALTER TABLE members ADD UNIQUE (email);
ALTER TABLE members ALTER COLUMN salt TYPE text;
ALTER TABLE members ALTER COLUMN userName TYPE text;
ALTER TABLE members ALTER COLUMN email TYPE text;
ALTER TABLE members ADD COLUMN "active" BOOLEAN NOT NULL DEFAULT FALSE;

select * from information_schema.table_constraints;
select constraint_name from information_schema.table_constraints where table_name='members';

INSERT INTO members(userHandle, userNameEmail, salt, pswrd, accActive, accNonExpired, accNonLocked, credNonExpired) VALUES (?,?,?,?,?,?,?,?)