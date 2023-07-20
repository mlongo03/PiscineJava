public class Egg extends Thread implements Runnable {

    private int count;
    Data        data;

    public Egg(int count, Data data, int index) {
        this.count = count;
        this.data = data;
    }

    @Override
    public void run() {

        for (int i = 0; i < this.count; i++) {
			System.out.println("Egg");
            data.put(i, count);
        }
    }
}
