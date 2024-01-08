package views;

import java.time.LocalDate;
import java.util.Vector;

import controllers.ReportController;
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
import models.PC;
import models.Report;
import stores.UserStore;
import util.StageManager;

public class ViewAllReportView extends BaseView {

	private Label viewAllReport_lb;
	
	private TableView<Report> reportTable_tv;
	
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
		this.viewAllReport_lb = new Label("View All Report");
	}
	
	public void initializeFormComponents() {
		reportTable_tv = new TableView<Report>();
		setTableComponent();
	}

	private void setTableComponent() {
		TableColumn<Report, Integer> idColumn = new TableColumn<Report, Integer>("Report ID");
		idColumn.setCellValueFactory(new PropertyValueFactory<Report, Integer>("reportID"));
		idColumn.setMinWidth(gp.getWidth() / 5);
		TableColumn<Report, String> userRoleColumn = new TableColumn<Report, String>("User Role");
		userRoleColumn.setCellValueFactory(new PropertyValueFactory<Report, String>("userRole"));
		userRoleColumn.setMinWidth(gp.getWidth() / 5);
		TableColumn<Report, Integer> pcIdColumn = new TableColumn<Report, Integer>("PC ID");
		pcIdColumn.setCellValueFactory(new PropertyValueFactory<Report, Integer>("pcID"));
		pcIdColumn.setMinWidth(gp.getWidth() / 5);
		TableColumn<Report, String> reportNoteColumn = new TableColumn<Report, String>("Report Note");
		reportNoteColumn.setCellValueFactory(new PropertyValueFactory<Report, String>("reportNote"));
		reportNoteColumn.setMinWidth(gp.getWidth() / 5);
		TableColumn<Report, LocalDate> reportDateColumn = new TableColumn<Report, LocalDate>("Report Date");
		reportDateColumn.setCellValueFactory(new PropertyValueFactory<Report, LocalDate>("reportDate"));
		reportDateColumn.setMinWidth(gp.getWidth() / 5);
		
		reportTable_tv.getColumns().addAll(idColumn, userRoleColumn, pcIdColumn, reportNoteColumn, reportDateColumn);
		
		Vector<Report> reports = ReportController.getAllReportData();
		
		for (Report report : reports) {
			reportTable_tv.getItems().add(report);
		}
	}
	
	@Override
	public void setComponent() {
		this.bp.setTop(menubar);
		this.bp.setCenter(gp);
		this.gp.add(viewAllReport_lb, 0, 1);
		
		// Udah Login atau belum dan role user
		this.gp.add(reportTable_tv, 0, 2);		
		
		this.scene.setRoot(bp);
	}

	@Override
	public void setStyle() {
		viewAllReport_lb.setFont(Font.font(20));
//		this.gp.setAlignment(Pos.CENTER_LEFT);
		GridPane.setColumnSpan(reportTable_tv, 3);
		GridPane.setHalignment(reportTable_tv, HPos.CENTER);
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
				
	}

}
