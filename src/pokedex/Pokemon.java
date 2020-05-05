/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

/**
 *
 * @author Leon
 */
public class Pokemon extends DBElement {

    public String name;
    int id, level, health;
    int id_trainer, id_move0, id_move1, id_move2, id_move3, id_pokedex;

    public Pokemon(String name, int level, int health, int id_trainer, int id_move0, int id_move1, int id_move2, int id_move3, int id_pokedex) {
        this.id = -1;
        this.name = name;
        this.level = level;
        this.health = health;
        this.id_trainer = id_trainer;
        this.id_move0 = id_move0;
        this.id_move1 = id_move1;
        this.id_move2 = id_move2;
        this.id_move3 = id_move3;
        this.id_pokedex = id_pokedex;
    }

    public Pokemon(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.name = rs.getString("name");
        this.level = rs.getInt("level");
        this.health = rs.getInt("health");
        this.id_trainer = rs.getInt("id_trainer");
        this.id_move0 = rs.getInt("id_move0");
        this.id_move1 = rs.getInt("id_move1");
        this.id_move2 = rs.getInt("id_move2");
        this.id_move3 = rs.getInt("id_move3");
        this.id_pokedex = rs.getInt("id_pokedex");;
    }

    @Override
    public String toString() {
        return "Pokemon{" + "name=" + name + ", id=" + id + ", level=" + level + ", health=" + health + ", id_trainer=" + id_trainer + ", id_move0=" + id_move0 + ", id_move1=" + id_move1 + ", id_move2=" + id_move2 + ", id_move3=" + id_move3 + ", id_pokedex=" + id_pokedex + '}';
    }

    @Override
    public String getInsertSubRequest() {
        return String.format(Locale.ROOT, "(default, '%s', %d, %d, %s, %d, %s, %s, %s, %d)",
                name.replace("'", "''"), level, health, int2StringRequest(id_trainer), id_move0, int2StringRequest(id_move1), int2StringRequest(id_move2), int2StringRequest(id_move3), id_pokedex);
    }
}
