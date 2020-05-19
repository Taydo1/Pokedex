/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex.gui.trainer;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import pokedex.database.Pokedex;
import pokedex.database.Pokemon;
import pokedex.database.Trainer;
import pokedex.gui.Action;
import pokedex.gui.MainPanel;
import pokedex.gui.widgets.InfoButton;

/**
 *
 * @author Leon
 */
public class TrainerModifInsertPanel extends JPanel implements ActionListener, ComponentListener {
    
    int idModif;
    MainPanel parent;
    JTextField name;
    JComboBox<Object> equipe[];
    ArrayList<Pokemon>listPokemon;
    JButton save, discard;
    Trainer currentTrainer;

    public TrainerModifInsertPanel(int id, MainPanel parent) { //trainer modification
        
        this.idModif = id;
        this.parent = parent;
        setSize(new Dimension(parent.getWidth(), parent.getHeight()));
        currentTrainer = parent.db.getFromDB("SELECT * from trainer WHERE id = " + idModif, Trainer.class).get(0);
        initComponentsModif();
        setVisible(true);
        addComponentListener(this);
    }

    public TrainerModifInsertPanel(MainPanel parent) { //new trainer
    }

    private void initComponentsModif() {
        
        listPokemon = currentTrainer.getPokemons(parent.db);
        equipe = new JComboBox[6];
        
        //Le nom du dresseur
        JPanel namePanel = new JPanel();
        namePanel.setBackground(Color.white);
        name = new JTextField(currentTrainer.name);
        name.setText(currentTrainer.name);
        namePanel.setBorder(BorderFactory.createTitledBorder("Nom du dresseur"));
        namePanel.add(name);
        
        //L'équipe
        setEquipe();
        
        JPanel[] team = new JPanel[6];
        for (int i = 0; i < 6; i++){
            team[i] = new JPanel();
            team[i].setBackground(Color.white);
            team[i].setBorder(BorderFactory.createTitledBorder("Pokémon n°" + i + " de l'équipe"));
            team[i].add(equipe[i]);
            equipe[i].addActionListener(this);
            equipe[i].setActionCommand(Action.CHANGE_TEAM.name());
        }
        
        save = new JButton("SAVE");
        save.setCursor(Cursor.getPredefinedCursor((Cursor.HAND_CURSOR)));
        save.addActionListener(this);
        save.setActionCommand(Action.SAVE_MODIFICATION.name());
        discard = new JButton("DISCARD");
        discard.setCursor(Cursor.getPredefinedCursor((Cursor.HAND_CURSOR)));
        discard.addActionListener(this);
        discard.setActionCommand(Action.DISCARD_MODIFICATION.name());
        
        JPanel savePanel = new JPanel();
        savePanel.add(save);
        savePanel.setBackground(Color.white);
        JPanel discardPanel = new JPanel();
        discardPanel.add(discard);
        discardPanel.setBackground(Color.white);
        
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 0;
        add(namePanel, c);
        c.gridwidth = 1;
        c.weightx = 0.5;
        c.gridy++;
        add(team[0], c);
        c.gridx++;
        add(team[1], c);
        c.gridx = 0;
        c.gridy++;
        add(team[2], c);
        c.gridx++;
        add(team[3], c);
        c.gridx = 0;
        c.gridy++;
        add(team[4], c);
        c.gridx++;
        add(team[5], c);
        c.gridx = 0;
        c.gridy++;
        add(savePanel, c);
        c.gridx++;
        add(discardPanel, c);

        updateDimension();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (Action.valueOf(e.getActionCommand())) {

            case CHANGE_TEAM:
                updateEquipe();
                break;

            case SAVE_MODIFICATION:
                
                ArrayList<Pokemon> listDispo = listPokemon;
                
                int[] equ = new int[6];
                for (int i = 0; i < 6; i++){
                  if (equipe[i].getSelectedItem() == null){
                        equ[i] = 0;
                    } else {
                        equ[i] = listDispo.get(equipe[i].getSelectedIndex()).id;
                    }  
                }
                
                
                
                Trainer temp = new Trainer(idModif, name.getText(), equ[0], equ[1], equ[2], equ[3], equ[4], equ[5]);
                temp.modifyInDB(parent.db);

                parent.trainerPanel.setId(parent.trainerPanel.idActuel);
                JOptionPane.showMessageDialog(null, "Modification sauvegardée", "Information", JOptionPane.INFORMATION_MESSAGE);
            case DISCARD_MODIFICATION:
                parent.removeTab(this, parent.pokemonPanel);
                break;
        }
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

    private void setEquipe() {
        
        ArrayList<Pokemon> listDispo = listPokemon;
        listDispo.add(null);
        
        for (int j = 0; j < 6; j++){
          equipe[j] = new JComboBox(listDispo.toArray());
          equipe[j].setSelectedItem(null);
            if(!listDispo.isEmpty()){
                for (int i = 0; i < equipe[j].getItemCount()-1; i++) {
                    if (listDispo.get(i).id == currentTrainer.id_pokemon[j]) {
                        equipe[j].setSelectedItem(listDispo.get(i));
                        listDispo.remove(listDispo.get(i));
                    }
                }
            }  
        }
        
        setEquipeAccess();
    }

    private void updateDimension() {
        int dimx = (this.getWidth() / 2) - 30;
        int dimy = (this.getHeight() / 6) - 30;
        name.setPreferredSize(new Dimension(dimx, dimy));
        for (int i = 0; i < 6; i++){
            equipe[i].setPreferredSize(new Dimension(dimx, dimy));
        }
        save.setPreferredSize(new Dimension(dimx + 20, (int) (dimy * 1.5)));
        discard.setPreferredSize(new Dimension(dimx + 20, (int) (dimy * 1.5)));
    }

    private void updateEquipe() {
        
        ArrayList<Pokemon> listDispo = listPokemon;
        listDispo.add(null);
        
        for (int j = 0; j < 6; j++){
          equipe[j] = new JComboBox(listDispo.toArray());
            if(!listDispo.isEmpty()){
                for (int i = 0; i < equipe[j].getItemCount()-1; i++) {
                    if (listDispo.get(i).id == listDispo.get(equipe[j].getSelectedIndex()).id) {
                        listDispo.remove(listDispo.get(i));
                    }
                }
            }  
        }
        
        setEquipeAccess();
    }

    private void setEquipeAccess() {
        
        for(int i = 0; i < 5; i++){
            if(equipe[i].getSelectedItem() == null){
                equipe[i + 1].setEnabled(false);
            } else  {
                equipe[i + 1].setEnabled(true);
            }
        }
    }
}
