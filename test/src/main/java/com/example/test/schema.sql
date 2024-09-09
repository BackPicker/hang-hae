drop table if exists item;

create table item
(
    price    integer not null,
    id       bigint  not null auto_increment,
    content  varchar(255),
    title    varchar(255),
    username varchar(255),
    primary key (id)
)