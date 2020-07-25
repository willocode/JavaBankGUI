package ignore.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.PrintStream;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import ignore.workers.ExampleAccount2Worker;
import ignore.workers.ExampleAccountWorker;
import ignore.workers.AddAccountWorker;
import ignore.workers.DepositWorker;
import ignore.workers.Messenger;
import ignore.workers.ViewAccountsWorker;
import ignore.workers.WithdrawWorker;

public class MainFrame extends JFrame implements ActionListener, PropertyChangeListener {
	private static final long serialVersionUID = 1L;
	private Label lblCount;
	private JButton executeBtn;
	private JComboBox colorList;
	private JProgressBar progressBar;
	JTextArea errorField;

	JMenu submenu;
	private HashMap<String, Component> componentMap = new HashMap<>();

	public MainFrame() {
		// setup data
		initializeData();

		// setup GUI
		initializeGUI();

		// setup messages
		initializeMessages();

		// initialize UI state
		initializeUIState();
	}

	private void initializeUIState() {
		// disable buttons if their functionality are not present
	}

	private void initializeMessages() {
		Messenger m = new Messenger();
		m.execute();
	}

	private void initializeGUI() {
		getContentPane().setLayout(new BoxLayout(getContentPane(), 3));

		JPanel panel = new JPanel(new FlowLayout());
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);

		add(panel);

		setupTabs();

		JTextArea area = createLogPanel();

		// Create the menu bar.
		JMenuBar menuBar = new JMenuBar();
		createFileMenu(menuBar, area);
		setJMenuBar(menuBar);

		setTitle("Spark Project");
		setSize(600, 400);

		setDefaultCloseOperation(3);

		setVisible(true);
	}

	private JPanel setupPanel1() {
		JPanel panel = new JPanel();
		JButton button = new JButton("Run Example 1");
		button.addActionListener(this);
		button.setActionCommand("exAct1");
		panel.add(button);

		return panel;
	}

	private JPanel setupPanel2() {
		JPanel panel = new JPanel();
		JButton button = new JButton("Run Example 2");
		button.addActionListener(this);
		button.setActionCommand("exAct2");
		panel.add(button);

		return panel;
	}

	private void setupTabs() {
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Example Account 1", setupPanel1());
		tabbedPane.addTab("Example Account 2", setupPanel2());
		tabbedPane.addTab("Accounts", createATMPanel());

		add(tabbedPane);
	}

	private JTextArea createLogPanel() {
		JTextArea outputArea = new JTextArea();
		
		// set output Area
		TitledBorder title;
		title = BorderFactory.createTitledBorder("Log");
		// outputArea.setBorder(title);
		outputArea.setEditable(false);
		PrintStream out = new PrintStream(new JOutput(outputArea));

		// redirect standard output stream to the TextAreaOutputStream
		System.setOut(out);

		// redirect standard error stream to the TextAreaOutputStream
		System.setErr(out);

		JScrollPane pane = new JScrollPane(outputArea);
		pane.setPreferredSize(new Dimension(200, 400));

		add(pane);

		return outputArea;
	}

	private JPanel createATMPanel() {
		JPanel amountPanel = new JPanel();
		amountPanel.setLayout(new FlowLayout());

		JComboBox accounts = new JComboBox();
		TitledBorder title2;
		title2 = BorderFactory.createTitledBorder("Accounts");
		accounts.setBorder(title2);
		accounts.addActionListener(this);
		amountPanel.add(accounts);
		componentMap.put("accounts", accounts);

		// create label
		JLabel label = new JLabel("$");
		amountPanel.add(label);

		// create box
		JTextField textbox = new JTextField();
		textbox.setEditable(true);
		textbox.setColumns(15);
		componentMap.put("amount", textbox);

		textbox.setName("Amountbox");
		TitledBorder title;
		title = BorderFactory.createTitledBorder("Amount");
		textbox.setBorder(title);
		amountPanel.add(textbox);

		// create radio buttons
		JRadioButton radiobtn1 = new JRadioButton();
		radiobtn1.setName("withdraw-button");
		radiobtn1.setText("withdraw");
		radiobtn1.setActionCommand("withdraw");
		componentMap.put("withdraw-button", radiobtn1);

		JRadioButton radiobtn2 = new JRadioButton();
		radiobtn2.setText("deposit");
		radiobtn2.setName("deposit-button");
		componentMap.put("deposit-button", radiobtn2);

		// Group the radio buttons.
		ButtonGroup group = new ButtonGroup();
		group.add(radiobtn1);
		group.add(radiobtn2);

		JPanel radioPanel = new JPanel(new GridLayout(0, 1));
		radioPanel.add(radiobtn1);
		radioPanel.add(radiobtn2);

		JPanel panel = new JPanel(new FlowLayout());

		panel.add(amountPanel);
		panel.add(radioPanel);

		JPanel p = new JPanel(new BorderLayout());
		p.add(panel, BorderLayout.PAGE_START);

		// add Buttons
		JPanel flowPanel = new JPanel(new FlowLayout());
		JButton accountAddBtn = new JButton("Add Accounts");
		accountAddBtn.setActionCommand("addAccounts");
		accountAddBtn.addActionListener(this);
		flowPanel.add(accountAddBtn);

		JButton accountBtn = new JButton("View Accounts");
		accountBtn.setActionCommand("viewAccounts");
		accountBtn.addActionListener(this);
		flowPanel.add(accountBtn);

		flowPanel.add(new JLabel());

		JButton executeBtn = new JButton("Execute");
		executeBtn.setActionCommand("execute");
		executeBtn.addActionListener(this);
		flowPanel.add(executeBtn);
		p.add(flowPanel, BorderLayout.CENTER);

		return p;
	}

	private void createFileMenu(JMenuBar menuBar, JTextArea area) {
		JMenu menu = new JMenu("File");

		JMenuItem clearConsoleItem = new JMenuItem("Clear console");
		clearConsoleItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Clearing console");
				area.setText("");
			}
		});

		menu.add(clearConsoleItem);

		menuBar.add(menu);
	}

	public Component getComponentByName(String name) {
		if (componentMap.containsKey(name)) {
			return (Component) componentMap.get(name);
		} else
			return null;
	}

	private void initializeData() {
		/*
		 * //setup a new Ledger this.ledger = new Ledger();
		 * 
		 * //setup a new Account Account a = new SavingsAccount();
		 * ledger.storeAccount(a);
		 */
	}

	public static void main(String[] args) {
		MainFrame app = new MainFrame();
	}

	public void actionPerformed(ActionEvent evt) {

		if (evt.getActionCommand().equals("help1")) {
			// Display modal of instructions
			JOptionPane.showMessageDialog(this,
					"Choose from the menus on the left to select to run one of the exercises");
		}else if (evt.getActionCommand().equals("viewAccounts")) {

			String s = (String) JOptionPane.showInputDialog(this, "Choose the minimum:\n",
					"Customized Dialog", JOptionPane.PLAIN_MESSAGE);

			JComboBox b = (JComboBox) getComponentByName("accounts");
			Object[] possibilities = { "checking", "savings" };
			String s2 = (String) JOptionPane.showInputDialog(this, "Choose the type of account to report on:\n",
					"Customized Dialog", JOptionPane.PLAIN_MESSAGE, null, possibilities, "checking");
			
			
			//If a string was returned, say so.
			if ((s != null) && (s.length() > 0) && (s2 != null) && (s2.length() > 0)) {
				ViewAccountsWorker worker = new ViewAccountsWorker(s, s2);
				worker.addPropertyChangeListener(this);
				worker.execute();

				return;
			}

			
		}else if (evt.getActionCommand().equals("addAccounts")) {
			JComboBox b = (JComboBox) getComponentByName("accounts");
			Object[] possibilities = { "checking", "savings" };
			String s = (String) JOptionPane.showInputDialog(this, "Choose the type of account to create:\n",
					"Customized Dialog", JOptionPane.PLAIN_MESSAGE, null, possibilities, "checking");

			// If a string was returned, say so.
			if ((s != null) && (s.length() > 0)) {
				AddAccountWorker worker = new AddAccountWorker(s, b);
				worker.execute();

				return;
			}


		} else if (evt.getActionCommand().equals("exAct1")) {
			ExampleAccountWorker worker = new ExampleAccountWorker();
			worker.execute();
		} else if (evt.getActionCommand().equals("exAct2")) {
			ExampleAccount2Worker worker = new ExampleAccount2Worker();
			worker.execute();
		} else if (evt.getActionCommand().equals("execute")) {

			// get value of radio button
			Component c = getComponentByName("withdraw-button");
			Component c2 = getComponentByName("deposit-button");
			Component a = getComponentByName("amount");
			JComboBox b = (JComboBox) getComponentByName("accounts");
			String accountID = (String) b.getSelectedItem();
			String val = ((JTextField) a).getText();

			if (((JRadioButton) c).isSelected()) {

				WithdrawWorker worker = new WithdrawWorker(val, accountID);
				worker.execute();
			} else if (((JRadioButton) c2).isSelected()) {
				DepositWorker worker = new DepositWorker(val, accountID);
				worker.execute();
			} else {
				System.out.println("Please choose to withdraw or deposit an amount.");
			}
		}


	}

	public void propertyChange(PropertyChangeEvent evt) {
		if ("progress" == evt.getPropertyName()) {
			int progress = ((Integer) evt.getNewValue()).intValue();
			// progressBar.setValue(progress);

			if (progress == 100) {
				// executeBtn.setEnabled(true);
			}
		}
	}
}