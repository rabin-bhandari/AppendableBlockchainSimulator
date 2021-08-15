package Simulator;

import java.util.ArrayList;

public class Queue {
	private static ArrayList<Event> eventList = new ArrayList<>();
	
	public static void addEvent(Event e) {
		eventList.add(e);
	}
	
	public static void removeEvent(Event e) {
		eventList.remove(eventList.indexOf(e));
	}
	
	public static Event getNextEvent() {
		eventList.sort((t1, t2) -> Double.compare(t1.getTime(), t2.getTime()));
		return eventList.get(0);	
	}
	
	public static int size() {
		return eventList.size();
	}
	
	public static boolean isEmpty() {
		return eventList.size() == 0;
	}
}
