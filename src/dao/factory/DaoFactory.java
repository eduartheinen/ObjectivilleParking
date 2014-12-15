package dao.factory;

import java.sql.Connection;

import dao.interfaces.ParkingDao;
import dao.interfaces.VehicleTypeDao;

/**
 * Classe abstrata responsável por retornar a classe concreta responsável por
 * tratar as requisições ao banco de dados (DAO). Utiliza injeção de
 * dependências através da classe MetaReader.
 * 
 * @author eduarth
 *
 */
public abstract class DaoFactory {

	public abstract Connection openConnection();

	public abstract ParkingDao getParkingDao();

	public abstract VehicleTypeDao getVehicleTypeDao();

	/**
	 * Pede que o método create leia as configurações para a criação de objetos
	 * da classe DaoFactory.
	 * 
	 * @return
	 */
	public static DaoFactory getDatabase() {
		return create(DaoFactory.class);
	}

	/**
	 * Método que lê o arquivo de configuração e retorna a classe correspondente
	 * ao nome da interface passado como parâmetro.
	 * 
	 * @see config/database.properties
	 * 
	 * @param _interface
	 * @return
	 */
	public static <E> E create(Class<E> _interface) {
		MetaReader reader = new MetaReader("config/database.properties");
		String className = reader.getProperty(_interface.getName());

		if (className == null)
			throw new IllegalArgumentException("Class " + _interface.getName()
					+ " does not exists on properties");

		try {
			Class<?> clazz = Class.forName(className);
			if (_interface.isAssignableFrom(clazz)) {
				return (E) clazz.newInstance();
			} else {
				throw new IllegalArgumentException("Class " + className
						+ " does not implements " + _interface.getName());
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
}
