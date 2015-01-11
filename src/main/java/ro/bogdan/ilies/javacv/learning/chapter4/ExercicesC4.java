package ro.bogdan.ilies.javacv.learning.chapter4;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.FloatPointer;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.opencv_core;

import java.nio.FloatBuffer;
import java.util.Date;

import static org.bytedeco.javacpp.helper.opencv_imgproc.cvCreateHist;
import static org.bytedeco.javacpp.opencv_core.*;

import static org.bytedeco.javacpp.opencv_highgui.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;
import static org.bytedeco.javacpp.opencv_legacy.cvQueryHistValue_1D;

/**
 * Created by Andra on 11/30/2014.
 */
public class ExercicesC4 implements Runnable {

    private static final String GRAY = "Gray";

    private static final String ORIGINAL = "Original";

    private static final String CANNY = "Canny";

    private static final String MERGED = "Merged";

    private static final String IMAGE = "C:\\Users\\Andra\\Desktop\\opencv\\noi.jpg";

    private static final String VIDEO = "C:\\Users\\Andra\\Desktop\\opencv\\video.mp4";

    private static final int[] HIST_SIZES = new int[] {8};

    private static final float[][] HIST_RANGES = new float[][] {{0, 31}, {32, 63}, {64, 95}, {96, 127}, {128, 159},
            {160, 191}, {192, 223}, {224, 256}};

    private static final String HIST_WINDOW = "Histogram";

    private static final String VIDEO_WINDOW = "Video window";

    private static final String VIDEO_TRACKBAR_LABEL = "seek";

    private static final int VIDEO_DEFAULT_FRAME_POS = 0;

    private static final String VIDEO_PLAY_PAUSE_LABEL = "control";

    private static final int VIDEO_DEFAULT_STATUS = 0;

    private static final int DEFAULT_HEIGHT = 480;

    private static final int DEFAULT_WIDTH = 640;

    private static final String PAINT_WINDOW = "Paint";

    private static final int SQUARE_TYPE = 0;

    private static final int LINE_TYPE = 1;

    private static final int CIRCLE_TYPE = 2;

    private static final int ELIPSE_TYPE = 3;

    private static final int FONT_WIDTH = 10;

    private static final int FONT_HEIGHT = 10;

    private static final int PERSPECTIVE_ROWS = 3;

    private static final int PERSPECTIVE_COLS = 3;

    private static final String WARPED_WINDOW = "Warped";


    @Override
    public void run() {
        // ------------------------ Chapter 4. Exercice 1.---------------------------

//        exercice1();

        // ------------------------ Chapter 4. Exercice 2.---------------------------

//        exercice2();

        // ------------------------ Chapter 4. Exercice 3.---------------------------

//        exercice3();

        // ------------------------ Chapter 4. Exercice 4.---------------------------

//        exercice4();

        // ------------------------ Chapter 4. Exercice 5.---------------------------

//        exercice5();

        // ------------------------ Chapter 4. Exercice 6.---------------------------

//        exercice6();

        // ------------------------ Chapter 4. Exercice 7.---------------------------

        exercice7();
    }

    private void exercice7() {
        // load image
        IplImage iplImage = cvLoadImage(IMAGE);

        // display original image
        cvShowImage(ORIGINAL, iplImage);

        // create perspective transform map
        CvMat transformMat = cvCreateMat(PERSPECTIVE_ROWS, PERSPECTIVE_COLS, CV_32F);
        cvSetZero(transformMat);

        // loop undefined
        while (true) {
            // create temp image where to store warped picture
            IplImage temp = cvCreateImage(cvGetSize(iplImage), iplImage.depth(), iplImage.nChannels());

            // compute warped picture
            cvWarpPerspective(iplImage, temp, transformMat, CV_INTER_LINEAR, cvScalar(1));

            // display warped image
            cvShowImage(WARPED_WINDOW, temp);

            // wait for user's input
            int key = cvWaitKey(33);
            // if user presses ESC then stop looping
            if (key == 27) {
                break;
            } else if (key > 48 && key < 58) {
                FloatPointer floatPointer = transformMat.data_fl();
                float value = floatPointer.get(key - 48);
                System.out.printf("before: %f%n", value);
                floatPointer.put(key - 48, value + 1);
                System.out.printf("after: %f%n", floatPointer.get(key - 48));
            } else if (key > 32 && key < 42) {
                FloatPointer floatPointer = transformMat.data_fl();
                float value = floatPointer.get(key - 32);
                // decrease value but not more than 0
                float decreasedValue = (value - 1 >= 0)? value - 1 : 0;
                System.out.printf("before: %f%n", value);
                floatPointer.put(key - 32, decreasedValue);
                System.out.printf("after: %f%n", floatPointer.get(key - 32));
            }

            // release temp image
            cvReleaseImage(temp);
        }
    }

    private void exercice6() {
        // create image of default width
        final IplImage iplImage = cvCreateImage(cvSize(DEFAULT_WIDTH, DEFAULT_HEIGHT), IPL_DEPTH_8U, 3);

        // zero it
        cvSetZero(iplImage);

        // show image
        cvShowImage(ORIGINAL, iplImage);

        // prepare  mouse callback
        CvMouseCallback cvMouseCallback = new CvMouseCallback() {

            @Override
            public void call(int i, int i1, int i2, int i3, Pointer pointer) {
                addUserInputToImage(iplImage, i, i1, i2);
            }
        };

        cvSetMouseCallback(ORIGINAL, cvMouseCallback);

        // loop undefined
        while (true) {

            // wait for user input
            int key = cvWaitKey(33);
            if (key == 27) {
                break;
            }
        }
    }

    private void addUserInputToImage(IplImage iplImage, int event, int x, int y) {
        int key = 0;
        int keyNo = 0;
        // if user clicked mouse's left button then start draw it's input
        if (event == EVENT_LBUTTONUP ) {

            do {
                // wait for user to type a letter
                key = cvWaitKey();

                if (key != 8 && key != 13) {
                    // put that letter on image
                    cvPutText(iplImage, String.format("%c", key), cvPoint(x + keyNo * FONT_WIDTH, y),
                            cvFont(FONT_HERSHEY_PLAIN), cvScalar(255));
                    // increase letter number
                    keyNo++;
                } else if (key == 8) {
                    // decrease letter number
                    keyNo--;
                    // remove last letter
                    cvSetImageROI(iplImage, cvRect(x + keyNo * FONT_WIDTH, y - FONT_HEIGHT, FONT_WIDTH, FONT_HEIGHT));
                    // fill ROI with zeros
                    cvSetZero(iplImage);
                    // reset ROI
                    cvResetImageROI(iplImage);
                }

                // redraw image on window
                cvShowImage(ORIGINAL, iplImage);
            } while (key != 13);

        }
    }

    private void exercice5() {
        // create image of default width and height
        IplImage iplImage = cvCreateImage(cvSize(DEFAULT_WIDTH, DEFAULT_HEIGHT), IPL_DEPTH_8U, 3);

        // fill image with zeros
        cvSetZero(iplImage);

        // create variable that will hold starting point and end point for figures that will be drawn
        RectPointer rectPointer = new RectPointer();

        // display empty image
        cvShowImage(PAINT_WINDOW, iplImage);

        // initialize figure's type handler
        FigureTypeCallback figureTypeCallback = new FigureTypeCallback();

        // add buttons
//        cvCreateButton(PAINT_WINDOW, figureTypeCallback, null, CV_CHECKBOX, 0);
//        cvCreateButton(PAINT_WINDOW, figureTypeCallback, null, CV_CHECKBOX, 1);
//        cvCreateButton(PAINT_WINDOW, figureTypeCallback, null, CV_CHECKBOX, 2);
//        cvCreateButton(PAINT_WINDOW, figureTypeCallback, null, CV_CHECKBOX, 3);

        // initialize mouse dispatcher
        MouseCallbackDispatcher mouseCallbackDispatcher = new MouseCallbackDispatcher(rectPointer);

        // add mouse callback to window
        cvSetMouseCallback(PAINT_WINDOW, mouseCallbackDispatcher);

        // set last operation timestamp
        Date lastOperationTimestamp = new Date();

        while (true) {
            // get type of figure to draw
            Integer type = figureTypeCallback.type;

            // draw figure if not already drawn
            lastOperationTimestamp = drawFigure(iplImage, rectPointer, lastOperationTimestamp, type, cvScalar(255));

            // display new image
            cvShowImage(PAINT_WINDOW, iplImage);

            // retrieve input key from user
            int key = cvWaitKey(33);
            // if user pressed "ESC" then stop looping
            if (key == 27) {
                break;
            }

        }
    }

    private class FigureTypeCallback extends CvButtonCallback {
        public int type;

        @Override
        public void call(int i, Pointer pointer) {
            type = i;
        }
    }

    private Date drawFigure(IplImage iplImage, RectPointer rectPointer, Date lastTimestamp, Integer type,
            CvScalar color) {

        // if newer rect pointer than last timestamp then create figure
        if (isRectValid(rectPointer) && lastTimestamp.compareTo(rectPointer.timestamp) == -1) {
            switch (type) {
                case SQUARE_TYPE:
                    cvDrawRect(iplImage, cvPoint(rectPointer.topLeft.x, rectPointer.topLeft.y),
                            cvPoint(rectPointer.bottomRight.x, rectPointer.bottomRight.y), color, 1, 8, 0);
                    break;
                case LINE_TYPE:
                    cvDrawLine(iplImage, cvPoint(rectPointer.topLeft.x, rectPointer.topLeft.y),
                            cvPoint(rectPointer.bottomRight.x, rectPointer.bottomRight.y), color, 1, 8, 0);
                    break;
                case CIRCLE_TYPE:
                    int middleX = (rectPointer.topLeft.x + rectPointer.bottomRight.x) / 2;
                    int middleY = (rectPointer.topLeft.y + rectPointer.bottomRight.y) / 2;
                    int size = Math.abs(rectPointer.topLeft.x - rectPointer.bottomRight.x);
                    cvDrawCircle(iplImage, cvPoint(middleX, middleY),
                            size, color, 1, 8, 0);
                    break;
                case ELIPSE_TYPE:
                    middleX = (rectPointer.topLeft.x + rectPointer.bottomRight.x) / 2;
                    middleY = (rectPointer.topLeft.y + rectPointer.bottomRight.y) / 2;
                    int width = Math.abs(rectPointer.topLeft.x - rectPointer.bottomRight.x) / 2;
                    int height = Math.abs(rectPointer.topLeft.x - rectPointer.bottomRight.x);
                    int angle = 30;
                    int startAngle = 0;
                    int endAngle = 360;
                    cvDrawEllipse(iplImage, cvPoint(middleX, middleY), cvSize(width, height),
                            angle, startAngle, endAngle, color, 1, 8, 0);
                    break;
            }

            return new Date();
        } else if (rectPointer.erase) {
            cvSetZero(iplImage);
        }

        return lastTimestamp;
    }

    private void exercice4() {
        final CvCapture cvCapture = cvCreateFileCapture(VIDEO);
        
        // retrieve first frame
        IplImage frame = cvQueryFrame(cvCapture);

        // get total number of frames
        double noOfFrames = cvGetCaptureProperty(cvCapture, CV_CAP_PROP_FRAME_COUNT);

        // compute half frame
        IplImage halfFrame = cvCreateImage(cvSize(frame.width() / 2, frame.height() / 2), frame.depth(),
                frame.nChannels());

        // resize original frame
        cvResize(frame, halfFrame);

        // display first frame
        cvShowImage(VIDEO_WINDOW, halfFrame);

        CvTrackbarCallback seekTrackBarCallback = new CvTrackbarCallback() {
            @Override
            public void call(int i) {
                cvSetCaptureProperty(cvCapture, CV_CAP_PROP_POS_FRAMES, i);
            }
        };

        // initialize semaphos
        final VideoStatus videoStatus = new VideoStatus();

        CvTrackbarCallback playPauseTrackBarCallback = new CvTrackbarCallback() {

            @Override
            public void call(int i) {
                videoStatus.paused = (i != 0);
            }
        };

        // attach track bar to video displaying video
        cvCreateTrackbar(VIDEO_TRACKBAR_LABEL, VIDEO_WINDOW, new int[] { VIDEO_DEFAULT_FRAME_POS }, (int) noOfFrames,
                seekTrackBarCallback);

        // attach track bar to video displaying video
        cvCreateTrackbar(VIDEO_PLAY_PAUSE_LABEL, VIDEO_WINDOW, new int[] { VIDEO_DEFAULT_STATUS }, 1,
                playPauseTrackBarCallback);

        // get video's fps
        double fps = cvGetCaptureProperty(cvCapture, CV_CAP_PROP_FPS);

        // compute frame frequency
        double frameFreq = 1000 / fps;
        System.out.printf("fps: %f, frameFreq: %f", fps, frameFreq);

        //  start playing video
        while (frame != null) {

            // compute half frame
            halfFrame = cvCreateImage(cvSize(frame.width() / 2, frame.height() / 2), frame.depth(),
                    frame.nChannels());

            // resize original frame
            cvResize(frame, halfFrame);

            // display next frame
            cvShowImage(VIDEO_WINDOW, halfFrame);

            // wait until displaying next frame
            int key = cvWaitKey((int) frameFreq);

            // if user pressed "ESC" then stop playing video
            if (key == 27) {
                break;
            }

            // get next frame
            if (!videoStatus.paused) {
                frame = cvQueryFrame(cvCapture);
            }
        }

        cvReleaseCapture(cvCapture);
    }

    private class VideoStatus {
        public boolean paused;
    }

    private void exercice3() {
        IplImage origImage = cvLoadImage(IMAGE, CV_LOAD_IMAGE_GRAYSCALE);

        // get empty histogram
        CvHistogram cvHistogram = getHistogram();

        // compute histogram
        cvCalcHist(origImage, cvHistogram);

        // draw histogram
        displayHistogram(cvHistogram, origImage.width(), origImage.height());

        // display image without any rectangle
        cvShowImage(ORIGINAL, origImage);

        // initialize rect pointer
        RectPointer rectPointer = new RectPointer();

        // set mouse events dispatcher
        cvSetMouseCallback(ORIGINAL, new MouseCallbackDispatcher(rectPointer));

        int key = -1;
        while (key != 27) {
            // clone original image
            IplImage temp = cvCloneImage(origImage);

            // draw rectangle
            drawRect(temp, rectPointer, cvScalar(255));

            // display processed image
            cvShowImage(ORIGINAL, temp);

            // wait for user's input for 33 ms
            key = cvWaitKey(33);

            // release image
            cvReleaseImage(temp);
        }

        // release histogram
        cvReleaseHist(cvHistogram);
    }

    private void displayHistogram(CvHistogram cvHistogram, int width, int height) {
        IplImage hist = cvCreateImage(cvSize(width, height), IPL_DEPTH_8U, 1);
        cvSet(hist, cvScalar(255));

        int barWidth = width / HIST_SIZES[0] - 5;

        for (int i = 0; i < HIST_SIZES[0]; i++) {
            int barHeight = (int) cvQueryHistValue_1D(cvHistogram, i) % height;
            cvRectangle(hist, cvPoint(i * barWidth + 5, height), cvPoint((i + 1) * barWidth, height - barHeight),
                    cvScalar(0), CV_FILLED, CV_AA, 0);
        }

        cvShowImage(HIST_WINDOW, hist);
    }

    private CvHistogram getHistogram() {
        CvHistogram cvHistogram = cvCreateHist(1, HIST_SIZES, CV_HIST_ARRAY, HIST_RANGES, 1);

        return cvHistogram;
    }

    private static void drawRect(IplImage origImage, RectPointer rectPointer, CvScalar color) {
        // if rect is valid the draw it
        if (isRectValid(rectPointer)) {
            cvDrawRect(origImage, cvPoint(rectPointer.topLeft.x, rectPointer.topLeft.y),
                    cvPoint(rectPointer.bottomRight.x, rectPointer.bottomRight.y), color, 1, 8, 0);
        }
    }

    private static boolean isRectValid(RectPointer rectPointer) {
        if (rectPointer.topLeft == null) {
            return false;
        }

        if (rectPointer.bottomRight == null) {
            return false;
        }

        if (rectPointer.topLeft.x == null) {
            return false;
        }

        if (rectPointer.topLeft.y == null) {
            return false;
        }

        if (rectPointer.bottomRight.x == null) {
            return false;
        }

        if (rectPointer.bottomRight.y == null) {
            return false;
        }

        if (rectPointer.timestamp == null) {
            return false;
        }

        return true;
    }

    private static class MouseCallbackDispatcher extends CvMouseCallback {

        private RectPointer rectPointer;

        private MouseCallbackDispatcher(RectPointer rectPointer) {
            this.rectPointer = rectPointer;
        }

        @Override
        public void call(int i, int i2, int i3, int i4, Pointer pointer) {
            dispatchMouseEvent(i, i2, i3, rectPointer);
        }

        private void dispatchMouseEvent(int event, int x, int y, RectPointer rectPointer) {
            switch (event) {
                case EVENT_LBUTTONDOWN:
                    resetRectPointer(rectPointer);
                    setTopLeftPoint(rectPointer, x, y);
                    break;
                case EVENT_LBUTTONUP:
                    setBottomRightPoint(rectPointer, x, y);
                    break;
                case EVENT_RBUTTONUP:
                    resetRectPointer(rectPointer);
                    rectPointer.erase = true;
                    break;
            }
        }

        private void resetRectPointer(RectPointer rectPointer) {
            rectPointer.topLeft = null;
            rectPointer.bottomRight = null;
            rectPointer.timestamp = null;
            rectPointer.erase = false;
        }

        private MousePosition getPointPointer(int x, int y) {
            MousePosition pointPointer = new MousePosition();
            pointPointer.x = x;
            pointPointer.y = y;

            return pointPointer;
        }

        private void setTopLeftPoint(RectPointer rectPointer, int x, int y) {
            MousePosition pointPointer = getPointPointer(x, y);

            rectPointer.topLeft = pointPointer;
        }

        private void setBottomRightPoint(RectPointer rectPointer, int x, int y) {
            MousePosition pointPointer = getPointPointer(x, y);

            rectPointer.bottomRight = pointPointer;
            rectPointer.timestamp = new Date();
        }
    }

    private static class RectPointer {
        public MousePosition topLeft;
        public MousePosition bottomRight;
        public Date timestamp;
        public boolean erase;
    }

    private static class MousePosition {
        public Integer x;
        public Integer y;
    }

    private void exercice2() {

        final MousePosition mousePosition = new MousePosition();

        CvMouseCallback cvMouseCallback = new CvMouseCallback() {
            public void call(int event, int x, int y, int flags, Pointer param) {
                System.out.printf("x=%d,y=%d", x, y);
                mousePosition.x = x;
                mousePosition.y = y;
            }
        };

        cvShowImage(ORIGINAL, null);

        cvSetMouseCallback(ORIGINAL, cvMouseCallback);

        CvCapture cvCapture = null;
        try {
            cvCapture = cvCaptureFromCAM(0);

            while (true) {
                opencv_core.IplImage frame = cvQueryFrame(cvCapture);

                putColorValues(frame, mousePosition);
                cvShowImage(ORIGINAL, frame);

                int key = cvWaitKey(31);
                System.out.printf("key= %d%n", key);
                if (key == 27) {
                    break;
                }
            }

        } finally {
            cvReleaseCapture(cvCapture);
            cvDestroyWindow(GRAY);
        }
    }

    private class Pixel {
        public byte red;
        public byte green;
        public byte blue;
    }

    private void putColorValues(IplImage frame, MousePosition mousePosition) {
        if (mousePosition.x != null && mousePosition.y != null) {
            Pixel pixel = getPixel(frame, mousePosition.x, mousePosition.y);

            String text = "(R:" + pixel.red + "G:" + pixel.green + "B:" + pixel.blue + ")";
            cvPutText(frame, text, cvPoint(mousePosition.x, mousePosition.y), cvFont(FONT_HERSHEY_PLAIN),
                    cvScalar(255));
        }
    }

    private Pixel getPixel(IplImage frame, Integer x, Integer y) {
        BytePointer bytePointer = cvPtr2D(frame, x, y);
        Pixel pixel = new Pixel();
        pixel.blue = bytePointer.get(0);
        pixel.green = bytePointer.get(1);
        pixel.red = bytePointer.get(2);

        return pixel;
    }

    private void putMousePosition(IplImage frame, MousePosition mousePosition) {
        if (mousePosition.x != null && mousePosition.y != null) {
            String text = "(x:" + mousePosition.x + "y:" + mousePosition.y + ")";
            cvPutText(frame, text, cvPoint(mousePosition.x, mousePosition.y), cvFont(FONT_HERSHEY_PLAIN),
                    cvScalar(255));
        }
    }

    private void exercice1() {
        // ------------------------ Chapter 3. Exercice 1.---------------------------

        CvCapture cvCapture = null;
        try {
            cvCapture = cvCaptureFromCAM(0);

            while (true) {
                opencv_core.IplImage frame = cvQueryFrame(cvCapture);

                opencv_core.IplImage gray = grayIt(frame);

                opencv_core.IplImage canny = cannyIt(frame);

                opencv_core.IplImage merged = mergeIntoOne(frame, gray, canny);

                IplImage labeledImage = labelFrames(merged);

//                cvShowImage(ORIGINAL, frame);
//                cvShowImage(GRAY, gray);
//                cvShowImage(CANNY, canny);
                cvShowImage(MERGED, labeledImage);

                int key = cvWaitKey(31);
                System.out.printf("key= %d%n", key);
                if (key == 27) {
                    break;
                }

                cvReleaseImage(gray);
                cvReleaseImage(canny);
                cvReleaseImage(merged);
            }

        } finally {
            cvReleaseCapture(cvCapture);
            cvDestroyWindow(GRAY);
        }
    }

    private IplImage labelFrames(IplImage merged) {
        int oneThirdWidth = merged.width() / 3;
        cvPutText(merged, ORIGINAL, cvPoint(5, 30), cvFont(FONT_HERSHEY_PLAIN), cvScalar(255));
        cvPutText(merged, GRAY, cvPoint(oneThirdWidth + 5, 30), cvFont(FONT_HERSHEY_PLAIN), cvScalar(255));
        cvPutText(merged, CANNY, cvPoint(oneThirdWidth * 2 + 5, 30), cvFont(FONT_HERSHEY_PLAIN), cvScalar(255));
        return merged;
    }

    private IplImage mergeIntoOne(IplImage frame, IplImage gray, IplImage canny) {
        IplImage mergedImage = cvCreateImage(cvSize(frame.width() * 3, frame.height()), frame.depth(),
                frame.nChannels());

        cvSetImageROI(mergedImage, cvRect(0, 0, frame.width(), frame.height()));
        cvCopy(frame, mergedImage);
        cvSetImageROI(mergedImage, cvRect(frame.width(), 0, frame.width(), frame.height()));
        cvCopy(gray, mergedImage);
        cvSetImageROI(mergedImage, cvRect(frame.width() * 2, 0, frame.width(), frame.height()));
        cvCopy(canny, mergedImage);
        cvResetImageROI(mergedImage);


        return mergedImage;
    }

    private opencv_core.IplImage cannyIt(opencv_core.IplImage frame) {
        IplImage canny3C = cvCreateImage(cvGetSize(frame), frame.depth(), frame.nChannels());

        opencv_core.IplImage cannyImage = cvCreateImage(cvGetSize(frame), frame.depth(), 1);
        cvCanny(frame, cannyImage, 100, 100);

        cvMerge(cannyImage, cannyImage, cannyImage, null, canny3C);
        return canny3C;
    }

    private opencv_core.IplImage grayIt(opencv_core.IplImage frame) {
        IplImage gray3C = cvCreateImage(cvGetSize(frame), frame.depth(), frame.nChannels());

        opencv_core.IplImage grayImage = cvCreateImage(cvGetSize(frame), frame.depth(), 1);
        cvCvtColor(frame, grayImage, COLOR_BGR2GRAY);

        cvMerge(grayImage, grayImage, grayImage, null, gray3C);
        return gray3C;
    }
}
