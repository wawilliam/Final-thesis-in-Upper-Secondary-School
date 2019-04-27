#include <Servo.h>
Servo servo;
Servo servo_1;
Servo servo_2;
Servo servo_3;
Servo servo_4;

String input;
String text_1;
String text_2;

float deg;
float deg_1;
float deg_2;
float deg_3;
float deg_4;

void setup(){  
  
  Serial.begin(9600); 
  servo.attach(9);
  servo_1.attach(10);
  servo_2.attach(11);
  servo_3.attach(12);
  servo_4.attach(13);
  
  servo.write(90);
  servo_1.write(90);
  servo_2.write(90);
  servo_3.write(90);
  servo_4.write(90);
  delay(100);
} 

void loop(){  

  if(Serial.available() > 9) {

    deg = Serial.parseFloat();
    deg_1 = Serial.parseFloat();
    deg_2 = Serial.parseFloat();
    deg_3 = Serial.parseFloat();
    deg_4 = Serial.parseFloat();
    
 
    servo.write(deg);
    servo_1.write(deg_1);
    servo_2.write(deg_2);
    servo_3.write(deg_3);
    servo_4.write(deg_4);

    delay(1);
    
      
      
    
  }

    
      
    
  
} 

