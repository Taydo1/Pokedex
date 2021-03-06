/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex.database;

import java.util.Locale;
import java.util.Map;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Leon
 */
public class Pokedex extends DBElement {

    public String name, en_name, classification;
    public int id, id_type1, id_type2, id_ability1, id_ability2, id_ability3, id_ability4, is_legendary,
            generation, id_lower_evolution, id_evolution1, id_evolution2;
    public float height, weight, percentage_male;
    public boolean has_shiny, has_mega;

    public Pokedex() {
    }

    //constructeur recevant toutes les variables et devant les stocker directement
    public Pokedex(int id, String name, String en_name, String classification, int id_type1, int id_type2, int id_ability1, int id_ability2, int id_ability3, int id_ability4, int generation, int id_lower_evolution, int id_evolution1, int id_evolution2, float height, float weight, float percentage_male, int is_legendary, boolean has_shiny, boolean has_mega) {
        this.id = id;
        this.name = name;
        this.en_name = en_name;
        this.classification = classification;
        this.id_type1 = id_type1;
        this.id_type2 = id_type2;
        this.id_ability1 = id_ability1;
        this.id_ability2 = id_ability2;
        this.id_ability3 = id_ability3;
        this.id_ability4 = id_ability4;
        this.generation = generation;
        this.id_lower_evolution = id_lower_evolution;
        this.id_evolution1 = id_evolution1;
        this.id_evolution2 = id_evolution2;
        this.height = height;
        this.weight = weight;
        this.percentage_male = percentage_male;
        this.is_legendary = is_legendary;
        this.has_shiny = has_shiny;
        this.has_mega = has_mega;

        //inversion possible pour remplir d'abord les types/evolution 1 avant les 2
        if (this.id_type1 == 0 && this.id_type2 != 0) {
            this.id_type1 = this.id_type2;
            this.id_type2 = 0;
        }
        if (this.id_evolution1 == 0 && this.id_evolution2 != 0) {
            this.id_evolution1 = this.id_evolution2;
            this.id_evolution2 = 0;
        }

        //tri par bulle pour remplir les colonnes dans l'ordre
        boolean good;
        do {
            good = true;
            if (this.id_ability1 == 0 && this.id_ability2 != 0) {
                good = false;
                this.id_ability1 = this.id_ability2;
                this.id_ability2 = 0;
            }
            if (this.id_ability2 == 0 && this.id_ability3 != 0) {
                good = false;
                this.id_ability2 = this.id_ability3;
                this.id_ability3 = 0;
            }
            if (this.id_ability3 == 0 && this.id_ability4 != 0) {
                good = false;
                this.id_ability3 = this.id_ability4;
                this.id_ability4 = 0;
            }
        } while (!good);
    }

    //constructeur recevant une ligne du fichier cvs et qui doit la "parser"
    public Pokedex(String cvsLign, Map<String, Integer> type2id, Map<String, Integer> ability2id) {
        String[] infos = cvsLign.split(";");
        this.id = -1;
        this.name = infos[1];
        this.en_name = infos[2];
        this.classification = infos[3];
        this.id_type1 = type2id.get(infos[4]);
        this.id_type2 = type2id.get(infos[5]);
        this.id_ability1 = ability2id.get(infos[6]);
        this.id_ability2 = ability2id.get(infos[7]);
        this.id_ability3 = ability2id.get(infos[8]);
        this.id_ability4 = ability2id.get(infos[9]);
        this.height = Float.parseFloat(infos[10]);
        this.weight = Float.parseFloat(infos[11]);
        this.percentage_male = StringToFloatParse(infos[12]); //utilisation de StringToFloatParse et StringToIntParse si la valeur peut etre null
        this.generation = Integer.parseInt(infos[13]);
        this.is_legendary = Integer.parseInt(infos[14]);
        this.has_shiny = Boolean.parseBoolean(infos[15]);
        this.has_mega = Boolean.parseBoolean(infos[16]);
        this.id_lower_evolution = StringToIntParse(infos[17]);
        this.id_evolution1 = StringToIntParse(infos[18]);
        this.id_evolution2 = StringToIntParse(infos[19]);

    }

    //constructeur recevant une ligne de la réponse à la requete et qui doit en extraire chaque info
    public Pokedex(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.name = rs.getString("name");
        this.en_name = rs.getString("en_name");
        this.classification = rs.getString("classification");
        this.id_ability1 = rs.getInt("id_ability1");
        this.id_ability2 = rs.getInt("id_ability2");
        this.id_ability3 = rs.getInt("id_ability3");
        this.id_ability4 = rs.getInt("id_ability4");
        this.id_type1 = rs.getInt("id_type1");
        this.id_type2 = rs.getInt("id_type2");
        this.generation = rs.getInt("generation");
        this.id_lower_evolution = rs.getInt("id_lower_evolution");
        this.id_evolution1 = rs.getInt("id_evolution1");
        this.id_evolution2 = rs.getInt("id_evolution2");
        this.height = rs.getFloat("height");
        this.weight = rs.getFloat("weight");
        this.percentage_male = rs.getFloat("percentage_male");
        this.is_legendary = rs.getInt("is_legendary_fabulous");
        this.has_shiny = rs.getBoolean("has_shiny");
        this.has_mega = rs.getBoolean("has_mega");
    }

    @Override
    public String toString() {
        return "Pokedex{" + "name=" + name + ", en_name=" + en_name + ", classification=" + classification + ", id=" + id + ", id_type1=" + id_type1 + ", id_type2=" + id_type2 + ", id_ability1=" + id_ability1 + ", id_ability2=" + id_ability2 + ", id_ability3=" + id_ability3 + ", id_ability4=" + id_ability4 + ", is_legendary=" + is_legendary + ", generation=" + generation + ", id_lower_evolution=" + id_lower_evolution + ", id_evolution1=" + id_evolution1 + ", id_evolution2=" + id_evolution2 + ", height=" + height + ", weight=" + weight + ", percentage_male=" + percentage_male + ", has_shiny=" + has_shiny + ", has_mega=" + has_mega + '}';
    }

    //renvoie la parentèse utilisée dans l'insertion avec toutes la valeurs stockées dans les variables
    @Override
    public String getInsertSubRequest() {
        return String.format(Locale.ROOT, "(default, '%s', '%s', '%s', %d, %s, %s, %s, %s, %s, %f, %f, %s, %d, %d,%s, %s, %s, %b, %b)",
                name.replace("'", "''"), en_name.replace("'", "''"),
                classification.replace("'", "''"),
                id_type1, int2StringRequest(id_type2),
                id_ability1, int2StringRequest(id_ability2), int2StringRequest(id_ability3), int2StringRequest(id_ability4),
                height, weight, float2StringRequest(percentage_male), is_legendary, generation,
                int2StringRequest(id_lower_evolution), int2StringRequest(id_evolution1), int2StringRequest(id_evolution2),
                has_shiny, has_mega);
    }

    //remplace toutes la valeurs de la ligne {id} dans la table pokedex avec les valeurs stockées dans les variables
    @Override
    public void modifyInDB(Database db) {
        db.executeUpdate(String.format(Locale.ROOT, "UPDATE pokedex SET name='%s', en_name='%s', "
                + "classification='%s', id_type1=%d, id_type2=%s,"
                + "id_ability1=%d, id_ability2=%s, id_ability3=%s, id_ability4=%s,"
                + "height=%f, weight=%f, percentage_male=%s, "
                + "is_legendary_fabulous=%d, generation=%d,  id_lower_evolution=%s,"
                + "id_evolution1=%s, id_evolution2=%s, has_shiny=%b, has_mega=%b WHERE id=%d",
                name.replace("'", "''"), en_name.replace("'", "''"),
                classification.replace("'", "''"),
                id_type1, int2StringRequest(id_type2),
                id_ability1, int2StringRequest(id_ability2), int2StringRequest(id_ability3), int2StringRequest(id_ability4),
                height, weight, float2StringRequest(percentage_male), is_legendary, generation,
                int2StringRequest(id_lower_evolution), int2StringRequest(id_evolution1), int2StringRequest(id_evolution2),
                has_shiny, has_mega, id));
    }

    //renvoie le nom du typeNb-ième type
    public String getTypeName(Database db, int typeNb) {
        ArrayList<Object[]> list = db.getFromDB("Select t.name from type t join pokedex p on p.id_type" + typeNb + " = t.id WHERE p.id =" + id);
        String valeurDonne = (String) list.get(0)[0];
        return valeurDonne;
    }

    //renvoie le nom du abilityNb-ième talent
    public String getAbilityName(Database db, int abilityNb) {
        ArrayList<Object[]> list = db.getFromDB("Select a.name from ability a join pokedex p on p.id_ability" + abilityNb + " = a.id WHERE p.id =" + id);
        String valeurDonne = (String) list.get(0)[0];
        return valeurDonne;
    }

    //renvoie le nom de la pré-evolution
    public String getLowerEvolutionName(Database db) {
        ArrayList<Object[]> list = db.getFromDB("Select p2.name from pokedex p1 join pokedex p2 on p1.id_lower_evolution=p2.id WHERE p1.id =" + id);
        String valeurDonne = (String) list.get(0)[0];
        return valeurDonne;
    }

    //renvoie le nom de la evolutionNb-ième evolution
    public String getEvolutionName(Database db, int evolutionNb) {
        ArrayList<Object[]> list = db.getFromDB("Select p2.name from pokedex p1 join pokedex p2 on p1.id_evolution" + evolutionNb + "=p2.id WHERE p1.id =" + id);
        String valeurDonne = list.get(0)[0].toString();
        return valeurDonne;
    }
}
