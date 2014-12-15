package model;

import java.sql.SQLException;

public class Bus extends Vehicle {
	public Bus(String p) {
		super(p);
	}

	@Override
	public float getPricebyHour() throws SQLException {
		return vehicleTypeDAO().getPriceByHour("model.Bus");
	}

	@Override
	public float getPricebyDay() throws SQLException {
		return vehicleTypeDAO().getPriceByDay("model.Bus");
	}

	@Override
	public float getPricebyMonth() throws SQLException {
		return vehicleTypeDAO().getPriceByMonth("model.Bus");
	}
}
