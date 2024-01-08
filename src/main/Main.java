package main;

import javafx.application.Application;
import javafx.stage.Stage;
import util.StageManager;
import views.RegistrationView;

public class Main extends Application {

	/**
	 * BE01
	 * Kelompok:
	 * Alfred Jonathan - 2540128455
	 * Edward Wijaya - 2540125434
	 * Geren Widiarta - 2540125030
	 * Jose Stephen Satrya - 2540124551
	 */
	
	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		StageManager.getInstance().setScene(new RegistrationView().getScene());
	}

}
