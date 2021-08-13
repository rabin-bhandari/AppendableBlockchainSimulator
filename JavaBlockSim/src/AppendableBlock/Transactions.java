package AppendableBlock;

public class Transactions {
		
	private long id = 0;
	private double[] timestamp = null;
	private int sender = 0;
	private Object to = 0;
	private int value = 0;
	private double size = 0.000546;
	private int fee = 0;
	private long previous = -1;
	
	
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
	public int getSender() {
		return sender;
	}
	/**
	 * @param sender the sender to set
	 */
	public void setSender(int sender) {
		this.sender = sender;
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
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
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

