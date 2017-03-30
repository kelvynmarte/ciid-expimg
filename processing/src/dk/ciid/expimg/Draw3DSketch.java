package dk.ciid.expimg;

import de.voidplus.leapmotion.*;
import processing.core.PApplet;
import processing.core.PVector;
import processing.serial.Serial;

public class Draw3DSketch extends PApplet {

    LeapMotion leap;
    private Serial serialPort;



    public void settings() {
        size(640, 480);
    }

    public void setup() {
        leap = new LeapMotion(this);
        String portName = Serial.list()[3];
        print(Serial.list());
        serialPort = new Serial(this, portName, 115200);
    }

    public void draw() {

        background(255);

        noStroke();
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



            int distance = new Double(Math.sqrt(Math.pow((fingerIndex.getPosition().x - width/2), 2) + Math.pow((fingerIndex.getPosition().y - height/2), 2))).intValue();
            boolean isIntersecting = (distance < 100);


            // println(isIntersecting);

            if(isIntersecting){
                fill(255,255,40);
                serialPort.write("0;0;255\n");

            }else{
                serialPort.write("0;0;0\n");
                fill(140);
            }


            ellipse(width/2, height/2, 200, 200);


            fill(0);
            ellipse(fingerIndex.getPosition().x, fingerIndex.getPosition().y, 8, 8);




        }


        // ====================================================
        // 7. Devices

        for (Device device : leap.getDevices()) {
            float deviceHorizontalViewAngle = device.getHorizontalViewAngle();
            float deviceVericalViewAngle = device.getVerticalViewAngle();
            float deviceRange = device.getRange();
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
        PApplet.main(Draw3DSketch.class.getName());
    }
}