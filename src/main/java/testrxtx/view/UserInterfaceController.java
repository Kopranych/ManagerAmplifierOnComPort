package testrxtx.view;
/*
 * Класс контроллер для обработки событий интерфейса
 */


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

	private String statusAmplifier = ConstAmp.getAmpOff();
	private String statusPreAmplifier = ConstAmp.getPreampOff();

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

	public SerialPort port;
	public ConstAmp constAmp;

	public void setPort(SerialPort port){
		this.port = port;
	}

	public Label getAmplifierLabel() {
		return amplifierLabel;
	}

	public Label getPreAmplifierLabel() {
		return preAmplifierLabel;
	}


	public Label getAttenuatorOneLabel() {
		return attenuatorOneLabel;
	}

	public Label getAttenuatorTwoLabel() {
		return attenuatorTwoLabel;
	}

	public Label getVoltageLabel() {
		return voltageLabel;
	}

	public Label getTemperatureLabel() {
		return temperatureLabel;
	}




	// Ссылка на главное приложение.
    private MainRxTx mainApp;

	/**
     * Инициализация класса-контроллера. Этот метод вызывается автоматически
     * после того, как fxml-файл будет загружен.
     */
	@FXML
    private void initialize() {
		amplifierLabel.setText(ConstAmp.getAmplifier());
		preAmplifierLabel.setText(ConstAmp.getPreamplifier());
		attenuatorOneLabel.setText(ConstAmp.getAttenuatorOne());
		attenuatorTwoLabel.setText(ConstAmp.getAttenuatorTwo());
		voltageLabel.setText(ConstAmp.getVoltage());
		temperatureLabel.setText(ConstAmp.getTemperature());
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
	 * метод вызывается при нажатии кнопки включения усилителя
	 * и отправляет команду через ком порт на микроконтроллер
	 * для управления усилителем
	 */
	@FXML
	private void handleAmpEn(){

		if(statusAmplifier.equals(ConstAmp.getAmpOff())){
			statusAmplifier = ConstAmp.getAmpOn();
			try {
				port.writeString(ConstAmp.getStartCom() + ConstAmp.getAmpOn() + ConstAmp.getEndCom());
			} catch (SerialPortException e) {
				e.printStackTrace();
			}

			amplifierLabel.setText(ConstAmp.getAmplifier() + " " + ConstAmp.getOn());
		}
		else{
			statusAmplifier = ConstAmp.getAmpOff();
			try {
				port.writeString(ConstAmp.getStartCom() + ConstAmp.getAmpOff() + ConstAmp.getEndCom());
			} catch (SerialPortException e) {

				e.printStackTrace();
			}
			amplifierLabel.setText(ConstAmp.getAmplifier() + " " + ConstAmp.getOff());
		}
	}

	/**
	 * метод вызывается при нажатии кнопки включения предусилителя
	 * и отправляет команду через ком порт на микроконтроллер
	 * для управления предусилителем
	 */
	@FXML
	private void handlePreAmpEn(){
		if(statusPreAmplifier.equals(ConstAmp.getPreampOff())){
			statusPreAmplifier = ConstAmp.getPreampOn();
			try {
				port.writeString(ConstAmp.getStartCom() + ConstAmp.getPreampOn() + ConstAmp.getEndCom());
			} catch (SerialPortException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			preAmplifierLabel.setText(ConstAmp.getPreamplifier() + " " + ConstAmp.getOn());
		}else{
			statusPreAmplifier = ConstAmp.getPreampOff();
			try {
				port.writeString(ConstAmp.getStartCom() + ConstAmp.getPreampOff() + ConstAmp.getEndCom());
			} catch (SerialPortException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			preAmplifierLabel.setText(ConstAmp.getPreamplifier() + " " + ConstAmp.getOff());
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
		if(tmpDouble > ConstAmp.getMaxAtt()||tmpDouble%ConstAmp.getStepAtt() != 0){//проверка введенных данных на соответсвие допустимых значений
			attOneField.setText("");
		}else{
			try {
				port.writeString(ConstAmp.getStartCom() + "3|" + attOneField.getText()
				+ ConstAmp.getEndCom());
			} catch (SerialPortException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
			attenuatorOneLabel.setText(ConstAmp.getAttenuatorOne() +
					" " + attOneField.getText() + " " + ConstAmp.getDecibell());
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
		if(tmpDouble > ConstAmp.getMaxAtt()||tmpDouble%ConstAmp.getStepAtt() != 0){//проверка введенных данных на соответсвие допустимых значений
			attTwoField.setText("");
		}else{
			try {
				port.writeString(ConstAmp.getStartCom() + "4|" + attTwoField.getText()
				+ ConstAmp.getEndCom());
			} catch (SerialPortException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			attenuatorTwoLabel.setText(ConstAmp.getAttenuatorTwo() +
					" " + attTwoField.getText() + " " + ConstAmp.getDecibell());
			attTwoField.setText("");
		}
	}

	/**
	 * метод вызывается при нажатии кнопки "установить"
	 * и отправляет команду через ком порт на микроконтроллер
	 * для установки настроек для всей доступной переферии устройства
	 */
	@FXML
	private void handleSetAllSettings(){
		String transivCommand = ConstAmp.getStartCom() + ConstAmp.getAllSet()
				+ "|" + statusAmplifier + "|" + statusPreAmplifier + "|" + "3 " + attOneField.getText() +
				"|" + "4 " + attTwoField.getText() + ConstAmp.getEndCom();
		try {
			port.writeString(transivCommand);
		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * метод вызывается при нажатии кнопки "обновить"
	 * и отправляет команду микроконтроллеру для чтения актуальных установок
	 * переферии и данных датчика температуры и уровня напряжения на усилителе
	 */
	@FXML
	private void handleReadDevice(){
		try {
			port.writeString(ConstAmp.getStartCom() + ConstAmp.getReadDevice() + ConstAmp.getEndCom());
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
