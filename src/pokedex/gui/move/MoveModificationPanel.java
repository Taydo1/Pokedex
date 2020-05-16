/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex.gui.move;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JTextField;
import pokedex.database.Move;
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
        String[] listType = new String[]{};
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
        JPanel ability1Panel = new JPanel();
        ability1Panel.setBackground(Color.white);
        type = new JComboBox<>(listType);
        type.setSelectedItem(listType[currentMove.id_type]);
        ability1Panel.setBorder(BorderFactory.createTitledBorder("Type de la capacité"));
        ability1Panel.add(type);
    }
    
    public void updateDimension() {
        int dimx = (this.getWidth() / 4) - 60;
        int dimy = (this.getHeight() / 7) - 70;
        name.setPreferredSize(new Dimension(dimx, dimy));
        enName.setPreferredSize(new Dimension(dimx, dimy));
        
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
