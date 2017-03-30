package dk.ciid.expimg;

import de.voidplus.leapmotion.Device;
import de.voidplus.leapmotion.Finger;
import de.voidplus.leapmotion.Hand;
import de.voidplus.leapmotion.LeapMotion;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import processing.serial.Serial;

import java.util.ArrayList;

public class DrawPhotoSketch extends PApplet {

    LeapMotion leap;
    private Serial serialPort;
    PImage img;

    ArrayList<String> images = new ArrayList();
    int imageIndex = 0;


    public void settings() {
        size(640, 480);
    }

    public void setup() {
        leap = new LeapMotion(this);
        String portName = Serial.list()[3];
        print(Serial.list());
        serialPort = new Serial(this, portName, 115200);
        images.add("RabbitHopF_1.jpg");
        images.add("RabbitHopF_2.jpg");
        images.add("RabbitHopF_3.jpg");
        images.add("RabbitHopF_4.jpg");
        images.add("RabbitHopF_5.jpg");
        images.add("RabbitHopF_6.jpg");
        images.add("RabbitHopF_7.jpg");
        images.add("RabbitHopF_8.jpg");


        loadImageWithIndex(0);
        image(img, 0, 0);
    }

    public void loadImageWithIndex(int index){
        img = loadImage(images.get(index));
    }

    public void draw() {

        background(255);

        noStroke();

        image(img, (width/2) - (img.width/2), (height/2) - (img.height/2));
        // ...

        int fps = leap.getFrameRate();
        for (Hand hand : leap.getHands ()) {


            // ==================================================
            // 2. Hand

            int     handId             = hand.getId();
            PVector handPosition       = hand.getPosition();
            PVector handStabilized     = hand.getStabilizedPosition();
            PVector handDirection      = hand.getDirection();
            PVector handDynamics       = hand.getDynamics();
            float   handRoll           = hand.getRoll();
            float   handPitch          = hand.getPitch();
            float   handYaw            = hand.getYaw();
            boolean handIsLeft         = hand.isLeft();
            boolean handIsRight        = hand.isRight();
            float   handGrab           = hand.getGrabStrength();
            float   handPinch          = hand.getPinchStrength();
            float   handTime           = hand.getTimeVisible();
            PVector spherePosition     = hand.getSpherePosition();
            float   sphereRadius       = hand.getSphereRadius();

            // --------------------------------------------------
            // Drawing
            //hand.draw();


            // ==================================================
            // 4. Finger


            Finger  fingerIndex        = hand.getIndexFinger();
            // or                        hand.getFinger("index");
            // or                        hand.getFinger(1);



            // int distance = new Double(Math.sqrt(Math.pow((fingerIndex.getPosition().x - width/2), 2) + Math.pow((fingerIndex.getPosition().y - height/2), 2))).intValue();
            // boolean isIntersecting = (distance < 100);
            int color = color(0);

            if(fingerIndex.getPosition().x > (width/2) - (img.width/2) && fingerIndex.getPosition().x < (width/2) + (img.width/2) &&
                    fingerIndex.getPosition().y > (height/2) - (img.height/2) && fingerIndex.getPosition().y < (height/2) + (img.height/2)  ) {

                int fX = (int)fingerIndex.getPosition().x - ((width/2) - (img.width/2));
                int fY = (int)fingerIndex.getPosition().y - ((height/2) - (img.height/2));
                println("get " + fX + " " +fY);
                 color = img.get(fX, fY);
                serialPort.write(red(color) + ";" + green(color) + ";" + blue(color) + "\n");
                //delay(40);
                serialPort.write("0;0;0\n");
                // delay(40);
            }else{

                serialPort.write("0;0;0\n");

            }
            //




            //ellipse(width/2, height/2, 200, 200);

            stroke(255);
            fill(color);
            ellipse(fingerIndex.getPosition().x, fingerIndex.getPosition().y, 10, 10);




        }


        // ====================================================
        // 7. Devices

        for (Device device : leap.getDevices()) {
            float deviceHorizontalViewAngle = device.getHorizontalViewAngle();
            float deviceVericalViewAngle = device.getVerticalViewAngle();
            float deviceRange = device.getRange();
        }


    }

    public void keyPressed()
    {
        if(key == CODED)
        {
            if (keyCode == LEFT)
            {
                if(imageIndex > 0) loadImageWithIndex(--imageIndex);
            }
            if(keyCode == RIGHT)
            {
                if(imageIndex < images.size()-1) loadImageWithIndex(++imageIndex);
            }

        }
    }

    // ======================================================
// 1. Callbacks

    void leapOnInit() {
        // println("Leap Motion Init");
    }
    void leapOnConnect() {
        // println("Leap Motion Connect");
    }
    void leapOnFrame() {
        // println("Leap Motion Frame");
    }
    void leapOnDisconnect() {
        // println("Leap Motion Disconnect");
    }
    void leapOnExit() {
        // println("Leap Motion Exit");
    }

    public static void main(String[] args) {
        PApplet.main(DrawPhotoSketch.class.getName());
    }
}