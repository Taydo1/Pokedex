/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex.gui.pokemon;

import javax.swing.JComboBox;
import pokedex.gui.InfoButton;
import pokedex.gui.Label;

/**
 *
 * @author Leon
 */
public class PokemonPanel {
    JComboBox<InfoButton> selector;
    InfoButton type1, type2, ability1, ability2, ability3, ability4, evolution1, evolution2, sousEvolution1;
    Label name, classification, poids, taille, pourcentageMale, type, ability, evolution, sousEvolution;
}
