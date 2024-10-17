package utils;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Validation {
    private final JTextField txtDays;
    private final JTextField txtStartYear;
    private final JTextField txtStopYear;

    public Validation(JTextField txtDays, JTextField txtStartYear, JTextField txtStopYear) {
        this.txtDays = txtDays;
        this.txtStartYear = txtStartYear;
        this.txtStopYear = txtStopYear;
    }

    private   List<String> errors() {
        List<String> errors = new ArrayList<>();
        String day = txtDays.getText().toString().trim();
        String stopYear = txtStopYear.getText().toString().trim();
        String startYear = txtStartYear.getText().toString().trim();

        if (day.isEmpty()) errors.add("Day field is empty");
        if (startYear.isEmpty()) errors.add("Start Year field is empty");
        if (stopYear.isEmpty()) errors.add("Stop Year field is empty");
        if (stopYear.length() < 4) {
            errors.add("Stop Year must be 4 digits");
        }
        if (startYear.length() < 4) {
            errors.add("Start Year must be 4 digits");
        }
        if (day.equals("0")) errors.add("Day cannot be 0. Please enter a day");
        return errors;

    }

    public boolean isFormValid() {
        List<String> errors = errors();
        if (!errors.isEmpty()) {
            StringBuilder sb = new StringBuilder(STR."This form contains the following errors\{System.lineSeparator()}");
            errors.forEach(error -> sb.append(error).append(System.lineSeparator()));
            Helper.showErrorMessage(sb.toString(), "form error");
            return false;
        }
        return true;
    }
}
