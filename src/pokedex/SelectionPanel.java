/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Spectan
 */
public class SelectionPanel extends JPanel{
    
    JFrame frame;
    JPanel gauche, droite, allerId;
    JLabel idActuel, nom, allerAId;
    JButton up, down, go, modification, ajout;
    JTextField goId;
    
    public SelectionPanel(int id, String name, JFrame f, String utilisateur){
        
        frame = f;
        gauche = new JPanel();
        droite = new JPanel();
        allerId = new JPanel();
        idActuel = new JLabel("ID actuel : " + String.valueOf(id));
        nom = new JLabel("Nom : " + name);
        allerAId = new JLabel("aller à l'ID : ");
        up = new JButton(new ImageIcon(getClass().getResource("/images/icones/fleche_haut.png")));
        up.setContentAreaFilled(false);
        up.setCursor(Cursor.getPredefinedCursor((Cursor.HAND_CURSOR)));
        down = new JButton(new ImageIcon(getClass().getResource("/images/icones/fleche_bas.png")));
        down.setContentAreaFilled(false);
        down.setCursor(Cursor.getPredefinedCursor((Cursor.HAND_CURSOR)));
        go = new JButton("GO to ID");
        go.setBackground(Color.gray);
        go.setCursor(Cursor.getPredefinedCursor((Cursor.HAND_CURSOR)));
        modification = new JButton("Modifier les données sur " + name);
        modification.setBackground(Color.gray);
        modification.setCursor(Cursor.getPredefinedCursor((Cursor.HAND_CURSOR)));
        modification.setForeground(Color.black);
        ajout = new JButton("Ajouter un pokémon");
        ajout.setBackground(Color.gray);
        ajout.setCursor(Cursor.getPredefinedCursor((Cursor.HAND_CURSOR)));
        ajout.setForeground(Color.black); 
        goId = new JTextField();
        goId.setBackground(Color.gray);
        goId.setForeground(Color.white);
        goId.setColumns(3);
        
        gauche.setLayout(new GridLayout(0, 1));
        gauche.setBackground(Color.gray);
        droite.setLayout(new BorderLayout());
        droite.setBackground(Color.gray);
        allerId.setLayout(new BorderLayout());
        allerId.setBackground(Color.gray);
        setLayout(new GridLayout(0,2));
        
        allerId.add(this.allerAId, BorderLayout.WEST);
        allerId.add(this.goId, BorderLayout.EAST);
        
        gauche.add(idActuel);
        gauche.add(nom);
        gauche.add(allerId);
        gauche.add(modification);
        gauche.add(ajout);
        gauche.setSize(frame.getWidth()/2, frame.getHeight()/2);
        
        droite.add(up, BorderLayout.NORTH);
        droite.add(go, BorderLayout.CENTER);
        droite.add(down, BorderLayout.SOUTH);
        droite.setSize(frame.getWidth()/2, frame.getHeight()/2);
        
        switch (utilisateur){
            case "professeur" :
                ajout.setEnabled(true);
                modification.setEnabled(true);                
                break;
            case "dresseur" :
                ajout.setEnabled(true);
                modification.setEnabled(false);                
                break;
            case "visiteur" :
                ajout.setEnabled(false);
                modification.setEnabled(false);
                break;
        }
        
        add(gauche);
        add(droite);
    }
}
