package dao.interfaces;

import java.sql.SQLException;

public interface VehicleTypeDao {

	float getPriceByDay(String classname) throws SQLException;

	float getPriceByHour(String classname) throws SQLException;

	float getPriceByMonth(String classname) throws SQLException;

}
