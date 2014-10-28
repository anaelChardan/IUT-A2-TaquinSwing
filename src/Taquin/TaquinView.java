package Taquin;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;


public class TaquinView extends JFrame implements Observer {

    public static final int _windowWidth = 700;
    public static final int _windowHeight = 700;

    private GridView grid;

    private TaquinController controller;

    private JButton addColumn = new JButton("Add a Column");
    private JButton addRow = new JButton("Add a Row");
    private JButton throwColumn = new JButton("Throw a Column");
    private JButton throwRow = new JButton("Throw a Row");
    private JButton reset = new JButton("Reset");

    private JPanel container = new JPanel();

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

        //////////Gestion de notre container////
        container.add(north, BorderLayout.NORTH);
        this.setContentPane(container);

    } 
    public void showView() {
        this.setVisible(true);
    }

    public TaquinModel getModel() {
        return this.controller.getModel();
    }

    @Override
    public void update(Observable o, Object arg) {
        this.grid.repaint();
        this.addRow.setEnabled(getModel().getGrid().canAddRow());
        this.addColumn.setEnabled(getModel().getGrid().canAddColumn());
        this.throwColumn.setEnabled(getModel().getGrid().canThrowColumn());
        this.throwRow.setEnabled(getModel().getGrid().canThrowRow());


        if ( getModel().getGrid().isComplete() ) {

            ImageIcon img  = new ImageIcon("pictures/congrats.png");
            JOptionPane jop = new JOptionPane();
            int option = jop.showConfirmDialog(null,
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

    public GridView getGrid() {
        return grid;
    }

    public void setGrid(GridView grid) {
        this.grid = grid;
    }


    private class AddColumn implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            controller.action(TaquinController.Event.AddColumn);
        }
    }


    private class AddRow implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            controller.action(TaquinController.Event.AddRow);
        }
    }

    private class ThrowColumn implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            controller.action(TaquinController.Event.ThrowColumn);
        }
    }

    private class ThrowRow implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            controller.action(TaquinController.Event.ThrowRow);
        }
    }

    private class Reset implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            controller.action(TaquinController.Event.ResetGrid);
        }
    }

    private class Click extends MouseInputAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            if ( grid.getTokenAt(e.getX(), e.getY()) != null )
               controller.move(grid.getTokenAt(e.getX(), e.getY()));

        }
    }
}

