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
import java.util.Locale;

/**
 *
 * @author Leon
 */
public class Type extends DBElement {

    static int idCounter = 1;
    public String name, en_name;
    public int id;
    public float[] vs;

    //constructeur recevant toutes les variables et devant les stocker directement
    public Type(int id, String name, String en_name,
            float vs_bug, float vs_dark,
            float vs_dragon, float vs_electric,
            float vs_fairy, float vs_fight,
            float vs_fire, float vs_flying,
            float vs_ghost, float vs_grass,
            float vs_ground, float vs_ice,
            float vs_normal, float vs_poison,
            float vs_psychic, float vs_rock,
            float vs_steel, float vs_water) {
        this.name = name;
        this.en_name = en_name;
        this.id = id;
        this.vs = new float[18];

        vs[0] = vs_bug;
        vs[1] = vs_dark;
        vs[2] = vs_dragon;
        vs[3] = vs_electric;
        vs[4] = vs_fairy;
        vs[5] = vs_fight;
        vs[6] = vs_fire;
        vs[7] = vs_flying;
        vs[8] = vs_ghost;
        vs[9] = vs_grass;
        vs[10] = vs_ground;
        vs[11] = vs_ice;
        vs[12] = vs_normal;
        vs[13] = vs_poison;
        vs[14] = vs_psychic;
        vs[15] = vs_rock;
        vs[16] = vs_steel;
        vs[17] = vs_water;
    }

    //constructeur recevant toutes les variables et devant les stocker directement
    public Type(int id, String name, String en_name, float[] vs) {
        this.id = id;
        this.name = name;
        this.en_name = en_name;
        this.vs = vs;
    }

    //constructeur recevant une ligne de la réponse à la requete et qui doit en extraire chaque info
    public Type(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.name = rs.getString("name");
        this.en_name = rs.getString("en_name");
        this.vs = new float[18];
        for (int i = 0; i < 18; i++) {
            vs[i] = rs.getFloat(i + 4);
        }
    }

    //constructeur recevant une ligne du fichier cvs et qui doit la "parser"
    public Type(String cvsLign, Map<String, Integer> type2id) {
        String[] infos = cvsLign.split(";");
        this.id = idCounter++;
        this.name = infos[0];
        this.en_name = infos[1];
        this.vs = new float[18];
        for (int i = 2; i < infos.length; i++) {
            vs[i - 2] = Float.parseFloat(infos[i]);
        }
        type2id.put(en_name, id);
    }

    @Override
    public String toString() {
        return "Type{" + "name=" + name + ", en_name=" + en_name + ", id=" + id + ", vs=" + Arrays.toString(vs) + '}';
    }

    //renvoie la parentèse utilisée dans l'insertion avec toutes la valeurs stockées dans les variables
    @Override
    public String getInsertSubRequest() {
        return String.format(Locale.ROOT, "(default, '%s', '%s', %.1f, %.1f, %.1f, %.1f, %.1f, %.1f, %.1f, %.1f, %.1f, %.1f, %.1f, %.1f, %.1f, %.1f, %.1f, %.1f, %.1f, %.1f)",
                name.replace("'", "''"), en_name.replace("'", "''"), vs[0], vs[1], vs[2], vs[3], vs[4], vs[5], vs[6], vs[7], vs[8], vs[9], vs[10], vs[11], vs[12], vs[13], vs[14], vs[15], vs[16], vs[17]);
    }

    //remplace toutes la valeurs de la ligne {id} dans la table type avec les valeurs stockées dans les variables
    @Override
    public void modifyInDB(Database db) {
        db.executeUpdate(String.format(Locale.ROOT, "UPDATE type SET name='%s', en_name='%s', "
                + "vs_bug=%f, vs_dark=%f, vs_dragon=%f, vs_electric=%f, vs_fairy=%f,"
                + "vs_fight=%f, vs_fire=%f, vs_flying=%f, vs_ghost=%f, vs_grass=%f,"
                + "vs_ground=%f, vs_ice=%f, vs_normal=%f, vs_poison=%f, vs_psychic=%f,"
                + "vs_rock=%f, vs_steel=%f, vs_water=%f WHERE id=%d",
                name.replace("'", "''"), en_name.replace("'", "''"),
                vs[0], vs[1], vs[2], vs[3], vs[4],
                vs[5], vs[6], vs[7], vs[8], vs[9],
                vs[10], vs[11], vs[12], vs[13], vs[14],
                vs[15], vs[16], vs[17], id
        ));
    }

}
