/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex.gui.pokedex;

import pokedex.gui.widgets.Label;
import pokedex.gui.widgets.InfoButton;
import pokedex.gui.widgets.ToggleButton;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import pokedex.gui.*;
import pokedex.database.*;

/**
 *
 * @author Quentin
 */
public class PokedexTopPanel extends JPanel {

    ToggleButton classique, chromatique, mega;
    InfoButton type1, type2, ability1, ability2, ability3, ability4, evolution1, evolution2, lowerEvolution1;
    Label idName, classification, weight, height, malePercentage, type, ability, evolution, lowerEvolution;
    Color backgroundColor;

    public PokedexTopPanel(PokedexPanel parent) {
        backgroundColor = Color.GRAY;

        ButtonGroup imageButtonGroup = new ButtonGroup();
        classique = new ToggleButton("Classique");
        classique.setCursor(Cursor.getPredefinedCursor((Cursor.HAND_CURSOR)));
        classique.setSelected(true);
        classique.setForeground(Color.WHITE);
        chromatique = new ToggleButton("Chromatique");
        chromatique.setCursor(Cursor.getPredefinedCursor((Cursor.HAND_CURSOR)));
        chromatique.setForeground(Color.WHITE);
        mega = new ToggleButton("Méga-évolution");
        mega.setCursor(Cursor.getPredefinedCursor((Cursor.HAND_CURSOR)));
        mega.setForeground(Color.WHITE);

        imageButtonGroup.add(classique);
        imageButtonGroup.add(chromatique);
        imageButtonGroup.add(mega);

        idName = new Label();
        idName.setFont(idName.getFont().deriveFont(20f));
        idName.setPreferredSize(new Dimension(1, 40));
        classification = new Label();
        classification.setFont(classification.getFont().deriveFont(18f));
        classification.setPreferredSize(new Dimension(1, 20));

        type = new Label("Type : ");
        type.setPreferredSize(new Dimension(1, 20));
        type1 = new InfoButton();
        type1.setActionCommand(Action.GET_TYPE.name());
        type1.addActionListener(parent.parent);
        type2 = new InfoButton();
        type2.setActionCommand(Action.GET_TYPE.name());
        type2.addActionListener(parent.parent);

        weight = new Label();
        weight.setPreferredSize(new Dimension(1, 20));
        height = new Label();
        height.setPreferredSize(new Dimension(1, 20));
        malePercentage = new Label();
        malePercentage.setPreferredSize(new Dimension(1, 20));

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

        lowerEvolution = new Label("Sous-Evolution");
        lowerEvolution.setPreferredSize(new Dimension(1, 20));
        lowerEvolution1 = new InfoButton();

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
        evolution1.addActionListener(parent.parent);
        evolution1.setActionCommand(Action.GET_POKEDEX.name());
        evolution2.addActionListener(parent.parent);
        evolution2.setActionCommand(Action.GET_POKEDEX.name());
        lowerEvolution1.addActionListener(parent.parent);
        lowerEvolution1.setActionCommand(Action.GET_POKEDEX.name());

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
        c.gridwidth = 2;
        add(idName, c);
        c.gridx += 2;
        c.gridwidth = 1;
        add(classification, c);
        c.gridx = 0;
        c.gridy++;
        add(type, c);
        c.gridx++;
        add(type1, c);
        c.gridx++;
        add(type2, c);
        c.gridy++;
        c.gridx = 0;
        add(weight, c);
        c.gridx++;
        add(height, c);
        c.gridx++;
        add(malePercentage, c);
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
        add(lowerEvolution, c);
        c.gridx++;
        add(lowerEvolution1, c);
        c.gridy++;
        c.gridx = 0;
        add(evolution, c);
        c.gridx++;
        add(evolution1, c);
        c.gridx++;
        add(evolution2, c);

        setBackground(backgroundColor);
    }

    public void setId(int id, Database db) {
        Pokedex pokeActuel = db.getFromDB("SELECT * FROM pokedex WHERE id=" + String.valueOf(id), Pokedex.class).get(0);
        classification.setText(pokeActuel.classification);
        if (id >= 100) {
            idName.setText("" + id + " " + pokeActuel.name + " (" + pokeActuel.en_name + ")");
        } else if (id < 10) {
            idName.setText("00" + id + " " + pokeActuel.name + " (" + pokeActuel.en_name + ")");
        } else {
            idName.setText("0" + id + " " + pokeActuel.name + " (" + pokeActuel.en_name + ")");
        }

        if (!pokeActuel.has_mega && mega.isSelected()) {
            classique.doClick();
        }
        if (!pokeActuel.has_shiny && chromatique.isSelected()) {
            classique.doClick();
        }

        switch (pokeActuel.is_legendary) {
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

        weight.setText(String.format("Poids : %.1fkg", pokeActuel.weight));
        height.setText(String.format("Taille : %.1fm", pokeActuel.height));
        malePercentage.setText(String.format("%.1f%% de mâles", pokeActuel.percentage_male * 100));
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
            lowerEvolution.setVisible(true);
            lowerEvolution1.setVisible(true);
            lowerEvolution1.setText(pokeActuel.getLowerEvolutionName(db));
            lowerEvolution1.setId(pokeActuel.id_lower_evolution);
        } else {
            lowerEvolution.setVisible(false);
            lowerEvolution1.setVisible(false);
        }
        if (pokeActuel.id_evolution1 != 0) {
            evolution.setVisible(true);
            evolution1.setVisible(true);
            evolution1.setText(pokeActuel.getEvolutionName(db, 1));
            evolution1.setId(pokeActuel.id_evolution1);
            if (pokeActuel.id_evolution2 != 0) {
                evolution2.setVisible(true);
                evolution2.setText(pokeActuel.getEvolutionName(db, 2));
                evolution2.setId(pokeActuel.id_evolution2);
            } else {
                evolution2.setVisible(false);
            }
        } else {
            evolution.setVisible(false);
            evolution1.setVisible(false);
            evolution2.setVisible(false);
        }

    }

    public void setColor(Color bgColor) {
        classique.setBackground(bgColor);
        chromatique.setBackground(bgColor);
        mega.setBackground(bgColor);
        setBackground(bgColor);
    }
}
