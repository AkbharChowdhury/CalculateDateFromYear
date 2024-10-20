import utils.Helper;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.stream.Collectors;

public static void main() {
    Month month = Month.NOVEMBER;
    int day = 13;
    LocalDate startDate = LocalDate.of(1999, month, day);
    LocalDate endDate = LocalDate.of(2030, month, day);
    var dow = DayOfWeek.MONDAY;
    var dates = getDatesBetweenStartAndEnd(startDate, endDate, dow);
    dates.forEach(date -> System.out.println(Helper.formatDate(FormatStyle.FULL, date)));

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
