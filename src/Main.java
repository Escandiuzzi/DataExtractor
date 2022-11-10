import gui.MainWindow;

import persistence.ConfigPersistence;
import watchers.WatchCallable;


import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {

        var configPersistence = new ConfigPersistence();

        new MainWindow(configPersistence);

        System.out.println("Starting a background thread for watching folders");

        var executor = Executors.newCachedThreadPool();
        executor.submit(new WatchCallable(configPersistence));

        System.out.println("After submitting Callable for watching folder");
    }
}