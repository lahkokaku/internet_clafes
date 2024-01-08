package views;

import java.util.Vector;

import controllers.JobController;
import controllers.PCController;
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
import models.Job;
import models.PC;
import models.User;
import stores.UserStore;
import util.StageManager;

public class ViewAllStaffJobView extends BaseView {

	private Label viewAllStaffJob_lb;
	
	private TableView<Job> staffJobTable_tv;
	
	private Label job_lb;
	private Label pcID_lb;
	private TextField pcID_tf;
	private Label technicianID_lb;
	private ComboBox<Integer> technicianID_cb;
	
	private Label updateJob_lb;
	private Label updateJobID_lb;
	private ComboBox<Integer> updateJobID_cb;
	private Label jobStatus_lb;
	private ComboBox<String> jobStatus_cb;
	
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
	private Button assignJob_btn;
	private Button updateJobStatus_btn;

	@Override
	public void initialize() {
		this.bp = new BorderPane();
		this.gpLeft = new GridPane();
		this.gpCenter = new GridPane();
		
		initializeMenu();
		initializeLabels();
		initializeFormComponents();
		
		Vector<Job> jobs = JobController.GetAllJobData();
		
		for (Job job : jobs) {
			staffJobTable_tv.getItems().add(job);
			updateJobID_cb.getItems().add(job.getJobID());
		}
		updateJobID_cb.getSelectionModel().selectFirst();
		
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
		this.viewAllStaffJob_lb = new Label("View All Staff Job");
		this.pcID_lb = new Label("PC ID to assign");
		this.technicianID_lb = new Label("Technician ID to be assigned");
		this.updateJobID_lb = new Label("Job ID to Update");
		this.jobStatus_lb = new Label("New Status to be updated");
		this.job_lb = new Label("Assign Job");
		this.updateJob_lb = new Label("Update Job Status");
	}
	
	public void initializeFormComponents() {
		this.assignJob_btn = new Button("Assign");
		this.updateJobStatus_btn = new Button("Update");
		staffJobTable_tv = new TableView<Job>();
		setTableComponent();
		this.pcID_tf = new TextField();
		this.technicianID_cb = new ComboBox<Integer>();
		this.updateJobID_cb = new ComboBox<Integer>();
		this.jobStatus_cb = new ComboBox<String>();
		
		this.jobStatus_cb.getItems().addAll("Complete", "UnComplete");
		this.jobStatus_cb.getSelectionModel().selectFirst();
		Vector<User> technicians = UserController.getAllTechnician();
		for (User user : technicians) {
			technicianID_cb.getItems().add((user.getUserID()));
		}
		technicianID_cb.getSelectionModel().selectFirst();
	}

	private void setTableComponent() {
		TableColumn<Job, Integer> idColumn = new TableColumn<Job, Integer>("Job ID");
		idColumn.setCellValueFactory(new PropertyValueFactory<Job, Integer>("jobID"));
		idColumn.setMinWidth(gpCenter.getWidth() / 4);
		TableColumn<Job, Integer> userIdColumn = new TableColumn<Job, Integer>("Technician ID");
		userIdColumn.setCellValueFactory(new PropertyValueFactory<Job, Integer>("userID"));
		userIdColumn.setMinWidth(gpCenter.getWidth() / 4);
		TableColumn<Job, Integer> pcIdColumn = new TableColumn<Job, Integer>("PC ID");
		pcIdColumn.setCellValueFactory(new PropertyValueFactory<Job, Integer>("pcID"));
		pcIdColumn.setMinWidth(gpCenter.getWidth() / 4);
		TableColumn<Job, String> statusColumn = new TableColumn<Job, String>("Job Status");
		statusColumn.setCellValueFactory(new PropertyValueFactory<Job, String>("jobStatus"));
		statusColumn.setMinWidth(gpCenter.getWidth() / 4);
		
		staffJobTable_tv.getColumns().addAll(idColumn, userIdColumn, pcIdColumn, statusColumn);
		
	}
	
	@Override
	public void setComponent() {
		this.bp.setTop(menubar);
		this.bp.setLeft(gpLeft);
		this.bp.setCenter(gpCenter);
		
		this.gpLeft.add(viewAllStaffJob_lb, 0, 1);
		this.gpLeft.add(staffJobTable_tv, 0, 2);

		int i = 1;
		this.gpCenter.add(job_lb, 0, i++);
		this.gpCenter.add(pcID_lb, 0, i); this.gpCenter.add(pcID_tf, 1, i++);
		this.gpCenter.add(technicianID_lb, 0, i); this.gpCenter.add(technicianID_cb, 1, i++);
		this.gpCenter.add(assignJob_btn, 1, i++);
		this.gpCenter.add(new Label(""), 0, i++);
		this.gpCenter.add(updateJob_lb, 0, i++);
		this.gpCenter.add(updateJobID_lb, 0, i); this.gpCenter.add(updateJobID_cb, 1, i++);
		this.gpCenter.add(jobStatus_lb, 0, i); this.gpCenter.add(jobStatus_cb, 1, i++);
		this.gpCenter.add(updateJobStatus_btn, 1, i++);
		
		this.scene.setRoot(bp);
	}

	@Override
	public void setStyle() {
		viewAllStaffJob_lb.setFont(Font.font(20));
		job_lb.setFont(Font.font(null, FontWeight.BOLD, -1));
		updateJob_lb.setFont(Font.font(null, FontWeight.BOLD, -1));
		this.gpLeft.setPadding(new Insets(15, 15, 15, 15));
		this.gpCenter.setPadding(new Insets(15, 15, 15, 15));
		GridPane.setColumnSpan(staffJobTable_tv, 2);
		GridPane.setHalignment(staffJobTable_tv, HPos.CENTER);
		
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
		
		pcID_tf.setOnKeyTyped(e->{
			String input = pcID_tf.getText();
			
			if(input.length() <= 0) {
				return;
			}
			if(!Character.isDigit(input.charAt(input.length() - 1))) {
				pcID_tf.clear();
			}
		});
		assignJob_btn.setOnAction(e->{
			
			if(technicianID_cb.getSelectionModel().getSelectedItem() == null) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setContentText("No job selected");
				alert.showAndWait();
				return;
			}
			
			PC pc = PCController.getPCDetail(Integer.parseInt(pcID_tf.getText()));
			
			if(pc == null) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setContentText("PC not found");
				alert.showAndWait();
				return;
			}
			
			Vector<Job> jobs = JobController.GetAllJobData();
			for (Job job : jobs) {
				if(job.getUserID() == technicianID_cb.getSelectionModel().getSelectedItem() && job.getJobStatus().equals("UnComplete")) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setContentText("Someone has already been working on that PC");
					alert.showAndWait();
					return;
				}
			}
			
			int result = JobController.AddNewJob(technicianID_cb.getSelectionModel().getSelectedItem(), Integer.parseInt(pcID_tf.getText()));
			if(result > 0) {
				PCController.updatePC(Integer.parseInt(pcID_tf.getText()), "Maintenance");
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText("Success");
				alert.setContentText("Job has been successfuly assigned");
				alert.showAndWait();
				StageManager.getInstance().setScene(new ViewAllStaffJobView().getScene());
				return;
			}
		});
		
		updateJobStatus_btn.setOnAction(event -> {
			if(updateJobID_cb.getSelectionModel().getSelectedItem() == null) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setContentText("No job selected");
				alert.showAndWait();
				return;
			}
			
			int result = JobController.UpdateJobStatus(updateJobID_cb.getSelectionModel().getSelectedItem(), jobStatus_cb.getSelectionModel().getSelectedItem());
			
			if(result > 0) {
				if(jobStatus_cb.getSelectionModel().getSelectedItem().equals("Complete")) {
					PCController.updatePC(JobController.getJobDetail(updateJobID_cb.getSelectionModel().getSelectedItem()).getPcID(), "Usable");
				}
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText("Success");
				alert.setContentText("Job status has been successfuly updated");
				alert.showAndWait();
				StageManager.getInstance().setScene(new ViewAllStaffJobView().getScene());
				return;
			}
		});
	}

}
