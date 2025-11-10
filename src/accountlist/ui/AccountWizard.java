package accountlist.ui;

import javax.swing.JDialog;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import accountlist.data.Account;
import accountlist.data.Fare;
import accountlist.data.StoredValue;
import accountlist.exceptions.ApplicationException;

import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.text.MaskFormatter;

import java.util.Date;
import java.util.Map;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JList;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFormattedTextField;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class AccountWizard extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTextField firstNameField;
	private final JTextField lastNameField;
	private final ButtonGroup fareCategoryTypeGroup = new ButtonGroup();
	private final JLabel dobFieldLbl;
	private final JSpinner dateField;
	private JLabel fareTypeFieldLbl;
	private JList<String> faresList;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private JButton btnNewButton_2;
	private JButton btnNewButton_3;
	private JFormattedTextField idField;

	/**
	 * Create the dialog.
	 */
	public AccountWizard(String key, Map<String,Account> list) {
		firstNameField = new JTextField();
		lastNameField = new JTextField();
		dobFieldLbl = new JLabel("Date Of Birth");
		dateField = new JSpinner();
		ArrayList<Fare> newFares = new ArrayList<>();
		if (key != null) {
			setTitle("Edit Account " + key);
			newFares.addAll(list.get(key).getFares());
		} else {
			setTitle("Create New Account");
		}
		setModal(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
		
		JLabel idFieldLbl = new JLabel("ID");
		getContentPane().add(idFieldLbl, "2, 2, right, default");
		
		try {
			idField = new JFormattedTextField(new MaskFormatter("################"));
			idField.addPropertyChangeListener(new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent evt) {
				}
			});
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), getTitle(), ERROR);
			e.printStackTrace();
		}
		if (key != null) {
			idField.setText(key);
		}
		idField.setEnabled(key == null);
		getContentPane().add(idField, "4, 2, 7, 1, fill, default");
		
		fareTypeFieldLbl = new JLabel("Type");
		getContentPane().add(fareTypeFieldLbl, "2, 4, right, default");
		
		JRadioButton generalFareOption = new JRadioButton("General");
		generalFareOption.setEnabled(key == null);
		if (key != null) {
			generalFareOption.setSelected(list.get(key).getProfile()[0].equalsIgnoreCase(generalFareOption.getText()));
		}
		fareCategoryTypeGroup.add(generalFareOption);
		getContentPane().add(generalFareOption, "4, 4");
		
		JRadioButton concessionFareOption = new JRadioButton("Concession");
		concessionFareOption.setEnabled(key == null);
		if (key != null) {
			concessionFareOption.setSelected(list.get(key).getProfile()[0].equalsIgnoreCase(concessionFareOption.getText()));
		}
		fareCategoryTypeGroup.add(concessionFareOption);
		getContentPane().add(concessionFareOption, "6, 4");
		
		JRadioButton personalizedFareOption = new JRadioButton("Personalized");
		personalizedFareOption.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				firstNameField.setEnabled(e.getStateChange() == ItemEvent.SELECTED);
				lastNameField.setEnabled(e.getStateChange() == ItemEvent.SELECTED);
				dobFieldLbl.setEnabled(e.getStateChange() == ItemEvent.SELECTED);
				dateField.setEnabled(e.getStateChange() == ItemEvent.SELECTED);
			}
		});
		if (key != null) {
			personalizedFareOption.setSelected(list.get(key).getProfile()[0].equalsIgnoreCase(personalizedFareOption.getText()));
		}
		personalizedFareOption.setEnabled(key == null);
		fareCategoryTypeGroup.add(personalizedFareOption);
		getContentPane().add(personalizedFareOption, "8, 4");
		
		if (key != null && personalizedFareOption.isSelected()) {
			firstNameField.setText(list.get(key).getProfile()[1]);
		}
		firstNameField.setEnabled(key == null && personalizedFareOption.isSelected());
		getContentPane().add(firstNameField, "4, 6, fill, default");
		firstNameField.setColumns(10);
		
		if (key != null && personalizedFareOption.isSelected()) {
			lastNameField.setText(list.get(key).getProfile()[2]);
		}
		lastNameField.setEnabled(key == null && personalizedFareOption.isSelected());
		getContentPane().add(lastNameField, "6, 6, fill, default");
		lastNameField.setColumns(10);
		
		String dateFormat = "yyyy-MM-dd";
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
				
		dobFieldLbl.setEnabled(key == null && personalizedFareOption.isSelected());
		getContentPane().add(dobFieldLbl, "8, 6, right, default");
		
		dobFieldLbl.setLabelFor(dateField);
		dateField.setModel(new SpinnerDateModel(new Date(1762675200000L), null, null, Calendar.DAY_OF_MONTH));
		dateField.setEditor(new JSpinner.DateEditor(dateField, dateFormat));
		dateField.setEnabled(key == null && personalizedFareOption.isSelected());
		if (key != null && personalizedFareOption.isSelected()) {
			try {
				dateField.setValue(formatter.parse(list.get(key).getProfile()[3]));
			} catch (ParseException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), getTitle(), ERROR);
				e.printStackTrace();
			}
		}
		getContentPane().add(dateField, "10, 6");
		
		JLabel faresListLbl = new JLabel("Fares");
		getContentPane().add(faresListLbl, "2, 8, right, fill");
		
		faresList = new JList<>();
		getContentPane().add(faresList, "4, 8, 5, 3, fill, fill");
		
		btnNewButton_2 = new JButton("Add Fare");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		getContentPane().add(btnNewButton_2, "10, 8");
		
		btnNewButton = new JButton("SAVE");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (key == null) {
					Account newAccount;
					ButtonModel optionSelected = fareCategoryTypeGroup.getSelection();
					if (!list.containsKey(idField.getText())) {
						if (personalizedFareOption.isSelected()) {
							try {
								newAccount = new Account(idField.getText(), new String[] {optionSelected.getActionCommand(), firstNameField.getText(), lastNameField.getText(), formatter.format(dateField.getValue())}, newFares);
							} catch (ApplicationException e1) {
								JOptionPane.showMessageDialog(null, e1.getMessage(), getTitle(), ERROR);
								e1.printStackTrace();
							}
						} else {
							try {
								newAccount = new Account(idField.getText(), new String[] {optionSelected.getActionCommand()}, newFares);
							} catch (ApplicationException e1) {
								JOptionPane.showMessageDialog(null, e1.getMessage(), getTitle(), ERROR);
								e1.printStackTrace();
							}
						}
					} else {
						JOptionPane.showMessageDialog(null, "ID already exists, please enter a different ID.", getTitle(), ABORT);
					}
				} else {
					int f = 0;
					if (list.get(key).getFares().size() <= newFares.size()) {
						for (; f < list.get(key).getFares().size(); f++) {
							if (list.get(key).getFares().get(f) instanceof StoredValue && newFares.get(f) instanceof StoredValue) {
								StoredValue existingSV = (StoredValue) list.get(key).getFares().get(f);
								StoredValue newSV = (StoredValue) newFares.get(f);
								if (newSV.getValue() < existingSV.getValue()) {
									try {
										existingSV.debit(existingSV.getValue() - newSV.getValue());
									} catch (ApplicationException e1) {
										JOptionPane.showMessageDialog(null, e1.getMessage(), getTitle(), ERROR);
										e1.printStackTrace();
									}
								} else if (newSV.getValue() > existingSV.getValue()) {
									try {
										existingSV.credit(newSV.getValue() - existingSV.getValue());
									} catch (ApplicationException e1) {
										JOptionPane.showMessageDialog(null, e1.getMessage(), getTitle(), ERROR);
										e1.printStackTrace();
									}
								}
							}
						}
						for (; f < newFares.size(); f++) {
							try {
								list.get(key).addFare(newFares.get(f));
							} catch (ApplicationException e1) {
								JOptionPane.showMessageDialog(null, e1.getMessage(), getTitle(), ERROR);
								e1.printStackTrace();
							}
						}
					} else {
						for (; f < newFares.size(); f++) {
							if (list.get(key).getFares().get(f) instanceof StoredValue && newFares.get(f) instanceof StoredValue) {
								StoredValue existingSV = (StoredValue) list.get(key).getFares().get(f);
								StoredValue newSV = (StoredValue) newFares.get(f);
								if (newSV.getValue() < existingSV.getValue()) {
									try {
										existingSV.debit(existingSV.getValue() - newSV.getValue());
									} catch (ApplicationException e1) {
										JOptionPane.showMessageDialog(null, e1.getMessage(), getTitle(), ERROR);
										e1.printStackTrace();
									}
								} else if (newSV.getValue() > existingSV.getValue()) {
									try {
										existingSV.credit(newSV.getValue() - existingSV.getValue());
									} catch (ApplicationException e1) {
										JOptionPane.showMessageDialog(null, e1.getMessage(), getTitle(), ERROR);
										e1.printStackTrace();
									}
								}
							}
						}
						while (newFares.size() < list.get(key).getFares().size()) {
							list.get(key).getFares().removeLast();
						}
					}
				}
			}
		});
		
		btnNewButton_3 = new JButton("Remove Fare");
		getContentPane().add(btnNewButton_3, "10, 9");
		getContentPane().add(btnNewButton, "4, 12");
		
		btnNewButton_1 = new JButton("DELETE");
		btnNewButton_1.setEnabled(key == null);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				list.remove(key);
			}
		});
		getContentPane().add(btnNewButton_1, "10, 12");

	}

}
