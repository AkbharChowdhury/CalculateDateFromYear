import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public final class Helper {
    private Helper() {

    }

    public static void showErrorMessage(String message, String title) {
        JOptionPane.showMessageDialog(null, message,
                title, JOptionPane.ERROR_MESSAGE);
    }

    public static List<String> errors(JTextField txtDays, JTextField txtStopYear) {
        List<String> errors = new ArrayList<>();
        String day = txtDays.getText().toString().trim();
        String stopYear = txtStopYear.getText().toString().trim();

        if (day.isEmpty()) errors.add("Day field is empty");
        if (stopYear.isEmpty()) errors.add("Stop Year field is empty");
        if (stopYear.length() < 4) {
            errors.add("Year must be 4 digits");
        }
        if (day.equals("0")) errors.add("Day cannot be 0. Please enter a day");
        return errors;

    }
}
