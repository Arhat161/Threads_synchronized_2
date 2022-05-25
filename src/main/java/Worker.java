import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Worker {

    Random random = new Random();

    final Object lock1 = new Object(); // first thread will be synchronized to this Object lock1
    final Object lock2 = new Object(); // second thread will be synchronized to this Object lock2

    private final List<Integer> list1 = new ArrayList<>(); // first list for random digits
    private final List<Integer> list2 = new ArrayList<>(); // second list for random digits

    public void addToList1() { // first method to add random digits to list1
        synchronized (lock1) { // in this point we synchronized to monitor of object 'lock1'
            try {
                Thread.sleep(1); // sleep 1 ms
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            list1.add(random.nextInt(100)); // add digit to list1
        }
    }

    public void addToList2() { // second method to add random digits to list2
        synchronized (lock2) { // in this point we synchronized to monitor of object 'lock2'
            try {
                Thread.sleep(1); // sleep 1 ms
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            list2.add(random.nextInt(100)); // add digit to list1
        }
    }

    public void work() { // add random 1000 digits to list1 and 1000 random digits to list 2
        for (int i = 0; i < 1000; i++) {
            addToList1();
            addToList2();
        }
    }

    public void main() {

        long before = System.currentTimeMillis(); // timestamp of program begin

        // first thread
        Thread thread1 = new Thread(this::work);
        /*

        or

        Thread thread1 = new Thread(new Runnable() { // first thread
            @Override
            public void run() {
                work();
            }
        });

        */

        // second thread
        Thread thread2 = new Thread(this::work);
        /*

        or

        Thread thread2 = new Thread(new Runnable() { // second thread
            @Override
            public void run() {
                work();
            }
        });

         */

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long after = System.currentTimeMillis();

        System.out.println("Program took " + (after - before) + " ms to run");

        System.out.println("List1 size : " + list1.size());
        System.out.println("List2 size : " + list2.size());
    }
}