package pt.ulusofona.lp2.deisiJungle;

import java.util.ArrayList;

public class House {
    ArrayList<Player> players;
    Food food;
    int position = 0;

    House() {
        players = new ArrayList<>();
        this.position++;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Food getFood() {
        return food;
    }

    public int getPosition() {
        return position;
    }

    public void colocarComida(Food food) {
        this.food = food;
    }
}
