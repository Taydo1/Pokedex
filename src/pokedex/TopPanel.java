/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex;

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
        choix.addActionListener(parent);
        choix.setActionCommand(Action.CHANGE_USER.name());
        classique = new JButton("Classique");
        chromatique = new JButton("Chromatique");
        mega = new JButton("Méga-évolution");
        ArrayList<Pokedex> list = db.getFromDB("SELECT * FROM pokedex WHERE id=" + String.valueOf(id), Pokedex.class);
        System.out.println(list.get(0).toString());
        if (id >= 100) {
            idNom = new JLabel(String.valueOf(id) + " " + list.get(0).name);
        } else if (id < 10) {
            idNom = new JLabel("00" + id + " " + list.get(0).name);
        } else {
            idNom = new JLabel("0" + id + " " + list.get(0).name);
        }
        //classification = new JLabel(list.get(0).classfication);
        poids = new JLabel(""+list.get(0).weight + " kg");
        taille = new JLabel(""+list.get(0).height + " m");
        type1 = new JLabel(list.get(0).getType1(id, db));
        type2 = new JLabel(list.get(0).getType2(id, db));

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        add(choix ,c);
        c.gridy = 1;
        c.weightx = (1/3);
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
}
