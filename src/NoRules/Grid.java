package NoRules;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.*;


/**
  * Grid is a class representing a grid
  * It extends from Canvas so is able to represent himself on a screen
  */
public class Grid extends Canvas {

    public static final int _columnWidth = 50;
    public static final int _rowHeight = 50 ;
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
     * Where the grid is beginning on the axis
     */
    private int axis;

    /**
     * Where the grid is beginning on the ordinate
     */
    private int ordinate;

    /**
     * The array which contains the grid's token
     */
    private Token[][] tokens;

    /**
     * Constructor of the class
     * @see NoRules.Grid#fillGrid()
     */
    public Grid() {
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
        checkIsWellPlaced();

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

    private void checkIsWellPlaced() {
        for ( int i = 0; i < nbRows; ++i ) {
            for ( int j = 0; j < nbColumns; ++j ) {
                if ( tokens[i][j] != null ) {
                    if ( i * nbColumns + j == tokens[i][j].get_number() )
                        tokens[i][j].setWellPlace();
                }

            }
        }
    }

    //////To convert an Index to represent it on the graphic /////////
    private int convertIndexXToPixel(int i) {
        return _columnWidth * i + axis;
    }

    private int convertIndexYToPixel(int j) {
        return _rowHeight * j + ordinate;
    }

    private double convertXPixel( int xpixel ) {
        if ( xpixel >= axis && xpixel <= ( axis + ( nbColumns * _columnWidth ) ) ) {
            return Math.ceil( ( xpixel - axis) / ( _columnWidth ) );
        }
        return -1;
    }

    private double convertYPixel( int ypixel ) {
        if ( ypixel >= ordinate && ypixel <= ( ordinate + ( nbRows * _rowHeight ) ) ) {
            return Math.ceil( ( ypixel - ordinate) / ( _rowHeight ) );
        }

        return -1;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        //////////////Calcul of the beginning of the axis and the ordinate////////////////
        axis = ( this.getSize().width / 2 ) - ( this.nbColumns * _columnWidth / 2 );
        ordinate = ( this.getSize().height / 2 ) - ( this.nbRows * _rowHeight / 2 );

        ///////////// Draw The Token Whole/////////////////
        for ( int i = 0; i < nbRows; ++i ) {
            for ( int j = 0; j < nbColumns; ++j ) {
                if ( tokens[i][j] != null ) {
                    g.setColor(tokens[i][j].get_backColor());
                    g.fillRect(convertIndexXToPixel(j), convertIndexYToPixel(i), _columnWidth, _rowHeight);
                }

            }
        }
        g.setColor(Color.black);

        //////////////Calcul of the size of the line//////////////////////
        int _axisSize = axis + ( nbColumns * _columnWidth );
        int _ordinateSize = ordinate + ( nbRows * _rowHeight );

        /////////////Draw horizontal Line////////////
        for ( int i = 0 ; i <= nbRows; i ++ )
            g.drawLine(axis, convertIndexYToPixel(i) , _axisSize,  convertIndexYToPixel(i) );

        /////////////Draw Vertical Line//////////////
        for ( int i = 0; i <= nbColumns; i++ )
            g.drawLine( convertIndexXToPixel(i), ordinate, convertIndexXToPixel(i), _ordinateSize );

        ////////////Draw the number/////////////////

        //Utils to center the number in it cell
        FontMetrics fm = g.getFontMetrics();
        Rectangle2D rect;

        for ( int i = 0; i < nbRows; ++i ) {
            for ( int j = 0; j < nbColumns; ++j ) {
                if ( tokens[i][j] != null ) {
                    String number = String.valueOf(tokens[i][j].get_number());
                    rect = fm.getStringBounds(number, g);
                    g.setColor(tokens[i][j].get_textColor());
                    g.drawString(number, (int) ( convertIndexXToPixel(j) + _columnWidth /2 - rect.getWidth()/2),
                            (int) ( convertIndexYToPixel(i) + _rowHeight /2 + rect.getHeight()/2));
                }

            }
        }
    }

    public int addColumn() {
        this.nbColumns++;
        fillGrid();
        repaint();
        if ( ( this.nbColumns + 1 ) * _columnWidth < this.getSize().width ) {
            return 0;
        } else
            return -1;


    }

    public int addRow() {
        this.nbRows++;
        fillGrid();
        repaint();
        if ( ( this.nbRows + 1 ) * _rowHeight < this.getSize().height ) {
            return 0;
        } else
            return -1;

    }

    public int throwColumn() {
        this.nbColumns--;
        fillGrid();
        repaint();
        if ( this.nbColumns == _minColumns ) {
            return -1;
        } else
            return 0;
    }

    public int throwRow() {
        this.nbRows--;
        fillGrid();
        repaint();
        if ( this.nbRows == _minColumns ) {
            return -1;
        } else
            return 0;

    }

    public void reset() {
        this.nbRows = _initialRow;
        this.nbColumns = _initialColumn;
        fillGrid();
        repaint();
    }
}
