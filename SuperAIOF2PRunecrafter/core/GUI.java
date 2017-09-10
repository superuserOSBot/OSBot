package core;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class GUI extends JFrame {

	public static JLabel lblNewLabel;
	public static JCheckBox chckbxRuneMysteries;
	public static JLabel lblAltarLocation;
	@SuppressWarnings("rawtypes")
	public static JComboBox cbxTasks;
	public static JButton btnStartButton;

	public static final long serialVersionUID = 1337;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public GUI() {
		setTitle("SuperAIOF2PRunecrafter");
		setBounds(100, 100, 350, 250);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);

		lblNewLabel = new JLabel("SuperAIOF2PRunecrafter");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 26));
		lblNewLabel.setBounds(10, 11, 314, 58);
		getContentPane().add(lblNewLabel);

		chckbxRuneMysteries = new JCheckBox("Complete Rune Mysteries");
		chckbxRuneMysteries.setFont(new Font("Arial", Font.PLAIN, 12));
		chckbxRuneMysteries.setBounds(10, 110, 304, 23);
		getContentPane().add(chckbxRuneMysteries);

		lblAltarLocation = new JLabel("Altar Location:");
		lblAltarLocation.setFont(new Font("Arial", Font.PLAIN, 12));
		lblAltarLocation.setBounds(20, 80, 101, 23);
		getContentPane().add(lblAltarLocation);

		cbxTasks = new JComboBox();
		cbxTasks.setModel(new DefaultComboBoxModel(new String[] { "Air", "Mind", "Water", "Earth", "Fire", "Body" }));
		cbxTasks.setBounds(138, 80, 176, 20);
		getContentPane().add(cbxTasks);

		btnStartButton = new JButton("Start");
		btnStartButton.setBounds(10, 140, 314, 60);
		btnStartButton.addActionListener(new startButtonListener());
		getContentPane().add(btnStartButton);
	}

	public class startButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	}
}
