package biz.gabrys.maven.plugins.css.splitter.token;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

import java.util.Calendar;

import org.junit.Test;

public final class DateTokenValueFactoryTest {

    @Test
    public void create() {
        final DateTokenValueFactory factory = spy(new DateTokenValueFactory());
        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2015);
        calendar.set(Calendar.MONTH, 3);
        calendar.set(Calendar.DAY_OF_MONTH, 22);
        doReturn(calendar.getTime()).when(factory).getNow();

        assertEquals("20150422", factory.create("yyyyMMdd"));
    }
}
