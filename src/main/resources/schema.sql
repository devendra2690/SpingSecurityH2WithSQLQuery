create table myusers(
    user_name varchar_ignorecase(50) not null primary key,
    pass_word varchar_ignorecase(50) not null,
    enabled boolean not null
);

create table myauthorities (
    user_name varchar_ignorecase(50) not null,
    auth_ority varchar_ignorecase(50) not null,
    constraint fk_authorities_users foreign key(user_name) references myusers(user_name)
);
create unique index ix_auth_username on myauthorities (user_name,auth_ority);


-- Below is Standard scehema define by SPring for Security purpose
--create table users(
  --  username varchar_ignorecase(50) not null primary key,
    --password varchar_ignorecase(50) not null,
    --enabled boolean not null
--);

--create table authorities (
  --  username varchar_ignorecase(50) not null,
    --authority varchar_ignorecase(50) not null,
   -- constraint fk_authorities_users foreign key(username) references users(username)-
--);
--create unique index ix_auth_username on authorities (username,authority);