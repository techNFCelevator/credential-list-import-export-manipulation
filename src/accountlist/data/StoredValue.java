package accountlist.data;

import accountlist.exceptions.ApplicationException;
import accountlist.util.Validator;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class StoredValue extends Fare {
    private double value;
    public static final String NAME = "Stored Value";
    public static final double MIN_VALUE = 0.00;
    public static final double MAX_VALUE = 500.00;
    public static final String ERROR_MSG_VALUE = NAME + " must be between $" + MIN_VALUE + " and $" + MAX_VALUE;
    public static final int DECIMAL_PLACES = 2;
    public StoredValue() throws ApplicationException {
        super(NAME);
        if(Validator.isValidStoredValue(MIN_VALUE)) {
            setValue(MIN_VALUE);
        } else {
            throw new ApplicationException(ERROR_MSG_VALUE);
        }
    }

    public StoredValue(double input) throws ApplicationException {
        super(NAME);
        if(Validator.isValidStoredValue(input)) {
            setValue(input);
        } else {
            throw new ApplicationException(ERROR_MSG_VALUE);
        }
    }

    public double getValue() {
        return this.value;
    }

    public void credit(double input) throws ApplicationException {
        if (input < 0) {
            input = -input;
        }
        BigDecimal bd = new BigDecimal(Double.toString(input)).setScale(DECIMAL_PLACES, RoundingMode.HALF_UP);
        if(Validator.isValidStoredValue(this.value + bd.doubleValue())) {
            setValue(this.value + bd.doubleValue());
        } else {
            System.err.println("Credit operation by $" + input + " would exceed maximum stored value.");
        }
    }

    public void debit(double input) throws ApplicationException {
        if (input < 0) {
            input = -input;
        }
        BigDecimal bd = new BigDecimal(Double.toString(input)).setScale(DECIMAL_PLACES, RoundingMode.HALF_UP);
        if(Validator.isValidStoredValue(this.value - bd.doubleValue())) {
            setValue(this.value - bd.doubleValue());
        } else {
            System.err.println("Debit operation by $" + input + " results in negative balance.");
        }
    }

    private void setValue(double input) {
        BigDecimal bd = new BigDecimal(Double.toString(input)).setScale(DECIMAL_PLACES, RoundingMode.HALF_UP);
        if (Validator.isValidStoredValue(bd.doubleValue())) {
            this.value = bd.doubleValue();
        }
    }
}
