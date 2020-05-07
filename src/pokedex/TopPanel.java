/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JPanel;

/**
 *
 * @author Spectan
 */
public class TopPanel extends JPanel{
    
    JComboBox choix;
    PokedexApp parent;
    
    public TopPanel(PokedexApp main){
        
        parent = main;
        String[] personnes = new String[]{"Visiteur" ,"Dresseur", "Professeur"};
        choix = new JComboBox<String>(personnes);
        choix.addActionListener(parent);
        
        add(choix);
    }
}
