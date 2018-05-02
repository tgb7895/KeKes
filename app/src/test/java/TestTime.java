import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TestTime {

    @Test
    public void s2() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, +30);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss", Locale.CHINA);

        System.out.println(formatter.format(cal.getTime()));



    }
}
