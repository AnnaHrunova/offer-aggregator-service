create table if not exists application
(
    id uuid primary key not null
);

create table if not exists offer
(
    id uuid primary key not null,
    application_id uuid not null

    constraint offer_application_id
        references application
);