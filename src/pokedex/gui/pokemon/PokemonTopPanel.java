/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex.gui.pokemon;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import pokedex.database.Database;
import pokedex.gui.Action;
import pokedex.gui.InfoButton;
import pokedex.gui.Label;
import pokedex.gui.MainPanel;

/**
 *
 * @author Leon
 */
public class PokemonTopPanel extends JPanel{
    JComboBox<InfoButton> selector;
    InfoButton type1, type2, ability1, ability2, ability3, ability4, evolution1, evolution2, sousEvolution1;
    Label name, classification, poids, taille, pourcentageMale, type, ability, evolution, sousEvolution;

    public PokemonTopPanel(Database db, PokemonPanel parent) {
        super();
        name = new Label("",true);
        classification = new Label("", true);
        poids = new Label("", true);
        taille = new Label("", true);
        pourcentageMale = new Label("", true);
        type = new Label("", true);
        ability = new Label("", true);
        evolution = new Label("", true);
        sousEvolution = new Label("", true);
        type1 = new InfoButton();
        selector = new JComboBox();
        selector.setBackground(Color.GRAY);
        selector.setForeground(Color.WHITE);

        InfoButton selectorButton;
        ArrayList<Object[]> abilityNames = db.getFromDB("SELECT id,name FROM ability");
        for (int i = 0; i < abilityNames.size(); i++) {
            selectorButton = new InfoButton((String) abilityNames.get(i)[1], (Integer) abilityNames.get(i)[0]);
            selector.addItem(selectorButton);
        }

        selector.setActionCommand(Action.GET_ABILITY.name());
        selector.addActionListener(parent);

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;


        //setId(1);
    }

    private void setId(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
