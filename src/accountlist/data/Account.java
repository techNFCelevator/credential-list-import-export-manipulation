package accountlist.data;

import java.util.ArrayList;
import accountlist.util.Validator;
import accountlist.exceptions.ApplicationException;

public class Account {
    private final String id;
    private final ArrayList<Fare> fares;
    private final String[] profile;

    public Account(String id, String[] profile) throws ApplicationException {
        if(Validator.isValidId(id)) {
            this.id = id;
        } else {
            throw new ApplicationException("Account ID cannot be null or empty.");
        }
        if(Validator.isValidProfile(profile)) {
            this.profile = profile;
        } else {
            throw new ApplicationException("Profile cannot be null or empty.");
        }
        fares = new ArrayList<>();
        fares.add(new StoredValue());
    }

    public Account(String id, String[] profile, double initialBalance) throws ApplicationException {
        if(Validator.isValidId(id)) {
            this.id = id;
        } else {
            throw new ApplicationException("Account ID cannot be null or empty.");
        }
        if(Validator.isValidProfile(profile)) {
            this.profile = profile;
        } else {
            throw new ApplicationException("Profile cannot be null or empty.");
        }
        fares = new ArrayList<>();
        fares.add(new StoredValue(initialBalance));
    }

    public Account(String id, String[] profile, Fare fare) throws ApplicationException {
        if(Validator.isValidId(id)) {
            this.id = id;
        } else {
            throw new ApplicationException("Account ID cannot be null or empty.");
        }
        if(Validator.isValidProfile(profile)) {
            this.profile = profile;
        } else {
            throw new ApplicationException("Profile cannot be null or empty.");
        }
        fares = new ArrayList<>();
        if(!(fare instanceof StoredValue)) {
            fares.add(new StoredValue());
        }
        fares.add(fare);
    }

    public Account(String id, String[] profile, ArrayList<Fare> fares) throws ApplicationException {
        if(Validator.isValidId(id)) {
            this.id = id;
        } else {
            throw new ApplicationException("Account ID cannot be null or empty.");
        }
        if(Validator.isValidProfile(profile)) {
            this.profile = profile;
        } else {
            throw new ApplicationException("Profile cannot be null or empty.");
        }
        this.fares = fares;
        boolean hasStoredValue = false;
        for(Fare fare : fares) {
            if(fare instanceof StoredValue) {
                hasStoredValue = true;
                break;
            }
        }
        if(!hasStoredValue) {
            this.fares.add(new StoredValue());
        }
    }

    public String getId() {
        return this.id;
    }

    public String[] getProfile() {
        return this.profile;
    }

    public ArrayList<Fare> getFares() {
        return this.fares;
    }

    public void addFare(Fare fare) throws ApplicationException {
        if (fare instanceof StoredValue add) {
            int f = 0;
            for (; f < fares.size(); f++) {
                if (fares.get(f) instanceof StoredValue sv) {
                    sv.credit(add.getValue());
                    return; // Already has a StoredValue fare, do not add another
                }
            }
            if (f == fares.size()) {
                this.fares.add(fare);
            }
        } else {
            this.fares.add(fare);
        }
    }
}