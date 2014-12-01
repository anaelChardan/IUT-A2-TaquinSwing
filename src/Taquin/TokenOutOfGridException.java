package Taquin;


public class TokenOutOfGridException extends Exception {
    private static final long serialVersionUID = 1L;
    @Override
    public String toString() {
        return "Try to leave the grid";
    }

}
