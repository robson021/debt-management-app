package robert.svc.api;

public interface AsyncTaskService {

	void submit(Runnable task);

}
