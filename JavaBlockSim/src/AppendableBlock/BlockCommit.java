package AppendableBlock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import Simulator.Queue;
import Simulator.Event;
import Simulator.InputConfig;
import Simulator.Scheduler;
import Simulator.Statistics;

public class BlockCommit {
	
	public static void handleEvent(Event event) throws CloneNotSupportedException {
		if (event.getType() == "create_block") {
			BlockCommit.createBlock(event);
		} else if (event.getType() =="append_txlist") {
			BlockCommit.appendTxList(event);
		} else if (event.getType() =="receive_tx_list") {
			BlockCommit.receiveTxList(event);
		}
	}

	
	private static void createBlock(Event event) {
		Statistics.total_blocks++;
		int index = InputConfig.getGATEWAY_IDS().indexOf(event.getBlock().getReceiverGatewayId());
		Node node = InputConfig.getNODES().get(index);
		event.getBlock().setPrevious(node.lastBlock().getId());
		node.getBlockchain().add(event.getBlock());
	}
	
	
	private static void appendTx(Transactions tx, ArrayList<Transactions> blockledger) {
		int txCount = blockledger.size();
		if (txCount > 0) {
			tx.setPrevious(blockledger.get(txCount-1).getId());
		}
		blockledger.add(tx);
	}
	
	// Appends a transaction list to specified gateway's block ledger
	private static void appendTxList(Event event) throws CloneNotSupportedException {
		int index = InputConfig.getGATEWAY_IDS().indexOf(event.getNodeId());
		Node gatewayNode = InputConfig.getNODES().get(index);
		double txInsertionDelay = Math.log(1-Math.random()/(InputConfig.getInsertTxDelay()));

		double txInsertionDelayIncrement = txInsertionDelay;
		int txCount = 1;
		
		for (Transactions tx : event.getBlock().getTransactions()) {
			Transactions t = (Transactions) tx.clone();
			t.getTimestamp()[2] = event.getBlock().getTimestamp() + txInsertionDelayIncrement;
			appendTx(t, gatewayNode.getBlockchain().get(tx.getSender() + InputConfig.getGn()).getTransactions());
			txCount++;
			txInsertionDelayIncrement += txCount * (txInsertionDelay/InputConfig.getTxListSize());
		}
	}
	
	private static void receiveTxList(Event event) throws CloneNotSupportedException {
		int index = InputConfig.getGATEWAY_IDS().indexOf(event.getNodeId());
		Node gatewayNode = InputConfig.getNODES().get(index);
		double gatewayPropDelay = Network.txListPropDelay();
		double txInsertionDelay = Math.log(1-Math.random()/(InputConfig.getInsertTxDelay()));
		double txInsertionDelayIncrement = txInsertionDelay;
		int txCount = 1;
		for (Transactions tx : event.getBlock().getTransactions()) {
			Transactions t = (Transactions) tx.clone();
			t.getTimestamp()[2] = event.getBlock().getTimestamp() + gatewayPropDelay + txInsertionDelayIncrement;
			appendTx(t, gatewayNode.getBlockchain().get(tx.getSender() + InputConfig.getGn()).getTransactions());
			txCount++;
			txInsertionDelayIncrement += txCount * (txInsertionDelay/InputConfig.getTxListSize());
		}
	}
	

	//Schedules a "receive transaction list" event for the specified gateway
	private static void scheduleEventPropTxList(ArrayList<Transactions> txList, ArrayList<Object> gatewayIds, double txTokenTime) {
		for (Object id : gatewayIds) {
			int listTime = 0;
			Scheduler.receiveTxListEvent(txList, id, txTokenTime, listTime);
		}	
	}
	
	public static void generateInitialEvents() {
		for (String id : InputConfig.getGATEWAY_IDS()) {
			for (Node n : InputConfig.getNODES()) {
				Scheduler.createBlockEvent(n, 0, id);
			}
		}
	}
	
	//Checks if all transactions are processed
	private static boolean transactionsProcessed() {
		boolean processed = true;
		for (int i =0; i< InputConfig.getGn()-1; i++) {
			if (InputConfig.getNODES().get(i).getTransactionsPool().size() > 0) {
				processed = false;
				break;
			}
		}
		return processed;
	}
	
	// Processes all the transaction events in the queue
	private static void processQueue() throws CloneNotSupportedException {
		while (Queue.isEmpty()) {
			Event nextEvent = Queue.getNextEvent();
			handleEvent(nextEvent);
			Queue.removeEvent(nextEvent);
		}
	}

	// Processes all the gateway transaction pools
	public static void processGateawayTransactionPools() throws CloneNotSupportedException {
		double txTokenTime = 0.0;
		
		// Loop processing all the gateway transaction events in the system
		while (!transactionsProcessed()) {
			boolean txListInserted = false;
			
			// Randomly allocates transaction token to a gateway
			Node gatewayNode = InputConfig.getNODES().get(ThreadLocalRandom.current().nextInt(InputConfig.getGn()));
			
			
            // Sort the transaction by receive time in ascending order
			gatewayNode.getTransactionsPool().sort((t1, t2) -> Double.compare(t1.getTimestamp()[1], t2.getTimestamp()[1]));
			
			int txPoolSize = gatewayNode.getTransactionsPool().size();
			ArrayList<Transactions> txList = new ArrayList<>();
			
			if (txPoolSize > 0) {
				int txCount = Math.min(InputConfig.getTxListSize(), txPoolSize);
				
				for (int i = 0; i<txCount ;i++) {
					Transactions tx = gatewayNode.getTransactionsPool().get(i);
					
					if (tx.getTimestamp()[1] <= txTokenTime) {
						txList.add(tx);
					}
					
					if (txList.size() > 0) {
						Scheduler.appendTxListEvent(txList, gatewayNode.getId(), txTokenTime, 0);
						scheduleEventPropTxList(txList, (ArrayList<Object>) gatewayNode.getGatewayIds(), txTokenTime); // <<<<<<<----- NEEDS ATTENTION!!
						
						for (Transactions t : txList) {
							gatewayNode.getTransactionsPool().remove(t);
						}
						
						if (InputConfig.getMaxTxListSize() < txList.size()) {
							InputConfig.setMaxTxListSize(txList.size());
						}
						txListInserted = true;
					}
				}
			}
			
			if (txListInserted) {
				txTokenTime += Network.txListPropDelay() + Network.txTokenReleaseDelay();
			} else {
				txTokenTime += Network.txTokenReleaseDelay();
			}
		}
		processQueue();
	}

}