package ro.bogdan.ilies.javacv.learning.chapter5;

import org.bytedeco.javacpp.opencv_core;

import java.util.Random;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_highgui.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;
import static org.bytedeco.javacpp.opencv_legacy.cvPyrSegmentation;

/**
 * Created by Andra on 1/17/2015.
 */
public class ExercicesC5 implements Runnable {

    private static final String IMAGE = "C:\\Users\\Andra\\Desktop\\opencv\\noi.jpg";

    private static final String EX4_FIRST_IMAGE = "C:\\Users\\Andra\\Desktop\\opencv\\ex4firstImage.jpg";

    private static final String EX4_SECOND_IMAGE = "C:\\Users\\Andra\\Desktop\\opencv\\ex4secondImage.jpg";

    private static final String EX5_FIRST_IMAGE = "C:\\Users\\Andra\\Desktop\\opencv\\ex5firstImage.jpg";

    private static final String EX5_SECOND_IMAGE = "C:\\Users\\Andra\\Desktop\\opencv\\ex5secondImage.jpg";

    private static final String EX5_RESULT = "C:\\Users\\Andra\\Desktop\\opencv\\ex5Result.jpg";

    private static final String EX6_RESULT = "C:\\Users\\Andra\\Desktop\\opencv\\ex6Result.jpg";

    private static final String TAKEN_PICTURES_DIR= "C:\\Users\\Andra\\Desktop\\opencv\\";

    private static final String TAKEN_PICTURES_TEMPLATE = "photo_%s.jpg";

    private static final String DIFF_WINDOW = "Diff image";

    private static final String ORIGINAL = "Original image";

    private static final String CELAN_DIFF = "Clean diff";

    private static final String DIRTY_DIFF = "Dirty diff";

    private static final String THRESHHOLDED_WINDOW = "Thresholded";

    private static final String OPENNED_WINDOW = "Opened";

    private static final String FILTERED_OBJECT = "Filtered Object";

    private static final String COPY_MASK = "Copy mask";

    private static final String EX8_PARTIAL = "C:\\Users\\Andra\\Desktop\\opencv\\ex8Partial.jpg";

    private static final String EX8_PARTIAL_WINDOW = "EX8 partial";

    private static final String EX9_WINDOW = "Top hat";

    private static final String EX10_INPUT = "C:\\Users\\Andra\\Desktop\\opencv\\tree-247122.jpg";

    private static final String EX10_WINDOW = "EX10 result";

    private static final String EX11_WINDOW = "EX11 pyr segm";

    private static final String EX12_WINDOW = "EX12 threshold";

    @Override
    public void run() {
        // ------------------------ Chapter 5. Exercice 1.---------------------------

//        exercice1();

        // ------------------------ Chapter 5. Exercice 2.---------------------------

//        exercice2();

        // ------------------------ Chapter 5. Exercice 3.---------------------------

//        exercice3();

        // ------------------------ Chapter 5. Exercice 4.---------------------------

//        exercice4();

        // ------------------------ Chapter 5. Exercice 5.---------------------------

//        exercice5();

        // ------------------------ Chapter 5. Exercice 6.---------------------------

//        exercice6();

        // ------------------------ Chapter 5. Exercice 7.---------------------------

//        exercice7();

        // ------------------------ Chapter 5. Exercice 8.---------------------------

        // uncomment and comment part2 when you want to generate image
//        exercice8part1();
//        exercice8part2();

        // ------------------------ Chapter 5. Exercice 9.---------------------------

//        exercice9();

        // ------------------------ Chapter 5. Exercice 10.---------------------------

//        exercice10();

        // ------------------------ Chapter 5. Exercice 11.---------------------------

//        exercice11();

        // ------------------------ Chapter 5. Exercice 12.---------------------------

        exercice12();

//        takePicturesFromFirstCam();
    }

    private void exercice12() {
        // load original image
        IplImage original = cvLoadImage(EX5_SECOND_IMAGE, CV_LOAD_IMAGE_GRAYSCALE);
        IplImage threshold = cvCreateImage(cvGetSize(original), original.depth(), original.nChannels());

        // threshold image
        cvThreshold(original, threshold, 128, 255, CV_THRESH_BINARY);
        cvShowImage(EX12_WINDOW, threshold);

        // wait for user input in order to continue
        System.out.println("Press any key to continue...");
        cvWaitKey();

        cvThreshold(original, threshold, 128, 255, CV_THRESH_BINARY_INV);
        cvShowImage(EX12_WINDOW, threshold);

        // wait for user input in order to continue
        System.out.println("Press any key to continue...");
        cvWaitKey();

        cvThreshold(original, threshold, 128, 255, CV_THRESH_TRUNC);
        cvShowImage(EX12_WINDOW, threshold);

        // wait for user input in order to continue
        System.out.println("Press any key to continue...");
        cvWaitKey();

        cvThreshold(original, threshold, 128, 255, CV_THRESH_TOZERO);
        cvShowImage(EX12_WINDOW, threshold);

        // wait for user input in order to continue
        System.out.println("Press any key to continue...");
        cvWaitKey();

        cvThreshold(original, threshold, 128, 255, CV_THRESH_TOZERO_INV);
        cvShowImage(EX12_WINDOW, threshold);

        // wait for user input in order to continue
        System.out.println("Press any key to continue...");
        cvWaitKey();

        // --------------------------- point a. ----------------------------------------

        cvAdaptiveThreshold(original, threshold, 255, CV_ADAPTIVE_THRESH_MEAN_C, CV_THRESH_BINARY, 3, 5);
        cvShowImage(EX12_WINDOW, threshold);

        // wait for user input in order to continue
        System.out.println("Press any key to continue...");
        cvWaitKey();

        cvAdaptiveThreshold(original, threshold, 255, CV_ADAPTIVE_THRESH_MEAN_C, CV_THRESH_BINARY_INV, 3, 5);
        cvShowImage(EX12_WINDOW, threshold);

        // wait for user input in order to continue
        System.out.println("Press any key to continue...");
        cvWaitKey();

        // --------------------------- point b. ----------------------------------------

        cvAdaptiveThreshold(original, threshold, 255, CV_ADAPTIVE_THRESH_MEAN_C, CV_THRESH_BINARY, 3, 0);
        cvShowImage(EX12_WINDOW, threshold);

        // wait for user input in order to continue
        System.out.println("Press any key to continue...");
        cvWaitKey();

        cvAdaptiveThreshold(original, threshold, 255, CV_ADAPTIVE_THRESH_MEAN_C, CV_THRESH_BINARY, 3, -5);
        cvShowImage(EX12_WINDOW, threshold);

        // wait for user input in order to continue
        System.out.println("Press any key to continue...");
        cvWaitKey();
    }

    private void exercice11() {
        // load original image
        IplImage original = cvLoadImage(EX10_INPUT, CV_LOAD_IMAGE_GRAYSCALE);
        IplImage pyrSegm = cvCreateImage(cvGetSize(original), original.depth(), original.nChannels());

        int levels = 2;
        int thresh1 = 50;
        int thresh2 = 50;

        // apply pyr segmentation
        CvMemStorage cvMemStorage = cvCreateMemStorage(1000);
        CvSeq cvSeq = null;
        cvPyrSegmentation(original, pyrSegm, cvMemStorage, cvSeq, levels, thresh1, thresh2);

        // display result image
        cvShowImage(EX11_WINDOW, pyrSegm);

        // wait for user input in order to continue
        System.out.println("Press any key to continue...");
        cvWaitKey();
    }

    private void exercice10() {
        // load original image
        IplImage original = cvLoadImage(EX10_INPUT);

        // --------------------------- point a. ----------------------------------------

        // resize image
        IplImage resized = cvCreateImage(cvSize(original.width() / 2, original.height() / 2), original.depth(),
                original.nChannels());

        // resize image
        cvResize(original, resized);

        // show image
        cvShowImage(EX10_WINDOW, resized);

        // wait for user input in order to continue
        System.out.println("Press any key to continue...");
        cvWaitKey();

        // --------------------------- point b. ----------------------------------------

        // resize image
        IplImage pyrDown = cvCreateImage(cvSize(original.width() / 2, original.height() / 2), original.depth(),
                original.nChannels());

        // resize image
        cvPyrDown(original, pyrDown);

        // show image
        cvShowImage(EX10_WINDOW, pyrDown);

        // wait for user input in order to continue
        System.out.println("Press any key to continue...");
        cvWaitKey();
    }

    private void exercice9() {
        // load gray scale image
        IplImage original = cvLoadImage(EX4_SECOND_IMAGE, CV_LOAD_IMAGE_GRAYSCALE);
        IplImage temp = cvCreateImage(cvGetSize(original), original.depth(), original.nChannels());

        // --------------------------- point a. ----------------------------------------

        IplImage topHat = cvCreateImage(cvGetSize(original), original.depth(), original.nChannels());

        // top hat the image
        cvMorphologyEx(original, topHat, temp, null, CV_MOP_TOPHAT, 2);

        // display the image
        cvShowImage(EX9_WINDOW, topHat);

        // wait for user input in order to continue
        System.out.println("Press any key to continue...");
        cvWaitKey();

        // --------------------------- point b. ----------------------------------------

        IplImage mask = cvCreateImage(cvGetSize(topHat), topHat.depth(), topHat.nChannels());

        // transform top hat into mask by thresholding
        cvThreshold(topHat, mask, 10, 256, CV_THRESH_BINARY);

        // display mask
        cvShowImage(EX9_WINDOW, mask);

        // wait for user input in order to continue
        System.out.println("Press any key to continue...");
        cvWaitKey();

        // --------------------------- point c. ----------------------------------------

        IplImage topHatWithPieces = cvCreateImage(cvGetSize(topHat), topHat.depth(), topHat.nChannels());
        IplImage ex5FirstImage = cvLoadImage(EX5_FIRST_IMAGE, CV_LOAD_IMAGE_GRAYSCALE);

        // transform top hat into mask by thresholding
        cvThreshold(topHat, mask, 10, 256, CV_THRESH_BINARY);

        // copy using created mask
        cvCopy(ex5FirstImage, topHatWithPieces, mask);

        // display mask
        cvShowImage(EX9_WINDOW, topHatWithPieces);

        // wait for user input in order to continue
        System.out.println("Press any key to continue...");
        cvWaitKey();
    }

    private void exercice8part2() {
        IplImage noisyImage = cvLoadImage(EX8_PARTIAL);
        IplImage bilateralFilter = cvCreateImage(cvGetSize(noisyImage), noisyImage.depth(), noisyImage.nChannels());

        // apply bilateral filter
        cvSmooth(noisyImage, bilateralFilter, CV_BILATERAL, 3, 3, 0.7, 40);

        // show image
        cvShowImage(EX8_PARTIAL_WINDOW, bilateralFilter);

        // wait for user input in order to continue
        System.out.println("Press any key to continue...");
        cvWaitKey();
    }

    private void exercice8part1() {
        IplImage generatedImage = cvCreateImage(cvSize(640, 480), IPL_DEPTH_8U, 3);

        for (int i = 0; i < generatedImage.height(); i++) {
            for (int j = 0; j < generatedImage.width(); j++) {
                cvSet2D(generatedImage, i, j, cvScalar(randInt() / 3));
            }
        }
        // save partial image
        cvSaveImage(EX8_PARTIAL, generatedImage);

        // display partial image
        cvShowImage(EX8_PARTIAL_WINDOW, generatedImage);

        // wait for user input in order to continue
        System.out.println("Press any key to continue...");
        cvWaitKey();
    }

    private void exercice7() {
        // load original image
        IplImage original = cvLoadImage(EX5_SECOND_IMAGE);
        // read mask
        IplImage mask = cvLoadImage(EX6_RESULT);
        // create result canvas
        IplImage resultCanvas = cvCreateImage(cvGetSize(original), original.depth(), original.nChannels());
        cvSet(resultCanvas, cvScalar(255));

        // copy object from first image to canvas
        cvCopy(original, resultCanvas, mask);

        // display result
        cvShowImage(COPY_MASK, resultCanvas);

        // wait for user input in order to continue
        System.out.println("Press any key to continue...");
        cvWaitKey();
    }

    private void exercice6() {
        // load first image
        IplImage source = cvLoadImage(EX5_RESULT, CV_LOAD_IMAGE_GRAYSCALE);

        CvPoint maxAreaStartPoint = cvPoint(0, 0);
        double maxArea = 0;
        CvPoint startPoint = null;
        for (int i = 0; i < source.height(); i++) {
            for (int j = 0; j < source.width(); j++) {
                if (cvGet2D(source, i, j).get() == 255) {
                    startPoint = cvPoint(j, i);
                    maxArea = fillAreaAndUpdateMax(source, startPoint, maxAreaStartPoint, maxArea);
                }
            }
        }

        // save image
        cvSaveImage(EX6_RESULT, source);
        // display image
        cvShowImage(FILTERED_OBJECT, source);
        cvWaitKey();
    }

    private double fillAreaAndUpdateMax(IplImage source, CvPoint startPoint, CvPoint maxAreaStartPoint, double maxArea) {
        double newMaxArea = maxArea;
        // initialize connected compo
        CvConnectedComp cvConnectedComp = new CvConnectedComp();

        cvFloodFill(source, startPoint, cvScalar(254), cvScalar(200), cvScalar(200), cvConnectedComp,
                FLOODFILL_FIXED_RANGE, null);

        if (maxArea < cvConnectedComp.area()) {
            newMaxArea = cvConnectedComp.area();
            cvFloodFill(source, maxAreaStartPoint, cvScalar(0), cvScalar(200), cvScalar(200), cvConnectedComp,
                    FLOODFILL_FIXED_RANGE, null);
            updateCvPoint(maxAreaStartPoint, startPoint);
        } else {
            cvFloodFill(source, startPoint, cvScalar(0), cvScalar(200), cvScalar(200), cvConnectedComp,
                    FLOODFILL_FIXED_RANGE, null);
        }

        return newMaxArea;
    }

    private void updateCvPoint(CvPoint maxAreaStartPoint, CvPoint startPoint) {
        maxAreaStartPoint.x(startPoint.x());
        maxAreaStartPoint.y(startPoint.y());
    }

    private void exercice5() {
        // load first image
        IplImage firstImage = cvLoadImage(EX5_FIRST_IMAGE, CV_LOAD_IMAGE_GRAYSCALE);
        // load second image
        IplImage secondImage = cvLoadImage(EX5_SECOND_IMAGE, CV_LOAD_IMAGE_GRAYSCALE);

        // create destination image of diff
        IplImage diff12 = cvCreateImage(cvGetSize(firstImage), firstImage.depth(), firstImage.nChannels());

        // --------------------------- point a. ----------------------------------------

        // compute diff
        cvAbsDiff(firstImage, secondImage, diff12);

        // show diff image
        cvShowImage(DIFF_WINDOW, diff12);

        // wait for user input in order to continue
        System.out.println("Press any key to continue...");
        cvWaitKey();

        // --------------------------- point b. ----------------------------------------

        // create image to hold thresholded diff
        IplImage thresholded = cvCreateImage(cvGetSize(diff12), diff12.depth(), diff12.nChannels());

        // threshold image
        cvThreshold(diff12, thresholded, 50, 255, CV_THRESH_BINARY);

        // display thresholded image
        cvShowImage(THRESHHOLDED_WINDOW, thresholded);

        // wait for user input in order to continue
        System.out.println("Press any key to continue...");
        cvWaitKey();

        // --------------------------- point b. ----------------------------------------

        // create image to hold opened image
        IplImage opened = cvCreateImage(cvGetSize(thresholded), thresholded.depth(), thresholded.nChannels());
        IplImage temp = cvCreateImage(cvGetSize(thresholded), thresholded.depth(), thresholded.nChannels());

        // open image
        cvMorphologyEx(thresholded, opened, temp, null, CV_MOP_OPEN);

        // display opened image
        cvShowImage(OPENNED_WINDOW, opened);

        // save image for exercice 6
        cvSaveImage(EX5_RESULT, opened);

        // wait for user input in order to continue
        System.out.println("Press any key to continue...");
        cvWaitKey();
    }

    private void takePicturesFromFirstCam() {
        // connect to fist camera device
        CvCapture cvCapture = cvCreateCameraCapture(0);

        System.out.println("1. Press \"s\" to take photo");
        System.out.println("2. Press \"ESC\" to exit");

        int i = 0;
        while (true) {
            // query frame
            IplImage frame = cvQueryFrame(cvCapture);
            // show image
            cvShowImage(ORIGINAL, frame);

            int key = cvWaitKey();

            if (key == 27) {
                break;
            } else if (key == 's') {
                // query frame
                frame = cvQueryFrame(cvCapture);
                // save image on disk
                cvSaveImage(TAKEN_PICTURES_DIR + String.format(TAKEN_PICTURES_TEMPLATE, i++), frame);
                // show image
                cvShowImage(ORIGINAL, frame);
            }
        }

        // release capture
        cvReleaseCapture(cvCapture);

    }

    private void exercice4() {
        // load first image
        IplImage firstImage = cvLoadImage(EX4_FIRST_IMAGE, CV_LOAD_IMAGE_GRAYSCALE);
        // load second image
        IplImage secondImage = cvLoadImage(EX4_SECOND_IMAGE, CV_LOAD_IMAGE_GRAYSCALE);

        // create destination image of diff
        IplImage diff12 = cvCreateImage(cvGetSize(firstImage), firstImage.depth(), firstImage.nChannels());

        // --------------------------- point a. ----------------------------------------

        // compute diff
        cvAbsDiff(firstImage, secondImage, diff12);

        // show diff image
        cvShowImage(DIFF_WINDOW, diff12);

        // wait for user input in order to finish app
        System.out.println("Press any key to continue...");
        cvWaitKey();

        // --------------------------- point b. ----------------------------------------

        IplImage cleanDiff = cvCreateImage(cvGetSize(diff12), diff12.depth(), diff12.nChannels());

        // apply erode on diff image
        cvErode(diff12, cleanDiff);
        // apply dilate on clean diff
        cvDilate(cleanDiff, cleanDiff);

        // show clean diff
        cvShowImage(CELAN_DIFF, cleanDiff);

        System.out.println("Press any key to continue...");
        cvWaitKey();

        // --------------------------- point c. ----------------------------------------

        IplImage dirtyDiff = cvCreateImage(cvGetSize(diff12), diff12.depth(), diff12.nChannels());

        // apply erode on diff image
        cvErode(diff12, dirtyDiff);
        // apply dilate on clean diff
        cvDilate(cleanDiff, dirtyDiff);

        // show clean diff
        cvShowImage(DIRTY_DIFF, dirtyDiff);

        System.out.println("Press any key to continue...");
        cvWaitKey();
    }

    private static void exercice3() {
        IplImage origImage = cvLoadImage(IMAGE);
        IplImage dstImageSD1 = cvCreateImage(cvGetSize(origImage), origImage.depth(), origImage.nChannels());
        IplImage dstImageSD4 = cvCreateImage(cvGetSize(origImage), origImage.depth(), origImage.nChannels());
        IplImage dstImageSD6 = cvCreateImage(cvGetSize(origImage), origImage.depth(), origImage.nChannels());

        // --------------------------- point a. ----------------------------------------

        // blur image with standard deviation equals to 1
        cvSmooth(origImage, dstImageSD1, CV_GAUSSIAN, 9, 9, 1, 0);
        // blur image with standard deviation equals to 4
        cvSmooth(origImage, dstImageSD4, CV_GAUSSIAN, 9, 9, 4, 0);
        // blur image with standard deviation equals to 6
        cvSmooth(origImage, dstImageSD6, CV_GAUSSIAN, 9, 9, 6, 0);

        // display blurred images
        cvShowImage("Gaussian9x9SD1", dstImageSD1);
        cvShowImage("Gaussian9x9SD4", dstImageSD4);
        cvShowImage("Gaussian9x9SD6", dstImageSD6);

        System.out.println("Press any key to continue...");
        cvWaitKey();

        // --------------------------- point b. ----------------------------------------

        // blur image with standard deviation equals to 1
        cvSmooth(origImage, dstImageSD1, CV_GAUSSIAN, 0, 0, 1, 0);
        // blur image with standard deviation equals to 4
        cvSmooth(origImage, dstImageSD4, CV_GAUSSIAN, 0, 0, 4, 0);
        // blur image with standard deviation equals to 6
        cvSmooth(origImage, dstImageSD6, CV_GAUSSIAN, 0, 0, 6, 0);

        // display blurred images
        cvShowImage("Gaussian9x9SD1", dstImageSD1);
        cvShowImage("Gaussian9x9SD4", dstImageSD4);
        cvShowImage("Gaussian9x9SD6", dstImageSD6);

        System.out.println("Press any key to continue...");
        cvWaitKey();

        // destroy all windows
        cvDestroyAllWindows();

        // --------------------------- point c. ----------------------------------------

        IplImage dstImageSD19 = cvCreateImage(cvGetSize(origImage), origImage.depth(), origImage.nChannels());

        cvSmooth(origImage, dstImageSD19, CV_GAUSSIAN, 0, 0, 1, 9);

        // display blurred images
        cvShowImage("Gaussian9x9SD1", dstImageSD19);

        System.out.println("Press any key to continue...");
        cvWaitKey();

        // --------------------------- point d. ----------------------------------------

        IplImage dstImageSD91 = cvCreateImage(cvGetSize(origImage), origImage.depth(), origImage.nChannels());

        cvSmooth(origImage, dstImageSD91, CV_GAUSSIAN, 0, 0, 9, 1);

        // display blurred images
        cvShowImage("Gaussian9x9SD1", dstImageSD91);

        System.out.println("Press any key to continue...");
        cvWaitKey();

        // destroy all windows
        cvDestroyAllWindows();

        // --------------------------- point e. ----------------------------------------

        // display blurred images
        cvShowImage("Gaussian9x9SD19", dstImageSD19);
        cvShowImage("Gaussian9x9SD91", dstImageSD91);

        System.out.println("Press any key to continue...");
        cvWaitKey();

        // --------------------------- point e. ----------------------------------------

        IplImage dstImageSD9x919 = cvCreateImage(cvGetSize(origImage), origImage.depth(), origImage.nChannels());
        IplImage dstImageSD9x991 = cvCreateImage(cvGetSize(origImage), origImage.depth(), origImage.nChannels());

        cvSmooth(origImage, dstImageSD9x919, CV_GAUSSIAN, 9, 9, 9, 1);
        cvSmooth(origImage, dstImageSD9x991, CV_GAUSSIAN, 9, 9, 9, 1);

        // display blurred images
        cvShowImage("Gaussian9x9SD19", dstImageSD19);
        cvShowImage("Gaussian9x9SD91", dstImageSD91);

        System.out.println("Press any key to continue...");
        cvWaitKey();
    }

    private static void exercice2() {
        // --------------------------- point a. ----------------------------------------
        // create 100x100 image
        IplImage origImage = cvCreateImage(cvSize(100, 100), IPL_DEPTH_8U, 1);
        IplImage dstImage5x5 = cvCreateImage(cvGetSize(origImage), origImage.depth(), origImage.nChannels());

        // clear image
        cvSetZero(origImage);
        // set center point to 255
        cvSet2D(origImage, origImage.width() / 2, origImage.height() / 2, cvScalar(255));

        // apply 5x5 gaussian filter
        cvSmooth(origImage, dstImage5x5, CV_GAUSSIAN, 5, 5, 0, 0);

        // show result image
        cvShowImage("Original", origImage);
        cvShowImage("Gaussian5x5", dstImage5x5);

        System.out.println("Press any key to continue...");
        cvWaitKey();

        // destroy all windows
        cvDestroyAllWindows();

        // --------------------------- point b. ----------------------------------------

        IplImage dstImage9x9 = cvCreateImage(cvGetSize(origImage), origImage.depth(), origImage.nChannels());
        // apply 9x9 gaussian filter
        cvSmooth(origImage, dstImage9x9, CV_GAUSSIAN, 9, 9, 0, 0);

        // display result
        cvShowImage("Gaussian9x9", dstImage9x9);

        System.out.println("Press any key to continue...");
        cvWaitKey();

        // destroy all windows
        cvDestroyAllWindows();

        // --------------------------- point c. ----------------------------------------

        IplImage dstImage5x5twice = cvCreateImage(cvGetSize(origImage), origImage.depth(), origImage.nChannels());

        // smooth second time 5x5 smoothed image with 5x5 kernel
        cvSmooth(dstImage5x5, dstImage5x5twice, CV_GAUSSIAN, 5, 5, 0, 0);

        // display result
        cvShowImage("Gaussian5x5 twice", dstImage5x5twice);
        cvShowImage("Gaussian9x9", dstImage9x9);

        //wait for user input to ed the program
        System.out.println("Press any key to continue...");
        cvWaitKey();
    }

    private static void exercice1() {
        IplImage origImage = cvLoadImage(IMAGE);

        // point a.
        IplImage dstImage3x3 = cvCreateImage(cvGetSize(origImage), origImage.depth(), origImage.nChannels());
        IplImage dstImage5x5 = cvCreateImage(cvGetSize(origImage), origImage.depth(), origImage.nChannels());
        IplImage dstImage9x9 = cvCreateImage(cvGetSize(origImage), origImage.depth(), origImage.nChannels());
        IplImage dstImage11x11 = cvCreateImage(cvGetSize(origImage), origImage.depth(), origImage.nChannels());

        cvSmooth(origImage, dstImage3x3, CV_GAUSSIAN, 3, 3, 0, 0);
        cvSmooth(origImage, dstImage5x5, CV_GAUSSIAN, 5, 5, 0, 0);
        cvSmooth(origImage, dstImage9x9, CV_GAUSSIAN, 9, 9, 0, 0);
        cvSmooth(origImage, dstImage11x11, CV_GAUSSIAN, 11, 11, 0, 0);

        cvShowImage("Original", origImage);
        cvShowImage("Gaussian3x3", dstImage3x3);
        cvShowImage("Gaussian5x5", dstImage5x5);
        cvShowImage("Gaussian9x9", dstImage9x9);
        cvShowImage("Gaussian11x11", dstImage11x11);

        System.out.println("Press any key to continue...");
        cvWaitKey();

        // destroy all windows
        cvDestroyAllWindows();

        // point b.
        cvSmooth(dstImage5x5, dstImage5x5, CV_GAUSSIAN, 5, 5, 0, 0);

        cvShowImage("Original", origImage);
        cvShowImage("Gaussian11x11", dstImage5x5);
        cvShowImage("Gaussian5x5 twice", dstImage11x11);

        System.out.println("Press any key to continue...");
        cvWaitKey();
    }
}
