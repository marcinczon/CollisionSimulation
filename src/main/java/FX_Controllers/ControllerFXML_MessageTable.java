package FX_Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;


public class ControllerFXML_MessageTable implements Initializable
{
	public static ControllerFXML_MessageTable controllerFXML_MessageTable;

	// FXML ITEMS

	@FXML
	TextArea messageArea;

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		controllerFXML_MessageTable = this;
	}

	public void appendMessage(String message)
	{
		messageArea.appendText(message + "\n");
	}

}
