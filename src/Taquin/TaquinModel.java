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
