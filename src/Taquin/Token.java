package Taquin;

import java.awt.*;


public class Token {
    private Color textColor;
    private Color backColor;
    private int indRow;
    private int indColumn;
    private int number;

    public Token( int indRow, int indColumn, int number, Color _color ) {
        this.indRow = indRow;
        this.indColumn = indColumn;
        this.number = number;
        setBackColor(_color);
    }

    public void setWellLocated() {
        this.setBackColor(Color.YELLOW);
    }

    public Color getTextColor() {
        return textColor;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }

    private static int Brightness(Color c)
    {
        return (int)Math.sqrt(
                c.getRed() * c.getRed() * .241 +
                        c.getGreen() * c.getGreen() * .691 +
                        c.getBlue() * c.getBlue() * .068);
    }

    public Color getBackColor() {
        return backColor;
    }

    public void setBackColor(Color backColor) {
        this.backColor = backColor;
        this.textColor = Brightness( this.backColor) < 130 ? Color.white : Color.black;
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
