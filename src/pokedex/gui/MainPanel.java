/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex.gui;

import pokedex.gui.widgets.InfoButton;
import pokedex.gui.widgets.PasswordPanel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import pokedex.gui.pokedex.*;
import pokedex.database.*;
import pokedex.gui.ability.AbilityPanel;
import pokedex.gui.move.MovePanel;
import pokedex.gui.pokemon.PokemonPanel;
import pokedex.gui.trainer.TrainerPanel;
import pokedex.gui.type.TypePanel;

/**
 *
 * @author Leon
 */
public class MainPanel extends JPanel implements ActionListener {

    JComboBox choice;
    String user;
    private JTabbedPane tabbedPane;
    public Database db;
    public PokedexPanel pokedexPanel;
    public TypePanel typePanel;
    public AbilityPanel abilityPanel;
    public MovePanel movePanel;
    public PokemonPanel pokemonPanel;
    public TrainerPanel trainerPanel;
    PokedexApp parent;

    public ArrayList<Component> trainerTabs, professorTabs;

    MainPanel(Database db, PokedexApp p) {
        parent = p;
        this.db = db;
        setLayout(new BorderLayout());

        tabbedPane = new JTabbedPane();
        trainerTabs = new ArrayList<>();
        professorTabs = new ArrayList<>();

        pokedexPanel = new PokedexPanel(db, this);
        typePanel = new TypePanel(db, this);
        abilityPanel = new AbilityPanel(db, this);
        movePanel = new MovePanel(db, this);
        pokemonPanel = new PokemonPanel(db, this);
        trainerPanel = new TrainerPanel(db, this);
        tabbedPane.addTab("Pokedex", pokedexPanel);
        tabbedPane.addTab("Type", typePanel);
        tabbedPane.addTab("Talent", abilityPanel);
        tabbedPane.addTab("Capacité", movePanel);
        tabbedPane.addTab("Pokemon", pokemonPanel);
        tabbedPane.addTab("Trainer", trainerPanel);

        String[] users = new String[]{"Visiteur", "Dresseur", "Professeur"};
        choice = new JComboBox<>(users);
        choice.setActionCommand(Action.CHANGE_USER.name());
        choice.addActionListener(this);
        add(choice, BorderLayout.NORTH);

        add(tabbedPane, BorderLayout.CENTER);

        user = "";
        changeUser(choice);
    }

    private void changeUser(JComboBox comboBox) {
        String selection = (String) choice.getSelectedItem();
        String password;
        if (!user.equals(selection)) {
            if (selection.equals("Visiteur")) {
                password = "visiteur";
            } else {
                password = getPassword();
            }

            if (password != null && password.toLowerCase().equals(selection.toLowerCase())) {
                if ("Visiteur".equals(selection)) {
                    for (Component tab : trainerTabs) {
                        tabbedPane.remove(tab);
                    }
                    trainerTabs.clear();
                }
                if ("Visiteur".equals(selection) || "Dresseur".equals(selection)) {
                    for (Component tab : professorTabs) {
                        tabbedPane.remove(tab);
                    }
                    professorTabs.clear();
                    trainerTabs.removeAll(professorTabs);
                }
                user = selection;
                pokedexPanel.setUser(user);
                typePanel.setUser(user);
                abilityPanel.setUser(user);
                pokemonPanel.setUser(user);
                trainerPanel.setUser(user);
                movePanel.setUser(user);
            } else if (password != null) {
                comboBox.setSelectedItem(user);
                JOptionPane.showMessageDialog(null, "Mauvais mot de passe, sry !", "Erreur", JOptionPane.ERROR_MESSAGE);
            } else {
                comboBox.setSelectedItem(user);
            }
        }
    }

    private String getPassword() {
        PasswordPanel passwordPanel = new PasswordPanel();
        JOptionPane op = new JOptionPane(passwordPanel);
        op.setMessageType(JOptionPane.QUESTION_MESSAGE);
        op.setOptionType(JOptionPane.OK_CANCEL_OPTION);

        JDialog dlg = op.createDialog("Mot de Passe");

        dlg.addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                passwordPanel.gainedFocus();
            }
        });
        dlg.setVisible(true);

        if (op.getValue() != null && op.getValue().equals(JOptionPane.OK_OPTION)) {
            return new String(passwordPanel.getPassword());
        }
        return null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        InfoButton source;
        switch (Action.valueOf(e.getActionCommand())) {
            case CHANGE_USER:
                changeUser((JComboBox) e.getSource());
                break;
            case GET_TYPE:
                source = (InfoButton) e.getSource();
                typePanel.setId(source.getId(), 0);
                tabbedPane.setSelectedComponent(typePanel);
                break;
            case GET_ABILITY:
                source = (InfoButton) e.getSource();
                abilityPanel.setId(source.getId());
                tabbedPane.setSelectedComponent(abilityPanel);
                break;
            case GET_MOVE:
                source = (InfoButton) e.getSource();
                movePanel.setId(source.getId());
                tabbedPane.setSelectedComponent(movePanel);
                break;
            case GET_POKEDEX:
                source = (InfoButton) e.getSource();
                pokedexPanel.setId(source.getId());
                tabbedPane.setSelectedComponent(pokedexPanel);
                break;
            case GET_POKEMON:
                source = (InfoButton) e.getSource();
                pokemonPanel.setId(source.getId());
                tabbedPane.setSelectedComponent(pokemonPanel);
                break;
            case GET_TRAINER:
                source = (InfoButton) e.getSource();
                trainerPanel.setId(source.getId());
                tabbedPane.setSelectedComponent(trainerPanel);
                break;
        }
    }

    public static final int PROFESSOR_TAB = 1;
    public static final int TRAINER_TAB = 2;

    public void addTab(JPanel tab, String tabName, int tabGroup) {
        tabbedPane.add(tabName, tab);
        tabbedPane.setSelectedComponent(tab);
        switch (tabGroup) {
            case PROFESSOR_TAB:
                professorTabs.add(tab);
            case TRAINER_TAB:
                trainerTabs.add(tab);
                break;
        }
    }

    public void removeTab(JPanel tab, JPanel tabToSelect) {
        tabbedPane.remove(tab);
        trainerTabs.remove(tab);
        professorTabs.remove(tab);
        tabbedPane.setSelectedComponent(tabToSelect);
    }
}
