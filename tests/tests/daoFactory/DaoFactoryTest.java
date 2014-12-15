package tests.daoFactory;

import static org.junit.Assert.*;

import org.junit.Test;

import dao.factory.DaoFactory;

public class DaoFactoryTest {

	@Test
	public void testGetDatabase() {
		DaoFactory dao = DaoFactory.getDatabase();
		assertTrue("deve retornar um DaoFactory", DaoFactory.class.isAssignableFrom(dao.getClass()));
	}
}
