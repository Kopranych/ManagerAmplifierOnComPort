package testrxtx.view;



import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
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

}
