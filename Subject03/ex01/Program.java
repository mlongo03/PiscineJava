public class Program {

    public static void main(String[] args) {

        String[]    input;
        int         count = 0;

        if (args.length != 1) {
            System.err.println("Error: wrong args length");
            System.exit(-1);
        }
        input = args[0].split("=");
        if (!input[0].equals("--count")) {
            System.err.println("Error: wrong flag");
            System.exit(-1);
        }
        try {
            count = Integer.parseInt(input[1]);
        } catch (NumberFormatException e) {
            System.out.println("Not a integer");
        }

        Data    data = new Data();
        Hen     henThread = new Hen(count, data);
        Egg     eggThread = new Egg(count, data);

        eggThread.start();
        henThread.start();
        try {
            henThread.join();
            eggThread.join();
        } catch (InterruptedException e) {
			System.out.println("Error: thread has been interrupted");
			System.exit(-1);
        }
    }
}
