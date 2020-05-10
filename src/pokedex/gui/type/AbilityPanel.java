/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex.gui.type;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import pokedex.database.Ability;
import pokedex.database.Database;
import pokedex.database.Type;
import pokedex.gui.Action;
import pokedex.gui.InfoButton;
import pokedex.gui.Label;
import pokedex.gui.MainPanel;

/**
 *
 * @author Leon
 */
public class AbilityPanel extends JPanel implements ActionListener {

    Database db;
    Label name, description1, description2;
    JComboBox<InfoButton> selector;

    public AbilityPanel(Database db, MainPanel parent) {
        super();
        this.db = db;
        name = new Label("Talent : ");
        description1 = new Label();
        description2 = new Label();
        selector = new JComboBox();

        InfoButton selectorButton;
        ArrayList<Object[]> abilityNames = db.getFromDB("SELECT id,name FROM ability");
        for (int i = 0; i < abilityNames.size(); i++) {
            selectorButton = new InfoButton((String) abilityNames.get(i)[1], (Integer) abilityNames.get(i)[0]);
            selector.addItem(selectorButton);
        }

        selector.setActionCommand(Action.GET_ABILITY.name());
        selector.addActionListener(this);

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        add(name, c);
        c.gridx = 1;
        add(selector, c);
        c.gridy++;
        c.gridx = 0;
        c.gridwidth = 2;
        add(description1, c);
        c.gridy++;
        add(description2, c);

        setId(1);
    }

    public void setId(int id) {
        Ability currentAbility = db.getFromDB("SELECT * FROM ability WHERE id=" + String.valueOf(id), Ability.class).get(0);

        //name.setText(currentAbility.name+" ("+currentAbility.en_name+")");
        selector.setSelectedIndex(id - 1);
        description1.setText(currentAbility.description[0]);
        description2.setText(currentAbility.description[1]);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        switch (Action.valueOf(e.getActionCommand())) {
            case GET_ABILITY:
                InfoButton abilityButton = (InfoButton) selector.getSelectedItem();
                setId(abilityButton.getId());
                break;
        }
    }
}
