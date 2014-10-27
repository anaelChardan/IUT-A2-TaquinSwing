package Taquin;

import javax.swing.*;

/**
 * Created by martylamoureux on 27/10/14.
 */
public class TaquinController {
    private TaquinModel model;

    public enum Event {
        AddColumn,
        ThrowColumn,
        AddRow,
        ThrowRow,
        ResetGrid
    }

    public void action(Event event) {
            switch (event) {

                case AddColumn: model.addColumn();
                    break;
                case ThrowColumn: model.throwColumn();
                    break;
                case AddRow: model.addRow();
                    break;
                case ThrowRow: model.throwRow();
                    break;
                case ResetGrid: model.reset();
                    break;
            }
    }

    public TaquinModel getModel() {
        return model;
    }

    public void setModel(TaquinModel model) {
        this.model = model;
    }
}
