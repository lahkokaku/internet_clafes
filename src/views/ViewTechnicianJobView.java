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
import stores.UserStore;
import util.StageManager;

public class ViewTechnicianJobView extends BaseView {

	private Label viewTechnicianJob_lb;
	
	private TableView<Job> jobsTable_tv;
	
	private Label finishTitle_lb;
	private Label finish_lb;
	private ComboBox<Integer> finish_combobox;
	
	private MenuBar menubar;
	private Menu generalMenu_menu;
	private MenuItem viewAllPC_mi;
	private MenuItem logout_mi;
	
	private Menu technicianMenu_menu;
	private MenuItem viewTechnicianJob_mi;
	
	private BorderPane bp;
	private GridPane gpLeft;
	private GridPane gpCenter;
	private Button finish_btn;

	@Override
	public void initialize() {
		this.bp = new BorderPane();
		this.gpLeft = new GridPane();
		this.gpCenter = new GridPane();
		
		initializeMenu();
		initializeLabels();
		initializeFormComponents();
		
		Vector<Job> jobs = JobController.GetTechnicianJob(UserStore.getInstance().getUserID());
		for (Job job : jobs) {
			jobsTable_tv.getItems().add(job);
			finish_combobox.getItems().add(job.getJobID());
		}
		finish_combobox.getSelectionModel().selectFirst();
	}
	
	private void initializeMenu() {
		this.generalMenu_menu = new Menu("General Menu");
		this.viewAllPC_mi = new MenuItem("View All PC");
		this.logout_mi = new MenuItem("Logout");
		this.generalMenu_menu.getItems().addAll(viewAllPC_mi, logout_mi);
		
		this.technicianMenu_menu = new Menu("Technician Menu");
		this.viewTechnicianJob_mi = new MenuItem("View Your Job");
		this.technicianMenu_menu.getItems().addAll(viewTechnicianJob_mi);
		
		this.menubar = new MenuBar(generalMenu_menu, technicianMenu_menu);
	}
	
	public void initializeLabels() {
		this.viewTechnicianJob_lb = new Label("Your Job List");
		this.finishTitle_lb = new Label("Finish Job");
		this.finish_lb = new Label("Input Job ID to Complete");
	}
	
	public void initializeFormComponents() {
		this.finish_btn = new Button("Finish");
		this.jobsTable_tv = new TableView<Job>();
		setTableComponent();
		this.finish_combobox = new ComboBox<>();
	}

	private void setTableComponent() {
		TableColumn<Job, Integer> idColumn = new TableColumn<Job, Integer>("Job ID");
		idColumn.setCellValueFactory(new PropertyValueFactory<Job, Integer>("jobID"));
		idColumn.setMinWidth(gpCenter.getWidth() / 4);
		TableColumn<Job, Integer> pcIdColumn = new TableColumn<Job, Integer>("PC ID");
		pcIdColumn.setCellValueFactory(new PropertyValueFactory<Job, Integer>("pcID"));
		pcIdColumn.setMinWidth(gpCenter.getWidth() / 4);
		TableColumn<Job, Integer> userIdColumn = new TableColumn<Job, Integer>("User ID");
		userIdColumn.setCellValueFactory(new PropertyValueFactory<Job, Integer>("userID"));
		userIdColumn.setMinWidth(gpCenter.getWidth() / 4);
		TableColumn<Job, String> jobStatusColumn = new TableColumn<Job, String>("Job Status");
		jobStatusColumn.setCellValueFactory(new PropertyValueFactory<Job, String>("jobStatus"));
		jobStatusColumn.setMinWidth(gpCenter.getWidth() / 4);
		
		jobsTable_tv.getColumns().addAll(idColumn, pcIdColumn, userIdColumn, jobStatusColumn);
		
		
	}
	
	@Override
	public void setComponent() {
		this.bp.setTop(menubar);
		this.bp.setLeft(gpLeft);
		this.bp.setCenter(gpCenter);

		this.gpLeft.add(viewTechnicianJob_lb, 0, 1);
		this.gpLeft.add(jobsTable_tv, 0, 2);
		
		this.gpCenter.add(finishTitle_lb, 0, 1);
		this.gpCenter.add(finish_lb, 0, 2);
		this.gpCenter.add(finish_combobox, 0, 3);
		this.gpCenter.add(finish_btn, 0, 4);
		
		this.scene.setRoot(bp);
	}

	@Override
	public void setStyle() {
		this.gpLeft.setPadding(new Insets(15, 15, 15, 15));
		this.gpCenter.setPadding(new Insets(15, 15, 15, 15));
		viewTechnicianJob_lb.setFont(Font.font(20));
		finishTitle_lb.setFont(Font.font(null, FontWeight.BOLD, -1));
		GridPane.setColumnSpan(jobsTable_tv, 2);
		GridPane.setHalignment(jobsTable_tv, HPos.CENTER);
		
		GridPane.setColumnSpan(finish_btn, 2);
		GridPane.setHalignment(finish_btn, HPos.CENTER);
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
		
		// Operator
		viewTechnicianJob_mi.setOnAction(e->{
			StageManager.getInstance().setScene(new ViewTechnicianJobView().getScene());
		});
		
		finish_btn.setOnMouseClicked(event -> {
			if(finish_combobox.getSelectionModel().getSelectedItem() == null) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setContentText("No job selected");
				alert.showAndWait();
				return;
			}
			
			int result = JobController.UpdateJobStatus(finish_combobox.getSelectionModel().getSelectedItem(), "Complete");
			
			if(result > 0) {
				PCController.updatePC(JobController.getJobDetail(finish_combobox.getSelectionModel().getSelectedItem()).getPcID(), "Usable");
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText("Success");
				alert.setContentText("Job has been successfuly finished");
				alert.showAndWait();
				StageManager.getInstance().setScene(new ViewTechnicianJobView().getScene());
				return;
			}
		});
	}

}
