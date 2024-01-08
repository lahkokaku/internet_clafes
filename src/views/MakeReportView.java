package views;

import java.util.Vector;

import controllers.PCController;
import controllers.ReportController;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import models.PC;
import stores.UserStore;
import util.StageManager;

public class MakeReportView extends BaseView {

	private Label makeReport_lb;
	
	private Label pcID_lb;
	private ComboBox<Integer> pcID_combo;
		
	private Label reportNote_lb;
	private TextArea reportNote_ta;
	
	private MenuBar menubar;
	private Menu generalMenu_menu;
	private MenuItem viewAllPC_mi;
	private MenuItem logout_mi;
	
	private Menu customerMenu_menu;
	private MenuItem viewCustomerTransactionHistory_mi;
	
	private Menu operatorMenu_menu;
	private MenuItem viewPCBookedData_mi;
	
	private MenuItem makeReport_mi;
	
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
		this.gp.setPadding(new Insets(15, 15, 15, 15));
		this.submit_btn = new Button("Submit");
	}
	
	private void initializeMenu() {
		this.generalMenu_menu = new Menu("General Menu");
		this.viewAllPC_mi = new MenuItem("View All PC");
		this.logout_mi = new MenuItem("Logout");
		this.generalMenu_menu.getItems().addAll(viewAllPC_mi, logout_mi);
		
		this.customerMenu_menu = new Menu("Customer Menu");
		this.viewCustomerTransactionHistory_mi = new MenuItem("View Your Transaction History");
		this.customerMenu_menu.getItems().addAll(viewCustomerTransactionHistory_mi);
		
		this.operatorMenu_menu = new Menu("Operator Menu");
		this.viewPCBookedData_mi = new MenuItem("View Booked PC");
		this.operatorMenu_menu.getItems().addAll(viewPCBookedData_mi);
		
		this.makeReport_mi = new MenuItem("Make Report");
		this.menubar = new MenuBar(generalMenu_menu);
		
		if(UserStore.getInstance().isAuth()) {
			if(UserStore.getInstance().getUserRole().equals("Customer")) {
				this.customerMenu_menu.getItems().add(makeReport_mi);
				this.menubar.getMenus().add(customerMenu_menu);
			}
			if(UserStore.getInstance().getUserRole().equals("Operator")) {
				this.operatorMenu_menu.getItems().add(makeReport_mi);				
				this.menubar.getMenus().add(operatorMenu_menu);
			}
		}
		
	}
	
	public void initializeLabels() {
		this.makeReport_lb = new Label("Make Report");
		this.pcID_lb = new Label("PC ID to be reported");
		this.reportNote_lb = new Label("Report Note");
	}
	
	public void initializeFormComponents() {
		this.pcID_combo = new ComboBox<>();
		this.reportNote_ta = new TextArea();
		
		Vector<PC> pcs = PCController.getAllPCData();
		for (PC pc : pcs) {
			pcID_combo.getItems().add(pc.getPcID());
		}
		
		this.pcID_combo.getSelectionModel().selectFirst();
	}

	@Override
	public void setComponent() {
		this.bp.setTop(menubar);
		this.bp.setCenter(gp);
		this.gp.add(makeReport_lb, 0, 1);
		this.gp.add(pcID_lb, 0, 2);
		this.gp.add(pcID_combo, 1, 2);
		this.gp.add(reportNote_lb, 0, 3);
		this.gp.add(reportNote_ta, 1, 3);
		this.gp.add(submit_btn, 0, 6);
		
		this.scene.setRoot(bp);
	}

	@Override
	public void setStyle() {
		makeReport_lb.setFont(Font.font(20));
//		this.gp.setAlignment(Pos.CENTER);
		GridPane.setColumnSpan(makeReport_lb, 2);
		GridPane.setHalignment(makeReport_lb, HPos.CENTER);
		
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
		
		// Customer
		viewCustomerTransactionHistory_mi.setOnAction(e->{
			StageManager.getInstance().setScene(new ViewCustomerTransactionHistoryView().getScene());
		});
		
		// Operator
		viewPCBookedData_mi.setOnAction(e->{
			StageManager.getInstance().setScene(new ViewPCBookedView().getScene());
		});
		
		// Customer and Operator
		makeReport_mi.setOnAction(e->{
			StageManager.getInstance().setScene(new MakeReportView().getScene());
		});
	
		submit_btn.setOnAction(e->{
			if(pcID_combo.getSelectionModel().getSelectedItem() == null) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setContentText("No PC selected");
				alert.showAndWait();
				return;
			}
			
			if(reportNote_ta.getText().equals("")) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setContentText("Report Note must be filled");
				alert.showAndWait();
				return;
			}
			
			int result = ReportController.addNewReport(UserStore.getInstance().getUserRole(), pcID_combo.getSelectionModel().getSelectedItem(), reportNote_ta.getText());
			
			if(result > 0) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText("Success");
				alert.setContentText("Report has been successfuly made");
				alert.showAndWait();
				StageManager.getInstance().setScene(new MakeReportView().getScene());
			}
		});
	}

}
