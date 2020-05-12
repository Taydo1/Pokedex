/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex.gui.ability;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import pokedex.database.Ability;
import pokedex.database.Pokedex;
import pokedex.gui.Action;
import pokedex.gui.InfoButton;
import pokedex.gui.MainPanel;

/**
 *
 * @author Spectan
 */
public class AbilityModificationPanel extends JPanel implements ActionListener {
    
    JTextField name, enName;
    JTextArea descriptionCombat, descriptionHors;
    JButton saveButton, discardButton;
    MainPanel parent;
    int idModif;
    
    public AbilityModificationPanel (int id, MainPanel p){
        
        Ability currentAbility = p.db.getFromDB("SELECT * FROM ability WHERE id=" + id, Ability.class).get(0);
        
        idModif = id;
        parent = p;
        name = new JTextField(currentAbility.name);
        enName = new JTextField(currentAbility.en_name);
        
        descriptionCombat = new JTextArea();
        descriptionCombat.append(currentAbility.description[0]);
        descriptionCombat.setLineWrap(true);
        descriptionCombat.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        descriptionHors = new JTextArea();
        descriptionHors.append("" + currentAbility.description[1]);
        descriptionHors.setLineWrap(true);
        descriptionHors.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        
        JPanel panNomfr = new JPanel();
        panNomfr.setBackground(Color.white);
        panNomfr.setBorder(BorderFactory.createTitledBorder("Nom du talent"));
        panNomfr.add(name);
        
        JPanel panNomen = new JPanel();
        panNomen.setBackground(Color.white);
        panNomen.setBorder(BorderFactory.createTitledBorder("Nom du talent anglais"));
        panNomen.add(enName);
        
        JPanel panDescriptionCombat = new JPanel();
        panDescriptionCombat.setBackground(Color.white);
        panDescriptionCombat.setBorder(BorderFactory.createTitledBorder("Description de l'effet en combat"));
        panDescriptionCombat.add(descriptionCombat);
        
        JPanel panDescriptionHors = new JPanel();
        panDescriptionHors.setBackground(Color.white);
        panDescriptionHors.setBorder(BorderFactory.createTitledBorder("Description de l'effet hors combat"));
        panDescriptionHors.add(descriptionHors);
        
        saveButton = new JButton("SAVE");
        saveButton.setCursor(Cursor.getPredefinedCursor((Cursor.HAND_CURSOR)));
        saveButton.addActionListener(this);
        saveButton.setActionCommand(Action.SAVE_ABILITY_MODIFICATION.name());
        discardButton = new JButton("DISCARD");
        discardButton.setCursor(Cursor.getPredefinedCursor((Cursor.HAND_CURSOR)));
        discardButton.addActionListener(this);
        discardButton.setActionCommand(Action.DISCARD_ABILITY_MODIFICATION.name());
        
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        add(panNomfr, c);
        c.gridx++;
        add(panNomen, c);
        c.gridx = 0;
        c.gridy++;
        c.gridwidth = 2;
        c.gridheight = 2;
        add(panDescriptionCombat, c);
        c.gridy = c.gridy + 2;
        add(panDescriptionHors, c);
        c.gridy = c.gridy + 2;
        c.gridwidth = 1;
        add(saveButton, c);
        c.gridx++;
        add(discardButton, c);
        
        setDimension();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        switch (Action.valueOf(e.getActionCommand())) {
            case SAVE_ABILITY_MODIFICATION:
                
                new Ability(idModif, name.getText(), enName.getText(), descriptionCombat.getText(),
                        descriptionHors.getText()).modifyInDB(parent.db);
                JOptionPane.showMessageDialog(null, "Modification sauvegardée", "Information", JOptionPane.INFORMATION_MESSAGE);
                parent.abilityPanel.setId(idModif);

            case DISCARD_ABILITY_MODIFICATION:
                parent.removeTab(this, parent.abilityPanel);
                break;
        }
    }

    private void setDimension() {
        int dimx = (parent.getWidth() / 2) - 40;
        int dimy = (parent.getHeight() / 6) - 60;
        name.setPreferredSize(new Dimension(dimx, dimy));
        enName.setPreferredSize(new Dimension(dimx, dimy));
        descriptionCombat.setPreferredSize(new Dimension(dimx * 2, dimy * 2));
        descriptionHors.setPreferredSize(new Dimension(dimx * 2, dimy * 2));
    }
}
