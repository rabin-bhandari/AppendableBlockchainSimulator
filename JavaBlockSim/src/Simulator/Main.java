package Simulator;

import AppendableBlock.BlockCommit;
import AppendableBlock.FullTransaction;
import AppendableBlock.Node;

public class Main {
		
	public static void main(String[] args) {
		
		new Main().run();
		
	}
		
	public void run() {
		

		for (int i = 0; i<InputConfig.getRuns(); i++) {
			System.out.println(i+1);
			// Initialise input configurations
			InputConfig.initialise();

			// Initialise clock for simulation run.
			double clock = 0;
			
			
			// Generate pending transactions
			FullTransaction.createTransactions();
			
			// Generate the genesis Block for all miners
			Node.generateGenesisBlock();
			
			// Initiate initial events >= 1 to start with
			BlockCommit.generateInitialEvents();
			
			while (!Queue.isEmpty() && (clock <= InputConfig.getSimTime())) {
				Event nextEvent = Queue.getNextEvent(); 
				
				// Move clock to the time of the event
				clock = nextEvent.getTime();
				
				BlockCommit.handleEvent(nextEvent);
				Queue.removeEvent(nextEvent);

			}
			
			
			BlockCommit.processGateawayTransactionPools();

			// ############## NEEDS TO BE IMPLEMENTED ################
			
//			// Verify the model
//			if (i == 0 && p.isVerifyImplementation()) {
//				Verification.performChecks();
//			} // Line 64 in main.py
			
			

			// Calculate the simulation results (e.g block statistics)
			Statistics.calculate();
			
			
			// Save results to excel file
			ExcelWriter.printToExcel(i, true);
			
			System.out.println("run complete");
			System.out.println("");

			// Reset global variables before next run
			Statistics.reset();
			
		}
		
	}
	
}
