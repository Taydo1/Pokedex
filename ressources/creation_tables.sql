CREATE SCHEMA $schemaName$;
SET search_path TO $schemaName$;

CREATE TABLE pokedex(
	id serial PRIMARY KEY,
    name varchar(20),
	name_en varchar(20),
	id_type1 int,
	id_type2 int,
	categorie varchar(20),
	taille numeric,
	poids numeric,
	id_talent int,
	id_evolution int,
	id_sous_evolution int
);


INSERT INTO pokedex(name, id_type1, taille, poids) VALUES ('Léon', 4,3.4,0.93);
INSERT INTO pokedex(name, id_type1, taille, poids) VALUES ('Léon2', 0,3.9,3.3);