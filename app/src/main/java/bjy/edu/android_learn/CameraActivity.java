package bjy.edu.android_learn;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import bjy.edu.android_learn.util.CameraUtil;

public class CameraActivity extends AppCompatActivity {
    private CameraManager cameraManager;
    private String cameraId;
    private Camera camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        TextView tv_1 = findViewById(R.id.tv_1);
        tv_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                CameraUtil.openFlashlight();


                cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                try{
                    String[] ids = cameraManager.getCameraIdList();
                    for (String id: ids){
                        CameraCharacteristics cameraCharacteristics = cameraManager.getCameraCharacteristics(id);
                        //是否包含闪光灯

                        if (cameraCharacteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE)){
//                            if (cameraCharacteristics.get(CameraCharacteristics.LENS_FACING) == CameraCharacteristics.LENS_FACING_BACK){
                                cameraId = id;
                                cameraManager.setTorchMode(id, true);
//                            }
                        }
                    }
                }catch (CameraAccessException e){

                }


//                if (camera == null)
//                    camera = Camera.open();
//                Camera.Parameters parameters = camera.getParameters();
//                Log.i("FlashMode", parameters.getFlashMode());
//                if (parameters.getFlashMode().equals(Camera.Parameters.FLASH_MODE_TORCH))
//                    return;
//                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
//                camera.setParameters(parameters);
            }
        });


        TextView tv_2 = findViewById(R.id.tv_2);
        tv_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                CameraUtil.closeFlashLight();


//                try{
//                    cameraManager.setTorchMode(cameraId, false);
//                }catch (CameraAccessException e){
//
//                }


//                if (camera == null)
//                    return;
//                Camera.Parameters parameters = camera.getParameters();
//                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
//                camera.setParameters(parameters);
//                camera.release();
//                camera = null;
            }
        });
    }
}
