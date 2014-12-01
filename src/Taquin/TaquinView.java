package Taquin;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

/**
 * This the view part of the Taquin
 */
public class TaquinView extends JFrame implements Observer {

    private static final long serialVersionUID = 1L;

    /**
     * The constant to know the Witdh of the window
     */
    public static final int _windowWidth = 700;

    /**
     * The constant to know the Height of the window
     */
    public static final int _windowHeight = 700;

    /**
     * The GridView
     */
    private GridView grid;

    /**
     * The controller
     */
    private TaquinController controller;

    /**
     * The JButon to add a column
     */
    private JButton addColumn = new JButton("Add a Column");

    /**
     * The JButon to add a row
     */
    private JButton addRow = new JButton("Add a Row");

    /**
     * The JButon to throw a column
     */
    private JButton throwColumn = new JButton("Throw a Column");

    /**
     * The JButon to throw a row
     */
    private JButton throwRow = new JButton("Throw a Row");

    /**
     * The JButon to reset the grid
     */
    private JButton reset = new JButton("Reset");

    /**
     * The JButon to add a column
     */
    private JPanel container = new JPanel();

    /**
     * The constructor
     * @param controller the controller of our view
     */
    public TaquinView(TaquinController controller) {
        this.controller = controller;
        this.grid = new GridView(this.getModel().getGrid());

        this.setTitle("..::Taquin by CHARDAN Anaël & LAMOUREUX Marty S3A\" ::..");
        this.setSize(_windowWidth, _windowHeight);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);


        container.setLayout(new BorderLayout());
        container.add(grid, BorderLayout.CENTER);


        ///////Button to add a Column///////
        addColumn.addActionListener(new AddColumn());

        ///////Button to add a Row///////
        addRow.addActionListener(new AddRow());

        ///////Button to throw a Column///////
        throwColumn.addActionListener(new ThrowColumn());

        //////Button to throw a Row//////////
        throwRow.addActionListener(new ThrowRow());

        //////Button to Reset//////////
        reset.addActionListener(new Reset());

        grid.addMouseListener(new Click());


        JPanel north = new JPanel();
        north.add(addColumn);
        north.add(addRow);
        north.add(throwColumn);
        north.add(throwRow);
        north.add(reset);

        //////////Management of the container////
        container.add(north, BorderLayout.NORTH);
        this.setContentPane(container);

    } 
    public void showView() {
        this.setVisible(true);
    }

    public TaquinModel getModel() {
        return this.controller.getModel();
    }

    /**
     * The method update
     * @param o the observable concerned to update
     * @param arg the object
     */
    @Override
    public void update(Observable o, Object arg) {
        this.grid.repaint();
        this.addRow.setEnabled(getModel().getGrid().canAddRow());
        this.addColumn.setEnabled(getModel().getGrid().canAddColumn());
        this.throwColumn.setEnabled(getModel().getGrid().canThrowColumn());
        this.throwRow.setEnabled(getModel().getGrid().canThrowRow());


        if ( getModel().getGrid().isCompleted() ) {

            ImageIcon img  = new ImageIcon("pictures/congrats.png");
            int option = JOptionPane.showConfirmDialog(null,
                    "Congratulations, you complete the grid ! Do you want to replay ? ", "Congratulations",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,img);

            if(option == JOptionPane.OK_OPTION){
                 controller.action(TaquinController.Event.ResetGrid);
            } else {
                System.exit(0);
            }
        }
    }

    /**
     * Getter
     * @return the grid
     */
    public GridView getGrid() {
        return grid;
    }

    /**
     * The setter of the grid view
     * @param grid the grid of our view
     */
    public void setGrid(GridView grid) {
        this.grid = grid;
    }

    /**
     * The intern class to listen the button addcolumn
     */
    private class AddColumn implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            controller.action(TaquinController.Event.AddColumn);
        }
    }

    /**
     * The intern class to listen the button addRow
     */
    private class AddRow implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            controller.action(TaquinController.Event.AddRow);
        }
    }

    /**
     * The intern class to listen the button throw column
     */
    private class ThrowColumn implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            controller.action(TaquinController.Event.ThrowColumn);
        }
    }
    /**
     * The intern class to listen the button throwRow
     */
    private class ThrowRow implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            controller.action(TaquinController.Event.ThrowRow);
        }
    }
    /**
     * The intern class to listen the button reset
     */
    private class Reset implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            controller.action(TaquinController.Event.ResetGrid);
        }
    }

    /**
     * The intern class to listen the click grid view
     */
    private class Click extends MouseInputAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            if ( grid.getTokenAt(e.getX(), e.getY()) != null )
               controller.move(grid.getTokenAt(e.getX(), e.getY()));

        }
    }
}

