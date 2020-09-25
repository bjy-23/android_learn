package bjy.edu.android_learn.permission;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

public class PermissionFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("111222", "PermissionFragment onCreate");
        String[] permissions = {Manifest.permission.CAMERA};
        requestPermissions(permissions, 1);
    }
}
