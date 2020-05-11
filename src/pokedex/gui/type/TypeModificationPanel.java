/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex.gui.type;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import pokedex.gui.MainPanel;

/**
 *
 * @author Spectan
 */
public class TypeModificationPanel extends JPanel{
    
    JTextField name, enName;
    MainPanel parent;
    int idModif;
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
        name = new JTextField(currentType.name);
        panName.setBorder(BorderFactory.createTitledBorder("Nom du type"));
        panName.add(name);
        
        //Pour le nom anglais
        JPanel panEnName = new JPanel();
        panEnName.setBackground(Color.white);
        enName = new JTextField(currentType.en_name);
        panEnName.setBorder(BorderFactory.createTitledBorder("Nom anglais du type"));
        panEnName.add(enName);
        
        String[] listFaiblesse = new String[]{"Immunisé", "Résistant", "Résistant", "Vulnérable"};
        
    //Pour les faiblesses dans l'ordre (même si le nom du type a été changé)
        JPanel panvsBug = new JPanel();
        panvsBug.setBackground(Color.white);
        vsBug = new JComboBox<>(listFaiblesse);
        vsBug.setSelectedItem(faiblesseToString(currentType.vs[0]));
        panvsBug.setBorder(BorderFactory.createTitledBorder("Contre " + list.get(0).name));
        panvsBug.add(vsBug);
        
        
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
    
}
