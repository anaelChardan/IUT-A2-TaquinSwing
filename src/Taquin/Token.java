package Taquin;

import java.awt.*;


public class Token {
    private Color backColor;
    private int indRow;
    private int indColumn;
    private int number;



    private boolean isWellLocated = false;

    public void setWellLocated(boolean isWellLocated) {
        this.isWellLocated = isWellLocated;
    }

    public Token( int indRow, int indColumn, int number, Color _color ) {
        this.indRow = indRow;
        this.indColumn = indColumn;
        this.number = number;
        setBackColor(_color);
    }


    public Color getBackColor() {
        if ( isWellLocated )
            return Color.yellow;
        else
            return backColor;


    }


    public void setBackColor(Color backColor) {
        this.backColor = backColor;
    }


    public int getIndRow() {
        return indRow;
    }

    public void setIndRow(int indRow) {
        this.indRow = indRow;
    }

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
}
