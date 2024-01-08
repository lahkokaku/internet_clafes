package views;

import controllers.PCController;
import controllers.UserController;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import models.PC;
import stores.UserStore;
import util.StageManager;

public class ViewPCDetailView extends BaseView {

	private Label viewPCDetail_lb;
	
	private Label pcID_lb;
	private TextField pcID_tf;
		
	private Label pcCondition_lb;
	private ComboBox<String> pcCondition_cb;
	
	private MenuBar menubar;
	private Menu generalMenu_menu;
	private MenuItem viewAllPC_mi;
	private MenuItem logout_mi;
	
	private Menu adminMenu_menu;
	private MenuItem viewAllReport_mi;
	private MenuItem viewAllStaff_mi;
	private MenuItem viewAllStaffJob_mi;
	private MenuItem viewAllTransactionHistory_mi;
	
	private Menu customerMenu_menu;
	private MenuItem viewCustomerTransactionHistory_mi;
	
	private Menu operatorMenu_menu;
	private MenuItem viewPCBookedData_mi;
	
	private MenuItem makeReportCust_mi;
	private MenuItem makeReportOpt_mi;
	
	private Menu technicianMenu_menu;
	private MenuItem viewTechnicianJob_mi;
	
	private BorderPane bp;
	private GridPane gp;
	private Button back_btn;
	private Button update_btn;
	private Button delete_btn;

	public ViewPCDetailView(int pcID) {
		super();
		this.pcID_tf.setText(String.valueOf(pcID));
		this.pcID_tf.setEditable(false);
		PC pc = PCController.getPCDetail(pcID);
		this.pcCondition_cb.getSelectionModel().select(pc.getPcCondition());
	}
	
	@Override
	public void initialize() {
		initializeMenu();
		initializeLabels();
		initializeFormComponents();
		
		this.bp = new BorderPane();
		this.gp = new GridPane();
		this.gp.setPadding(new Insets(15, 15, 15, 15));
		this.delete_btn = new Button("Delete");
		this.update_btn = new Button("Update");
		this.back_btn = new Button("Back");
	}
	
	private void initializeMenu() {
		this.generalMenu_menu = new Menu("General Menu");
		this.viewAllPC_mi = new MenuItem("View All PC");
		this.logout_mi = new MenuItem("Logout");
		
		if(UserStore.getInstance().isAuth()) {			
			// User udah login
			this.generalMenu_menu.getItems().addAll(viewAllPC_mi, logout_mi);
		}
		
		
		this.adminMenu_menu = new Menu("Admin Menu");
		this.viewAllReport_mi = new Menu("View All Report");
		this.viewAllStaff_mi = new Menu("View All Staff");
		this.viewAllStaffJob_mi = new Menu("View All Staff Job");
		this.viewAllTransactionHistory_mi = new Menu("View All Transaction History");
		this.adminMenu_menu.getItems().addAll(viewAllReport_mi, viewAllStaff_mi, viewAllStaffJob_mi, viewAllTransactionHistory_mi);

		this.makeReportCust_mi = new MenuItem("Make Report");
		this.makeReportOpt_mi = new MenuItem("Make Report");
		
		this.customerMenu_menu = new Menu("Customer Menu");
		this.viewCustomerTransactionHistory_mi = new MenuItem("View Your Transaction History");
		this.customerMenu_menu.getItems().addAll(viewCustomerTransactionHistory_mi, makeReportCust_mi);
		
		this.operatorMenu_menu = new Menu("Operator Menu");
		this.viewPCBookedData_mi = new MenuItem("View Booked PC");
		this.operatorMenu_menu.getItems().addAll(viewPCBookedData_mi, makeReportOpt_mi);
		
		this.technicianMenu_menu = new Menu("Computer Technician Menu");
		this.viewTechnicianJob_mi = new MenuItem("View Computer Technician Job");
		this.technicianMenu_menu.getItems().add(viewTechnicianJob_mi);
		
		this.menubar = new MenuBar(generalMenu_menu);
		
		if(UserStore.getInstance().isAuth()) {
			// Admin
			if(UserStore.getInstance().getUserRole().equals("Admin"))
				this.menubar.getMenus().add(adminMenu_menu);
			
			// Customer
			if(UserStore.getInstance().getUserRole().equals("Customer")) {			
				this.menubar.getMenus().add(customerMenu_menu);
			}
			
			// Operator
			if(UserStore.getInstance().getUserRole().equals("Operator")) {			
				this.menubar.getMenus().add(operatorMenu_menu);
			}
			
			// Computer Technician		
			if(UserStore.getInstance().getUserRole().equals("Computer Technician"))			
				this.menubar.getMenus().add(technicianMenu_menu);
		}
	}
	
	public void initializeLabels() {
		this.viewPCDetail_lb = new Label("View PC Detail");
		this.pcID_lb = new Label("PC ID");
		this.pcCondition_lb = new Label("PC Condition");
	}
	
	public void initializeFormComponents() {
		this.pcID_tf = new TextField();
		this.pcCondition_cb = new ComboBox<String>();
		this.pcCondition_cb.getItems().addAll("Usable", "Broken", "Maintenance");
	}

	@Override
	public void setComponent() {
		this.bp.setTop(menubar);
		this.bp.setCenter(gp);
		this.gp.add(back_btn, 0, 1);
		this.gp.add(viewPCDetail_lb, 0, 2);
		this.gp.add(pcID_lb, 0, 3);
		this.gp.add(pcID_tf, 1, 3);
		this.gp.add(pcCondition_lb, 0, 4);
		this.gp.add(pcCondition_cb, 1, 4);
		
		if(UserStore.getInstance().getUserRole().equals("Admin")) {			
			this.gp.add(delete_btn, 0, 5);
			this.gp.add(update_btn, 1, 5);
		}else {
			pcCondition_cb.setEditable(false);
		}
		
		this.scene.setRoot(bp);
	}

	@Override
	public void setStyle() {
		viewPCDetail_lb.setFont(Font.font(20));
		GridPane.setColumnSpan(viewPCDetail_lb, 2);
		GridPane.setHalignment(viewPCDetail_lb, HPos.CENTER);
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
		
		// Customer
		viewCustomerTransactionHistory_mi.setOnAction(e->{
			StageManager.getInstance().setScene(new ViewCustomerTransactionHistoryView().getScene());
		});
		
		// Operator
		viewPCBookedData_mi.setOnAction(e->{
			StageManager.getInstance().setScene(new ViewPCBookedView().getScene());
		});
		
		// Customer and Operator
		makeReportCust_mi.setOnAction(e->{
			StageManager.getInstance().setScene(new MakeReportView().getScene());
		});
		makeReportOpt_mi.setOnAction(e->{
			StageManager.getInstance().setScene(new MakeReportView().getScene());
		});
		
		// Computer Technician
		viewTechnicianJob_mi.setOnAction(e->{
			StageManager.getInstance().setScene(new ViewTechnicianJobView().getScene());
		});
		
		back_btn.setOnAction(e->{
			StageManager.getInstance().setScene(new ViewAllPCView().getScene());
		});
		
		delete_btn.setOnAction(e->{
			int result = PCController.deletePC(Integer.parseInt(pcID_tf.getText()));
			
			if(result > 0) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText("Success");
				alert.setContentText("Successfuly deleted PC");
				alert.showAndWait();
				StageManager.getInstance().setScene(new ViewAllPCView().getScene());
			}
		});
		update_btn.setOnAction(e->{
			int result = PCController.updatePC(Integer.valueOf(pcID_tf.getText()), pcCondition_cb.getSelectionModel().getSelectedItem());

			if(result > 0) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText("Success");
				alert.setContentText("Successfuly updated PC");
				alert.showAndWait();
				StageManager.getInstance().setScene(new ViewPCDetailView(Integer.valueOf(pcID_tf.getText())).getScene());
			}
		});
	}

}
