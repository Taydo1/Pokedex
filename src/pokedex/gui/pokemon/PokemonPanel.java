/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex.gui.pokemon;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import pokedex.database.Database;
import pokedex.gui.Action;
import pokedex.gui.InfoButton;
import pokedex.gui.MainPanel;

/**
 *
 * @author Leon
 */
public class PokemonPanel extends JPanel implements ActionListener {

    PokemonTopPanel topPanel;
    MainPanel parent;

    public PokemonPanel(Database db, MainPanel parent) {
        this.parent = parent;
        setLayout(new BorderLayout());
        topPanel = new PokemonTopPanel(db, this);
        add(topPanel, BorderLayout.NORTH);
    }

    public void setUser(String user) {
        topPanel.setUser(user);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        InfoButton source;
        switch (Action.valueOf(e.getActionCommand())) {
            case GET_POKEMON:
                source = (InfoButton) topPanel.selector.getSelectedItem();
                topPanel.setId(source.getId());
        }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
