import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

public class DateUtil {
    public static void main(String[] args) {
        Date date = new Date();
        System.out.println(date);
        System.out.println(System.currentTimeMillis());

        LocalDate date1 = LocalDate.now();
        System.out.println(date1);
        System.out.println("年-月-日" + date1.getYear() + "-" + date1.getMonth() + "-" + date1.getDayOfWeek());

        LocalTime time = LocalTime.now();
        System.out.println("time:" + time);

        LocalDateTime dateTime = LocalDateTime.now();
        System.out.println("dateTime:" + dateTime);
    }
}
