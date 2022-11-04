package pt.ulusofona.lp2.deisiJungle;

import javax.swing.*;
import java.util.ArrayList;

public class GameManager {

    //fun obg

    public GameManager(){

    }

    public String[][] getSpecies() {
        return new String[5][];
    }

    public boolean createInitialJungle(int jungleSize,int initialEnergy,String[][] playersInfo) {
        return true;
    }

    public int[] getPlayerIds(int squareNr) {
        return new int[5];
    }

    public String[] getSquareInfo(int squareNr) {
        return new String[5];
    }

    public String[] getPlayerInfo(int playerId) {
        return new String[5];
    }

    public String[] getCurrentPlayerInfo() {
        return new String[5];
    }

    public String[][] getPlayersInfo() {
        return new String[5][];
    }

    public boolean moveCurrentPlayer(int nrSquares,boolean bypassValidations) {
        return true;
    }

    public String[] getWinnerInfo() {
        return new String[5];
    }

    public ArrayList<String> getGameResults() {
        return new ArrayList<String>();
    }

    public JPanel getAuthorsPanel() {
        return new JPanel();
    }

    public String whoIsTaborda() {
        return "";
    }

}
