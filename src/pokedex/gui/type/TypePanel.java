/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex.gui.type;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import pokedex.database.*;
import pokedex.gui.*;

/**
 *
 * @author Leon
 */
public class TypePanel extends JPanel implements ActionListener {

    Database db;
    MainPanel parent;
    static Color veryStrong = Color.GRAY;
    static Color strong = Color.DARK_GRAY;
    static Color weak = Color.MAGENTA;
    static Color veryWeak = Color.RED;
    static Color immune = Color.LIGHT_GRAY;
    static Color efficace = Color.BLACK;
    InfoButton names[], modificationType1, modificationType2;
    Label type1label, type2label, vs_type[], weakness;
    JComboBox<InfoButton> type1selector, type2selector;

    public TypePanel(Database db, MainPanel p) {

        parent = p;
        this.db = db;
        type1label = new Label("Type 1 : ", true);
        type2label = new Label("Type 2 : ", true);
        type1selector = new JComboBox<>();
        type1selector.setBackground(Color.GRAY);
        type1selector.setForeground(Color.WHITE);
        type1selector.setPreferredSize(new Dimension(1, 20));
        type2selector = new JComboBox<>();
        type2selector.setBackground(Color.GRAY);
        type2selector.setForeground(Color.WHITE);
        type2selector.setPreferredSize(new Dimension(1, 20));
        type2selector.addItem(new InfoButton("", 0));
        weakness = new Label("Faiblesse versus ...", true);
        names = new InfoButton[18];
        vs_type = new Label[18];
        modificationType1 = new InfoButton("Modifier le type 1", 0, true);
        modificationType2 = new InfoButton("Modifier le type 2", 0, true);

        ArrayList<Object[]> typeNames = db.getFromDB("SELECT id,name FROM type ORDER BY id ASC");
        for (int i = 0; i < 18; i++) {
            names[i] = new InfoButton("", 0, true);
            names[i].addActionListener(parent);
            names[i].setActionCommand(Action.GET_TYPE.name());
            vs_type[i] = new Label("", true);
            type1selector.addItem(names[i]);
            type2selector.addItem(names[i]);
        }
        this.setNames();

        type1selector.setActionCommand(Action.GET_COMBINED_TYPE.name());
        type2selector.setActionCommand(Action.GET_COMBINED_TYPE.name());
        modificationType1.setActionCommand(Action.START_TYPE_MODIFICATION.name());
        modificationType2.setActionCommand(Action.START_TYPE_MODIFICATION.name());
        modificationType1.addActionListener(this);
        modificationType2.addActionListener(this);
        type1selector.addActionListener(this);
        type2selector.addActionListener(this);

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.gridy = 0;
        c.gridx = 0;
        add(type1label, c);
        c.gridx++;
        add(type1selector, c);
        c.gridy++;
        c.gridx = 0;
        add(type2label, c);
        c.gridx++;
        add(type2selector, c);
        c.gridy++;
        c.gridx = 0;
        c.gridwidth = 2;
        add(new Label(), c);
        c.gridy++;
        add(weakness, c);
        c.gridwidth = 1;
        for (int i = 0; i < 18; i++) {
            c.gridx = 0;
            c.gridy++;
            add(names[i], c);
            c.gridx = 1;
            add(vs_type[i], c);
        }
        setId(1, 0);
        c.gridx = 0;
        c.gridy++;
        c.gridwidth = 2;
        add(new Label(), c);
        c.gridy++;
        add(modificationType1, c);
        c.gridy++;
        add(modificationType2, c);
    }

    public String weakToString(float weakness) {
        if (weakness == 4) {
            return "Très Vulnérable";
        } else if (weakness == 2) {
            return "Vulnérable";
        } else if (weakness == 1) {
            return "Efficace";
        } else if (weakness == 0.5) {
            return "Résistant";
        } else if (weakness == 0.25) {
            return "Très Résistant";
        } else {
            return "Immunisé";
        }

    }

    public void setNames() {
        ArrayList<Object[]> typeNames = db.getFromDB("SELECT id,name FROM type ORDER BY id ASC");
        for (int i = 0; i < 18; i++) {
            names[i].setText((String) typeNames.get(i)[1]);
            names[i].setId((Integer) typeNames.get(i)[0]);
        }
    }

    public void setId(int id1, int id2) {
        Type type1 = db.getFromDB("SELECT * FROM type WHERE id=" + String.valueOf(id1), Type.class).get(0);

        type1selector.setSelectedIndex(id1 - 1);
        type2selector.setSelectedIndex(id2);
        if (id2 == 0 || id2 == id1) {
            for (int i = 0; i < 18; i++) {
                vs_type[i].setText(weakToString(type1.vs[i]));
            }
            modificationType2.setVisible(false);
        } else {
            Type type2 = db.getFromDB("SELECT * FROM type WHERE id=" + String.valueOf(id2), Type.class).get(0);
            for (int i = 0; i < 18; i++) {
                vs_type[i].setText(weakToString(type1.vs[i] * type2.vs[i]));
            }
            modificationType2.setVisible(true);
            modificationType2.setText("Modifier le type " + type2.name);
            modificationType2.setId(id2);
        }
        modificationType1.setText("Modifier le type " + type1.name);
        modificationType1.setId(id1);
        for (int i = 0; i < 18; i++) {
            String faiblesse = vs_type[i].getText();
            if (faiblesse.equals("Très Vulnérable")) {
                vs_type[i].setForeground(veryWeak);
            } else if (faiblesse.equals("Vulnérable")) {
                vs_type[i].setForeground(weak);
            } else if (faiblesse.equals("Résistant")) {
                vs_type[i].setForeground(strong);
            } else if (faiblesse.equals("Très Résistant")) {
                vs_type[i].setForeground(veryStrong);
            } else if (faiblesse.equals("Immunisé")) {
                vs_type[i].setForeground(immune);
            } else if (faiblesse.equals("Efficace")) {
                vs_type[i].setForeground(efficace);
            }
        }
        parent.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        InfoButton typeButton1 = (InfoButton) type1selector.getSelectedItem();
        InfoButton typeButton2 = (InfoButton) type2selector.getSelectedItem();
        switch (Action.valueOf(e.getActionCommand())) {
            case GET_COMBINED_TYPE:
                setId(typeButton1.getId(), typeButton2.getId());
                break;
            case START_TYPE_MODIFICATION:
                InfoButton source = (InfoButton) e.getSource();
                String typeName = (String) db.getFromDB("SELECT name FROM type WHERE id=" + source.getId()).get(0)[0];
                parent.addTab(new TypeModificationPanel(source.getId(), parent), "Modification du type " + typeName, parent.PROFESSOR_TAB);
                break;
        }
    }

    void update() {
        InfoButton typeButton1 = (InfoButton) type1selector.getSelectedItem();
        InfoButton typeButton2 = (InfoButton) type2selector.getSelectedItem();
        this.setId(typeButton1.getId(), typeButton2.getId());
        this.setNames();
        this.repaint();
        this.revalidate();
    }
}
