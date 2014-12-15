package control;

import static org.junit.Assert.fail;

import java.sql.SQLException;

import model.Car;
import model.Parking;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ParkingControllerTest {

	@Test
	public void testAddVehicle() {
		ParkingController.getInstance().addVehicle("add123", Car.class);
		try {
			Parking p = Parking.findByVehiclePlate("add123");
			assertThat(p.getId(), notNullValue());
		} catch (SQLException e) {
			fail(e.getMessage());
		}
	}
}
