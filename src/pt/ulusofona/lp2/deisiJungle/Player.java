package pt.ulusofona.lp2.deisiJungle;

import java.util.ArrayList;

public class Player {
    int identifier;
    String nome;
    Species specie;

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

}
