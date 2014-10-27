package Taquin;

/**
 * Created by martylamoureux on 27/10/14.
 */
public class Taquin {
    public static void main(String[] args) {
        TaquinController controller = new TaquinController();
        TaquinModel model = new TaquinModel();
        controller.setModel(model);

        TaquinView view = new TaquinView(controller);
        model.addObserver(view);

        view.showView();
    }
}
