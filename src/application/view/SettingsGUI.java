package application.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SettingsGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	MonthlySalesGUI monthlySalesGUI = new MonthlySalesGUI();
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SettingsGUI frame = new SettingsGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SettingsGUI() {
		setTitle("AYARLAR");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
		setBounds(100, 100, 1366, 768);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel settingsPanel = new JPanel();
		settingsPanel.setBackground(new Color(197, 197, 197));
		settingsPanel.setBounds(10, 11, 1330, 707);
		contentPane.add(settingsPanel);
		settingsPanel.setLayout(null);
		
		JButton monthlySalesBtn = new JButton("AYLIK CİRO RAPORU");
		monthlySalesBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				monthlySalesGUI.setVisible(true);
				
			}
		});
		monthlySalesBtn.setFont(new Font("Times New Roman", Font.BOLD, 20));
		monthlySalesBtn.setBounds(70, 44, 239, 70);
		settingsPanel.add(monthlySalesBtn);
	}
}
