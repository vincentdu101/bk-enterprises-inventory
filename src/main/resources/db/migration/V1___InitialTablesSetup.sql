create table inventory (
    uuid varchar(255) not null,
    product_uuid varchar(255) not null,
    vendor_uuid varchar(255) not null,
    quantity int not null,
    total_cost decimal(10, 2) not null,
    delivered_on timestamp not null,
    modified_on timestamp not null,
    primary key(uuid)
);