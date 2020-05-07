/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author Spectan
 */
public class SelectionPanel extends JPanel{

    JFrame frame;
    JPanel gauche, droite, allerId;
    JLabel idActuel, nom, allerAId;
    JButton up, down, go, modification, ajout, gerer;
    JTextField goId;
    String utilisateur;

    public SelectionPanel(int id, String name, JFrame f, String user, PokedexApp parent) {        
        frame = f;
        gauche = new JPanel();
        droite = new JPanel();
        allerId = new JPanel();
        idActuel = new JLabel("ID actuel : " + String.valueOf(id), SwingConstants.CENTER);
        nom = new JLabel("Nom : " + name, SwingConstants.CENTER);
        allerAId = new JLabel("Aller à l'ID : ", SwingConstants.CENTER);
        up = new JButton(new ImageIcon(getClass().getResource("/images/icones/fleche_haut.png")));
        up.setContentAreaFilled(false);
        up.setCursor(Cursor.getPredefinedCursor((Cursor.HAND_CURSOR)));
        up.setText("up");
        down = new JButton(new ImageIcon(getClass().getResource("/images/icones/fleche_bas.png")));
        down.setContentAreaFilled(false);
        down.setCursor(Cursor.getPredefinedCursor((Cursor.HAND_CURSOR)));
        down.setText("down");
        go = new JButton("GO to ID");
        go.setBackground(Color.gray);
        go.setCursor(Cursor.getPredefinedCursor((Cursor.HAND_CURSOR)));
        go.setText("go");
        modification = new JButton("Modifier les données sur " + name);
        modification.setBackground(Color.gray);
        modification.setCursor(Cursor.getPredefinedCursor((Cursor.HAND_CURSOR)));
        modification.setForeground(Color.black);
        ajout = new JButton("Ajouter un pokémon");
        ajout.setBackground(Color.gray);
        ajout.setCursor(Cursor.getPredefinedCursor((Cursor.HAND_CURSOR)));
        ajout.setForeground(Color.black);
        gerer = new JButton("Gérer l'équipe");
        gerer.setBackground(Color.gray);
        gerer.setCursor(Cursor.getPredefinedCursor((Cursor.HAND_CURSOR)));
        gerer.setForeground(Color.black);
        goId = new JTextField();
        goId.setBackground(Color.gray);
        goId.setForeground(Color.white);
        goId.setColumns(3);

        go.addActionListener(parent);
        goId.addActionListener(parent);
        up.addActionListener(parent);
        down.addActionListener(parent);

        
        utilisateur = user;

        gauche.setLayout(new GridLayout(0, 1));
        gauche.setBackground(Color.gray);
        droite.setLayout(new BorderLayout());
        droite.setBackground(Color.gray);
        allerId.setLayout(new GridBagLayout());
        allerId.setBackground(Color.gray);
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.4;
        allerId.add(allerAId, c);
        c.gridx = 1;
        c.weightx = 0.2;
        allerId.add(goId, c);
        c.gridx = 2;
        c.weightx = 0.4;
        allerId.add(go, c);

        gauche.add(nom);
        gauche.add(allerId);
        gauche.add(modification);
        gauche.add(ajout);
        gauche.add(gerer);
        gauche.setSize((int) (frame.getWidth() * 0.7), (int) (frame.getHeight() * 0.2));

        droite.add(up, BorderLayout.NORTH);
        droite.add(idActuel, BorderLayout.CENTER);
        droite.add(down, BorderLayout.SOUTH);
        droite.setSize((int) (frame.getWidth() * 0.3), (int) (frame.getHeight() * 0.2));

        switch (utilisateur) {
            case "professeur":
                ajout.setEnabled(true);
                modification.setEnabled(true);
                gerer.setEnabled(false);
                break;
            case "dresseur":
                ajout.setEnabled(true);
                modification.setEnabled(false);
                gerer.setEnabled(true);
                break;
            case "visiteur":
                ajout.setEnabled(false);
                modification.setEnabled(false);
                gerer.setEnabled(false);
                break;
        }
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.weightx = 0.7;
        add(gauche, c);
        c.gridx = 1;
        c.weightx = 0.3;
        add(droite, c);
    }

    public void setUtilisateur(String user) {

    }

    public void setId(int id, Database db) {
        ArrayList<Pokedex> listname = db.getFromDB("SELECT * FROM pokedex WHERE id=" + String.valueOf(id), Pokedex.class);
        String name = listname.get(0).name;
        idActuel.setText("ID actuel : " + String.valueOf(id));
        nom.setText("Nom : " + name);
        this.repaint();
    }

    public int getGoId() {
        return Integer.parseInt(goId.getText());
    }
}
