package NoRules;

import java.awt.*;


public class Token {
    private Color _textColor;
    private Color _backColor;
    private int _indRow;
    private int _indColumn;
    private int _number;

    public Token( int _indRow, int _indColumn, int _number, Color _color ) {
        this._indRow = _indRow;
        this._indColumn = _indColumn;
        this._number = _number;
        set_backColor(_color);
    }

    public void setWellPlace() {
        this.set_backColor(Color.YELLOW);
    }

    public Color get_textColor() {
        return _textColor;
    }

    public void set_textColor(Color _textColor) {
        this._textColor = _textColor;
    }

    private static int Brightness(Color c)
    {
        return (int)Math.sqrt(
                c.getRed() * c.getRed() * .241 +
                        c.getGreen() * c.getGreen() * .691 +
                        c.getBlue() * c.getBlue() * .068);
    }

    public Color get_backColor() {
        return _backColor;
    }

    public void set_backColor(Color _backColor) {
        this._backColor = _backColor;
        this._textColor = Brightness( this._backColor ) < 130 ? Color.white : Color.black;
    }


    public int get_indRow() {
        return _indRow;
    }

    public void set_indRow(int _indRow) {
        this._indRow = _indRow;
    }

    public int get_indColumn() {
        return _indColumn;
    }

    public void set_indColumn(int _indColumn) {
        this._indColumn = _indColumn;
    }

    public int get_number() {
        return _number;
    }

    public void set_number(int _number) {
        this._number = _number;
    }
}
