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
    GET_POKEMON,
    GET_MOVE,
    GET_TRAINER,

    START_POKEDEX_MODIFICATION,
    SAVE_POKEDEX_MODIFICATION,
    DISCARD_POKEDEX_MODIFICATION,
    START_TYPE_MODIFICATION,
    SAVE_TYPE_MODIFICATION,
    DISCARD_TYPE_MODIFICATION,
    START_ABILITY_MODIFICATION,
    SAVE_ABILITY_MODIFICATION,
    DISCARD_ABILITY_MODIFICATION,
    START_MOVE_MODIFICATION,
    SAVE_MOVE_MODIFICATION,
    DISCARD_MOVE_MODIFICATION,
}
