package PSO;

import java.util.Arrays;

public class PSOMain {
	
	public static void main(String args[]) {
		
		
			begin();
			//PSO pso = new PSO();
			//pso.execute();

			//LocalPSO pso = new LocalPSO();
			//pso.execute();

		
	}
	public static void begin() {
		int numberOfRuns = 25;
		double[] averageValues = new double[numberOfRuns];
		double total = 0;
		double finalAverage;
		for(int i = 0; i<numberOfRuns; i++){
			
		GlobalPSO pso = new GlobalPSO();
		pso.execute();
		//PSO pso = new PSO();
		//pso.execute();

		//LocalPSO pso = new LocalPSO();
		//pso.execute();
		
		
		
		
		
		averageValues[i] = pso.gBestFitness;			
		total += averageValues[i];	
		}
		System.out.println("     Average Values: " + Arrays.toString(averageValues));
		finalAverage = total/numberOfRuns;
		System.out.println("     Final Average: " + finalAverage);
		System.out.println("\n");
		
	}
	public double getMean(double mean){
		return mean;		
	}
	public double getStandDeviation(double deviation){
		return deviation;
	}

}
