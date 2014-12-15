package model;

import java.sql.SQLException;

public class ChargeByDays implements ChargeMethod {

	@Override
	public float calc(long duration, Vehicle v) {
		try {
			return v.getPricebyDay() * (duration / 86400); //86400s = 24h
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return 0;
	}
}
