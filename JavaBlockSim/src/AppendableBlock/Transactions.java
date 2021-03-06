package AppendableBlock;

public class Transactions {
		
	private long id = 0;
	private double[] timestamp = null;
	private Object senderId;
	private Object to;
	private int value = 0;
	private double size = 0.000546;
	private int fee = 0;
	private long previous = -1;
	
	
	
	public Transactions(Transactions t) {
		id = t.id;
		timestamp = t.timestamp;
		senderId = t.senderId;
		to = t.to;
		value = t.value;
		size = t.size;
		fee = t.fee;
		previous = t.previous;
	}
	
	public Transactions() {
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param l the id to set
	 */
	public void setId(long l) {
		this.id = l;
	}
	/**
	 * @return the timestamp
	 */
	public double[] getTimestamp() {
		return timestamp;
	}
	/**
	 * @param ds the timestamp to set
	 */
	public void setTimestamp(double[] ds) {
		this.timestamp = ds;
	}
	/**
	 * @return the sender
	 */
	public Object getSender() {
		return senderId;
	}
	/**
	 * @param senderId the sender to set
	 */
	public void setSender(Object senderId) {
		this.senderId = senderId;
	}
	/**
	 * @return the to
	 */
	public Object getTo() {
		return to;
	}
	/**
	 * @param object the to to set
	 */
	public void setTo(Object to) {
		this.to = to;
	}

	/**
	 * @return the previous
	 */
	public long getPrevious() {
		return previous;
	}
	/**
	 * @param l the previous to set
	 */
	public void setPrevious(long l) {
		this.previous = l;
	}
	
}

