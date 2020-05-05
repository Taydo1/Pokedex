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
public class Pokemon extends DBElement {
    public String name;
    int id, level, health;
    int id_trainer, id_attack0, id_attack1, id_attack2, id_attack3, id_pokedex;

    public Pokemon(String name, int level, int health, int id_trainer, int id_attack0, int id_attack1, int id_attack2, int id_attack3, int id_pokedex) {
        this.id = -1;
        this.name = name;
        this.level = level;
        this.health = health;
        this.id_trainer = id_trainer;
        this.id_attack0 = id_attack0;
        this.id_attack1 = id_attack1;
        this.id_attack2 = id_attack2;
        this.id_attack3 = id_attack3;
        this.id_pokedex = id_pokedex;
    }
    
    public void addToDB(Database db){
        db.executeUpdate(String.format("INSERT INTO pokemon VALUES (default, '%s', %d, %d, %d, %d,  %d, %d, %d, %d)",
                name.replace("'", "''"), level, health, id_trainer, id_attack0, id_attack1, id_attack2, id_attack3, id_pokedex));
    }

    @Override
    public String getInsertSubRequest() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
