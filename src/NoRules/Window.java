package NoRules;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Ananas on 25/10/14.
 */
public class Window extends JFrame {

    private Grid _theGrid = new Grid();
    private JButton _addColumn = new JButton("Add a Column");
    private JButton _addRow = new JButton("Add a row");
    private JButton _throwColumn = new JButton("Throw a Column");
    private JButton _throwRow = new JButton("Throw a Row");
    private JButton _reset = new JButton("Reset");

    private JPanel container = new JPanel();

    public Window() {
        this.setTitle("Taquin");
        this.setSize(700, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);


        container.setLayout(new BorderLayout());
        container.add(_theGrid, BorderLayout.CENTER);


        ///////Button to add a Column///////
        _addColumn.addActionListener( new addColumn() );

        ///////Button to add a Row///////
        _addRow.addActionListener( new addRow() );

        ///////Button to throw a Column///////
        _throwColumn.addActionListener( new throwColumn() );
        _throwColumn.setEnabled( false );

        //////Button to throw a Row//////////
        _throwRow.addActionListener( new throwRow() );
        _throwRow.setEnabled( false );

        //////Button to reset//////////
        _reset.addActionListener( new reset() );


        JPanel north = new JPanel();
        north.add(_addColumn);
        north.add(_addRow);
        north.add(_throwColumn);
        north.add(_throwRow);
        north.add(_reset);

        //////////Gestion de notre container////
        container.add(north,BorderLayout.NORTH);
        this.setContentPane(container);
        this.setVisible(true);
    }


    private class addColumn implements ActionListener {
        public void actionPerformed(ActionEvent arg0) {
            if ( _theGrid.addColumn() == -1 ) {
                _addColumn.setEnabled(false);
            }
            _throwColumn.setEnabled(true);
        }
    }


    private class addRow implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if ( _theGrid.addRow() == -1 ) {
                _addRow.setEnabled(false);
            }
            _throwRow.setEnabled(true);
        }
    }

    private class throwColumn implements ActionListener {
        public void actionPerformed(ActionEvent e) {
           if ( _theGrid.throwColumn() == -1 ) {
               _throwColumn.setEnabled(false);
           }
           _addColumn.setEnabled(true);

        }
    }

    private class throwRow implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (  _theGrid.throwRow() == -1 ) {
                _throwRow.setEnabled(false);
            }
            _addRow.setEnabled(true);

        }
    }

    public static void main(String[] args) {
        Window _game = new Window();
    }

    private class reset implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            _theGrid.reset();
        }
    }
}

