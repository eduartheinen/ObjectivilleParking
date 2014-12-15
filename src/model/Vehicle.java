package model;

import java.sql.SQLException;

import dao.factory.DaoFactory;
import dao.interfaces.VehicleTypeDao;

public abstract class Vehicle {
	private String plate;
	private float pricebyHour;
	private float pricebyDay;
	private float pricebyMonth;

	public Vehicle(String p) {
		plate = p;
	}

	public String getPlate() {
		return plate;
	}

	public abstract float getPricebyHour() throws SQLException;

	public abstract float getPricebyDay() throws SQLException;

	public abstract float getPricebyMonth() throws SQLException;

	public void setPlate(String plate) {
		this.plate = plate;
	}

	protected static VehicleTypeDao vehicleTypeDAO() {
		DaoFactory dao = DaoFactory.getDatabase();
		return dao.getVehicleTypeDao();
	}
}
