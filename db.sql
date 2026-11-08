create table author(
                       id uuid not null primary key,
                       name varchar(100) not null,
                       birth_date date not null,
                       nationality varchar(50) not null
);

create table book(
                     id uuid not null primary key,
                     isbn varchar(20) not null,
                     title varchar(150) not null,
                     price numeric(18,2),
                     publish_date date not null,
                     genre varchar(30) not null,
                     autor_id uuid not null references author(id)
                         constraint chk_genre check(genre in ('FICTION', 'FANTASY', 'MISTERY', 'ROMANCE', 'BIOGRAPHY', 'SCIENCE'))
);