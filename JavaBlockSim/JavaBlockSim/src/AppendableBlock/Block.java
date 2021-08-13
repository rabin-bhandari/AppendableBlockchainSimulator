package AppendableBlock;
import java.util.ArrayList;

public class Block {
	private int depth;
	private long id;
	private long previous;
	private double timestamp;
	private Object miner;
	private ArrayList<Transactions> transactions;
	private double size;
	private Object nodeId;
	private Object gatewayIds;
	private Object receiverGatewayId;
	
	
	
	public Block() {
		super();
		this.depth = 0;
		this.id = 0;
		this.previous = -1;
		this.timestamp = 0;
		this.miner = -1;
		this.transactions = new ArrayList<>();
		this.size = 1.0;
		this.nodeId = 0;
		this.gatewayIds = "x";
		this.receiverGatewayId = "x";
		
	}


	/**
	 * @param depth
	 * @param id
	 * @param previous
	 * @param timestamp
	 * @param miner
	 * @param transactions
	 * @param size
	 * @param nodeId
	 * @param gatewayIds
	 * @param recieverGatewayId
	 */
	public Block(int depth, int id, long previous, double timestamp, Object miner, ArrayList<Transactions> transactions,
			double size, Object nodeId, Object gatewayIds, Object receiverGatewayId) {
		super();
		this.depth = depth;
		this.id = id;
		this.previous = previous;
		this.timestamp = timestamp;
		this.miner = miner;
		this.transactions = transactions;
		this.size = size;
		this.nodeId = nodeId;
		this.gatewayIds = gatewayIds;
		this.receiverGatewayId = receiverGatewayId;
	}


	/**
	 * @return the previous
	 */
	public long getPrevious() {
		return previous;
	}
	

	public void setPrevious(long l) {
		this.previous = l;
	}


	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}


	/**
	 * @return the receiverGatewayId
	 */
	public Object getReceiverGatewayId() {
		return receiverGatewayId;
	}


	/**
	 * @return the transactions
	 */
	public ArrayList<Transactions> getTransactions() {
		return transactions;
	}


	/**
	 * @return the timestamp
	 */
	public double getTimestamp() {
		return timestamp;
	}


	/**
	 * @return the miner
	 */
	public Object getMiner() {
		return miner;
	}


	public int getDepth() {
		return depth;
	}


	public void setDepth(int depth) {
		this.depth = depth;
	}


	public double getSize() {
		return size;
	}


	public void setSize(double size) {
		this.size = size;
	}


	public Object getNodeId() {
		return nodeId;
	}


	public void setNodeId(Object id) {
		this.nodeId = id;
	}


	public Object getGatewayIds() {
		return gatewayIds;
	}


	public void setGatewayIds(Object id) {
		this.gatewayIds = id;
	}


	public void setId(long l) {
		this.id = l;
	}


	public void setTimestamp(double txTokenTime) {
		this.timestamp = txTokenTime;
	}


	public void setMiner(Object minerId) {
		this.miner = minerId;
	}


	public void setTransactions(ArrayList<Transactions> transactions) {
		this.transactions = transactions;
	}


	public void setReceiverGatewayId(Object receiverGatewayId) {
		this.receiverGatewayId = receiverGatewayId;
	}
	
	
}