package bjy.edu.android_learn_ipc;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import bjy.edu.android_learn.IStudentInterface;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent("action_servive_aidltest");
        intent.setPackage("bjy.edu.android_learn");
        bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.i("111222", "绑定成功");
                IStudentInterface iStudentInterface = IStudentInterface.Stub.asInterface(service);

                try {
                    Log.i("111222", "name: " + iStudentInterface.getName());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Context.BIND_AUTO_CREATE);
//        startActivity(new Intent(this, ContentProviderActivity.class));
    }
}
