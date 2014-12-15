package model;

import java.sql.SQLException;

public class Truck extends Vehicle {
	public Truck(String p) {
		super(p);
	}

	@Override
	public float getPricebyHour() throws SQLException {
		return vehicleTypeDAO().getPriceByHour("model.Truck");
	}

	@Override
	public float getPricebyDay() throws SQLException {
		return vehicleTypeDAO().getPriceByDay("model.Truck");
	}

	@Override
	public float getPricebyMonth() throws SQLException {
		return vehicleTypeDAO().getPriceByMonth("model.Truck");
	}
}
