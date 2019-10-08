package bjy.edu.android_learn.util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class DateUtilTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void date2Str() {
        assertEquals(DateUtil.date2Str(new Date(), "yyyy-MM-dd"), "2019-09-10");
        assertThat(DateUtil.date2Str(new Date(), "yyyy-MM-dd"), is("2019-09-10"));
    }
}