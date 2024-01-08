package views;

import java.time.LocalDate;
import java.util.Vector;

import controllers.TransactionController;
import controllers.UserController;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import models.TransactionDetail;
import stores.UserStore;
import util.StageManager;

public class ViewTransactionDetailView extends BaseView {

	// Untuk menyimpan transaction ID yang di passing dari View All Transaction History
	private String transactionID;
	
	private Label viewCustomerTransactionHistory_lb;
	
	private TableView<TransactionDetail> transactionHistoryTable_tv;
	
	private Button back_btn;
	
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
	private GridPane gp;

	public ViewTransactionDetailView(String transactionID) {
		super();
		this.transactionID = transactionID;
		this.viewCustomerTransactionHistory_lb.setText("Transaction Detail ID: " + transactionID);
		Vector<TransactionDetail> tds = TransactionController.getAllTransactionDetail(Integer.parseInt(transactionID));
		
		for (TransactionDetail transactionDetail : tds) {
			transactionHistoryTable_tv.getItems().add(transactionDetail);
		}
		if(tds.isEmpty()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setContentText("No Transaction found. Please go back to the previous page");
			alert.showAndWait();
			StageManager.getInstance().setScene(new ViewAllTransactionHistoryView().getScene());
		}
	}
	
	@Override
	public void initialize() {
		this.bp = new BorderPane();
		this.gp = new GridPane();
		this.gp.setPadding(new Insets(15, 15, 15, 15));
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
		this.viewCustomerTransactionHistory_lb = new Label("Transaction Detail ID: " + transactionID);
	}
	
	public void initializeFormComponents() {
		transactionHistoryTable_tv = new TableView<TransactionDetail>();
		setTableComponent();
		this.back_btn = new Button("Back");
	}

	private void setTableComponent() {
		TableColumn<TransactionDetail, Integer> idColumn = new TableColumn<TransactionDetail, Integer>("Transaction ID");
		idColumn.setCellValueFactory(new PropertyValueFactory<TransactionDetail, Integer>("transactionID"));
		idColumn.setMinWidth(gp.getWidth() / 4);
		TableColumn<TransactionDetail, Integer> pcIdColumn = new TableColumn<TransactionDetail, Integer>("PC ID");
		pcIdColumn.setCellValueFactory(new PropertyValueFactory<TransactionDetail, Integer>("pcID"));
		pcIdColumn.setMinWidth(gp.getWidth() / 4);
		TableColumn<TransactionDetail, LocalDate> customerNameColumn = new TableColumn<TransactionDetail, LocalDate>("Customer Name");
		customerNameColumn.setCellValueFactory(new PropertyValueFactory<TransactionDetail, LocalDate>("customerName"));
		customerNameColumn.setMinWidth(gp.getWidth() / 4);
		TableColumn<TransactionDetail, LocalDate> bookedTimeColumn = new TableColumn<TransactionDetail, LocalDate>("Booked Time");
		bookedTimeColumn.setCellValueFactory(new PropertyValueFactory<TransactionDetail, LocalDate>("bookedTime"));
		bookedTimeColumn.setMinWidth(gp.getWidth() / 4);
		
		transactionHistoryTable_tv.getColumns().addAll(idColumn, pcIdColumn, customerNameColumn, bookedTimeColumn);
		
	}
	
	@Override
	public void setComponent() {
		this.bp.setTop(menubar);
		this.bp.setCenter(gp);
		this.gp.add(back_btn, 0, 1);
		this.gp.add(viewCustomerTransactionHistory_lb, 0, 2);
		
		this.gp.add(transactionHistoryTable_tv, 0, 3);		
		
		this.scene.setRoot(bp);
	}

	@Override
	public void setStyle() {
		viewCustomerTransactionHistory_lb.setFont(Font.font(20));
		GridPane.setColumnSpan(transactionHistoryTable_tv, 2);
		GridPane.setHalignment(transactionHistoryTable_tv, HPos.CENTER);
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
		
		back_btn.setOnAction(e->{
			StageManager.getInstance().setScene(new ViewAllTransactionHistoryView().getScene());
		});
	}

}
