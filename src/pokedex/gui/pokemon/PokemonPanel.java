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
import pokedex.gui.widgets.InfoButton;
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
    int idActuel;

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
            case START_MODIFICATION:
                parent.addTab(
                        new PokemonModifInsertPanel(bottomPanel.modification.getId(), parent),
                        "Modification de " + db.getFromDB("SELECT name FROM pokemon WHERE id=" + bottomPanel.modification.getId()).get(0)[0],
                        MainPanel.PROFESSOR_TAB
                );
                break;
            case START_INSERTION:
                parent.addTab(
                        new PokemonModifInsertPanel(parent),
                        "Naissance d'un Pokemon", 
                        MainPanel.PROFESSOR_TAB
                );
                break;
            case DELETE:
                System.err.println("PAS ENCORE IMPLEMENTE");
                break;
        }
    }

    public void setId(int id) {
        topPanel.setId(id);
        idActuel = id;

        ArrayList<Object[]> infos = db.getFromDB("SELECT id_pokedex, is_shiny FROM pokemon p WHERE p.id=" + id);
        if (!infos.isEmpty()) {
            String imgName;
            if((Boolean)infos.get(0)[1]){
                imgName="image_shiny";
            }else{
                imgName="image";
            }
            Image image = db.getImage("SELECT "+imgName+" FROM pokedex WHERE id=" + infos.get(0)[0]);
            imagePanel.setImage(image);
        }else{
            imagePanel.setImage(null);
        }
        bottomPanel.setId(id);
    }

}
