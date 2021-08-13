package AppendableBlock;

import java.util.concurrent.ThreadLocalRandom;

import Simulator.InputConfig;

public class Network {	
	
	// Delay for propagating transactions in the network
	public static double txPropDelay() {
		return Math.log(1-Math.random()/(InputConfig.getPropTxDelay()));
	}
	
	
	// Delay for propagating transactions in the network
	public static double txListPropDelay() {
		return Math.log(1-Math.random()/(InputConfig.getPropTxListDelay()));
	}
	
	
	// Delay for releasing the token
	public static double txTokenReleaseDelay() {
		return ThreadLocalRandom.current().nextDouble(0.0001, 0.0005);
	}

}
