package FX_Controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import GameObjects.Ball;
import GameObjects.BallController;
import Status.CollisionBits;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ControllerFXML_CollisionTable implements Initializable
{
	public static ControllerFXML_CollisionTable controllerFXML_CollisionTable;
	private ObservableList<Ball> BALL_OBS_LIST_REFERENCE;
	private ArrayList<TableColumn<CollisionBits, Boolean>> listOfColumn = new ArrayList<>();

	@FXML
	TableView TableCollisionView;

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		controllerFXML_CollisionTable = this;
		BALL_OBS_LIST_REFERENCE = BallController.getBALL_OBS_LIST();
		TableCollisionView.setStyle("-fx-font-size:12px;");
	}

	public void addNewColumn(int number)
	{
		TableColumn<CollisionBits, Boolean> newColumn = new TableColumn<CollisionBits, Boolean>(String.format("%s", number));
		newColumn.setCellValueFactory(new PropertyValueFactory<CollisionBits, Boolean>("getStatusTable"));
		newColumn.setPrefWidth(70);
		listOfColumn.add(newColumn);
		refreshTable();

	}

	public void refreshTable()
	{
		TableCollisionView.getColumns().clear();
		for (TableColumn column : listOfColumn)
		{
			TableCollisionView.getColumns().add(column);
		}

	}

}
