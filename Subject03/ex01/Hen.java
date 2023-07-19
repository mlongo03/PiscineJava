public class Hen extends Thread implements Runnable {

    public int count;
    Data       data;

    public Hen(int count, Data data) {
        this.count = count;
        this.data = data;
    }

    @Override
    public void run() {

        for (int i = 0; i < this.count; i++) {
            System.out.println("Hen");
            data.put(i, count);
        }
    }
}
