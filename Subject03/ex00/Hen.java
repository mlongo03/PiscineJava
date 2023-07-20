public class Hen extends Thread implements Runnable {

    public Hen() {
    }

    @Override
    public void run() {
            System.out.println("Hen");
    }
}
