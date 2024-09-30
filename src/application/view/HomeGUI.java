package application.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import org.json.JSONObject;

import application.controller.CategoryController;
import application.controller.OrderController;
import application.controller.ProductController;
import application.controller.ShortcutMenuController;
import application.controller.ShortcutMenuItemController;
import application.model.Category;
import application.model.CreateOrderDetailRequest;
import application.model.PaymentMethod;
import application.model.Product;
import application.model.SaleRequest;
import application.model.ShortcutMenu;
import application.model.ShortcutMenuItem;
import java.awt.SystemColor;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class HomeGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField barcodeTxtField;
	private JTextField quantityTxtField;
	private JPanel productPanel;
	private JTable table;
	private JPanel numberPanel;
	private JButton numBtn_1;
	private JButton numBtn_2;
	private JButton numBtn_3;
	private JButton numBtn_4;
	private JButton numBtn_5;
	private JButton numBtn_6;
	private JButton numBtn_7;
	private JButton numBtn_8;
	private JButton numBtn_9;
	private JButton numBtn_10;
	private JButton numBtn_0;
	private JButton numBtn_del;
	private JPanel paymentPanel;
	private JButton cancelbtn;
	private JPanel productMenuPanel;
	private CardLayout cardLayout;
	private JToggleButton grocery2btn;
	private JToggleButton nonBarcodes;
	private JToggleButton grocery1btn;
	private JToggleButton grocery4btn;
	private JToggleButton grocery3btn;
	private JToggleButton toybtn;
	private JToggleButton butcherbtn;
	private JToggleButton clothingbtn;
	private JToggleButton sheeshabtn;
	private JPanel menuItemPanel;
	private DefaultTableModel tableModel;
	private JLabel quantitylbl = new JLabel();
	private JLabel quantityTextlbl = new JLabel();
	private JLabel totalPricelbl = new JLabel();
	private JLabel totalPriceTextlbl = new JLabel();

	private UUID selectedCategoryId;
	private UUID selectedProductId;
	private UUID selectedShortcutMenuId;

	SettingsGUI settingsGUI = new SettingsGUI();
	JToggleButton[] topButtons = new JToggleButton[9];
	String[] buttonLabels = new String[9];
	private CategoryController categoryController = new CategoryController();
	private ShortcutMenuController menuController = new ShortcutMenuController();

	JButton[] menuItemButtons = new JButton[15];
	String[] menuItemLabels = new String[15];

	private Map<String, UUID> barcodeToProductIdMap = new HashMap<>();

	private ShortcutMenuItemController menuItemController = new ShortcutMenuItemController();
	private ShortcutMenuItem item;

	List<ShortcutMenuItem> menuItems;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
        UIManager.put("OptionPane.yesButtonText", "Evet");
        UIManager.put("OptionPane.noButtonText", "Hayır");
        UIManager.put("OptionPane.cancelButtonText", "İptal");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HomeGUI frame = new HomeGUI();
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
	public HomeGUI() {

		setTitle("EasyBarcode");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1366, 768);
		contentPane = new JPanel();
		contentPane.setFocusable(false);
		contentPane.setForeground(new Color(0, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel barkodPanel = new JPanel();
		barkodPanel.setBackground(SystemColor.scrollbar);
		barkodPanel.setBounds(10, 99, 662, 112);
		contentPane.add(barkodPanel);
		barkodPanel.setLayout(null);

		numberPanel = new JPanel();
		numberPanel.setBounds(682, 0, 285, 211);
		contentPane.add(numberPanel);
		numberPanel.setLayout(new GridLayout(0, 3, 0, 0));

		barcodeTxtField = new JTextField();
		barcodeTxtField.setFont(new Font("Times New Roman", Font.BOLD, 34));
//		barcodeTxtField
//				.setBorder(new TitledBorder(null, "BARKOD NO", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		barcodeTxtField.setBounds(10, 38, 296, 63);
		barkodPanel.add(barcodeTxtField);
		barcodeTxtField.setColumns(10);
		barcodeTxtField.addActionListener(e -> searchProduct());

		quantityTxtField = new JTextField();
//		quantityTxtField.setBorder(new TitledBorder(
//				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "ADET",
//				TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		quantityTxtField.setBounds(316, 38, 186, 63);

		quantityTxtField.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				JTextField focusedField = (JTextField) e.getSource();

			}

			@Override
			public void focusLost(FocusEvent e) {
			}
		});

		barkodPanel.add(quantityTxtField);
		quantityTxtField.setFont(new Font("Times New Roman", Font.BOLD, 34));
		quantityTxtField.setColumns(10);

		JButton prodSearchBtn = new JButton("ÜRÜN BUL");
		prodSearchBtn.setForeground(new Color(255, 255, 255));
		prodSearchBtn.setFont(new Font("Times New Roman", Font.BOLD, 20));
		prodSearchBtn.setFocusPainted(false);
		prodSearchBtn.setContentAreaFilled(false);
		prodSearchBtn.setOpaque(true);

		prodSearchBtn.setBackground(new Color(43, 138, 255));
		prodSearchBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchProduct();
				focusOnBarcodeTxtField();
			}
		});
		prodSearchBtn.setBounds(512, 11, 140, 90);
		barkodPanel.add(prodSearchBtn);

		JLabel barcodeNoLbl = new JLabel("BARKOD NO");
		barcodeNoLbl.setBackground(new Color(0, 0, 0));
		barcodeNoLbl.setForeground(Color.BLACK);
		barcodeNoLbl.setFont(new Font("Times New Roman", Font.BOLD, 25));
		barcodeNoLbl.setHorizontalAlignment(SwingConstants.CENTER);
		barcodeNoLbl.setBounds(10, 0, 296, 38);
		barkodPanel.add(barcodeNoLbl);

		JLabel quantityLbl = new JLabel("MİKTAR");
		quantityLbl.setHorizontalAlignment(SwingConstants.CENTER);
		quantityLbl.setFont(new Font("Times New Roman", Font.BOLD, 25));
		quantityLbl.setBounds(316, 0, 186, 38);
		barkodPanel.add(quantityLbl);

		productPanel = new JPanel();
		productPanel.setBounds(10, 215, 957, 400);
		contentPane.add(productPanel);

		String[] columns = { "BARKOD", "ÜRÜN ADI", "MİKTAR", "FİYAT", "TOPLAM" };
		Object[][] data = {};
		tableModel = new DefaultTableModel(data, columns) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// "TOPLAM" sütunu düzenlenemez hale getiriliyor (column == 4)
				return column == 3;
			}
		};
		productPanel.setLayout(null);
		table = new JTable(tableModel);
		table.setBounds(1, 26, 632, 10);
		table.setRowHeight(45);
		productPanel.add(table);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBackground(new Color(240, 240, 240));
		scrollPane.setBounds(0, 0, 957, 400);
		productPanel.add(scrollPane);

		TableColumnModel columnModel = table.getColumnModel();

		columnModel.getColumn(0).setPreferredWidth(100); // "BARKOD" kolonunun genişliği
		columnModel.getColumn(1).setPreferredWidth(250); // "ÜRÜN ADI" kolonunun genişliği
		columnModel.getColumn(2).setPreferredWidth(100); // "MİKTAR" kolonunun genişliği
		columnModel.getColumn(3).setPreferredWidth(100); // "FİYAT" kolonunun genişliği
		columnModel.getColumn(4).setPreferredWidth(100); // "TOPLAM" kolonunun genişliği
		table.setFont(new Font("Arial", Font.PLAIN, 20));
		table.setRowHeight(40);
		JTableHeader header = table.getTableHeader();
		header.setFont(new Font("Arial", Font.BOLD, 25));

		tableModel.addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				int row = e.getFirstRow();
				int column = e.getColumn();

				if (column == 2 || column == 3) { // Eğer "MİKTAR" sütununda değişiklik yapıldıysa
					try {
						// "MİKTAR" hücresindeki değeri al
						double quantity = Double.parseDouble(tableModel.getValueAt(row, 2).toString());
						// "FİYAT" hücresindeki değeri al
						double price = Double.parseDouble(tableModel.getValueAt(row, 3).toString());
						// Toplam fiyatı hesapla
						double totalPrice = quantity * price;
						// "TOPLAM" hücresini güncelle
						tableModel.setValueAt(totalPrice, row, 4);
					} catch (NumberFormatException ex) {
						System.err.println("Hatalı sayı formatı: " + ex.getMessage());
					}
				}
				updateTotals();
			}
		});

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.rowAtPoint(e.getPoint());
				int column = table.columnAtPoint(e.getPoint());

				// Eğer tıklanan sütun "ÜRÜN ADI" sütunuysa
				if (column == 1) {
					// Seçili ürün adını al
					String productName = tableModel.getValueAt(row, 1).toString();

					// Kullanıcıya ürünü silmek isteyip istemediğini soran bir pop-up gösterin
					int confirm = JOptionPane.showConfirmDialog(productPanel,
							"Bu ürünü silmek istediğinizden emin misiniz? \nÜrün: " + productName, "Ürünü Sil",
							JOptionPane.YES_NO_OPTION);

					// Eğer kullanıcı "Evet" seçeneğini seçerse, ürünü sil
					if (confirm == JOptionPane.YES_OPTION) {
						tableModel.removeRow(row);
						updateTotals(); // Toplamları güncelle
					}
				}
			}
		});

		numBtn_1 = new JButton("1");
		numBtn_1.setForeground(new Color(255, 255, 255));
		numBtn_1.setBackground(new Color(207, 124, 69));
		numBtn_1.setFont(new Font("Times New Roman", Font.BOLD, 30));
		numBtn_1.setFocusPainted(false);
		numBtn_1.setContentAreaFilled(false);
		numBtn_1.setOpaque(true);
		numberPanel.add(numBtn_1);
		numBtn_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				quantityTxtField.setText(quantityTxtField.getText() + "1");
				focusOnBarcodeTxtField();
			}
		});

		numBtn_2 = new JButton("2");
		numBtn_2.setForeground(new Color(255, 255, 255));
		numBtn_2.setBackground(new Color(207, 124, 69));
		numBtn_2.setFont(new Font("Times New Roman", Font.BOLD, 30));
		numBtn_2.setFocusPainted(false);
		numBtn_2.setContentAreaFilled(false);
		numBtn_2.setOpaque(true);
		numberPanel.add(numBtn_2);
		numBtn_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				quantityTxtField.setText(quantityTxtField.getText() + "2");
				focusOnBarcodeTxtField();

			}
		});

		numBtn_3 = new JButton("3");
		numBtn_3.setForeground(new Color(255, 255, 255));
		numBtn_3.setBackground(new Color(207, 124, 69));
		numBtn_3.setFont(new Font("Times New Roman", Font.BOLD, 30));
		numBtn_3.setFocusPainted(false);
		numBtn_3.setContentAreaFilled(false);
		numBtn_3.setOpaque(true);
		numberPanel.add(numBtn_3);
		numBtn_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				quantityTxtField.setText(quantityTxtField.getText() + "3");
				focusOnBarcodeTxtField();
			}
		});

		numBtn_4 = new JButton("4");
		numBtn_4.setForeground(new Color(255, 255, 255));
		numBtn_4.setBackground(new Color(207, 124, 69));
		numBtn_4.setFont(new Font("Times New Roman", Font.BOLD, 30));
		numBtn_4.setFocusPainted(false);
		numBtn_4.setContentAreaFilled(false);
		numBtn_4.setOpaque(true);
		numberPanel.add(numBtn_4);
		numBtn_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				quantityTxtField.setText(quantityTxtField.getText() + "4");
				focusOnBarcodeTxtField();

			}
		});

		numBtn_5 = new JButton("5");
		numBtn_5.setForeground(new Color(255, 255, 255));
		numBtn_5.setBackground(new Color(207, 124, 69));
		numBtn_5.setFont(new Font("Times New Roman", Font.BOLD, 30));
		numBtn_5.setFocusPainted(false);
		numBtn_5.setContentAreaFilled(false);
		numBtn_5.setOpaque(true);
		numberPanel.add(numBtn_5);
		numBtn_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				quantityTxtField.setText(quantityTxtField.getText() + "5");
				focusOnBarcodeTxtField();

			}
		});

		numBtn_6 = new JButton("6");
		numBtn_6.setForeground(new Color(255, 255, 255));
		numBtn_6.setBackground(new Color(207, 124, 69));
		numBtn_6.setFont(new Font("Times New Roman", Font.BOLD, 30));
		numBtn_6.setFocusPainted(false);
		numBtn_6.setContentAreaFilled(false);
		numBtn_6.setOpaque(true);
		numberPanel.add(numBtn_6);
		numBtn_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				quantityTxtField.setText(quantityTxtField.getText() + "6");
				focusOnBarcodeTxtField();
			}
		});

		numBtn_7 = new JButton("7");
		numBtn_7.setForeground(new Color(255, 255, 255));
		numBtn_7.setBackground(new Color(207, 124, 69));
		numBtn_7.setFont(new Font("Times New Roman", Font.BOLD, 30));
		numBtn_7.setFocusPainted(false);
		numBtn_7.setContentAreaFilled(false);
		numBtn_7.setOpaque(true);
		numberPanel.add(numBtn_7);
		numBtn_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				quantityTxtField.setText(quantityTxtField.getText() + "7");
				focusOnBarcodeTxtField();
			}
		});

		numBtn_8 = new JButton("8");
		numBtn_8.setForeground(new Color(255, 255, 255));
		numBtn_8.setBackground(new Color(207, 124, 69));
		numBtn_8.setFont(new Font("Times New Roman", Font.BOLD, 30));
		numBtn_8.setFocusPainted(false);
		numBtn_8.setContentAreaFilled(false);
		numBtn_8.setOpaque(true);
		numberPanel.add(numBtn_8);
		numBtn_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				quantityTxtField.setText(quantityTxtField.getText() + "8");
				focusOnBarcodeTxtField();
			}
		});

		numBtn_9 = new JButton("9");
		numBtn_9.setForeground(new Color(255, 255, 255));
		numBtn_9.setBackground(new Color(207, 124, 69));
		numBtn_9.setFont(new Font("Times New Roman", Font.BOLD, 30));
		numBtn_9.setFocusPainted(false);
		numBtn_9.setContentAreaFilled(false);
		numBtn_9.setOpaque(true);
		numberPanel.add(numBtn_9);
		numBtn_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				quantityTxtField.setText(quantityTxtField.getText() + "9");
				focusOnBarcodeTxtField();
			}
		});

		numBtn_10 = new JButton(".");
		numBtn_10.setForeground(new Color(255, 255, 255));
		numBtn_10.setBackground(new Color(207, 124, 69));
		numBtn_10.setFont(new Font("Times New Roman", Font.BOLD, 30));
		numBtn_10.setFocusPainted(false);
		numBtn_10.setContentAreaFilled(false);
		numBtn_10.setOpaque(true);
		numBtn_10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				quantityTxtField.setText(quantityTxtField.getText() + ".");
				focusOnBarcodeTxtField();
			}
		});
		numberPanel.add(numBtn_10);

		numBtn_0 = new JButton("0");
		numBtn_0.setForeground(new Color(255, 255, 255));
		numBtn_0.setBackground(new Color(207, 124, 69));
		numBtn_0.setFont(new Font("Times New Roman", Font.BOLD, 30));
		numBtn_0.setFocusPainted(false);
		numBtn_0.setContentAreaFilled(false);
		numBtn_0.setOpaque(true);
		numBtn_0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				quantityTxtField.setText(quantityTxtField.getText() + "0");
				focusOnBarcodeTxtField();
			}
		});
		numberPanel.add(numBtn_0);

		numBtn_del = new JButton("SİL");
		numBtn_del.setForeground(new Color(255, 255, 255));
		numBtn_del.setBackground(new Color(207, 124, 69));
		numBtn_del.setFont(new Font("Times New Roman", Font.BOLD, 30));
		numBtn_del.setFocusPainted(false);
		numBtn_del.setContentAreaFilled(false);
		numBtn_del.setOpaque(true);
		numBtn_del.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (quantityTxtField != null) {
					quantityTxtField.setText("");
					focusOnBarcodeTxtField();
				}
			}
		});
		numberPanel.add(numBtn_del);

		paymentPanel = new JPanel();
		paymentPanel.setBounds(10, 619, 1350, 69);
		contentPane.add(paymentPanel);
		paymentPanel.setLayout(new GridLayout(1, 5, 5, 5));
		ButtonGroup paymentMethod = new ButtonGroup();

		JButton cash = new JButton("SATIŞ");
		cash.setForeground(new Color(255, 255, 255));
		cash.setBackground(new Color(50, 205, 50));
		cash.setFont(new Font("Times New Roman", Font.BOLD, 40));
		cash.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createOrder(PaymentMethod.CASH);
				focusOnBarcodeTxtField();
			}
		});
		cash.setFocusPainted(false);
		cash.setBorderPainted(false);
		cash.setContentAreaFilled(false);
		cash.setOpaque(true);
		paymentMethod.add(cash);

		paymentPanel.add(cash);

		JButton savebtn = new JButton("FİŞ YAZDIR");
		savebtn.setForeground(Color.WHITE);
		savebtn.setFocusPainted(false);
		savebtn.setBorderPainted(false);
		savebtn.setContentAreaFilled(false);
		savebtn.setOpaque(true);
		
		savebtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				createOrder(PaymentMethod.CASH);
				focusOnBarcodeTxtField();
			}
		});
		savebtn.setBackground(new Color(255, 128, 0));
		savebtn.setFont(new Font("Times New Roman", Font.BOLD, 40));
		paymentPanel.add(savebtn);

		cancelbtn = new JButton("FİŞ İPTAL");
		cancelbtn.setBackground(Color.RED);
		cancelbtn.setFocusPainted(false);
		cancelbtn.setBorderPainted(false);
		cancelbtn.setContentAreaFilled(false);
		cancelbtn.setOpaque(true);
		cancelbtn.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        // OptionPane ile kullanıcıya soruyu sor
		        int result = JOptionPane.showConfirmDialog(
		            null, 
		            "Satışı iptal etmek istediğinize emin misiniz?", 
		            "Satış İptali", 
		            JOptionPane.YES_NO_OPTION, 
		            JOptionPane.QUESTION_MESSAGE
		        );

		        // Kullanıcı 'Evet' seçeneğini seçerse
		        if (result == JOptionPane.YES_OPTION) {
		            tableModel.setRowCount(0); // Tabloyu sıfırla
		        }
		        // Kullanıcı 'Hayır' seçerse hiçbir işlem yapılmaz
		        
		        focusOnBarcodeTxtField();
		    }
		});

		cancelbtn.setForeground(Color.WHITE);
		cancelbtn.setFont(new Font("Times New Roman", Font.BOLD, 40));
		paymentPanel.add(cancelbtn);
		
		JButton settingsbtn = new JButton("AYARLAR");
		settingsbtn.setForeground(Color.WHITE);
		settingsbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
               
                settingsGUI.setVisible(true);
			}
		});
		settingsbtn.setBackground(Color.DARK_GRAY);
		settingsbtn.setFont(new Font("Times New Roman", Font.BOLD, 40));
		settingsbtn.setFocusPainted(false);
		settingsbtn.setBorderPainted(false);
		settingsbtn.setContentAreaFilled(false);
		settingsbtn.setOpaque(true);
		paymentPanel.add(settingsbtn);

		JPanel controlPanel = new JPanel();
		controlPanel.setBounds(10, 0, 662, 88);
		contentPane.add(controlPanel);
		controlPanel.setLayout(null);

		quantitylbl = new JLabel();
		quantityTextlbl = new JLabel("ADET :");

		quantitylbl.setForeground(Color.GRAY);
		quantitylbl.setFont(new Font("Times New Roman", Font.BOLD, 24));
		quantityTextlbl.setForeground(Color.GRAY);
		quantityTextlbl.setFont(new Font("Times New Roman", Font.BOLD, 24));

		quantitylbl.setBounds(100, 2, 552, 38);
		quantityTextlbl.setBounds(10, 2, 84, 38);
		controlPanel.add(quantitylbl);
		controlPanel.add(quantityTextlbl);

		totalPricelbl = new JLabel();
		totalPricelbl.setBackground(new Color(0, 0, 0));
		totalPricelbl.setForeground(new Color(0, 0, 0));
		totalPriceTextlbl = new JLabel("TOPLAM :");
		totalPricelbl.setFont(new Font("Times New Roman", Font.BOLD, 40));
		totalPriceTextlbl.setFont(new Font("Times New Roman", Font.BOLD, 40));
		totalPricelbl.setBounds(220, 44, 432, 44);
		totalPriceTextlbl.setBounds(10, 44, 200, 44);
		controlPanel.add(totalPricelbl);
		controlPanel.add(totalPriceTextlbl);

		productMenuPanel = new JPanel();
		productMenuPanel.setFont(new Font("Times New Roman", Font.BOLD, 30));
		productMenuPanel.setBounds(977, 0, 383, 211);
		contentPane.add(productMenuPanel);
		productMenuPanel.setLayout(new GridLayout(3, 3));

		ButtonGroup productMenubtn = new ButtonGroup();

		JButton createShortcutMenuBtn = new JButton("MENÜ OLUŞTUR");
		createShortcutMenuBtn.setBounds(10, 11, 140, 50);
//		popupPanel.add(createShortcutMenuBtn);

		for (int i = 0; i < buttonLabels.length; i++) {
			List<ShortcutMenu> menus = menuController.getAllShortcutMenus();
			if (!menus.isEmpty()) {
				String[] menuNames = menus.stream().map(ShortcutMenu::getCategoryName).toArray(String[]::new);
				// Copy the menu names into the buttonLabels array
				System.arraycopy(menuNames, 0, buttonLabels, 0, Math.min(menuNames.length, 9));
			}

			System.out.println(buttonLabels[i]);

			topButtons[i] = new JToggleButton(buttonLabels[i]); // buttonLabels dizisinden metni ayarla
			topButtons[i].addActionListener(e-> focusOnBarcodeTxtField());
			topButtons[i].setBackground(new Color(255, 228, 150));
			topButtons[i].setFocusPainted(false);
			topButtons[i].setContentAreaFilled(false);
			topButtons[i].setOpaque(true);
			topButtons[0].setSelected(true);
			selectedShortcutMenuId = menus.get(0).getId();
			productMenubtn.add(topButtons[i]);
			final int index = i;
			topButtons[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// menuItemPanel.removeAll();
					createShortcutMenuBtn.addActionListener(s -> showCategoryPopup(index));

					selectedShortcutMenuId = menus.get(index).getId();
					getShortcutMenuItems(selectedShortcutMenuId, index);

				}
			});
			productMenuPanel.add(topButtons[i]);
		}

		menuItemPanel = new JPanel();
		menuItemPanel.setBounds(977, 215, 383, 400);
		contentPane.add(menuItemPanel);
		menuItemPanel.setLayout(new GridLayout(5, 3));

		ButtonGroup menuItembtn = new ButtonGroup();

		for (int i = 0; i < menuItemLabels.length; i++) {
			menuItems = menuItemController.getAllShortcutMenuItems(selectedShortcutMenuId);
			if (!menuItems.isEmpty()) {
				String[] menuItemNames = menuItems.stream().map(ShortcutMenuItem::getProductName)
						.toArray(String[]::new);
				// Copy the menu names into the buttonLabels array
				System.arraycopy(menuItemNames, 0, menuItemLabels, 0, Math.min(menuItemNames.length, 15));
			}

			menuItemButtons[i] = new JButton(menuItemLabels[i]); // buttonLabels dizisinden metni ayarla
			menuItemButtons[i].addActionListener(e-> focusOnBarcodeTxtField());
			menuItembtn.add(menuItemButtons[i]);
			final int index = i;
			menuItemButtons[i].addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					menuItems = menuItemController.getAllShortcutMenuItems(selectedShortcutMenuId);
					item = menuItems.get(index);
					System.err.println(item.getProductName());
					if (menuItemButtons[index].getText() == "" || menuItemButtons[index].getText() == null) {
						showProductPopup(index);
					} else {
						barcodeTxtField.setText(item.getBarcode());
						simulateEnterKeyPress();
					}

				}
			});
			menuItemPanel.add(menuItemButtons[i]);
		}

	}

	private void searchProduct() {
		String barcode = barcodeTxtField.getText(); // Barkod değerini al
		String quantityText = quantityTxtField.getText();
		if (barcode.isEmpty()) {
			return; // Barkod boşsa işlem yapma
		}

		// Ürün bilgilerini JSON olarak API'den al
		JSONObject productJson = ProductController.getProductByBarcode(barcode);

		if (productJson != null) {
			// JSON'dan gerekli bilgileri al
			UUID productId = UUID.fromString(productJson.optString("id"));
			String barcodeValue = productJson.optString("barcode");
			String name = productJson.optString("productName");
			double price = productJson.optDouble("unitPrice");
			double quantity = quantityText.isEmpty() ? 1 : Double.parseDouble(quantityText);
			double totalPrice = price * quantity; // Dinamik olarak hesaplanan totalPrice

			// Ürün bilgilerini tabloya ekle
			if (tableModel != null) {
				// Yeni satır ekle
				Object[] rowData = { barcodeValue, name, quantity, price, totalPrice };
				tableModel.addRow(rowData);
			} else {
				System.err.println("Table model is null.");
			}

			barcodeToProductIdMap.put(barcodeValue, productId);

		} else {
			openProductRegistrationPanel(barcode);
			System.out.println("Ürün bulunamadı: " + barcode);
			
			
		}

		// Barkod giriş alanını temizle
		barcodeTxtField.setText("");
		quantityTxtField.setText("");
	}
	
	
	private void openProductRegistrationPanel(String barcode) {
	    // Ürün bilgilerini almak için yeni bir panel oluştur
	    JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10)); // GridLayout ile alanları organize et

	    // Alanları oluştur
	    JTextField barcodeField = new JTextField(barcode);
	    JTextField nameField = new JTextField();
	    JTextField purchasePriceField = new JTextField();
	    JTextField unitPriceField = new JTextField();
	    JTextField quantityField = new JTextField();
	    JTextField criticalCountField = new JTextField();

	    // Panelde alanları ekle
	    panel.add(new JLabel("Barkod:"));
	    panel.add(barcodeField);
	    panel.add(new JLabel("Ürün Adı:"));
	    panel.add(nameField);
	    panel.add(new JLabel("Alış Fiyatı:"));
	    panel.add(purchasePriceField);
	    panel.add(new JLabel("Birim Fiyatı:"));
	    panel.add(unitPriceField);
	    panel.add(new JLabel("Miktar:"));
	    panel.add(quantityField);
	    panel.add(new JLabel("Kritik Stok:"));
	    panel.add(criticalCountField);

	    // Kullanıcıya yeni ürün bilgilerini girmesi için bir dialog göster
	    int result = JOptionPane.showConfirmDialog(null, panel, 
	             "Yeni Ürün Kaydet", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

	    if (result == JOptionPane.OK_OPTION) {
	        // Kullanıcı bilgileri onaylarsa ürün kaydetme isteği gönder
	        saveNewProduct(barcodeField.getText(), nameField.getText(), 
	            Double.parseDouble(purchasePriceField.getText()), Double.parseDouble(unitPriceField.getText()), 
	            Integer.parseInt(quantityField.getText()), Integer.parseInt(criticalCountField.getText()));
	    }
	}

	private void saveNewProduct(String barcode, String name, double purchasePrice, double unitPrice, int quantity, int criticalCount) {
	    // Ürün verilerini JSON formatına dönüştür
	    JSONObject newProductJson = new JSONObject();
	    newProductJson.put("barcode", barcode);
	    newProductJson.put("productName", name);
	    newProductJson.put("purchasePrice", purchasePrice);
	    newProductJson.put("unitPrice", unitPrice);
	    newProductJson.put("quantity", quantity);
	    newProductJson.put("criticalCount", criticalCount);

	    // Ürün kaydetme isteği gönder
	    ProductController.createProduct(newProductJson);
	}
	
//	private void cancelOrder() {
//		
//		List<CreateOrderDetailRequest> productItems = new ArrayList<>();
//		int rowCount = tableModel.getRowCount();
//
//		for (int i = 0; i < rowCount; i++) {
//			// UUID productId = UUID.fromString((String) tableModel.getValueAt(i, 0));
//			String barcodeValue = (String) tableModel.getValueAt(i, 0);
//			UUID productId = barcodeToProductIdMap.get(barcodeValue);
//
//			double quantity = Double.parseDouble(tableModel.getValueAt(i, 2).toString()); // Miktar
//			CreateOrderDetailRequest orderDetail = new CreateOrderDetailRequest();
//			orderDetail.setProductId(productId);
//			orderDetail.setQuantity(quantity);
//			productItems.add(orderDetail);
//		}
//		
//		if (productItems.isEmpty()) {
//			JOptionPane.showMessageDialog(null, "Lütfen Satış Yapmak İçin Ürün Ekleyiniz.", "Hata",
//					JOptionPane.ERROR_MESSAGE);
//			return;
//		}
//	}

	private void createOrder(PaymentMethod paymentMethod) {
		// Tablodaki verileri al
		List<CreateOrderDetailRequest> productItems = new ArrayList<>();
		List<Product> products = new ArrayList<Product>();
		int rowCount = tableModel.getRowCount();

		for (int i = 0; i < rowCount; i++) {
			// UUID productId = UUID.fromString((String) tableModel.getValueAt(i, 0));
			String barcodeValue = (String) tableModel.getValueAt(i, 0);
			UUID productId = barcodeToProductIdMap.get(barcodeValue);
			String productName = (String) tableModel.getValueAt(i, 1);
			double quantity = Double.parseDouble(tableModel.getValueAt(i, 2).toString());
			double unitPrice = Double.parseDouble(tableModel.getValueAt(i, 3).toString());
			double totalPrice = Double.parseDouble(tableModel.getValueAt(i, 4).toString());

			// Miktar
			CreateOrderDetailRequest orderDetail = new CreateOrderDetailRequest();
			orderDetail.setProductId(productId);
			orderDetail.setQuantity(quantity);
			productItems.add(orderDetail);

			Product orderDetailWithReceipt = new Product();

			orderDetailWithReceipt.setProductName(productName);
			orderDetailWithReceipt.setQuantity(quantity);
			orderDetailWithReceipt.setUnitPrice(unitPrice);

			products.add(orderDetailWithReceipt);
		}

		if (productItems.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Lütfen Satış Yapmak İçin Ürün Ekleyiniz.", "Hata",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		// SaleRequest nesnesini oluştur
		SaleRequest saleRequest = new SaleRequest();
		saleRequest.setProductItems(productItems);
		saleRequest.setCustomerId(null); // Örnek olarak, müşteri ID'si random atanıyor, uygun bir ID
		saleRequest.setPaymentMethod(paymentMethod); // atayın

		// OrderController ile createOrder metodunu çağır
		OrderController orderController = new OrderController();
		orderController.createOrder(saleRequest);

		ReceiptPrinter receiptPrinter = new ReceiptPrinter();
		receiptPrinter.printReceipt(products);

		tableModel.setRowCount(0);

	}

	private void updateTotals() {
		double totalQuantity = 0;
		double totalPrice = 0.0;

		// Tabloyu al
		DefaultTableModel model = (DefaultTableModel) table.getModel();

		// Tablo satırlarını döngüye al
		for (int i = 0; i < model.getRowCount(); i++) {
			// Miktarı ve toplam fiyatı al
			double quantity = Double.parseDouble(model.getValueAt(i, 2).toString());
			double itemTotalPrice = Double.parseDouble(model.getValueAt(i, 4).toString());

			// Toplamları güncelle
			totalQuantity += quantity;
			totalPrice += itemTotalPrice;
		}

		// Etiketleri güncelle
		quantitylbl.setText(String.valueOf(totalQuantity));
		String formattedTotalPrice;
		if (totalPrice % 1 == 0) {
			// If there is no decimal part
			formattedTotalPrice = String.format("%,.0f TL", totalPrice);
		} else {
			// If there is a decimal part
			formattedTotalPrice = String.format("%,.2f TL", totalPrice);
		}
		totalPricelbl.setText(formattedTotalPrice);
	}

	private void showCategoryPopup(int buttonIndex) {
		JDialog dialog = new JDialog(this, "Select Category", true);
		dialog.setSize(300, 200);
		dialog.getContentPane().setLayout(new BorderLayout());

		List<Category> categories = categoryController.fetchCategories();

		DefaultListModel<String> categoryListModel = new DefaultListModel<>();
		JList<String> categoryList = new JList<>(categoryListModel);
		dialog.getContentPane().add(new JScrollPane(categoryList), BorderLayout.CENTER);

		JButton selectButton = new JButton("Select");
		selectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedCategory = categoryList.getSelectedValue();
				if (selectedCategory != null) {
					// Find the corresponding ID for the selected category
					for (Category category : categories) {
						if (category.getName().equals(selectedCategory)) {
							selectedCategoryId = category.getId();
							menuController.createShortcutMenu(selectedCategoryId);
							dialog.dispose();
							break;
						}
					}

					topButtons[buttonIndex].setText(selectedCategory);

					dialog.dispose();
				}
			}

		});
		dialog.getContentPane().add(selectButton, BorderLayout.SOUTH);

		for (Category category : categories) {
			categoryListModel.addElement(category.getName());
		}

		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}

	private void getShortcutMenuItems(UUID id, int buttonİndex) {

		Arrays.fill(menuItemLabels, "");

		menuItems = menuItemController.getAllShortcutMenuItems(id);

		for (int j = 0; j < menuItemLabels.length; j++) {
			menuItemButtons[j].setText("");
		}

		for (int i = 0; i < menuItems.size(); i++) {
			menuItemButtons[i].setText(menuItems.get(i).getProductName());
			System.out.println(menuItems.get(i).getBarcode());
		}

	}

	private void showProductPopup(int buttonIndex) {

		JDialog dialog = new JDialog(this, "Select Product", true);
		dialog.setSize(300, 200);
		dialog.getContentPane().setLayout(new BorderLayout());

		List<Product> products = ProductController.fetchProducts();

		DefaultListModel<String> productListModel = new DefaultListModel<>();
		JList<String> productList = new JList<>(productListModel);
		dialog.getContentPane().add(new JScrollPane(productList), BorderLayout.CENTER);

		JButton selectButton = new JButton("Select");
		selectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedProduct = productList.getSelectedValue();
				if (selectedProduct != null) {
					// Find the corresponding ID for the selected product
					for (Product product : products) {
						if (product.getProductName().equals(selectedProduct)) {
							selectedProductId = product.getId();
							menuItemController.createShortcutMenuItem(selectedShortcutMenuId, selectedProductId);
							dialog.dispose();
							break;
						}
					}

					menuItemButtons[buttonIndex].setText(selectedProduct);

					dialog.dispose();
				}
			}

		});
		dialog.getContentPane().add(selectButton, BorderLayout.SOUTH);

		for (Product product : products) {
			productListModel.addElement(product.getProductName());
		}

		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}

	private void simulateEnterKeyPress() {
		// Create a KeyEvent to simulate Enter key press
		ActionEvent actionEvent = new ActionEvent(barcodeTxtField, ActionEvent.ACTION_PERFORMED, "Enter");
		for (ActionListener al : barcodeTxtField.getActionListeners()) {
			al.actionPerformed(actionEvent);
		}
	}
	
	private void focusOnBarcodeTxtField() {
	    barcodeTxtField.requestFocusInWindow();
	}
}
