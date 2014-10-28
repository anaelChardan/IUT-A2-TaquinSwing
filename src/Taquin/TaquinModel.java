package Taquin;

import java.util.Observable;

/**
 * Created by martylamoureux on 27/10/14.
 */
public class TaquinModel extends Observable {
    private GridModel grid;

    public TaquinModel() {
        this.grid = new GridModel();
    }

    public GridModel getGrid() {
        return grid;
    }

    public void setGrid(GridModel grid) {
        this.grid = grid;
        notifyChanges();
    }

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

    public void notifyChanges() {
        setChanged();
        notifyObservers();
    }

    public void addColumn() {
        this.grid.addColumn();
        notifyChanges();
    }

    public void addRow() {
        this.grid.addRow();
        notifyChanges();
    }

    public void throwColumn() {
        this.grid.throwColumn();
        notifyChanges();
    }

    public void throwRow() {
        this.grid.throwRow();
        notifyChanges();
    }

    public void reset() {
        this.grid.reset();
        notifyChanges();
    }
}
