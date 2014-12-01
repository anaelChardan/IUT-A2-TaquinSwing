package Taquin;

import java.awt.*;
import java.awt.geom.Rectangle2D;


/**
  * GridModel is a class representing a grid
  * It extends from Canvas so is able to represent himself on a screen
  */
public class GridView extends Canvas {

    private static final long serialVersionUID = 1L;

    /**
     * The constant to know the columnWidth
     */
    public static final int _columnWidth = 50;

    /**
     * The constant to know the row Height
     */
    public static final int _rowHeight = 50 ;

    /**
     * Where the grid is beginning on the axis
     */
    private int axis;

    /**
     * Where the grid is beginning on the ordinate
     */
    private int ordinate;

   private GridModel gridModel;

    /**
     * Constructor of the class
     */
    public GridView(GridModel gridModel) {
        this.gridModel = gridModel;
    }

    /**
     * the method to convert a cell axis into a pixel axis
     * @param i the cell axis
     * @return the pixel axis
     */
    private int convertIndexXToPixel(int i) {
        return _columnWidth * i + axis;
    }

    /**
     * the method to convert a pixel axis into a cell axis
     * @param j the cell
     * @return the pixel ordinate
     */
    private int convertIndexYToPixel(int j) {
        return _rowHeight * j + ordinate;
    }

    /**
     * The method to convert a pixel axis into a cell
     * @param xpixel the pixel axis
     * @return the cell axis
     */
    public int convertXPixel( int xpixel ) {
        if ( xpixel >= axis && xpixel <= ( axis + ( gridModel.getNbColumns() * _columnWidth ) ) ) {
            return (int) Math.ceil( ( xpixel - axis) / ( _columnWidth ) );
        }
        return -1;
    }

    /**
     * The method to convert a pixel ordinate into a cell ordinate
     * @param ypixel the pixel ordinate
     * @return the cell ordinate
     */
    public int convertYPixel( int ypixel ) {
        if ( ypixel >= ordinate && ypixel <= ( ordinate + ( gridModel.getNbRows() * _rowHeight ) ) ) {
            return (int) Math.ceil( ( ypixel - ordinate) / ( _rowHeight ) );
        }

        return -1;
    }

    /**
     * The method to take a token at a cell place
     * @param x the axis of our grid
     * @param y the ordinate of our grid
     * @return the Token
     */
    public Token getTokenAt(int x, int y) {
        int coordX = convertXPixel(x);
        int coordY = convertYPixel(y);

        if (coordX < 0 || coordY < 0)
            return null;

        if (coordX > GridModel._maxColumns || coordY > GridModel._maxRows)
            return null;

        return gridModel.getToken(coordY, coordX);
    }

    /**
     * The method to paint this gridView
     * @param g the graphics
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        //////////////Calcul of the beginning of the axis and the ordinate////////////////
        axis = ( this.getSize().width / 2 ) - ( this.gridModel.getNbColumns() * _columnWidth / 2 );
        ordinate = ( this.getSize().height / 2 ) - ( this.gridModel.getNbRows() * _rowHeight / 2 );

        ///////////// Draw The Token Whole/////////////////
        for ( int i = 0; i < gridModel.getNbRows(); ++i ) {
            for ( int j = 0; j < gridModel.getNbColumns(); ++j ) {
                if ( gridModel.getToken(i, j) != null ) {
                    g.setColor(gridModel.getToken(i, j).getBackColor());
                    g.fillRect(convertIndexXToPixel(j), convertIndexYToPixel(i), _columnWidth, _rowHeight);
                }

            }
        }
        g.setColor(Color.black);

        //////////////Calcul of the size of the line//////////////////////
        int _axisSize = axis + ( gridModel.getNbColumns() * _columnWidth );
        int _ordinateSize = ordinate + ( gridModel.getNbRows() * _rowHeight );

        /////////////Draw horizontal Line////////////
        for ( int i = 0 ; i <= gridModel.getNbRows(); i ++ )
            g.drawLine(axis, convertIndexYToPixel(i) , _axisSize,  convertIndexYToPixel(i) );

        /////////////Draw Vertical Line//////////////
        for ( int i = 0; i <= gridModel.getNbColumns(); i++ )
            g.drawLine( convertIndexXToPixel(i), ordinate, convertIndexXToPixel(i), _ordinateSize );

        ////////////Draw the number/////////////////

        //Utils to center the number in it cell
        FontMetrics fm = g.getFontMetrics();
        Rectangle2D rect;

        for ( int i = 0; i < gridModel.getNbRows(); ++i ) {
            for ( int j = 0; j < gridModel.getNbColumns(); ++j ) {
                if ( gridModel.getToken(i, j) != null ) {
                    String number = String.valueOf(gridModel.getToken(i, j).getNumber());
                    rect = fm.getStringBounds(number, g);
                    g.setColor( Brightness( gridModel.getToken(i, j).getBackColor() ) < 130 ? Color.white : Color.black );
                    g.drawString(number, (int) ( convertIndexXToPixel(j) + _columnWidth /2 - rect.getWidth()/2),
                            (int) ( convertIndexYToPixel(i) + _rowHeight /2 + rect.getHeight()/2));
                }

            }
        }

    }

    /**
     * The color perception method to easier the readding of the number in a cell in front of a backColor
     * @param c th font back color
     * @return the color perception
     */
    private static int Brightness(Color c)
    {
        return (int)Math.sqrt(
                c.getRed() * c.getRed() * .241 +
                        c.getGreen() * c.getGreen() * .691 +
                        c.getBlue() * c.getBlue() * .068);
    }
}
