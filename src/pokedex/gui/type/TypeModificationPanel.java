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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import pokedex.gui.Action;
import pokedex.gui.InfoButton;
import pokedex.gui.MainPanel;

/**
 *
 * @author Spectan
 */
public class TypeModificationPanel extends JPanel implements ActionListener{
    
    JLabel name, enName;
    MainPanel parent;
    int idModif;
    JButton saveButton, discardButton;
    JComboBox<String> vsBug, vsDark, vsDragon, vsElectric, vsFairy, vsFight, vsFire, vsFlying, vsGhost, vsGrass, vsGround, vsIce, vsNormal,vsPoison, vsPsychic, vsRock, vsSteel, vsWater;

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
        name = new JLabel(currentType.name);
        panName.setBorder(BorderFactory.createTitledBorder("Nom du type"));
        panName.add(name);
        
        //Pour le nom anglais
        JPanel panEnName = new JPanel();
        panEnName.setBackground(Color.white);
        enName = new JLabel(currentType.en_name);
        panEnName.setBorder(BorderFactory.createTitledBorder("Nom anglais du type"));
        panEnName.add(enName);
        
        String[] listFaiblesse = new String[]{"Immunisé", "Résistant", "Efficace", "Vulnérable"};
        
    //Pour les faiblesses dans l'ordre (même si le nom du type a été changé)
        JPanel panvsBug = new JPanel();
        panvsBug.setBackground(Color.white);
        vsBug = new JComboBox<>(listFaiblesse);
        vsBug.setSelectedItem(faiblesseToString(currentType.vs[0]));
        panvsBug.setBorder(BorderFactory.createTitledBorder("Contre " + list.get(0).name));
        panvsBug.add(vsBug);
        
        JPanel panvsDark = new JPanel();
        panvsDark.setBackground(Color.white);
        vsDark = new JComboBox<>(listFaiblesse);
        vsDark.setSelectedItem(faiblesseToString(currentType.vs[1]));
        panvsDark.setBorder(BorderFactory.createTitledBorder("Contre " + list.get(1).name));
        panvsDark.add(vsDark);
        
        JPanel panvsDragon = new JPanel();
        panvsDragon.setBackground(Color.white);
        vsDragon = new JComboBox<>(listFaiblesse);
        vsDragon.setSelectedItem(faiblesseToString(currentType.vs[2]));
        panvsDragon.setBorder(BorderFactory.createTitledBorder("Contre " + list.get(2).name));
        panvsDragon.add(vsDragon);
        
        JPanel panvsElectric = new JPanel();
        panvsElectric.setBackground(Color.white);
        vsElectric = new JComboBox<>(listFaiblesse);
        vsElectric.setSelectedItem(faiblesseToString(currentType.vs[3]));
        panvsElectric.setBorder(BorderFactory.createTitledBorder("Contre " + list.get(3).name));
        panvsElectric.add(vsElectric);
        
        JPanel panvsFairy = new JPanel();
        panvsFairy.setBackground(Color.white);
        vsFairy = new JComboBox<>(listFaiblesse);
        vsFairy.setSelectedItem(faiblesseToString(currentType.vs[4]));
        panvsFairy.setBorder(BorderFactory.createTitledBorder("Contre " + list.get(4).name));
        panvsFairy.add(vsFairy);
        
        JPanel panvsFight = new JPanel();
        panvsFight.setBackground(Color.white);
        vsFight = new JComboBox<>(listFaiblesse);
        vsFight.setSelectedItem(faiblesseToString(currentType.vs[5]));
        panvsFight.setBorder(BorderFactory.createTitledBorder("Contre " + list.get(5).name));
        panvsFight.add(vsFight);
        
        JPanel panvsFire = new JPanel();
        panvsFire.setBackground(Color.white);
        vsFire = new JComboBox<>(listFaiblesse);
        vsFire.setSelectedItem(faiblesseToString(currentType.vs[6]));
        panvsFire.setBorder(BorderFactory.createTitledBorder("Contre " + list.get(6).name));
        panvsFire.add(vsFire);
        
        JPanel panvsFlying = new JPanel();
        panvsFlying.setBackground(Color.white);
        vsFlying = new JComboBox<>(listFaiblesse);
        vsFlying.setSelectedItem(faiblesseToString(currentType.vs[7]));
        panvsFlying.setBorder(BorderFactory.createTitledBorder("Contre " + list.get(7).name));
        panvsFlying.add(vsFlying);
        
        JPanel panvsGhost = new JPanel();
        panvsGhost.setBackground(Color.white);
        vsGhost = new JComboBox<>(listFaiblesse);
        vsGhost.setSelectedItem(faiblesseToString(currentType.vs[8]));
        panvsGhost.setBorder(BorderFactory.createTitledBorder("Contre " + list.get(8).name));
        panvsGhost.add(vsGhost);
        
        JPanel panvsGrass = new JPanel();
        panvsGrass.setBackground(Color.white);
        vsGrass = new JComboBox<>(listFaiblesse);
        vsGrass.setSelectedItem(faiblesseToString(currentType.vs[9]));
        panvsGrass.setBorder(BorderFactory.createTitledBorder("Contre " + list.get(9).name));
        panvsGrass.add(vsGrass);
        
        JPanel panvsGround = new JPanel();
        panvsGround.setBackground(Color.white);
        vsGround = new JComboBox<>(listFaiblesse);
        vsGround.setSelectedItem(faiblesseToString(currentType.vs[10]));
        panvsGround.setBorder(BorderFactory.createTitledBorder("Contre " + list.get(10).name));
        panvsGround.add(vsGround);
        
        JPanel panvsIce = new JPanel();
        panvsIce.setBackground(Color.white);
        vsIce = new JComboBox<>(listFaiblesse);
        vsIce.setSelectedItem(faiblesseToString(currentType.vs[11]));
        panvsIce.setBorder(BorderFactory.createTitledBorder("Contre " + list.get(11).name));
        panvsIce.add(vsIce);
        
        JPanel panvsNormal = new JPanel();
        panvsNormal.setBackground(Color.white);
        vsNormal = new JComboBox<>(listFaiblesse);
        vsNormal.setSelectedItem(faiblesseToString(currentType.vs[12]));
        panvsNormal.setBorder(BorderFactory.createTitledBorder("Contre " + list.get(12).name));
        panvsNormal.add(vsNormal);
        
        JPanel panvsPoison = new JPanel();
        panvsPoison.setBackground(Color.white);
        vsPoison = new JComboBox<>(listFaiblesse);
        vsPoison.setSelectedItem(faiblesseToString(currentType.vs[13]));
        panvsPoison.setBorder(BorderFactory.createTitledBorder("Contre " + list.get(13).name));
        panvsPoison.add(vsPoison);
        
        JPanel panvsPsychic = new JPanel();
        panvsPsychic.setBackground(Color.white);
        vsPsychic = new JComboBox<>(listFaiblesse);
        vsPsychic.setSelectedItem(faiblesseToString(currentType.vs[14]));
        panvsPsychic.setBorder(BorderFactory.createTitledBorder("Contre " + list.get(14).name));
        panvsPsychic.add(vsPsychic);
        
        JPanel panvsRock = new JPanel();
        panvsRock.setBackground(Color.white);
        vsRock = new JComboBox<>(listFaiblesse);
        vsRock.setSelectedItem(faiblesseToString(currentType.vs[15]));
        panvsRock.setBorder(BorderFactory.createTitledBorder("Contre " + list.get(15).name));
        panvsRock.add(vsRock);
        
        JPanel panvsSteel = new JPanel();
        panvsSteel.setBackground(Color.white);
        vsSteel = new JComboBox<>(listFaiblesse);
        vsSteel.setSelectedItem(faiblesseToString(currentType.vs[16]));
        panvsSteel.setBorder(BorderFactory.createTitledBorder("Contre " + list.get(16).name));
        panvsSteel.add(vsSteel);
        
        JPanel panvsWater = new JPanel();
        panvsWater.setBackground(Color.white);
        vsWater = new JComboBox<>(listFaiblesse);
        vsWater.setSelectedItem(faiblesseToString(currentType.vs[17]));
        panvsWater.setBorder(BorderFactory.createTitledBorder("Contre " + list.get(17).name));
        panvsWater.add(vsWater);
        
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
        add(panvsBug, c);
        c.gridx++;
        add(panvsDark, c);
        c.gridx = 0;
        c.gridy++;
        add(panvsDragon, c);
        c.gridx++;
        add(panvsElectric, c);
        c.gridx++;
        add(panvsFairy, c);
        c.gridx++;
        add(panvsFight, c);
        c.gridx = 0;
        c.gridy++;
        add(panvsFire, c);
        c.gridx++;
        add(panvsFlying, c);
        c.gridx++;
        add(panvsGhost, c);
        c.gridx++;
        add(panvsGrass, c);
        c.gridx = 0;
        c.gridy++;
        add(panvsGround, c);
        c.gridx++;
        add(panvsIce, c);
        c.gridx++;
        add(panvsNormal, c);
        c.gridx++;
        add(panvsPoison, c);
        c.gridx = 0;
        c.gridy++;
        add(panvsPsychic, c);
        c.gridx++;
        add(panvsRock, c);
        c.gridx++;
        add(panvsSteel, c);
        c.gridx++;
        add(panvsWater, c);
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
        vsBug.setPreferredSize(new Dimension(dimx, dimy));
        vsDark.setPreferredSize(new Dimension(dimx, dimy));
        vsDragon.setPreferredSize(new Dimension(dimx, dimy));
        vsElectric.setPreferredSize(new Dimension(dimx, dimy));
        vsFairy.setPreferredSize(new Dimension(dimx, dimy));
        vsFight.setPreferredSize(new Dimension(dimx, dimy));
        vsFire.setPreferredSize(new Dimension(dimx, dimy));
        vsFlying.setPreferredSize(new Dimension(dimx, dimy));
        vsGhost.setPreferredSize(new Dimension(dimx, dimy));
        vsGrass.setPreferredSize(new Dimension(dimx, dimy));
        vsGround.setPreferredSize(new Dimension(dimx, dimy));
        vsIce.setPreferredSize(new Dimension(dimx, dimy));
        vsNormal.setPreferredSize(new Dimension(dimx, dimy));
        vsPoison.setPreferredSize(new Dimension(dimx, dimy));
        vsPsychic.setPreferredSize(new Dimension(dimx, dimy));
        vsRock.setPreferredSize(new Dimension(dimx, dimy));
        vsSteel.setPreferredSize(new Dimension(dimx, dimy));
        vsWater.setPreferredSize(new Dimension(dimx, dimy));
    }

    public String faiblesseToString(float valeurFaiblesse) {
        if (valeurFaiblesse == 2){
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
    
    public float stringToFaiblesse(String value){
        if (value.equals("Vulnérable")){
            return 2;
        } else if (value.equals("Efficace")) {
            return 1;
        } else if (value.equals("Résistant")) {
            return 0;
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
                String[] colonnesModif = new String[]{"vs_bug", "vs_dark", "vs_dragon", "vs_electric", "vs_fairy",
                    "vs_fight", "vs_fire", "vs_flying", "vs_ghost", "vs_grass", "vs_ground", "vs_ice", "vs_normal", "vs_poison",
                    "vs_psychic", "vs_rock", "vs_steel", "vs_water"};

                Object[] valeursModif = new Object[]{stringToFaiblesse(vsBug.getSelectedItem().toString()),
                                                     stringToFaiblesse(vsDark.getSelectedItem().toString()),
                                                     stringToFaiblesse(vsDragon.getSelectedItem().toString()),
                                                     stringToFaiblesse(vsElectric.getSelectedItem().toString()),
                                                     stringToFaiblesse(vsFairy.getSelectedItem().toString()),
                                                     stringToFaiblesse(vsFight.getSelectedItem().toString()),
                                                     stringToFaiblesse(vsFire.getSelectedItem().toString()),
                                                     stringToFaiblesse(vsFlying.getSelectedItem().toString()),
                                                     stringToFaiblesse(vsGhost.getSelectedItem().toString()),
                                                     stringToFaiblesse(vsGrass.getSelectedItem().toString()),
                                                     stringToFaiblesse(vsGround.getSelectedItem().toString()),
                                                     stringToFaiblesse(vsIce.getSelectedItem().toString()),
                                                     stringToFaiblesse(vsNormal.getSelectedItem().toString()),
                                                     stringToFaiblesse(vsPoison.getSelectedItem().toString()),
                                                     stringToFaiblesse(vsPsychic.getSelectedItem().toString()),
                                                     stringToFaiblesse(vsRock.getSelectedItem().toString()),
                                                     stringToFaiblesse(vsSteel.getSelectedItem().toString()),
                                                     stringToFaiblesse(vsWater.getSelectedItem().toString()),
                                                    };
                parent.db.modify("type", idModif, colonnesModif, valeursModif);
                parent.tabbedPane.setSelectedComponent(parent.typePanel);
                parent.pokedexPanel.goToID(parent.pokedexPanel.idActuel);
                JOptionPane jop = new JOptionPane();
                jop.showMessageDialog(null, "Modification sauvegardée", "Information", JOptionPane.INFORMATION_MESSAGE);

            case DISCARD_TYPE_MODIFICATION:
                parent.tabbedPane.remove(this);
                parent.tabbedPane.setSelectedComponent(parent.typePanel);
                break;
        }
    }
}
