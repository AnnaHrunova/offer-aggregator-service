create table if not exists application
(
    id uuid primary key not null,
    version bigint not null,
    created_at timestamp not null,
    updated_at timestamp not null,
    phone varchar(16) not null,
    email varchar(100) not null,
    monthly_income numeric(7, 2)not null,
    monthly_expenses numeric(7, 2)not null,
    monthly_credit_liabilities numeric(7, 2) not null,
    dependents int not null,
    is_checked_consent boolean not null,
    amount numeric(7, 2) not null,
    marital_status varchar(20) not null
);

create table if not exists offer
(
    id uuid primary key not null,
    version bigint not null,
    created_at timestamp not null,
    updated_at timestamp not null,
    financing_institution varchar(20) not null,
    monthly_payment_amount numeric(7, 2) not null,
    total_repayment_amount numeric(7, 2) not null,
    number_of_payments numeric(7, 2) not null,
    annual_percentage_rate numeric(7, 2) not null,
    first_repayment_date timestamp not null,

    application_id uuid not null
    constraint offer_application_id
        references application
);