package ro.bogdan.ilies.javacv.learning.chapter4;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_legacy;

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

    private static final String IMAGE = "C:\\Users\\Andra\\Desktop\\noi.jpg";
    private static final int[] HIST_SIZES = new int[] {8};
    private static final float[][] HIST_RANGES = new float[][] {{0, 31}, {32, 63}, {64, 95}, {96, 127}, {128, 159},
            {160, 191}, {192, 223}, {224, 256}};

    private static final String HIST_WINDOW = "Histogram";

    @Override
    public void run() {
        // ------------------------ Chapter 4. Exercice 1.---------------------------

//        exercice1();

        // ------------------------ Chapter 4. Exercice 2.---------------------------

//        exercice2();

        // ------------------------ Chapter 4. Exercice 3.---------------------------

        exercice3();
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
            }
        }

        private void resetRectPointer(RectPointer rectPointer) {
            rectPointer.topLeft = null;
            rectPointer.bottomRight = null;
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
        }
    }

    private static class RectPointer {
        public MousePosition topLeft;
        public MousePosition bottomRight;
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
