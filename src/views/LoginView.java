package views;

import controllers.UserController;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import util.StageManager;

public class LoginView extends BaseView {

	private Label login_lb;
	
	private Label UserName_lb;
	private TextField UserName_tf;
		
	private Label UserPassword_lb;
	private PasswordField UserPassword_pf;
	
	private MenuBar menubar;
	private Menu generalMenu_menu;
	private MenuItem registration_mi;
	private MenuItem login_mi;
	private MenuItem viewAllPC_mi;
	
	private BorderPane bp;
	private GridPane gp;
	private Button submit_btn;

	@Override
	public void initialize() {
		initializeMenu();
		initializeLabels();
		initializeFormComponents();
		
		this.bp = new BorderPane();
		this.gp = new GridPane();
		this.gp.setPadding(new Insets(15, 15, 15, 100));
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
		this.login_lb = new Label("Login");
		this.UserName_lb = new Label("Name");
		this.UserPassword_lb = new Label("Password");
	}
	
	public void initializeFormComponents() {
		this.UserName_tf = new TextField();
		this.UserPassword_pf = new PasswordField();
	}

	@Override
	public void setComponent() {
		this.bp.setTop(menubar);
		this.bp.setCenter(gp);
		int i = 1;
		this.gp.add(login_lb, 0, i++);
		// Empty space
		this.gp.add(new Label(""), 0, i++);
		this.gp.add(UserName_lb, 0, i);
		this.gp.add(UserName_tf, 1, i++);
		this.gp.add(UserPassword_lb, 0, i);
		this.gp.add(UserPassword_pf, 1, i++);
		this.gp.add(submit_btn, 0, i++);
		
		this.scene.setRoot(bp);
	}

	@Override
	public void setStyle() {
		login_lb.setFont(Font.font(40));
//		this.gp.setAlignment(Pos.CENTER);
		GridPane.setColumnSpan(login_lb, 2);
		GridPane.setHalignment(login_lb, HPos.CENTER);
		
		GridPane.setColumnSpan(submit_btn, 2);
		GridPane.setHalignment(submit_btn, HPos.CENTER);
	}
	
	@Override
	public void setAction() {
		registration_mi.setOnAction(e->{
			StageManager.getInstance().setScene(new RegistrationView().getScene());
		});
		viewAllPC_mi.setOnAction(e->{
			StageManager.getInstance().setScene(new ViewAllPCView().getScene());
		});
		submit_btn.setOnAction(event -> {
			UserController.login(UserName_tf.getText(), UserPassword_pf.getText());
		});
	}

}
