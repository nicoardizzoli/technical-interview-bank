

CREATE SCHEMA technical-interview-bank;


 CREATE TABLE account (
       account_id int8 not null,
        account_number int4 not null,
        account_type varchar(255) not null,
        balance numeric(19, 2) not null,
        state boolean not null,
        withdraw_limit numeric(19, 2) not null,
        customer_id varchar(255) not null,
        primary key (account_id)
    );


    create table customer (
       customer_id varchar(255) not null,
        password varchar(255),
        state boolean,
        person_id int8 not null,
        primary key (person_id)
    );


    create table movement (
       movement_id int8 not null,
        amount numeric(19, 2) not null,
        date timestamp not null,
        initial_account_balance numeric(19, 2) not null,
        movement_type varchar(255),
        account_id int8 not null,
        primary key (movement_id)
    );


    create table person (
       person_id int8 not null,
        address varchar(255) not null,
        age int4 not null,
        gender varchar(255) not null,
        identification varchar(255) not null,
        name varchar(255) not null,
        phone_number varchar(255) not null,
        surname varchar(255) not null,
        primary key (person_id)
    );


    alter table account
       add constraint account_number_unique unique (account_number);



    alter table customer add constraint customer_id_unique unique (customer_id);


    alter table person
       add constraint person_identification_unique unique (identification);


    alter table account
       add constraint customer_id_account_id_fk
       foreign key (customer_id)
       references customer (customer_id);


    alter table customer
       add constraint person_id_customer_id
       foreign key (person_id)
       references person;


    alter table movement
       add constraint movement_id_account_id_fk
       foreign key (account_id)
       references account;