/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex;

import java.util.Map;
import java.util.Locale;

/**
 *
 * @author Leon
 */
public class Type {
    String name, en_name;
    int id;
    float[] vs;

    public Type(String name, String en_name,
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
        this.id = -1;
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
    
    public Type(String cvsLign, Map<String, Integer> type2id){
        String[] infos = cvsLign.split(";");
        this.name = infos[0];
        this.en_name = infos[1];
        this.vs = new float[18];
        for (int i = 2; i < infos.length; i++) {
            vs[i-2] = Float.parseFloat(infos[i]);
        }
        type2id.put(en_name, id);
    }
    
    public String getRequest(){
        return String.format(Locale.ROOT, "(default, '%s', '%s', %.1f, %.1f, %.1f, %.1f, %.1f, %.1f, %.1f, %.1f, %.1f, %.1f, %.1f, %.1f, %.1f, %.1f, %.1f, %.1f, %.1f, %.1f)",
                name.replace("'", "''"), en_name.replace("'", "''"), vs[0], vs[1], vs[2], vs[3], vs[4], vs[5], vs[6], vs[7], vs[8], vs[9], vs[10], vs[11], vs[12], vs[13], vs[14], vs[15], vs[16], vs[17]);
    }
}
