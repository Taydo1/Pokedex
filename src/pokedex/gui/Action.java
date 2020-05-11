/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex.gui;

/**
 *
 * @author Leon
 */
public enum Action {
    UP,
    DOWN,
    GO,
    GO_NOM,

    CHANGE_USER,
    IMAGE_NORMAL,
    IMAGE_SHINY,
    IMAGE_MEGA,

    GET_POKEDEX,
    GET_TYPE,
    GET_COMBINED_TYPE,
    GET_ABILITY,
    
    NOUVEAU_TYPE,
    
    SUPPRESSION_TYPE1,
    SUPPRESSION_TYPE2,

    START_POKEDEX_MODIFICATION,
    SAVE_POKEDEX_MODIFICATION,
    DISCARD_POKEDEX_MODIFICATION,
    START_MODIFICATION_TYPE1,
    START_MODIFICATION_TYPE2;
}
