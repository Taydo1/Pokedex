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
import pokedex.gui.Action;
import pokedex.gui.widgets.InfoButton;
import pokedex.gui.pokedex.StyledButton;

/**
 *
 * @author Leon
 */
public class PokemonBottomPanel extends JPanel {

    InfoButton modification, delete;
    StyledButton add;
    Database db;

    public PokemonBottomPanel(Database db, PokemonPanel parent) {
        super();
        this.db = db;

        add = new StyledButton("Ajouter un Pokemon", true);
        modification = new InfoButton("", 0, true);
        delete = new InfoButton("", 0, true);

        setLayout(new GridLayout(3, 1));
        add(add);
        add(modification);
        add(delete);
        
        add.addActionListener(parent);
        modification.addActionListener(parent);
        delete.addActionListener(parent);
        add.setActionCommand(Action.START_INSERTION.name());
        modification.setActionCommand(Action.START_MODIFICATION.name());
        delete.setActionCommand(Action.DELETE.name());
    }

    public void setUser(String user) {
        switch (user.toLowerCase()) {
            case "professeur":
                add.setEnabled(true);
                modification.setEnabled(true);
                delete.setEnabled(true);
                break;
            case "dresseur":
                add.setEnabled(true);
                modification.setEnabled(false);
                delete.setEnabled(true);
                break;
            case "visiteur":
                add.setEnabled(false);
                modification.setEnabled(false);
                delete.setEnabled(false);
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
            modification.setVisible(true);
            delete.setVisible(true);
        }else{
            modification.setVisible(false);
            delete.setVisible(false);
        }
    }
}
