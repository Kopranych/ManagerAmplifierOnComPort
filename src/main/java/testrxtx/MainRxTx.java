/*
 * Основной класс JavaFX приложения, основная разметка эдементов интерфейса
 * выполнена через файлы fxml: RootLayout, UserInterface. Так же в приложении
 * используется библиотека jssc для работы с com портом класс SerialReader.java
 */
package testrxtx;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;
import testrxtx.control.SerialReader;
import testrxtx.view.UserInterfaceController;

public class MainRxTx extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;
	private UserInterfaceController controller;
	private SerialPort comPort = new SerialPort("COM1");//в дальнейшем планировал исполльзовать метод SerialPortList.getPortNames() для возможности выбора необходимого порта


	public static void main(String[] args) {
		launch(args);//вызывает метод start()

	}

	//основной метод выполнения
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Управление усилителем");

		initRootLayout();
		showUserInterface();
		initComPort();


	}

	//метод вызывается при закрытии основного окна приложения
	//и закрывает соединение с comport
	@Override
	public void stop(){
		try {
			comPort.closePort();
		} catch (SerialPortException e) {

			e.printStackTrace();
		}
	}


	//инициализация корневого макета отображения
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

	//добавление основных элементов интерфейса в корневой макет
	public void showUserInterface(){
		try{
			//Загружаем интерфейс из fxml файла
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainRxTx.class.getResource("view/UserInterface.fxml"));
			AnchorPane userInterface = (AnchorPane)loader.load();
			// Помещаем интерфейс в центр корневого макета.
			rootLayout.setCenter(userInterface);
			// Даём контроллеру доступ к главному приложению.
	        controller = loader.getController();
	        controller.setMainApp(this);
	        controller.setPort(comPort);

		}catch(IOException e){
			e.printStackTrace();
		}

	}

	//инициализация com порта
	public void initComPort(){
		try {

			comPort.openPort();
			comPort.setParams(SerialPort.BAUDRATE_9600,
                SerialPort.DATABITS_8,
                SerialPort.STOPBITS_1,
                SerialPort.PARITY_NONE);
			comPort.setEventsMask(SerialPort.MASK_RXCHAR);
			comPort.addEventListener(new SerialReader(comPort, controller));

		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}

