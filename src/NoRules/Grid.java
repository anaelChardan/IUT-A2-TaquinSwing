package NoRules;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.*;

/**
 * Created by Ananas on 25/10/14.
 */
public class Grid extends Canvas {
    private int _nbRows;
    private int _nbColumns;
    private int _axis;
    private int _ordinate;

    private Token[][] _tokenWhole;

    public Grid() {
        this._nbRows = Constant._initialRow;
        this._nbColumns = Constant._initialColumn;
        fillGrid();
    }

    private void fillGrid() {
        int cpt = 0;
        _tokenWhole = new Token[_nbRows][_nbColumns];

        double colorProgression = Math.ceil( 255 / ( _nbRows * _nbColumns ) );

        ///////////Fill the grid////////////////
        for ( int i = 0; i < _nbRows; ++i ) {
            for ( int j = 0; j < _nbColumns; ++j ) {
                if ( i != _nbRows - 1 || j != _nbColumns - 1 ) {
                    int greenAndBlueValue = ( cpt + 1 ) * (int)colorProgression;
                    Color backgroundColor = new Color( 100, greenAndBlueValue, greenAndBlueValue );
                    _tokenWhole[i][j] = new Token( i, j, cpt, backgroundColor );
                    cpt++;
                } else {
                    ////////The last cell in the grid had to be empty////////
                    _tokenWhole[i][j] = null;
                }


            }
        }

        //shuffleGrid();


    }

    private void shuffleGrid() {
        Vector<Token> tokenVector = new Vector<>();
        for ( int i = 0; i < _nbRows; ++i ) {
            tokenVector.addAll(Arrays.asList(_tokenWhole[i]).subList(0, _nbColumns));
        }

        Collections.shuffle(tokenVector);

        int indexVector = 0;

        for ( int i = 0; i < _nbRows; ++i ) {
            for ( int j = 0; j < _nbColumns; ++j ) {
                _tokenWhole[i][j] = tokenVector.get(indexVector);
                indexVector++;
            }
        }
    }

    //////To convert an Index to represent it on the graphic /////////
    private int convertIndexXToPixel(int i) {
        return Constant._columnWidth * i + _axis;
    }

    private int convertIndexYToPixel(int j) {
        return Constant._rowHeight * j + _ordinate;
    }

    private double convertXPixel( int xpixel ) {
        if ( xpixel >= _axis  && xpixel <= ( _axis + ( _nbColumns * Constant._columnWidth ) ) ) {
            return Math.ceil( ( xpixel - _axis ) / ( Constant._columnWidth ) );
        }
        return -1;
    }

    private double convertYPixel( int ypixel ) {
        if ( ypixel >= _ordinate && ypixel <= ( _ordinate + ( _nbRows * Constant._rowHeight ) ) ) {
            return Math.ceil( ( ypixel - _ordinate ) / ( Constant._rowHeight ) );
        }

        return -1;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        //////////////Calcul of the beginning of the axis and the ordinate////////////////
        _axis = ( this.getSize().width / 2 ) - ( this._nbColumns * Constant._columnWidth / 2 );
        _ordinate = ( this.getSize().height / 2 ) - ( this._nbRows * Constant._rowHeight / 2 );

        ///////////// Draw The Token Whole/////////////////
        for ( int i = 0; i < _nbRows; ++i ) {
            for ( int j = 0; j < _nbColumns; ++j ) {
                if ( _tokenWhole[i][j] != null ) {
                    g.setColor(_tokenWhole[i][j].get_backColor());
                    g.fillRect(convertIndexXToPixel(j), convertIndexYToPixel(i), Constant._columnWidth, Constant._rowHeight);
                }

            }
        }
        g.setColor(Color.black);

        //////////////Calcul of the size of the line//////////////////////
        int _axisSize = _axis + ( _nbColumns * Constant._columnWidth );
        int _ordinateSize = _ordinate + ( _nbRows * Constant._rowHeight );

        /////////////Draw horizontal Line////////////
        for ( int i = 0 ; i <= _nbRows ; i ++ )
            g.drawLine( _axis, convertIndexYToPixel(i) , _axisSize,  convertIndexYToPixel(i) );

        /////////////Draw Vertical Line//////////////
        for ( int i = 0; i <= _nbColumns; i++ )
            g.drawLine( convertIndexXToPixel(i), _ordinate, convertIndexXToPixel(i), _ordinateSize );

        ////////////Draw the number/////////////////

        //Utils to center the number in it cell
        FontMetrics fm = g.getFontMetrics();
        Rectangle2D rect;

        for ( int i = 0; i < _nbRows; ++i ) {
            for ( int j = 0; j < _nbColumns; ++j ) {
                if ( _tokenWhole[i][j] != null ) {
                    String number = String.valueOf(_tokenWhole[i][j].get_number());
                    rect = fm.getStringBounds(number, g);
                    g.setColor(_tokenWhole[i][j].get_textColor());
                    g.drawString(number, (int) ( convertIndexXToPixel(j) + Constant._columnWidth /2 - rect.getWidth()/2),
                            (int) ( convertIndexYToPixel(i) + Constant._rowHeight /2 + rect.getHeight()/2));
                }

            }
        }
    }

    public int addColumn() {
        this._nbColumns ++;
        fillGrid();
        repaint();
        if ( ( this._nbColumns + 1 ) * Constant._columnWidth < this.getSize().width ) {
            return 0;
        } else
            return -1;


    }

    public int addRow() {
        this._nbRows++;
        fillGrid();
        repaint();
        if ( ( this._nbRows + 1 ) * Constant._rowHeight < this.getSize().height ) {
            return 0;
        } else
            return -1;

    }

    public int throwColumn() {
        this._nbColumns--;
        fillGrid();
        repaint();
        if ( this._nbColumns == Constant._minColumns ) {
            return -1;
        } else
            return 0;
    }

    public int throwRow() {
        this._nbRows--;
        fillGrid();
        repaint();
        if ( this._nbRows == Constant._minColumns ) {
            return -1;
        } else
            return 0;

    }

    public void reset() {
        this._nbRows = Constant._initialRow;
        this._nbColumns = Constant._initialColumn;
        fillGrid();
        repaint();
    }
}
