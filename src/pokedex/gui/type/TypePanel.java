/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex.gui.type;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import pokedex.database.*;
import pokedex.gui.Action;
import pokedex.gui.InfoButton;

/**
 *
 * @author Leon
 */
public class TypePanel extends JPanel implements ActionListener{
    TopTypePanel topTypePanel;
    Database db;

    public TypePanel(Database db) {
        this.db = db;
        topTypePanel = new TopTypePanel(db, this);
        
        setLayout(new BorderLayout());
        add(topTypePanel, BorderLayout.CENTER);
    }

    public void setId(int id, Database db) {
        Type pokeActuel = db.getFromDB("SELECT * FROM type WHERE id=" + String.valueOf(id), Type.class).get(0);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        InfoButton button;
        switch(Action.valueOf(e.getActionCommand())){
            case GO:
                button = (InfoButton)e.getSource();
                topTypePanel.setId(button.getId(), db);
        }
    }

}
