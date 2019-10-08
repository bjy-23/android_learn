package bjy.edu.android_learn.util;

import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import bjy.edu.android_learn.App;
import bjy.edu.android_learn.R;

import static org.junit.Assert.*;

public class ResUtilTest {

    private Context context;

    @Before
    public void setUp() throws Exception {
        context = App.getInstance();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getColorString() {
        System.out.println(context.getPackageName());
        System.out.println(context.getResources().getString(R.string.book_read_permission));
    }
}