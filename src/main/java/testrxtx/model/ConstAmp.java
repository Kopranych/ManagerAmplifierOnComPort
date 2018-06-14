/*
 * Класс хранит основные константы приложения
 */
package testrxtx.model;

public class ConstAmp {

	private static final String AMPLIFIER = "УМ20";
	private static final String ON = "Вкл";
	private static final String OFF = "Выкл";
	private static final String PREAMPLIFIER = "Предусилитель";
	private static final String ATTENUATOR_ONE = "Аттенюатор 1:";
	private static final String ATTENUATOR_TWO = "Аттенюатор 2:";
	private static final String DECIBELL = "dB";
	private static final String VOLTAGE = "Напряжение:";
	private static final String VOLT = "В";
	private static final String TEMPERATURE = "Температура:";
	private static final String CELCIUM = "С";

	private static final double STEP_ATT = 0.5;
	private static final double MAX_ATT = 31.5;
	private static final double MAX_SIZE_STRING = 4;
	//команда управления устройством
	private static final String START_COM = "*";
	private static final String END_COM = "%";
	private static final String AMP_ON = "1|1";
	private static final String AMP_OFF = "1|0";
	private static final String PREAMP_ON = "2|1";
	private static final String PREAMP_OFF = "2|0";
	private static final String ALL_SET = "0";
	private static final String READ_DEVICE = "5";

	private static ConstAmp constAmp;

	private ConstAmp(){}

	public static synchronized ConstAmp instance(){
		if(constAmp == null){
			constAmp = new ConstAmp();
		}
		return constAmp;
	}



	public static void setConstAmp(ConstAmp constAmp) {
		ConstAmp.constAmp = constAmp;
	}

	public static String getAmplifier() {
		return AMPLIFIER;
	}

	public static String getOn() {
		return ON;
	}

	public static String getOff() {
		return OFF;
	}

	public static String getPreamplifier() {
		return PREAMPLIFIER;
	}

	public static String getAttenuatorOne() {
		return ATTENUATOR_ONE;
	}

	public static String getAttenuatorTwo() {
		return ATTENUATOR_TWO;
	}

	public static String getDecibell() {
		return DECIBELL;
	}

	public static String getVoltage() {
		return VOLTAGE;
	}

	public static String getVolt() {
		return VOLT;
	}

	public static String getTemperature() {
		return TEMPERATURE;
	}

	public static String getCelcium() {
		return CELCIUM;
	}

	public static double getStepAtt() {
		return STEP_ATT;
	}

	public static double getMaxAtt() {
		return MAX_ATT;
	}

	public static double getMaxSizeString() {
		return MAX_SIZE_STRING;
	}

	public static String getAmpOn() {
		return AMP_ON;
	}

	public static String getAmpOff() {
		return AMP_OFF;
	}

	public static String getPreampOn() {
		return PREAMP_ON;
	}

	public static String getPreampOff() {
		return PREAMP_OFF;
	}

	public static String getStartCom() {
		return START_COM;
	}

	public static String getEndCom() {
		return END_COM;
	}

	public static String getAllSet() {
		return ALL_SET;
	}

	public static String getReadDevice() {
		return READ_DEVICE;
	}


}
