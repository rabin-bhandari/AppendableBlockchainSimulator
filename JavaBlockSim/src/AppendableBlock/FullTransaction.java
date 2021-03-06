package AppendableBlock;

import java.util.concurrent.ThreadLocalRandom;

import Simulator.InputConfig;

public class FullTransaction {
		
	public static void createTransactions() {
		
		for (int i = 0; i < InputConfig.getTn(); i++) {
			for (int j = 0; j< (InputConfig.getGn() * InputConfig.getDn()); j++) {
				Transactions tx = new Transactions();
				tx.setId(ThreadLocalRandom.current().nextLong(10000000000L));
				double creationTime = ThreadLocalRandom.current().nextDouble(i, i+1);
				double receiveTime = creationTime;
				double insertTime = receiveTime;
				tx.setTimestamp(new double[]{creationTime, receiveTime, insertTime});
								
				int nodeIndex = InputConfig.getGn() + j;
				
				Node sender = InputConfig.getNODES().get(nodeIndex);

				tx.setSender(sender.getId());
								
				tx.setTo(sender.getGatewayIds().get(0));
				propagateTransaction(tx);
			}
		}
	}

	// Transaction propagation & preparing pending lists for miners
	private static void propagateTransaction(Transactions tx) {
		
		int index = InputConfig.getGATEWAY_IDS().indexOf(tx.getTo());
		tx.getTimestamp()[1] += Network.txPropDelay();		
		InputConfig.getNODES().get(index).getTransactionsPool().add(tx);
		
	}

}
