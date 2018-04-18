package testrxtx.view;



import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import jssc.SerialPort;
import jssc.SerialPortException;
import testrxtx.MainRxTx;
import testrxtx.model.ConstAmp;

public class UserInterfaceController {

	private String statusAmplifier = ConstAmp.AMP_OFF;
	private String statusPreAmplifier = ConstAmp.PREAMP_OFF;

	@FXML
	private Label amplifierLabel;
	@FXML
	private Label preAmplifierLabel;
	@FXML
	private Label attenuatorOneLabel;
	@FXML
	private Label attenuatorTwoLabel;
	@FXML
	private Label voltageLabel;
	@FXML
	private Label temperatureLabel;
	@FXML
	private TextField attOneField;
	@FXML
	private TextField attTwoField;
	@FXML
	private Button buttomAmp;
	@FXML
	private Button buttomPreAmp;

	SerialPort port;

	public void setPort(SerialPort port){
		this.port = port;
	}


	public Label getAmplifierLabel() {
		return amplifierLabel;
	}

	public Label getAttenuatorOneLabel() {
		return attenuatorOneLabel;
	}

	public Label getAttenuatorTwoLabel() {
		return attenuatorTwoLabel;
	}

	public TextField getAttOneField() {
		return attOneField;
	}

	public TextField getAttTwoField() {
		return attTwoField;
	}


	// Ссылка на главное приложение.
    private MainRxTx mainApp;

	/**
     * Инициализация класса-контроллера. Этот метод вызывается автоматически
     * после того, как fxml-файл будет загружен.
     */
	@FXML
    private void initialize() {
		amplifierLabel.setText(ConstAmp.AMPLIFIER);
		preAmplifierLabel.setText(ConstAmp.PREAMPLIFIER);
		attenuatorOneLabel.setText(ConstAmp.ATTENUATOR_ONE);
		attenuatorTwoLabel.setText(ConstAmp.ATTENUATOR_TWO);
		voltageLabel.setText(ConstAmp.VOLTAGE);
		temperatureLabel.setText(ConstAmp.TEMPERATURE);
		//Ограничение на ввод символов кроме цифр и точки
		constrateEnterTextField(attOneField);
		constrateEnterTextField(attTwoField);

	}

	/**
     * Вызывается главным приложением, которое даёт на себя ссылку.
     *
     * @param mainApp
     */
    public void setMainApp(MainRxTx mainApp) {
        this.mainApp = mainApp;

//        // Добавление в таблицу данных из наблюдаемого списка
//        userTable.setItems(mainApp.getListUser());
    }

	/**
	 * метод вызывается при нажатии кнопки включения усилителя.
	 */
	@FXML
	private void handleAmpEn(){

		if(statusAmplifier.equals(ConstAmp.AMP_OFF)){
			statusAmplifier = ConstAmp.AMP_ON;
			try {
				port.writeString(ConstAmp.START_COM + ConstAmp.AMP_ON + ConstAmp.END_COM);
			} catch (SerialPortException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			amplifierLabel.setText(ConstAmp.AMPLIFIER + " " + ConstAmp.ON);
		}
		else{
			statusAmplifier = ConstAmp.AMP_OFF;
		try {
			port.writeString(ConstAmp.START_COM + ConstAmp.AMP_OFF + ConstAmp.END_COM);
		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			amplifierLabel.setText(ConstAmp.AMPLIFIER + " " + ConstAmp.OFF);
		}
	}

	@FXML
	private void handlePreAmpEn(){
		if(statusPreAmplifier.equals(ConstAmp.PREAMP_OFF)){
			statusPreAmplifier = ConstAmp.PREAMP_ON;
			try {
				port.writeString(ConstAmp.START_COM + ConstAmp.PREAMP_ON + ConstAmp.END_COM);
			} catch (SerialPortException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			preAmplifierLabel.setText(ConstAmp.PREAMPLIFIER + " " + ConstAmp.ON);
		}else{
			statusPreAmplifier = ConstAmp.PREAMP_OFF;
			try {
				port.writeString(ConstAmp.START_COM + ConstAmp.PREAMP_OFF + ConstAmp.END_COM);
			} catch (SerialPortException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			preAmplifierLabel.setText(ConstAmp.PREAMPLIFIER + " " + ConstAmp.OFF);
		}
	}

	/**
	 * Обработка значений для первого аттенюатора
	 * @param ae
	 */
	@FXML
	private void handleSetAttenuatorOne(ActionEvent ae){
		String tmp = attOneField.getText();
		double tmpDouble = Double.parseDouble(tmp);
		if(tmpDouble > ConstAmp.MAX_ATT||tmpDouble%ConstAmp.STEP_ATT != 0){//проверка введенных данных на соответсвие допустимых значений
			attOneField.setText("");
		}else{
			try {
				port.writeString(ConstAmp.START_COM + "3 " + attOneField.getText()
				+ ConstAmp.END_COM);
			} catch (SerialPortException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
			attenuatorOneLabel.setText(ConstAmp.ATTENUATOR_ONE +
					" " + attOneField.getText() + " " + ConstAmp.DECIBELL);
			attOneField.setText("");
		}
	}

	/**
	 * Обработка поля значений второго аттенюатора
	 * @param ae
	 */

	@FXML
	private void handleSetAttenuatorTwo(ActionEvent ae){
		String tmp = attTwoField.getText();
		double tmpDouble = Double.parseDouble(tmp);
		if(tmpDouble > ConstAmp.MAX_ATT||tmpDouble%ConstAmp.STEP_ATT != 0){//проверка введенных данных на соответсвие допустимых значений
			attTwoField.setText("");
		}else{
			try {
				port.writeString(ConstAmp.START_COM + "4 " + attTwoField.getText()
				+ ConstAmp.END_COM);
			} catch (SerialPortException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			attenuatorTwoLabel.setText(ConstAmp.ATTENUATOR_TWO +
					" " + attTwoField.getText() + " " + ConstAmp.DECIBELL);
			attTwoField.setText("");
		}
	}

	@FXML
	private void handleSetAllSettings(){
		String transivCommand = ConstAmp.START_COM + ConstAmp.ALL_SET
				+ "|" + statusAmplifier + "|" + statusPreAmplifier + "|" + "3 " + attOneField.getText() +
				"|" + "4 " + attTwoField.getText() + ConstAmp.END_COM;
		try {
			port.writeString(transivCommand);
		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@FXML
	private void handleReadDevice(){
		try {
			port.writeString(ConstAmp.START_COM + ConstAmp.READ_DEVICE + ConstAmp.END_COM);
		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//Ограничение на ввод символов кроме цифр и точки
	private void constrateEnterTextField(TextField attField){
		Pattern p = Pattern.compile("(\\d+\\.?\\d*)?");
		attField.textProperty().addListener((observable, oldValue, newValue)->{
			if(!p.matcher(newValue).matches())attField.setText(oldValue);});

	}
}
