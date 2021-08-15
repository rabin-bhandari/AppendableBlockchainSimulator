package Simulator;

import java.util.ArrayList;

public class Queue {
	private static ArrayList<Event> eventList = new ArrayList<>();
	
	public static void addEvent(Event e) {
		getEventList().add(e);
	}
	
	public static void removeEvent(Event e) {
		getEventList().remove(getEventList().indexOf(e));
	}
	
	public static Event getNextEvent() {
		getEventList().sort((t1, t2) -> Double.compare(t1.getTime(), t2.getTime()));
		return getEventList().get(0);	
	}
	
	public static int size() {
		return getEventList().size();
	}
	
	public static boolean isEmpty() {
		return getEventList().size() == 0;
	}

	public static ArrayList<Event> getEventList() {
		return eventList;
	}

	public static void setEventList(ArrayList<Event> eventList) {
		Queue.eventList = eventList;
	}
}
