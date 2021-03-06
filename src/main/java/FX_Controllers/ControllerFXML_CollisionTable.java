package FX_Controllers;

// ctrl + sift + o
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.sun.prism.paint.Color;

import GameObjects.Ball;
import GameObjects.BallController;
import Parameters.GeneralString;
import Status.CollisionBits;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import static FX_Controllers.ControllerFXML_MessageTable.controllerFXML_MessageTable;


public class ControllerFXML_CollisionTable implements Initializable, GeneralString
{
	public static ControllerFXML_CollisionTable controllerFXML_CollisionTable;

	@FXML
	AnchorPane anchorPane;

	public GridPane mainGrid = new GridPane();

	private ObservableList<Ball> BALL_OBS_LIST_REFERENCE;
	private ArrayList<TableColumn<CollisionBits, Boolean>> listOfColumn = new ArrayList<>();
	private ObservableList<Object> statusBitList;

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		controllerFXML_CollisionTable = this;

		mainGrid.setGridLinesVisible(false);
		mainGrid.setHgap(10);
		mainGrid.setVgap(5);
		anchorPane.getChildren().add(mainGrid);
		BALL_OBS_LIST_REFERENCE = BallController.getBALL_OBS_LIST();
		generateColisionBitTable();
	}

	public void generateColisionBitTable()
	{
		String color;
		mainGrid.getChildren().clear();
		for (Ball ball : BALL_OBS_LIST_REFERENCE)
		{

			int size = ball.getCollisionBits().getStatusBitList().size();
			for (int i = 0; i < size; i++)
			{
				addColumn(String.format(nameBall, ball.getParameter().getBaseNumber(),0,0, i), ball.getParameter().getBaseNumber(), i, ball.getCollisionBits().getStatusBitList().get(i).isStatusLock());
			}
		}
	}

	public void addColumn(String name, int column, int row, boolean status)
	{
		Button button = new Button(name);
		if (status)
		{
			button.setDisable(true);
			button.setStyle(styleButton2);
			button.setOpacity(10);
		}
		button.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			public void handle(MouseEvent event)
			{
				System.out.println(name + " " + column + " " + row);
				controllerFXML_MessageTable.appendMessage(name + " " + column + " " + row);
				// Connect button to associated collision status
			}
		});

		mainGrid.add(button, column, row);
	}

}
