/*
 * �������� ����� JavaFX ����������, �������� �������� ��������� ����������
 * ��������� ����� ����� fxml: RootLayout, UserInterface. ��� �� � ����������
 * ������������ ���������� jssc ��� ������ � com ������ ����� SerialReader.java
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
	private SerialPort comPort = new SerialPort("COM1");//� ���������� ���������� ������������� ����� SerialPortList.getPortNames() ��� ����������� ������ ������������ �����


	public static void main(String[] args) {
		launch(args);//�������� ����� start()

	}

	//�������� ����� ����������
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("���������� ����������");

		initRootLayout();
		showUserInterface();
		initComPort();


	}

	//����� ���������� ��� �������� ��������� ���� ����������
	//� ��������� ���������� � comport
	@Override
	public void stop(){
		try {
			comPort.closePort();
		} catch (SerialPortException e) {

			e.printStackTrace();
		}
	}


	//������������� ��������� ������ �����������
	public void initRootLayout(){
		try{
			// ��������� �������� ����� �� fxml �����.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainRxTx.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane)loader.load();

			// ���������� �����, ���������� �������� �����.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setResizable(false);
		}catch(IOException e){
			e.printStackTrace();
		}

	}

	//���������� �������� ��������� ���������� � �������� �����
	public void showUserInterface(){
		try{
			//��������� ��������� �� fxml �����
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainRxTx.class.getResource("view/UserInterface.fxml"));
			AnchorPane userInterface = (AnchorPane)loader.load();
			// �������� ��������� � ����� ��������� ������.
			rootLayout.setCenter(userInterface);
			// ��� ����������� ������ � �������� ����������.
	        controller = loader.getController();
	        controller.setMainApp(this);
	        controller.setPort(comPort);

		}catch(IOException e){
			e.printStackTrace();
		}

	}

	//������������� com �����
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

