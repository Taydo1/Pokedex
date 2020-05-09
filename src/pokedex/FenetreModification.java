/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex;

/**
 *
 * @author Spectan
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class FenetreModification extends JPanel {
    
    JComboBox type1, type2, generation, talent1, talent2, talent3, talent4, preEvolution, evolution1, evolution2, rarete, shiny, mega;
    JTextField nomfr, nomen, classification;
    JFormattedTextField taille, poids, pourcentage;
    MainPanel parent;
    int idModif;
    
    public FenetreModification(int id, MainPanel main) {
        
        idModif = id;
        parent = main;
        this.initComponent();
        this.setVisible(true);
    }
    
    public void showFenetreModification() {
        this.setVisible(true);        
    }
    
    private void initComponent() {
        
        Pokedex currentPokedex = parent.db.getFromDB("SELECT * FROM pokedex WHERE id=" + idModif, Pokedex.class).get(0);

        //Le nom français
        JPanel panNomfr = new JPanel();
        panNomfr.setBackground(Color.white);
        panNomfr.setPreferredSize(new Dimension(220, 60));
        nomfr = new JTextField();
        nomfr.setText(currentPokedex.name);
        nomfr.setPreferredSize(new Dimension(100, 25));
        panNomfr.setBorder(BorderFactory.createTitledBorder("Nom du pokémon"));
        panNomfr.add(nomfr);

        //Le nom anglais
        JPanel panNomen = new JPanel();
        panNomen.setBackground(Color.white);
        panNomen.setPreferredSize(new Dimension(220, 60));
        nomen = new JTextField();
        nomen.setText(currentPokedex.en_name);
        nomen.setPreferredSize(new Dimension(100, 25));
        panNomen.setBorder(BorderFactory.createTitledBorder("Nom anglais du pokémon"));
        panNomen.add(nomen);

        //La classification
        JPanel panClassification = new JPanel();
        panClassification.setBackground(Color.white);
        panClassification.setPreferredSize(new Dimension(220, 60));
        classification = new JTextField();
        classification.setText(currentPokedex.classification);
        classification.setPreferredSize(new Dimension(100, 25));
        panClassification.setBorder(BorderFactory.createTitledBorder("Classification du pokémon"));
        panClassification.add(classification);
        ArrayList<String> listTalent = new ArrayList();
        listTalent.add("");
        ArrayList<Ability> talent = parent.db.getFromDB("SELECT * FROM ability", Ability.class);
        for (int i = 0; i < talent.size(); i++) {
            listTalent.add(talent.get(i).name);
        }

        //Talent n°1
        JPanel panTalent1 = new JPanel();
        panTalent1.setBackground(Color.white);
        panTalent1.setPreferredSize(new Dimension(220, 60));
        talent1 = new JComboBox<>(listTalent.toArray());
        talent1.setSelectedItem(listTalent.get(currentPokedex.id_ability1));
        talent1.setPreferredSize(new Dimension(100, 25));
        panTalent1.setBorder(BorderFactory.createTitledBorder("Talent n°1 du pokémon"));
        panTalent1.add(talent1);

        //Talent n°2
        JPanel panTalent2 = new JPanel();
        panTalent2.setBackground(Color.white);
        panTalent2.setPreferredSize(new Dimension(220, 60));
        talent2 = new JComboBox<>(listTalent.toArray());
        if (currentPokedex.id_ability2 != 0) {
            talent2.setSelectedItem(listTalent.get(currentPokedex.id_ability2));
        } else {
            talent2.setSelectedItem(listTalent.get(0));
        }
        talent2.setPreferredSize(new Dimension(100, 25));
        panTalent2.setBorder(BorderFactory.createTitledBorder("Talent n°2 du pokémon"));
        panTalent2.add(talent2);

        //Talent n°3
        JPanel panTalent3 = new JPanel();
        panTalent3.setBackground(Color.white);
        panTalent3.setPreferredSize(new Dimension(220, 60));
        talent3 = new JComboBox<>(listTalent.toArray());
        if (currentPokedex.id_ability3 != 0) {
            talent3.setSelectedItem(listTalent.get(currentPokedex.id_ability3));
        } else {
            talent3.setSelectedItem(listTalent.get(0));
        }
        talent3.setPreferredSize(new Dimension(100, 25));
        panTalent3.setBorder(BorderFactory.createTitledBorder("Talent n°3 du pokémon"));
        panTalent3.add(talent3);

        //Talent n°4
        JPanel panTalent4 = new JPanel();
        panTalent4.setBackground(Color.white);
        panTalent4.setPreferredSize(new Dimension(220, 60));
        talent4 = new JComboBox<>(listTalent.toArray());
        if (currentPokedex.id_ability4 != 0) {
            talent4.setSelectedItem(listTalent.get(currentPokedex.id_ability4));
        } else {
            talent4.setSelectedItem(listTalent.get(0));
        }
        talent4.setPreferredSize(new Dimension(100, 25));
        panTalent4.setBorder(BorderFactory.createTitledBorder("Talent n°4 du pokémon"));
        panTalent4.add(talent4);
        
        ArrayList<String> types = new ArrayList();
        types.add("");
        ArrayList<pokedex.Type> listType = parent.db.getFromDB("SELECT * FROM type", pokedex.Type.class);
        for (int i = 0; i < listType.size(); i++) {
            types.add(listType.get(i).name);
        }

        //Type n°1
        JPanel panType1 = new JPanel();
        panType1.setBackground(Color.white);
        panType1.setPreferredSize(new Dimension(220, 60));
        type1 = new JComboBox<>(types.toArray());
        type1.setSelectedItem(types.get(currentPokedex.id_type1));
        type1.setPreferredSize(new Dimension(100, 25));
        panType1.setBorder(BorderFactory.createTitledBorder("Type n°1 du pokémon"));
        panType1.add(type1);

        //Type n°2 
        JPanel panType2 = new JPanel();
        panType2.setBackground(Color.white);
        panType2.setPreferredSize(new Dimension(220, 60));
        type2 = new JComboBox<>(types.toArray());
        if (currentPokedex.id_type2 != 0) {
            type2.setSelectedItem(types.get(currentPokedex.id_type2));
        } else {
            type2.setSelectedItem(types.get(0));
        }
        type2.setPreferredSize(new Dimension(100, 25));
        panType2.setBorder(BorderFactory.createTitledBorder("Type n°2 du pokémon"));
        panType2.add(type2);
        
        String[] gen = new String[]{"gen 1", "gen 2", "gen 3", "gen 4", "gen 5", "gen 6", "gen 7"};

        //Génération
        JPanel panGeneration = new JPanel();
        panGeneration.setBackground(Color.white);
        panGeneration.setPreferredSize(new Dimension(220, 60));
        generation = new JComboBox<>(gen);
        generation.setSelectedItem(gen[currentPokedex.generation - 1]);
        generation.setPreferredSize(new Dimension(100, 25));
        panGeneration.setBorder(BorderFactory.createTitledBorder("Génération du pokémon"));
        panGeneration.add(generation);
        
        ArrayList<String> pkmn = new ArrayList();
        ArrayList<Object[]> pkmnTemp = parent.db.getFromDB("SELECT name FROM pokedex ORDER BY id ASC");
        pkmn.add("");
        for (Object[] objects : pkmnTemp) {
            pkmn.add((String) objects[0]);
        }

        //Pré-évolution
        JPanel panPreEvolution = new JPanel();
        panPreEvolution.setBackground(Color.white);
        panPreEvolution.setPreferredSize(new Dimension(220, 60));
        preEvolution = new JComboBox<>(pkmn.toArray());
        if (currentPokedex.id_lower_evolution != 0) {
            preEvolution.setSelectedItem(pkmn.get(currentPokedex.id_lower_evolution));
        } else {
            preEvolution.setSelectedItem(pkmn.get(0));
        }
        preEvolution.setPreferredSize(new Dimension(100, 25));
        panPreEvolution.setBorder(BorderFactory.createTitledBorder("Pré-évolution du pokémon"));
        panPreEvolution.add(preEvolution);

        //Evolution 1
        JPanel panEvolution1 = new JPanel();
        panEvolution1.setBackground(Color.white);
        panEvolution1.setPreferredSize(new Dimension(220, 60));
        evolution1 = new JComboBox<>(pkmn.toArray());
        if (currentPokedex.id_evolution1 != 0) {
            evolution1.setSelectedItem(pkmn.get(currentPokedex.id_evolution1));
        } else {
            evolution1.setSelectedItem(pkmn.get(0));
        }
        evolution1.setPreferredSize(new Dimension(100, 25));
        panEvolution1.setBorder(BorderFactory.createTitledBorder("Évolution n°1 du pokémon"));
        panEvolution1.add(evolution1);

        //Evolution 2
        JPanel panEvolution2 = new JPanel();
        panEvolution2.setBackground(Color.white);
        panEvolution2.setPreferredSize(new Dimension(220, 60));
        evolution2 = new JComboBox<>(pkmn.toArray());
        if (currentPokedex.id_evolution2 != 0) {
            evolution2.setSelectedItem(pkmn.get(currentPokedex.id_evolution2));
        } else {
            evolution2.setSelectedItem(pkmn.get(0));
        }
        evolution2.setPreferredSize(new Dimension(100, 25));
        panEvolution2.setBorder(BorderFactory.createTitledBorder("Évolution n°2 du pokémon"));
        panEvolution2.add(evolution2);
        
        NumberFormat formatPoidsTaille = NumberFormat.getInstance();
        formatPoidsTaille.setMaximumFractionDigits(1);

        //Taille
        JPanel panTaille = new JPanel();
        panTaille.setBackground(Color.white);
        panTaille.setPreferredSize(new Dimension(220, 60));
        taille = new JFormattedTextField(formatPoidsTaille);
        taille.setValue(currentPokedex.height);
        taille.setPreferredSize(new Dimension(100, 25));
        panTaille.setBorder(BorderFactory.createTitledBorder("Taille du pokémon"));
        panTaille.add(taille);

        //Poids
        JPanel panPoids = new JPanel();
        panPoids.setBackground(Color.white);
        panPoids.setPreferredSize(new Dimension(220, 60));
        poids = new JFormattedTextField(formatPoidsTaille);
        poids.setValue(currentPokedex.weight);
        poids.setPreferredSize(new Dimension(100, 25));
        panPoids.setBorder(BorderFactory.createTitledBorder("Poids du pokémon"));
        panPoids.add(poids);
        
        NumberFormat formatPourcentage = NumberFormat.getPercentInstance();

        //Pourcentage de mâle
        JPanel panPourcent = new JPanel();
        panPourcent.setBackground(Color.white);
        panPourcent.setPreferredSize(new Dimension(220, 60));
        pourcentage = new JFormattedTextField(formatPourcentage);
        pourcentage.setValue(currentPokedex.percentage_male);
        pourcentage.setPreferredSize(new Dimension(100, 25));
        panPourcent.setBorder(BorderFactory.createTitledBorder("Pourcentage de mâle"));
        panPourcent.add(pourcentage);
        
        String[] rar = new String[]{"Banal", "Fabuleux", "Légendaire"};

        //Rareté du pokémon
        JPanel panRarete = new JPanel();
        panRarete.setBackground(Color.white);
        panRarete.setPreferredSize(new Dimension(220, 60));
        rarete = new JComboBox<>(rar);
        rarete.setSelectedItem(rar[currentPokedex.is_legendary]);
        rarete.setPreferredSize(new Dimension(100, 25));
        panRarete.setBorder(BorderFactory.createTitledBorder("Rareté du pokémon"));
        panRarete.add(rarete);
        
        String[] ouiNon = new String[]{"Oui", "Non"};

        //Forme Shiny
        JPanel panShiny = new JPanel();
        panShiny.setBackground(Color.white);
        panShiny.setPreferredSize(new Dimension(220, 60));
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
        panMega.setPreferredSize(new Dimension(220, 60));
        mega = new JComboBox<>(ouiNon);
        if (currentPokedex.has_mega) {
            mega.setSelectedItem(ouiNon[0]);
        } else {
            mega.setSelectedItem(ouiNon[1]);
        }
        panMega.setBorder(BorderFactory.createTitledBorder("Le pokémon a une forme méga ?"));
        panMega.add(mega);
        
        JPanel content = new JPanel();
        content.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        content.setBackground(Color.white);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        content.add(panNomfr, c);
        c.gridx = 1;
        content.add(panNomen, c);
        c.gridx = 2;
        content.add(panClassification, c);
        c.gridy = 1;
        content.add(panTalent1, c);
        c.gridx = 1;
        content.add(panType2, c);
        c.gridx = 0;
        content.add(panType1, c);
        c.gridy = 2;
        content.add(panTalent2, c);
        c.gridx = 1;
        content.add(panTalent3, c);
        c.gridx = 2;
        content.add(panTalent4, c);
        c.gridy = 3;
        content.add(panEvolution1, c);
        c.gridx = 1;
        content.add(panPreEvolution, c);
        c.gridx = 0;
        content.add(panGeneration, c);
        c.gridy = 4;
        content.add(panEvolution2, c);
        c.gridx = 1;
        content.add(panTaille, c);
        c.gridx = 2;
        content.add(panPoids, c);
        c.gridy = 5;
        content.add(panShiny, c);
        c.gridx = 1;
        content.add(panRarete, c);
        c.gridx = 0;
        content.add(panPourcent, c);
        c.gridx = 1;
        c.gridy = 6;
        content.add(panMega, c);
        
        JPanel control = new JPanel();
        JButton okBouton = new JButton("OK");
        
        okBouton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                JOptionPane jop = new JOptionPane();
                int rang = jop.showOptionDialog(null, "Êtes-vous sûr ?", "Double vérification", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, ouiNon, ouiNon[1]);
                
            }
            
            public String getTaille() {
                return (taille.getText().equals("")) ? "180" : taille.getText();
            }            
        });
        
        JButton cancelBouton = new JButton("Annuler");
        cancelBouton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                parent.tabbedPane.remove(FenetreModification.this);
            }            
        });
        
        control.add(okBouton);
        control.add(cancelBouton);
        
        this.add(content, BorderLayout.CENTER);
        this.add(control, BorderLayout.SOUTH);
    }    
}
