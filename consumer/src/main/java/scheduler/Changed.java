package scheduler;

import java.util.HashMap;
import java.util.Map.Entry;

public class Changed implements Changeable {
	HashMap<String, String> oldMap = new HashMap<String, String>();
	HashMap<String, String> newMap = new HashMap<String, String>();

	public void setNewMap(HashMap<String, String> newMap) {
		this.newMap = newMap;
	}

	public Changed() {

	}

	public synchronized void execute() {
		System.out.println("I'm being executed...");
	}

	@Override
	public boolean compare() {
		oldMap.clear();
		for (Entry<String, String> entry : newMap.entrySet()) {
			System.out.println(entry.getKey() + " new to:old " + entry.getValue());
			oldMap.put(entry.getKey(), entry.getValue());
		}

		for (Entry<String, String> entry : oldMap.entrySet()) {
			doOnChange(entry);
		}
		// object not changed
		if (oldMap.isEmpty()) {
			return true;
		}
		// object has changed
		return false;
	}
	
	private void doOnChange(Object object) {
		Entry<String,String>entry = (Entry<String, String>) object;
		System.out.println(entry.getKey() + " <<Emit>> " + entry.getValue());
		newMap.remove(entry.getKey());
	}

}
