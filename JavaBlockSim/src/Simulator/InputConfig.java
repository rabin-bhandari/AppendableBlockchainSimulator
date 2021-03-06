package Simulator;
import java.util.ArrayList;
import java.util.Arrays;

import AppendableBlock.Node;

public class InputConfig {
    
	/** Transaction Parameters **/
	
	private static final boolean hasTrans = true; 
    
    private static final String Ttechnique = "Full";
    
    private static final int Tn = 10; // Rate of number of transactions per second
    
    private static final int txListSize = 100; // Max transactions that can be added to transaction list
    
    
    /** Node Parameters **/
    
    private static final int Dn = 10; // # of devices per gateway in the network
    
    private static final int Gn = 2; // # of gateway nodes in the network
    
    private static final int Nn = getGn() + (getGn()*getDn()); // # Total number of nodes in the network
    
    private static ArrayList<Node> NODES = new ArrayList<>(); // List of all nodes 
    
    private static ArrayList<String> GATEWAY_IDS = new ArrayList<>(); // List of all Gateway Id's
  

	/** Simulation Parameters **/
    
    private static final double propTxDelay = 0.000690847927; // average transaction propagation delay in seconds
    
    private static final double propTxListDelay = 0.00864894; // average transaction list propagation delay in seconds

    private static final double insertTxDelay = 0.000010367235; // average transaction insertion delay in seconds

    private static final int simTime = 500; // average transaction insertion delay in seconds

    private static final int Runs = 5; // simulation length in seconds
    
    /** Verification **/

	private static final boolean VerifyImplementation = true; // verify the model implementation at the end of each first run
	
    private static int maxTxListSize = 0;

    
    public static void initialise() {
    	NODES.clear();
    	GATEWAY_IDS.clear();
    	generateGatewayIDs();
    	generateGateways();
    	generateNodes();
    }
    
    
    
    // Generates the gateway Id's
    public static void generateGatewayIDs() {
    	for (int i = 0 ; i<getGn() ; i++) {
    		getGATEWAY_IDS().add(String.valueOf((char)(i+65)));
    	}
    }
    
    // Create all the gateways
    public static void generateGateways() {
    	for (String s :getGATEWAY_IDS()) {
        	ArrayList<String> otherGatewayIds = new ArrayList<>(getGATEWAY_IDS());
        	otherGatewayIds.remove(otherGatewayIds.indexOf(s));
        	getNODES().add(new Node(s, "g", otherGatewayIds));
    	}
    }

    
    // Creates device nodes for each gateway
    public static void generateNodes() {
    	int deviceNodeId =1;
    	for (String s : getGATEWAY_IDS()) {
        	for (int j = 0; j < getDn(); j++) {
        		getNODES().add(new Node(deviceNodeId, "d", new ArrayList<>(Arrays.asList(s))));
        		deviceNodeId++;
        	}
    	}
    }
     
    
    /**
	 * @return the simTime
	 */
	public static int getSimTime() {
		return simTime;
	}


	/**
	 * @return the tn
	 */
	public static int getTn() {
		return Tn;
	}


	/**
	 * @return the nODES
	 */
	public static ArrayList<Node> getNODES() {
		return NODES;
	}


	/**
	 * @return the propTxDelay
	 */
	public static double getPropTxDelay() {
		return propTxDelay;
	}


	/**
	 * @return the propTxListDelay
	 */
	public static double getPropTxListDelay() {
		return propTxListDelay;
	}


	/**
	 * @return the gn
	 */
	public static int getGn() {
		return Gn;
	}


	/**
	 * @return the dn
	 */
	public static int getDn() {
		return Dn;
	}


	/**
	 * @return the gATEWAY_IDS
	 */
	public static ArrayList<String> getGATEWAY_IDS() {
		return GATEWAY_IDS;
	}

	/**
	 * @param gATEWAY_IDS the gATEWAY_IDS to set
	 */
	public static void setGATEWAY_IDS(ArrayList<String> gATEWAY_IDS) {
		GATEWAY_IDS = gATEWAY_IDS;
	}

	/**
	 * @return the insertTxDelay
	 */
	public static double getInsertTxDelay() {
		return insertTxDelay;
	}

	/**
	 * @return the txListSize
	 */
	public static int getTxListSize() {
		return txListSize;
	}

	/**
	 * @return the maxTxListSize
	 */
	public static int getMaxTxListSize() {
		return maxTxListSize;
	}
	

	public static void setMaxTxListSize(int maxTxListSize) {
		InputConfig.maxTxListSize = maxTxListSize;
	}

	/**
	 * @return the runs
	 */
	public static int getRuns() {
		return Runs;
	}

	/**
	 * @return the verifyImplementation
	 */
	public static boolean isVerifyImplementation() {
		return VerifyImplementation;
	}

	/**
	 * @return the nn
	 */
	public static int getNn() {
		return Nn;
	}
	
	

}


