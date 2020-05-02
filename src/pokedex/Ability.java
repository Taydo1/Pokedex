/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex;

/**
 *
 * @author Leon
 */
public class Ability {

    int id;
    String name, en_name, description[];

    public Ability(String name, String description1, String description2) {
        this.id = -1;
        this.name = name;
        this.description = new String[]{description1, description2};
    }

    public Ability(String cvsLign) {
        String[] infos = cvsLign.split(";");
        this.name = infos[0];
        this.en_name = infos[1];
        this.description = new String[]{infos[2], infos[3]};
    }
    
    public String  getRequest(){
        return String.format("(default, '%s', '%s', '%s', '%s')",
                name.replace("'", "''"), en_name.replace("'", "''"), description[0].replace("'", "''"), description[1].replace("'", "''"));
    }
}
