

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.format.TextStyle;
import java.util.*;
import java.util.List;

import static java.lang.StringTemplate.STR;


public final class DateForm extends JFrame implements ActionListener, KeyListener {
    JButton button = new JButton("Show Dates");
    private final DefaultListModel<String> model = new DefaultListModel<>();

    private final JList<String> list = new JList<>(model);

    JTextField txtDays = new JTextField(2);
    JTextField txtStopYear = new JTextField(3);

    JComboBox<String> cbDays = new JComboBox<>(getDays().toArray(new String[0]));
    JComboBox<String> cbMonths = new JComboBox<>(getMonths().toArray(new String[0]));

    private static TextStyle getTextStyle() {
        return TextStyle.FULL;

    }

    public static List<String> getMonths() {
        return Arrays.stream(Month.values())
                .map(dow -> dow.getDisplayName(getTextStyle(), Locale.UK))
                .toList();
    }

    public static List<String> getDays() {
        return Arrays.stream(DayOfWeek.values())
                .map(dow -> dow.getDisplayName(getTextStyle(), Locale.UK))
                .toList();
    }

    public DateForm() {
        list.setPreferredSize(new Dimension(600, 600));

        list.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);

        setResizable(false);
        setLayout(new BorderLayout());
        setSize(500, 300);
        setTitle("Dates application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel top = new JPanel();
        JPanel middle = new JPanel();
        JPanel south = new JPanel();

        top.add(new JLabel("Day"));
        top.add(txtDays);
        top.add(cbDays);
        top.add(cbMonths);
        top.add(new JLabel("Stop Year"));
        top.add(txtStopYear);
        txtDays.addKeyListener(this);
        txtStopYear.addKeyListener(this);


        south.add(button);
        middle.add(new JScrollPane(list));

        add(BorderLayout.NORTH, top);
        add(BorderLayout.CENTER, middle);
        add(BorderLayout.SOUTH, south);


        button.addActionListener(this);


        setVisible(true);
    }


    public static void main(String[] args) {
        new DateForm();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            List<String> errors = Helper.errors(txtDays, txtStopYear);
            if (!errors.isEmpty()) {
                StringBuilder sb = new StringBuilder(STR."This form contains the following errors\{System.lineSeparator()}");
                errors.forEach(error -> sb.append(error).append(System.lineSeparator()));
                Helper.showErrorMessage(sb.toString(), "form error");
                return;

            }

            int currentYear = LocalDate.now().getYear();
            int stopYear = Integer.parseInt(txtStopYear.getText().toString());
            if (stopYear > currentYear) {
                Helper.showErrorMessage(STR."The stop year must be less less than or equal to \{currentYear}", "Stop year error");
                txtStopYear.grabFocus();
                return;
            }


            try {
                Month month = Month.valueOf(Objects.requireNonNull(cbMonths.getSelectedItem()).toString().toUpperCase(Locale.ENGLISH));
                DayOfWeek dow = DayOfWeek.valueOf(Objects.requireNonNull(cbDays.getSelectedItem()).toString().toUpperCase(Locale.ENGLISH));
                int day = Integer.parseInt(txtDays.getText().toString());
                var date = LocalDate.of(LocalDate.now().getYear(), month, day);
                displayDates(date, stopYear - 1, dow);

            } catch (DateTimeException ex) {
                System.err.println(ex.getMessage());

            }


        }


    }

    private void displayDates(LocalDate date, int stopYear, DayOfWeek dow) {
        List<LocalDate> dates = new ArrayList<>();
        while (date.getYear() != stopYear) {
            if (date.getDayOfWeek() == dow) {
                dates.add(date);
                System.out.println(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).format(date));
            }
            date = date.minusYears(1);
        }
        if (dates.isEmpty()) {
            Helper.showErrorMessage("There are no dates found", "Dates error");
            return;
        }
        clearList();
        dates.forEach(d -> model.addElement(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).format(d)));

    }

    private void clearList() {
        list.clearSelection();
        model.clear();
    }


    @Override
    public void keyTyped(KeyEvent e) {

        if (e.getSource() == txtStopYear) validateMaxField(txtStopYear, 3, e);
        if (e.getSource() == txtDays) validateMaxField(txtDays, 2, e);

    }

    private void validateMaxField(JTextField field, int maxLength, KeyEvent e) {
        if (field.getText().length() > maxLength) e.consume();


    }

    private void validateNumber(JTextField field, KeyEvent e) {
        //  link https://www.youtube.com/watch?v=cdPKsws5f-4
        field.setEditable(!Character.isLetter(e.getKeyChar()));

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getSource() == txtDays) validateNumber(txtDays, e);
        if (e.getSource() == txtStopYear) validateNumber(txtStopYear, e);


    }

    @Override
    public void keyReleased(KeyEvent e) {


    }


}

