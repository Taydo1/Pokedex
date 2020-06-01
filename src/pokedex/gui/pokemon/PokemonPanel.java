/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex.gui.pokemon;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import pokedex.database.Database;
import pokedex.database.Pokedex;
import pokedex.database.Pokemon;
import pokedex.database.Trainer;
import pokedex.gui.Action;
import pokedex.gui.ImagePanel;
import pokedex.gui.widgets.InfoButton;
import pokedex.gui.MainPanel;

/**
 *
 * @author Leon
 */
public class PokemonPanel extends JPanel implements ActionListener {

    PokemonTopPanel topPanel;
    public PokemonBottomPanel bottomPanel;
    ImagePanel imagePanel;
    MainPanel parent;
    Database db;
    int currentId;

    public PokemonPanel(Database db, MainPanel parent) {
        this.parent = parent;
        this.db = db;
        setLayout(new BorderLayout());
        topPanel = new PokemonTopPanel(db, this);
        imagePanel = new ImagePanel();
        bottomPanel = new PokemonBottomPanel(db, this);
        add(topPanel, BorderLayout.NORTH);
        add(imagePanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        
        setId(-1);
    }

    public void setUser(String user) {
        bottomPanel.setUser(user);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        InfoButton source;
        switch (Action.valueOf(e.getActionCommand())) {
            case GET_POKEMON:
                source = (InfoButton) topPanel.selector.getSelectedItem();
                setId(source.getId());
                break;
            case START_MODIFICATION:
                parent.addTab(
                        new PokemonModifInsertPanel(bottomPanel.modification.getId(), parent),
                        "Modification de " + db.getFromDB("SELECT name FROM pokemon WHERE id=" + bottomPanel.modification.getId()).get(0)[0],
                        MainPanel.PROFESSOR_TAB
                );
                break;
            case START_INSERTION:
                parent.addTab(
                        new PokemonModifInsertPanel(parent),
                        "Naissance d'un Pokemon", 
                        MainPanel.PROFESSOR_TAB
                );
                break;
                
                //Action de suppression d'un pokemon
            case DELETE:
                
                int del_id[] = {bottomPanel.delete.getId()};    //on récupère l'ID du pokémon à supprimer
                Pokemon Del_pokemon = parent.db.getFromDB("SELECT * from pokemon WHERE id = " + bottomPanel.delete.getId(), Pokemon.class).get(0); // On récupère le pokémon à supprimer
                
                // On test ensuite le pokémon appartient à un dresseur, si c'est le cas, on va devoir le supprimer de son équipe avant de le supprimer lui-même
                if(Del_pokemon.id_trainer!=0) 
                {
                    // C'est le cas, on récupère donc ce dresseur 
                    Trainer currentTrainer = parent.db.getFromDB("SELECT * from trainer WHERE id = " + topPanel.trainer.getId(), Trainer.class).get(0);
                    String Nothing[] = {null};  // Valeur "null" qui sert pour l'action modify ci-dessous et qui requiert un tableau de string
                    
                    //On va ici parcourir tous les pokemons dans l'équipe dresseur afin  
                    //de supprimer le pokémon de son equipe pour éviter les erreurs d'appel de clé étrangères
                    for(int k=0; k<6; k++){
                        if(bottomPanel.delete.getId()==currentTrainer.id_pokemon[k]){
                            String PokeModif[] = {"id_pokemon"+(k+1)};  //La ligne qui correspond à l'ID du pkm dans Trainer (numéroté de 1 à 6
                            currentTrainer.id_pokemon[k] = -1;  //La valeur -1 est ensuite transformée en null automatiquement
                            db.modify("trainer",topPanel.trainer.getId(), PokeModif , Nothing);//Modification de la table Trainer pour metre null à la place de l'id du pkm à supprimer
                        }
                    }
                }
                
                topPanel.selector.removeItemAt(topPanel.findSelectorId(bottomPanel.delete.getId()));    //On retire le Pokémon de la barre de la liste déroulante.
                db.deleteFromID("pokemon", del_id);    //Et enfin on peut supprimer le Pokémon de la base de données.
                
                break;
                
            case EVOLUTION:
                Pokemon currentPokemon = parent.db.getFromDB("SELECT * from pokemon WHERE id = " + bottomPanel.evolution.getId(), Pokemon.class).get(0);
                Pokedex pokedex = parent.db.getFromDB("SELECT * from pokedex WHERE id = " + currentPokemon.id_pokedex, Pokedex.class).get(0);
                if( pokedex.id_evolution2 != 0){
                    ArrayList<Object[]> evo1 = parent.db.getFromDB("SELECT name from pokedex WHERE id = " + pokedex.id_evolution1);
                    ArrayList<Object[]> evo2 = parent.db.getFromDB("SELECT name from pokedex WHERE id = " + pokedex.id_evolution2);
                    String[] evoDispo = new String[]{(String) evo1.get(0)[0], (String) evo2.get(0)[0]};
                    JOptionPane jop = new JOptionPane();
                    int rang = jop.showOptionDialog(null, 
                                "Veuillez indiquer en quoi " + currentPokemon.name + " évolue",
                                "" + currentPokemon.name + " évolue !",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                evoDispo,
                                evoDispo[0]);
                    if (rang == 0){
                        currentPokemon.id_pokedex = pokedex.id_evolution1;
                    } else {
                        currentPokemon.id_pokedex = pokedex.id_evolution2;
                    }
                } else {
                    currentPokemon.id_pokedex = pokedex.id_evolution1;
                }
                currentPokemon.health = (int) currentPokemon.health * 12 / 10;
                Pokedex pokedexEvo = parent.db.getFromDB("SELECT * from pokedex WHERE id = " + currentPokemon.id_pokedex, Pokedex.class).get(0);
                if (pokedexEvo.id_ability4 != 0){
                    switch ((int) (Math.random() * 5)){
                        case 1:
                            currentPokemon.id_ability = pokedexEvo.id_ability1;
                            break;
                        case 2:
                            currentPokemon.id_ability = pokedexEvo.id_ability2;
                            break;
                        case 3:
                            currentPokemon.id_ability = pokedexEvo.id_ability3;
                            break;
                        case 4:
                            currentPokemon.id_ability = pokedexEvo.id_ability4;
                            break;
                    }
                } else if (pokedexEvo.id_ability3 != 0){
                    switch ((int) (Math.random() * 4)){
                        case 1:
                            currentPokemon.id_ability = pokedexEvo.id_ability1;
                            break;
                        case 2:
                            currentPokemon.id_ability = pokedexEvo.id_ability2;
                            break;
                        case 3:
                            currentPokemon.id_ability = pokedexEvo.id_ability3;
                            break;
                    }
                } else if (pokedexEvo.id_ability2 != 0){
                    switch ((int) (Math.random() * 3)){
                        case 1:
                            currentPokemon.id_ability = pokedexEvo.id_ability1;
                            break;
                        case 2:
                            currentPokemon.id_ability = pokedexEvo.id_ability2;
                            break;
                    }
                } else {
                    currentPokemon.id_ability = pokedexEvo.id_ability1;
                }
                if (currentPokemon.name.equals(pokedex.name)){
                    ArrayList<Object[]> evo1 = parent.db.getFromDB("SELECT name from pokedex WHERE id = " + currentPokemon.id_pokedex);
                    currentPokemon.name = (String) evo1.get(0)[0];
                }
                currentPokemon.modifyInDB(db);
                this.updatePokemonDispo();
                this.setId(bottomPanel.evolution.getId());
                bottomPanel.repaint();
                break;
            case LVLUP:
                
                Pokemon currentPkmn = parent.db.getFromDB("SELECT * from pokemon WHERE id = " + bottomPanel.lvlUp.getId(), Pokemon.class).get(0);
                currentPkmn.level = currentPkmn.level + 1;
                currentPkmn.health = currentPkmn.health + (int) (Math.random() * 6);
                currentPkmn.modifyInDB(db);
                bottomPanel.setLVL(currentPkmn.level);
                this.setId(bottomPanel.lvlUp.getId());
                
                break;
        }
                
    }

    public void setId(int id) {
        topPanel.setId(id);
        currentId = id;

        ArrayList<Object[]> infos = db.getFromDB("SELECT id_pokedex, is_shiny FROM pokemon p WHERE p.id=" + id);
        if (!infos.isEmpty()) {
            String imgName;
            if((Boolean)infos.get(0)[1]){
                imgName="image_shiny";
            }else{
                imgName="image";
            }
            Image image = db.getImage("SELECT "+imgName+" FROM pokedex WHERE id=" + infos.get(0)[0]);
            imagePanel.setImage(image);
        }else{
            imagePanel.setImage(null);
        }
        bottomPanel.setId(id);
    }
    
    public void update(){
        setId(currentId);
    }

    public void updatePokemonDispo() {
        topPanel.selector.removeActionListener(this);
        topPanel.selector.removeAllItems();
        InfoButton selectorButton;
        ArrayList<Object[]> pokemonList = db.getFromDB("SELECT id,name FROM pokemon ORDER BY id ASC");
        pokemonList.add(0, new Object[]{0, ""});
        for (int i = 0; i < pokemonList.size(); i++) {
            selectorButton = new InfoButton((String) pokemonList.get(i)[1], (Integer) pokemonList.get(i)[0]);
            topPanel.selector.addItem(selectorButton);
        }
        topPanel.selector.addActionListener(this);
    }
}
