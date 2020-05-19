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
public class Trainer extends DBElement {

    public String name;
    public int id, id_pokemon[];

    public Trainer(String name, int id_pokemon1, int id_pokemon2, int id_pokemon3, int id_pokemon4, int id_pokemon5, int id_pokemon6) {
        this.id = -1;
        this.name = name;
        id_pokemon = new int[6];
        id_pokemon[0] = id_pokemon1;
        id_pokemon[1] = id_pokemon2;
        id_pokemon[2] = id_pokemon3;
        id_pokemon[3] = id_pokemon4;
        id_pokemon[4] = id_pokemon5;
        id_pokemon[5] = id_pokemon6;
    }

    public Trainer(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.name = rs.getString("name");
        id_pokemon = new int[6];
        id_pokemon[0] = rs.getInt("id_pokemon1");
        id_pokemon[1] = rs.getInt("id_pokemon2");
        id_pokemon[2] = rs.getInt("id_pokemon3");
        id_pokemon[3] = rs.getInt("id_pokemon4");
        id_pokemon[4] = rs.getInt("id_pokemon5");
        id_pokemon[5] = rs.getInt("id_pokemon6");
    }

    @Override
    public String toString() {
        return "Trainer{" + "name=" + name + ", id=" + id + '}';
    }

    @Override
    public String getInsertSubRequest() {
        return String.format(Locale.ROOT, "(default, '%s', %s, %s, %s, %s, %s, %s)",
                name.replace("'", "''"),
                int2StringRequest(id_pokemon[0]), int2StringRequest(id_pokemon[1]),
                int2StringRequest(id_pokemon[2]), int2StringRequest(id_pokemon[3]),
                int2StringRequest(id_pokemon[4]), int2StringRequest(id_pokemon[5]));
    }

    @Override
    public void modifyInDB(Database db) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public ArrayList<Pokemon> getTeam(Database db) {
        ArrayList<Pokemon> team = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            ArrayList<Pokemon> temp = db.getFromDB("SELECT * FROM pokemon p WHERE p.id=" + this.id_pokemon[i], Pokemon.class);
            if (!temp.isEmpty()) {
                team.add(temp.get(0));
            }
        }
        return team;
    }

    public ArrayList<Pokemon> getPokemons(Database db) {
        return db.getFromDB("SELECT * FROM pokemon p WHERE p.id_trainer=" + id, Pokemon.class);
    }
}
