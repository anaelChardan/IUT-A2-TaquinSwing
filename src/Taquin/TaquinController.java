package Taquin;

/**
 * The controller of the game
 */
public class TaquinController {
    private TaquinModel model;

    /**
     * The enumeration of the action which are possible
     */
    public enum Event {
        AddColumn,
        ThrowColumn,
        AddRow,
        ThrowRow,
        ResetGrid,
    }

    /**
     * The "router" of the event which redirect into an order to the model
     */
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

    /**
     * The method correspond to the movement of the token
     * @param token
     */
    public void move( Token token ) {
        if ( this.model.getGrid().isTokenMovable(token) ) {
            this.model.moveToken( token, this.model.getGrid().getDirectionToEmpty(token) );
        }
    }

    /**
     * the method which return the model
     * @return
     */
    public TaquinModel getModel() {
        return model;
    }

    /**
     * The method to set the model
     * @param model
     */
    public void setModel(TaquinModel model) {
        this.model = model;
    }
}
