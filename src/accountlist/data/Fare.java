package accountlist.data;

import accountlist.exceptions.ApplicationException;

public class Fare {
    protected final String name;

    protected Fare(String input) throws ApplicationException {
        if (input != null && !input.isBlank() && !input.isEmpty()) {
            this.name = input;
        } else {
            throw new ApplicationException("Fare name cannot be null or empty");
        }
    }

    public String getName() {
        return this.name;
    }
}
