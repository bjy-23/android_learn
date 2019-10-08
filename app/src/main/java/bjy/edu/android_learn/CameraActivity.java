package bjy.edu.android_learn;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;

import bjy.edu.android_learn.util.CameraUtil;

public class CameraActivity extends AppCompatActivity {
    private CameraManager cameraManager;
    private String cameraId;
    private Camera camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //开启闪光灯测试
//        method_1();

        //surfaceview （把相机的图像投射到surfaceview上）
        method_2();
    }

    private void method_1(){
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

    private void method_2(){
        setContentView(R.layout.activity_camera_2);

        final SurfaceView surfaceView = findViewById(R.id.surfaceview);

        TextView tv_camera_on = findViewById(R.id.tv_camera_on);
        tv_camera_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Camera camera = Camera.open();
                try {
                    camera.setPreviewDisplay(surfaceView.getHolder());
                    Camera.Parameters parameters = camera.getParameters();
                    parameters.getPreviewFormat();

                    parameters.set("flash-value", 2);
                    parameters.set("flash-mode", "off");
                    parameters.set("zoom", "2.7");
                    parameters.set("taking-picture-zoom", 27);
                    camera.setDisplayOrientation(90);
                    camera.setParameters(parameters);

                    camera.startPreview();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
