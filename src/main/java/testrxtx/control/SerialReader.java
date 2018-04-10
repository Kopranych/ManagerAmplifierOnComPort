package testrxtx.control;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class SerialReader implements SerialPortEventListener{

	private SerialPort port;

	public SerialReader(SerialPort port){
		this.port = port;
	}

	@Override
	public void serialEvent(SerialPortEvent serialPortEvent) {
		if(serialPortEvent.isRXCHAR()){
			try {
				System.out.print(port.readString(serialPortEvent.getEventValue()));
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
