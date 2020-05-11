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
import javax.swing.JButton;
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
    static Color veryStrong = Color.GRAY;
    static Color strong = Color.DARK_GRAY;
    static Color weak = Color.MAGENTA;
    static Color veryWeak = Color.RED;
    static Color immune = Color.LIGHT_GRAY;
    static Color efficace = Color.BLACK;
    InfoButton typeName[], modificationType1, modificationType2;
    Label type1label, type2label, vs_type[], weakness;
    JComboBox<InfoButton> type1, type2;

    public TypePanel(Database db, MainPanel parent) {
        this.db = db;
        type1label = new Label("Type 1 : ", true);
        type2label = new Label("Type 2 : ", true);
        type1 = new JComboBox();
        type1.setBackground(Color.GRAY);
        type1.setForeground(Color.WHITE);
        type2 = new JComboBox();
        type2.setBackground(Color.GRAY);
        type2.setForeground(Color.WHITE);
        type2.addItem(new InfoButton("", 0));
        weakness = new Label("Faiblesse versus ...", true);
        typeName = new InfoButton[18];
        vs_type = new Label[18];
        modificationType1 = new InfoButton("Modifier le type 1", 0, true);
        modificationType2 = new InfoButton("Modifier le type 2", 0, true);

        ArrayList<Object[]> typeNames = db.getFromDB("SELECT id,name FROM type");
        for (int i = 0; i < 18; i++) {
            typeName[i] = new InfoButton((String) typeNames.get(i)[1], (Integer) typeNames.get(i)[0], true);
            typeName[i].addActionListener(parent);
            typeName[i].setActionCommand(Action.GET_TYPE.name());
            vs_type[i] = new Label("", true);
            type1.addItem(typeName[i]);
            type2.addItem(typeName[i]);
        }

        type1.setActionCommand(Action.GET_COMBINED_TYPE.name());
        type2.setActionCommand(Action.GET_COMBINED_TYPE.name());
        modificationType1.setActionCommand(Action.START_MODIFICATION_TYPE1.name());
        modificationType2.setActionCommand(Action.START_MODIFICATION_TYPE2.name());
        modificationType1.addActionListener(this);
        modificationType2.addActionListener(this);
        type1.addActionListener(this);
        type2.addActionListener(this);

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
        c.gridy++;
        c.gridx = 0;
        c.gridwidth=2;
        add(new Label(), c);
        c.gridy++;
        add(weakness, c);
        c.gridwidth=1;
        for (int i = 0; i < 18; i++) {
            c.gridx = 0;
            c.gridy++;
            add(typeName[i], c);
            c.gridx = 1;
            add(vs_type[i], c);
        }
        setId(1, 0);
        c.gridx = 0;
        c.gridy++;
        c.gridwidth = 2;
        add(new Label(), c);
        c.gridy++;
        c.gridwidth = 1;
        add(modificationType1, c);
        c.gridx++;
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

    public void setId(int id1, int id2) {
        ArrayList<Type> currentTypes = db.getFromDB("SELECT * FROM type WHERE id=" + String.valueOf(id1) + " OR id=" + String.valueOf(id2), Type.class);
        
        
        type1.setSelectedIndex(id1-1);
        type2.setSelectedIndex(id2);
        if (id2 == 0 || id2 == id1) {
            for (int i = 0; i < 18; i++) {
                vs_type[i].setText(weakToString(currentTypes.get(0).vs[i]));
            }
            modificationType2.setVisible(false);
        } else {
            for (int i = 0; i < 18; i++) {
                vs_type[i].setText(weakToString(currentTypes.get(0).vs[i] * currentTypes.get(1).vs[i]));
            }
            modificationType2.setVisible(true);
            modificationType2.setText("Modifier le type " + type2.getSelectedItem().toString());
        }
        modificationType1.setText("Modifier le type " + type1.getSelectedItem().toString());
        for (int i = 0; i < 18; i++) {
            String faiblesse = vs_type[i].getText();
            if (faiblesse.equals("Très Vulnérable")){
                vs_type[i].setForeground(veryWeak);
            } else if (faiblesse.equals("Vulnérable")) {
                vs_type[i].setForeground(weak);
            } else if (faiblesse.equals("Résistant")) {
                vs_type[i].setForeground(strong);
            } else if (faiblesse.equals("Très Résistant")) {
                vs_type[i].setForeground(veryStrong);
            } else if (faiblesse.equals("Immunisé")) {
                vs_type[i].setForeground(immune);
            } else if (faiblesse.equals("Efficace")){
                vs_type[i].setForeground(efficace);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (Action.valueOf(e.getActionCommand())) {
            case GET_COMBINED_TYPE :
                InfoButton typeButton1 = (InfoButton) type1.getSelectedItem();
                InfoButton typeButton2 = (InfoButton) type2.getSelectedItem();
                setId(typeButton1.getId(), typeButton2.getId());
                break;
            case NOUVEAU_TYPE :
                
                break;
            case START_MODIFICATION_TYPE1 :
                
                break;
            case START_MODIFICATION_TYPE2 :
                
                break;
            case SUPPRESSION_TYPE1 :
                
                break;
            case SUPPRESSION_TYPE2 :
                
                break;
        }
    }
}
