/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex.gui.pokedex;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import pokedex.gui.*;
import pokedex.database.*;

/**
 *
 * @author Quentin
 */
public class PokedexBottomPanel extends JPanel {
    
    JPanel left, right, goIdPanel, goNamePanel;
    Label currentId, goIdLabel, goNameLabel;
    JButton up, down;
    StyledButton goIdButton, modification, goNameButton;
    JTextField goId, goName;
    
    public PokedexBottomPanel(String utilisateur, PokedexPanel parent) {
        
        left = new JPanel();
        right = new JPanel();
        goIdPanel = new JPanel();
        goIdPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        currentId = new Label("");
        goIdLabel = new Label("Aller à l'ID : ");
        goIdLabel.setBorder(null);
        up = new JButton(new ImageIcon(getClass().getResource("/images/icones/fleche_haut.png")));
        up.setContentAreaFilled(false);
        up.setCursor(Cursor.getPredefinedCursor((Cursor.HAND_CURSOR)));
        down = new JButton(new ImageIcon(getClass().getResource("/images/icones/fleche_bas.png")));
        down.setContentAreaFilled(false);
        down.setCursor(Cursor.getPredefinedCursor((Cursor.HAND_CURSOR)));
        goIdButton = new StyledButton("GO to ID");
        goIdButton.setPreferredSize(new Dimension(1, 35));
        goIdButton.setBorder(null);
        
        modification = new StyledButton("");
        modification.addActionListener(parent);
        modification.setActionCommand(Action.START_POKEDEX_MODIFICATION.name());
        goId = new JTextField();
        goId.setBackground(Color.gray);
        goId.setForeground(Color.white);
        goId.setColumns(3);
        goNamePanel = new JPanel();
        goNamePanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        goName = new JTextField();
        goName.setBackground(Color.gray);
        goName.setForeground(Color.white);
        goName.setColumns(3);
        goNameLabel = new Label("Search by name:");
        goNameLabel.setBorder(null);
        goNameButton = new StyledButton("Search name");
        goNameButton.setBorder(null);
        goNameButton.setPreferredSize(new Dimension(1, 35));
        goIdButton.addActionListener(parent);
        goId.addActionListener(parent);
        up.addActionListener(parent);
        down.addActionListener(parent);
        goIdButton.setActionCommand(Action.GO.name());
        goId.setActionCommand(Action.GO.name());
        up.setActionCommand(Action.UP.name());
        down.setActionCommand(Action.DOWN.name());
        goName.addActionListener(parent);
        goName.setActionCommand(Action.GO_NOM.name());
        goNameButton.addActionListener(parent);
        goNameButton.setActionCommand(Action.GO_NOM.name());
        
        left.setLayout(new GridLayout(0, 1));
        left.setBackground(Color.gray);
        right.setLayout(new BorderLayout());
        right.setBackground(Color.gray);
        goIdPanel.setLayout(new GridBagLayout());
        goIdPanel.setBackground(Color.gray);
        goNamePanel.setLayout(new GridBagLayout());
        goNamePanel.setBackground(Color.gray);
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.4;
        goIdPanel.add(goIdLabel, c);
        c.gridx = 1;
        c.weightx = 0.2;
        goIdPanel.add(goId, c);
        c.gridx = 2;
        c.weightx = 0.4;
        goIdPanel.add(goIdButton, c);
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.4;
        goNamePanel.add(goNameLabel, c);
        c.gridx = 1;
        c.weightx = 0.4;
        goNamePanel.add(goName, c);
        c.gridx = 2;
        c.weightx = 0.4;
        goNamePanel.add(goNameButton, c);
        
        left.add(goIdPanel);
        left.add(goNamePanel);
        left.add(modification);
        left.setSize((int) (parent.getWidth() * 0.7), 1);
        
        right.add(up, BorderLayout.NORTH);
        right.add(currentId, BorderLayout.CENTER);
        right.add(down, BorderLayout.SOUTH);
        right.setSize((int) (parent.getWidth() * 0.3), 1);
        
        setUser(utilisateur);
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.weightx = 0.7;
        add(left, c);
        c.gridx = 1;
        c.weightx = 0.3;
        add(right, c);
    }
    
    public void setUser(String user) {
        switch (user.toLowerCase()) {
            case "professeur":
                modification.setEnabled(true);
                break;
            case "dresseur":
            case "visiteur":
                modification.setEnabled(false);
                break;
        }
    }
    
    public void setId(int id, Database db) {
        String name = (String) db.getFromDB("SELECT name FROM pokedex WHERE id=" + String.valueOf(id)).get(0)[0];
        currentId.setText("ID actuel : " + String.valueOf(id));
        modification.setText("Modifier les données sur " + name);
        ArrayList<Object[]> nb_pokemon = db.getFromDB("SELECT id FROM pokedex");
        if (id == 1) {
            down.setEnabled(false);
            up.setEnabled(true);
        } else if (id == nb_pokemon.size()) {
            down.setEnabled(true);
            up.setEnabled(false);
        } else {
            down.setEnabled(true);
            up.setEnabled(true);
        }
    }
    
    public int getGoId() throws NumberFormatException {
        return Integer.parseInt(goId.getText());
    }
    
    public void clearGoId() {
        goId.setText("");
    }
    
    public int getIDFromNom(Database db) {
        int id = (int) db.getFromDB("SELECT id FROM pokedex WHERE UPPER(name) LIKE UPPER('" + goName.getText() + "') ORDER BY id ASC").get(0)[0];
        System.out.println("ID récupéré : " + id);
        return id;
    }
    
    public void clearGoNom() {
        goName.setText("");
    }
}
