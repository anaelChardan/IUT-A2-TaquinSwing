package Taquin;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


/**
  * GridModel is a class representing a grid
  * It extends from Canvas so is able to represent himself on a screen
  */
public class GridView extends Canvas {

    public static final int _columnWidth = 50;
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

    //////To convert an Index to represent it on the graphic /////////
    private int convertIndexXToPixel(int i) {
        return _columnWidth * i + axis;
    }

    private int convertIndexYToPixel(int j) {
        return _rowHeight * j + ordinate;
    }

    private double convertXPixel( int xpixel ) {
        if ( xpixel >= axis && xpixel <= ( axis + ( gridModel.getNbColumns() * _columnWidth ) ) ) {
            return Math.ceil( ( xpixel - axis) / ( _columnWidth ) );
        }
        return -1;
    }

    private double convertYPixel( int ypixel ) {
        if ( ypixel >= ordinate && ypixel <= ( ordinate + ( gridModel.getNbRows() * _rowHeight ) ) ) {
            return Math.ceil( ( ypixel - ordinate) / ( _rowHeight ) );
        }

        return -1;
    }

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
                    g.setColor(gridModel.getToken(i, j).get_backColor());
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
                    String number = String.valueOf(gridModel.getToken(i, j).get_number());
                    rect = fm.getStringBounds(number, g);
                    g.setColor(gridModel.getToken(i, j).get_textColor());
                    g.drawString(number, (int) ( convertIndexXToPixel(j) + _columnWidth /2 - rect.getWidth()/2),
                            (int) ( convertIndexYToPixel(i) + _rowHeight /2 + rect.getHeight()/2));
                }

            }
        }
    }
}
