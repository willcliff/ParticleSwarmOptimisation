package PSO;

import java.util.Arrays;

public class PSOMain {
	
	public static void main(String args[]) throws Exception {
		
		
			begin();
			//PSO pso = new PSO();
			//pso.execute();

			//LocalPSO pso = new LocalPSO();
			//pso.execute();

		
	}
	public static void begin() throws Exception {
		int numberOfRuns = 5;
		int function = 3;
		
		
		for(int j = 1; j<=1; j++){
			double[] avergaeBestFitness = new double[numberOfRuns];
			
			double total = 0;
			double finalAverage;
			double[] averageFitnesses = new double[10];
			//for (int r = 0; r < numberOfRuns; r++) {

				// GlobalPSO pso = new GlobalPSO();
				// pso.execute();
				// PSO pso = new PSO();
				// pso.execute();
				// Problem problem = new Problem(function);

				//BasicTestFunc problem = new BasicTestFunc(j);
				BasicTestFunc problem = new BasicTestFunc(function);
				//TestFunc14 problem = new TestFunc14(function);
				LocalPSO pso = new LocalPSO(problem, numberOfRuns);
				//GlobalPSO pso = new GlobalPSO(problem, numberOfRuns);
				
				pso.execute();
				
				
							

				//avergaeBestFitness[r] = pso.gBestFitness;
				//total += avergaeBestFitness[r];
			//}			
			
			/*System.out.println("     Average BestValues: "
					+ Arrays.toString(avergaeBestFitness));
			
			finalAverage = total / numberOfRuns;
			System.out.println("     Final Average: " + finalAverage);
			System.out.println("\n");*/

		}
	}
	public double getMean(double mean){
		return mean;		
	}
	public double getStandDeviation(double deviation){
		return deviation;
	}

}
