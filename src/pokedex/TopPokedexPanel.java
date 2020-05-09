/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex;

import java.awt.Color;
import java.awt.Cursor;
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
    InfoButton type1, type2;
    JLabel idNom, classification, poids, taille;

    public TopPokedexPanel(PokedexPanel parent) {

        ButtonGroup group = new ButtonGroup();
        classique = new ToggleButton("Classique");
        classique.setBackground(Color.gray);
        classique.setCursor(Cursor.getPredefinedCursor((Cursor.HAND_CURSOR)));
        classique.setSelected(true);
        group.add(classique);
        chromatique = new ToggleButton("Chromatique");
        chromatique.setBackground(Color.gray);
        chromatique.setCursor(Cursor.getPredefinedCursor((Cursor.HAND_CURSOR)));
        group.add(chromatique);
        mega = new ToggleButton("Méga-évolution");
        mega.setBackground(Color.gray);
        mega.setCursor(Cursor.getPredefinedCursor((Cursor.HAND_CURSOR)));
        group.add(mega);

        idNom = new JLabel();
        idNom.setBackground(Color.gray);
        classification = new JLabel();
        classification.setBackground(Color.gray);
        poids = new JLabel();
        poids.setBackground(Color.gray);
        taille = new JLabel();
        taille.setBackground(Color.gray);
        type1 = new InfoButton();
        type1.setBackground(Color.gray);
        type2 = new InfoButton();
        type2.setBackground(Color.gray);

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

        setLayout(new GridBagLayout());
        setBackground(Color.gray);
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
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
        mega.setEnabled(pokeActuel.has_mega);
        chromatique.setEnabled(pokeActuel.has_shiny);

        poids.setText("" + pokeActuel.weight + " kg");
        taille.setText("" + pokeActuel.height + " m");
        type1.setText(pokeActuel.getType1(id, db));
        type1.setId(pokeActuel.id_type1);
        if (pokeActuel.id_type2 == 0) {
            type2.setText("");
        } else {
            type2.setText(pokeActuel.getType2(id, db));
            type2.setId(pokeActuel.id_type2);
        }
        System.out.println(""+pokeActuel.id_ability4);
        this.repaint();
    }
}
