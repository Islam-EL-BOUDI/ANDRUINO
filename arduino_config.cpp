//*
Required Components:
Arduino (Uno, Nano, etc.)
HC-05 or HC-06 Bluetooth module
Android device with a Bluetooth terminal app (e.g., "Bluetooth Terminal" app)
Wiring:
HC-05/HC-06 -> Arduino
VCC -> 5V
GND -> GND
TX -> Arduino RX (Pin 0) (Use a voltage divider if using HC-05)
RX -> Arduino TX (Pin 1)
*//

#include <SoftwareSerial.h>

// Define the pins for RX and TX on the Arduino
SoftwareSerial bluetooth(2, 3); // RX, TX

void setup() {
  // Start the serial communication with the Arduino IDE (Serial Monitor)
  Serial.begin(9600);
  
  // Start the Bluetooth communication
  bluetooth.begin(9600);
  
  Serial.println("Bluetooth Device is Ready to Pair");
}

void loop() {
  // If data is received via Bluetooth
  if (bluetooth.available()) {
    char received = bluetooth.read();
    Serial.print("Received via Bluetooth: ");
    Serial.println(received);
    
    // Echo the received data back to the Android device
    bluetooth.print("Arduino Received: ");
    bluetooth.println(received);
  }

  // If data is sent from the Serial Monitor
  if (Serial.available()) {
    char data = Serial.read();
    bluetooth.print(data);
  }
}
