package testrxtx;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainRxTx extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("���������� ����������");

		initRootLayout();
		showUserInterface();

	}

	public static void main(String[] args) {
		launch(args);
	}

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

	public void showUserInterface(){
		try{
			//��������� ��������� �� fxml �����
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainRxTx.class.getResource("view/UserInterface.fxml"));
			AnchorPane userInterface = (AnchorPane)loader.load();
			// �������� ��������� � ����� ��������� ������.
			rootLayout.setCenter(userInterface);


		}catch(IOException e){
			e.printStackTrace();
		}

	}

}

