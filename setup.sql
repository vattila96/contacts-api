create table company
(
    id   bigint generated by default as identity,
    name varchar(200) not null,
    constraint pk_company primary key (id)
);

insert into company (name)
values ('Company #1'),
       ('Company #2'),
       ('Company #3');
