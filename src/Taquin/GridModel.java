package Taquin;

import java.awt.*;
import java.util.*;


/**
 * GridModel is a class representing a grid
 * It extends from Canvas so is able to represent himself on a screen
 */
public class GridModel {


    /**
     * Enumeration to block the choice of the direction
     */
    public enum Direction {
        North, West, East, South
    }

    /**
     * The number initial of row at start
     */
    public static final int _initialRow = 5;

    /**
     * The number initial of column at start
     */
    public static final int _initialColumn = 5;

    /**
     * The number maximum of row during the game
     */
    public static final int _maxRows = 12;

    /**
     * The number maximum of column during the game
     */
    public static final int _maxColumns = 13;

    /**
     * The number minimum of row during the game
     */
    public static final int _minRows = 2;

    /**
     * The number minimum of column during the game
     */
    public static final int _minColumns = 2;


    /**
     * Boolean to know if the level is resolved
     */
    private boolean isComplete = false;

    /**
     * Number of rows in the grid
     *
     * @see GridModel#getNbRows()
     * @see GridModel#setNbRows(int)
     */
    private int nbRows;

    /**
     * Number of columns in the grid
     *
     * @see GridModel#getNbColumns()
     * @see GridModel#setNbColumns(int)
     */
    private int nbColumns;

    /**
     * The array which contains the grid's token
     *
     * @see Token
     * @see GridModel#getToken(int, int)
     */
    private Token[][] tokens;

    /**
     * Constructor of the class
     *
     * @see GridModel#fillGrid()
     */
    public GridModel() {
        this.nbRows = _initialRow;
        this.nbColumns = _initialColumn;
        fillGrid();
    }

    /**
     * Method to fill the grid
     *
     * @see GridModel#fillResolvedGrid()
     * @see GridModel#shuffleGrid()
     * @see GridModel#checkIsWellLocated()
     */
    public void fillGrid() {
        fillResolvedGrid();

        shuffleGrid();
        checkIsWellLocated();

    }

    /**
     * Method to fill the grid in right order with gradient color
     */
    public void fillResolvedGrid() {
        int cpt = 1;
        tokens = new Token[nbRows][nbColumns];

        double colorProgression = Math.ceil(255 / (nbRows * nbColumns));

        ///////////Fill the grid////////////////
        for (int i = 0; i < nbRows; ++i) {
            for (int j = 0; j < nbColumns; ++j) {
                if (i != nbRows - 1 || j != nbColumns - 1) {
                    int greenAndBlueValue = (cpt + 1) * (int) colorProgression;
                    Color backgroundColor = new Color(100, greenAndBlueValue, greenAndBlueValue);
                    tokens[i][j] = new Token(i, j, cpt, backgroundColor);
                    cpt++;
                } else {
                    ////////The last cell in the grid had to be empty////////
                    tokens[i][j] = null;
                }


            }
        }
    }

    /**
     * Method to shuffle the grid
     */
    public void shuffleGrid() {

        ArrayList<Token> tokens = new ArrayList<Token>();

        //Convert the tab into an ArrayList in order to use the Collection.shuffle
        for (int i = 0; i < nbRows; ++i)
            tokens.addAll(Arrays.asList(this.tokens[i]).subList(0, nbColumns));

        Collections.shuffle(tokens);

        int indexVector = 0;

        //Convert the ArrayList into a tab 2D
        for (int i = 0; i < nbRows; ++i) {
            for (int j = 0; j < nbColumns; ++j) {
                this.tokens[i][j] = tokens.get(indexVector);
                if (this.tokens[i][j] != null) {
                    this.tokens[i][j].setIndRow(i);
                    this.tokens[i][j].setIndColumn(j);
                }
                indexVector++;
            }
        }
    }

    /**
     * Method to know if a token is well located
     */
    private void checkIsWellLocated() {
        isComplete = true;
        for (int i = 0; i < nbRows; ++i) {
            for (int j = 0; j < nbColumns; ++j) {
                if (tokens[i][j] != null) {
                    if ((i * nbColumns + j) + 1 == tokens[i][j].getNumber()) {
                        tokens[i][j].setWellLocated(true);
                    } else {
                        tokens[i][j].setWellLocated(false);
                        isComplete = false;
                    }
                }
            }
        }
    }

    /**
     * Method to know if a token is movable
     *
     * @param token The token tested
     * @return the result
     * @see TokenOutOfGridException
     */
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
                (!skipWest && tokenWest == null) ||
                (!skipEast && tokenEast == null)
        );
    }

    /**
     * Method to know where is the blank into the grid
     *
     * @param token the token we're looking around
     * @return Direction
     * @see TokenOutOfGridException
     */
    public Direction getDirectionToEmpty(Token token) {
        Direction rightWay = null;

        try {
            if (getNextToken(token, Direction.North) == null) {
                rightWay = Direction.North;
            }
        } catch (TokenOutOfGridException e) {
        }

        if (rightWay == null) {
            try {
                if (getNextToken(token, Direction.South) == null) {
                    rightWay = Direction.South;
                }
            } catch (TokenOutOfGridException e) {
            }
        }

        if (rightWay == null) {
            try {
                if (getNextToken(token, Direction.East) == null) {
                    rightWay = Direction.East;
                }
            } catch (TokenOutOfGridException e) {
            }
        }


        if (rightWay == null) {
            try {
                if (getNextToken(token, Direction.West) == null) {
                    rightWay = Direction.West;
                }
            } catch (TokenOutOfGridException e) {
                System.out.println(e);
            }
        }

        return rightWay;
    }


    /**
     * Method to get the next token at a direction
     *
     * @param token the token we're looking around
     * @param dir   the direction we're looking around
     * @see Token#getIndRow()
     * @see Token#getIndColumn()
     * @see GridModel#getToken(int, int)
     * @return The next token
     * @throws TokenOutOfGridException
     */
    public Token getNextToken(Token token, Direction dir) throws TokenOutOfGridException {
        int row = token.getIndRow();
        int col = token.getIndColumn();

        if ((row == 0 && dir == Direction.North) ||
                (col == 0 && dir == Direction.West) ||
                (row == nbRows - 1 && dir == Direction.South) ||
                (col == nbColumns - 1 && dir == Direction.East))
            throw new TokenOutOfGridException();

        switch (dir) {
            case North:
                row--;
                break;
            case West:
                col--;
                break;
            case East:
                col++;
                break;
            case South:
                row++;
                break;
        }

        return getToken(row, col);
    }


    /**
     * Syntaxic sugar to call a function with special parameters
     * @see GridModel#moveToken(Token, int, int)
     * @param token
     */
    public void moveNorth(Token token) {
        moveToken(token, -1, 0);
    }

    /**
     * Syntaxic sugar to call a function with special parameters
     * @see GridModel#moveToken(Token, int, int)
     * @param token
     */
    public void moveWest(Token token) {
        moveToken(token, 0, -1);
    }

    /**
     * Syntaxic sugar to call a function with special parameters
     * @see GridModel#moveToken(Token, int, int)
     * @param token
     */
    public void moveEast(Token token) {
        moveToken(token, 0, 1);
    }

    /**
     * Syntaxic sugar to call a function with special parameters
     * @see GridModel#moveToken(Token, int, int)
     * @param token
     */
    public void moveSouth(Token token) {
        moveToken(token, 1, 0);
    }

    /**
     * Method to move a token into the grid
     * @see Token#getIndColumn()
     * @see Token#getIndRow()
     * @see Token#setIndColumn(int)
     * @see Token#setIndRow(int)
     * @param token the token we're moving
     * @param row the row where the token is
     * @param col the column where the token is
     */
    private void moveToken(Token token, int row, int col) {
        tokens[token.getIndRow()][token.getIndColumn()] = null;
        tokens[token.getIndRow() + row][token.getIndColumn() + col] = token;
        tokens[token.getIndRow() + row][token.getIndColumn() + col].setIndRow(token.getIndRow() + row);
        tokens[token.getIndRow()][token.getIndColumn() + col].setIndColumn(token.getIndColumn() + col);
        checkIsWellLocated();
    }

    /**
     * Method to add a column to the grid
     * @see GridModel#canAddColumn()
     * @see GridModel#fillGrid()
     * @return this
     */
    public GridModel addColumn() {
        if (!this.canAddColumn())
            return this;
        this.nbColumns++;
        fillGrid();
        return this;
    }

    /**
     * Method to know if it's possible to add column to the grid
     * @return the result
     */
    public boolean canAddColumn() {
        return (this.nbColumns < _maxColumns);
    }

    /**
     * Method to add a row to the grid
     * @see GridModel#canAddRow()
     * @see GridModel#fillGrid()
     * @return this
     */
    public GridModel addRow() {
        if (!this.canAddRow())
            return this;
        this.nbRows++;
        fillGrid();
        return this;
    }

    /**
     * Method to know if it's possible to add row to the grid
     * @return the result
     */
    public boolean canAddRow() {
        return (this.nbRows < _maxRows);
    }

    /**
     * Method to throw a column to the grid
     * @see GridModel#canThrowColumn()
     * @see GridModel#fillGrid()
     * @return this
     */
    public GridModel throwColumn() {
        if (!canThrowColumn())
            return this;
        this.nbColumns--;
        fillGrid();
        return this;
    }

    /**
     * Method to know if it's possible to throw column to the grid
     * @return the result
     */
    public boolean canThrowColumn() {
        return (this.nbColumns > _minColumns);
    }

    /**
     * Method to throw a row to the grid
     * @see GridModel#canThrowRow()
     * @see GridModel#fillGrid()
     * @return this
     */
    public GridModel throwRow() {
        if (!canThrowRow())
            return this;
        this.nbRows--;
        fillGrid();
        return this;
    }

    /**
     * Method to know if it's possible to throw row to the grid
     * @return the result
     */
    public boolean canThrowRow() {
        return (this.nbRows > _minRows);
    }

    /**
     * Method to put the grid in initial form
     * @see GridModel#fillGrid()
     */
    public void reset() {
        this.nbRows = _initialRow;
        this.nbColumns = _initialColumn;
        fillGrid();
    }

    /**
     * Method to return the actual number of row
     * @return the number of row
     */
    public int getNbRows() {
        return nbRows;
    }

    /**
     * Method to set the actual number of row
     */
    public void setNbRows(int nbRows) {
        this.nbRows = Math.max(Math.min(nbRows, _maxRows), _minRows);
    }

    /**
     * Method to return the actual number of column
     * @return the number of column
     */
    public int getNbColumns() {
        return nbColumns;
    }

    /**
     * Method to set the actual number of column
     */
    public void setNbColumns(int nbColumns) {
        this.nbColumns = Math.max(Math.min(nbColumns, _maxColumns), _minColumns);
    }

    /**
     * Method to get a token placed into our parameters
     * @param row
     * @param col
     * @return
     */
    public Token getToken(int row, int col) {
        return this.tokens[row][col];
    }

    /**
     * Method to set a place to a token
     * @param row
     * @param col
     * @param token
     * @return
     */
    public GridModel setToken(int row, int col, Token token) {
        this.tokens[row][col] = token;
        return this;
    }

    /**
     * To know if the grid is Completed
     * @return the result
     */
    public boolean isCompleted() {
        return isComplete;
    }
}
