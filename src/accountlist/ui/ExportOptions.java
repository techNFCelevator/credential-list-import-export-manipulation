package accountlist.ui;

import javax.swing.JDialog;
import javax.swing.JFileChooser;

import net.miginfocom.swing.MigLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.filechooser.FileNameExtensionFilter;

import accountlist.data.Account;
import accountlist.io.Exporter;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.awt.event.ActionEvent;

public class ExportOptions extends JDialog {

	private static final long serialVersionUID = 1L;
	private static final String saveOptionViewStr = "exportFormattedList";
	private static final String saveOptionMachineReadStr = "exportList";
	private final ButtonGroup saveFormatOptions = new ButtonGroup();

	/**
	 * Create the dialog.
	 */
	public ExportOptions(Map<String, Account> list) {
		setTitle("Save list");
		setModal(true);
		setBounds(100, 100, 218, 163);
		getContentPane().setLayout(new MigLayout("", "[]", "[][][][]"));
		
		JLabel lblNewLabel = new JLabel("How would you like to save list?");
		getContentPane().add(lblNewLabel, "cell 0 0,alignx center,aligny center");
		
		JButton saveBtn = new JButton("SAVE");
		saveBtn.setEnabled(false);
		saveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser exporter = new JFileChooser();
				exporter.setCurrentDirectory(exporter.getCurrentDirectory());
				exporter.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
				if (saveFormatOptions.getSelection().getActionCommand() == saveOptionViewStr) {
					String title = "Viewable list export";
					exporter.setDialogTitle("Please specify where to save the viewable list");
					if (exporter.showSaveDialog(getParent()) == JFileChooser.APPROVE_OPTION) {
						try {
							Exporter.exportFormattedList(list, exporter.getSelectedFile());
						} catch (FileNotFoundException e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage(), title, ERROR);
							e1.printStackTrace();
						} catch (IOException e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage(), title, ERROR);
							e1.printStackTrace();
						}
						dispose();
					}
				} else if (saveFormatOptions.getSelection().getActionCommand() == saveOptionMachineReadStr) {
					String title = "Machine-readable list export";
					exporter.setDialogTitle("Please specify where to save the list");
					if (exporter.showSaveDialog(getParent()) == JFileChooser.APPROVE_OPTION) {
						try {
							Exporter.exportList(list, exporter.getSelectedFile());
						} catch (FileNotFoundException e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage(), title, ERROR);
							e1.printStackTrace();
						} catch (IOException e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage(), title, ERROR);
							e1.printStackTrace();
						}
						dispose();
					}
				} else {
					System.err.println("Save button currently works without selecting an option due to saveFormatOptions.getSelection().getActionCommand() == " + saveFormatOptions.getSelection().getActionCommand());
				}
			}
		});
		getContentPane().add(saveBtn, "cell 0 3,alignx center,aligny center");
		
		JRadioButton viewing = new JRadioButton("For viewing");
		viewing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveBtn.setEnabled(true);
			}
		});
		getContentPane().add(viewing, "cell 0 1");
		viewing.setActionCommand(saveOptionViewStr);
		saveFormatOptions.add(viewing);
		
		JRadioButton machineReadable = new JRadioButton("Machine-readable");
		machineReadable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveBtn.setEnabled(true);
			}
		});
		getContentPane().add(machineReadable, "cell 0 2");
		machineReadable.setActionCommand(saveOptionMachineReadStr);
		saveFormatOptions.add(machineReadable);
	}

}
