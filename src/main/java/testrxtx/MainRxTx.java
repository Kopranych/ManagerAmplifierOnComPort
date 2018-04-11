package testrxtx;

import java.io.IOException;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import jssc.SerialPort;
import jssc.SerialPortException;
import testrxtx.control.SerialReader;
import testrxtx.view.UserInterfaceController;

public class MainRxTx extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;
	SerialPort comPort = new SerialPort("COM1");

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Управление усилителем");

		initRootLayout();
		showUserInterface();
		initComPort();

	}

	@Override
	public void stop(){
		try {
			comPort.closePort();
		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void initRootLayout(){
		try{
			// Загружаем корневой макет из fxml файла.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainRxTx.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane)loader.load();

			// Отображаем сцену, содержащую корневой макет.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setResizable(false);
		}catch(IOException e){
			e.printStackTrace();
		}

	}

	public void showUserInterface(){
		try{
			//Загружаем интерфейс из fxml файла
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainRxTx.class.getResource("view/UserInterface.fxml"));
			AnchorPane userInterface = (AnchorPane)loader.load();
			// Помещаем интерфейс в центр корневого макета.
			rootLayout.setCenter(userInterface);
			// Даём контроллеру доступ к главному приложению.
	        UserInterfaceController controller = loader.getController();
	        controller.setMainApp(this);
	        controller.setPort(comPort);

		}catch(IOException e){
			e.printStackTrace();
		}

	}

	public void initComPort(){


		try {

			comPort.openPort();
			comPort.setParams(SerialPort.BAUDRATE_115200,
                SerialPort.DATABITS_8,
                SerialPort.STOPBITS_1,
                SerialPort.PARITY_NONE);
			comPort.setEventsMask(SerialPort.MASK_RXCHAR);
			comPort.addEventListener(new SerialReader(comPort));

		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

