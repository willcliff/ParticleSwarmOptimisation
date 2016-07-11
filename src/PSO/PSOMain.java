package PSO;

import java.io.BufferedWriter;
import java.util.*;
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
		long startTime = new Date( ).getTime();
		String psoType = "Default";
		String parameters = "Default";
		
		for(int j = 1; j<=7; j++){
			int numberOfRuns = 25;
			int iterations;
			double total = 0;
			double finalAverage;
			double standardDeviation = 0;
			double[] avergaeBestFitness = new double[numberOfRuns];
			double[] averageSwarmFitnesses = new double[10000];
			double[] averageSwarmSize = new double[10000];
			double[] evoFacts = new double[10000];
			String dir;			
			String dir1;
			String dir2;
			String fileName = "DefaultFile";
			String fileName1 = "DefaultFile";
			String fileName2 = "DefaultFile";
			String fileName3 = "DefaultFile";
			for (int r = 0; r < numberOfRuns; r++) {

				// GlobalPSO pso = new GlobalPSO();
				// pso.execute();
				// PSO pso = new PSO();
				// pso.execute();
				// Problem problem = new Problem(function);

				BasicTestFunc problem = new BasicTestFunc(j);
				//TestFunc14 problem = new TestFunc14(j);
				//LocalPSO pso = new LocalPSO(problem);
				//GlobalPSO pso = new GlobalPSO(problem);
				//VonNeumann pso = new VonNeumann(problem);
				//GIDNPSO pso = new GIDNPSO(problem);
				GIDNPSONEW pso = new GIDNPSONEW(problem);
				//GIDNPSONEW2 pso = new GIDNPSONEW2(problem);
				pso.execute();
				//BasicTestFunc problem1 = new BasicTestFunc(j);
				//TestFunc14 problem1 = new TestFunc14(function);
				//LocalPSO pso1 = new LocalPSO(problem1);
				//pso1.execute();
				
				
				for(int i = 0; i < pso.averageFitnesses.length; i++){
					averageSwarmFitnesses[i] += pso.averageFitnesses[i];
					averageSwarmSize[i] += pso.averageNeighbSize[i];
					evoFacts[i] += pso.evoFacts[i];
				}
				//System.out.println("     Average swarmValuesMAIN RUN: " + r + " "	+ Arrays.toString(averageSwarmFitnesses) + "\n");				
				//BasicTestFunc problem1 = new BasicTestFunc(j);
				//TestFunc14 problem1 = new TestFunc14(function);
				//LocalPSO pso1 = new LocalPSO(problem1);
				//pso1.execute();
				psoType = pso.psoType;
				parameters = pso.parameters;
				dir = "//fs2/14232817/Desktop/PSOResults/GraphResults/";
				//dir = "C:/Users/William/Documents/NUIG Masters/Year2/PSOResults/BasicFunctions";
				fileName = dir + pso.psoType +"//" + pso.psoType + problem.functionName + ".dat";
				
				dir2 = "//fs2/14232817/Desktop/PSOResults/GraphResults/";
				fileName2 = dir2 + pso.psoType + "//" + pso.psoType + problem.functionName + "SwarmSize.dat";
				
				dir2 = "//fs2/14232817/Desktop/PSOResults/GraphResults/";
				fileName3 = dir2 + pso.psoType + "//" + pso.psoType + problem.functionName + "EvoFacts.dat";
				
				dir1 = "//fs2/14232817/Desktop/PSOResults/SummaryResults";
				//dir1 = "C:/Users/William/Documents/NUIG Masters/Year2/PSOResults/SummaryResults";
				fileName1 = dir1 + "//Summary" + pso.psoType + problem.functionName + ".txt";
				
				
							

				avergaeBestFitness[r] = pso.gBestFitness;
				total += avergaeBestFitness[r];
				
				
				
			}//end of runs
			//standard deviation
			
			
			long endTime = new Date( ).getTime();
			long difference = endTime - startTime;
			
			System.out.println("     Average BestValuesMAIN: "
					+ Arrays.toString(avergaeBestFitness));
			for(int i = 0; i < averageSwarmSize.length; i++){
				averageSwarmSize[i] = (int) averageSwarmSize[i] / numberOfRuns;
				evoFacts[i] = evoFacts[i] / numberOfRuns;
			}
			

			/*System.out.println("     averageSwarmSize: "
					+ Arrays.toString(averageSwarmSize));
			System.out.println("     averageEvoFactor: "
					+ Arrays.toString(evoFacts));*/
			
			finalAverage = total / numberOfRuns;
			double temp = 0;
			for (int r = 0; r < numberOfRuns; r++) {
				//double standardDeviation = new double[r];
				
				temp += (finalAverage - avergaeBestFitness[r]) * (finalAverage - avergaeBestFitness[r]);
				
			}
			//variance = temp/(numberOfRuns-1)
			standardDeviation = Math.sqrt((temp/(numberOfRuns-1)));
			double standardDeviationPop;
			standardDeviationPop = Math.sqrt(temp/numberOfRuns);
			
			System.out.println("     Final AverageMAIN: " + finalAverage);
			System.out.println("     Standard Deviation: " + standardDeviation);
			System.out.println("\n");
			System.out.println("Time Elapsed: " + difference);
			
			BufferedWriter output;
			output = new BufferedWriter(new FileWriter(fileName));
			for (int i = 0; i < averageSwarmFitnesses.length; i++)
	        {
				averageSwarmFitnesses[i] = averageSwarmFitnesses[i] / numberOfRuns;           
	            output.write(i+1 + "\t" + averageSwarmFitnesses[i] + "\n");         
	        }
			output.close();
			
			output = new BufferedWriter(new FileWriter(fileName2));
			for (int i = 0; i < averageSwarmSize.length; i++)
	        {
				//averageSwarmSize[i] = averageSwarmSize[i] / numberOfRuns;           
	            output.write(i+1 + "\t" + averageSwarmSize[i] + "\n");         
	        }
			output.close();
			
			output = new BufferedWriter(new FileWriter(fileName3));
			for (int i = 0; i < evoFacts.length; i++)
	        {
				//averageSwarmSize[i] = averageSwarmSize[i] / numberOfRuns;           
	            output.write(i+1 + "\t" + evoFacts[i] + "\n");         
	        }
			output.close();
			
			BufferedWriter output1;
			output1 = new BufferedWriter(new FileWriter(fileName1));
			output1.write("PSOType: " + psoType);
			output1.write("\n");
			output1.write("Parameters: " + parameters);
			output1.write("\n");
			output1.write("Average Best Values: " + Arrays.toString(avergaeBestFitness));
			output1.write("\n");
			output1.write("Average Swarm Sizes: " + Arrays.toString(averageSwarmSize));
			output1.write("\n");
			output1.write("Average Evo Factors: " + Arrays.toString(evoFacts));
			output1.write("\n");
			output1.write("Standard Deviation: " + standardDeviation);
			output1.write("\n");
			output1.write("Standard Deviation Population: " + standardDeviationPop);
			output1.write("\n");
			output1.write("Final Average: " + finalAverage);
			output1.write("\n");
			output1.write("Time Elapsed: " + difference);
			output1.close();
			
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
