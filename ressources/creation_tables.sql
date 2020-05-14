CREATE SCHEMA pokedex;
SET search_path TO pokedex;

CREATE TABLE type(
	id serial PRIMARY KEY,
	name varchar(10),
	en_name varchar(10),
	vs_bug numeric,
	vs_dark numeric,
	vs_dragon numeric,
	vs_electric numeric,
	vs_fairy numeric,
	vs_fight numeric,
	vs_fire numeric,
	vs_flying numeric,
	vs_ghost numeric,
	vs_grass numeric,
	vs_ground numeric,
	vs_ice numeric,
	vs_normal numeric,
	vs_poison numeric,
	vs_psychic numeric,
	vs_rock numeric,
	vs_steel numeric,
	vs_water numeric
);

CREATE TABLE move(
	id serial PRIMARY KEY,
	name varchar(30),
	en_name varchar(30),
	id_type int,
	category varchar(10),
	pp int,
	power int,
	accuracy int
);

CREATE TABLE ability(
	id serial PRIMARY KEY,
	name varchar(20),
	en_name varchar(20),
	description1 text,
	description2 text
);

CREATE TABLE pokedex(
	id serial PRIMARY KEY,
    name varchar(20),
	en_name varchar(20),
	classification varchar(30),
	id_type1 int REFERENCES type(id),
	id_type2 int REFERENCES type(id),
	id_ability1 int REFERENCES ability(id),
	id_ability2 int REFERENCES ability(id),
	id_ability3 int REFERENCES ability(id),
	id_ability4 int REFERENCES ability(id),
	height numeric,
	weight numeric,
	percentage_male numeric,
	is_legendary_fabulous int,
	generation int,
	id_lower_evolution int REFERENCES pokedex(id),
	id_evolution1 int REFERENCES pokedex(id),
	id_evolution2 int REFERENCES pokedex(id),
	has_shiny BOOLEAN,
	has_mega BOOLEAN,
	image bytea,
	image_shiny bytea,
	image_mega bytea
);

CREATE TABlE trainer(
	id serial PRIMARY KEY,
	name varchar(20)
);

CREATE TABLE pokemon(
	id serial PRIMARY KEY,
	name varchar(20),
	level int,
	health int,
	id_trainer int REFERENCES trainer(id),
	id_move1 int REFERENCES move(id),
	id_move2 int REFERENCES move(id),
	id_move3 int REFERENCES move(id),
	id_move4 int REFERENCES move(id),
	id_pokedex int REFERENCES pokedex(id),
	id_ability int REFERENCES ability(id)
);
