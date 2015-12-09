package us.mcsw.core.util;

public class NumbersUtil {
	
	public static double roundDouble(double round, int places) {
		double pow = Math.pow(10, places);
		round *= pow;
		round = (int) Math.round(round);
		round /= pow;
		return round;
	}

}
