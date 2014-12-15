package model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import control.TimeManager;
import dao.factory.DaoFactory;
import dao.interfaces.ParkingDao;

/**
 * Classe que representa a instância do estacionamento, armazena um objeto do
 * tipo Vehicle (Car, Bus ou Truck), o tempo de início, tempo de fim e o id do
 * parking.
 * 
 * @author eduarth
 *
 */
public class Parking {

	private Vehicle vehicle;
	private long startTime;
	private long endTime;
	private Long id = null;

	/**
	 * O construtor da classe recebe o veículo armazenado e define o tempo de
	 * início do estacionamento
	 * 
	 * @param v
	 */
	public Parking(Vehicle v) {
		this.vehicle = v;
		this.startTime = System.currentTimeMillis();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public String getVehiclePlate() {
		return vehicle.getPlate();
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public double getStartTime() {
		return startTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public String[] toArray() {
		return new String[] { this.id.toString(), this.vehicle.getPlate(),
				String.valueOf(this.startTime) };
	}

	@Override
	public String toString() {
		return " id: " + id + " vehicle plate: " + vehicle.getPlate()
				+ " start time: " + startTime;
	}

	/**
	 * Method to save the current parking in the database
	 */
	public void save() throws SQLException {
		Parking pn = parkingDAO().insert(this);
		this.id = pn.getId();
	}

	/**
	 * Method to delete the current parking from the database
	 */
	public void delete() throws SQLException {
		parkingDAO().delete(this);
		this.id = null;
	}

	/**
	 * Method to find all parkings from the database
	 * 
	 * @return users all parkings from the database
	 */
	public static List<Parking> all() throws SQLException {
		return parkingDAO().all();
	}

	/**
	 * Método para recuperar um parking do banco de dados pela placa do veículo
	 * 
	 * @param vehiclePlate
	 * @return Parking
	 * @throws SQLException
	 */
	public static Parking findByVehiclePlate(String vehiclePlate)
			throws SQLException {
		return parkingDAO().findByVehiclePlate(vehiclePlate);
	}

	/**
	 * Método para recuperar um parking do banco de dados por seu id
	 * 
	 * @param id
	 * @return Parking
	 * @throws SQLException
	 */
	public static Parking findById(Long id) throws SQLException {
		return parkingDAO().findById(id);
	}

	/**
	 * Método para deletar todos os parkings do banco de dados
	 * 
	 * @return int rows affected
	 * @throws SQLException
	 */
	public static int deleteAll() throws SQLException {
		return parkingDAO().deleteAll();
	}

	/**
	 * Retorna uma instância do DAO responsável pela entidade Parking. Utiliza o
	 * padrão Factory para que não seja necessário fixar qual SGBD.
	 * 
	 * @see DaoFactory
	 * 
	 * @return ParkingDao
	 */
	private static ParkingDao parkingDAO() {
		DaoFactory dao = DaoFactory.getDatabase();
		return dao.getParkingDao();
	}

	/**
	 * Método responsável por encerrar um estacionamento; define primeiramente o
	 * tempo de encerramento do estacionamento, então pede que o ParkingDAO
	 * encerre o estacionamento; calcula a duração e faz uma chamada ao método
	 * setBillingMethod da classe TimeManager. A classe TimeManager define qual
	 * é o ChargeMethod dependendo do tempo que o estacionamento durou. O método
	 * setBillingMethod(duration) retorna uma instância de uma classe que
	 * implemente a interface ChargeMethod; por fim o método retorna o valor do
	 * estacionamento calculado pelo ChargeMethod definido pela classe
	 * TimeManager.
	 * 
	 * @return
	 */
	public float checkout() {
		endTime = System.currentTimeMillis();
		try {
			parkingDAO().finishParking(this);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long duration = endTime - startTime;
		ChargeMethod b = TimeManager.setBillingMethod(duration);
		return b.calc(duration, vehicle);
	}

	/**
	 * Pede ao DAO que recupere do banco a lista de carros estacionados e então
	 * povoa um set com os valores das placas.
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static Set<String> getParkedCarsList() throws SQLException {
		List<Parking> l = parkingDAO().all();
		HashSet<String> s = new HashSet<String>();
		for (Parking parking : l) {
			s.add(parking.getVehiclePlate());
		}
		return s;
	}
}
