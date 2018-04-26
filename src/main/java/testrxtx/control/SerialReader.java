package testrxtx.control;

import javafx.application.Platform;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import testrxtx.model.ConstAmp;
import testrxtx.view.UserInterfaceController;

public class SerialReader implements SerialPortEventListener{

	private SerialPort port;
	private UserInterfaceController uiController;
	private boolean isRecieve = false;
	private String recieverString;
	private char comReciever;

	public SerialReader(SerialPort port){
		this.port = port;
	}

	public SerialReader(SerialPort port, UserInterfaceController uiController) {
		this.port = port;
		this.uiController = uiController;

	}

	/**
	 * принимает событие от порта и обрабатывает их
	 */
	@Override
	public void serialEvent(SerialPortEvent serialPortEvent) {
		if(serialPortEvent.isRXCHAR()){
			try {
				recieverString = port.readString(serialPortEvent.getEventValue());
				System.out.println(recieverString);
			} catch (SerialPortException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
				Platform.runLater(() -> {
					comReciever = recieverString.charAt(0);
					System.out.println(comReciever);
					String[] bufRead = recieverString.split("|");
					switch(comReciever){
					case '0':
						int size = bufRead.length;

						if(bufRead[2].equals("1")){
							uiController.getPreAmplifierLabel().setText(ConstAmp.PREAMPLIFIER + " " + ConstAmp.ON);
						}else{
							uiController.getPreAmplifierLabel().setText(ConstAmp.PREAMPLIFIER + " " + ConstAmp.OFF);
						}

						uiController.getAttenuatorOneLabel().setText(ConstAmp.ATTENUATOR_ONE +
								" " + bufRead[3] + " " + ConstAmp.DECIBELL);
						uiController.getAttenuatorTwoLabel().setText(ConstAmp.ATTENUATOR_TWO +
								" " + bufRead[4] + " " + ConstAmp.DECIBELL);
						uiController.getVoltageLabel().setText(ConstAmp.VOLTAGE + " " + bufRead[5]);
						uiController.getTemperatureLabel().setText(ConstAmp.TEMPERATURE + " " + bufRead[6]);
						break;
					case '1':
						if(bufRead[2].equals("1")){
							uiController.getAmplifierLabel().setText(ConstAmp.AMPLIFIER + " " + ConstAmp.ON);
						}else{
							uiController.getAmplifierLabel().setText(ConstAmp.AMPLIFIER + " " + ConstAmp.OFF);
						}
						break;
					case '2':
						if(bufRead[1].equals("1")){
							uiController.getPreAmplifierLabel().setText(ConstAmp.PREAMPLIFIER + " " + ConstAmp.ON);
						}else{
							uiController.getPreAmplifierLabel().setText(ConstAmp.PREAMPLIFIER + " " + ConstAmp.OFF);
						}
						break;
					case '3':
						uiController.getAttenuatorOneLabel().setText(ConstAmp.ATTENUATOR_ONE +
								" " + bufRead[1] + " " + ConstAmp.DECIBELL);
						break;
					case '4':
						uiController.getAttenuatorTwoLabel().setText(ConstAmp.ATTENUATOR_TWO +
								" " + bufRead[1] + " " + ConstAmp.DECIBELL);
						break;
					case '5':
						uiController.getVoltageLabel().setText(ConstAmp.VOLTAGE + " " + bufRead[1]);
						break;
					case '6':
						uiController.getTemperatureLabel().setText(ConstAmp.TEMPERATURE + " " + bufRead[1]);
					default:
						System.out.println("Uncknown command");
					}
				});


	}

	public SerialPort getPort() {
		return port;
	}


	public void setPort(SerialPort port) {
		this.port = port;
	}



}
