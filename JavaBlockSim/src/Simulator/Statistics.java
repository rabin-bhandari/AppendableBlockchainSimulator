package Simulator;

import java.util.ArrayList;
import AppendableBlock.Block;
import AppendableBlock.Node;
import AppendableBlock.Transactions;

public class Statistics {
	
	public static int total_blocks = 0;
	private static ArrayList<Object[]> chains = new ArrayList<>();
	private static ArrayList<Object[]> transactions = new ArrayList<>();
	private static ArrayList<Object[]> transactionLatencies = new ArrayList<>();
	public static double averageTransactionLatency = 0;
	public static double transactionThroughput = 0;
	public static double simulationDuration = 0;
	

// Gathers simulation data and calculates results
	public static void calculate() {
		gatewayChains();
		gatewayTransactions();
		transactionLatency();
		results();
	}

	private static void gatewayChains() {
		for(int i=0; i<InputConfig.getGn(); i++) {
			Node gatewayNode = InputConfig.getNODES().get(i);
			for (Block b : gatewayNode.getBlockchain()) {
				Object[] info = {
								gatewayNode.getId(),
								b.getDepth(),
								b.getId(),
								b.getPrevious(),
								b.getTimestamp(),
								b.getTransactions().size()};
				getChains().add(info);
			}
		}
	}
	
	private static void gatewayTransactions() {
		for(int i=0; i<InputConfig.getGn(); i++) {
			Node gatewayNode = InputConfig.getNODES().get(i);	
			for (Block b : gatewayNode.getBlockchain()) {
				for (Transactions tx: b.getTransactions()) {
					Object[] info = {
									gatewayNode.getId(),
									tx.getId(),
									tx.getSender(),
									tx.getTo(),
									tx.getTimestamp()[0],
									tx.getTimestamp()[1],
									tx.getTimestamp()[2]};
					getTransactions().add(info);
				}
			}
		}
	}
	
	private static void transactionLatency() {
		ArrayList<Object[]> sortedTx = new ArrayList<>(getTransactions());
		
		//Sort transactions according to transaction ID
		sortedTx.sort((tx1, tx2) -> Long.compare((Long) tx1[1], (Long) tx2[1]));
		double maxInsertionTime = 0;
		int txCount = 0;
		for (Object[] tx : sortedTx) {
			txCount++;
			if ((Double) tx[6] > maxInsertionTime) {
				maxInsertionTime = (Double) tx[6];
			}
			
			if (txCount % InputConfig.getGn() == 0) {
				double latency = maxInsertionTime - (Double) tx[4];
				Object[] info = {tx[1], latency};
				
				// Insert transaction Id and Latency
				getTransactionLatencies().add(info);
				maxInsertionTime = 0;
			}
		}
	}

	// Calculate Results
	private static void results() {
		// Average latency
		double totalLatency = 0;
		for (Object[] l : getTransactionLatencies()) {
			totalLatency += (Double) l[1];
		}
		averageTransactionLatency = totalLatency / getTransactionLatencies().size();
		
		// Obtain the earliest transaction creation time and the latest transaction insertion time
		
		double earliestTxCreationTime = 9999999999.0;
		double latestTxInsertionTime = 0.0;
		
		for (Object[] tx : getTransactions()) {
			if ((double) tx[4] < earliestTxCreationTime) {
				earliestTxCreationTime = (double) tx[4];
			}
			
			if ((double) tx[6] > latestTxInsertionTime) {
				latestTxInsertionTime = (double) tx[6];
			}
		}
		
		// Calculation Simulation Duration
		simulationDuration = latestTxInsertionTime - earliestTxCreationTime;
		
		// Calculate transaction throughput (transactions per second)
		float numberOfTx = InputConfig.getDn() * InputConfig.getGn() * InputConfig.getTn();
		
		transactionThroughput = numberOfTx/simulationDuration;
		
	}
	

	// Reset the statistics data and clear global variables ready for the next simulation run
	public static void reset() {
		
		// Initialise class variables
		total_blocks = 0;
		chains.clear();
		transactions.clear();
		transactionLatencies.clear();
		averageTransactionLatency = 0.0;
		transactionThroughput = 0.0;
		simulationDuration = 0.0;
		
		// Initialise gatewayNode variables
		
		for (int i=0 ; i<InputConfig.getGn(); i++) {
			Node node = InputConfig.getNODES().get(i);
			node.resetState();
		}
	}

	public static ArrayList<Object[]> getChains() {
		return chains;
	}

	public static void setChains(ArrayList<Object[]> chains) {
		Statistics.chains = chains;
	}

	public static ArrayList<Object[]> getTransactions() {
		return transactions;
	}

	public static void setTransactions(ArrayList<Object[]> transactions) {
		Statistics.transactions = transactions;
	}

	public static ArrayList<Object[]> getTransactionLatencies() {
		return transactionLatencies;
	}

	public static void setTransactionLatencies(ArrayList<Object[]> transactionLatencies) {
		Statistics.transactionLatencies = transactionLatencies;
	}
}
