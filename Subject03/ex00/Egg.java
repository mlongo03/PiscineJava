public class Egg extends Thread implements Runnable {

    public Egg() {
    }

    @Override
    public void run() {
			System.out.println("Egg");
    }
}
