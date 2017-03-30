package dk.ciid.kelvyn.expimg;


import processing.core.PApplet;
import processing.video.Capture;

public class PointExposureSketch extends PApplet {

    Capture cam;
    public static int CAMERA_WIDTH = 1280;
    public static int CAMERA_HEIGHT = 720;
    public static int READING_RESOLUTION = 2;
    public static int MAX_RADIUS = 2;


    public void settings() {
        fullScreen();
    }

    public void setup() {
        String[] cameras = Capture.list();

        if (cameras.length == 0) {
            println("There are no cameras available for capture.");
            exit();
        } else {
            println("Available cameras:");
            for (int i = 0; i < cameras.length; i++) {
                println(cameras[i]);
            }

            // The camera can be initialized directly using an
            // element from the array returned by list():
            // Capture(parent, requestWidth, requestHeight, cameraName, frameRate)
            cam = new Capture(this, CAMERA_WIDTH, CAMERA_HEIGHT, cameras[0]);

            cam.start();
        }

    }

    public void draw() {
        // background(255);
        noStroke();
        // image(cam, 0, 0);
        cam.read();
        cam.loadPixels();

        for (int rand = 0; rand < width ; rand++) {
            int x = (int)random(0, width);
            int y = (int)random(0, height);
            //int c = cam.get((int)map(x, 0, CAMERA_WIDTH, 0, width), (int)map(y, 0, CAMERA_HEIGHT, 0, height) );
            int c = cam.get(x, y);

            int radius = (int)random(1, MAX_RADIUS);

            for(int r = MAX_RADIUS; r >= 1; r--){
                fill(c, (8 - r) * 30);

                //pushMatrix();
                //translate(x-(r/2), y-(r/2));
                ellipse(x, y, r, r);

                //popMatrix();
            }


            //delay(10);
        }
    }

    public static void main(String[] args) {
        PApplet.main(PointExposureSketch.class.getName());
    }
}