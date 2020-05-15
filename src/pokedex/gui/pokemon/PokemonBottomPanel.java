/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex.gui.pokemon;

import javax.swing.JPanel;
import pokedex.database.Database;
import pokedex.gui.InfoButton;

/**
 *
 * @author Leon
 */
public class PokemonBottomPanel extends JPanel {

    InfoButton add, modification, delete;
    Database db;

    public PokemonBottomPanel(Database db, PokemonPanel parent) {
        super();
        this.db = db;

        modification =new InfoButton("", 0, true);
    }

    public void setUser(String user) {
        switch (user.toLowerCase()) {
            case "professeur":
                modification.setEnabled(true);
                break;
            case "dresseur":
            case "visiteur":
                modification.setEnabled(false);
                break;
        }
    }
    
    public void setId(int id){
        
    }

}
