package model;

import java.sql.SQLException;
import java.util.Set;

import org.junit.Test;
import org.junit.internal.runners.statements.Fail;
import org.junit.runner.notification.Failure;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class ParkingTest {
	@Test
	public void testSave() {
		Parking p = new Parking(new Car("tsv123"));
		try {
			p.save();
			assertThat(p.getId(), notNullValue());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testFindByVehiclePlate() {
		try {
			Parking p = new Parking(new Car("tvp123"));
			p.save();
			Parking q = Parking.findByVehiclePlate("tvp123");
			assertThat(q.getVehiclePlate(), equalTo(p.getVehiclePlate()));
		} catch (SQLException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testGetParkedCarsList() {
		Parking p = new Parking(new Car("lst123"));
		try {
			Set<String> s = Parking.getParkedCarsList();
			assertThat(s.size(), greaterThan(0));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testCheckout() {
		Parking p = new Parking(new Truck("ckt321"));
		try {
			p.save();
			p.checkout();
			assertThat(p.getEndTime(), notNullValue());
		} catch (SQLException e) {
			fail(e.getMessage());
		}
	}

}
