package dao.interfaces;

import java.sql.SQLException;
import java.util.List;

import model.Parking;

public interface ParkingDao {

	Parking insert(Parking parking) throws SQLException;

	List<Parking> all() throws SQLException;

	int deleteAll() throws SQLException;

	int delete(Parking parking) throws SQLException;

	Parking findByVehiclePlate(String login) throws SQLException;

	Parking findById(Long id) throws SQLException;

	Parking finishParking(Parking parking) throws SQLException;
}
