/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex.gui.move;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JTextField;
import pokedex.database.Move;
import pokedex.gui.Action;
import pokedex.gui.MainPanel;

/**
 *
 * @author Leon
 */
public class MoveModificationPanel extends JPanel implements ActionListener, ComponentListener {
    
    
    JTextField name, enName;
    JComboBox<String> type, category;
    JComboBox pp, accuracy;
    JFormattedTextField power;
    MainPanel parent;
    JButton saveButton, discardButton;
    int idModif;
    
    public MoveModificationPanel(int id, MainPanel p) {
        
        idModif = id;
        parent = p;
        this.setSize(new Dimension(parent.getWidth(), parent.getHeight()));
        this.initComponents();
        this.setVisible(true);
        addComponentListener(this);
    }

    private void initComponents() {
        
        ArrayList<pokedex.database.Type> list = parent.db.getFromDB("SELECT * from type ORDER BY id ASC", pokedex.database.Type.class);
        String[] listType = new String[list.size()];
        for (int i = 0; i < list.size(); i++){
            listType[i] = list.get(i).name; 
        }
        Move currentMove = parent.db.getFromDB("SELECT * from move WHERE id = " + idModif, Move.class).get(0);
        
        //Le nom français
        JPanel namePanel = new JPanel();
        namePanel.setBackground(Color.white);
        name = new JTextField(currentMove.name);
        namePanel.setBorder(BorderFactory.createTitledBorder("Nom de la capacité"));
        namePanel.add(name);
        
        //Le nom anglais
        JPanel enNamePanel = new JPanel();
        enNamePanel.setBackground(Color.white);
        enName = new JTextField(currentMove.en_name);
        enNamePanel.setBorder(BorderFactory.createTitledBorder("Nom anglais de la capacité"));
        enNamePanel.add(enName);
        
        //Le type de la capacité
        JPanel typePanel = new JPanel();
        typePanel.setBackground(Color.white);
        type = new JComboBox<>(listType);
        type.setSelectedItem(listType[currentMove.id_type - 1]);
        typePanel.setBorder(BorderFactory.createTitledBorder("Type de la capacité"));
        typePanel.add(type);
        
        String[] listCategory = new String[]{"Physique", "Spéciale", "Statut"};
        
        //Catégorie de la capacité
        JPanel categoryPanel = new JPanel();
        categoryPanel.setBackground(Color.white);
        category = new JComboBox<>(listCategory);
        category.setSelectedItem(currentMove.category);
        categoryPanel.setBorder(BorderFactory.createTitledBorder("Catégorie de la capacité"));
        categoryPanel.add(category);
        
        Object[] possibilitePP = new Object[]{0,5,10,15,20,25,30,35,40};
        
        //PP de la capacité
        JPanel ppPanel = new JPanel();
        ppPanel.setBackground(Color.white);
        pp = new JComboBox<>(possibilitePP);
        pp.setSelectedItem(currentMove.pp);
        ppPanel.setBorder(BorderFactory.createTitledBorder("PP de base de la capacité"));
        ppPanel.add(pp);
        
        NumberFormat formatPuissance = NumberFormat.getPercentInstance();
        formatPuissance.setMaximumFractionDigits(0);
        
        //Puissance de la capacité
        JPanel powerPanel = new JPanel();
        powerPanel.setBackground(Color.white);
        power = new JFormattedTextField(formatPuissance);
        if (currentMove.power == -1){
            power.setValue(0);
        } else {
            power.setValue(currentMove.power);
        }
        powerPanel.setBorder(BorderFactory.createTitledBorder("Puissance de la capacité"));
        powerPanel.add(power);
        
        Object[] possibiliteAccuracy = new Object[]{"Ne rate jamais", 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90, 95, 100};
        
        //Précision de la capacité
        JPanel accuracyPanel = new JPanel();
        accuracyPanel.setBackground(Color.white);
        accuracy = new JComboBox<>(possibiliteAccuracy);
        if (currentMove.accuracy == -1){
            accuracy.setSelectedItem(possibiliteAccuracy[0]);
        } else {
            accuracy.setSelectedItem(currentMove.accuracy * 100);    
        }
        accuracyPanel.setBorder(BorderFactory.createTitledBorder("Précision de la capacité"));
        accuracyPanel.add(accuracy);
        
        saveButton = new JButton("SAVE");
        saveButton.setCursor(Cursor.getPredefinedCursor((Cursor.HAND_CURSOR)));
        saveButton.addActionListener(this);
        saveButton.setActionCommand(Action.SAVE_MODIFICATION.name());
        discardButton = new JButton("DISCARD");
        discardButton.setCursor(Cursor.getPredefinedCursor((Cursor.HAND_CURSOR)));
        discardButton.addActionListener(this);
        discardButton.setActionCommand(Action.DISCARD_MODIFICATION.name());

        JPanel savePanel = new JPanel();
        savePanel.add(saveButton);
        savePanel.setBackground(Color.white);
        JPanel discardPanel = new JPanel();
        discardPanel.add(discardButton);
        discardPanel.setBackground(Color.white);
        
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        setBackground(Color.white);
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.33;
        c.weighty = 0.33;
        add(namePanel, c);
        c.gridx++;
        add(enNamePanel, c);
        c.gridx++;
        add(typePanel, c);
        c.gridx = 0;
        c.gridy++;
        add(categoryPanel, c);
        c.gridx++;
        add(ppPanel, c);
        c.gridx++;
        add(powerPanel, c);
        c.gridx = 0;
        c.gridy++;
        add(savePanel, c);
        c.gridx++;
        add(accuracyPanel, c);
        c.gridx++;
        add(discardPanel, c);
        
        updateDimension();
    }
    
    public void updateDimension() {
        int dimx = (this.getWidth() / 3) - 60;
        int dimy = (this.getHeight() / 4) - 70;
        name.setPreferredSize(new Dimension(dimx, dimy));
        enName.setPreferredSize(new Dimension(dimx, dimy));
        type.setPreferredSize(new Dimension(dimx, dimy));
        category.setPreferredSize(new Dimension(dimx, dimy));
        pp.setPreferredSize(new Dimension(dimx, dimy));
        power.setPreferredSize(new Dimension(dimx, dimy));
        accuracy.setPreferredSize(new Dimension(dimx, dimy));
        saveButton.setPreferredSize(new Dimension(dimx + 20, (int) (dimy * 1.5)));
        discardButton.setPreferredSize(new Dimension(dimx + 20, (int) (dimy * 1.5)));
        
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
    
    @Override
    public void componentResized(ComponentEvent arg0) {
        updateDimension();
    }

    @Override
    public void componentMoved(ComponentEvent arg0) {
    }

    @Override
    public void componentShown(ComponentEvent arg0) {
    }

    @Override
    public void componentHidden(ComponentEvent arg0) {
    }
}
