package view;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import observer.Observer;
import observer.Subject;
import control.ParkingController;

/**
 * View que apresenta a Lista de carros estacionados.
 * <p>
 * No construtor adiciona ao layout o JScrollPane retornado pelo método
 * initList(); declara a sí mesma como observer da classe ParkingController;
 * 
 * @author eduarth
 */

public class ListView extends JPanel implements Subject, Observer,
		ListSelectionListener {
	private static final long serialVersionUID = 2724666589846648871L;
	private ArrayList<observer.Observer> observers;
	private static JList<String> list;
	private static DefaultListModel<String> listModel;

	public ListView() {
		super(new BorderLayout());
		observers = new ArrayList<observer.Observer>();
		this.add(initList());

		// observer
		ParkingController.getInstance().registerObserver(this);
	}

	/**
	 * Cria uma lista, faz uma chamada ao método loadListData(), responsável por
	 * popular a lista; define o tamanho com "setVisibleRowCount(10)"; adiciona
	 * a própria classe ListView como SelectionListener; e retorna a lista
	 * construída e populada.
	 * 
	 * @return JScrollPane(povoado pelo método loadListData());
	 */
	private JScrollPane initList() {
		listModel = new DefaultListModel<String>();
		loadListData();

		list = new JList<String>(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// list.setSelectedIndex(0);
		list.setVisibleRowCount(10);
		list.addListSelectionListener(this);

		return new JScrollPane(list);
	}

	/**
	 * Utiliza uma instância do ParkingController para pedir a lista de carros
	 * estacionados através do metodo getCars();
	 */
	private void loadListData() {
		listModel.clear();
		for (String plate : ParkingController.getInstance().getCars()) {
			listModel.addElement(plate);
		}
	}

	/**
	 * Métodos implementados da interface Subject
	 * 
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

	/**
	 * Métodos implementados da interface ListSelectionListener
	 */
	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (list.getSelectedValue() != null)
			notifyObservers(list.getSelectedValue().toString());
	}

	/**
	 * Métodos implementados da interface Observer, sempre que o
	 * ParkingController utilizar o método notifyObservers esta lista receberá a
	 * chamada e atualizará os seus elementos com o método loadListData();
	 */
	@Override
	public void update(String arg) {
		loadListData();
	}
}
