package testrxtx.control;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import testrxtx.view.UserInterfaceController;

public class SerialReader implements SerialPortEventListener{

	private SerialPort port;
	private UserInterfaceController uiController;

	public SerialReader(SerialPort port){
		this.port = port;
	}

	public SerialReader(SerialPort port, UserInterfaceController uiController) {
		this.port = port;
		this.uiController = uiController;

	}

	@Override
	public void serialEvent(SerialPortEvent serialPortEvent) {
		if(serialPortEvent.isRXCHAR()){
			try {
				System.out.print(port.readString(serialPortEvent.getEventValue()));
				String recieverString = port.readString(serialPortEvent.getEventValue());
				char comReciever = recieverString.charAt(0);
				switch(comReciever){
					case '0':
						String[] bufRead = recieverString.split("|");
						int size = bufRead.length;
						uiController.getAmplifierLabel().setText(bufRead[0]);

						break;
					case '1':
						break;
					case '2':
						break;
					case '3':
						break;
					case '4':
						break;

				}

			} catch (SerialPortException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public SerialPort getPort() {
		return port;
	}


	public void setPort(SerialPort port) {
		this.port = port;
	}

}
