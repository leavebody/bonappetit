package com.hophacks2018.bonappetit.bonappetit.activity;

/*
 * Copyright (C) The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.hophacks2018.bonappetit.bonappetit.R;

import com.hophacks2018.bonappetit.bonappetit.models.Food;
import com.hophacks2018.bonappetit.bonappetit.models.KnowledgeGraphRaw;
import com.hophacks2018.bonappetit.bonappetit.models.ScanResult;
import com.hophacks2018.bonappetit.bonappetit.util.CameraSource;
import com.hophacks2018.bonappetit.bonappetit.util.CameraSourcePreview;
import com.hophacks2018.bonappetit.bonappetit.util.Globals;
import com.hophacks2018.bonappetit.bonappetit.util.GraphicOverlay;
import com.hophacks2018.bonappetit.bonappetit.util.OcrGraphic;
import com.hophacks2018.bonappetit.bonappetit.service.ApiService;
import com.hophacks2018.bonappetit.bonappetit.service.ModelResult;
import com.hophacks2018.bonappetit.bonappetit.service.ServiceCallBack;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;


/**
 * Activity for the Ocr Detecting app.  This app detects text and displays the value with the
 * rear facing camera. During detection overlay graphics are drawn to indicate the position,
 * size, and contents of each TextBlock.
 */
public final class CameraActivity extends AppCompatActivity {
    private TextRecognizer textRecognizer;

    private static final String TAG = "OcrCaptureActivity";

    // Intent request code to handle updating play services if needed.
    private static final int RC_HANDLE_GMS = 9001;

    // Permission request codes need to be < 256
    private static final int RC_HANDLE_CAMERA_PERM = 2;

    // Constants used to pass extra data in the intent
    public static final String AutoFocus = "AutoFocus";
    public static final String UseFlash = "UseFlash";
    public static final String TextBlockObject = "String";

    private CameraSource mCameraSource;
    private CameraSourcePreview mPreview;
    private GraphicOverlay<OcrGraphic> mGraphicOverlay;

    private Button captureButton;
    private int rotation;

    /**
     * Initializes the UI and creates the detector pipeline.
     */
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_camera);

        mPreview = (CameraSourcePreview) findViewById(R.id.preview);

        mGraphicOverlay = (GraphicOverlay<OcrGraphic>) findViewById(R.id.graphicOverlay);

//        ApiService.getDishName(this, "mapo tofu", new ServiceCallBack<String>() {
//            @Override
//            public void getModelOnSuccess(ModelResult<String> modelResult) {
//                if (modelResult.isStatus()){
//                    Log.d("asdfgh", modelResult.getModel());
//                    String name = modelResult.getModel();
//                    ApiService.getKnowledge(CameraActivity.this, name, new ServiceCallBack<KnowledgeGraphRaw>() {
//                        @Override
//                        public void getModelOnSuccess(ModelResult<KnowledgeGraphRaw> modelResult) {
//                            if (modelResult.isStatus()){
//                                Log.d("asdfgh", modelResult.getModel().toString());
//                            }
//                        }
//                    });
//                }
//            }
//        });

        captureButton = (Button) findViewById(R.id.button_capture);
        rotation = getWindowManager().getDefaultDisplay().getRotation();

        // Set good defaults for capturing text.
        boolean autoFocus = true;
        boolean useFlash = false;

        // Check for the camera permission before accessing the camera.  If the
        // permission is not granted yet, request permission.
        int rc = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (rc == PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);

            createCameraSource(autoFocus, useFlash);
        } else {
            requestCameraPermission();
        }

        captureButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // get an image from the camera
                        mCameraSource.takePicture(null, new CameraSource.PictureCallback() {
                            private File imageFile;

                            @Override
                            public void onPictureTaken(byte[] bytes) {
                                try {
                                    // convert byte array into bitmap
                                    Bitmap loadedImage = null;
                                    Bitmap rotatedBitmap = null;
                                    loadedImage = BitmapFactory.decodeByteArray(bytes, 0,
                                            bytes.length);

                                    // rotate Image
                                    Matrix rotateMatrix = new Matrix();
                                    rotateMatrix.postRotate(rotation);
                                    rotatedBitmap = Bitmap.createBitmap(loadedImage, 0, 0,
                                            loadedImage.getWidth(), loadedImage.getHeight(),
                                            rotateMatrix, false);
                                    /*Bitmap rotatedBitmap = convertToBitmap(bytes);
                                    //saveImage(imageFile, rotatedBitmap);*/

                                    String state = Environment.getExternalStorageState();
                                    File folder = null;
                                    if (state.contains(Environment.MEDIA_MOUNTED)) {
                                        folder = new File(Environment
                                                .getExternalStorageDirectory() + "/Demo");
                                    } else {
                                        folder = new File(Environment
                                                .getExternalStorageDirectory() + "/Demo");
                                    }
                                    boolean success = true;
                                    if (!folder.exists()) {
                                        success = folder.mkdirs();
                                    }
                                    if (success) {
                                        java.util.Date date = new java.util.Date();
                                        imageFile = new File(folder.getAbsolutePath()
                                                + File.separator
                                                + new Date(date.getTime()).toString()
                                                + "Image.jpg");

                                        Log.d("ImagePath", imageFile.toString());

                                        imageFile.createNewFile();
                                    } else {
                                        Toast.makeText(getBaseContext(), "Image Not saved",
                                                Toast.LENGTH_SHORT).show();
                                        return;
                                    }

                                    ByteArrayOutputStream ostream = new ByteArrayOutputStream();

                                    // save image into gallery
                                    rotatedBitmap = resize(rotatedBitmap, 800, 600);
                                    rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, ostream);

                                    FileOutputStream fout = new FileOutputStream(imageFile);
                                    fout.write(ostream.toByteArray());
                                    fout.close();
                                    ContentValues values = new ContentValues();

                                    values.put(MediaStore.Images.Media.DATE_TAKEN,
                                            System.currentTimeMillis());
                                    values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                                    values.put(MediaStore.MediaColumns.DATA,
                                            imageFile.getAbsolutePath());
                                    Log.d("AndroidFinish", "image finish");

                                    //set the textBlocks
                                    Frame outputFrame = new Frame.Builder().setBitmap(rotatedBitmap).build();
                                    textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
                                    Globals globals = (Globals)getApplication();
                                    globals.setTextBlockSparseArray(textRecognizer.detect(outputFrame));

                                    globals.setScanResult(new ScanResult(CameraActivity.this, imageFile.toString()));
                                    ScanResult scanResult = globals.getScanResult();

                                    for (int i = 0; i < globals.getTextBlockSparseArray().size(); ++i) {
                                        TextBlock item = globals.getTextBlockSparseArray().valueAt(i);
                                        if (item != null && item.getValue() != null) {
                                            Food food = new Food(item.getValue(), scanResult, CameraActivity.this);
                                            scanResult.addFoodItem(food);
                                        }
                                    }
                                    scanResult.finishFilling();

                                    Log.d("AndroidFinish", "waiting...................................");

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }
        );

    }

    /**
     * Handles the requesting of the camera permission.  This includes
     * showing a "Snackbar" message of why the permission is needed then
     * sending the request.
     */
    private void requestCameraPermission() {
        Log.w(TAG, "Camera permission is not granted. Requesting permission");

        final String[] permissions = new String[]{Manifest.permission.CAMERA};

        if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {
            ActivityCompat.requestPermissions(this, permissions, RC_HANDLE_CAMERA_PERM);
            return;
        }

        final Activity thisActivity = this;

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(thisActivity, permissions,
                        RC_HANDLE_CAMERA_PERM);
            }
        };

        Snackbar.make(mGraphicOverlay, R.string.permission_camera_rationale,
                Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.ok, listener)
                .show();
    }

    /**
     * Creates and starts the camera.  Note that this uses a higher resolution in comparison
     * to other detection examples to enable the ocr detector to detect small text samples
     * at long distances.
     * <p>
     * Suppressing InlinedApi since there is a check that the minimum version is met before using
     * the constant.
     */
    @SuppressLint("InlinedApi")
    private void createCameraSource(boolean autoFocus, boolean useFlash) {

        // Creates and starts the camera.  Note that this uses a higher resolution in comparison
        // to other detection examples to enable the text recognizer to detect small pieces of text.
        mCameraSource =
                new CameraSource.Builder(getApplicationContext(), null)
                        .setFacing(CameraSource.CAMERA_FACING_BACK)
                        .setRequestedPreviewSize(1280, 1024)
                        .setRequestedFps(2.0f)
                        .setFlashMode(useFlash ? Camera.Parameters.FLASH_MODE_TORCH : null)
                        .setFocusMode(autoFocus ? Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE : null)
                        .build();
    }

    /**
     * Restarts the camera.
     */
    @Override
    protected void onResume() {
        super.onResume();
        startCameraSource();
    }

    /**
     * Stops the camera.
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (mPreview != null) {
            mPreview.stop();
        }
    }

    /**
     * Releases the resources associated with the camera source, the associated detectors, and the
     * rest of the processing pipeline.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPreview != null) {
            mPreview.release();
        }
    }

    /**
     * Callback for the result from requesting permissions. This method
     * is invoked for every call on {@link #requestPermissions(String[], int)}.
     * <p>
     * <strong>Note:</strong> It is possible that the permissions request interaction
     * with the user is interrupted. In this case you will receive empty permissions
     * and results arrays which should be treated as a cancellation.
     * </p>
     *
     * @param requestCode  The request code passed in {@link #requestPermissions(String[], int)}.
     * @param permissions  The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *                     which is either {@link PackageManager#PERMISSION_GRANTED}
     *                     or {@link PackageManager#PERMISSION_DENIED}. Never null.
     * @see #requestPermissions(String[], int)
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != RC_HANDLE_CAMERA_PERM) {
            Log.d(TAG, "Got unexpected permission result: " + requestCode);
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }

        if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Camera permission granted - initialize the camera source");
            // we have permission, so create the camerasource
            boolean autoFocus = getIntent().getBooleanExtra(AutoFocus, false);
            boolean useFlash = getIntent().getBooleanExtra(UseFlash, false);
            createCameraSource(autoFocus, useFlash);
            return;
        }

        Log.e(TAG, "Permission not granted: results len = " + grantResults.length +
                " Result code = " + (grantResults.length > 0 ? grantResults[0] : "(empty)"));

        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Multitracker sample")
                .setMessage(R.string.no_camera_permission)
                .setPositiveButton(R.string.ok, listener)
                .show();
    }

    /**
     * Starts or restarts the camera source, if it exists.  If the camera source doesn't exist yet
     * (e.g., because onResume was called before the camera source was created), this will be called
     * again when the camera source is created.
     */
    private void startCameraSource() throws SecurityException {
        // check that the device has play services available.
        int code = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(
                getApplicationContext());
        if (code != ConnectionResult.SUCCESS) {
            Dialog dlg =
                    GoogleApiAvailability.getInstance().getErrorDialog(this, code, RC_HANDLE_GMS);
            dlg.show();
        }

        if (mCameraSource != null) {
            try {
                mPreview.start(mCameraSource, mGraphicOverlay);
            } catch (IOException e) {
                Log.e(TAG, "Unable to start camera source.", e);
                mCameraSource.release();
                mCameraSource = null;
            }
        }
    }

    private class ScaleListener implements ScaleGestureDetector.OnScaleGestureListener {

        /**
         * Responds to scaling events for a gesture in progress.
         * Reported by pointer motion.
         *
         * @param detector The detector reporting the event - use this to
         *                 retrieve extended info about event state.
         * @return Whether or not the detector should consider this event
         * as handled. If an event was not handled, the detector
         * will continue to accumulate movement until an event is
         * handled. This can be useful if an application, for example,
         * only wants to update scaling factors if the change is
         * greater than 0.01.
         */
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            return false;
        }

        /**
         * Responds to the beginning of a scaling gesture. Reported by
         * new pointers going down.
         *
         * @param detector The detector reporting the event - use this to
         *                 retrieve extended info about event state.
         * @return Whether or not the detector should continue recognizing
         * this gesture. For example, if a gesture is beginning
         * with a focal point outside of a region where it makes
         * sense, onScaleBegin() may return false to ignore the
         * rest of the gesture.
         */
        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            return true;
        }

        /**
         * Responds to the end of a scale gesture. Reported by existing
         * pointers going up.
         * <p/>
         * Once a scale has ended, {@link ScaleGestureDetector#getFocusX()}
         * and {@link ScaleGestureDetector#getFocusY()} will return focal point
         * of the pointers remaining on the screen.
         *
         * @param detector The detector reporting the event - use this to
         *                 retrieve extended info about event state.
         */
        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            if (mCameraSource != null) {
                mCameraSource.doZoom(detector.getScaleFactor());
            }
        }
    }

    private Bitmap resize(Bitmap image, int maxWidth, int maxHeight) {
        if (maxHeight > 0 && maxWidth > 0) {
            int width = image.getWidth();
            int height = image.getHeight();
            float ratioBitmap = (float) width / (float) height;
            float ratioMax = (float) maxWidth / (float) maxHeight;

            int finalWidth = maxWidth;
            int finalHeight = maxHeight;
            if (ratioMax > 1) {
                finalWidth = (int) ((float) maxHeight * ratioBitmap);
            } else {
                finalHeight = (int) ((float) maxWidth / ratioBitmap);
            }
            image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true);
            return image;
        } else {
            return image;
        }
    }

}
