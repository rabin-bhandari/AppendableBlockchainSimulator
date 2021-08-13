package AppendableBlock;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import Simulator.InputConfig;

public class FullTransaction {
		
	public static void createTransactions() {
		
		for (int i = 0; i < InputConfig.getTn(); i++) {
			for (int j = 0; j< (InputConfig.getGn() * InputConfig.getDn()); i++) {
				Transactions tx = new Transactions();
				tx.setId(ThreadLocalRandom.current().nextLong(10000000000L));
				double creationTime = ThreadLocalRandom.current().nextDouble(i, i+1);
				double receiveTime = creationTime;
				double insertTime = receiveTime;
				tx.setTimestamp(new double[]{creationTime, receiveTime, insertTime});
								
				int nodeIndex = InputConfig.getGn() + j;
				
				Node sender = InputConfig.getNODES().get(nodeIndex);

				tx.setSender((int) sender.getId());
				
				tx.setTo(sender.getGatewayIds());
				
				transactionProp(tx);
			}
			
		}
	}

	// Transaction propogation & preparing pending lists for miners
	private static void transactionProp(Transactions tx) {
		
		int index = InputConfig.getGATEWAY_IDS().indexOf(tx.getTo());
		double[] newTimestamp = new double[]{tx.getTimestamp()[0], tx.getTimestamp()[1] + Network.txPropDelay(), tx.getTimestamp()[2]};
		tx.setTimestamp(newTimestamp);
		
		InputConfig.getNODES().get(index).getTransactionsPool().add(tx);
		
	}

}
