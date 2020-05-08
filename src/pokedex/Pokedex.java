/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex;

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

    String name, en_name, classification;
    int id, id_type1, id_type2, id_ability1, id_ability2, id_ability3, id_ability4,
            generation, id_lower_evolution, id_evolution;
    float height, weight, percentage_male;
    boolean is_legendary;

    public Pokedex() {}
    
    public Pokedex(String name, String en_name, String classification, int id_type1, int id_type2, int id_ability1, int id_ability2, int id_ability3, int id_ability4, int generation, int id_lower_evolution, int id_evolution, float height, float weight, float percentage_male, boolean is_legendary) {
        this.id = -1;
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
        this.id_evolution = id_evolution;
        this.height = height;
        this.weight = weight;
        this.percentage_male = percentage_male;
        this.is_legendary = is_legendary;
    }

    public Pokedex(String cvsLign, Map<String, Integer> type2id, Map<String, Integer> ability2id) {
        String[] infos = cvsLign.split(";");
        //System.out.println(""+cvsLign);
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
        this.percentage_male = StringToFloatParse(infos[12]);
        this.generation = Integer.parseInt(infos[13]);
        this.is_legendary = Boolean.parseBoolean(infos[14]);
        this.id_lower_evolution = StringToIntParse(infos[15]);
        this.id_evolution = StringToIntParse(infos[16]);
    }
    
    public Pokedex(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.name = rs.getString("name");
        this.en_name = rs.getString("en_name");
        this.classification = rs.getString("category");
        this.id_ability1 = rs.getInt("id_ability1");
        this.id_ability2 = rs.getInt("id_ability2");
        this.id_ability3 = rs.getInt("id_ability3");
        this.id_ability4 = rs.getInt("id_ability4");
        this.id_type1 = rs.getInt("id_type1");
        this.id_type2 = rs.getInt("id_type2");
        this.generation = rs.getInt("generation");
        this.id_lower_evolution = rs.getInt("id_lower_evolution");
        this.id_evolution = rs.getInt("id_evolution");
        this.height = rs.getFloat("height");
        this.weight = rs.getFloat("weight");
        this.percentage_male = rs.getFloat("percentage_male");
        this.is_legendary = rs.getBoolean("is_legendary");
    }


    @Override
    public String toString() {
        return "Pokedex{" + "name=" + name + ", en_name=" + en_name + ", classification=" + classification + ", id=" + id + ", id_type1=" + id_type1 + ", id_type2=" + id_type2 + ", id_ability1=" + id_ability1 + ", id_ability2=" + id_ability2 + ", id_ability3=" + id_ability3 + ", id_ability4=" + id_ability4 + ", generation=" + generation + ", id_lower_evolution=" + id_lower_evolution + ", id_evolution=" + id_evolution + ", height=" + height + ", weight=" + weight + ", percentage_male=" + percentage_male + ", is_legendary=" + is_legendary + '}';
    }
    
    @Override
    public String getInsertSubRequest() {
        return String.format(Locale.ROOT, "(default, '%s', '%s', '%s', %d, %s, %s, %s, %s, %s, %f, %f, %s, %b, %d,%s, %s)",
                name.replace("'", "''"), en_name.replace("'", "''"),
                classification.replace("'", "''"),
                id_type1, int2StringRequest(id_type2),
                id_ability1, int2StringRequest(id_ability2), int2StringRequest(id_ability3), int2StringRequest(id_ability4),
                height, weight, float2StringRequest(percentage_male), is_legendary, generation,
                int2StringRequest(id_lower_evolution), int2StringRequest(id_evolution));
    }
    
    public String getType1(int id, Database db){
        ArrayList<Object[]> list = db.getFromDB("Select * from type t join pokedex p on p.id_type1 = t.id WHERE p.id =" + id);
        String valeurDonne = list.get(0)[1].toString();
        return valeurDonne;
    }
    public String getType2(int id, Database db){
        ArrayList<Object[]> list = db.getFromDB("Select * from type t join pokedex p on p.id_type2 = t.id WHERE p.id =" + id);
        String valeurDonne = list.get(0)[1].toString();
        return valeurDonne;
    }
}
