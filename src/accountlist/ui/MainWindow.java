package accountlist.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import accountlist.data.Account;
import accountlist.exceptions.ApplicationException;
import accountlist.io.Reader;
import net.miginfocom.swing.MigLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.TreeMap;
import java.awt.event.ActionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private final TreeMap<String,Account> accounts = new TreeMap<String,Account>();

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		setTitle("Account list manipulator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 424, 294);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[][][]", "[][grow]"));
		
		DefaultListModel<String> idList = new DefaultListModel<>();
		
		JButton btnNewButton = new JButton("Import from file");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser importer = new JFileChooser();
				importer.setCurrentDirectory(new File(System.getProperty("user.home")));
				importer.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
				importer.setDialogTitle("Please select file to load");
				if (importer.showOpenDialog(getParent()) == JFileChooser.APPROVE_OPTION) {
					try {
						accounts.putAll(Reader.importAccounts(importer.getSelectedFile()));
						idList.addAll(accounts.keySet());
					} catch (FileNotFoundException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(), getTitle(), ERROR);
						e1.printStackTrace();
					} catch (ApplicationException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(), getTitle(), ERROR);
						e1.printStackTrace();
					}
				}
			}
		});
		contentPane.add(btnNewButton, "cell 0 0");
		
		JButton btnNewButton_1 = new JButton("Export to file");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							ExportOptions dialog = new ExportOptions(accounts);
							dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
							dialog.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		
		JButton btnNewButton_2 = new JButton("Create new account");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							AccountWizard dialog = new AccountWizard(null, accounts);
							dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
							dialog.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		contentPane.add(btnNewButton_2, "cell 1 0");
		contentPane.add(btnNewButton_1, "cell 2 0");
		
		JList<String> list = new JList<>(idList);
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(!e.getValueIsAdjusting()) {
					AccountWizard editAccount = new AccountWizard(list.getSelectedValue(), accounts);
					editAccount.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
					editAccount.setVisible(true);
				}
			}
		});
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		contentPane.add(list, "cell 0 1 3 1,grow");

	}

}
