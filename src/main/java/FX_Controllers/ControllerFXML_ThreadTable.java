package FX_Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

import GameObjects.Ball;
import Status.ThreadInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import static FX_Controllers.ControllerFXML_MessageTable.controllerFXML_MessageTable;


public class ControllerFXML_ThreadTable implements Initializable
{

	public static ControllerFXML_ThreadTable controllerFXML_ThreadTable;
	static ObservableList<ThreadInfo> THREAD_OBS_LIST = FXCollections.observableArrayList();

	@FXML
	TableView TableThreads;

	private TableColumn<ThreadInfo, String> nameColumn = new TableColumn<ThreadInfo, String>("Name");
	private TableColumn<ThreadInfo, String> stateColumn = new TableColumn<ThreadInfo, String>("State");
	private TableColumn<ThreadInfo, Integer> priorityColumn = new TableColumn<ThreadInfo, Integer>("Priority");
	private TableColumn<ThreadInfo, String> typeColumn = new TableColumn<ThreadInfo, String>("Type");

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		controllerFXML_ThreadTable=this;
		TableThreads.setStyle("-fx-font-size:12px;");
		nameColumn.setCellValueFactory(new PropertyValueFactory<ThreadInfo, String>("Name"));
		nameColumn.setPrefWidth(70);
		stateColumn.setCellValueFactory(new PropertyValueFactory<ThreadInfo, String>("State"));
		stateColumn.setPrefWidth(70);
		priorityColumn.setCellValueFactory(new PropertyValueFactory<ThreadInfo, Integer>("Priority"));
		priorityColumn.setPrefWidth(70);
		typeColumn.setCellValueFactory(new PropertyValueFactory<ThreadInfo, String>("Type"));
		typeColumn.setPrefWidth(70);
		TableThreads.getColumns().addAll(nameColumn, stateColumn, priorityColumn, typeColumn);
		TableThreads.setItems(THREAD_OBS_LIST);
	}


	public static void threadList()
	{
		Set<Thread> threads = Thread.getAllStackTraces().keySet();
		THREAD_OBS_LIST.clear();
		for (Thread t : threads)
		{
			THREAD_OBS_LIST.add(new ThreadInfo(t));
		}
	}
}
