package Taquin;

import java.util.Observable;

/**
 * Class to represente the TaquinModel
 */
public class TaquinModel extends Observable {

    /**
     * The grid model
     */
    private GridModel grid;

    /**
     * The contructor of the taquin model
     */
    public TaquinModel() {
        this.grid = new GridModel();
    }

    /**
     * Getter of the grid
     * @return the grid
     */
    public GridModel getGrid() {
        return grid;
    }

    /**
     * The setter of the grid
     * @param grid
     */
    public void setGrid(GridModel grid) {
        this.grid = grid;
        notifyChanges();
    }

    /**
     * The method to move the token into the grid
     * @param token
     * @param direction
     */
    public void moveToken(Token token, GridModel.Direction direction ) {
        switch ( direction ) {
            case North:
                this.grid.moveNorth(token);
                break;
            case West:
                this.grid.moveWest(token);
                break;
            case East:
                this.grid.moveEast(token);
                break;
            case South:
                this.grid.moveSouth(token);
                break;
        }
        notifyChanges();
    }

    /**
     * The method to notify the changement
     */
    public void notifyChanges() {
        setChanged();
        notifyObservers();
    }

    /**
     * The method to add a column
     */
    public void addColumn() {
        this.grid.addColumn();
        notifyChanges();
    }

    /**
     * The method to add a row
     */
    public void addRow() {
        this.grid.addRow();
        notifyChanges();
    }

    /**
     * The method to throw a column
     */
    public void throwColumn() {
        this.grid.throwColumn();
        notifyChanges();
    }

    /**
     * The method to throw a row
     */
    public void throwRow() {
        this.grid.throwRow();
        notifyChanges();
    }

    /**
     * The method to reset the grid
     */
    public void reset() {
        this.grid.reset();
        notifyChanges();
    }
}
