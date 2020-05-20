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
import pokedex.gui.Action;
import pokedex.gui.MainPanel;

/**
 *
 * @author Spectan
 */
public class AbilityModificationPanel extends JPanel implements ActionListener {

    JTextField name, enName;
    JTextArea descriptionFight, descriptionOutFight;
    JButton saveButton, discardButton;
    MainPanel parent;
    int idModif;

    public AbilityModificationPanel(int id, MainPanel p) {

        Ability currentAbility = p.db.getFromDB("SELECT * FROM ability WHERE id=" + id, Ability.class).get(0);

        idModif = id;
        parent = p;
        name = new JTextField(currentAbility.name);
        enName = new JTextField(currentAbility.en_name);

        descriptionFight = new JTextArea();
        descriptionFight.append(currentAbility.description[0]);
        descriptionFight.setLineWrap(true);
        descriptionFight.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        descriptionOutFight = new JTextArea();
        descriptionOutFight.append("" + currentAbility.description[1]);
        descriptionOutFight.setLineWrap(true);
        descriptionOutFight.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

        JPanel namePanel = new JPanel();
        namePanel.setBackground(Color.white);
        namePanel.setBorder(BorderFactory.createTitledBorder("Nom du talent"));
        namePanel.add(name);

        JPanel enNamePanel = new JPanel();
        enNamePanel.setBackground(Color.white);
        enNamePanel.setBorder(BorderFactory.createTitledBorder("Nom du talent anglais"));
        enNamePanel.add(enName);

        JPanel descriptionFightPanel = new JPanel();
        descriptionFightPanel.setBackground(Color.white);
        descriptionFightPanel.setBorder(BorderFactory.createTitledBorder("Description de l'effet en combat"));
        descriptionFightPanel.add(descriptionFight);

        JPanel descriptionOutPanel = new JPanel();
        descriptionOutPanel.setBackground(Color.white);
        descriptionOutPanel.setBorder(BorderFactory.createTitledBorder("Description de l'effet hors combat"));
        descriptionOutPanel.add(descriptionOutFight);

        saveButton = new JButton("SAVE");
        saveButton.setCursor(Cursor.getPredefinedCursor((Cursor.HAND_CURSOR)));
        saveButton.addActionListener(this);
        saveButton.setActionCommand(Action.SAVE_MODIFICATION.name());
        discardButton = new JButton("DISCARD");
        discardButton.setCursor(Cursor.getPredefinedCursor((Cursor.HAND_CURSOR)));
        discardButton.addActionListener(this);
        discardButton.setActionCommand(Action.DISCARD_MODIFICATION.name());

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        add(namePanel, c);
        c.gridx++;
        add(enNamePanel, c);
        c.gridx = 0;
        c.gridy++;
        c.gridwidth = 2;
        c.gridheight = 2;
        add(descriptionFightPanel, c);
        c.gridy = c.gridy + 2;
        add(descriptionOutPanel, c);
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
            case SAVE_MODIFICATION:

                new Ability(idModif, name.getText(), enName.getText(), descriptionFight.getText(),
                        descriptionOutFight.getText()).modifyInDB(parent.db);
                JOptionPane.showMessageDialog(null, "Modification sauvegardée", "Information", JOptionPane.INFORMATION_MESSAGE);
                parent.abilityPanel.setId(idModif);
                parent.removeTab(this, parent.abilityPanel, true);
                break;
            case DISCARD_MODIFICATION:
                parent.removeTab(this, parent.abilityPanel, false);
                break;
        }
    }

    private void setDimension() {
        int dimx = (parent.getWidth() / 2) - 40;
        int dimy = (parent.getHeight() / 6) - 60;
        name.setPreferredSize(new Dimension(dimx, dimy));
        enName.setPreferredSize(new Dimension(dimx, dimy));
        descriptionFight.setPreferredSize(new Dimension(dimx * 2, dimy * 2));
        descriptionOutFight.setPreferredSize(new Dimension(dimx * 2, dimy * 2));
    }
}
