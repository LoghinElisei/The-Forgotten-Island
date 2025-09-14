package PaooGame.Timer;


public class Timer {
    private static Timer instance;

    private static long startTime;
    private static long elapsedTime;
    private static boolean running;

    private Timer(){
        startTime =0 ;
        elapsedTime =0;
        running =false;
    }

    public static Timer getInstance(){
        if(instance == null){
            instance = new Timer();
        }
        return instance;
    }

    public static void start()
    {
        if(!running)
        {
            startTime = System.nanoTime();
            running = true;
        }
    }

    public static void stop()
    {
        if(running)
        {
            elapsedTime += System.nanoTime() - startTime;
            running = false;
        }
    }

    public static double getElapsedTime(){
        if(running)
        {
            return (System.nanoTime() - startTime + elapsedTime) / 1_000_000_000.0; //seconds
        }else {
            return elapsedTime / 1_000_000_000.0;
        }
    }
    public static void setElapsedTime(int time){
        elapsedTime = time * 1_000_000_000L;

        if(running)
        {
            startTime = System.nanoTime();
        }

    }
    public static void reset(){
        elapsedTime = 0;
        if(running)
        {
            startTime = System.nanoTime();
        }
    }

}
