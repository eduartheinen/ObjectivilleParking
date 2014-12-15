package tests.dao;

import static org.junit.Assert.assertNotNull;
import java.sql.SQLException;

import model.Car;
import model.Truck;

import org.junit.Test;

public class PostgreslqVehicleTypeDaoTest {

	@Test
	public void testGetPriceByDay() {
		Truck t = new Truck("abc123");
		try {
			float price = t.getPricebyDay();
			assertNotNull(price);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetPriceByHour() {
		Car t = new Car("abc123");
		try {
			float price = t.getPricebyHour();
			assertNotNull(price);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetPriceByMonth() {
		Car t = new Car("abc123");
		try {
			float price = t.getPricebyMonth();
			assertNotNull(price);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
