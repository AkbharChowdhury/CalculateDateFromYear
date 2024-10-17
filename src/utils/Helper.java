package utils;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public final class Helper {
    private Helper() {

    }

    public static void showErrorMessage(String message, String title) {
        JOptionPane.showMessageDialog(null, message,
                title, JOptionPane.ERROR_MESSAGE);
    }
    public static void validateNumber(JTextField field, KeyEvent e) {
        //  link https://www.youtube.com/watch?v=cdPKsws5f-4
        field.setEditable(!Character.isLetter(e.getKeyChar()));

    }







    public static List<String> getMonths() {
        return Arrays.stream(Month.values())
                .map(dow -> dow.getDisplayName(getTextStyle(), Locale.UK))
                .toList();
    }
    private static TextStyle getTextStyle() {
        return TextStyle.FULL;

    }

    public static List<String> getDays() {
        return Arrays.stream(DayOfWeek.values())
                .map(dow -> dow.getDisplayName(getTextStyle(), Locale.UK))
                .toList();
    }
    public static void validateMaxField(JTextField field, int maxLength, KeyEvent e) {
        if (field.getText().length() > maxLength) e.consume();


    }

    public static String formatDate(FormatStyle formatStyle, LocalDate date){
       return DateTimeFormatter.ofLocalizedDate(formatStyle).format(date);
    }


    public static List<LocalDate> getDatesBetweenStartAndEnd(
            LocalDate startDate,
            LocalDate endDate,
            DayOfWeek dow
    ) {

        return startDate.datesUntil(endDate)
                .filter(date -> date.getDayOfWeek() == dow
                        && date.getMonth() == startDate.getMonth()
                        && date.getDayOfMonth() == startDate.getDayOfMonth())
                .collect(Collectors.toList());
    }
}
