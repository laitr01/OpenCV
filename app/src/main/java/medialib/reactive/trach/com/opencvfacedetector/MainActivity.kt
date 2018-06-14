package medialib.reactive.trach.com.opencvfacedetector

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.SurfaceView
import kotlinx.android.synthetic.main.activity_main.*
import org.opencv.android.BaseLoaderCallback
import org.opencv.android.CameraBridgeViewBase
import org.opencv.android.OpenCVLoader
import org.opencv.core.CvType
import org.opencv.core.Mat

class MainActivity : AppCompatActivity(), CameraBridgeViewBase.CvCameraViewListener2 {

    var rgba: Mat? = null

    override fun onCameraViewStarted(width: Int, height: Int) {
        rgba = Mat(height, width, CvType.CV_8UC4)
    }

    override fun onCameraViewStopped() {
        rgba?.release()
    }

    override fun onCameraFrame(inputFrame: CameraBridgeViewBase.CvCameraViewFrame?): Mat? {
        rgba = inputFrame?.rgba()
        rgba?.let {
            OpenCVMiddle.faceDetection(it.nativeObjAddr)
        }
        return rgba
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //sau khi mà camera đã connected thì sẽ hiện nó lên
        opencvCamera.visibility = SurfaceView.VISIBLE
        opencvCamera.setCvCameraViewListener(this)
    }

    override fun onPause() {
        super.onPause()
        opencvCamera.disableView()
    }

    override fun onResume() {
        super.onResume()
        // Used to load the 'native-lib' library on application startup.
        //check xem loaded thu viện =thành công hay chưa
        if(OpenCVLoader.initDebug()){
            Log.e(TAG, "load lib successfully")
            baseLoaderCallback.onManagerConnected(BaseLoaderCallback.SUCCESS)
        }else {
            Log.e(TAG, "load lib not successfully")
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_4_0, this, baseLoaderCallback)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        opencvCamera.disableView()
    }

    val baseLoaderCallback = object :BaseLoaderCallback(baseContext){
        override fun onManagerConnected(status: Int){
            when(status){
                BaseLoaderCallback.SUCCESS -> {
                    opencvCamera.enableView()
                }
                else -> super.onManagerConnected(status)
            }
        }
    }

    companion object {
        val TAG = "Main Activity"
    }
}
