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
public class Trainer extends DBElement{
    
    public String name;
    public int id;

    public Trainer(String name) {
        this.id = -1;
        this.name = name;
    }


    public Trainer(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.name = rs.getString("name");
    }

    @Override
    public String toString() {
        return "Trainer{" + "name=" + name + ", id=" + id + '}';
    }


    @Override
    public String getInsertSubRequest() {
        return String.format(Locale.ROOT, "(default, '%s')",
                name.replace("'", "''"));
    }

    @Override
    public void modifyInDB(Database db) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public ArrayList<Pokemon> getPokemons(Database db){
        return db.getFromDB("SELECT * FROM pokemon p WHERE p.id_trainer="+id, Pokemon.class);
    }
}