INSERT INTO myusers (user_name, pass_word, enabled)
values('user', 'user', true);

INSERT INTO myusers(user_name,pass_word,enabled)
values('admin','admin',true);

INSERT INTO myauthorities (user_name, auth_ority)
values ('user','USER');

INSERT INTO myauthorities (user_name, auth_ority)
values ('admin','ADMIN');