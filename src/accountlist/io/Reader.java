package accountlist.io;

import java.util.Scanner;

import accountlist.data.Account;
import accountlist.data.Fare;
import accountlist.data.StoredValue;
import accountlist.exceptions.ApplicationException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

@SuppressWarnings("resource")
public class Reader {
    public static TreeMap<String,Account> importAccounts(File file) throws ApplicationException, FileNotFoundException {
        Scanner scanner = new Scanner(file);
        TreeMap<String,Account> accounts = new TreeMap<>();
        String[] format = parseAccount(scanner.nextLine());
        String[] rawAccount;
        String[] rawFare;
        HashMap<String, String> accountData = new HashMap<>();
        Account account;
        while(scanner.hasNext()) {
            try {
                rawAccount = parseAccount(scanner.nextLine());
                for (int a = 0; a < format.length; a++) {
                    accountData.put(format[a], rawAccount[a]);
                }
                ArrayList<Fare> fares = new ArrayList<>();
                for (String fare : parseFares(accountData.get("FARES"))) {
                    rawFare = parseFare(fare);
                    if (rawFare[0].equalsIgnoreCase("Stored Value")) {
                        int f = 0;
                        for (; f < fares.size(); f++) {
                            if (fares.get(f) instanceof StoredValue sv) {
                                if (Double.parseDouble(rawFare[1]) < 0) {
                                    sv.debit(Double.parseDouble(rawFare[1]));
                                } else if (Double.parseDouble(rawFare[1]) > 0) {
                                    sv.credit(Double.parseDouble(rawFare[1]));
                                }
                                break;
                            }
                        }
                        if(f == fares.size()) {
                            fares.add(new StoredValue(Double.parseDouble(rawFare[1])));
                        }
                    }
                }
                account = new Account(accountData.get("ID"), parseProfile(accountData.get("PROFILE")), fares);
                accounts.put(account.getId(), account);
                accountData.clear();
            } catch (ApplicationException e) {
                System.err.println("Error importing account: " + e.getMessage());
            }
        }
        return accounts;
    }
    public static String[] parseAccount(String profileStr) throws ApplicationException {
        String[] output = profileStr.split("[|]");
        if (output.length == 3) {
            return output;
        } else {
            throw new ApplicationException("Profile string is not in the correct format.");
        }
    }
    public static String[] parseFares(String faresStr) throws ApplicationException {
        return faresStr.split("[,]");
    }
    public static String[] parseFare(String fareStr) throws ApplicationException {
        return fareStr.split("[:]");
    }
    public static String[] parseProfile(String profileStr) throws ApplicationException {
        return profileStr.split("[:]");
    }
}
