/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex.gui.ability;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import pokedex.database.Ability;
import pokedex.database.Database;
import pokedex.gui.Action;
import pokedex.gui.widgets.InfoButton;
import pokedex.gui.widgets.Label;
import pokedex.gui.MainPanel;

/**
 *
 * @author Leon
 */
public class AbilityPanel extends JPanel implements ActionListener {

    Database db;
    Label title, description1, description2;
    JComboBox<InfoButton> selector;
    InfoButton modification;
    MainPanel parent;
    int currentId;

    public AbilityPanel(Database db, MainPanel p) {

        super();
        parent = p;
        this.db = db;
        title = new Label("Talent : ", true);
        description1 = new Label("", true);
        description1.setPreferredSize(new Dimension(1, 50));
        description2 = new Label("", true);
        description2.setPreferredSize(new Dimension(1, 50));
        selector = new JComboBox();
        selector.setBackground(Color.GRAY);
        selector.setForeground(Color.WHITE);
        
        Label blank = new Label("");

        InfoButton selectorButton;
        ArrayList<Object[]> abilityNames = db.getFromDB("SELECT id,name FROM ability ORDER by id ASC");
        for (int i = 0; i < abilityNames.size(); i++) {
            selectorButton = new InfoButton((String) abilityNames.get(i)[1], (Integer) abilityNames.get(i)[0]);
            selector.addItem(selectorButton);
        }

        modification = new InfoButton("Modifier le talent " + abilityNames.get(0)[1].toString(), 1, true);

        selector.setActionCommand(Action.GET_ABILITY.name());
        selector.addActionListener(this);
        modification.setActionCommand(Action.START_MODIFICATION.name());
        modification.addActionListener(this);

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        add(title, c);
        c.gridx = 1;
        add(selector, c);
        c.gridy++;
        c.gridx = 0;
        c.gridwidth = 2;
        add(description1, c);
        c.gridy++;
        add(description2, c);
        c.gridy++;
        add(blank, c);
        c.gridy++;
        add(modification, c);

        setId(1);
    }

    public void setId(int id) {
        Ability currentAbility = db.getFromDB("SELECT * FROM ability WHERE id=" + id, Ability.class).get(0);

        //name.setText(currentAbility.title+" ("+currentAbility.en_name+")");
        selector.setSelectedIndex(id - 1);
        description1.setText("<html>" + currentAbility.description[0] + "</html>");
        description2.setText("<html>" + currentAbility.description[1] + "</html>");
        modification.setText("Modifier le talent " + currentAbility.name);
        modification.setId(id);
        currentId=id;
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

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (Action.valueOf(e.getActionCommand())) {
            case GET_ABILITY:
                InfoButton abilityButton = (InfoButton) selector.getSelectedItem();
                setId(abilityButton.getId());
                break;
            case START_MODIFICATION:
                parent.addTab(
                        new AbilityModificationPanel(modification.getId(), parent),
                        "Modification de " + db.getFromDB("SELECT name FROM ability WHERE id=" + modification.getId()).get(0)[0], 1
                );
                break;
        }
    }
    
    public void update(){
        setId(currentId);
    }
}
