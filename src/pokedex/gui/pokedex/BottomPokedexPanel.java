/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex.gui.pokedex;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import pokedex.gui.*;
import pokedex.database.*;
/**
 *
 * @author Quentin
 */
public class BottomPokedexPanel extends JPanel{

    JPanel gauche, droite, allerId, allerNom;
    Label idActuel, allerAId, allerANom;
    JButton up, down, go, modification, ajout, delete, gerer,goNomButton;
    JTextField goId, goNom;
    String utilisateur;

    public BottomPokedexPanel(String utilisateur, PokedexPanel parent) {  
        
        gauche = new JPanel();
        droite = new JPanel();
        allerId = new JPanel();
        idActuel = new Label("");
        allerAId = new Label("Aller à l'ID : ");
        up = new JButton(new ImageIcon(getClass().getResource("/images/icones/fleche_haut.png")));
        up.setContentAreaFilled(false);
        up.setCursor(Cursor.getPredefinedCursor((Cursor.HAND_CURSOR)));
        down = new JButton(new ImageIcon(getClass().getResource("/images/icones/fleche_bas.png")));
        down.setContentAreaFilled(false);
        down.setCursor(Cursor.getPredefinedCursor((Cursor.HAND_CURSOR)));
        go = new JButton("GO to ID");
        go.setBackground(Color.gray);
        go.setCursor(Cursor.getPredefinedCursor((Cursor.HAND_CURSOR)));
        modification = new JButton("");
        modification.setBackground(Color.gray);
        modification.setCursor(Cursor.getPredefinedCursor((Cursor.HAND_CURSOR)));
        modification.setForeground(Color.black);
        modification.addActionListener(parent);
        modification.setActionCommand(Action.START_POKEDEX_MODIFICATION.name());
        ajout = new JButton("Ajouter un pokémon");
        ajout.setBackground(Color.gray);
        ajout.setCursor(Cursor.getPredefinedCursor((Cursor.HAND_CURSOR)));
        ajout.setForeground(Color.black);
        delete = new JButton("");
        delete.setBackground(Color.gray);
        delete.setCursor(Cursor.getPredefinedCursor((Cursor.HAND_CURSOR)));
        delete.setForeground(Color.black);
        gerer = new JButton("Gérer l'équipe");
        gerer.setBackground(Color.gray);
        gerer.setCursor(Cursor.getPredefinedCursor((Cursor.HAND_CURSOR)));
        gerer.setForeground(Color.black);
        goId = new JTextField();
        goId.setBackground(Color.gray);
        goId.setForeground(Color.white);
        goId.setColumns(3);
        allerNom = new JPanel();
        goNom = new JTextField();
        goNom.setBackground(Color.gray);
        goNom.setForeground(Color.white);
        goNom.setColumns(3);
        allerANom = new Label("Search by name:");
        goNomButton = new JButton("Search name");
        goNomButton.setBackground(Color.gray);
        goNomButton.setCursor(Cursor.getPredefinedCursor((Cursor.HAND_CURSOR)));

        go.addActionListener(parent);
        goId.addActionListener(parent);
        up.addActionListener(parent);
        down.addActionListener(parent);
        go.setActionCommand(Action.GO.name());
        goId.setActionCommand(Action.GO.name());
        up.setActionCommand(Action.UP.name());
        down.setActionCommand(Action.DOWN.name());
        goNom.addActionListener(parent);
        goNom.setActionCommand(Action.GO_NOM.name());
        goNomButton.addActionListener(parent);
        goNomButton.setActionCommand(Action.GO_NOM.name());

        gauche.setLayout(new GridLayout(0, 1));
        gauche.setBackground(Color.gray);
        droite.setLayout(new BorderLayout());
        droite.setBackground(Color.gray);
        allerId.setLayout(new GridBagLayout());
        allerId.setBackground(Color.gray);
        allerNom.setLayout(new GridBagLayout());
        allerNom.setBackground(Color.gray);
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
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.4;
        allerNom.add(allerANom,c);
        c.gridx = 1;
        c.weightx = 0.4;
        allerNom.add(goNom,c);
        c.gridx = 2;
        c.weightx = 0.4;
        allerNom.add(goNomButton,c);

        gauche.add(allerId);
        gauche.add(allerNom);
        gauche.add(ajout);
        gauche.add(modification);
        gauche.add(delete);
        gauche.add(gerer);
        gauche.setSize((int) (parent.getWidth() * 0.7), (int) (parent.getHeight() * 0.2));

        droite.add(up, BorderLayout.NORTH);
        droite.add(idActuel, BorderLayout.CENTER);
        droite.add(down, BorderLayout.SOUTH);
        droite.setSize((int) (parent.getWidth() * 0.3), (int) (parent.getHeight() * 0.2));

        setUtilisateur(utilisateur);
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.weightx = 0.7;
        add(gauche, c);
        c.gridx = 1;
        c.weightx = 0.3;
        add(droite, c);
    }

    public void setUtilisateur(String utilisateur) {
        this.utilisateur = utilisateur.substring(0, 1).toUpperCase() + utilisateur.substring(1);
        switch (this.utilisateur) {
            case "Professeur":
                ajout.setEnabled(true);
                modification.setEnabled(true);
                gerer.setEnabled(false);
                delete.setEnabled(true);
                break;
            case "Dresseur":
                ajout.setEnabled(true);
                modification.setEnabled(false);
                gerer.setEnabled(true);
                delete.setEnabled(false);
                break;
            case "Visiteur":
                ajout.setEnabled(false);
                modification.setEnabled(false);
                gerer.setEnabled(false);
                delete.setEnabled(false);
                break;
        }
        this.repaint();
    }

    public String getUtilisateur() {
        return utilisateur;
    }

    public void setId(int id, Database db) {
        String name = (String) db.getFromDB("SELECT name FROM pokedex WHERE id=" + String.valueOf(id)).get(0)[0];
        idActuel.setText("ID actuel : " + String.valueOf(id));
        delete.setText("Supprimer les données sur " + name);
        modification.setText("Modifier les données sur " + name);
        ArrayList<Object[]> nb_pokemon = db.getFromDB("SELECT id FROM pokedex");
        if(id == 1){
            down.setEnabled(false);
            up.setEnabled(true);
        } else if (id == nb_pokemon.size()){
            down.setEnabled(true);
            up.setEnabled(false);
        } else {
            down.setEnabled(true);
            up.setEnabled(true);
        }
        this.repaint();
    }

    public int getGoId() throws  NumberFormatException{
        return Integer.parseInt(goId.getText());
    }
    
    public void clearGoId() {
        goId.setText("");
    }
    public int getIDFromNom(Database db){
        int id = (int) db.getFromDB("SELECT id FROM pokedex WHERE UPPER(name) LIKE UPPER('"+goNom.getText()+"')").get(0)[0];
        System.out.println("ID récupéré : "+id);
        return id;
    }

 public void clearGoNom(){
        goNom.setText("");
    }
}
