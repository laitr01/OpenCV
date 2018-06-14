package medialib.reactive.trach.com.opencvfacedetector

class OpenCVMiddle {
    companion object {
        init {
            System.loadLibrary("MyLibs")
        }
        @JvmStatic external fun faceDetection(addressRgba: Long)
    }
}