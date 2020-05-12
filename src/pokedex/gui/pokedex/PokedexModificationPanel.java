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

    JComboBox type1, type2, generation, talent1, talent2, talent3, talent4, preEvolution, evolution1, evolution2, rarete, shiny, mega;
    JTextField nomfr, nomen, classification;
    JFormattedTextField taille, poids, pourcentage;
    JButton saveBouton, discardBouton;
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
        JPanel panNomfr = new JPanel();
        panNomfr.setBackground(Color.white);
        nomfr = new JTextField(currentPokedex.name);
        panNomfr.setBorder(BorderFactory.createTitledBorder("Nom du pokémon"));
        panNomfr.add(nomfr);

        //Le nom anglais
        JPanel panNomen = new JPanel();
        panNomen.setBackground(Color.white);
        nomen = new JTextField(currentPokedex.en_name);
        panNomen.setBorder(BorderFactory.createTitledBorder("Nom anglais du pokémon"));
        panNomen.add(nomen);

        //La classification
        JPanel panClassification = new JPanel();
        panClassification.setBackground(Color.white);
        classification = new JTextField(currentPokedex.classification);
        panClassification.setBorder(BorderFactory.createTitledBorder("Classification du pokémon"));
        panClassification.add(classification);

        ArrayList<Object[]> talent = parent.db.getFromDB("SELECT name FROM ability ORDER BY id ASC");
        String[] listTalent = new String[talent.size() + 1];
        listTalent[0] = "";
        for (int i = 1; i <= talent.size(); i++) {
            listTalent[i] = (String) talent.get(i - 1)[0];
        }

        //Talent n°1
        JPanel panTalent1 = new JPanel();
        panTalent1.setBackground(Color.white);
        talent1 = new JComboBox<>(listTalent);
        talent1.setSelectedItem(listTalent[currentPokedex.id_ability1]);
        panTalent1.setBorder(BorderFactory.createTitledBorder("Talent n°1 du pokémon"));
        panTalent1.add(talent1);

        //Talent n°2
        JPanel panTalent2 = new JPanel();
        panTalent2.setBackground(Color.white);
        talent2 = new JComboBox<>(listTalent);
        talent2.setSelectedItem(listTalent[currentPokedex.id_ability2]);
        panTalent2.setBorder(BorderFactory.createTitledBorder("Talent n°2 du pokémon"));
        panTalent2.add(talent2);

        //Talent n°3
        JPanel panTalent3 = new JPanel();
        panTalent3.setBackground(Color.white);
        talent3 = new JComboBox<>(listTalent);
        talent3.setSelectedItem(listTalent[currentPokedex.id_ability3]);
        panTalent3.setBorder(BorderFactory.createTitledBorder("Talent n°3 du pokémon"));
        panTalent3.add(talent3);

        //Talent n°4
        JPanel panTalent4 = new JPanel();
        panTalent4.setBackground(Color.white);
        talent4 = new JComboBox<>(listTalent);
        talent4.setSelectedItem(listTalent[currentPokedex.id_ability4]);
        panTalent4.setBorder(BorderFactory.createTitledBorder("Talent n°4 du pokémon"));
        panTalent4.add(talent4);

        ArrayList<Object[]> listType = parent.db.getFromDB("SELECT name FROM type ORDER BY id ASC");
        ArrayList<String> types = new ArrayList();
        for (int i = 1; i <= listType.size(); i++) {
            types.add((String) listType.get(i - 1)[0]);
        }

        //Type n°1
        JPanel panType1 = new JPanel();
        panType1.setBackground(Color.white);
        type1 = new JComboBox<>(types.toArray());
        type1.setSelectedItem(types.get(currentPokedex.id_type1-1));
        panType1.setBorder(BorderFactory.createTitledBorder("Type n°1 du pokémon"));
        panType1.add(type1);

        //Type n°2
        types.add(0,"");
        JPanel panType2 = new JPanel();
        panType2.setBackground(Color.white);
        type2 = new JComboBox<>(types.toArray());
        type2.setSelectedItem(types.get(currentPokedex.id_type2));
        panType2.setBorder(BorderFactory.createTitledBorder("Type n°2 du pokémon"));
        panType2.add(type2);

        String[] gen = new String[]{"gen 1", "gen 2", "gen 3", "gen 4", "gen 5", "gen 6", "gen 7"};

        //Génération
        JPanel panGeneration = new JPanel();
        panGeneration.setBackground(Color.white);
        generation = new JComboBox<>(gen);
        generation.setSelectedItem(gen[currentPokedex.generation - 1]);
        panGeneration.setBorder(BorderFactory.createTitledBorder("Génération du pokémon"));
        panGeneration.add(generation);

        ArrayList<Object[]> pkmnTemp = parent.db.getFromDB("SELECT name FROM pokedex ORDER BY id ASC");
        String[] pkmn = new String[pkmnTemp.size() + 1];
        pkmn[0] = "";
        for (int i = 1; i <= pkmnTemp.size(); i++) {
            pkmn[i] = (String) pkmnTemp.get(i - 1)[0];
        }

        //Pré-évolution
        JPanel panPreEvolution = new JPanel();
        panPreEvolution.setBackground(Color.white);
        preEvolution = new JComboBox<>(pkmn);
        preEvolution.setSelectedItem(pkmn[currentPokedex.id_lower_evolution]);
        panPreEvolution.setBorder(BorderFactory.createTitledBorder("Pré-évolution du pokémon"));
        panPreEvolution.add(preEvolution);

        //Evolution 1
        JPanel panEvolution1 = new JPanel();
        panEvolution1.setBackground(Color.white);
        evolution1 = new JComboBox<>(pkmn);
        evolution1.setSelectedItem(pkmn[currentPokedex.id_evolution1]);
        panEvolution1.setBorder(BorderFactory.createTitledBorder("Évolution n°1 du pokémon"));
        panEvolution1.add(evolution1);

        //Evolution 2
        JPanel panEvolution2 = new JPanel();
        panEvolution2.setBackground(Color.white);
        evolution2 = new JComboBox<>(pkmn);
        evolution2.setSelectedItem(pkmn[currentPokedex.id_evolution2]);
        panEvolution2.setBorder(BorderFactory.createTitledBorder("Évolution n°2 du pokémon"));
        panEvolution2.add(evolution2);

        NumberFormat formatPoidsTaille = NumberFormat.getInstance();
        formatPoidsTaille.setMaximumFractionDigits(1);

        //Taille
        JPanel panTaille = new JPanel();
        panTaille.setBackground(Color.white);
        taille = new JFormattedTextField(formatPoidsTaille);
        taille.setValue(currentPokedex.height);
        panTaille.setBorder(BorderFactory.createTitledBorder("Taille du pokémon"));
        panTaille.add(taille);

        //Poids
        JPanel panPoids = new JPanel();
        panPoids.setBackground(Color.white);
        poids = new JFormattedTextField(formatPoidsTaille);
        poids.setValue(currentPokedex.weight);
        panPoids.setBorder(BorderFactory.createTitledBorder("Poids du pokémon"));
        panPoids.add(poids);

        NumberFormat formatPourcentage = NumberFormat.getPercentInstance();
        formatPourcentage.setMaximumFractionDigits(1);

        //Pourcentage de mâle
        JPanel panPourcent = new JPanel();
        panPourcent.setBackground(Color.white);
        pourcentage = new JFormattedTextField(formatPourcentage);
        pourcentage.setValue((double)currentPokedex.percentage_male);
        panPourcent.setBorder(BorderFactory.createTitledBorder("Pourcentage de mâle"));
        panPourcent.add(pourcentage);

        String[] rar = new String[]{"Banal", "Fabuleux", "Légendaire"};

        //Rareté du pokémon
        JPanel panRarete = new JPanel();
        panRarete.setBackground(Color.white);
        rarete = new JComboBox<>(rar);
        if (currentPokedex.is_legendary == -1) {
            rarete.setSelectedItem(rar[0]);
        } else {
            rarete.setSelectedItem(rar[currentPokedex.is_legendary]);
        }
        panRarete.setBorder(BorderFactory.createTitledBorder("Rareté du pokémon"));
        panRarete.add(rarete);

        String[] ouiNon = new String[]{"Oui", "Non"};

        //Forme Shiny
        JPanel panShiny = new JPanel();
        panShiny.setBackground(Color.white);
        shiny = new JComboBox<>(ouiNon);
        if (currentPokedex.has_shiny) {
            shiny.setSelectedItem(ouiNon[0]);
        } else {
            shiny.setSelectedItem(ouiNon[1]);
        }
        panShiny.setBorder(BorderFactory.createTitledBorder("Le pokémon a une forme shiny ?"));
        panShiny.add(shiny);

        //Forme Méga
        JPanel panMega = new JPanel();
        panMega.setBackground(Color.white);
        mega = new JComboBox<>(ouiNon);
        if (currentPokedex.has_mega) {
            mega.setSelectedItem(ouiNon[0]);
        } else {
            mega.setSelectedItem(ouiNon[1]);
        }
        panMega.setBorder(BorderFactory.createTitledBorder("Le pokémon a une forme méga ?"));
        panMega.add(mega);

        saveBouton = new JButton("SAVE");
        saveBouton.setCursor(Cursor.getPredefinedCursor((Cursor.HAND_CURSOR)));
        saveBouton.addActionListener(this);
        saveBouton.setActionCommand(Action.SAVE_POKEDEX_MODIFICATION.name());
        discardBouton = new JButton("DISCARD");
        discardBouton.setCursor(Cursor.getPredefinedCursor((Cursor.HAND_CURSOR)));
        discardBouton.addActionListener(this);
        discardBouton.setActionCommand(Action.DISCARD_POKEDEX_MODIFICATION.name());

        JPanel savePanel = new JPanel();
        savePanel.add(saveBouton);
        savePanel.setBackground(Color.white);
        JPanel discardPanel = new JPanel();
        discardPanel.add(discardBouton);
        discardPanel.setBackground(Color.white);

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        setBackground(Color.white);
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.33;
        c.weighty = 0.14;
        add(panNomfr, c);
        c.gridx = 1;
        add(panNomen, c);
        c.gridx = 2;
        add(panClassification, c);
        c.gridy = 1;
        add(panTalent1, c);
        c.gridx = 1;
        add(panType2, c);
        c.gridx = 0;
        add(panType1, c);
        c.gridy = 2;
        add(panTalent2, c);
        c.gridx = 1;
        add(panTalent3, c);
        c.gridx = 2;
        add(panTalent4, c);
        c.gridy = 3;
        add(panEvolution1, c);
        c.gridx = 1;
        add(panPreEvolution, c);
        c.gridx = 0;
        add(panGeneration, c);
        c.gridy = 4;
        add(panEvolution2, c);
        c.gridx = 1;
        add(panTaille, c);
        c.gridx = 2;
        add(panPoids, c);
        c.gridy = 5;
        add(panShiny, c);
        c.gridx = 1;
        add(panRarete, c);
        c.gridx = 0;
        add(panPourcent, c);
        c.gridx = 1;
        c.gridy = 6;
        add(panMega, c);
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
        nomfr.setPreferredSize(new Dimension(dimx, dimy));
        nomen.setPreferredSize(new Dimension(dimx, dimy));
        classification.setPreferredSize(new Dimension(dimx, dimy));
        talent1.setPreferredSize(new Dimension(dimx, dimy));
        talent2.setPreferredSize(new Dimension(dimx, dimy));
        talent3.setPreferredSize(new Dimension(dimx, dimy));
        talent4.setPreferredSize(new Dimension(dimx, dimy));
        type1.setPreferredSize(new Dimension(dimx, dimy));
        type2.setPreferredSize(new Dimension(dimx, dimy));
        generation.setPreferredSize(new Dimension(dimx, dimy));
        preEvolution.setPreferredSize(new Dimension(dimx, dimy));
        evolution1.setPreferredSize(new Dimension(dimx, dimy));
        evolution2.setPreferredSize(new Dimension(dimx, dimy));
        taille.setPreferredSize(new Dimension(dimx, dimy));
        poids.setPreferredSize(new Dimension(dimx, dimy));
        pourcentage.setPreferredSize(new Dimension(dimx, dimy));
        rarete.setPreferredSize(new Dimension(dimx, dimy));
        shiny.setPreferredSize(new Dimension(dimx, dimy));
        mega.setPreferredSize(new Dimension(dimx, dimy));
        saveBouton.setPreferredSize(new Dimension(dimx + 20, (int) (dimy * 1.5)));
        discardBouton.setPreferredSize(new Dimension(dimx + 20, (int) (dimy * 1.5)));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        InfoButton source;
        switch (Action.valueOf(e.getActionCommand())) {
            case SAVE_POKEDEX_MODIFICATION:
                //Traduit les Oui/Non en booléen
                int r;
                boolean s,m;
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
                if (rarete.getSelectedItem().equals("Légendaire")) {
                    r = 2;
                } else if (rarete.getSelectedItem().equals("Fabuleux")) {
                    r = 1;
                } else {
                    r = 0;
                }
                double temp = (double)pourcentage.getValue();
                new Pokedex(idModif, nomfr.getText(), nomen.getText(), classification.getText(),
                        type1.getSelectedIndex()+1, type2.getSelectedIndex(),
                        talent1.getSelectedIndex(), talent2.getSelectedIndex(), 
                        talent3.getSelectedIndex(), talent4.getSelectedIndex(), 
                        generation.getSelectedIndex()+1, preEvolution.getSelectedIndex(), 
                        evolution1.getSelectedIndex(), evolution2.getSelectedIndex(), 
                        Float.parseFloat(taille.getText().replace(',', '.')), Float.parseFloat(poids.getText().replace(',', '.')),
                        (float)temp, r, s, m).modifyInDB(parent.db);

                parent.pokedexPanel.setId(parent.pokedexPanel.idActuel);
                JOptionPane.showMessageDialog(null, "Modification sauvegardée", "Information", JOptionPane.INFORMATION_MESSAGE);

            case DISCARD_POKEDEX_MODIFICATION:
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
