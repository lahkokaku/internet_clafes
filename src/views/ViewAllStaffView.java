package views;

import java.util.Vector;

import controllers.UserController;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import models.User;
import stores.UserStore;
import util.StageManager;

public class ViewAllStaffView extends BaseView {

	private Label viewAllStaff_lb;
	
	private TableView<User> staffTable_tv;
	
	private Label staff_lb;
	private Label staffID_lb;
	private TextField staffID_tf;
	private Label newRole_lb;
	private ComboBox<String> newRole_cb;
	
	private MenuBar menubar;
	private Menu generalMenu_menu;
	private MenuItem viewAllPC_mi;
	private MenuItem logout_mi;
	
	private Menu adminMenu_menu;
	private MenuItem viewAllReport_mi;
	private MenuItem viewAllStaff_mi;
	private MenuItem viewAllStaffJob_mi;
	private MenuItem viewAllTransactionHistory_mi;
	
	private BorderPane bp;
	private GridPane gpLeft;
	private GridPane gpCenter;
	private Button submit_btn;

	@Override
	public void initialize() {
		this.bp = new BorderPane();
		this.gpLeft = new GridPane();
		this.gpCenter = new GridPane();
		initializeMenu();
		initializeLabels();
		initializeFormComponents();
		
	}
	
	private void initializeMenu() {
		this.generalMenu_menu = new Menu("General Menu");
		this.viewAllPC_mi = new MenuItem("View All PC");
		this.logout_mi = new MenuItem("Logout");
		this.generalMenu_menu.getItems().addAll(viewAllPC_mi, logout_mi);
		
		this.adminMenu_menu = new Menu("Admin Menu");
		this.viewAllReport_mi = new Menu("View All Report");
		this.viewAllStaff_mi = new Menu("View All Staff");
		this.viewAllStaffJob_mi = new Menu("View All Staff Job");
		this.viewAllTransactionHistory_mi = new Menu("View All Transaction History");
		this.adminMenu_menu.getItems().addAll(viewAllReport_mi, viewAllStaff_mi, viewAllStaffJob_mi, viewAllTransactionHistory_mi);
		
		this.menubar = new MenuBar(generalMenu_menu, adminMenu_menu);
	}
	
	public void initializeLabels() {
		this.viewAllStaff_lb = new Label("View All Staff");
		this.staff_lb = new Label("Update Staff Role");
		this.staffID_lb = new Label("Staff ID to Update Role");
		this.newRole_lb = new Label("New Role to be assigned");
	}
	
	public void initializeFormComponents() {
		staffTable_tv = new TableView<User>();
		setTableComponent();
		this.staffID_tf = new TextField();
		this.newRole_cb = new ComboBox<String>();
		
		this.submit_btn = new Button("Submit");
		this.newRole_cb.getItems().addAll("Admin", "Computer Technician", "Operator", "Customer");
		this.newRole_cb.getSelectionModel().selectFirst();
	}

	private void setTableComponent() {
		TableColumn<User, Integer> idColumn = new TableColumn<User, Integer>("User ID");
		idColumn.setCellValueFactory(new PropertyValueFactory<User, Integer>("userID"));
		idColumn.setMinWidth(gpCenter.getWidth() / 4);
		TableColumn<User, String> nameColumn = new TableColumn<User, String>("Staff Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("userName"));
		nameColumn.setMinWidth(gpCenter.getWidth() / 4);
		TableColumn<User, Integer> ageColumn = new TableColumn<User, Integer>("Staff Age");
		ageColumn.setCellValueFactory(new PropertyValueFactory<User, Integer>("userAge"));
		ageColumn.setMinWidth(gpCenter.getWidth() / 4);
		TableColumn<User, String> roleColumn = new TableColumn<User, String>("Staff Role");
		roleColumn.setCellValueFactory(new PropertyValueFactory<User, String>("userRole"));
		roleColumn.setMinWidth(gpCenter.getWidth() / 4);
		
		staffTable_tv.getColumns().addAll(idColumn, nameColumn, ageColumn, roleColumn);
		
		Vector<User> users = UserController.getAllUserData();
		
		for (User user : users) {
			if(!user.getUserRole().equals("Customer"))
				staffTable_tv.getItems().add(user);
		}
	}
	
	@Override
	public void setComponent() {
		this.bp.setTop(menubar);
		this.bp.setLeft(gpLeft);
		this.bp.setCenter(gpCenter);

		this.gpLeft.add(viewAllStaff_lb, 0, 1);
		this.gpLeft.add(staffTable_tv, 0, 2);
		
		this.gpCenter.add(staff_lb, 0, 3);
		this.gpCenter.add(staffID_lb, 0, 4); this.gpCenter.add(staffID_tf, 1, 4);
		this.gpCenter.add(newRole_lb, 0, 5); this.gpCenter.add(newRole_cb, 1, 5);
		this.gpCenter.add(submit_btn, 0, 6);
		
		this.scene.setRoot(bp);
	}

	@Override
	public void setStyle() {
		viewAllStaff_lb.setFont(Font.font(20));
		staff_lb.setFont(Font.font(null, FontWeight.BOLD, -1));
		this.gpLeft.setPadding(new Insets(15, 15, 15, 15));
		this.gpCenter.setPadding(new Insets(15, 15, 15, 15));
		GridPane.setColumnSpan(staffTable_tv, 2);
		GridPane.setHalignment(staffTable_tv, HPos.CENTER);
		
		GridPane.setColumnSpan(submit_btn, 2);
		GridPane.setHalignment(submit_btn, HPos.CENTER);
	}
	
	@Override
	public void setAction() {
		// General
		viewAllPC_mi.setOnAction(e->{
			StageManager.getInstance().setScene(new ViewAllPCView().getScene());
		});
		logout_mi.setOnAction(e->{
			UserController.logout();
		});
		
		// Admin
		viewAllReport_mi.setOnAction(e->{
			StageManager.getInstance().setScene(new ViewAllReportView().getScene());
		});
		viewAllStaff_mi.setOnAction(e->{
			StageManager.getInstance().setScene(new ViewAllStaffView().getScene());
		});
		viewAllStaffJob_mi.setOnAction(e->{
			StageManager.getInstance().setScene(new ViewAllStaffJobView().getScene());
		});
		viewAllTransactionHistory_mi.setOnAction(e->{
			StageManager.getInstance().setScene(new ViewAllTransactionHistoryView().getScene());
		});
		
		staffID_tf.setOnKeyTyped(e->{
			if(staffID_tf.getText().equals("")) {
				return;
			}
			
			if(!Character.isDigit(staffID_tf.getText().charAt(staffID_tf.getText().length() - 1))) {
				staffID_tf.clear();
			}
		});
		submit_btn.setOnMouseClicked(event -> {
			if(staffID_tf.getText().equals("")) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setContentText("Staff ID field must be filled");
				alert.showAndWait();
				return;
			}
			
			// Kami asumsikan kita tidak boleh mengganti role diri sendiri
			if(Integer.parseInt(staffID_tf.getText()) == UserStore.getInstance().getUserID()) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setContentText("You are not allowed to change your own role");
				alert.showAndWait();
				return;
			}
			
			// Kami asumsikan kita tidak boleh mengganti role customer
			User user = UserController.getUserData(Integer.parseInt(staffID_tf.getText()));
			if(user.getUserRole().equals("Customer")) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setContentText("Target person is a customer");
				alert.showAndWait();
				return;
			}
			
			boolean result = UserController.changeUserRole(Integer.parseInt(staffID_tf.getText()), newRole_cb.getSelectionModel().getSelectedItem());
			if(result) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText("Success");
				alert.setContentText("User Role has been successfuly changed");
				alert.showAndWait();
				StageManager.getInstance().setScene(new ViewAllStaffView().getScene());
			}
		});
	}

}
