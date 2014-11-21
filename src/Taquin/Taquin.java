package Taquin;

/**
 * THE main class to start the class
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
