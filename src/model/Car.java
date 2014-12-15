package model;

import java.sql.SQLException;

public class Car extends Vehicle {
	public Car(String p) {
		super(p);
	}

	@Override
	public float getPricebyHour() throws SQLException {
		return vehicleTypeDAO().getPriceByHour("model.Car");
	}

	@Override
	public float getPricebyDay() throws SQLException {
		return vehicleTypeDAO().getPriceByDay("model.Car");
	}

	@Override
	public float getPricebyMonth() throws SQLException {
		return vehicleTypeDAO().getPriceByMonth("model.Car");
	}
}
