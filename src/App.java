
import java.awt.EventQueue;
import java.io.File;
import java.util.TreeMap;

import accountlist.data.Account;
import accountlist.data.Fare;
import accountlist.data.StoredValue;
import accountlist.exceptions.ApplicationException;
import accountlist.io.Exporter;
import accountlist.io.Reader;
import accountlist.ui.MainWindow;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Importing accounts...");
        TreeMap<String,Account> accounts = new TreeMap<String,Account>();
        accounts.putAll(Reader.importAccounts(new File("input.txt")));
        String keyStr = "1010234598764765";
        System.out.println("Import successful. Obtaining account with ID " + keyStr + "...");
        Account account = accounts.get(keyStr);
        double amount = 25.00;
        StoredValue storedValue = null;
        for (Fare fare : account.getFares()) {
            if (fare instanceof StoredValue sv) {
                storedValue = sv;
                break;
            }
        }
        if (storedValue == null) {
            throw new ApplicationException("No Stored Value fare found for account ID " + keyStr);
        }
        System.out.println("Crediting $" + amount + "...");
        storedValue.credit(amount);
        System.out.println("New balance: $" + storedValue.getValue());
        amount = 10.00;
        System.out.println("Debiting $" + amount + "...");
        storedValue.debit(amount);
        System.out.println("New balance: $" + storedValue.getValue());
        System.out.println("Creating new account...");
        keyStr = "2021345678901234";
        Account newAcct = new Account(keyStr, new String[]{"General"}, 50.00);
        System.out.println("Adding new account to list...");
        accounts.put(newAcct.getId(), newAcct);
        keyStr = "1010234598764765";
        System.out.println("New account added. Now removing account with ID " + keyStr + "...");
        accounts.remove(keyStr);
        System.out.println("Exporting accounts...");
        Exporter.exportList(accounts, new File("output.txt"));
        System.out.println("Export complete. Exporting accounts in formatted style...");
        Exporter.exportFormattedList(accounts, new File("formatted_output.txt"));
        System.out.println("Formatted export complete.");
        EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
    }
}
