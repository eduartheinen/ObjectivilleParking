package dao.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import dao.concrete.PostgreslqVehicleTypeDao;
import dao.concrete.PostgresqlParkingDao;
import dao.interfaces.ParkingDao;
import dao.interfaces.VehicleTypeDao;

public class Postgresql extends DaoFactory {
	
	private static String url = "jdbc:postgresql://localhost/";
	private static String database = "objectville_parking";
	private static String driver = "org.postgresql.Driver";
	private static String user = "postgres_user";
	private static String password = "user@postgres";
	
	@Override
	public Connection openConnection() {   
		try {
			Class.forName(driver).newInstance();
			Connection connection = DriverManager.getConnection(url + database, user, password);
			return connection;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	@Override
	public ParkingDao getParkingDao() {
		return new PostgresqlParkingDao();
	}

	@Override
	public VehicleTypeDao getVehicleTypeDao() {
		return new PostgreslqVehicleTypeDao();
	}
}
