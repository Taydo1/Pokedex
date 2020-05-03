/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex;

import java.util.Map;

/**
 *
 * @author Leon
 */
public class Move {

    String name, en_name, category;
    int id, id_type, pp, power, accuracy;

    public Move(String name, String en_name, int id_type, String category, int pp, int power, int accuracy) {
        this.id = -1;
        this.name = name;
        this.en_name = en_name;
        this.id_type = id_type;
        this.category = category;
        this.pp = pp;
        this.power = power;
        this.accuracy = accuracy;
    }

    public Move(String cvsLign, Map<String, Integer> type2id) {
        String[] infos = cvsLign.split(";");
        System.out.println(""+cvsLign);
        this.id = -1;
        this.name = infos[0];
        this.en_name = infos[1];
        this.id_type = type2id.get(infos[2]);
        this.category = infos[3];
        this.pp = Integer.parseInt(infos[4]);
        this.power = Integer.parseInt(infos[5]);
        this.accuracy = Integer.parseInt(infos[6]);
    }
    
    public String  getRequest(){
        return String.format("(default, '%s', '%s', %d, '%s', %d, %d, %d)",
                name.replace("'", "''"), en_name.replace("'", "''"), id_type, category.replace("'", "''"), pp, power, accuracy);
    }
}
