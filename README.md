# DSA_Project
Smart Parking Lot Manager

A simple console-based Java project that manages a parking lot using Array, Stack, Queue and Hashing. Made as a Data Structures and Algorithms (DSA) project.

Features
Park a vehicle and get a slot automatically
If the lot is full, the vehicle waits in a queue
When a slot becomes free, the first waiting vehicle is parked automatically
Remove a vehicle and see the parking fee (Rs. 20 per hour, minimum 1 hour)
Search a vehicle instantly by its number
Blank and duplicate vehicle numbers are not allowed
Data Structures Used
Data Structure	Used For
Array	Stores the parking slots
Stack	Stores free slot numbers (last freed slot is used first)
Queue	Stores waiting vehicles (first come, first served)
HashMap	Finds a vehicle's slot in O(1) time
Project Structure
ParkingLotManager/
├── Main.java        # Menu and user input
├── ParkingLot.java  # All parking logic
└── Vehicle.java     # Vehicle number and entry time
How to Run

You need JDK 8 or above.

javac *.java
java Main

Or open the folder in VS Code and click Run above main in Main.java.

Menu
1. Park Vehicle
2. Remove Vehicle
3. Search Vehicle (Hashing)
4. Display Parking Slots
5. Display Waiting Queue
6. Exit
Sample Output
Enter your choice: 1
Enter vehicle number: CG07AB1234
Vehicle CG07AB1234 parked at slot 1.

Enter your choice: 1
Enter vehicle number: CG07XY9999
Parking full. Vehicle CG07XY9999 added to waiting queue (position 1).

Enter your choice: 2
Enter vehicle number: CG07AB1234
Vehicle CG07AB1234 left slot 1. Parked: 1 hour(s). Fee: Rs. 20
Next vehicle from waiting queue: CG07XY9999
Vehicle CG07XY9999 parked at slot 1.
Time Complexity
Operation	Time
Park / Remove a vehicle	O(1)
Search a vehicle	O(1) average
Display slots	O(N)
Note

The lot has 3 slots by default. To change it, edit CAPACITY in Main.java.

Future Scope
Save records in a file
Different slots and rates for bikes, cars and trucks
Database and GUI
Online payment and slot reservation
Author

Rashi Prasad B.Tech CSE (Data Science), Rungta International Skills University, Bhilai Guide: Mr. Abhijeet Dutta
