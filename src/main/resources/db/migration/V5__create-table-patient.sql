create table patient(

    id bigint not null auto_increment,
    name varchar(100) not null,
    email varchar(100) not null unique,
    phone varchar(20) not NULL,
    cpf varchar(14) not null unique,
    street varchar(100) not null,
    neighborhood varchar(100) not null,
    postal_code varchar(9) not null,
    city varchar(100) not null,
    state char(2) not null,
    complement varchar(100),
    number varchar(20),
    active tinyint not null,
    primary key(id)

);