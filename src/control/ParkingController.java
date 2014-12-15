package control;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Set;

import dao.factory.DaoFactory;
import dao.interfaces.ParkingDao;
import model.Parking;
import model.Vehicle;
import observer.Subject;

/**
 * Classe controladora dos recursos disponíveis para as Views. Implementa a
 * interface Subject, declara observers através do método registerOBserver e
 * toda vez que o método notifyObservers() é chamado todos os observers que
 * estiverem registrados são notificados.
 * 
 * @author eduarth
 */
public class ParkingController implements Subject {

	private ArrayList<observer.Observer> observers = new ArrayList<observer.Observer>();
	private static ParkingController instance = new ParkingController();
	private String selectedVehiclePlate;

	public static ParkingController getInstance() {
		return instance;
	}

	private ParkingController() {
	}

	/**
	 * Método que retorna a lista de carros utilizada na população da ListView
	 * 
	 * @return Set<String>
	 */
	public Set<String> getCars() {
		try {
			return Parking.getParkedCarsList();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Cria um novo estacionamento. Método chamado pela classe interna
	 * startParkingListener da classe MenuView. Recebe o a placa do veículo e a
	 * classe do veículo a ser criado. Cria uma nova instância o veículo
	 * utilizando o método getDeclaredConstructor().
	 * 
	 * @param vehiclePlate
	 * @param vehicleType
	 */
	public void addVehicle(String vehiclePlate, Class vehicleType) {
		try {
			Vehicle vehicle = (Vehicle) vehicleType.getDeclaredConstructor(
					String.class).newInstance(vehiclePlate);

			Parking new_parking = new Parking(vehicle);
			new_parking.save();
			notifyObservers(vehicle.getPlate());

		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Encerra o estacionamento selecionado. Método chamado pela classe interna
	 * finishParkingListener da classe MenuView. Recebe o a placa do veículo do
	 * estacionamento que será encerrado. Retorna o valor do escationamento.
	 * 
	 * @param plate
	 * @return value
	 */
	public double finishSelectedParking(String plate) {
		Parking p;
		try {
			p = Parking.findByVehiclePlate(plate);
			float value = p.checkout();
			notifyObservers(plate);
			return value;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 0;
	}

	/**
	 * Métodos recebidos da interface Subject
	 * @see Subject
	 */
	@Override
	public void registerObserver(observer.Observer o) {
		observers.add(o);

	}

	@Override
	public void removeObserver(observer.Observer o) {
		int i = observers.indexOf(o);
		if (i >= 0)
			observers.remove(i);

	}

	@Override
	public void notifyObservers(String arg) {
		for (int i = 0; i < observers.size(); i++) {
			observer.Observer obsrv = observers.get(i);
			obsrv.update(arg);
		}

	}

}
