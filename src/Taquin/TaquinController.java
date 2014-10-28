package Taquin;


public class TaquinController {
    private TaquinModel model;

    public enum Event {
        AddColumn,
        ThrowColumn,
        AddRow,
        ThrowRow,
        ResetGrid,
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

    public void move( Token token ) {
        if ( this.model.getGrid().isTokenMovable(token) ) {
            this.model.moveToken( token, this.model.getGrid().getDirectionEmpty( token ) );
        }
    }

    public TaquinModel getModel() {
        return model;
    }

    public void setModel(TaquinModel model) {
        this.model = model;
    }
}
