import utils.Helper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.FormatStyle;
import java.util.List;

import static utils.Helper.*;

public final class DateForm2 extends JFrame implements ActionListener, KeyListener {
    JButton btnShowDates = new JButton("Show Dates");
    private final DefaultListModel<String> model = new DefaultListModel<>();

    private final JList<String> list = new JList<>(model);

    private final JTextField txtDays = new JTextField(2);
    private final JTextField txtStartYear = new JTextField(3);
    private final JTextField txtStopYear = new JTextField(3);

    JComboBox<String> cbDays = new JComboBox<>(Helper.getDays().toArray(new String[0]));
    JComboBox<String> cbMonths = new JComboBox<>(Helper.getMonths().toArray(new String[0]));


    public DateForm2() {
        list.setPreferredSize(new Dimension(600, 600));
        list.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);

        setResizable(true);
        setLayout(new BorderLayout());
        setSize(700, 300);
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


        top.add(new JLabel("Start Year"));
        top.add(txtStartYear);

        top.add(new JLabel("Stop Year"));
        top.add(txtStopYear);

        middle.add(new JScrollPane(list));
        south.add(btnShowDates);

        add(BorderLayout.NORTH, top);
        add(BorderLayout.CENTER, middle);
        add(BorderLayout.SOUTH, south);


        btnShowDates.addActionListener(this);
        txtDays.addKeyListener(this);
        txtStartYear.addKeyListener(this);
        txtStopYear.addKeyListener(this);

        setVisible(true);
    }


    private boolean isFormValid() {
        List<String> errors = Helper.errors2(txtDays, txtStartYear, txtStopYear);
        if (!errors.isEmpty()) {
            StringBuilder sb = new StringBuilder(STR."This form contains the following errors\{System.lineSeparator()}");
            errors.forEach(error -> sb.append(error).append(System.lineSeparator()));
            Helper.showErrorMessage(sb.toString(), "form error");
            return false;
        }
        return true;
    }


    public static void main() {
        new DateForm2();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnShowDates) {
            if (!isFormValid()) return;

            int day = Integer.parseInt(txtDays.getText());
            DayOfWeek dayOfWeek = DayOfWeek.valueOf((cbDays.getSelectedItem()).toString().toUpperCase());
            Month month = Month.valueOf(cbMonths.getSelectedItem().toString().toUpperCase());
            int startYear = Integer.parseInt(txtStartYear.getText());
            int endYear = Integer.parseInt(txtStopYear.getText());

            if (startYear > endYear) {
                Helper.showErrorMessage("start year cannot exceed stop year", "error");
                return;
            }


            LocalDate startDate = LocalDate.of(startYear, month, day);
            LocalDate endDate = LocalDate.of(endYear, month, day);
            List<LocalDate> dates = getDatesBetweenStartAndEnd(startDate, endDate, dayOfWeek);
            clearList();
            dates.forEach(date -> model.addElement(Helper.formatDate(FormatStyle.FULL, date)));


        }


    }


    private void clearList() {
        list.clearSelection();
        model.clear();
    }


    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getSource() == txtStartYear) validateMaxField(txtStartYear, 3, e);
        if (e.getSource() == txtStopYear) validateMaxField(txtStopYear, 3, e);
        if (e.getSource() == txtDays) validateMaxField(txtDays, 2, e);

    }


    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getSource() == txtDays) validateNumber(txtDays, e);
        if (e.getSource() == txtStartYear) validateNumber(txtStartYear, e);
        if (e.getSource() == txtStopYear) validateNumber(txtStopYear, e);

    }

    @Override
    public void keyReleased(KeyEvent e) {


    }




}

