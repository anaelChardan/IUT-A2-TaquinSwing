package Taquin;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.*;


/**
  * GridModel is a class representing a grid
  * It extends from Canvas so is able to represent himself on a screen
  */
public class GridModel {

    public static final int _initialRow = 5;
    public static final int _initialColumn = 5;
    public static final int _maxRows = 12;
    public static final int _maxColumns = 13;
    public static final int _minRows = 2;
    public static final int _minColumns = 2;
    /**
     * Number of rows in the grid
     */
    private int nbRows;

    /**
     * Number of columns in the grid
     */
    private int nbColumns;

    /**
     * The array which contains the grid's token
     */
    private Token[][] tokens;

    /**
     * Constructor of the class
     * @see GridModel#fillGrid()
     */
    public GridModel() {
        this.nbRows = _initialRow;
        this.nbColumns = _initialColumn;
        fillGrid();
    }

    /**
      *
    */
    private void fillGrid() {
        int cpt = 0;
        tokens = new Token[nbRows][nbColumns];

        double colorProgression = Math.ceil( 255 / ( nbRows * nbColumns) );

        ///////////Fill the grid////////////////
        for ( int i = 0; i < nbRows; ++i ) {
            for ( int j = 0; j < nbColumns; ++j ) {
                if ( i != nbRows - 1 || j != nbColumns - 1 ) {
                    int greenAndBlueValue = ( cpt + 1 ) * (int)colorProgression;
                    Color backgroundColor = new Color( 100, greenAndBlueValue, greenAndBlueValue );
                    tokens[i][j] = new Token( i, j, cpt, backgroundColor );
                    cpt++;
                } else {
                    ////////The last cell in the grid had to be empty////////
                    tokens[i][j] = null;
                }


            }
        }

        shuffleGrid();
        checkIsWellLocated();

    }

    private void shuffleGrid() {
        ArrayList<Token> tokens = new ArrayList<>();
        for ( int i = 0; i < nbRows; ++i )
            tokens.addAll(Arrays.asList(this.tokens[i]).subList(0, nbColumns));

        Collections.shuffle(tokens);

        int indexVector = 0;

        for ( int i = 0; i < nbRows; ++i ) {
            for ( int j = 0; j < nbColumns; ++j ) {
                this.tokens[i][j] = tokens.get(indexVector);
                indexVector++;
            }
        }
    }

    private void checkIsWellLocated() {
        for ( int i = 0; i < nbRows; ++i ) {
            for ( int j = 0; j < nbColumns; ++j ) {
                if ( tokens[i][j] != null ) {
                    if ( i * nbColumns + j == tokens[i][j].get_number() )
                        tokens[i][j].setWellLocated();
                }

            }
        }
    }

    public GridModel addColumn() {
        this.nbColumns++;
        fillGrid();
        return this;
    }

    public boolean canAddColumn() {
        return (this.nbColumns < _maxColumns);
    }

    public GridModel addRow() {
        this.nbRows++;
        fillGrid();
        return this;
    }

    public boolean canAddRow() {
        return (this.nbRows < _maxRows);
    }

    public GridModel throwColumn() {
        this.nbColumns--;
        fillGrid();
        return this;
    }

    public boolean canThrowColumn() {
        return (this.nbColumns > _minColumns);
    }

    public GridModel throwRow() {
        this.nbRows--;
        fillGrid();
        return this;
    }

    public boolean canThrowRow() {
        return (this.nbRows > _minRows);
    }

    public void reset() {
        this.nbRows = _initialRow;
        this.nbColumns = _initialColumn;
        fillGrid();
    }

    public int getNbRows() {
        return nbRows;
    }

    public void setNbRows(int nbRows) {
        this.nbRows = nbRows;
    }

    public int getNbColumns() {
        return nbColumns;
    }

    public void setNbColumns(int nbColumns) {
        this.nbColumns = nbColumns;
    }

    public Token getToken(int row, int col) {
        return this.tokens[row][col];
    }

    public GridModel setToken(int row, int col, Token token) {
        this.tokens[row][col] = token;
        return this;
    }
}
