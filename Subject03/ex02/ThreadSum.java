public class ThreadSum extends Thread implements Runnable {

    private int index;
    private int from;
    private int to;
    private int sum;
    Integer[] arr;

    public ThreadSum(int index, int from, int to, Integer[] arr) {
        this.index = index;
        this.from = from;
        this.to = to;
        this.sum = 0;
        this.arr = arr;
    }

    @Override
    public void run() {
        try {
            for(int i = from; i <= to; i++){
            sum += arr[i];
            }
        } catch (Exception e) {
            System.out.println(index);
        }
        System.out.println("Thread " + index + ": from " + from + " to " + to + " sum is " + sum);
        synchronized (this){
            Program.threadSum += sum;
        }
    }
}
