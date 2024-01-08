package views;

import java.time.LocalDate;
import java.util.Vector;

import controllers.TransactionController;
import controllers.UserController;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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

public class ViewCustomerTransactionHistoryView extends BaseView {

	private Label viewCustomerTransactionHistory_lb;
	
	private TableView<TransactionDetail> transactionHistoryTable_tv;
	
	private MenuBar menubar;
	private Menu generalMenu_menu;
	private MenuItem viewAllPC_mi;
	private MenuItem logout_mi;

	private Menu customerMenu_menu;
	private MenuItem viewCustomerTransactionHistory_mi;
	
	private MenuItem makeReport_mi;
	
	private BorderPane bp;
	private GridPane gp;

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
		
		this.customerMenu_menu = new Menu("Customer Menu");
		this.viewCustomerTransactionHistory_mi = new MenuItem("View Your Transaction History");
		this.makeReport_mi = new MenuItem("Make Report");
		this.customerMenu_menu.getItems().addAll(viewCustomerTransactionHistory_mi, makeReport_mi);
		
		this.menubar = new MenuBar(generalMenu_menu, customerMenu_menu);
	}
	
	public void initializeLabels() {
		this.viewCustomerTransactionHistory_lb = new Label("Your Transaction History");
	}
	
	public void initializeFormComponents() {
		transactionHistoryTable_tv = new TableView<TransactionDetail>();
		setTableComponent();
	}

	private void setTableComponent() {
		TableColumn<TransactionDetail, Integer> idColumn = new TableColumn<TransactionDetail, Integer>("Transaction ID");
		idColumn.setCellValueFactory(new PropertyValueFactory<TransactionDetail, Integer>("transactionID"));
		idColumn.setMinWidth(gp.getWidth() / 3);
		TableColumn<TransactionDetail, Integer> pcIdColumn = new TableColumn<TransactionDetail, Integer>("PC ID");
		pcIdColumn.setCellValueFactory(new PropertyValueFactory<TransactionDetail, Integer>("pcID"));
		pcIdColumn.setMinWidth(gp.getWidth() / 3);
		TableColumn<TransactionDetail, LocalDate> bookedTimeColumn = new TableColumn<TransactionDetail, LocalDate>("Booked Time");
		bookedTimeColumn.setCellValueFactory(new PropertyValueFactory<TransactionDetail, LocalDate>("bookedTime"));
		bookedTimeColumn.setMinWidth(gp.getWidth() / 3);
		
		transactionHistoryTable_tv.getColumns().addAll(idColumn, pcIdColumn,bookedTimeColumn);
		
		Vector<TransactionDetail> tds = TransactionController.getUserTransactionDetails(UserStore.getInstance().getUserID());
		
		for (TransactionDetail transactionDetail : tds) {
			transactionHistoryTable_tv.getItems().add(transactionDetail);
		}
		
	}
	
	@Override
	public void setComponent() {
		this.bp.setTop(menubar);
		this.bp.setCenter(gp);
		this.gp.add(viewCustomerTransactionHistory_lb, 0, 1);
		
		this.gp.add(transactionHistoryTable_tv, 0, 2);		
		
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
		
		// Customer
		viewCustomerTransactionHistory_mi.setOnAction(e->{
			StageManager.getInstance().setScene(new ViewCustomerTransactionHistoryView().getScene());
		});
		
		// Customer and Operator
		makeReport_mi.setOnAction(e->{
			StageManager.getInstance().setScene(new MakeReportView().getScene());
		});
	}

}
