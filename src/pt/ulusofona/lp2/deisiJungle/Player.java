package pt.ulusofona.lp2.deisiJungle;

import java.util.ArrayList;
//import java.util.List;

public class Player {
    int identifier;
    String nome;
    Species specie;
    int energy; //Mudar tipo de variavel consoante o que é pedido

    public Player() {
    }

    public Player(int identifier, String nome) {
        this.identifier = identifier;
        this.nome = nome;
    }

    public Player(int identifier, String nome, Species specie) {
        this.identifier = identifier;
        this.nome = nome;
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
            if(playerWithSmallerId.getIdentifier() > player.getIdentifier())
            {
                playerWithSmallerId = player;
            }
        }
        return playerWithSmallerId;
    }

    public boolean checkID(int identifier)
    {
        return this.identifier + 1 != identifier;
    }
    public int getIdentifier(){
        return identifier;
    }
    public String getNome(){
        return nome;
    }

    public int getEnergy(){
        return energy;
    }
}
