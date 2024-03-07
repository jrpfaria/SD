package assignment1.entities;

import java.lang.Math;

public class Test extends Thread {
    private int id;
    
    public Test(ThreadGroup group, int id) {
        super(group, String.format("Test-%d", id));
        this.id = id;
    }

    public int id() {
        return id;
    }

    @Override
    public void run() {
        System.out.println(String.format("%s starting", this.getName()));
        try {
            int time = (int)(Math.random()*10000);
            sleep(time);
        }
        catch (InterruptedException e) {}
        System.out.println(String.format("%s stopping", this.getName()));
    }
}
