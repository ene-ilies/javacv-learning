package ro.bogdan.ilies.javacv.learning.chapter3;

import org.bytedeco.javacpp.opencv_core;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_core.cvPtr2D;
import static org.bytedeco.javacpp.opencv_highgui.*;
import static org.bytedeco.javacpp.opencv_highgui.cvDestroyWindow;

/**
 * Created by Andra on 11/30/2014.
 */
public class ExercicesC3 implements Runnable {

    @Override
    public void run() {
        // ------------------------ Chapter 3. Exercice 1.---------------------------

        exercice1();

        // ------------------------ Chapter 3. Exercice 2.---------------------------

        exercice2();

        // ------------------------ Chapter 3. Exercice 3.---------------------------

        exercice3();

        // ------------------------ Chapter 3. Exercice 4.---------------------------

        exercice4();

        // ------------------------ Chapter 3. Exercice 5.---------------------------

        exercice5();

        // ------------------------ Chapter 3. Exercice 6.---------------------------

        exercice6();

        // ------------------------ Chapter 3. Exercice 7.---------------------------

        exercice7();
    }

    private void exercice7() {
        // ------------------------ Chapter 3. Exercice 7. point a.---------------------------

        opencv_core.IplImage origImage = cvLoadImage("C:\\Users\\Public\\Pictures\\Sample Pictures\\Tulips.jpg");

        opencv_core.IplImage redChannel = cvCreateImage(cvGetSize(origImage), origImage.depth(), 1);
        opencv_core.IplImage greenChannel = cvCreateImage(cvGetSize(origImage), origImage.depth(), 1);
        opencv_core.IplImage blueChannel = cvCreateImage(cvGetSize(origImage), origImage.depth(), 1);

        opencv_core.IplImage greenImage = cvCreateImage(cvGetSize(origImage), origImage.depth(), origImage.nChannels());

        cvSplit(origImage, redChannel, greenChannel, blueChannel, null);

        cvMerge(null, greenChannel, null, null, greenImage);

        cvShowImage("Green image", greenImage);
        System.out.println("Press any key to continue...");
        cvWaitKey();
        cvDestroyWindow("Green image");


        // ------------------------ Chapter 3. Exercice 7. point b.---------------------------

        IplImage greenClone1 = cvCloneImage(greenChannel);
        IplImage greenClone2 = cvCloneImage(greenChannel);

        // ------------------------ Chapter 3. Exercice 7. point c.---------------------------

        byte max = greenChannel.getByteBuffer(0).get();
        byte min = max;

        // extract min and max
        for (int i = 0; i < greenChannel.width() * greenChannel.height(); i++) {
            min = (greenChannel.getByteBuffer(i).get() < min)? greenChannel.getByteBuffer(i).get() : min;
            max = (greenChannel.getByteBuffer(i).get() > max)? greenChannel.getByteBuffer(i).get() : max;
        }

        // ------------------------ Chapter 3. Exercice 7. point d.---------------------------

        char thresh = (char) (max - min / 2);

        cvSet(greenClone1, cvScalar(thresh));

        // ------------------------ Chapter 3. Exercice 7. point e.---------------------------

        cvSet(greenClone2, cvScalar(0));
        cvCmp(greenChannel, greenClone1, greenClone2, CV_CMP_GE);

        // ------------------------ Chapter 3. Exercice 7. point f.---------------------------

        cvSubS(greenChannel, cvScalar(thresh/2), greenChannel, greenClone2);

        cvShowImage("Threshold", greenChannel);
        System.out.println("Press any key to continue...");
        cvWaitKey();
        cvDestroyWindow("Threshold");

    }

    private void exercice6() {
        IplImage origImage = cvLoadImage("C:\\Users\\Public\\Pictures\\Sample Pictures\\Tulips.jpg");

        IplImage firstSection = cvCreateImageHeader(cvSize(20, 30), origImage.depth(), origImage.nChannels());
        firstSection.widthStep(origImage.widthStep());
        firstSection.imageData(cvPtr2D(origImage, 5, 10));


        IplImage secondSection = cvCreateImageHeader(cvSize(20, 30), origImage.depth(), origImage.nChannels());
        secondSection.widthStep(origImage.widthStep());
        secondSection.imageData(cvPtr2D(origImage, 50, 60));

        cvNot(firstSection, secondSection);

        cvShowImage("Tulips", origImage);
        System.out.println("Press any key to continue...");
        cvWaitKey();
        cvDestroyWindow("Tulips");
    }

    private void exercice5() {
        IplImage greenChannelImage = cvCreateImage(cvSize(210, 210), IPL_DEPTH_8U, 1);

        byte color = 0;
        byte borderThick = 10;
        for (int i = 0; i < 10; i++) {
            CvRect cvRect = cvRect(i * borderThick, i * borderThick, 210 - 2 * i * borderThick, 210 - 2 * i * borderThick);
            cvSetImageROI(greenChannelImage, cvRect);
            cvSet(greenChannelImage, cvScalar(color));
            color += 20;
        }

        cvResetImageROI(greenChannelImage);
        cvShowImage("Pyramid", greenChannelImage);
        System.out.println("Press any key to continue...");
        cvWaitKey();
        cvDestroyWindow("Pyramid");
    }

    private void exercice4() {
        IplImage greenChannelImage = cvCreateImage(cvSize(100, 100), IPL_DEPTH_8U, 3);
        cvSet(greenChannelImage, cvScalar(0));

        // set top and bottom line
        for (int x = 20; x <= 40; x++) {
            // set bottom line
            greenChannelImage.getByteBuffer(x * 100 * 3 + 5 * 3).put(1, (byte) 255);
            // set top line
            greenChannelImage.getByteBuffer(x * 100 * 3 + 20 * 3).put(1, (byte) 255);
        }

        // set left and right line
        for (int y = 5; y <= 20; y++) {
            // set bottom line
            greenChannelImage.getByteBuffer(20 * 100 * 3 + y * 3).put(1, (byte) 255);
            // set top line
            greenChannelImage.getByteBuffer(40 * 100 * 3 + y * 3).put(1, (byte) 255);
        }

        cvShowImage("Green rectangle on black background 2", greenChannelImage);
        System.out.println("Press any key to continue...");
        cvWaitKey();
        cvDestroyWindow("Green rectangle on black background 2");
    }

    private void exercice1() {
        // get absolute value of negative float number
        System.out.println("Absolute value of negative float number: " + cvRound(-7.5));

        // get floor of float number
        System.out.println("Floor value of float number: " + cvFloor(7.5));

        // get ceiling of float number
        System.out.println("Ceil value of float number: " + cvCeil(7.5));

        // random generated numbers
        long[] longs = new long[5];
        cvRandInt(longs);
        System.out.println("Random generated numbers: " + java.util.Arrays.toString(longs));

        // get x and y of 2d point
        CvPoint2D32f cvPoint2D32f = cvPoint2D32f(10, 20);
        System.out.println("CvPoint2D32f[x]=" + cvPoint2D32f.get(0));
        System.out.println("CvPoint2D32f[y]=" + cvPoint2D32f.get(1));
        System.out.println("CvPoint2D32f[x]=" + cvPoint2D32f.x());
        System.out.println("CvPoint2D32f[y]=" + cvPoint2D32f.y());

        // converting CvPoint2D32f to CvPoint
        CvPoint2D32f cvPoint2D32f1 = cvPoint2D32f(10, 20);
        CvPoint cvPoint = cvPointFrom32f(cvPoint2D32f1);
        System.out.println("CvPoint: " + cvPoint);

        // convert CvPoint to CvPoint2D32f
        CvPoint cvPoint1 = cvPoint(1, 2);
        CvPoint2D32f cvPoint2D32f2 = cvPointTo32f(cvPoint1);
        System.out.println("CvPoint2D32f: " + cvPoint2D32f2);
    }

    private void exercice2() {
        // draw a circle onto back background
        IplImage circleImage= cvCreateImage(cvSize(100, 100), IPL_DEPTH_8U, 3);
        cvSet(circleImage, cvScalar(0));
        cvCircle(circleImage, cvPoint(50, 50), 30, cvScalar(41, 204, 182, 0));
        cvShowImage("Circle on black background", circleImage);
        System.out.println("Press any key to continue...");
        cvWaitKey();
        cvDestroyWindow("Circle on black background");
    }

    private void exercice3() {
        IplImage greenChannelImage = cvCreateImage(cvSize(100, 100), IPL_DEPTH_8U, 3);
        cvSet(greenChannelImage, cvScalar(0));

        // set top and bottom line
        for (int x = 20; x <= 40; x++) {
            // set bottom line
            cvPtr2D(greenChannelImage, x, 20).put(1, (byte) 255);
            // set top line
            cvPtr2D(greenChannelImage, x, 5).put(1, (byte) 255);
        }

        // set left and right line
        for (int y = 5; y <= 20; y++) {
            // set bottom line
            cvPtr2D(greenChannelImage, 20, y).put(1, (byte) 255);
            // set top line
            cvPtr2D(greenChannelImage, 40, y).put(1, (byte) 255);
        }

        cvShowImage("Green rectangle on black background", greenChannelImage);
        System.out.println("Press any key to continue...");
        cvWaitKey();
        cvDestroyWindow("Green rectangle on black background");
    }


}
