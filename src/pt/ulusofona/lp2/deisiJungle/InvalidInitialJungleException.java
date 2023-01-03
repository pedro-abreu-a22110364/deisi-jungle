package pt.ulusofona.lp2.deisiJungle;

public class InvalidInitialJungleException extends Exception{
    private String message;
    private boolean invalidPlayer;
    private boolean invalidFood;

    public InvalidInitialJungleException(String message, boolean invalidPlayer, boolean invalidFood) {
        this.message = message;
        this.invalidPlayer = invalidPlayer;
        this.invalidFood = invalidFood;
    }

    public String getMessage(){
        return message;
    }
    public boolean isInvalidPlayer(){
        return invalidPlayer;
    }

    public boolean isInvalidFood(){
        return invalidFood;
    }
}
