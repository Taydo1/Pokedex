/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex.gui.move;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import pokedex.database.Database;
import pokedex.database.Move;
import pokedex.gui.Action;
import pokedex.gui.widgets.InfoButton;
import pokedex.gui.widgets.Label;
import pokedex.gui.MainPanel;

/**
 *
 * @author Leon
 */
public class MovePanel extends JPanel implements ActionListener {

    Database db;
    Label title, category, pp, power, accuracy;
    JComboBox<InfoButton> selector;
    InfoButton type, modification;
    MainPanel parent;
    int idActuel;

    public MovePanel(Database db, MainPanel parent) {

        super();
        this.parent = parent;
        this.db = db;
        title = new Label("Capacité : ", true);
        category = new Label("", true);
        pp = new Label("", true);
        power = new Label("", true);
        accuracy = new Label("", true);
        Label blank = new Label("");

        type = new InfoButton("", 0, true);
        modification = new InfoButton("", 0, true);

        selector = new JComboBox();
        selector.setBackground(Color.GRAY);
        selector.setForeground(Color.WHITE);

        InfoButton selectorButton;
        ArrayList<Object[]> typesNames = db.getFromDB("SELECT id,name FROM move ORDER by name ASC");
        for (int i = 0; i < typesNames.size(); i++) {
            selectorButton = new InfoButton((String) typesNames.get(i)[1], (Integer) typesNames.get(i)[0]);
            selector.addItem(selectorButton);
        }

        selector.setActionCommand(Action.GET_MOVE.name());
        selector.addActionListener(this);
        type.setActionCommand(Action.GET_TYPE.name());
        type.addActionListener(parent);
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
        add(type, c);
        c.gridy++;
        add(category, c);
        c.gridy = c.gridy + 3;
        add(pp, c);
        c.gridy++;
        add(power, c);
        c.gridy++;
        add(accuracy, c);
        c.gridy++;
        add(blank, c);
        c.gridy++;
        add(modification, c);

        InfoButton firstButton = selector.getItemAt(0);
        setId(firstButton.getId());
    }

    private int findSelectorId(int id) {
        for (int i = 0; i < selector.getItemCount(); i++) {
            InfoButton item = selector.getItemAt(i);
            if (item.getId() == id) {
                return i;
            }
        }
        return -1;
    }

    public void setId(int id) {
        Move currentMove = db.getFromDB("SELECT * FROM move WHERE id=" + id, Move.class).get(0);
        
        idActuel = id;

        //name.setText(currentAbility.title+" ("+currentAbility.en_name+")");
        selector.setSelectedIndex(findSelectorId(id));
        if (currentMove.category.equals("Statut")) {
            category.setText("Capacité de " + currentMove.category.toLowerCase());
        } else {
            category.setText("Capacité " + currentMove.category.toLowerCase());
        }
        pp.setText("PP : " + currentMove.pp);

        if (currentMove.power != -1) {
            power.setVisible(true);
            power.setText("Puissance : " + currentMove.power);
        } else {
            power.setVisible(false);
        }
        
        if (currentMove.accuracy != -1) {
            accuracy.setText("Précision : " + currentMove.accuracy*100+"%");
        } else {
            accuracy.setText("Ne peut pas manquer sa cible");
        }
        
        type.setId(currentMove.id_type);
        type.setText("Type : " + currentMove.getTypeName(db));
        
        modification.setText("Modifier la capacité "+currentMove.name);
        modification.setId(id);
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
            case GET_MOVE:
                InfoButton abilityButton = (InfoButton) selector.getSelectedItem();
                setId(abilityButton.getId());
                break;
            case START_MODIFICATION:
                parent.addTab(
                        new MoveModificationPanel(modification.getId(), parent),
                        "Modification de " + db.getFromDB("SELECT name FROM move WHERE id=" + modification.getId()).get(0)[0], 1
                );
                break;
        }
    }
}
