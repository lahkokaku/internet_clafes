package views;

import java.time.LocalDate;
import java.util.Vector;

import controllers.PCBookController;
import controllers.PCController;
import controllers.UserController;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import models.PC;
import models.PCBook;
import stores.UserStore;
import util.StageManager;

public class ViewAllPCView extends BaseView {

	private Label viewAllPC_lb;
	
	private TableView<PC> pcTable_tv;
	
	private Label addPc_lb;
	private Label viewPc_lb;
	private Label bookPc_lb;
	
	private Label pcID_lb;
	private Label bookDate_lb;
	private DatePicker bookDate_dp;
	private TextField pcID_tf;
	
	private Label addPcName_lb;
	private TextField addPcId_tf;

	private Label viewPcID_lb;
	private TextField viewPcID_tf;
	
	private MenuBar menubar;
	private Menu generalMenu_menu;
	private MenuItem viewAllPC_mi;
	private MenuItem login_mi;
	private MenuItem registration_mi;
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
	private GridPane gpLeft;
	private GridPane gpCenter;
	private Button book_btn;
	private Button addPc_btn;
	private Button viewPc_btn;

	@Override
	public void initialize() {
		this.bp = new BorderPane();
		this.gpLeft = new GridPane();
		this.gpCenter = new GridPane();
		this.book_btn = new Button("Submit");
		this.addPc_btn = new Button("Add New PC");
		this.viewPc_btn = new Button("View PC");
		initializeMenu();
		initializeLabels();
		initializeFormComponents();
		
	}
	
	private void initializeMenu() {
		this.generalMenu_menu = new Menu("General Menu");
		this.viewAllPC_mi = new MenuItem("View All PC");
		this.registration_mi = new MenuItem("Registration");
		this.login_mi = new MenuItem("Login");
		this.logout_mi = new MenuItem("Logout");
		
		if(UserStore.getInstance().isAuth()) {			
			// User udah login
			this.generalMenu_menu.getItems().addAll(viewAllPC_mi, logout_mi);
		}else {			
			// User belum login
			this.generalMenu_menu.getItems().addAll(viewAllPC_mi, login_mi, registration_mi);
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
		
		this.technicianMenu_menu = new Menu("Technician Menu");
		this.viewTechnicianJob_mi = new MenuItem("View Technician Job");
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
		this.viewAllPC_lb = new Label("View All PC");
		this.pcID_lb = new Label("PC ID to Book");
		this.bookDate_lb = new Label("Book Date");
		this.addPcName_lb = new Label("New PC ID");
		this.viewPcID_lb = new Label("PC ID to View");
		this.addPc_lb = new Label("Add new PC");
		this.viewPc_lb = new Label("View PC Detail");
		this.bookPc_lb = new Label("Book PC");
	}
	
	public void initializeFormComponents() {
		pcTable_tv = new TableView<PC>();
		setTableComponent();
		this.pcID_tf = new TextField();
		this.bookDate_dp = new DatePicker(LocalDate.now());
		this.addPcId_tf = new TextField();
		this.viewPcID_tf = new TextField();
	}

	private void setTableComponent() {
		TableColumn<PC, Integer> idColumn = new TableColumn<PC, Integer>("PC ID");
		idColumn.setCellValueFactory(new PropertyValueFactory<PC, Integer>("pcID"));
		idColumn.setMinWidth(gpCenter.getWidth() / 2);
		TableColumn<PC, String> conditionColumn = new TableColumn<PC, String>("PC_Condition");
		conditionColumn.setCellValueFactory(new PropertyValueFactory<PC, String>("pcCondition"));
		conditionColumn.setMinWidth(gpCenter.getWidth() / 2);
		
		pcTable_tv.getColumns().addAll(idColumn, conditionColumn);
		
		Vector<PC> pcs = PCController.getAllPCData();
		
		for (PC pc : pcs) {
			pcTable_tv.getItems().add(pc);
		}
	}
	
	@Override
	public void setComponent() {
		this.bp.setTop(menubar);
		this.bp.setLeft(gpLeft);
		this.bp.setCenter(gpCenter);
		this.gpLeft.add(viewAllPC_lb, 0, 1);
		this.gpLeft.add(pcTable_tv, 0, 2);
		
		if(UserStore.getInstance().isAuth()) {
			int i = 1;
			this.gpCenter.add(viewPc_lb, 0, i++);
			this.gpCenter.add(viewPcID_lb, 0, i); this.gpCenter.add(viewPcID_tf, 1, i++);
			this.gpCenter.add(viewPc_btn, 1, i++);	
			this.gpCenter.add(new Label(""), 0, i++);
			
			if(UserStore.getInstance().getUserRole().equals("Customer")) {
				// Udah Login dan role Customer
				this.gpCenter.add(bookPc_lb, 0, i++);
				this.gpCenter.add(pcID_lb, 0, i); this.gpCenter.add(pcID_tf, 1, i++);
				this.gpCenter.add(bookDate_lb, 0, i); this.gpCenter.add(bookDate_dp, 1, i++);
				this.gpCenter.add(book_btn, 1, i++);
				
			}
			if(UserStore.getInstance().getUserRole().equals("Admin")) {
				// Udah Login dan role Admin
				this.gpCenter.add(addPc_lb, 0, i++);
				this.gpCenter.add(addPcName_lb, 0, i); this.gpCenter.add(addPcId_tf, 1, i++);
				this.gpCenter.add(addPc_btn, 1, i++);
				
			}
		}
		
		
		this.scene.setRoot(bp);
	}

	@Override
	public void setStyle() {
		viewAllPC_lb.setFont(Font.font(20));
		this.gpLeft.setPadding(new Insets(15, 15, 15, 15));
		this.gpCenter.setPadding(new Insets(15, 15, 15, 15));
		addPc_lb.setFont(Font.font(null, FontWeight.BOLD, -1));
		viewPc_lb.setFont(Font.font(null, FontWeight.BOLD, -1));
		bookPc_lb.setFont(Font.font(null, FontWeight.BOLD, -1));
		GridPane.setColumnSpan(pcTable_tv, 2);
		GridPane.setHalignment(pcTable_tv, HPos.CENTER);
	}
	
	@Override
	public void setAction() {
		// General
		viewAllPC_mi.setOnAction(e->{
			StageManager.getInstance().setScene(new ViewAllPCView().getScene());
		});
		login_mi.setOnAction(e->{
			StageManager.getInstance().setScene(new LoginView().getScene());
		});
		registration_mi.setOnAction(e->{
			StageManager.getInstance().setScene(new RegistrationView().getScene());
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
		
		pcID_tf.setOnKeyTyped(e->{
			String input = addPcId_tf.getText();
			
			if(input.length() <= 0) {
				return;
			}
			if(!Character.isDigit(input.charAt(input.length() - 1))) {
				addPcId_tf.clear();
			}
		});
		book_btn.setOnMouseClicked(event -> {
			if(pcID_tf.getText().equals("")) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setContentText("Please fill PC ID field");
				alert.showAndWait();
				return;
			}
			
			// Booking date harus ditanggal yang sudah lewat
			if(bookDate_dp.getValue().isBefore(LocalDate.now())) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setContentText("Book Date should not be before than today");
				alert.showAndWait();
				return;
			}
			
			PC pc = PCController.getPCDetail(Integer.parseInt(pcID_tf.getText()));
			
			if(pc == null) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setContentText("PC doesn't exist");
				alert.showAndWait();
				return;
			}
			if(!pc.getPcCondition().equals("Usable")) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setContentText("PC isn't Usable");
				alert.showAndWait();
				return;
			}
			
			int result = PCBookController.addNewBook(Integer.parseInt(pcID_tf.getText()), UserStore.getInstance().getUserID(), bookDate_dp.getValue());
			if(result > 0) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText("Success");
				alert.setContentText("Book has been successfuly placed");
				alert.showAndWait();
				StageManager.getInstance().setScene(new ViewAllPCView().getScene());
				return;
			}
		});
		
		addPcId_tf.setOnKeyTyped(e->{
			String input = addPcId_tf.getText();
			
			if(input.length() <= 0) {
				return;
			}
			if(!Character.isDigit(input.charAt(input.length() - 1))) {
				addPcId_tf.clear();
			}
		});
		addPc_btn.setOnAction(e->{
			if(addPcId_tf.getText().equals("")) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setContentText("Please fill PC ID field");
				alert.showAndWait();
				return;
			}
			
			int result = PCController.addNewPC(Integer.parseInt(addPcId_tf.getText()));
			if(result > 0) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText("Success");
				alert.setContentText("Successfuly added new PC");
				alert.showAndWait();
				StageManager.getInstance().setScene(new ViewAllPCView().getScene());
			}
		});
		viewPc_btn.setOnAction(e->{
			if(viewPcID_tf.getText().length() < 1) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setContentText("Please fill PC ID field");
				alert.showAndWait();
				return;
			}
			
			PC pc = PCController.getPCDetail(Integer.parseInt(viewPcID_tf.getText()));
			
			if(pc == null) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("PC not found");
				alert.showAndWait();
				return;
			}
			
			StageManager.getInstance().setScene(new ViewPCDetailView(Integer.valueOf(viewPcID_tf.getText())).getScene());
		});
	}

}
