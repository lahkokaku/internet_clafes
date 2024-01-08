package views;

import javafx.scene.Group;
import javafx.scene.Scene;
import util.Constants;

public abstract class BaseView implements IComponent {

	protected Scene scene;

	public BaseView() {
		this.scene = new Scene(new Group(), Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
		initialize();
		setComponent();
		setStyle();
		setAction();
	}

	public Scene getScene() {
		return scene;
	}

}
