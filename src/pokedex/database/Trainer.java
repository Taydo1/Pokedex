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

    //constructeur recevant toutes les variables sauf l'id et devant les stocker directement
    public Trainer(String name, int id_pokemon1, int id_pokemon2, int id_pokemon3, int id_pokemon4, int id_pokemon5, int id_pokemon6) {
        this(-1, name, id_pokemon1, id_pokemon2, id_pokemon3, id_pokemon4, id_pokemon5, id_pokemon6);
    }
    
    //constructeur recevant toutes les variables et devant les stocker directement
    public Trainer(int id, String name, int id_pokemon1, int id_pokemon2, int id_pokemon3, int id_pokemon4, int id_pokemon5, int id_pokemon6) {
        this.id = id;
        this.name = name;
        id_pokemon = new int[6];
        id_pokemon[0] = id_pokemon1;
        id_pokemon[1] = id_pokemon2;
        id_pokemon[2] = id_pokemon3;
        id_pokemon[3] = id_pokemon4;
        id_pokemon[4] = id_pokemon5;
        id_pokemon[5] = id_pokemon6;
    }

    //constructeur recevant une ligne de la réponse à la requete et qui doit en extraire chaque info
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

    //renvoie la parentèse utilisée dans l'insertion avec toutes la valeurs stockées dans les variables
    @Override
    public String getInsertSubRequest() {
        return String.format(Locale.ROOT, "(default, '%s', %s, %s, %s, %s, %s, %s)",
                name.replace("'", "''"),
                int2StringRequest(id_pokemon[0]), int2StringRequest(id_pokemon[1]),
                int2StringRequest(id_pokemon[2]), int2StringRequest(id_pokemon[3]),
                int2StringRequest(id_pokemon[4]), int2StringRequest(id_pokemon[5]));
    }

    //remplace toutes la valeurs de la ligne {id} dans la table pokedex avec les valeurs stockées dans les variables
    @Override
    public void modifyInDB(Database db) {
        String[] colonnesModifiees = new String[]{"name", "id_pokemon1", "id_pokemon2", "id_pokemon3", "id_pokemon4", "id_pokemon5", "id_pokemon6"};
        Object[] pkmn = new Object[6];
        for (int i = 0; i < 6; i++){
            if(id_pokemon[i] == 0){
                pkmn[i] = null;
            } else {
                pkmn[i] = id_pokemon[i];
            }
        }
        Object[] valeursModif = new Object[]{name, pkmn[0], pkmn[1], pkmn[2], pkmn[3], pkmn[4], pkmn[5]};
        db.modify("trainer", id, colonnesModifiees, valeursModif);
    }

    //renvoie tous les pokemon de l'equipe du dresseur
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

    //renvoie tous les pokemon du dresseur
    public ArrayList<Pokemon> getPokemons(Database db) {
        return db.getFromDB("SELECT * FROM pokemon p WHERE p.id_trainer=" + id+" ORDER BY p.id ASC", Pokemon.class);
    }
}
