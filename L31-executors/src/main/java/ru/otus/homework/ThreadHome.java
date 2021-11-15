package ru.otus.homework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.monitor.PingPong;

public class ThreadHome {
    private static final Logger logger = LoggerFactory.getLogger(ThreadHome.class);
    private String last = "td2";
    public static void main(String[] args) {
        ThreadHome threadHome = new ThreadHome();
        new Thread(() -> threadHome.action("td1")).start();
        new Thread(() -> threadHome.action("td2")).start();

    }

    private synchronized void action(String name) {
        while (true) {
            try {
                for (int i = 1; i < 11; i++) {
                    while (last.equals(name)) {
                        wait();
                    }
                    sleep(i);
                    last = name;
                    notifyAll();
                }
                logger.info("finish 1");
                for (int i = 9; i >= 0; i--) {
                    while (last.equals(name)) {
                        wait();
                    }
                    sleep(i);
                    last = name;
                    notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            notifyAll();
        }
    }

    private void sleep(int print) throws InterruptedException {
        logger.info(String.valueOf(print));
        Thread.sleep(200);
    }
}
