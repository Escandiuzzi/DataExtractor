package watchers;

import persistence.ConfigPersistence;

import java.util.concurrent.Callable;

public class WatchCallable implements Callable<Void> {

	private ConfigPersistence configPersistence;

	public WatchCallable(ConfigPersistence configPersistence) {
		this.configPersistence = configPersistence;
	}

	@Override
	public Void call() throws Exception {

		WatchDir wd = new WatchDir(configPersistence);
		wd.watchDir();

		return null;
	}

}
