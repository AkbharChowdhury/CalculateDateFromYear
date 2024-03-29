import javax.swing.*;
import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.format.TextStyle;
import java.util.*;


public class Main {
    public static void main(String[] args) {
        try {
            int day = Integer.parseInt(JOptionPane.showInputDialog("Enter a number between 1-31"));
            JComboBox<String> cbDays = showComboBox(getDays(), "select a day");
            JComboBox<String> cbMonths = showComboBox(getMonths(), "select a month");
            int stopYear = Integer.parseInt(JOptionPane.showInputDialog("Enter a stop year"));

            Month month = Month.valueOf(Objects.requireNonNull(cbMonths.getSelectedItem()).toString().toUpperCase(Locale.ENGLISH));
            DayOfWeek dow = DayOfWeek.valueOf(Objects.requireNonNull(cbDays.getSelectedItem()).toString().toUpperCase(Locale.ENGLISH));
            var date = LocalDate.of(LocalDate.now().getYear(), month, day);
            displayDates(date, stopYear - 1, dow);


        } catch (DateTimeException e) {
            System.err.println(e.getMessage());

        }


    }


    private static JComboBox<String> showComboBox(List<String> list, String title) {

        JComboBox<String> comboBox = new JComboBox<>(list.toArray(new String[0]));
        comboBox.setSelectedIndex(0);
        JOptionPane.showMessageDialog(null, comboBox, title, JOptionPane.QUESTION_MESSAGE);
        return comboBox;

    }


    private static void displayDates(LocalDate date, int stopYear, DayOfWeek dow) {
        List<LocalDate> dates = new ArrayList<>();
        while (date.getYear() != stopYear) {
            if (date.getDayOfWeek() == dow) {
                dates.add(date);
            }
            date = date.minusYears(1);
        }
        if (dates.isEmpty()) {
            System.err.println("There are no dates found");
            return;
        }

        dates.forEach(d -> System.out.println(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).format(d)));

    }


    public static List<String> getDays() {
        return Arrays.stream(DayOfWeek.values())
                .map(dow -> dow.getDisplayName(getTextStyle(), Locale.UK))
                .toList();
    }

    public static List<String> getMonths() {
        return Arrays.stream(Month.values())
                .map(dow -> dow.getDisplayName(getTextStyle(), Locale.UK))
                .toList();
    }


    private static TextStyle getTextStyle() {
        return TextStyle.FULL;

    }
}
