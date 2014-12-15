package view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import model.Bus;
import model.Car;
import model.Truck;
import observer.Observer;
import observer.Subject;
import control.ParkingController;

/**
 * View que apresenta os botões de iniciar e encerrar o "parking" e o campo para
 * a placa.
 * <p>
 * No construtor adiciona a sí mesma como observadora do "subject" (sujeito
 * observável) que recebe como parâmetro, adiciona ao layout um botão de
 * encerrar o estacionamento através do método finishParkingButton(), um campo
 * para a placa do carro através do método vehiclePlateField(), uma JComboBox
 * para a seleção do tipo de veículo através do método vehicleTypeCombobox(), e
 * um botão para iniciar um estacionamento através do método
 * startParkingButton().
 * 
 * @author eduarth
 */
public class MenuView extends JPanel implements Observer {

	private static final long serialVersionUID = 4170601937523669733L;
	private static final String startParkingString = "Iniciar";
	private static final String finishParkingString = "Encerrar";
	private StartParkingListener startParkingListener = new StartParkingListener();
	private FinishParkingListener finishParkingListener = new FinishParkingListener();
	private JTextField vehiclePlateField;
	private JComboBox vehicleTypeComboBox;
	private String selectedPlate;
	private Subject subject;
	private JButton startParkingButton;
	private JButton finishParkingButton;

	public MenuView(Subject subject) {
		this.subject = subject;
		subject.registerObserver(this);

		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

		this.add(finishParkingButton());
		this.add(Box.createHorizontalStrut(5));

		this.add(new JSeparator(SwingConstants.VERTICAL));
		this.add(Box.createHorizontalStrut(5));

		this.add(vehiclePlateField());
		this.add(Box.createHorizontalStrut(5));

		this.add(vehicleTypeComboBox());
		this.add(Box.createHorizontalStrut(5));

		this.add(startParkingButton());
		this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
	}

	/**
	 * JComboBox construída utilizando um renderer específico. JComboBox podem
	 * ser construídas utilizando um vetor de objetos. Cria-se então uma classe
	 * ComboBoxItem que armazena uma classe e uma string. Então cria-se a
	 * JComboBox utilizando este vetor de objetos da classe ComboBoxItem, e
	 * então define-se um novo renderer, que conhece a implementação da
	 * ComboBoxItem e constrói a JComboBox utilizando as Strings armazenadas nos
	 * objetos como rótulos.
	 * 
	 * É construída dessa maneira para que se possa retornar ao controller a
	 * classe que deverá ser criada para armazenar o estacionamento: Car, Bus ou
	 * Truck.
	 * 
	 * @return JComboBox
	 */
	private JComboBox vehicleTypeComboBox() {
		Vector model = new Vector();
		model.addElement(new ComboBoxItem("Carro", Car.class));
		model.addElement(new ComboBoxItem("Caminhão", Truck.class));
		model.addElement(new ComboBoxItem("Ônibus", Bus.class));

		vehicleTypeComboBox = new JComboBox(model);
		vehicleTypeComboBox.setRenderer(new ItemRenderer());

		return vehicleTypeComboBox;
	}

	/**
	 * Constrói e retorna o campo necessário para receber a placa do carro
	 * estacionado.
	 * 
	 * @return JTextField
	 */
	private JTextField vehiclePlateField() {
		vehiclePlateField = new JTextField(10);

		return vehiclePlateField;
	}

	/**
	 * Constrói e retorna o botão responsável por encerrar o estacionamento
	 * selecionado. Adiciona o parmâmetro finishParkingListener da classe com o
	 * mesmo nome como listener.
	 * 
	 * @return JTextField
	 */
	private JButton finishParkingButton() {
		finishParkingButton = new JButton(finishParkingString);
		finishParkingButton.setActionCommand(finishParkingString);
		finishParkingButton.addActionListener(finishParkingListener);

		return finishParkingButton;
	}

	/**
	 * Constrói e retorna o botão responsável por iniciar o estacionamento
	 * selecionado. Adiciona o parmâmetro startParkingListener da classe com o
	 * mesmo nome como listener.
	 * 
	 * @return JTextField
	 */
	private JButton startParkingButton() {
		startParkingButton = new JButton(startParkingString);
		startParkingButton.setActionCommand(startParkingString);
		startParkingButton.addActionListener(startParkingListener);

		return startParkingButton;
	}

	@Override
	public void update(String arg) {
		selectedPlate = arg;
	}

	/**
	 * Classe responsável por ouvir cliques no botão startParkingButton.
	 * Verifica se o campo vehiclePlateField foi preenchido: se não cria um
	 * dialog, se sim envia para o método addVehicle de uma instância do
	 * ParkingController a placa e a classe retirada do objeto ComboBoxItem.
	 * 
	 * @author eduarth
	 *
	 */
	class StartParkingListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String vehiclePlate = vehiclePlateField.getText();
			if (vehiclePlate.equals("")) {
				JOptionPane.showMessageDialog(getParent(),
						"Preencha a placa do veículo");
			} else {
				Class vehicleType = ((ComboBoxItem) vehicleTypeComboBox
						.getSelectedItem()).getVehicle();

				ParkingController.getInstance().addVehicle(vehiclePlate,
						vehicleType);
			}
		}
	}

	/**
	 * Classe responsável por ouvir cliques no botão finishParkingButton. Envia
	 * para o método finishSelectedParking de uma instância do ParkingController
	 * a placa selecionada. O método conhece a placa pois a classe MenuView está
	 * declarada como observer da classe ListView. Sempre que há uma mudança de
	 * seleção a classe MenuView é notificada e o método update() é executado.
	 * Este método atualiza o o campo selectedPlate com o valor de placa que
	 * recebe.
	 * 
	 * @author eduarth
	 *
	 */
	class FinishParkingListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			double value = ParkingController.getInstance()
					.finishSelectedParking(selectedPlate);

			JOptionPane.showMessageDialog(null, "Encerrar estacionamento: "
					+ value);
		}
	}

	/**
	 * Classe responsável por armazenar uma string e uma classe utilizados para
	 * construir o ComboBox que envia a classe que deve ser instanciada para o
	 * controller.
	 * 
	 * @author eduarth
	 *
	 */
	class ComboBoxItem {
		public String description;
		public Class vehicle;

		public ComboBoxItem(String description, Class vehicle) {
			this.description = description;
			this.vehicle = vehicle;
		}

		public String getDescription() {
			return description;
		}

		public Class getVehicle() {
			return vehicle;
		}
	}

	/**
	 * Classe que define o renderer para os elementos específicos criados com a
	 * classe ComboBoxItem.
	 * 
	 * @author eduarth
	 *
	 */
	class ItemRenderer extends BasicComboBoxRenderer {
		@Override
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index, isSelected,
					cellHasFocus);

			if (value != null) {
				ComboBoxItem item = (ComboBoxItem) value;
				setText(item.getDescription());
			}

			if (index == -1) {
				ComboBoxItem item = (ComboBoxItem) value;
				setText("" + item.getDescription());
			}

			return this;
		}
	}
}