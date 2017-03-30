int redPin = 11;
int greenPin = 10;
int bluePin = 9;

int red = 0;
int green = 0;
int blue = 0;

//uncomment this line if using a Common Anode LED
//#define COMMON_ANODE

void setup()
{
  pinMode(redPin, OUTPUT);
  pinMode(greenPin, OUTPUT);
  pinMode(bluePin, OUTPUT);

   Serial.begin(115200);
}

void loop()
{


    if (Serial.available() > 0) {
      String first  = Serial.readStringUntil(';');
      //Serial.read(); //next character is comma, so skip it using this
      String second = Serial.readStringUntil(';');
      //Serial.read();
      String third  = Serial.readStringUntil('\n');
      //Serial.print(first); Serial.print(" - "); Serial.print(second); Serial.print(" - "); Serial.println(third); 

      if(first != NULL && second != NULL & third != NULL){
              red = (int)first.toInt();
      green  = (int)second.toInt();
      blue  = (int)third.toInt();
      }

    }
  

  
  setColor(red, green, blue);  // 
}

void setColor(int r, int g, int b)
{
  #ifdef COMMON_ANODE
    red = 255-r;
    green = 255-g;
    blue = 255-b;
  #endif
  analogWrite(redPin, r);
  analogWrite(greenPin, g);
  analogWrite(bluePin, b); 
  Serial.print(r);
  Serial.print(g);
  Serial.println(b);
}
