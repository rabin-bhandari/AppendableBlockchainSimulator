package AppendableBlock;

import java.util.ArrayList;
import java.util.List;

import Simulator.InputConfig;

public class Node {
	
	private final Object id;
	private final String nodeType;
	private final Object gatewayIDs;
	private final ArrayList<Block> blockchain;
	private final ArrayList<Transactions> transactionsPool;
	private final int blocks;
	private final int balance;


	public Node(Object nodeID, String nodeType, Object gatewayIDs) {
		
		this.id = nodeID; 
		this.nodeType = nodeType;
		this.gatewayIDs = gatewayIDs;
		this.blockchain = new ArrayList<>();
		this.transactionsPool = new ArrayList<>();
		this.blocks = 0;
		this.balance = 0;
		
	}
	
	// Generate the genesis block and append it to the local blockchain for all nodes
	public static void generateGenesisBlock() {
		
		for (int i=0; i<InputConfig.getGn(); i++) {
			InputConfig.getNODES().get(i).getBlockchain().add(new Block());
		}
		
	}
	
	
	// Return the last block at the nodes local blockchain
	public Block lastBlock() {
		return this.getBlockchain().get(getBlockchain().size()-1);
	}
	
	// Return the number of blocks on the blockchain
	public int blockchainLength() {
		return this.getBlockchain().size();
	}
	
	public void resetState() {
		this.getBlockchain().clear();
		this.getTransactionsPool().clear();
	}

	/**
	 * @return the transactionsPool
	 */
	public ArrayList<Transactions> getTransactionsPool() {
		return transactionsPool;
	}
	
	public Object getId() {
		return id;
	}
	
	public Object getGatewayIds() {
		return gatewayIDs;
	}
	

	/**
	 * @return the blockchain
	 */
	public ArrayList<Block> getBlockchain() {
		return blockchain;
	}

}
