package PSO;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
		int function = 1;
		
		
		for(int j = 1; j<=7; j++){
			int numberOfRuns = 25;
			double total = 0;
			double finalAverage;
			double[] avergaeBestFitness = new double[numberOfRuns];
			double[] averageSwarmFitnesses = new double[10000];			
			String dir;
			String fileName = "DefaultFile";
			String dir1;
			String fileName1 = "DefaultFile";
			for (int r = 0; r < numberOfRuns; r++) {

				// GlobalPSO pso = new GlobalPSO();
				// pso.execute();
				// PSO pso = new PSO();
				// pso.execute();
				// Problem problem = new Problem(function);

				//BasicTestFunc problem = new BasicTestFunc(j);
				BasicTestFunc problem = new BasicTestFunc(function);
				//TestFunc14 problem = new TestFunc14(function);
				//LocalPSO pso = new LocalPSO(problem);
				GlobalPSO pso = new GlobalPSO(problem);
				//VonNeumann pso = new VonNeumann(problem);
				//GIDNPSO pso = new GIDNPSO(problem);
				pso.execute();
				
				//BasicTestFunc problem1 = new BasicTestFunc(j);
				//TestFunc14 problem1 = new TestFunc14(function);
				//LocalPSO pso1 = new LocalPSO(problem1);
				//pso1.execute();
				for(int i = 0; i < pso.averageFitnesses.length; i++){
					averageSwarmFitnesses[i] += pso.averageFitnesses[i];
				}
				System.out.println("     Average swarmValuesMAIN RUN: " + r + " "
						+ Arrays.toString(averageSwarmFitnesses) + "\n");
				//BasicTestFunc problem1 = new BasicTestFunc(j);
				//TestFunc14 problem1 = new TestFunc14(function);
				//LocalPSO pso1 = new LocalPSO(problem1);
				//pso1.execute();
				dir = "C:/Users/William/Documents/NUIG Masters/Year2/PSOResults/BasicFunctions";
				fileName = dir + "//" + pso.psoType + problem.functionName + ".dat";
				
				dir1 = "C:/Users/William/Documents/NUIG Masters/Year2/PSOResults/SummaryResults";
				fileName1 = dir1 + "//Summary" + pso.psoType + problem.functionName + ".txt";
				
				
							

				avergaeBestFitness[r] = pso.gBestFitness;
				total += avergaeBestFitness[r];
				
			}			
			
			System.out.println("     Average BestValuesMAIN: "
					+ Arrays.toString(avergaeBestFitness));
			
			finalAverage = total / numberOfRuns;
			System.out.println("     Final AverageMAIN: " + finalAverage);
			System.out.println("\n");
			
			BufferedWriter output;
			output = new BufferedWriter(new FileWriter(fileName));
			for (int i = 0; i < averageSwarmFitnesses.length; i++)
	        {
				averageSwarmFitnesses[i] = averageSwarmFitnesses[i] / numberOfRuns;           
	            output.write(i+1 + "\t" + averageSwarmFitnesses[i] + "\n");         
	        }
			output.close();
			BufferedWriter output1;
			output1 = new BufferedWriter(new FileWriter(fileName1));			
			output1.write("Average Best Values: " + Arrays.toString(avergaeBestFitness));
			output1.write("\n");
			output1.write("Final Average: " + finalAverage);
			output1.write("\n");
			output1.close();
			
			System.out.println("\nFile Created!\n");
		}
	}
	
	/*public void createAvSummary(double averageFitnesses[], String date, int noRuns) throws IOException{
		String dir = "C:/Users/William/Documents/NUIG Masters/Year2/PSOResults";
		String fileName = dir + "//" + psoType + problem.functionName + ".dat";
		BufferedWriter output;
		output = new BufferedWriter(new FileWriter(fileName));
		for (int i = 0; i < averageFitnesses.length; i++)
        {
            averageFitnesses[i] = averageFitnesses[i] / noRuns;           
            output.write(i+1 + "\t" + averageFitnesses[i] + "\n");         
        }
		output.close();
		System.out.println("\nFile Created!\n");
        //gnuPlot(fileName, date);	
	}*/
	public double getMean(double mean){
		return mean;		
	}
	public double getStandDeviation(double deviation){
		return deviation;
	}

}
