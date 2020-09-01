package bjy.edu.android_learn.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import bjy.edu.android_learn.IStudentInterface;
import bjy.edu.android_learn.binder.IBookManager;
import bjy.edu.android_learn.util.LogUtil;

public class AidlTestService extends Service {
    private LogUtil logUtil = new LogUtil(true, "111222");

    public AidlTestService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        logUtil.log("[AidlTestService] onBind");
        return new AidlBinder();
    }

    @Override
    public void onCreate() {
        logUtil.log("[AidlTestService] onCreate");
        super.onCreate();
    }

    class AidlBinder extends IStudentInterface.Stub{

        @Override
        public String getName() throws RemoteException {
            return "bjy is 1229";
        }
    }
}
