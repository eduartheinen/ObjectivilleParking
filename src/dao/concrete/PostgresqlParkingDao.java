package dao.concrete;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Parking;
import model.Vehicle;
import dao.factory.DaoFactory;
import dao.interfaces.ParkingDao;

public class PostgresqlParkingDao implements ParkingDao {

	private static final String INSERT = "INSERT INTO parkings (vehicle_plate, start_time, vehicle_type_id) VALUES (?, ?, (SELECT id FROM vehicle_types WHERE description = ?))";

	private static final String ALL = "SELECT * FROM parkings p, vehicle_types vt where p.vehicle_type_id = vt.id and p.end_time is null";

	private static final String FIND_BY_VEHICLE_PLATE = "SELECT * FROM parkings p, vehicle_types vt where p.vehicle_type_id = vt.id and p.vehicle_plate = ?";

	private static final String FIND_BY_ID = "SELECT * FROM parkings p, vehicle_types vt where p.vehicle_type_id = vt.id and p.id = ?";

	private static final String DELETE = "DELETE FROM parkings where id = ?";

	private static final String DELETE_ALL = "DELETE FROM parkings";
	
	private static final String FINISH = "UPDATE parkings set end_time = ? where id = ?";

	@Override
	public Parking insert(Parking parking) throws SQLException {
		Connection c = DaoFactory.getDatabase().openConnection();

		PreparedStatement pstmt = c.prepareStatement(INSERT,
				Statement.RETURN_GENERATED_KEYS);

		pstmt.setString(1, parking.getVehiclePlate());
		pstmt.setDouble(2, parking.getStartTime());
		pstmt.setString(3, parking.getVehicle().getClass().getName());

		pstmt.executeUpdate();

		ResultSet rset = pstmt.getGeneratedKeys();

		rset.next();
		Long idGenerated = rset.getLong(1);
		parking.setId(idGenerated);

		pstmt.close();
		c.close();

		return parking;
	}

	@Override
	public List<Parking> all() throws SQLException {
		ArrayList<Parking> parkings = new ArrayList<Parking>();

		Connection c = DaoFactory.getDatabase().openConnection();
		PreparedStatement pstmt = c.prepareStatement(ALL);

		ResultSet rset = pstmt.executeQuery();
		while (rset.next()) {
			parkings.add(createParking(rset));
		}

		pstmt.close();
		c.close();

		return parkings;
	}

	@Override
	public int deleteAll() throws SQLException {
		Connection c = DaoFactory.getDatabase().openConnection();
		PreparedStatement pstmt = c.prepareStatement(DELETE_ALL);

		int rowsAffected = pstmt.executeUpdate();

		pstmt.close();
		c.close();

		return rowsAffected;
	}

	@Override
	public int delete(Parking parking) throws SQLException {
		Connection c = DaoFactory.getDatabase().openConnection();
		PreparedStatement pstmt = c.prepareStatement(DELETE);
		pstmt.setLong(1, parking.getId());

		int rowsAffected = pstmt.executeUpdate();

		pstmt.close();
		c.close();

		return rowsAffected;
	}

	@Override
	public Parking findById(Long id) throws SQLException {
		Connection c = DaoFactory.getDatabase().openConnection();

		PreparedStatement pstmt = c.prepareStatement(FIND_BY_ID);
		pstmt.setLong(1, id);

		Parking parking = null;
		ResultSet rset = pstmt.executeQuery();

		while (rset.next()) {
			parking = createParking(rset);
		}

		pstmt.close();
		c.close();

		return parking;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Parking createParking(ResultSet rset) throws SQLException {
		try {
			Class c = Class.forName(rset.getString("description"));
			Vehicle v = (Vehicle) c.getDeclaredConstructor(String.class)
					.newInstance(rset.getString("vehicle_plate"));
			Parking parking = new Parking(v);
			parking.setId(rset.getLong("id"));
			parking.setStartTime(rset.getLong("start_time"));
			parking.setEndTime(rset.getLong("end_time"));
			return parking;

		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException
				| ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Parking findByVehiclePlate(String login) throws SQLException {
		Connection c = DaoFactory.getDatabase().openConnection();

		PreparedStatement pstmt = c.prepareStatement(FIND_BY_VEHICLE_PLATE);
		pstmt.setString(1, login);

		Parking parking = null;
		ResultSet rset = pstmt.executeQuery();

		while (rset.next()) {
			parking = createParking(rset);
		}

		pstmt.close();
		c.close();

		return parking;
	}

	public List<Parking> getAllPlates() throws SQLException {
		ArrayList<Parking> parkings = new ArrayList<Parking>();

		Connection c = DaoFactory.getDatabase().openConnection();
		PreparedStatement pstmt = c.prepareStatement(ALL);

		ResultSet rset = pstmt.executeQuery();
		while (rset.next()) {
			parkings.add(createParking(rset));
		}

		pstmt.close();
		c.close();

		return parkings;
	}

	@Override
	public Parking finishParking(Parking parking) throws SQLException {
		Connection c = DaoFactory.getDatabase().openConnection();

		PreparedStatement pstmt = c.prepareStatement(FINISH,
				Statement.RETURN_GENERATED_KEYS);

		pstmt.setLong(1, parking.getEndTime());
		pstmt.setLong(2, parking.getId());

		pstmt.executeUpdate();

		ResultSet rset = pstmt.getGeneratedKeys();

		rset.next();
		Long idGenerated = rset.getLong(1);
		parking.setId(idGenerated);

		pstmt.close();
		c.close();

		return parking;
		
	}
}
