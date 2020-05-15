/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex.gui.pokemon;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JPanel;
import pokedex.database.Database;
import pokedex.gui.Action;
import pokedex.gui.ImagePanel;
import pokedex.gui.InfoButton;
import pokedex.gui.MainPanel;

/**
 *
 * @author Leon
 */
public class PokemonPanel extends JPanel implements ActionListener {

    PokemonTopPanel topPanel;
    PokemonBottomPanel bottomPanel;
    ImagePanel imagePanel;
    MainPanel parent;
    Database db;

    public PokemonPanel(Database db, MainPanel parent) {
        this.parent = parent;
        this.db = db;
        setLayout(new BorderLayout());
        topPanel = new PokemonTopPanel(db, this);
        imagePanel = new ImagePanel();
        bottomPanel = new PokemonBottomPanel(db, this);
        add(topPanel, BorderLayout.NORTH);
        add(imagePanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        
        setId(-1);
    }

    public void setUser(String user) {
        bottomPanel.setUser(user);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        InfoButton source;
        switch (Action.valueOf(e.getActionCommand())) {
            case GET_POKEMON:
                source = (InfoButton) topPanel.selector.getSelectedItem();
                setId(source.getId());
                break;
            case START_POKEMON_MODIFICATION:
                parent.addTab(
                        new PokemonModifInsertPanel(bottomPanel.modification.getId(), parent),
                        "Modification de " + db.getFromDB("SELECT name FROM pokemon WHERE id=" + bottomPanel.modification.getId()).get(0)[0],
                        MainPanel.PROFESSOR_TAB
                );
                break;
            case START_POKEMON_INSERTION:
                parent.addTab(
                        new PokemonModifInsertPanel(parent),
                        "Naissance d'un Pokemon", 
                        MainPanel.PROFESSOR_TAB
                );
                break;
            case DELETE_POKEMON:
                System.err.println("PAS ENCORE IMPLEMENTE");
                break;
        }
    }

    public void setId(int id) {
        topPanel.setId(id);

        ArrayList<Object[]> idList = db.getFromDB("SELECT id_pokedex FROM pokemon p WHERE p.id=" + id);
        if (!idList.isEmpty()) {
            Image image = db.getImage("SELECT image FROM pokedex WHERE id=" + idList.get(0)[0]);
            imagePanel.setImage(image);
        }else{
            imagePanel.setImage(null);
        }
        bottomPanel.setId(id);
    }

}
