package biz.gabrys.maven.plugins.css.splitter.token;

import java.util.Calendar;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public final class DateTokenValueFactoryTest {

    @Test
    public void create() {
        final DateTokenValueFactory factory = Mockito.spy(new DateTokenValueFactory());
        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2015);
        calendar.set(Calendar.MONTH, 3);
        calendar.set(Calendar.DAY_OF_MONTH, 22);
        Mockito.doReturn(calendar.getTime()).when(factory).getNow();

        Assert.assertEquals("Created value.", "20150422", factory.create("yyyyMMdd"));
    }
}
