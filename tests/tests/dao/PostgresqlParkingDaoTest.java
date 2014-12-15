package tests.dao;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import model.Bus;
import model.Car;
import model.Parking;
import model.Truck;

import org.junit.Test;

import dao.concrete.PostgresqlParkingDao;
import dao.interfaces.ParkingDao;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PostgresqlParkingDaoTest {

	@Test
	public void testInsert() {
		Car c = new Car("ins123");
		Parking p = new Parking(c);
		try {
			assertNotNull(parkingDAO().insert(p));
		} catch (SQLException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testAll() {
		try {
			List<Parking> l = parkingDAO().all();
			assertTrue(l.size() > 0);
		} catch (SQLException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testDeleteAll() {
		try {
			Parking.deleteAll();
			assertTrue(parkingDAO().all().size() == 0);
		} catch (SQLException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testDelete() {
		Bus b = new Bus("del123");
		Parking p = new Parking(b);
		try {
			Parking pn = parkingDAO().insert(p);
			assertThat(parkingDAO().delete(pn), notNullValue());
		} catch (SQLException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testFindById() {
		Truck t = new Truck("fni123");
		Parking p = new Parking(t);
		try {
			Parking pn = parkingDAO().insert(p);
			assertThat(p, equalTo(pn));
		} catch (SQLException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testFindByVehiclePlate() {
		Bus b = new Bus("fnp123");
		Parking p = new Parking(b);
		try {
			Parking pn = parkingDAO().insert(p);
			assertNotNull(parkingDAO().findByVehiclePlate(b.getPlate()));
		} catch (SQLException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testFinishParking(){
		Parking p = new Parking(new Truck("fns123"));
		p.setStartTime(System.currentTimeMillis() - 18000000); // = 5 horas
		try {
			p.save();
			parkingDAO().finishParking(p);
			assertThat(parkingDAO().findById(p.getId()).getEndTime(), notNullValue());
		} catch (SQLException e) {
			fail(e.getMessage());
		}
	}

	private static ParkingDao parkingDAO() {
		return new PostgresqlParkingDao();
	}



}
