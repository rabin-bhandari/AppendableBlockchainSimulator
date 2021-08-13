package Simulator;

import AppendableBlock.Block;

public class Event {

	private String type;
	private Object nodeId;
	private double time;
	private Block block;

	
	/**
	 * @param type
	 * @param nodeId
	 * @param time
	 * @param block
	 */
	public Event(String type, Object nodeId, double time, Block block) {
		super();
		this.type = type;
		this.nodeId = nodeId;
		this.time = time;
		this.block = block;
	}
	
	
	
	/**
	 * @return the block
	 */
	public Block getBlock() {
		return block;
	}
	/**
	 * @param block the block to set
	 */
	public void setBlock(Block block) {
		this.block = block;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the node
	 */
	public Object getNodeId() {
		return nodeId;
	}
	/**
	 * @param node the node to set
	 */
	public void setNodeId(Object nodeId) {
		this.nodeId = nodeId;
	}
	/**
	 * @return the time
	 */
	public double getTime() {
		return time;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(long time) {
		this.time = time;
	}

}
