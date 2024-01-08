package util;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class StageManager {
	private static StageManager instance;

	public static StageManager getInstance() {
		if (instance == null)
			instance = new StageManager();
		return instance;
	}

	private Stage stage;

	private StageManager() {
		this.stage = new Stage();
		this.stage.setTitle("BD01-Kelompok1-Internet Clafes");
		this.stage.setWidth(Constants.SCREEN_WIDTH);
		this.stage.setHeight(Constants.SCREEN_HEIGHT);
		this.stage.setResizable(false);
	}

	public Stage getStage() {
		return stage;
	}

	public void setScene(Scene scene) {
		this.stage.setScene(scene);
		if (!this.stage.isShowing()) {
			this.stage.show();
		}
	}
}
