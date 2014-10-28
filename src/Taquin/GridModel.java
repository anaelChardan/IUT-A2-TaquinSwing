package Taquin;

import java.awt.*;
import java.util.*;


/**
  * GridModel is a class representing a grid
  * It extends from Canvas so is able to represent himself on a screen
  */
public class GridModel {



    public enum Direction {
        North, West, East, South
    }

    public static final int _initialRow = 5;
    public static final int _initialColumn = 5;
    public static final int _maxRows = 12;
    public static final int _maxColumns = 13;
    public static final int _minRows = 2;
    public static final int _minColumns = 2;



    private boolean isComplete = false;
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
    public void fillGrid() {
        fillResolvedGrid();

        shuffleGrid();
        checkIsWellLocated();

    }

    public void fillResolvedGrid() {
        int cpt = 1;
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
    }

    public void shuffleGrid() {
        ArrayList<Token> tokens = new ArrayList<Token>();
        for ( int i = 0; i < nbRows; ++i )
            tokens.addAll(Arrays.asList(this.tokens[i]).subList(0, nbColumns));

        Collections.shuffle(tokens);

        int indexVector = 0;

        for ( int i = 0; i < nbRows; ++i ) {
            for ( int j = 0; j < nbColumns; ++j ) {
                this.tokens[i][j] = tokens.get(indexVector);
                if (this.tokens[i][j] != null) {
                    this.tokens[i][j].setIndRow(i);
                    this.tokens[i][j].setIndColumn(j);
                }
                indexVector++;
            }
        }
    }

    private void checkIsWellLocated() {
        isComplete = true;
        for ( int i = 0; i < nbRows; ++i ) {
            for ( int j = 0; j < nbColumns; ++j ) {
                if ( tokens[i][j] != null ) {
                    if ( (i * nbColumns + j)+1 == tokens[i][j].getNumber() ) {
                        tokens[i][j].setWellLocated(true);
                    } else {
                        tokens[i][j].setWellLocated(false);
                        isComplete = false;
                    }
                }
            }
        }
    }

    public boolean isTokenMovable(Token token) {

        Token tokenNorth = null, tokenSouth = null, tokenEast = null, tokenWest = null;
        boolean skipNorth = false, skipSouth = false, skipEast = false, skipWest = false;

        try {
            tokenNorth = getNextToken(token, Direction.North);
        } catch (TokenOutOfGridException e) {
            skipNorth = true;
        }

        try {
            tokenSouth = getNextToken(token, Direction.South);
        } catch (TokenOutOfGridException e) {
            skipSouth = true;
        }

        try {
            tokenEast = getNextToken(token, Direction.East);
        } catch (TokenOutOfGridException e) {
            skipEast = true;
        }

        try {
            tokenWest = getNextToken(token, Direction.West);
        } catch (TokenOutOfGridException e) {
            skipWest = true;
        }


        return ((!skipNorth && tokenNorth == null) ||
                (!skipSouth && tokenSouth == null) ||
                (!skipWest && tokenWest== null) ||
                (!skipEast && tokenEast == null)
        );
    }

    public Direction getDirectionEmpty( Token token ) {
       Direction rightWay = null;

        try {
            if ( getNextToken(token, Direction.North) == null ) {
                rightWay = Direction.North;
            }
        } catch (TokenOutOfGridException e) {  }

        if ( rightWay == null ) {
            try {
                if ( getNextToken(token, Direction.South) == null ) {
                    rightWay = Direction.South;
                }
            } catch (TokenOutOfGridException e) { }
        }

        if ( rightWay == null ) {
            try {
                if ( getNextToken(token, Direction.East) == null ) {
                    rightWay = Direction.East;
                }
            } catch (TokenOutOfGridException e) { }
        }


        if ( rightWay == null ) {
            try {
                if ( getNextToken(token, Direction.West) == null ) {
                    rightWay = Direction.West;
                }
            } catch (TokenOutOfGridException e) {
                System.out.println(e);
            }
        }

        return rightWay;
    }


    public Token getNextToken(Token token, Direction dir) throws TokenOutOfGridException {
        int row = token.getIndRow();
        int col = token.getIndColumn();

        if ((row == 0 && dir == Direction.North) ||
                (col == 0 && dir == Direction.West) ||
                (row == nbRows - 1 && dir == Direction.South) ||
                (col == nbColumns - 1 && dir == Direction.East))
            throw new TokenOutOfGridException();

        switch (dir) {
            case North: row--;
                break;
            case West: col--;
                break;
            case East: col++;
                break;
            case South: row++;
                break;
        }

        return getToken(row, col);
    }



    ///////////There methodes to move the token////////////////
    public void moveNorth(Token token) {
        move(token, -1, 0);
    }

    public void moveWest(Token token) {
        move(token, 0, -1);
    }

    public void moveEast(Token token) {
        move(token, 0, 1);
    }

    public void moveSouth(Token token) {
        move(token, 1, 0);
    }

    private void move(Token token, int row, int col) {
        tokens[token.getIndRow()][token.getIndColumn()] = null;
        tokens[token.getIndRow() + row ][token.getIndColumn() + col ] = token;
        tokens[token.getIndRow() + row ][token.getIndColumn() + col].setIndRow(token.getIndRow() + row );
        tokens[token.getIndRow()][token.getIndColumn() + col].setIndColumn(token.getIndColumn() + col);
        checkIsWellLocated();
    }


    public GridModel addColumn() {
        if (!this.canAddColumn())
            return this;
        this.nbColumns++;
        fillGrid();
        return this;
    }

    public boolean canAddColumn() {
        return (this.nbColumns < _maxColumns);
    }

    public GridModel addRow() {
        if (!this.canAddRow())
            return this;
        this.nbRows++;
        fillGrid();
        return this;
    }

    public boolean canAddRow() {
        return (this.nbRows < _maxRows);
    }

    public GridModel throwColumn() {
        if (!canThrowColumn())
            return this;
        this.nbColumns--;
        fillGrid();
        return this;
    }

    public boolean canThrowColumn() {
        return (this.nbColumns > _minColumns);
    }

    public GridModel throwRow() {
        if (!canThrowRow())
            return this;
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
        this.nbRows = Math.max(Math.min(nbRows, _maxRows), _minRows);
    }

    public int getNbColumns() {
        return nbColumns;
    }

    public void setNbColumns(int nbColumns) {
        this.nbColumns =  Math.max(Math.min(nbColumns, _maxColumns), _minColumns);
    }

    public Token getToken(int row, int col) {
        return this.tokens[row][col];
    }

    public GridModel setToken(int row, int col, Token token) {
        this.tokens[row][col] = token;
        return this;
    }

    public boolean isComplete() {
        return isComplete;
    }
}
