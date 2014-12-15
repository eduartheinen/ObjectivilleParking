package dao.concrete;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.factory.DaoFactory;
import dao.interfaces.VehicleTypeDao;

public class PostgreslqVehicleTypeDao implements VehicleTypeDao {

	private static final String GET_PRICE_BY_HOUR = "SELECT price_by_hour from vehicle_types where description = ?";
	private static final String GET_PRICE_BY_DAY = "SELECT price_by_day from vehicle_types where description = ?";
	private static final String GET_PRICE_BY_MONTH = "SELECT price_by_month from vehicle_types where description = ?";

	@Override
	public float getPriceByDay(String classname) throws SQLException {
		Connection c = DaoFactory.getDatabase().openConnection();
		PreparedStatement pstmt = c.prepareStatement(GET_PRICE_BY_DAY);
		pstmt.setString(1, classname);

		ResultSet rset = pstmt.executeQuery();
		rset.next();
		float value = rset.getFloat(1);

		pstmt.close();
		c.close();

		return value;
	}

	@Override
	public float getPriceByHour(String classname) throws SQLException {
		Connection c = DaoFactory.getDatabase().openConnection();
		PreparedStatement pstmt = c.prepareStatement(GET_PRICE_BY_HOUR);
		pstmt.setString(1, classname);

		ResultSet rset = pstmt.executeQuery();
		rset.next();
		float value = rset.getFloat(1);

		pstmt.close();
		c.close();

		return value;
	}

	@Override
	public float getPriceByMonth(String classname) throws SQLException {
		Connection c = DaoFactory.getDatabase().openConnection();
		PreparedStatement pstmt = c.prepareStatement(GET_PRICE_BY_MONTH);
		pstmt.setString(1, classname);

		ResultSet rset = pstmt.executeQuery();
		rset.next();
		float value = rset.getFloat(1);

		pstmt.close();
		c.close();

		return value;
	}

}
