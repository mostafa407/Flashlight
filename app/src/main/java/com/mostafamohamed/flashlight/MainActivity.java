package com.mostafamohamed.flashlight;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageButton;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

public class MainActivity extends AppCompatActivity {
   ImageButton imageButton;
   boolean state;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );
        imageButton=findViewById ( R.id.top );
        Dexter.withContext ( this ).withPermissions ( Manifest.permission.CAMERA ).withListener ( new MultiplePermissionsListener ( ) {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                runFlashLight();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {

            }
        } ).check ();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void runFlashLight() {
        imageButton.setOnClickListener ( new View.OnClickListener ( ) {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if (!state){
                    CameraManager cameraManager=(CameraManager)getSystemService ( Context.CAMERA_SERVICE );
                    try {
                        String cameraId =cameraManager.getCameraIdList ()[0];
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            cameraManager.setTorchMode ( cameraId,true );
                        }
                        state=true;
                        imageButton.setImageResource ( R.drawable.torch_on );
                    }catch (CameraAccessException e){

                    }
                }else {
                    CameraManager cameraManager=(CameraManager)getSystemService ( Context.CAMERA_SERVICE );
                    try {
                        String cameraId =cameraManager.getCameraIdList ()[0];
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            cameraManager.setTorchMode ( cameraId,false );
                        }
                        state=false;
                        imageButton.setImageResource ( R.drawable.torch_off);
                    }catch (CameraAccessException e){

                    }
                }
            }
        } );
    }
}