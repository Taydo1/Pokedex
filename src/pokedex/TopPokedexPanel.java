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
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;

/**
 *
 * @author Quentin
 */
public class TopPokedexPanel extends JPanel {

    ToggleButton classique, chromatique, mega;
    InfoButton type1, type2, ability1, ability2, ability3, ability4, evolution1, evolution2, sousEvolution1;
    Label idNom, classification, poids, taille, pourcentageMale, type, ability, evolution, sousEvolution;
    Color backgroundColor;

    public TopPokedexPanel(PokedexPanel parent) {
        backgroundColor = Color.GRAY;

        ButtonGroup imageButtonGroup = new ButtonGroup();
        classique = new ToggleButton("Classique");
        classique.setCursor(Cursor.getPredefinedCursor((Cursor.HAND_CURSOR)));
        classique.setSelected(true);
        chromatique = new ToggleButton("Chromatique");
        chromatique.setCursor(Cursor.getPredefinedCursor((Cursor.HAND_CURSOR)));
        mega = new ToggleButton("Méga-évolution");
        mega.setCursor(Cursor.getPredefinedCursor((Cursor.HAND_CURSOR)));

        imageButtonGroup.add(classique);
        imageButtonGroup.add(chromatique);
        imageButtonGroup.add(mega);

        idNom = new Label();
        idNom.setFont(idNom.getFont().deriveFont(20f));
        idNom.setPreferredSize(new Dimension(1, 40));
        classification = new Label();
        classification.setFont(classification.getFont().deriveFont(18f));
        classification.setPreferredSize(new Dimension(1, 20));
        
        type = new Label("Type : ");
        type.setPreferredSize(new Dimension(1, 20));
        type1 = new InfoButton();
        type2 = new InfoButton();
        
        poids = new Label();
        poids.setPreferredSize(new Dimension(1, 20));
        taille = new Label();
        taille.setPreferredSize(new Dimension(1, 20));
        pourcentageMale = new Label();
        pourcentageMale.setPreferredSize(new Dimension(1, 20));
        
        ability = new Label("Talent : ");
        ability.setPreferredSize(new Dimension(1, 20));
        ability1 = new InfoButton();
        ability2 = new InfoButton();
        ability3 = new InfoButton();
        ability4 = new InfoButton();
        
        evolution = new Label("Evolution");
        evolution.setPreferredSize(new Dimension(1, 20));
        evolution1 = new InfoButton();
        evolution2 = new InfoButton();

        sousEvolution = new Label("Sous-Evolution");
        sousEvolution.setPreferredSize(new Dimension(1, 20));
        sousEvolution1 = new InfoButton();

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
        evolution1.addActionListener(parent);
        evolution1.setActionCommand(Action.GET_POKEDEX.name());
        evolution2.addActionListener(parent);
        evolution2.setActionCommand(Action.GET_POKEDEX.name());
        sousEvolution1.addActionListener(parent);
        sousEvolution1.setActionCommand(Action.GET_POKEDEX.name());

        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.gridwidth = 1;
        add(classique, c);
        c.gridx++;
        add(chromatique, c);
        c.gridx++;
        add(mega, c);
        c.gridy++;
        c.gridx = 0;
        c.gridwidth=2;
        add(idNom,c);
        c.gridx+=2;
        c.gridwidth=1;
        add(classification,c);
        c.gridx=0;
        c.gridy++;
        add(type, c);
        c.gridx++;
        add(type1, c);
        c.gridx++;
        add(type2, c);
        c.gridy++;
        c.gridx = 0;
        add(poids, c);
        c.gridx++;
        add(taille, c);
        c.gridx++;
        add(pourcentageMale, c);
        c.gridy++;
        c.gridx = 0;
        c.gridheight = 2;
        add(ability, c);
        c.gridheight = 1;
        c.gridx++;
        add(ability1, c);
        c.gridx++;
        add(ability2, c);
        c.gridy++;
        c.gridx = 1;
        add(ability3, c);
        c.gridx++;
        add(ability4, c);
        c.gridy++;
        c.gridx = 0;
        add(sousEvolution, c);
        c.gridx++;
        add(sousEvolution1, c);
        c.gridy++;
        c.gridx = 0;
        add(evolution, c);
        c.gridx++;
        add(evolution1, c);
        c.gridx++;
        add(evolution2, c);
        
        setColor(backgroundColor, Color.WHITE);
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

        switch (pokeActuel.is_legendary) {
            case 0:
                setColor(Color.GRAY, Color.WHITE);
                break;
            case 1:
                setColor(new Color(126, 138, 171), Color.WHITE);
                break;
            case 2:
                setColor(new Color(126, 200, 217), Color.WHITE);
                break;
        }
        mega.setEnabled(pokeActuel.has_mega);
        chromatique.setEnabled(pokeActuel.has_shiny);

        poids.setText("" + pokeActuel.weight + " kg");
        taille.setText("" + pokeActuel.height + " m");
        type1.setText(pokeActuel.getTypeName(db, 1));
        type1.setId(pokeActuel.id_type1);

        if (pokeActuel.id_type2 != 0) {
            type2.setVisible(true);
            type2.setText(pokeActuel.getTypeName(db, 2));
            type2.setId(pokeActuel.id_type2);
        } else {
            type2.setVisible(false);
        }
        ability1.setText(pokeActuel.getAbilityName(db, 1));
        ability1.setId(pokeActuel.id_ability1);

        if (pokeActuel.id_ability2 != 0) {
            ability2.setVisible(true);
            ability2.setText(pokeActuel.getAbilityName(db, 2));
            ability2.setId(pokeActuel.id_ability2);
        } else {
            ability2.setVisible(false);
        }
        if (pokeActuel.id_ability3 != 0) {
            ability3.setVisible(true);
            ability3.setText(pokeActuel.getAbilityName(db, 3));
            ability3.setId(pokeActuel.id_ability3);
        } else {
            ability3.setVisible(false);
        }
        if (pokeActuel.id_ability4 != 0) {
            ability4.setVisible(true);
            ability4.setText(pokeActuel.getAbilityName(db, 4));
            ability4.setId(pokeActuel.id_ability4);
        } else {
            ability4.setVisible(false);
        }
        
        if (pokeActuel.id_lower_evolution != 0) {
            sousEvolution.setVisible(true);
            sousEvolution1.setVisible(true);
            sousEvolution1.setText(pokeActuel.getLowerEvolutionName(db));
            sousEvolution1.setId(pokeActuel.id_lower_evolution);
        } else {
            sousEvolution.setVisible(false);
            sousEvolution1.setVisible(false);
        }
        if (pokeActuel.id_evolution != 0) {
            evolution.setVisible(true);
            evolution1.setVisible(true);
            evolution1.setText(pokeActuel.getEvolutionName(db));
            evolution1.setId(pokeActuel.id_evolution);
        } else {
            evolution.setVisible(false);
            evolution1.setVisible(false);
        }
        
        this.repaint();
    }

    public void setColor(Color bgColor,Color fgColor) {
        classique.setBackground(bgColor);
        chromatique.setBackground(bgColor);
        mega.setBackground(bgColor);
        type1.setBackground(bgColor);
        type2.setBackground(bgColor);
        ability1.setBackground(bgColor);
        ability2.setBackground(bgColor);
        ability3.setBackground(bgColor);
        ability4.setBackground(bgColor);
        evolution1.setBackground(bgColor);
        evolution2.setBackground(bgColor);
        sousEvolution1.setBackground(bgColor);
        setBackground(bgColor);
        
        classique.setForeground(fgColor);
        chromatique.setForeground(fgColor);
        mega.setForeground(fgColor);
        idNom.setForeground(fgColor);
        classification.setForeground(fgColor);
        type.setForeground(fgColor);
        type1.setForeground(fgColor);
        type2.setForeground(fgColor);
        poids.setForeground(fgColor);
        taille.setForeground(fgColor);
        pourcentageMale.setForeground(fgColor);
        ability.setForeground(fgColor);
        ability1.setForeground(fgColor);
        ability2.setForeground(fgColor);
        ability3.setForeground(fgColor);
        ability4.setForeground(fgColor);
        evolution.setForeground(fgColor);
        evolution1.setForeground(fgColor);
        evolution2.setForeground(fgColor);
        sousEvolution.setForeground(fgColor);
        sousEvolution1.setForeground(fgColor);
    }
}
