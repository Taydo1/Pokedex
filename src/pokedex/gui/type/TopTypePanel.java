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
    Label name, vs_type[];

    public TopTypePanel(Database db, TypePanel parent) {
        name = new Label();
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
        }

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.gridwidth = 2;
        c.gridy = 0;
        add(name, c);
        c.gridwidth = 1;
        for (int i = 0; i < 18; i++) {
            c.gridx = 0;
            c.gridy++;
            add(typeName[i], c);
            c.gridx = 1;
            add(vs_type[i], c);
        }
        setId(5,6, db);
    }

    public String weakToString(float weakness) {
        if (weakness == 4) {
            return "Très Vulnérable";
        } else if (weakness == 2) {
            return "Vulnérable";
        } else if (weakness == 1) {
            return "Classique";
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
        if (id2 != 0) {
            typeName+=" - "+currentTypes.get(1).name + " (" + currentTypes.get(1).en_name + ")";
        }
        name.setText(typeName);
        for (int i = 0; i < 18; i++) {
            vs_type[i].setText(weakToString(currentTypes.get(0).vs[i]* currentTypes.get(1).vs[i]));
        }
    }
}
