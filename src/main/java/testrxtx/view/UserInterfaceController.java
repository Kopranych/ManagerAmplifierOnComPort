package testrxtx.view;
/*
 * ����� ���������� ��� ��������� ������� ����������
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




	// ������ �� ������� ����������.
    private MainRxTx mainApp;

	/**
     * ������������� ������-�����������. ���� ����� ���������� �������������
     * ����� ����, ��� fxml-���� ����� ��������.
     */
	@FXML
    private void initialize() {
		amplifierLabel.setText(ConstAmp.getAmplifier());
		preAmplifierLabel.setText(ConstAmp.getPreamplifier());
		attenuatorOneLabel.setText(ConstAmp.getAttenuatorOne());
		attenuatorTwoLabel.setText(ConstAmp.getAttenuatorTwo());
		voltageLabel.setText(ConstAmp.getVoltage());
		temperatureLabel.setText(ConstAmp.getTemperature());
		//����������� �� ���� �������� ����� ���� � �����
		constrateEnterTextField(attOneField);
		constrateEnterTextField(attTwoField);

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
	 * ����� ���������� ��� ������� ������ ��������� ���������
	 * � ���������� ������� ����� ��� ���� �� ���������������
	 * ��� ���������� ����������
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
	 * ����� ���������� ��� ������� ������ ��������� �������������
	 * � ���������� ������� ����� ��� ���� �� ���������������
	 * ��� ���������� ��������������
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
	 * ��������� �������� ��� ������� �����������
	 * @param ae
	 */
	@FXML
	private void handleSetAttenuatorOne(ActionEvent ae){
		String tmp = attOneField.getText();
		double tmpDouble = Double.parseDouble(tmp);
		if(tmpDouble > ConstAmp.getMaxAtt()||tmpDouble%ConstAmp.getStepAtt() != 0){//�������� ��������� ������ �� ����������� ���������� ��������
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
	 * ��������� ���� �������� ������� �����������
	 * @param ae
	 */

	@FXML
	private void handleSetAttenuatorTwo(ActionEvent ae){
		String tmp = attTwoField.getText();
		double tmpDouble = Double.parseDouble(tmp);
		if(tmpDouble > ConstAmp.getMaxAtt()||tmpDouble%ConstAmp.getStepAtt() != 0){//�������� ��������� ������ �� ����������� ���������� ��������
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
	 * ����� ���������� ��� ������� ������ "����������"
	 * � ���������� ������� ����� ��� ���� �� ���������������
	 * ��� ��������� �������� ��� ���� ��������� ��������� ����������
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
	 * ����� ���������� ��� ������� ������ "��������"
	 * � ���������� ������� ���������������� ��� ������ ���������� ���������
	 * ��������� � ������ ������� ����������� � ������ ���������� �� ���������
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

	//����������� �� ���� �������� ����� ���� � �����
	private void constrateEnterTextField(TextField attField){
		Pattern p = Pattern.compile("(\\d+\\.?\\d*)?");
		attField.textProperty().addListener((observable, oldValue, newValue)->{
			if(!p.matcher(newValue).matches())attField.setText(oldValue);});

	}
}
