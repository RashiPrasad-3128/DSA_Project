// Parking_lot_Manager
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;
 
/* ---------------------------------------------------------
   Smart Parking Lot Manager
   Data structures used:
   - Array   : parking slots
   - Stack   : free slot numbers (most recently freed slot is reused first)
   - Queue   : vehicles waiting when the lot is full (FIFO)
   - HashMap : vehicle number -> slot (fast search, O(1) average)
   --------------------------------------------------------- */
 
class Vehicle {
    private String number;
    private long entryTime;
 
    public Vehicle(String number) {
        this.number = number;
        this.entryTime = System.currentTimeMillis();
    }
 
    public String getNumber() {
        return number;
    }
 
    public long getEntryTime() {
        return entryTime;
    }
 
    public void setEntryTime(long entryTime) {
        this.entryTime = entryTime;
    }
}
 
class ParkingLot {
    private static final int RATE_PER_HOUR = 20;
 
    private final Vehicle[] slots;                       // Array
    private final Stack<Integer> freeSlots = new Stack<>();          // Stack
    private final Queue<Vehicle> waitingQueue = new LinkedList<>();  // Queue
    private final HashMap<String, Integer> vehicleMap = new HashMap<>(); // Hashing
    private final HashSet<String> waitingSet = new HashSet<>();      // fast duplicate check for queue
 
    public ParkingLot(int capacity) {
        slots = new Vehicle[capacity];
        // Push in reverse so that slot 1 (index 0) is on top of the stack
        for (int i = capacity - 1; i >= 0; i--) {
            freeSlots.push(i);
        }
    }
 
    // Park a vehicle, or put it in the waiting queue if the lot is full
    public void parkVehicle(String number) {
        number = number.trim().toUpperCase();
 
        if (number.isEmpty()) {
            System.out.println("Vehicle number cannot be blank.");
            return;
        }
        if (vehicleMap.containsKey(number) || waitingSet.contains(number)) {
            System.out.println("Vehicle " + number + " is already parked or waiting.");
            return;
        }
 
        Vehicle v = new Vehicle(number);
        if (!freeSlots.isEmpty()) {
            assignSlot(v);
        } else {
            waitingQueue.add(v);
            waitingSet.add(number);
            System.out.println("Parking full. Vehicle " + number
                    + " added to waiting queue (position " + waitingQueue.size() + ").");
        }
    }
 
    // Give the top free slot (from the stack) to a vehicle
    private void assignSlot(Vehicle v) {
        int slot = freeSlots.pop();
        v.setEntryTime(System.currentTimeMillis());
        slots[slot] = v;
        vehicleMap.put(v.getNumber(), slot);
        System.out.println("Vehicle " + v.getNumber() + " parked at slot " + (slot + 1) + ".");
    }
 
    // Remove a vehicle, calculate fee, and park the next waiting vehicle
    public void removeVehicle(String number) {
        number = number.trim().toUpperCase();
        Integer slot = vehicleMap.get(number);
 
        if (slot == null) {
            System.out.println("Vehicle " + number + " not found in parking lot.");
            return;
        }
 
        Vehicle v = slots[slot];
        long minutes = (System.currentTimeMillis() - v.getEntryTime()) / 60000;
        long hours = Math.max(1, (minutes + 59) / 60); // part of an hour counts as full hour
        long fee = hours * RATE_PER_HOUR;
 
        slots[slot] = null;
        vehicleMap.remove(number);
        freeSlots.push(slot);
 
        System.out.println("Vehicle " + number + " left slot " + (slot + 1)
                + ". Parked: " + hours + " hour(s). Fee: Rs. " + fee);
 
        if (!waitingQueue.isEmpty()) {
            Vehicle next = waitingQueue.poll();
            waitingSet.remove(next.getNumber());
            System.out.println("Next vehicle from waiting queue: " + next.getNumber());
            assignSlot(next);
        }
    }
 
    // Search a vehicle using the HashMap
    public void searchVehicle(String number) {
        number = number.trim().toUpperCase();
        Integer slot = vehicleMap.get(number);
 
        if (slot != null) {
            System.out.println("Found: " + number + " is parked at slot " + (slot + 1) + ".");
        } else if (waitingSet.contains(number)) {
            System.out.println(number + " is in the waiting queue.");
        } else {
            System.out.println("Vehicle " + number + " not found in parking lot.");
        }
    }
 
    public void displaySlots() {
        System.out.println("--- PARKING SLOTS ---");
        for (int i = 0; i < slots.length; i++) {
            String info = (slots[i] == null) ? "Empty" : slots[i].getNumber();
            System.out.println("Slot " + (i + 1) + ": " + info);
        }
        if (freeSlots.isEmpty()) {
            System.out.println("Parking lot is full.");
        } else {
            System.out.println("Next free slot (stack top): " + (freeSlots.peek() + 1));
        }
    }
 
    public void displayWaitingQueue() {
        if (waitingQueue.isEmpty()) {
            System.out.println("No vehicles waiting.");
            return;
        }
        System.out.println("--- WAITING QUEUE ---");
        int position = 1;
        for (Vehicle v : waitingQueue) {
            System.out.println(position + ". " + v.getNumber());
            position++;
        }
    }
}
 
public class parking_lot_manager{
    private static final int CAPACITY = 3; // change this to make a bigger lot
 
    public static void main(String[] args) {
        ParkingLot lot = new ParkingLot(CAPACITY);
        Scanner sc = new Scanner(System.in);
        int choice = 0;
 
        while (choice != 6) {
            System.out.println();
            System.out.println("====== Smart Parking Lot Manager ======");
            System.out.println("1. Park Vehicle");
            System.out.println("2. Remove Vehicle");
            System.out.println("3. Search Vehicle (Hashing)");
            System.out.println("4. Display Parking Slots");
            System.out.println("5. Display Waiting Queue");
            System.out.println("6. Exit");
            System.out.println("---------------------------------------");
            System.out.print("Enter your choice: ");
 
            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number from 1 to 6.");
                choice = 0;
                continue;
            }
 
            switch (choice) {
                case 1:
                    System.out.print("Enter vehicle number: ");
                    lot.parkVehicle(sc.nextLine());
                    break;
                case 2:
                    System.out.print("Enter vehicle number: ");
                    lot.removeVehicle(sc.nextLine());
                    break;
                case 3:
                    System.out.print("Enter vehicle number: ");
                    lot.searchVehicle(sc.nextLine());
                    break;
                case 4:
                    lot.displaySlots();
                    break;
                case 5:
                    lot.displayWaitingQueue();
                    break;
                case 6:
                    System.out.println("Thank you for using Smart Parking Lot Manager.");
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1 to 6.");
            }
        }
        sc.close();
    }
}
 