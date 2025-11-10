package accountlist.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Map;
import java.util.TreeMap;

import accountlist.data.Account;
import accountlist.data.StoredValue;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;

public class Exporter {    
    public static void exportFormattedList(Map<String,Account> accounts, File output) throws FileNotFoundException, IOException {
        if(accounts != null && !accounts.isEmpty()) {
            int idSize = 0;
            int profileSize = 0;
            int faresSize = 0;
            String col1Str = "ID";
            String col2Str = "Profile";
            String col3Str = "Fares";
            FileOutputStream fileOutputer = new FileOutputStream(output);
            PrintStream printer = new PrintStream(fileOutputer);
            TreeMap<Integer,TreeMap<String,String>> results = new TreeMap<>();
            int line = 1;
            for(Map.Entry<String,Account> entry : accounts.entrySet()) {
                Account account = entry.getValue();
                String fares = "";
                for(int i = 0; i < account.getFares().size(); i++) {
                    fares += (account.getFares().get(i).getName() + ":");
                    if(account.getFares().get(i) instanceof StoredValue sv) {
                        BigDecimal bd = new BigDecimal(Double.toString(sv.getValue())).setScale(StoredValue.DECIMAL_PLACES, RoundingMode.HALF_UP);
                        fares += bd.toString();
                    }
                    if(i < account.getFares().size() - 1) {
                        fares += ",";
                    }
                }
                String profile = "";
                for(int i = 0; i < account.getProfile().length; i++) {
                    profile += account.getProfile()[i];
                    if(i < account.getProfile().length - 1) {
                        profile += ":";
                    }
                }
                if(account.getId().length() > idSize) {
                    idSize = account.getId().length();
                }
                if(profile.length() > profileSize) {
                    profileSize = profile.length();
                }
                if(fares.length() > faresSize) {
                    faresSize = fares.length();
                }
                TreeMap<String,String> printout = new TreeMap<>();
                printout.put(col1Str,account.getId());
                printout.put(col2Str,profile);
                printout.put(col3Str,fares);
                results.put(line, printout);
                line++;
            }
            final int borderSize = idSize + 1 + profileSize + 1 + faresSize;
            final String format = "%-" + idSize + "s %-" + profileSize + "s %-" + faresSize + "s";
            printer.format(format, col1Str, col2Str, col3Str);
            printer.println();
            printer.println("-".repeat(borderSize));
            Iterator<Map.Entry<Integer,TreeMap<String,String>>> listIterator = results.entrySet().iterator();
            while(listIterator.hasNext()) {
                Map.Entry<Integer,TreeMap<String,String>> lineEntry = listIterator.next();
                printer.format(format, lineEntry.getValue().get(col1Str), lineEntry.getValue().get(col2Str), lineEntry.getValue().get(col3Str));
                if(listIterator.hasNext()) {
                    printer.println();
                }
            }
            printer.close();
            fileOutputer.close();
        }
    }
    public static void exportList(Map<String,Account> accounts, File output) throws FileNotFoundException, IOException {
        if(accounts != null && !accounts.isEmpty()) {
            FileOutputStream fileOutput = new FileOutputStream(output);
            PrintStream printer = new PrintStream(fileOutput);
            final String format = "%s|%s|%s";
            printer.format(format, "ID", "FARES", "PROFILE");
            for (Map.Entry<String,Account> entry : accounts.entrySet()) {
                printer.println();
                Account account = entry.getValue();
                String fares = "";
                for(int i = 0; i < account.getFares().size(); i++) {
                    fares += (account.getFares().get(i).getName() + ":");
                    if(account.getFares().get(i) instanceof StoredValue sv) {
                        fares += sv.getValue();
                    }
                    if(i < account.getFares().size() - 1) {
                        fares += ",";
                    }
                }
                String profile = "";
                for(int i = 0; i < account.getProfile().length; i++) {
                    profile += account.getProfile()[i];
                    if(i < account.getProfile().length - 1) {
                        profile += ":";
                    }
                }
                printer.format(format, account.getId(), fares, profile);
            }
            printer.close();
            fileOutput.close();
        }
    }
}
