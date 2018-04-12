package testrxtx.view;



import java.util.regex.Pattern;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import jssc.SerialPort;
import jssc.SerialPortException;
import testrxtx.MainRxTx;
import testrxtx.model.ConstAmp;

public class UserInterfaceController {

	private boolean isAmplifier = false;
	private boolean isPreAmplifier = false;

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

	SerialPort port;

	public void setPort(SerialPort port){
		this.port = port;
	}


	// ������ �� ������� ����������.
    private MainRxTx mainApp;

	/**
     * ������������� ������-�����������. ���� ����� ���������� �������������
     * ����� ����, ��� fxml-���� ����� ��������.
     */
	@FXML
    private void initialize() {
		amplifierLabel.setText(ConstAmp.AMPLIFIER);
		preAmplifierLabel.setText(ConstAmp.PREAMPLIFIER);
		attenuatorOneLabel.setText(ConstAmp.ATTENUATOR_ONE);
		attenuatorTwoLabel.setText(ConstAmp.ATTENUATOR_TWO);
		voltageLabel.setText(ConstAmp.VOLTAGE);
		temperatureLabel.setText(ConstAmp.TEMPERATURE);
		Pattern p = Pattern.compile("(\\d+\\.?\\d*)?");
		attOneField.textProperty().addListener((observable, oldValue, newValue)->{
			if(!p.matcher(newValue).matches())attOneField.setText(oldValue);});
	}

	/**
     * ���������� ������� �����������, ������� ��� �� ���� ������.
     *
     * @param mainApp
     */
    public void setMainApp(MainRxTx mainApp) {
        this.mainApp = mainApp;

//        // ���������� � ������� ������ �� ������������ ������
//        userTable.setItems(mainApp.getListUser());
    }

	/**
	 * ����� ���������� ��� ������� ������ ��������� ���������.
	 */
	@FXML
	private void handleAmpEn(){

		if(!isAmplifier){
			isAmplifier = true;
			try {
				port.writeString("*1 20%");
			} catch (SerialPortException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			amplifierLabel.setText(ConstAmp.AMPLIFIER + " " + ConstAmp.ON);
		}
		else{
			isAmplifier = false;
		try {
			port.writeString("*0 20%");
		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			amplifierLabel.setText(ConstAmp.AMPLIFIER + " " + ConstAmp.OFF);
		}
	}

	@FXML
	private void handlePreAmpEn(){
		if(!isPreAmplifier){
			isPreAmplifier = true;
			try {
				port.writeString("*PreAmpEn%");
			} catch (SerialPortException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			preAmplifierLabel.setText(ConstAmp.PREAMPLIFIER + " " + ConstAmp.ON);
		}else{
			isPreAmplifier = false;
			try {
				port.writeString("*PreAmpDis%");
			} catch (SerialPortException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			preAmplifierLabel.setText(ConstAmp.PREAMPLIFIER + " " + ConstAmp.OFF);
		}
	}

	/**
	 * ��������� �������� ��� ������� �����������
	 * @param ae
	 */
	@FXML
	private void handleSetAttenuatorOne(ActionEvent ae){
		String tmp = attOneField.getText();
		double tmpDouble = Double.parseDouble(tmp);
		if((tmp.length() > ConstAmp.MAX_SIZE_STRING||(tmpDouble > ConstAmp.MAX_ATT))||
				tmpDouble%ConstAmp.STEP_ATT != 0){//�������� ��������� ������ �� ����������� ���������� ��������
			attOneField.setText("");
		}else{
			try {
				port.writeString("*PreAmpDis%");
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
	 * ��������� ���� �������� ������� �����������
	 * @param ae
	 */

	@FXML
	private void handleSetAttenuatorTwo(ActionEvent ae){
		String tmp = attOneField.getText();
		double tmpDouble = Double.parseDouble(tmp);
		if((tmp.length() > ConstAmp.MAX_SIZE_STRING||(tmpDouble > ConstAmp.MAX_ATT))||
				tmpDouble%ConstAmp.STEP_ATT != 0){//�������� ��������� ������ �� ����������� ���������� ��������
			attTwoField.setText("");
		}else{
			try {
				port.writeString("*PreAmpDis%");
			} catch (SerialPortException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			attenuatorTwoLabel.setText(ConstAmp.ATTENUATOR_TWO +
					" " + attTwoField.getText() + " " + ConstAmp.DECIBELL);
			attOneField.setText("");
		}
	}

	@FXML
	private void handleSetAllSettings(){

	}

}
