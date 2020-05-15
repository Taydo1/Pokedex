/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex.gui.pokedex;

/**
 *
 * @author Spectan
 */
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
import javax.swing.JPanel;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import pokedex.gui.*;
import pokedex.database.*;

public class PokedexModificationPanel extends JPanel implements ActionListener, ComponentListener {

    JComboBox type1, type2, generation, ability1, ability2, ability3, ability4, lowerEvolution, evolution1, evolution2, rarity, shiny, mega;
    JTextField name, enName, classification;
    JFormattedTextField height, weight, malePercentage;
    JButton saveButton, discardButton;
    MainPanel parent;
    int idModif;

    public PokedexModificationPanel(int id, MainPanel main) {

        idModif = id;
        parent = main;
        this.initComponent();
        this.setVisible(true);
    }

    private void initComponent() {

        Pokedex currentPokedex = parent.db.getFromDB("SELECT * FROM pokedex WHERE id=" + idModif, Pokedex.class).get(0);

        //Le nom français
        JPanel namPanel = new JPanel();
        namPanel.setBackground(Color.white);
        name = new JTextField(currentPokedex.name);
        namPanel.setBorder(BorderFactory.createTitledBorder("Nom du pokémon"));
        namPanel.add(name);

        //Le nom anglais
        JPanel enNamePanel = new JPanel();
        enNamePanel.setBackground(Color.white);
        enName = new JTextField(currentPokedex.en_name);
        enNamePanel.setBorder(BorderFactory.createTitledBorder("Nom anglais du pokémon"));
        enNamePanel.add(enName);

        //La classification
        JPanel classificationPanel = new JPanel();
        classificationPanel.setBackground(Color.white);
        classification = new JTextField(currentPokedex.classification);
        classificationPanel.setBorder(BorderFactory.createTitledBorder("Classification du pokémon"));
        classificationPanel.add(classification);

        ArrayList<Object[]> talent = parent.db.getFromDB("SELECT name FROM ability ORDER BY id ASC");
        String[] listTalent = new String[talent.size() + 1];
        listTalent[0] = "";
        for (int i = 1; i <= talent.size(); i++) {
            listTalent[i] = (String) talent.get(i - 1)[0];
        }

        //Talent n°1
        JPanel ability1Panel = new JPanel();
        ability1Panel.setBackground(Color.white);
        ability1 = new JComboBox<>(listTalent);
        ability1.setSelectedItem(listTalent[currentPokedex.id_ability1]);
        ability1Panel.setBorder(BorderFactory.createTitledBorder("Talent n°1 du pokémon"));
        ability1Panel.add(ability1);

        //Talent n°2
        JPanel ability2Panel = new JPanel();
        ability2Panel.setBackground(Color.white);
        ability2 = new JComboBox<>(listTalent);
        ability2.setSelectedItem(listTalent[currentPokedex.id_ability2]);
        ability2Panel.setBorder(BorderFactory.createTitledBorder("Talent n°2 du pokémon"));
        ability2Panel.add(ability2);

        //Talent n°3
        JPanel ability3Panel = new JPanel();
        ability3Panel.setBackground(Color.white);
        ability3 = new JComboBox<>(listTalent);
        ability3.setSelectedItem(listTalent[currentPokedex.id_ability3]);
        ability3Panel.setBorder(BorderFactory.createTitledBorder("Talent n°3 du pokémon"));
        ability3Panel.add(ability3);

        //Talent n°4
        JPanel ability4Panel = new JPanel();
        ability4Panel.setBackground(Color.white);
        ability4 = new JComboBox<>(listTalent);
        ability4.setSelectedItem(listTalent[currentPokedex.id_ability4]);
        ability4Panel.setBorder(BorderFactory.createTitledBorder("Talent n°4 du pokémon"));
        ability4Panel.add(ability4);

        ArrayList<Object[]> listType = parent.db.getFromDB("SELECT name FROM type ORDER BY id ASC");
        ArrayList<String> types = new ArrayList();
        for (int i = 1; i <= listType.size(); i++) {
            types.add((String) listType.get(i - 1)[0]);
        }

        //Type n°1
        JPanel type1JPanel = new JPanel();
        type1JPanel.setBackground(Color.white);
        type1 = new JComboBox<>(types.toArray());
        type1.setSelectedItem(types.get(currentPokedex.id_type1 - 1));
        type1JPanel.setBorder(BorderFactory.createTitledBorder("Type n°1 du pokémon"));
        type1JPanel.add(type1);

        //Type n°2
        types.add(0, "");
        JPanel type2Panel = new JPanel();
        type2Panel.setBackground(Color.white);
        type2 = new JComboBox<>(types.toArray());
        type2.setSelectedItem(types.get(currentPokedex.id_type2));
        type2Panel.setBorder(BorderFactory.createTitledBorder("Type n°2 du pokémon"));
        type2Panel.add(type2);

        String[] gen = new String[]{"gen 1", "gen 2", "gen 3", "gen 4", "gen 5", "gen 6", "gen 7"};

        //Génération
        JPanel generationPanel = new JPanel();
        generationPanel.setBackground(Color.white);
        generation = new JComboBox<>(gen);
        generation.setSelectedItem(gen[currentPokedex.generation - 1]);
        generationPanel.setBorder(BorderFactory.createTitledBorder("Génération du pokémon"));
        generationPanel.add(generation);

        ArrayList<Object[]> pkmnTemp = parent.db.getFromDB("SELECT name FROM pokedex ORDER BY id ASC");
        String[] pkmn = new String[pkmnTemp.size() + 1];
        pkmn[0] = "";
        for (int i = 1; i <= pkmnTemp.size(); i++) {
            pkmn[i] = (String) pkmnTemp.get(i - 1)[0];
        }

        //Pré-évolution
        JPanel lowerEvolutionPanel = new JPanel();
        lowerEvolutionPanel.setBackground(Color.white);
        lowerEvolution = new JComboBox<>(pkmn);
        lowerEvolution.setSelectedItem(pkmn[currentPokedex.id_lower_evolution]);
        lowerEvolutionPanel.setBorder(BorderFactory.createTitledBorder("Pré-évolution du pokémon"));
        lowerEvolutionPanel.add(lowerEvolution);

        //Evolution 1
        JPanel evolution1Panel = new JPanel();
        evolution1Panel.setBackground(Color.white);
        evolution1 = new JComboBox<>(pkmn);
        evolution1.setSelectedItem(pkmn[currentPokedex.id_evolution1]);
        evolution1Panel.setBorder(BorderFactory.createTitledBorder("Évolution n°1 du pokémon"));
        evolution1Panel.add(evolution1);

        //Evolution 2
        JPanel evolution2Panel = new JPanel();
        evolution2Panel.setBackground(Color.white);
        evolution2 = new JComboBox<>(pkmn);
        evolution2.setSelectedItem(pkmn[currentPokedex.id_evolution2]);
        evolution2Panel.setBorder(BorderFactory.createTitledBorder("Évolution n°2 du pokémon"));
        evolution2Panel.add(evolution2);

        NumberFormat formatPoidsTaille = NumberFormat.getInstance();
        formatPoidsTaille.setMaximumFractionDigits(1);

        //Taille
        JPanel heightPanel = new JPanel();
        heightPanel.setBackground(Color.white);
        height = new JFormattedTextField(formatPoidsTaille);
        height.setValue(currentPokedex.height);
        heightPanel.setBorder(BorderFactory.createTitledBorder("Taille du pokémon"));
        heightPanel.add(height);

        //Poids
        JPanel weightPanel = new JPanel();
        weightPanel.setBackground(Color.white);
        weight = new JFormattedTextField(formatPoidsTaille);
        weight.setValue(currentPokedex.weight);
        weightPanel.setBorder(BorderFactory.createTitledBorder("Poids du pokémon"));
        weightPanel.add(weight);

        NumberFormat formatPourcentage = NumberFormat.getPercentInstance();
        formatPourcentage.setMaximumFractionDigits(1);

        //Pourcentage de mâle
        JPanel percentagePanel = new JPanel();
        percentagePanel.setBackground(Color.white);
        malePercentage = new JFormattedTextField(formatPourcentage);
        malePercentage.setValue((double) currentPokedex.percentage_male);
        percentagePanel.setBorder(BorderFactory.createTitledBorder("Pourcentage de mâle"));
        percentagePanel.add(malePercentage);

        String[] rar = new String[]{"Banal", "Fabuleux", "Légendaire"};

        //Rareté du pokémon
        JPanel rarityPanel = new JPanel();
        rarityPanel.setBackground(Color.white);
        rarity = new JComboBox<>(rar);
        if (currentPokedex.is_legendary == -1) {
            rarity.setSelectedItem(rar[0]);
        } else {
            rarity.setSelectedItem(rar[currentPokedex.is_legendary]);
        }
        rarityPanel.setBorder(BorderFactory.createTitledBorder("Rareté du pokémon"));
        rarityPanel.add(rarity);

        String[] ouiNon = new String[]{"Oui", "Non"};

        //Forme Shiny
        JPanel shinyPanel = new JPanel();
        shinyPanel.setBackground(Color.white);
        shiny = new JComboBox<>(ouiNon);
        if (currentPokedex.has_shiny) {
            shiny.setSelectedItem(ouiNon[0]);
        } else {
            shiny.setSelectedItem(ouiNon[1]);
        }
        shinyPanel.setBorder(BorderFactory.createTitledBorder("Le pokémon a une forme shiny ?"));
        shinyPanel.add(shiny);

        //Forme Méga
        JPanel megaPanel = new JPanel();
        megaPanel.setBackground(Color.white);
        mega = new JComboBox<>(ouiNon);
        if (currentPokedex.has_mega) {
            mega.setSelectedItem(ouiNon[0]);
        } else {
            mega.setSelectedItem(ouiNon[1]);
        }
        megaPanel.setBorder(BorderFactory.createTitledBorder("Le pokémon a une forme méga ?"));
        megaPanel.add(mega);

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
        c.weighty = 0.14;
        add(namPanel, c);
        c.gridx = 1;
        add(enNamePanel, c);
        c.gridx = 2;
        add(classificationPanel, c);
        c.gridy = 1;
        add(ability1Panel, c);
        c.gridx = 1;
        add(type2Panel, c);
        c.gridx = 0;
        add(type1JPanel, c);
        c.gridy = 2;
        add(ability2Panel, c);
        c.gridx = 1;
        add(ability3Panel, c);
        c.gridx = 2;
        add(ability4Panel, c);
        c.gridy = 3;
        add(evolution1Panel, c);
        c.gridx = 1;
        add(lowerEvolutionPanel, c);
        c.gridx = 0;
        add(generationPanel, c);
        c.gridy = 4;
        add(evolution2Panel, c);
        c.gridx = 1;
        add(heightPanel, c);
        c.gridx = 2;
        add(weightPanel, c);
        c.gridy = 5;
        add(shinyPanel, c);
        c.gridx = 1;
        add(rarityPanel, c);
        c.gridx = 0;
        add(percentagePanel, c);
        c.gridx = 1;
        c.gridy = 6;
        add(megaPanel, c);
        c.gridx = 0;
        add(savePanel, c);
        c.gridx = 2;
        add(discardPanel, c);

        updateDimension();
        addComponentListener(this);
    }

    public void updateDimension() {
        int dimx = (parent.getWidth() / 3) - 70;
        int dimy = (parent.getHeight() / 7) - 60;
        name.setPreferredSize(new Dimension(dimx, dimy));
        enName.setPreferredSize(new Dimension(dimx, dimy));
        classification.setPreferredSize(new Dimension(dimx, dimy));
        ability1.setPreferredSize(new Dimension(dimx, dimy));
        ability2.setPreferredSize(new Dimension(dimx, dimy));
        ability3.setPreferredSize(new Dimension(dimx, dimy));
        ability4.setPreferredSize(new Dimension(dimx, dimy));
        type1.setPreferredSize(new Dimension(dimx, dimy));
        type2.setPreferredSize(new Dimension(dimx, dimy));
        generation.setPreferredSize(new Dimension(dimx, dimy));
        lowerEvolution.setPreferredSize(new Dimension(dimx, dimy));
        evolution1.setPreferredSize(new Dimension(dimx, dimy));
        evolution2.setPreferredSize(new Dimension(dimx, dimy));
        height.setPreferredSize(new Dimension(dimx, dimy));
        weight.setPreferredSize(new Dimension(dimx, dimy));
        malePercentage.setPreferredSize(new Dimension(dimx, dimy));
        rarity.setPreferredSize(new Dimension(dimx, dimy));
        shiny.setPreferredSize(new Dimension(dimx, dimy));
        mega.setPreferredSize(new Dimension(dimx, dimy));
        saveButton.setPreferredSize(new Dimension(dimx + 20, (int) (dimy * 1.5)));
        discardButton.setPreferredSize(new Dimension(dimx + 20, (int) (dimy * 1.5)));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        InfoButton source;
        switch (Action.valueOf(e.getActionCommand())) {
            case SAVE_MODIFICATION:
                //Traduit les Oui/Non en booléen
                int r;
                boolean s,
                 m;
                if (shiny.getSelectedItem().equals("Oui")) {
                    s = true;
                } else {
                    s = false;
                }
                if (mega.getSelectedItem().equals("Oui")) {
                    m = true;
                } else {
                    m = false;
                }
                //Réglage d'un bug sur la rareté
                if (rarity.getSelectedItem().equals("Légendaire")) {
                    r = 2;
                } else if (rarity.getSelectedItem().equals("Fabuleux")) {
                    r = 1;
                } else {
                    r = 0;
                }
                double percent = (double) malePercentage.getValue();
                new Pokedex(idModif, name.getText(), enName.getText(), classification.getText(),
                        type1.getSelectedIndex() + 1, type2.getSelectedIndex(),
                        ability1.getSelectedIndex(), ability2.getSelectedIndex(),
                        ability3.getSelectedIndex(), ability4.getSelectedIndex(),
                        generation.getSelectedIndex() + 1, lowerEvolution.getSelectedIndex(),
                        evolution1.getSelectedIndex(), evolution2.getSelectedIndex(),
                        Float.parseFloat(height.getText().replace(',', '.')), Float.parseFloat(weight.getText().replace(',', '.')),
                        (float) percent, r, s, m).modifyInDB(parent.db);

                parent.pokedexPanel.setId(parent.pokedexPanel.currentId);
                JOptionPane.showMessageDialog(null, "Modification sauvegardée", "Information", JOptionPane.INFORMATION_MESSAGE);

            case DISCARD_MODIFICATION:
                parent.removeTab(this, parent.pokedexPanel);
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

}
