package control;

import model.ChargeByDays;
import model.ChargeByHours;
import model.ChargeByMonth;
import model.ChargeMethod;

public class TimeManager {

	public static ChargeMethod setBillingMethod(long duration) {
		if (duration < 15000) { // se menor que 15s
			return new ChargeByHours();
		} else if (duration < 30000) {// se menor 30s
			return new ChargeByDays();
		} else {
			return new ChargeByMonth();
		}
	}
}
