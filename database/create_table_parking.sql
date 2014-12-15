create database IF NOT EXISTS objectville_parking;

use objectville_parking;

create table IF NOT EXISTS vehicle_types(
	id serial primary key,
	description varchar(32),
	price_by_hour numeric(6,2),
	price_by_day numeric(6,2),
	price_by_month numeric(6,2)
);

create table IF NOT EXISTS parkings (
	id serial primary key,
	vehicle_plate varchar(10),
	vehicle_type_id int references vehicle_types(id),
	start_time bigint not null,
	end_time bigint
);

create table IF NOT EXISTS invoices(
	id serial primary key,
	parking_id int references parkings(id),
	price_by_vehicle_type numeric(6,2),
	price numeric(6,2)
);