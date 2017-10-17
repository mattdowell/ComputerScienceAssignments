package program3;

public class LogOutputHandler implements OutputHandler {

	private Log log;

	public LogOutputHandler() {
		log = Log.getInstance();
		log.init();
	}

	@Override
	public void out(String in) {
		log.debug(in);
	}

	@Override
	public void event(long time, String event, String bathroom, String queue) {
		String full = "Time " + time + "\t" + event + "\t Bathroom = " + bathroom + "\t Queue = " + queue;
		log.write(full);
	}

	@Override
	public void close() {
		log.close();
	}

}
