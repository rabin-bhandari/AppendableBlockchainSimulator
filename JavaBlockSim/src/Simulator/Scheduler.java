package Simulator;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import AppendableBlock.Block;
import AppendableBlock.Node;
import AppendableBlock.Transactions;

public class Scheduler {
	
// Schedule a block creation event for a miner and add it to the event list (Appendable Block Model)

	public static void createBlockEvent(Node node, long eventTime, String receiverGatewayId) { 
		String eventType = "create_block";
		if (eventTime <= InputConfig.getSimTime()) {
			// Populate event attributes
			Block block = new Block();
			block.setId(ThreadLocalRandom.current().nextLong(100000000000L));
			block.setTimestamp(eventTime);
			block.setNodeId(node.getId());
			block.setGatewayIds(node.getGatewayIds());
			block.setReceiverGatewayId(receiverGatewayId);
			
			// Create the event
			Event event = new Event(eventType, node.getId(), eventTime, block);
			// Append the event to the event list
			Queue.addEvent(event);
		}
	}

	
// Schedule a block receiving event for a node and append to event list
	public static void receiveBlockEvent(Node recipient, Block block, double blockDelay) {
		String eventType = "receive_block";
		double receiveBlockTime = block.getTimestamp() + blockDelay;
		if (receiveBlockTime <= InputConfig.getSimTime()) {
			Event event = new Event(eventType, recipient.getId(), receiveBlockTime, block);
			Queue.addEvent(event);
		}
	}


	@SuppressWarnings("unchecked")
	public static void appendTxListEvent(ArrayList<Transactions> txList, Object gatewayId, double txTokenTime, double eventTime) {
		String eventType = "append_tx_list";
		if (eventTime <= InputConfig.getSimTime()) {
			Block block = new Block();
			block.setTransactions((ArrayList<Transactions>) txList.clone());
			block.setTimestamp(txTokenTime);
			
			Event event = new Event(eventType, gatewayId, eventTime, block);
			
			Queue.addEvent(event);
		}
	}
	
	
	// Schedule a transaction list receiving event for a gateway
	@SuppressWarnings("unchecked")
	public static void receiveTxListEvent(ArrayList<Transactions> txList, Object gatewayId, double txTokenTime, double eventTime) {
       String eventType = "receive_tx_list";
		if (eventTime <= InputConfig.getSimTime()) {
			Block block = new Block();
			block.setTransactions((ArrayList<Transactions>) txList.clone());
			block.setTimestamp(txTokenTime);
			
			Event event = new Event(eventType, gatewayId, eventTime, block);
			
			Queue.addEvent(event); 
		}
	}
}
