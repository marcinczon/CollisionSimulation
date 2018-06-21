
import FX_Controllers.ControllerFXML_Base;
import FX_Controllers.ControllerFXML_CollisionTable2;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Start extends Application
{

	// Future work
	// - Show all thread in list and status
	// - Change calculating of ball, cant move thrue each other
	// - Make online connection to play
	// - Add paddle to play
	
	//Reference to class
//	static ControllerFXML_Base controllerFXML_Base = new ControllerFXML_Base();

	static Stage stage2 = new Stage();
	static Stage stage3 = new Stage();
	static Parent parent3;

	public static void ShowStage2()
	{
		stage2.show();
	}

	public static void ShowStage3()
	{
		stage3.show();
	}

	public static void main(String[] args)
	{
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		try
		{
			Parent parent1 = FXMLLoader.load(getClass().getResource("FX_FXML/FXML_Base.fxml"));
			Scene scene1 = new Scene(parent1);

			Parent parent2 = FXMLLoader.load(getClass().getResource("FX_FXML/FXML_ThreadTable.fxml"));
			Scene scene2 = new Scene(parent2);
			
			parent3 = FXMLLoader.load(getClass().getResource("FX_FXML/FXML_CollisionTable2.fxml"));
			Scene scene3 = new Scene(parent3);

			primaryStage.setScene(scene1);
			primaryStage.setResizable(false);
			primaryStage.show();
			
			stage2.setScene(scene2);
			stage2.setResizable(true);
			stage2.show();

			stage3.setScene(scene3);
			stage3.setResizable(true);
			stage3.show();



		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void showStage2()
	{
		stage2.show();
	}

}
