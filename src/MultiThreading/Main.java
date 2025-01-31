package MultiThreading;

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.*;

public class Main{
    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of rooms: ");
        int numberRooms = scanner.nextInt();

        RezervareThread threadTask = new RezervareThread(numberRooms);
        threadTask.start();

        RezervareRunnable runnableTask = new RezervareRunnable(numberRooms);
//        Thread thread1 = new Thread(runnableTask);
//        thread1.start();

        Callable<Integer> callableTask = () -> {
            Random random = new Random();
            int roomNumber = random.nextInt(numberRooms);
            System.out.println("Reserving room (via callable): " + roomNumber);
            RezervareThread callableReserve = new RezervareThread(roomNumber);
            return callableReserve.reserveSpecificRoom(roomNumber);
        };

        Future<Integer> futureTask = executorService.submit(callableTask);
        try{
            int reserveRoom = futureTask.get();
            if (reserveRoom != -1){
                System.out.println("Room " + reserveRoom + " was reserved succefully!");
            } else {
                System.out.println("Reservation failed!");
            }
        } catch (Exception e) {
            System.out.println("Error occured during reservation...");
        }

        scheduledExecutorService.scheduleAtFixedRate(() -> {
            RezervareRunnable reserveRunnable = new RezervareRunnable(numberRooms);
            int roomNumber = reserveRunnable.autoReserveRoom();
            System.out.println("Room " + roomNumber + " was reserved succefully!");
        }, 0, 3, TimeUnit.SECONDS);

        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        executorService.shutdown();
        scheduledExecutorService.shutdown();

        System.out.println("Application shutting down ... ");

        //RezervareThread thread1 = new RezervareThread(20);
    }
}