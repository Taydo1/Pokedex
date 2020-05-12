/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;

/**
 *
 * @author Leon
 */
public class Pokemon extends DBElement {

    public String name;
    public int id, level, health;
    public int id_trainer, id_move1, id_move2, id_move3, id_move4, id_pokedex, id_ability;

    public Pokemon(String name, int level, int health, int id_trainer, int id_move1, int id_move2, int id_move3, int id_move4, int id_ability, int id_pokedex) {
        this.id = -1;
        this.name = name;
        this.level = level;
        this.health = health;
        this.id_trainer = id_trainer;
        this.id_move1 = id_move1;
        this.id_move2 = id_move2;
        this.id_move3 = id_move3;
        this.id_move4 = id_move4;
        this.id_ability = id_ability;
        this.id_pokedex = id_pokedex;
    }

    public Pokemon(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.name = rs.getString("name");
        this.level = rs.getInt("level");
        this.health = rs.getInt("health");
        this.id_trainer = rs.getInt("id_trainer");
        this.id_move1 = rs.getInt("id_move1");
        this.id_move2 = rs.getInt("id_move2");
        this.id_move3 = rs.getInt("id_move3");
        this.id_move4 = rs.getInt("id_move4");
        this.id_ability = rs.getInt("id_ability");
        this.id_pokedex = rs.getInt("id_pokedex");
    }

    @Override
    public String toString() {
        return "Pokemon{" + "name=" + name + ", id=" + id + ", level=" + level + ", health=" + health + ", id_trainer=" + id_trainer + ", id_move1=" + id_move1 + ", id_move2=" + id_move2 + ", id_move3=" + id_move3 + ", id_move4=" + id_move4 + ", id_pokedex=" + id_pokedex + ", id_ability=" + id_ability + '}';
    }

    @Override
    public String getInsertSubRequest() {
        return String.format(Locale.ROOT, "(default, '%s', %d, %d, %s, %d, %s, %s, %s, %d, %d)",
                name.replace("'", "''"), level, health, int2StringRequest(id_trainer), id_move1, int2StringRequest(id_move2), int2StringRequest(id_move3), int2StringRequest(id_move4), id_pokedex, id_ability);
    }

    @Override
    public void modifyInDB(Database db) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getPokedexName(Database db) {
        ArrayList<Object[]> list = db.getFromDB("Select p.name from pokedex p join pokemon pm ON pm.id_pokedex=p.id WHERE pm.id =" + id);
        String valeurDonne = (String) list.get(0)[0];
        return valeurDonne;
    }

    public String getTypeName(Database db, int typeNb) {
        ArrayList<Object[]> list = db.getFromDB("Select t.name from type t join pokedex p on p.id_type" + typeNb + " = t.id join pokemon pm on pm.id_pokedex=p.id WHERE pm.id =" + id);
        if (list.isEmpty()) {
            return "";
        }
        String valeurDonne = (String) list.get(0)[0];
        return valeurDonne;
    }

    public int getTypeId(Database db, int typeNb) {
        ArrayList<Object[]> list = db.getFromDB("Select p.id_type" + typeNb + " from pokedex p join pokemon pm ON pm.id_pokedex=p.id WHERE pm.id =" + id);
        return (int) list.get(0)[0];
    }

    public String getAbilityName(Database db) {
        ArrayList<Object[]> list = db.getFromDB("Select name from ability WHERE id =" + id_ability);
        String valeurDonne = (String) list.get(0)[0];
        return valeurDonne;
    }

    public String getTrainerName(Database db) {
        ArrayList<Object[]> list = db.getFromDB("Select t.name from trainer t join pokemon p on p.id_trainer=t.id WHERE p.id =" + id);
        if (list.isEmpty()) {
            return "";
        }
        String valeurDonne = (String) list.get(0)[0];
        return valeurDonne;
    }

    public String getMoveName(Database db, int moveNb) {
        ArrayList<Object[]> list = db.getFromDB("Select m.name from move m join pokemon p on p.id_move"+moveNb+"=m.id WHERE p.id =" + id);
        if (list.isEmpty()) {
            return "";
        }
        String valeurDonne = (String) list.get(0)[0];
        return valeurDonne;
    }
}
