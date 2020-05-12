/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;

/**
 *
 * @author Leon
 */
public class Ability extends DBElement {

    static int idCounter = 1;
    public int id;
    public String name, en_name, description[];

    public Ability() {
    }
    
    public Ability(int id, String name,String en_name, String description1, String description2) {
        this.id = id;
        this.name = name;
        this.en_name = en_name;
        this.description = new String[]{description1, description2};
    }
    
    public Ability(String name, String description1, String description2) {
        this.id = -1;
        this.name = name;
        this.description = new String[]{description1, description2};
    }

    public Ability(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.name = rs.getString("name");
        this.en_name = rs.getString("en_name");
        this.description = new String[]{rs.getString("description1"),rs.getString("description2")};
    }

    public Ability(String cvsLign, Map<String, Integer> abilityid) {
        String[] infos = cvsLign.split(";");
        this.id = idCounter++;
        this.name = infos[0];
        this.en_name = infos[1];
        this.description = new String[]{infos[2], infos[3]};
        abilityid.put(en_name, id);
    }

    @Override
    public String toString() {
        return "Ability{" + "id=" + id + ", name=" + name + ", en_name=" + en_name + ", description=" + Arrays.toString(description) + '}';
    }

    @Override
    public String getInsertSubRequest() {
        return String.format("(default, '%s', '%s', '%s', '%s')",
                name.replace("'", "''"), en_name.replace("'", "''"), description[0].replace("'", "''"), description[1].replace("'", "''"));
    }

    @Override
    public void modifyInDB(Database db) {
        String[] colonnes = new String[]{"name", "en_name", "description1", "description2"};
        Object[] valeurs = new Object[]{this.name, this.en_name, this.description[0], this.description[1]};
        db.modify("ability", this.id, colonnes, valeurs);
    }
}
