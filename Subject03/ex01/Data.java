public class Data {

    private final Object lock = new Object();

    public void put() {
        
        synchronized (this.lock) {
            try {
                this.lock.notify();
                while (true) {
                    this.lock.wait();
                    break ;
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        synchronized (this.lock) {
				this.lock.notify();
        }
    }
}