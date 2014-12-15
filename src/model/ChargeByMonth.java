package model;

import java.sql.SQLException;

public class ChargeByMonth implements ChargeMethod {

	@Override
	public float calc(long duration, Vehicle v) {
		try {
			return v.getPricebyMonth() * (duration / 2592000);//2592000s = 1mês
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

}
