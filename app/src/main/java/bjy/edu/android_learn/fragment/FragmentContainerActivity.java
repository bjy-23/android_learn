package bjy.edu.android_learn.fragment;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import bjy.edu.android_learn.R;

/**
 * 给未绑定Activity的fragment提供一个容器Activity
 *
 * fragment通过反射来生成；参数通过
 */
public class FragmentContainerActivity extends AppCompatActivity {
    public static final String NAME = "name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_container);

        addFragment();
    }

    private void addFragment(){
        String name = getIntent().getStringExtra(NAME);
        if (!TextUtils.isEmpty(name)){
            try {
                Fragment fragment = (Fragment) Class.forName(name).newInstance();
                Bundle bundle = getIntent().getExtras();
                if (bundle != null)
                    fragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().add(R.id.container, fragment).commit();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
