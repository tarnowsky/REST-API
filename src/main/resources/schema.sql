create table if not exists users (
    id identity not null primary key,
    username varchar(255) unique,
    gender varchar(255),
    age int,
    timestamp timestamp
);