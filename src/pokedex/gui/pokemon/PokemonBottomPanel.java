/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex.gui.pokemon;

import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JPanel;
import pokedex.database.Database;
import pokedex.database.Pokedex;
import pokedex.database.Pokemon;
import pokedex.gui.Action;
import pokedex.gui.widgets.InfoButton;
import pokedex.gui.pokedex.StyledButton;

/**
 *
 * @author Leon
 */
public class PokemonBottomPanel extends JPanel {

    InfoButton modification, delete, evolution, lvlUp;
    StyledButton add;
    Database db;

    public PokemonBottomPanel(Database db, PokemonPanel parent) {
        super();
        this.db = db;

        add = new StyledButton("Ajouter un Pokemon", true);
        modification = new InfoButton("", 0, true);
        delete = new InfoButton("", 0, true);
        evolution = new InfoButton("", 0, true);
        lvlUp = new InfoButton("", 0, true);

        setLayout(new GridLayout(5, 1));
        add(add);
        add(modification);
        add(delete);
        add(lvlUp);
        add(evolution);
        
        add.addActionListener(parent);
        modification.addActionListener(parent);
        delete.addActionListener(parent);
        evolution.addActionListener(parent);
        lvlUp.addActionListener(parent);
        add.setActionCommand(Action.START_INSERTION.name());
        modification.setActionCommand(Action.START_MODIFICATION.name());
        delete.setActionCommand(Action.DELETE.name());
        evolution.setActionCommand(Action.EVOLUTION.name());
        lvlUp.setActionCommand(Action.LVLUP.name());
    }

    public void setUser(String user) {
        switch (user.toLowerCase()) {
            case "professeur":
                add.setEnabled(true);
                modification.setEnabled(true);
                delete.setEnabled(true);
                evolution.setEnabled(true);
                lvlUp.setEnabled(true);
                break;
            case "dresseur":
                add.setEnabled(true);
                modification.setEnabled(false);
                delete.setEnabled(true);
                evolution.setEnabled(true);
                lvlUp.setEnabled(true);
                break;
            case "visiteur":
                add.setEnabled(false);
                modification.setEnabled(false);
                delete.setEnabled(false);
                evolution.setEnabled(false);
                lvlUp.setEnabled(false);
                break;
        }
    }

    public void setId(int id) {
        ArrayList<Object[]> currentNameList = db.getFromDB("SELECT name FROM pokemon WHERE id=" + id);
        if (!currentNameList.isEmpty()) {
            modification.setText("Modifier le Pokemon " + currentNameList.get(0)[0]);
            modification.setId(id);
            delete.setText("Supprimer le Pokemon " + currentNameList.get(0)[0]);
            delete.setId(id);
            evolution.setText("" + currentNameList.get(0)[0] + " évolue ???");
            evolution.setId(id);
            lvlUp.setText("Montée de niveau de " + currentNameList.get(0)[0]);
            lvlUp.setId(id);
            modification.setVisible(true);
            delete.setVisible(true);
            Pokemon currentPokemon = db.getFromDB("SELECT * from pokemon WHERE id = " + id, Pokemon.class).get(0);
            if ((int) db.getFromDB("SELECT id_evolution1 from pokedex WHERE id = " + currentPokemon.id_pokedex).get(0)[0] != 0){
                evolution.setVisible(true);
            } else {
                evolution.setVisible(false);
            }
            lvlUp.setVisible(true);
        }else{
            modification.setVisible(false);
            delete.setVisible(false);
            evolution.setVisible(false);
            lvlUp.setVisible(false);
        }
    }
}
