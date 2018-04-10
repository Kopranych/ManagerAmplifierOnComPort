package testrxtx;

import testrxtx.control.SerialReader;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortList;

public class Main {



	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] namePort = SerialPortList.getPortNames();
		for(String tmpPort:namePort){
			System.out.println(tmpPort);
		}
		SerialPort comPort = new SerialPort(namePort[0]);

		try{
			comPort.openPort();
			comPort.setParams(SerialPort.BAUDRATE_115200,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
			comPort.setEventsMask(SerialPort.MASK_RXCHAR);

			comPort.addEventListener(new SerialReader(comPort));

			while(true){
//				comPort.writeString("*1 20%");
//				comPort.writeString("*0 20%");
			}

		}catch(SerialPortException e){
			System.out.println("Erorr opened com port");
		}

	}
}



