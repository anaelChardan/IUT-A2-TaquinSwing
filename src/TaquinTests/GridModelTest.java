package TaquinTests;

import Taquin.GridModel;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * Created by martylamoureux on 27/10/14.
 */
public class GridModelTest {
    GridModel model = new GridModel();




    @Before
    public void setUp() throws Exception {
        model = new GridModel();
    }

    @Test
    public void testIsTokenMovable() throws Exception {
        model.fillResolvedGrid();
        // The resolved grid allows us to know on which grid we are playing

        Assert.assertNull("To be sure the empty cell is in (4, 4)", model.getToken(4, 4));
        Assert.assertTrue("token #20 is movable", model.isTokenMovable(model.getToken(3, 4)));
        Assert.assertTrue("token #24 is movable", model.isTokenMovable(model.getToken(4, 3)));
        Assert.assertFalse("token #19 is NOT movable", model.isTokenMovable(model.getToken(3, 3)));
    }

    @Test
    public void testGetNextToken() throws Exception {
        model.fillResolvedGrid();

        Assert.assertEquals("at the right of 19, there is 20", 20, model.getNextToken(model.getToken(3, 3), GridModel.Direction.East).getNumber());
        Assert.assertEquals("at the left of 19, there is 18", 18, model.getNextToken(model.getToken(3, 3), GridModel.Direction.West).getNumber());
        Assert.assertEquals("above 19, there is 14", 14, model.getNextToken(model.getToken(3, 3), GridModel.Direction.North).getNumber());
        Assert.assertEquals("below 19, there is 14", 24, model.getNextToken(model.getToken(3, 3), GridModel.Direction.South).getNumber());
    }

    @Test
    public void testAddColumn() throws Exception {
        Assert.assertEquals("initial value", GridModel._initialColumn, model.getNbColumns());
        for (int i = model.getNbColumns(); i < model._maxColumns; i++) {
            model.addColumn();
            Assert.assertEquals(""+ (i + 1) + " cols", i+1, model.getNbColumns());
        }
        model.addColumn();
        Assert.assertEquals("much col", GridModel._maxColumns, model.getNbColumns());
    }

    @Test
    public void testCanAddColumn() throws Exception {
        Assert.assertEquals("initial value", GridModel._initialColumn, model.getNbColumns());
        model.setNbColumns(12);
        Assert.assertTrue("can add col when ok", model.canAddColumn());
        model.setNbColumns(13);
        Assert.assertFalse("cannot add col when too high", model.canAddColumn());
        model.setNbColumns(99);
        Assert.assertFalse("cannot add col when really too high", model.canAddColumn());
    }

    @Test
    public void testAddRow() throws Exception {
        Assert.assertEquals("initial value", GridModel._initialRow, model.getNbRows());
        for (int i = model.getNbRows(); i < model._maxRows; i++) {
            model.addRow();
            Assert.assertEquals(""+ (i + 1) + " rows", i+1, model.getNbRows());
        }
        model.addRow();
        Assert.assertEquals("much rows", GridModel._maxRows, model.getNbRows());
    }

    @Test
    public void testCanAddRow() throws Exception {
        Assert.assertEquals("initial value", GridModel._initialRow, model.getNbRows());
        model.setNbRows(11);
        Assert.assertTrue("can add row when ok", model.canAddRow());
        model.setNbRows(12);
        Assert.assertFalse("cannot add row when too high", model.canAddRow());
        model.setNbRows(99);
        Assert.assertFalse("cannot add row when really too high", model.canAddRow());
    }

    @Test
    public void testThrowColumn() throws Exception {
        Assert.assertEquals("initial value", GridModel._initialColumn, model.getNbColumns());
        for (int i = model.getNbColumns(); i > model._minColumns; i--) {
            model.throwColumn();
            Assert.assertEquals(""+ (i - 1) + " cols", i - 1, model.getNbColumns());
        }
        model.throwColumn();
        Assert.assertEquals("much cols", GridModel._minColumns, model.getNbColumns());
    }

    @Test
    public void testCanThrowColumn() throws Exception {
        Assert.assertEquals("initial value", GridModel._initialColumn, model.getNbColumns());
        model.setNbColumns(3);
        Assert.assertTrue("can throw col when ok", model.canThrowColumn());
        model.setNbColumns(2);
        Assert.assertFalse("cannot throw col when too low", model.canThrowColumn());
        model.setNbColumns(0);
        Assert.assertFalse("cannot throw col when really too low", model.canThrowColumn());
    }

    @Test
    public void testThrowRow() throws Exception {
        Assert.assertEquals("initial value", GridModel._initialRow, model.getNbRows());
        for (int i = model.getNbRows(); i > model._minRows; i--) {
            model.throwRow();
            Assert.assertEquals(""+ (i - 1) + " rows", i - 1, model.getNbRows());
        }
        model.throwRow();
        Assert.assertEquals("much rows", GridModel._minRows, model.getNbRows());
    }

    @Test
    public void testCanThrowRow() throws Exception {
        Assert.assertEquals("initial value", GridModel._initialRow, model.getNbRows());
        model.setNbRows(3);
        Assert.assertTrue("can throw row when ok", model.canThrowRow());
        model.setNbRows(2);
        Assert.assertFalse("cannot throw row when too low", model.canThrowRow());
        model.setNbRows(0);
        Assert.assertFalse("cannot throw row when really too low", model.canThrowRow());
    }

    @Test
    public void testReset() throws Exception {
        model.setNbColumns(10);
        model.setNbRows(10);
        model.reset();
        Assert.assertEquals("cols reset", GridModel._initialColumn, model.getNbColumns());
        Assert.assertEquals("rows reset", GridModel._initialRow, model.getNbRows());
        Assert.assertTrue("test shuffle", (model.getToken(0, 0).getNumber() != 1 && model.getToken(0, 1).getNumber() != 2));
    }

    @Test
    public void testSetNbRows() throws Exception {
        Assert.assertEquals("initial value", GridModel._initialRow, model.getNbRows());
        model.setNbRows(8);
        Assert.assertEquals("value is in range", 8, model.getNbRows());
        model.setNbRows(12);
        Assert.assertEquals("value is in range", 12, model.getNbRows());
        model.setNbRows(20);
        Assert.assertEquals("value is over limit, value is fixed to the limited", 12, model.getNbRows());
        model.setNbRows(2);
        Assert.assertEquals("value is in range", 2, model.getNbRows());
        model.setNbRows(1);
        Assert.assertEquals("value is under limit, value is fixed to the limited", 2, model.getNbRows());
    }

    @Test
    public void testSetNbColumns() throws Exception {
        Assert.assertEquals("initial value", GridModel._initialColumn, model.getNbColumns());
        model.setNbColumns(8);
        Assert.assertEquals("value is in range", 8, model.getNbColumns());
        model.setNbColumns(13);
        Assert.assertEquals("value is in range", 13, model.getNbColumns());
        model.setNbColumns(20);
        Assert.assertEquals("value is over limit, value is fixed to the limited", 13, model.getNbColumns());
        model.setNbColumns(2);
        Assert.assertEquals("value is in range", 2, model.getNbColumns());
        model.setNbColumns(1);
        Assert.assertEquals("value is under limit, value is fixed to the limited", 2, model.getNbColumns());
    }
}
