package testrxtx.control;
/*
 * Класс для работы с com портом
 */
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
							uiController.getPreAmplifierLabel().setText(ConstAmp.getPreamplifier() + " " + ConstAmp.getOn());
						}else{
							uiController.getPreAmplifierLabel().setText(ConstAmp.getPreamplifier() + " " + ConstAmp.getOff());
						}

						uiController.getAttenuatorOneLabel().setText(ConstAmp.getAttenuatorOne() +
								" " + bufRead[3] + " " + ConstAmp.getDecibell());
						uiController.getAttenuatorTwoLabel().setText(ConstAmp.getAttenuatorTwo() +
								" " + bufRead[4] + " " + ConstAmp.getDecibell());
						uiController.getVoltageLabel().setText(ConstAmp.getVoltage() + " " + bufRead[5]);
						uiController.getTemperatureLabel().setText(ConstAmp.getTemperature() + " " + bufRead[6]);
						break;
					case '1':
						if(bufRead[2].equals("1")){
							uiController.getAmplifierLabel().setText(ConstAmp.getAmplifier() + " " + ConstAmp.getOn());
						}else{
							uiController.getAmplifierLabel().setText(ConstAmp.getAmplifier() + " " + ConstAmp.getOff());
						}
						break;
					case '2':
						if(bufRead[1].equals("1")){
							uiController.getPreAmplifierLabel().setText(ConstAmp.getPreamplifier() + " " + ConstAmp.getOn());
						}else{
							uiController.getPreAmplifierLabel().setText(ConstAmp.getPreamplifier() + " " + ConstAmp.getOff());
						}
						break;
					case '3':
						uiController.getAttenuatorOneLabel().setText(ConstAmp.getAttenuatorOne() +
								" " + bufRead[1] + " " + ConstAmp.getDecibell());
						break;
					case '4':
						uiController.getAttenuatorTwoLabel().setText(ConstAmp.getAttenuatorTwo() +
								" " + bufRead[1] + " " + ConstAmp.getDecibell());
						break;
					case '5':
						uiController.getVoltageLabel().setText(ConstAmp.getVoltage() + " " + bufRead[1]);
						break;
					case '6':
						uiController.getTemperatureLabel().setText(ConstAmp.getTemperature() + " " + bufRead[1]);
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
