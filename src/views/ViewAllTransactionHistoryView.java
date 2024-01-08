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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import models.TransactionHeader;
import util.StageManager;

public class ViewAllTransactionHistoryView extends BaseView {

	private Label viewAllTransactionHistory_lb;
	
	private TableView<TransactionHeader> transactionHistoryTable_tv;
	
	private Label viewTransactionDetailTitle_lb;
	private Label viewTransactionDetail_lb;
	private TextField viewTransactionDetail_tf;
	private Button viewTransactionDetail_btn;
	
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
		this.viewAllTransactionHistory_lb = new Label("View All Transaction History");
		this.viewTransactionDetailTitle_lb = new Label("View Transaction Detail");
		this.viewTransactionDetail_lb = new Label("Transaction ID");
	}
	
	public void initializeFormComponents() {
		transactionHistoryTable_tv = new TableView<TransactionHeader>();
		setTableComponent();
		this.viewTransactionDetail_tf = new TextField();
		this.viewTransactionDetail_btn = new Button("View Transaction Detail");
	}

	private void setTableComponent() {
		TableColumn<TransactionHeader, Integer> idColumn = new TableColumn<TransactionHeader, Integer>("Transaction ID");
		idColumn.setCellValueFactory(new PropertyValueFactory<TransactionHeader, Integer>("transactionID"));
		idColumn.setMinWidth(gpCenter.getWidth() / 4);
		TableColumn<TransactionHeader, Integer> staffIdColumn = new TableColumn<TransactionHeader, Integer>("Staff ID");
		staffIdColumn.setCellValueFactory(new PropertyValueFactory<TransactionHeader, Integer>("staffID"));
		staffIdColumn.setMinWidth(gpCenter.getWidth() / 4);
		TableColumn<TransactionHeader, String> staffNameColumn = new TableColumn<TransactionHeader, String>("Staff Name");
		staffNameColumn.setCellValueFactory(new PropertyValueFactory<TransactionHeader, String>("staffName"));
		staffNameColumn.setMinWidth(gpCenter.getWidth() / 4);
		TableColumn<TransactionHeader, LocalDate> transactionDateColumn = new TableColumn<TransactionHeader, LocalDate>("Transaction Date");
		transactionDateColumn.setCellValueFactory(new PropertyValueFactory<TransactionHeader, LocalDate>("transactionDate"));
		transactionDateColumn.setMinWidth(gpCenter.getWidth() / 4);
		
		transactionHistoryTable_tv.getColumns().addAll(idColumn, staffIdColumn, staffNameColumn, transactionDateColumn);
	
		Vector<TransactionHeader> ths = TransactionController.getAllTransactionHeader();
		
		for (TransactionHeader transactionHeader : ths) {
			transactionHistoryTable_tv.getItems().add(transactionHeader);
		}
	}
	
	@Override
	public void setComponent() {
		this.bp.setTop(menubar);
		this.bp.setLeft(gpLeft);
		this.bp.setCenter(gpCenter);

		this.gpLeft.add(viewAllTransactionHistory_lb, 0, 1);
		this.gpLeft.add(transactionHistoryTable_tv, 0, 2);	
		
		this.gpCenter.add(viewTransactionDetailTitle_lb, 0, 3);
		this.gpCenter.add(viewTransactionDetail_lb, 0, 4);
		this.gpCenter.add(viewTransactionDetail_tf, 1, 4);
		this.gpCenter.add(viewTransactionDetail_btn, 1, 5);
		
		this.scene.setRoot(bp);
	}

	@Override
	public void setStyle() {
		viewAllTransactionHistory_lb.setFont(Font.font(20));
		viewTransactionDetailTitle_lb.setFont(Font.font(null, FontWeight.BOLD, -1));
		this.gpLeft.setPadding(new Insets(15, 15, 15, 15));
		this.gpCenter.setPadding(new Insets(15, 15, 15, 15));
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
		
		viewTransactionDetail_btn.setOnMouseClicked(e->{
			StageManager.getInstance().setScene(new ViewTransactionDetailView(viewTransactionDetail_tf.getText()).getScene());
		});
		
		viewTransactionDetail_tf.setOnKeyTyped(e->{
			String input = viewTransactionDetail_tf.getText();
			
			if(input.length() <= 0) {
				return;
			}
			if(!Character.isDigit(input.charAt(input.length() - 1))) {
				viewTransactionDetail_tf.clear();
			}
		});
		
		viewTransactionDetail_btn.setOnAction(e->{
			String input = viewTransactionDetail_tf.getText();
			
			if(input.length() <= 0) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setContentText("Please fill Transaction ID field");
				alert.showAndWait();
				return;
			}

			StageManager.getInstance().setScene(new ViewTransactionDetailView(input).getScene());
		});
	}

}
