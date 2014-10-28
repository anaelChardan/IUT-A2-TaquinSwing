package Taquin;


public class TokenOutOfGridException extends Exception {
    @Override
    public String toString() {
        return "Try to leave the grid";
    }

}
