CREATE SCHEMA $schemaName$;
SET search_path TO $schemaName$;

CREATE TABLE type(
	id serial PRIMARY KEY,
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

CREATE TABlE trainer(
	id serial PRIMARY KEY,
	name varchar(20)
);

CREATE TABLE attack(
	id serial PRIMARY KEY,
	name varchar(20)
);

CREATE TABLE ability(
	id serial PRIMARY KEY,
	name varchar(20)
);

CREATE TABLE pokedex(
	id serial PRIMARY KEY,
    name varchar(20),
	name_en varchar(20),
	id_type1 int REFERENCES type(id),
	id_type2 int REFERENCES type(id),
	category varchar(20),
	height numeric,
	weight numeric,
	id_lower_evolution int REFERENCES pokedex(id),
	id_evolution int REFERENCES pokedex(id)
);

CREATE TABLE pokemon(
	id serial PRIMARY KEY,
	name varchar(20),
	level int,
	health int,
	id_trainer int,
	id_attack0 int REFERENCES attack(id),
	id_attack1 int REFERENCES attack(id),
	id_attack2 int REFERENCES attack(id),
	id_attack3 int REFERENCES attack(id),
	id_pokedex int REFERENCES pokedex(id)
);

INSERT INTO type VALUES (default, 1, 1, 1, 0.5, 1, 1, 1, 0.5, 1, 1, 2, 1, 1, 1, 1, 1, 0.5, 1);
INSERT INTO pokedex VALUES (172, 'Pichu', 'Pichu', 1, NULL, 'Pokémon Minisouris',  0.2, 2, NULL, 25),
						   (25, 'Pikachu', 'Pikachu', 1, NULL, 'Pokémon Souris',  0.4, 6, 172, 26),
						   (26, 'Raichu', 'Raichu', 1, NULL, 'Pokémon Souris',  0.8, 30, 25, NULL);