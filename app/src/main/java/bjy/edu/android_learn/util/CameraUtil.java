package bjy.edu.android_learn.util;

import android.app.Application;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.support.annotation.RequiresApi;

import bjy.edu.android_learn.App;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class CameraUtil {
    private static Context sContext = App.getInstance();

    /**
     * 开启闪光灯（后置）
     *
     * 不建议直接使用，对于23一下的手机多次调用open方法，闪光灯会多次关闭再重启;
     * 可以在调用处搭配一个boolean值来使用
     */
    public static void openFlashlight(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            CameraManager cameraManager = (CameraManager) sContext.getSystemService(Context.CAMERA_SERVICE);
                try{
                    String[] ids = cameraManager.getCameraIdList();
                    for (String id: ids){
                        CameraCharacteristics cameraCharacteristics = cameraManager.getCameraCharacteristics(id);
                        //是否包含闪光灯
                        Boolean flashAvailable = cameraCharacteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
                        if (flashAvailable != null && flashAvailable){
                            Integer lensFacing = cameraCharacteristics.get(CameraCharacteristics.LENS_FACING);
                            if (lensFacing != null && lensFacing == CameraCharacteristics.LENS_FACING_BACK){
                                cameraManager.setTorchMode(id, true);
                                break;
                            }
                        }
                    }
                }catch (CameraAccessException e){

                }
        }else {
            Camera camera = Camera.open();
            Camera.Parameters parameters = camera.getParameters();
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(parameters);
        }
    }

    /**
     * 关闭闪光灯
     */
    public static void closeFlashLight(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            CameraManager cameraManager = (CameraManager) sContext.getSystemService(Context.CAMERA_SERVICE);
            try{
                String[] ids = cameraManager.getCameraIdList();
                for (String id: ids){
                    CameraCharacteristics cameraCharacteristics = cameraManager.getCameraCharacteristics(id);
                    //是否包含闪光灯
                    Boolean flashAvailable = cameraCharacteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
                    if (flashAvailable != null && flashAvailable){
                        Integer lensFacing = cameraCharacteristics.get(CameraCharacteristics.LENS_FACING);
                        if (lensFacing != null && lensFacing == CameraCharacteristics.LENS_FACING_BACK){
                            cameraManager.setTorchMode(id, false);
                            break;
                        }
                    }
                }
            }catch (CameraAccessException e){

            }
        }else {
            Camera camera = Camera.open();
            Camera.Parameters parameters = camera.getParameters();
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(parameters);
            camera.release();
        }
    }
}
