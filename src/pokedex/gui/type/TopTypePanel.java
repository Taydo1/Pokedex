/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex.gui.type;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import pokedex.database.Database;
import pokedex.database.Type;
import pokedex.gui.*;

/**
 *
 * @author Leon
 */
public class TopTypePanel extends JPanel {

    static Color veryStrong = Color.GREEN;
    static Color strong = Color.CYAN;
    static Color weak = Color.MAGENTA;
    static Color veryWeak = Color.RED;
    InfoButton typeName[];
    Label type1label, type2label, vs_type[];
    JComboBox<InfoButton> type1, type2;

    public TopTypePanel(Database db, TypePanel parent) {
        type1label = new Label("Type 1 : ");
        type2label = new Label("Type 2 : ");
        type1 = new JComboBox();
        type2 = new JComboBox();
        type2.addItem(new InfoButton("", 0));
        typeName = new InfoButton[18];
        vs_type = new Label[18];

        ArrayList<Object[]> typeNames = db.getFromDB("SELECT id,name FROM type");
        for (int i = 0; i < 18; i++) {
            typeName[i] = new InfoButton();
            typeName[i].setText((String) typeNames.get(i)[1]);
            typeName[i].setId((Integer) typeNames.get(i)[0]);
            typeName[i].setActionCommand(Action.GO.name());
            typeName[i].addActionListener(parent);
            vs_type[i] = new Label();
            type1.addItem(typeName[i]);
            type2.addItem(typeName[i]);
        }

        type1.setActionCommand(Action.GO_COMBINED_TYPE.name());
        type2.setActionCommand(Action.GO_COMBINED_TYPE.name());
        type1.addActionListener(parent);
        type2.addActionListener(parent);

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.gridy = 0;
        c.gridx = 0;
        add(type1label, c);
        c.gridx++;
        add(type1, c);
        c.gridy++;
        c.gridx = 0;
        add(type2label, c);
        c.gridx++;
        add(type2, c);
        for (int i = 0; i < 18; i++) {
            c.gridx = 0;
            c.gridy++;
            add(typeName[i], c);
            c.gridx = 1;
            add(vs_type[i], c);
        }
        setId(1,0, db);
    }

    public String weakToString(float weakness) {
        if (weakness == 4) {
            return "Très Vulnérable";
        } else if (weakness == 2) {
            return "Vulnérable";
        } else if (weakness == 1) {
            return "Classique ??????????";
        } else if (weakness == 0.5) {
            return "Résistant";
        } else if (weakness == 0.25) {
            return "Très Résistant";
        } else {
            return "Immunisé";
        }

    }

    public void setId(int id1, int id2, Database db) {
        ArrayList<Type> currentTypes = db.getFromDB("SELECT * FROM type WHERE id=" + String.valueOf(id1) + " OR id=" + String.valueOf(id2), Type.class);

        String typeName = currentTypes.get(0).name + " (" + currentTypes.get(0).en_name + ")";
        if (id2 == 0 || id2==id1) {
            for (int i = 0; i < 18; i++) {
                vs_type[i].setText(weakToString(currentTypes.get(0).vs[i]));
            }
        } else {
            for (int i = 0; i < 18; i++) {
                vs_type[i].setText(weakToString(currentTypes.get(0).vs[i] * currentTypes.get(1).vs[i]));
            }
        }
    }
}
