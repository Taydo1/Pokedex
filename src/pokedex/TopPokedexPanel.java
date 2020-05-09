/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Quentin
 */
public class TopPokedexPanel extends JPanel {

    ToggleButton classique, chromatique, mega;
    InfoButton type1, type2, ability1, ability2, ability3, ability4;
    JLabel idNom, classification, poids, taille;
    Color backgroundColor;

    public TopPokedexPanel(PokedexPanel parent) {
        backgroundColor=Color.GRAY;
        
        ButtonGroup group = new ButtonGroup();
        classique = new ToggleButton("Classique");
        classique.setCursor(Cursor.getPredefinedCursor((Cursor.HAND_CURSOR)));
        classique.setSelected(true);
        group.add(classique);
        chromatique = new ToggleButton("Chromatique");
        chromatique.setCursor(Cursor.getPredefinedCursor((Cursor.HAND_CURSOR)));
        group.add(chromatique);
        mega = new ToggleButton("Méga-évolution");
        mega.setCursor(Cursor.getPredefinedCursor((Cursor.HAND_CURSOR)));
        group.add(mega);

        idNom = new JLabel();
        idNom.setPreferredSize(new Dimension(1, 20));
        classification = new JLabel();
        classification.setPreferredSize(new Dimension(1, 20));
        poids = new JLabel();
        poids.setPreferredSize(new Dimension(1, 20));
        taille = new JLabel();
        taille.setPreferredSize(new Dimension(1, 20));
        type1 = new InfoButton();
        type2 = new InfoButton();
        ability1 = new InfoButton();
        ability2 = new InfoButton();
        ability3 = new InfoButton();
        ability4 = new InfoButton();
        JPanel abilityPanel = new JPanel();

        abilityPanel.setLayout(new GridBagLayout());
        GridBagConstraints c1 = new GridBagConstraints();
        c1.fill = GridBagConstraints.BOTH;
        c1.weightx = 1;
        c1.gridx = 0;
        abilityPanel.add(ability1, c1);
        c1.gridx = 1;
        abilityPanel.add(ability2, c1);
        c1.gridx = 2;
        abilityPanel.add(ability3, c1);
        c1.gridx = 3;
        abilityPanel.add(ability4, c1);

        classique.addActionListener(parent);
        classique.setActionCommand(Action.IMAGE_NORMAL.name());
        chromatique.addActionListener(parent);
        chromatique.setActionCommand(Action.IMAGE_SHINY.name());
        mega.addActionListener(parent);
        mega.setActionCommand(Action.IMAGE_MEGA.name());
        type1.addActionListener((MainPanel) parent.parent);
        type1.setActionCommand(Action.GET_TYPE.name());
        type2.addActionListener((MainPanel) parent.parent);
        type2.setActionCommand(Action.GET_TYPE.name());
        ability1.addActionListener((MainPanel) parent.parent);
        ability1.setActionCommand(Action.GET_ABILITY.name());
        ability2.addActionListener((MainPanel) parent.parent);
        ability2.setActionCommand(Action.GET_ABILITY.name());
        ability3.addActionListener((MainPanel) parent.parent);
        ability3.setActionCommand(Action.GET_ABILITY.name());
        ability4.addActionListener((MainPanel) parent.parent);
        ability4.setActionCommand(Action.GET_ABILITY.name());

        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.weightx = 1;
        c.gridwidth = 1;
        c.gridy = 1;
        add(classique, c);
        c.gridx = 1;
        add(chromatique, c);
        c.gridx = 2;
        add(mega, c);
        c.gridy = 2;
        add(taille, c);
        c.gridx = 1;
        add(poids, c);
        c.gridx = 0;
        add(idNom, c);
        c.gridy = 3;
        add(classification, c);
        c.gridx = 1;
        add(type1, c);
        c.gridx = 2;
        add(type2, c);
        c.gridy = 4;
        c.gridx = 0;
        c.gridwidth = 3;
        add(abilityPanel, c);
        
        setColor(backgroundColor);
    }

    public void setId(int id, Database db) {
        Pokedex pokeActuel = db.getFromDB("SELECT * FROM pokedex WHERE id=" + String.valueOf(id), Pokedex.class).get(0);
        classification.setText(pokeActuel.classification);
        if (id >= 100) {
            idNom.setText(String.valueOf(id) + " " + pokeActuel.name);
        } else if (id < 10) {
            idNom.setText("00" + id + " " + pokeActuel.name);
        } else {
            idNom.setText("0" + id + " " + pokeActuel.name);
        }

        if (!pokeActuel.has_mega && mega.isSelected()) {
            classique.doClick();
        }
        if (!pokeActuel.has_shiny && chromatique.isSelected()) {
            classique.doClick();
        }
        
        switch(pokeActuel.is_legendary){
            case 0:
                setColor(Color.GRAY);
                break;
            case 1:
                setColor(new Color(126, 138, 171));
                break;
            case 2:
                setColor(new Color(126, 200, 217));
                break;
        }
        mega.setEnabled(pokeActuel.has_mega);
        chromatique.setEnabled(pokeActuel.has_shiny);

        poids.setText("" + pokeActuel.weight + " kg");
        taille.setText("" + pokeActuel.height + " m");
        type1.setText(pokeActuel.getTypeName(id, db, 1));
        type1.setId(pokeActuel.id_type1);

        if (pokeActuel.id_type2 != 0) {
            type2.setVisible(true);
            type2.setText(pokeActuel.getTypeName(id, db, 2));
            type2.setId(pokeActuel.id_type2);
        } else {
            type2.setVisible(false);
        }
        ability1.setText(pokeActuel.getAbilityName(id, db, 1));
        ability1.setId(pokeActuel.id_ability1);

        if (pokeActuel.id_ability2 != 0) {
            ability2.setVisible(true);
            ability2.setText(pokeActuel.getAbilityName(id, db, 2));
            ability2.setId(pokeActuel.id_ability2);
        } else {
            ability2.setVisible(false);
        }
        if (pokeActuel.id_ability3 != 0) {
            ability3.setVisible(true);
            ability3.setText(pokeActuel.getAbilityName(id, db, 3));
            ability3.setId(pokeActuel.id_ability3);
        } else {
            ability3.setVisible(false);
        }
        if (pokeActuel.id_ability4 != 0) {
            ability4.setVisible(true);
            ability4.setText(pokeActuel.getAbilityName(id, db, 4));
            ability4.setId(pokeActuel.id_ability4);
        } else {
            ability4.setVisible(false);
        }
        this.repaint();
    }
    
    public void setColor(Color color){
        classique.setBackground(color);
        chromatique.setBackground(color);
        mega.setBackground(color);
        idNom.setBackground(color);
        classification.setBackground(color);
        poids.setBackground(color);
        taille.setBackground(color);
        type1.setBackground(color);
        type2.setBackground(color);
        ability1.setBackground(color);
        ability2.setBackground(color);
        ability3.setBackground(color);
        ability4.setBackground(color);
        setBackground(color);
    }
}
