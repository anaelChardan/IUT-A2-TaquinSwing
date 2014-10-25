package NoRules;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Ananas on 25/10/14.
 */
public class Grid extends JPanel {
    private int _nbRows;
    private int _nbColumns;
    private int _axis;
    private int _ordinate;
    private int _columnWidth = 50;
    private int _rowHeight = 50 ;
    private int _initialRow = 5;
    private int _initialColumn = 5;

    public Grid() {
        this._nbRows = this._initialRow;
        this._nbColumns = this._initialColumn;
    }

    @Override
    public void paintComponent(Graphics g) {

        //////////////Calcul of the beginning of the axis and the ordinate////////////////
        _axis = ( this.getSize().width / 2 ) - ( this._nbColumns * this._columnWidth / 2 );
        _ordinate = ( this.getSize().height / 2 ) - ( this._nbRows * this._rowHeight / 2 );

        /////////////Draw horizontal Line////////////
        for ( int i = 0 ; i <= _nbRows ; i ++ )
            g.drawLine( _axis, _ordinate + _rowHeight * i , _axis + ( _nbColumns * _columnWidth ),  _ordinate + _rowHeight * i );

        /////////////Draw Vertical Line//////////////
        for ( int i = 0; i <= _nbColumns; i++ )
            g.drawLine( _axis + _columnWidth * i, _ordinate, _axis + _columnWidth * i, _ordinate + ( _nbRows * _rowHeight ) );

    }

    public int addColumn() {
        this._nbColumns ++;
        repaint();
        if ( ( this._nbColumns + 1 ) * this._columnWidth < this.getSize().width ) {
            return 0;
        } else
            return -1;


    }

    public int addRow() {
        this._nbRows++;
        repaint();
        if ( ( this._nbRows + 1 ) * this._rowHeight < this.getSize().height ) {
            return 0;
        } else
            return -1;

    }

    public int throwColumn() {
        this._nbColumns--;
        repaint();
        if ( this._nbColumns == this._initialColumn ) {
            return -1;
        } else
            return 0;
    }

    public int throwRow() {
        this._nbRows--;
        repaint();
        if ( this._nbRows == this._initialRow ) {
            return -1;
        } else
            return 0;

    }

    public void reset() {
        this._nbRows = this._initialRow;
        this._nbColumns = this._initialColumn;
        repaint();
    }
}
