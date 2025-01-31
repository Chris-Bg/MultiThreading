package MultiThreading;

// My theme: Hotel Room Reservation System

import java.util.Random;

public class RezervareRunnable implements Runnable {
    int totalRooms;
    boolean[] rooms;

    public RezervareRunnable(int totalRooms) {
        this.totalRooms = totalRooms;
        this.rooms = new boolean[totalRooms];
    }

    public synchronized int reserveRoom() {
        Random rand = new Random();
        int roomNumber = rand.nextInt(totalRooms);
        if (!rooms[roomNumber]) {
            rooms[roomNumber] = true;
            System.out.println("Room " + roomNumber + " reserved.");
            return roomNumber;
        } else {
            System.out.println("No available rooms found.");
            return -1;
        }
    }

    public synchronized int reserveSpecificRoom(int roomNumber) {
        if (!rooms[roomNumber]) {
            rooms[roomNumber] = true;
            System.out.println("Room " + roomNumber + " has been reserved.");
            return roomNumber;
        } else {
            System.out.println("Room " + roomNumber + " is unavailable.");
        }
        return -1;
    }

    public synchronized void releaseRoom(int roomNumber) {
        if( roomNumber >= 0 && roomNumber < rooms.length) {
            rooms[roomNumber] = false;
            System.out.println("Releasing room " + roomNumber + " .");
        }
    }

    public synchronized void roomsAvailable() {
        System.out.println("Rooms available: ");
        for(boolean b : rooms) {
            System.out.println(b ? " [Busy] " : " [Free] ");
        }
        System.out.println();
    }

    public synchronized int autoReserveRoom() {
        Random rand = new Random();
        int roomNumber = rand.nextInt(totalRooms);
        if (!rooms[roomNumber]) {
            rooms[roomNumber] = true;
            return roomNumber;
        } else {
            return -1;
        }
    }

    public void run() {
        System.out.println("Reserving room ...");
        int roomId = autoReserveRoom();
        try{
            Thread.sleep(4000);
        } catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
        System.out.println("Reserving room " + roomId + " finished.");
    }

}
