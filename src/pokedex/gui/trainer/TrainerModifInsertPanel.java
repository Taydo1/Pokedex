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
    JComboBox<InfoButton> equipe[];
    ArrayList<InfoButton> listPokemon;
    JButton save, discard;
    boolean isInsert;

    public TrainerModifInsertPanel(int id, MainPanel parent) { //trainer modification
        isInsert = false;
        this.idModif = id;
        this.parent = parent;
        setSize(new Dimension(parent.getWidth(), parent.getHeight()));
        Trainer currentTrainer = parent.db.getFromDB("SELECT * from trainer WHERE id = " + idModif, Trainer.class).get(0);
        initComponentsModif(currentTrainer);
        setVisible(true);
        addComponentListener(this);
    }

    public TrainerModifInsertPanel(MainPanel parent) { //new trainer
        isInsert = true;
        this.parent = parent;
        setSize(new Dimension(parent.getWidth(), parent.getHeight()));
        Trainer currentTrainer = new Trainer(-1, "", -1, -1, -1, -1, -1, -1);
        initComponentsModif(currentTrainer);
        setVisible(true);
        addComponentListener(this);
    }

    private void initComponentsModif(Trainer currentTrainer) {

        ArrayList<Pokemon> listPkmn = currentTrainer.getPokemons(parent.db);
        listPokemon = new ArrayList<>();
        for (int i = 0; i < listPkmn.size(); i++) {
            listPokemon.add(new InfoButton(listPkmn.get(i).name, listPkmn.get(i).id));
        }

        equipe = new JComboBox[6];

        //Le nom du dresseur
        JPanel namePanel = new JPanel();
        namePanel.setBackground(Color.white);
        name = new JTextField(currentTrainer.name);
        name.setText(currentTrainer.name);
        namePanel.setBorder(BorderFactory.createTitledBorder("Nom du dresseur"));
        namePanel.add(name);

        JPanel[] team = new JPanel[6];
        for (int i = 0; i < 6; i++) {
            equipe[i] = new JComboBox(listPokemon.toArray());
            equipe[i].setSelectedIndex(findSelectorId(currentTrainer.id_pokemon[i], i));
            equipe[i].addActionListener(this);

            equipe[i].setActionCommand(Action.CHANGE_TEAM.name());

            team[i] = new JPanel();
            team[i].setBackground(Color.white);
            int num = i + 1;
            team[i].setBorder(BorderFactory.createTitledBorder("Pokémon n°" + num + " de l'équipe"));
            team[i].add(equipe[i]);
        }

        updateEquipe();
        
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
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (Action.valueOf(e.getActionCommand())) {

            case CHANGE_TEAM:
                updateEquipe();
                break;

            case SAVE_MODIFICATION:

                int[] equ = new int[6];
                for (int i = 0; i < 6; i++) {
                    InfoButton buttonSelect = (InfoButton) equipe[i].getSelectedItem();
                    if (buttonSelect == null || buttonSelect.getId() == 0) {
                        equ[i] = 0;
                    } else {
                        InfoButton pkmnSelect = (InfoButton) equipe[i].getSelectedItem();
                        equ[i] = pkmnSelect.getId();
                    }
                }

                Trainer temp = new Trainer(idModif, name.getText(), equ[0], equ[1], equ[2], equ[3], equ[4], equ[5]);
                if (isInsert) {
                    parent.db.executeUpdate("INSERT INTO trainer VALUES " + temp.getInsertSubRequest());
                } else {
                    temp.modifyInDB(parent.db);
                }

                parent.trainerPanel.setId(parent.trainerPanel.currentId);
                JOptionPane.showMessageDialog(null, "Modification sauvegardée", "Information", JOptionPane.INFORMATION_MESSAGE);
                parent.removeTab(this, parent.trainerPanel, true);
                break;
            case DISCARD_MODIFICATION:
                parent.removeTab(this, parent.trainerPanel, false);
                break;
        }
    }

    @Override
    public void componentResized(ComponentEvent arg0) {
        updateDimension();
        revalidate();
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

    private int findSelectorId(int id, int n) {
        for (int i = 0; i < equipe[n].getItemCount(); i++) {
            InfoButton item = equipe[n].getItemAt(i);
            if (item.getId() == id) {
                return i;
            }
        }
        return -1;
    }

    public void updateDimension() {
        int dimx = (this.getWidth() / 2) - 30;
        int dimy = (this.getHeight() / 6) - 70;
        name.setPreferredSize(new Dimension(dimx, dimy));
        for (int i = 0; i < 6; i++) {
            equipe[i].setPreferredSize(new Dimension(dimx, dimy));
        }
        save.setPreferredSize(new Dimension(dimx + 20, (int) (dimy * 1.5)));
        discard.setPreferredSize(new Dimension(dimx + 20, (int) (dimy * 1.5)));
    }

    private void updateEquipe() {

//        ArrayList<InfoButton> listDispo = new ArrayList<>(listPokemon);
//        listDispo.add(new InfoButton("", 0, true));
//        System.out.println("je fais l'update");
//        for (int j = 0; j < 6; j++) {
//            equipe[j] = new JComboBox(listDispo.toArray());
//            if (!listDispo.isEmpty()) {
//                int selectedId = ((InfoButton) equipe[j].getSelectedItem()).getId();
//                for (int i = listDispo.size() - 1; i >= 0; i--) {
//                    if (listDispo.get(i).getId() == selectedId) {
//                        listDispo.remove(i);
//                    }
//                }
//            }
//        }
        for (int i = 0; i < 6; i++) {
            equipe[i].removeActionListener(this);
            InfoButton selected = (InfoButton) equipe[i].getSelectedItem();
            equipe[i].removeAllItems();

            ArrayList<InfoButton> tmp = removeSelected(listPokemon, i);
            tmp.add(0, new InfoButton("", 0));
            for (InfoButton infoButton : tmp) {
                equipe[i].addItem(infoButton);
            }
            equipe[i].setSelectedItem(selected);
            equipe[i].addActionListener(this);
        }

        setEquipeAccess();
    }

    private ArrayList<InfoButton> removeSelected(ArrayList<InfoButton> listDispo, int selectoToExcept) {
        ArrayList<InfoButton> returnList = new ArrayList<>(listDispo);
        for (int i = 0; i < 6; i++) {
            if (i != selectoToExcept) {
                InfoButton selected = (InfoButton) equipe[i].getSelectedItem();
                returnList.remove(selected);
            }
        }
        return returnList;
    }

    private void setEquipeAccess() {
        for (int i = 0; i < 5; i++) {
            InfoButton buttonSelect = (InfoButton) equipe[i].getSelectedItem();
            if (buttonSelect == null || buttonSelect.getId() == 0) {
                equipe[i + 1].setEnabled(false);
            } else {
                equipe[i + 1].setEnabled(true);
            }
        }
    }
}
