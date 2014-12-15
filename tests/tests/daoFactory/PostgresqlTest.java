package tests.daoFactory;

import static org.junit.Assert.*;

import java.sql.Connection;

import org.junit.Test;

import dao.factory.Postgresql;

public class PostgresqlTest {

	@Test
	public void testOpenConnection() {
		Postgresql psql = new Postgresql();
		Connection conn = psql.openConnection();
		assertNotNull("Deve retornar uma conexão", conn);
	}

}
