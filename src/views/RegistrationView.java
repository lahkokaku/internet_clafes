package views;

import controllers.UserController;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import stores.UserStore;
import util.StageManager;

public class RegistrationView extends BaseView {

	private Label register_lb;
	
	private Label UserName_lb;
	private TextField UserName_tf;
		
	private Label UserPassword_lb;
	private PasswordField UserPassword_pf;
	private Label ConfPassword_lb;
	private PasswordField ConfPassword_pf;
	
	private Label UserAge_lb;
	private Spinner<Integer> UserAge_spinner;
	
	private MenuBar menubar;
	private Menu generalMenu_menu;
	private MenuItem registration_mi;
	private MenuItem login_mi;
	private MenuItem viewAllPC_mi;
	
	private BorderPane bp;
	private GridPane gp;
	private HBox iagree_hb;
	private Button submit_btn;

	@Override
	public void initialize() {
		initializeMenu();
		initializeLabels();
		initializeFormComponents();
		
		this.bp = new BorderPane();
		this.gp = new GridPane();
		this.gp.setPadding(new Insets(15, 15, 15, 100));
		this.iagree_hb = new HBox();
		this.submit_btn = new Button("Submit");
	}
	
	private void initializeMenu() {
		this.generalMenu_menu = new Menu("General Menu");
		this.login_mi = new MenuItem("Login");
		this.registration_mi = new MenuItem("Register");
		this.viewAllPC_mi = new MenuItem("View All PC");
		this.generalMenu_menu.getItems().addAll(viewAllPC_mi, login_mi, registration_mi);
		this.menubar = new MenuBar(generalMenu_menu);
	}
	
	public void initializeLabels() {
		this.register_lb = new Label("Register");
		this.UserName_lb = new Label("Name");
		this.UserPassword_lb = new Label("Password");
		this.ConfPassword_lb = new Label("Confirm Password");
		this.UserAge_lb = new Label("Age");
	}
	
	public void initializeFormComponents() {
		this.UserName_tf = new TextField();
		this.UserPassword_pf = new PasswordField();
		this.ConfPassword_pf = new PasswordField();
		this.UserAge_spinner = new Spinner<Integer>(13, 65, 13, 1);
	}

	@Override
	public void setComponent() {
		this.bp.setTop(menubar);
		this.bp.setCenter(gp);
		int i = 1;
		this.gp.add(register_lb, 0, i++);
		this.gp.add(new Label(""), 0, i++);
		this.gp.add(UserName_lb, 0, i);
		this.gp.add(UserName_tf, 1, i++);
		this.gp.add(UserPassword_lb, 0, i);
		this.gp.add(UserPassword_pf, 1, i++);
		this.gp.add(ConfPassword_lb, 0, i);
		this.gp.add(ConfPassword_pf, 1, i++);
		this.gp.add(UserAge_lb, 0, i);
		this.gp.add(UserAge_spinner, 1, i++);
		this.gp.add(submit_btn, 0, i++);
		
		this.scene.setRoot(bp);
	}

	@Override
	public void setStyle() {
		register_lb.setFont(Font.font(40));
//		this.gp.setAlignment(Pos.CENTER);
		GridPane.setColumnSpan(register_lb, 2);
		GridPane.setHalignment(register_lb, HPos.CENTER);
		
		GridPane.setColumnSpan(submit_btn, 2);
		GridPane.setHalignment(submit_btn, HPos.CENTER);
	}
	
	@Override
	public void setAction() {
		login_mi.setOnAction(e->{
			StageManager.getInstance().setScene(new LoginView().getScene());
		});
		viewAllPC_mi.setOnAction(e->{
			StageManager.getInstance().setScene(new ViewAllPCView().getScene());
		});
		submit_btn.setOnAction(event -> {
			boolean isSuccessful = UserController.addNewUser(UserName_tf.getText(), UserPassword_pf.getText(), ConfPassword_pf.getText(), UserAge_spinner.getValue());
			
			if(isSuccessful) {
				StageManager.getInstance().setScene(new LoginView().getScene());
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText("Success");
				alert.setContentText("Successfuly registered. Please login using registered credentials.");
				alert.showAndWait();
			}
		});
	}
}
