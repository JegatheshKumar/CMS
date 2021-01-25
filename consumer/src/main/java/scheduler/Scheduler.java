package scheduler;

import java.lang.reflect.Method;
import java.util.Calendar;

public class Scheduler {

	public void setObservableInstance(Object ObservableInstance) {
		this.ObservableInstance = ObservableInstance;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public void setCalendarTimeUnit(int calendarTimeUnit) {
		this.calendarTimeUnit = calendarTimeUnit;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public void setSleepFor(long sleepFor) {
		this.sleepFor = sleepFor;
	}

	public void setInitialDelay(long initialDelay) {
		this.initialDelay = initialDelay;
	}

	public void setThread(Thread thread) {
		this.thread = thread;
	}

	public void setExceptionsLimit(int exceptionsLimit) {
		this.exceptionsLimit = exceptionsLimit;
	}

	public Object ObservableInstance;
	public String methodName;
	public int calendarTimeUnit;
	public int value;
	public long sleepFor;
	public long initialDelay;
	public int exceptionsLimit;

	private Thread thread;

	private Scheduler() {

	}

	public Scheduler(Object ObservableInstance, String methodName, int calendarTimeUnit, int value, long sleepFor,
			long initialDelay, int exceptionsLimit) {

		this.ObservableInstance = ObservableInstance;
		this.methodName = methodName;
		this.calendarTimeUnit = calendarTimeUnit;
		this.value = value;
		this.sleepFor = sleepFor;
		this.initialDelay = initialDelay;
		this.exceptionsLimit = exceptionsLimit;
		Scheduler scheduler = new Scheduler();
		try {

			scheduler.setObservableInstance(ObservableInstance);
			scheduler.setMethodName(methodName);
			scheduler.setCalendarTimeUnit(calendarTimeUnit);
			scheduler.setValue(value);
			scheduler.setSleepFor(sleepFor);
			scheduler.setInitialDelay(initialDelay);

			this.thread = new Thread() {

				Method method = ObservableInstance.getClass().getDeclaredMethod(methodName);

				@Override
				public void run() {
					if (scheduler.initialDelay < 0L) {
						scheduler.initialDelay = 0L;
					}

					boolean valid = scheduler.calendarTimeUnit == Calendar.HOUR ? true
							: scheduler.calendarTimeUnit == Calendar.MINUTE ? true
									: scheduler.calendarTimeUnit == Calendar.SECOND ? true : false;
					String typeString = "";
					typeString = scheduler.calendarTimeUnit == Calendar.HOUR ? "HOUR(S)"
							: scheduler.calendarTimeUnit == Calendar.MINUTE ? "MINUTE(S)"
									: scheduler.calendarTimeUnit == Calendar.SECOND ? "SECOND(S)" : "";

					if (!valid) {
						System.out.println(
								"The param calendarTimeUnit must be any of Calendar.HOUR/Calendar.MINUTE/Calendar.SECOND..");
						this.interrupt();
					}

					Calendar cal = Calendar.getInstance();
					int from = cal.get(scheduler.calendarTimeUnit);
					cal.add(scheduler.calendarTimeUnit, scheduler.value);
					int to = cal.get(scheduler.calendarTimeUnit);
					System.out.println("Scheduler started at " + Calendar.getInstance().getTime() + " for " + value
							+ " : " + typeString);

					int exceptionCounter = 0;
					try {
						Thread.sleep(scheduler.initialDelay);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					while (!(from == to)) {

						if (exceptionCounter > 3) {
							System.out
									.println("More than " + scheduler.exceptionsLimit + " exceptions have occured in the method "
											+ scheduler.methodName + ". Stopping the scheduler...");
							break;
						}

						try {
							boolean isChanged = false;
							Thread.sleep(scheduler.sleepFor);
							isChanged = (boolean) method.invoke(ObservableInstance);

							if (!isChanged) {
								System.out.println(
										"precautionary stopping of scheduler  : reason : no change in watched object");
								this.interrupt();
							}

							from = Calendar.getInstance().get(scheduler.calendarTimeUnit);
						} catch (Exception e) {
							exceptionCounter++;
							e.printStackTrace();
						}

					}
					System.out.println("Scheduler stopped at " + Calendar.getInstance().getTime());
				}
			};
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String args[]) {
		Scheduler sched = new Scheduler(new Changed(), "compare", Calendar.SECOND, 100, 1000, 0,3);
		sched.start();
		sched = new Scheduler(new Changed(), "compare", Calendar.SECOND, 100, 1000, 10000,3);
		sched.start();
	}

	public boolean start() {
		// TODO Auto-generated method stub
		this.thread.start();
		
		return this.thread.isAlive();
	}

}
