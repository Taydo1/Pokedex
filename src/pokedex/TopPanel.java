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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Spectan
 */
public class TopPanel extends JPanel{

    PokedexApp parent;
    JComboBox choix;
    JButton classique, chromatique, mega;
    JLabel idNom, classification, poids, taille, type1, type2;

    public TopPanel(PokedexApp main, int id, Database db){

        parent = main;
        String[] personnes = new String[]{"Visiteur" ,"Dresseur", "Professeur"};
        choix = new JComboBox<String>(personnes);
        classique = new JButton("Classique");
        classique.setBackground(Color.gray);
        classique.setCursor(Cursor.getPredefinedCursor((Cursor.HAND_CURSOR)));
        chromatique = new JButton("Chromatique");
        chromatique.setBackground(Color.gray);
        chromatique.setCursor(Cursor.getPredefinedCursor((Cursor.HAND_CURSOR)));
        mega = new JButton("Méga-évolution");
        mega.setBackground(Color.gray);
        mega.setCursor(Cursor.getPredefinedCursor((Cursor.HAND_CURSOR)));
        ArrayList<Pokedex> list = db.getFromDB("SELECT * FROM pokedex WHERE id=" + String.valueOf(id), Pokedex.class);
        System.out.println(list.get(0).toString());
        if (id >= 100) {
            idNom = new JLabel(String.valueOf(id) + " " + list.get(0).name);
        } else if (id < 10) {
            idNom = new JLabel("00" + id + " " + list.get(0).name);
        } else {
            idNom = new JLabel("0" + id + " " + list.get(0).name);
        }
        idNom.setBackground(Color.gray);
        classification = new JLabel(list.get(0).classification);
        classification.setBackground(Color.gray);
        poids = new JLabel(""+list.get(0).weight + " kg");
        poids.setBackground(Color.gray);
        taille = new JLabel(""+list.get(0).height + " m");
        taille.setBackground(Color.gray);
        type1 = new JLabel(list.get(0).getType1(id, db));
        type1.setBackground(Color.gray);
        type2 = new JLabel(list.get(0).getType2(id, db));
        type2.setBackground(Color.gray);
        
        
        choix.addActionListener(parent);

        setLayout(new GridBagLayout());
        setBackground(Color.gray);
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        c.weightx = 1;
        add(choix ,c);
        c.gridwidth = 1;
        c.gridy = 1;
        add(classique, c);
        c.gridx = 1;
        add(chromatique, c);
        c.gridx = 2;
        add(mega ,c);
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
        ArrayList<Pokedex> list = db.getFromDB("SELECT * FROM pokedex WHERE id=" + String.valueOf(id), Pokedex.class);
        classification.setText(list.get(0).classification);
        if (id >= 100) {
            idNom.setText(String.valueOf(id) + " " + list.get(0).name);
        } else if (id < 10) {
            idNom.setText("00" + id + " " + list.get(0).name);
        } else {
            idNom.setText("0" + id + " " + list.get(0).name);
        }
        poids.setText(""+list.get(0).weight + " kg");
        taille.setText(""+list.get(0).height + " m");
        type1.setText(list.get(0).getType1(id, db));
        if (list.get(0).id_type2 == 0){
            type2.setText("");
        } else {
            type2.setText(list.get(0).getType2(id, db));
        }
        this.repaint();
    }
}