import utils.Helper;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static utils.Helper.validateMaxField;
import static utils.Helper.validateNumber;

public final class DateForm extends JFrame implements ActionListener, KeyListener {
    JButton button = new JButton("Show Dates");
    private final DefaultListModel<String> model = new DefaultListModel<>();

    private final JList<String> list = new JList<>(model);

    private final JTextField txtDays = new JTextField(2);
    private final JTextField txtStopYear = new JTextField(3);

    JComboBox<String> cbDays = new JComboBox<>(Helper.getDays().toArray(new String[0]));
    JComboBox<String> cbMonths = new JComboBox<>(Helper.getMonths().toArray(new String[0]));


    public DateForm() {
        list.setPreferredSize(new Dimension(600, 600));
        list.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);

        setResizable(false);
        setLayout(new BorderLayout());
        setSize(500, 300);
        setTitle("Dates Application");
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

        middle.add(new JScrollPane(list));
        south.add(button);

        add(BorderLayout.NORTH, top);
        add(BorderLayout.CENTER, middle);
        add(BorderLayout.SOUTH, south);


        button.addActionListener(this);
        txtDays.addKeyListener(this);
        txtStopYear.addKeyListener(this);


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

            int maxStopYear = LocalDate.now().getYear();
            int stopYear = Integer.parseInt(txtStopYear.getText().toString());
            if (stopYear > maxStopYear) {
                Helper.showErrorMessage(STR."The stop year must be less less than or equal to \{maxStopYear}", "Stop year error");
                txtStopYear.grabFocus();
                return;
            }


            try {
                Month month = Month.valueOf(Objects.requireNonNull(cbMonths.getSelectedItem()).toString().toUpperCase(Locale.ENGLISH));
                DayOfWeek dow = DayOfWeek.valueOf(Objects.requireNonNull(cbDays.getSelectedItem()).toString().toUpperCase(Locale.ENGLISH));
                int day = Integer.parseInt(txtDays.getText().toString());
                var date = LocalDate.of(maxStopYear, month, day);
                displayDates(date, stopYear - 1, dow);

            } catch (DateTimeException ex) {
                Helper.showErrorMessage(ex.getMessage(), "Date Error");

            }


        }


    }

    private void displayDates(LocalDate date, int stopYear, DayOfWeek dow) {
        List<LocalDate> dates = new ArrayList<>();
        while (date.getYear() != stopYear) {
            if (date.getDayOfWeek() == dow) {
                dates.add(date);
            }
            date = date.minusYears(1);
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


    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getSource() == txtDays) validateNumber(txtDays, e);
        if (e.getSource() == txtStopYear) validateNumber(txtStopYear, e);


    }

    @Override
    public void keyReleased(KeyEvent e) {


    }


}

