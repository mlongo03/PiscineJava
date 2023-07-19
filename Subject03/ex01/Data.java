public class Data {

    private final Object lock = new Object();

    public void put(int i, int count) {

        synchronized (this.lock) {
            try {
                this.lock.notify();
                if (i != count - 1) {
                    this.lock.wait();
                }
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
