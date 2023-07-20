public class Program {

    public static void main(String[] args) {
        String[]    input;
        int         count = 0;

        if(args.length != 1){
            System.err.println("Error: wrong args length");
            System.exit(-1);
        }
        input = args[0].split("=");
        if(!input[0].equals("--count")){
            System.err.println("Error: wrong flag");
            System.exit(-1);
        }
        try {
            count = Integer.parseInt(input[1]);
        } catch (NumberFormatException e) {
            System.out.println("Not a integer");
        }

        Hen henThread = new Hen();
        Egg eggThread = new Egg();

        for (int i = 0; i < count; i++) {
            henThread = new Hen();
            eggThread = new Egg();
            henThread.start();
            eggThread.start();
        }
        try {
            henThread.join();
            eggThread.join();
        } catch (InterruptedException e) {
			System.out.println("Error: thread has been interrupted");
			System.exit(-1);
		}
		for (int i = 0; i < count; i++) {
			System.out.println("Human");
		}
    }
}
