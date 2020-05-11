/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex.gui.type;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import pokedex.database.Type;
import pokedex.gui.Action;
import pokedex.gui.InfoButton;
import pokedex.gui.MainPanel;

/**
 *
 * @author Spectan
 */
public class TypeModificationPanel extends JPanel implements ActionListener {

    JTextField name, enName;
    MainPanel parent;
    int idModif;
    JButton saveButton, discardButton;
    JComboBox<String>[] vsSelectors;
    JPanel panVs[];

    public TypeModificationPanel(int id, MainPanel p) {

        idModif = id;
        parent = p;
        this.initComponents();
        this.setVisible(true);

    }

    private void initComponents() {

        ArrayList<pokedex.database.Type> list = parent.db.getFromDB("SELECT * from type ORDER BY id ASC", pokedex.database.Type.class);
        pokedex.database.Type currentType = parent.db.getFromDB("SELECT * from type WHERE id = " + idModif, pokedex.database.Type.class).get(0);

        //Pour le nom français
        JPanel panName = new JPanel();
        panName.setBackground(Color.white);
        name = new JTextField(currentType.name);
        panName.setBorder(BorderFactory.createTitledBorder("Nom du type"));
        panName.add(name);

        //Pour le nom anglais
        JPanel panEnName = new JPanel();
        panEnName.setBackground(Color.white);
        enName = new JTextField(currentType.en_name);
        panEnName.setBorder(BorderFactory.createTitledBorder("Nom anglais du type"));
        panEnName.add(enName);

        String[] listFaiblesse = new String[]{"Immunisé", "Résistant", "Efficace", "Vulnérable"};

        //Pour les faiblesses dans l'ordre (même si le nom du type a été changé)
        vsSelectors = new JComboBox[18];
        panVs = new JPanel[18];
        for (int i = 0; i < 18; i++) {
            panVs[i] = new JPanel();
            panVs[i].setBackground(Color.white);
            vsSelectors[i] = new JComboBox<>(listFaiblesse);
            vsSelectors[i].setSelectedItem(faiblesseToString(currentType.vs[i]));
            panVs[i].setBorder(BorderFactory.createTitledBorder("Contre " + list.get(i).name));
            panVs[i].add(vsSelectors[i]);
        }


        saveButton = new JButton("SAVE");
        saveButton.setCursor(Cursor.getPredefinedCursor((Cursor.HAND_CURSOR)));
        saveButton.addActionListener(this);
        saveButton.setActionCommand(Action.SAVE_TYPE_MODIFICATION.name());
        discardButton = new JButton("DISCARD");
        discardButton.setCursor(Cursor.getPredefinedCursor((Cursor.HAND_CURSOR)));
        discardButton.addActionListener(this);
        discardButton.setActionCommand(Action.DISCARD_TYPE_MODIFICATION.name());

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.25;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 0;
        add(panName, c);
        c.gridx++;
        add(panEnName, c);
        c.gridx++;
        add(panVs[0], c);
        c.gridx++;
        add(panVs[1], c);
        c.gridx = 0;
        c.gridy++;
        add(panVs[2], c);
        c.gridx++;
        add(panVs[3], c);
        c.gridx++;
        add(panVs[4], c);
        c.gridx++;
        add(panVs[5], c);
        c.gridx = 0;
        c.gridy++;
        add(panVs[6], c);
        c.gridx++;
        add(panVs[7], c);
        c.gridx++;
        add(panVs[8], c);
        c.gridx++;
        add(panVs[9], c);
        c.gridx = 0;
        c.gridy++;
        add(panVs[10], c);
        c.gridx++;
        add(panVs[11], c);
        c.gridx++;
        add(panVs[12], c);
        c.gridx++;
        add(panVs[13], c);
        c.gridx = 0;
        c.gridy++;
        add(panVs[14], c);
        c.gridx++;
        add(panVs[15], c);
        c.gridx++;
        add(panVs[16], c);
        c.gridx++;
        add(panVs[17], c);
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy++;
        add(saveButton, c);
        c.gridx = 2;
        add(discardButton, c);

        updateDimension();
    }

    public void updateDimension() {
        int dimx = (parent.getWidth() / 3) - 70;
        int dimy = (parent.getHeight() / 7) - 60;
        name.setPreferredSize(new Dimension(dimx, dimy));
        enName.setPreferredSize(new Dimension(dimx, dimy));
        for (int i = 0; i < 18; i++) {
            vsSelectors[i].setPreferredSize(new Dimension(dimx, dimy));
        }
    }

    public String faiblesseToString(float valeurFaiblesse) {
        if (valeurFaiblesse == 2) {
            return "Vulnérable";
        } else if (valeurFaiblesse == 1) {
            return "Efficace";
        } else if (valeurFaiblesse == 0.5) {
            return "Résistant";
        } else if (valeurFaiblesse == 0) {
            return "Immunisé";
        }
        return "Efficace";
    }

    public float stringToFaiblesse(String value) {
        if (value.equals("Vulnérable")) {
            return 2;
        } else if (value.equals("Efficace")) {
            return 1;
        } else if (value.equals("Résistant")) {
            return 0.5f;
        } else if (value.equals("Immunisé")) {
            return 0;
        }
        return 1;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        InfoButton source;
        switch (Action.valueOf(e.getActionCommand())) {
            case SAVE_TYPE_MODIFICATION:
                float[] vs = new float[18];
                for (int i = 0; i < 18; i++) {
                    vs[i]=stringToFaiblesse(vsSelectors[i].getSelectedItem().toString());
                }
                new Type(idModif, name.getText(), enName.getText(), vs).modifyInDB(parent.db);
                
                JOptionPane.showMessageDialog(null, "Modification sauvegardée", "Information", JOptionPane.INFORMATION_MESSAGE);
                parent.tabbedPane.setSelectedComponent(parent.typePanel);
                parent.typePanel.update();

            case DISCARD_TYPE_MODIFICATION:
                parent.tabbedPane.remove(this);
                parent.tabbedPane.setSelectedComponent(parent.typePanel);
                break;
        }
    }
}
