package Taquin;

import java.awt.*;

/**
 * class Token which represented the token in a grid
 */
public class Token {

    /**
     * The color of the token
     */
    private Color backColor;

    /**
     * the index of the row
     */
    private int indRow;

    /**
     * the index of the column
     */
    private int indColumn;

    /**
     * The number of the token
     */
    private int number;

    /**
     * To know if the token is well located
     */
    private boolean isWellLocated = false;

    /**
     * The constuctor of the method
     * @param indRow the index of the row of the token
     * @param indColumn the index of the column or the token
     * @param number the number of the token
     * @param color the color of the token
     */
    public Token( int indRow, int indColumn, int number, Color color ) {
        this.indRow = indRow;
        this.indColumn = indColumn;
        this.number = number;
        setBackColor(color);
    }

    /**
     * The getter of the color according to its location
     * @return its backColor if is not well Located else he return the yellow color
     */
    public Color getBackColor() {
        if ( isWellLocated )
            return Color.yellow;
        else
            return backColor;


    }

    /**
     * Set the backColor of the token
     * @param backColor the future backColor
     */
    public void setBackColor(Color backColor) {
        this.backColor = backColor;
    }

    /**
     * Getter
     * @return the index of the row
     */
    public int getIndRow() {
        return indRow;
    }

    /**
     * Setter
     * @param indRow the future index of the row
     */
    public Token setIndRow(int indRow) {
        this.indRow = indRow;
        return this;
    }

    /**
     * Getter
     * @return index of the column
     */
    public int getIndColumn() {
        return indColumn;
    }


    public Token setIndColumn(int indColumn) {
        this.indColumn = indColumn;
        return this;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setWellLocated(boolean isWellLocated) {
        this.isWellLocated = isWellLocated;
    }

    public boolean isWellLocated() {
        return isWellLocated;
    }
}
