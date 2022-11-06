package pt.ulusofona.lp2.deisiJungle;

import java.util.ArrayList;
//import java.util.List;

public class Player {
    int identifier;
    Specie specie;
    String name;
    int energy; //Mudar tipo de variavel consoante o que é pedido

    int position; //Mudar de classe caso necessário

    int playingOrder; //Mudar caso necessário

    public Player() {
    }

    public Player(int identifier, String name) {
        this.identifier = identifier;
        this.name = name;
    }

    public Player(int identifier, String name, Specie specie) {
        this.identifier = identifier;
        this.name = name;
        this.specie = specie;
    }

    public boolean isAlreadyTarzanPlaying(ArrayList<Player> alPlayers)
    {
        for (Player player : alPlayers) {
            if(player.specie.getIdentifier() == 'Z'){
                return true;
            }
        }

        return false;
    }

    public Player getPlayerWithSmallerID(ArrayList<Player> players) {

        Player playerWithSmallerId = new Player(Integer.MAX_VALUE, "Dummy");
        for (Player player : players) {
            if(playerWithSmallerId.getIdentifier() > player.getIdentifier()) {
                playerWithSmallerId = player;
            }
        }
        return playerWithSmallerId;
    }

    public boolean checkID(int identifier) {
        return this.identifier + 1 != identifier;
    }

    public int getIdentifier(){
        return identifier;
    }

    public String getName(){
        return name;
    }

    public Specie getSpecie() {
        return specie;
    }
    public int getPosition(){
        return position;
    }

    public int getPlayingOrder(){
        return playingOrder;
    }

    public int getEnergy(){
        return energy;
    }
}
