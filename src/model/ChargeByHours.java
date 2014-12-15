package model;

import java.sql.SQLException;

public class ChargeByHours implements ChargeMethod {

	@Override
	public float calc(long duration, Vehicle v) {
		try {
			return v.getPricebyHour() * (duration / 3600); // 3600s = 1h
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return 0;
	}

}
