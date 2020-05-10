/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex.gui.type;

import java.awt.Color;
import javax.swing.JPanel;
import pokedex.gui.InfoButton;
import pokedex.gui.Label;

/**
 *
 * @author Leon
 */
public class TypePanel extends JPanel{

    static Color veryStrong = Color.GREEN;
    static Color strong = Color.CYAN;
    static Color weak = Color.MAGENTA;
    static Color veryWeak = Color.RED;
    InfoButton type1, type2, ability1, ability2, ability3, ability4, evolution1, evolution2, sousEvolution1;
    Label idNom, classification, poids, taille, pourcentageMale, type, ability, evolution, sousEvolution;
}
