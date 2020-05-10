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
    Label nom, vs_type[];

    public TopTypePanel(Database db, TypePanel parent) {
        nom = new Label();
        typeName = new InfoButton[18];
        vs_type = new Label[18];

        ArrayList<Object[]> typeNames = db.getFromDB("SELECT id,name FROM type");

        for (int i = 0; i < 18; i++) {
            typeName[i] = new InfoButton();
            typeName[i].setText((String) typeNames.get(i)[1]);
            typeName[i].setId((Integer)typeNames.get(i)[0]);
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
        add(nom, c);
        c.gridwidth = 1;
        for (int i = 0; i < 18; i++) {
            c.gridx = 0;
            c.gridy++;
            add(typeName[i], c);
            c.gridx = 1;
            add(vs_type[i], c);
        }
        setId(1, db);
    }

    public void setId(int id, Database db) {
        Type currentType = db.getFromDB("SELECT * FROM type WHERE id=" + String.valueOf(id), Type.class).get(0);
        nom.setText(currentType.name + " (" + currentType.en_name + ")");
        for (int i = 0; i < 18; i++) {
            vs_type[i].setText(String.format("%.2f", currentType.vs[i]));
        }
    }
}
