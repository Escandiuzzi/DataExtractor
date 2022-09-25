import gui.MainWindow;

import persistence.ConfigPersistence;
import watchers.WatchCallable;

import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static final String folderPath = Paths.get("src","resources", "invoices").toAbsolutePath().toString();

    public static void main(String[] args) {

        ConfigPersistence configPersistence = new ConfigPersistence();

        new MainWindow(configPersistence);

        System.out.println("Starting a background thread for watching folders");

        ExecutorService executor = Executors.newCachedThreadPool();
        executor.submit(new WatchCallable(configPersistence));

        System.out.println("After submitting Callable for watching folder");
    }
}