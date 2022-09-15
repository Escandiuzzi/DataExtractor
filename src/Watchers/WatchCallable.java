package Watchers;

import java.util.concurrent.Callable;

public class WatchCallable implements Callable<Void> {
	
	@Override
	public Void call() throws Exception {

		WatchDir wd = new WatchDir();
		wd.watchDir();

		return null;
	}

}
