package PSO;

import java.io.BufferedWriter;
import java.util.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class PSOMain {
	
	public static void main(String args[]) throws Exception {	
			begin();	
	}
	
	public static void begin() throws Exception {
		long startTime = new Date( ).getTime();
		String psoType = "Default";
		String parameters = "Default";
		int probs = 3;//no of problems in suite 7 for basic functions, 30 for cec2014 functions or select individual functions and edit loop
		int function = 1;
		int numberOfRuns = 5;
		int iterations = 10;
		List<double[]> allBests = new ArrayList<double[]>();
		String[] functionNames = new String[probs];
		
		for(int j = 1; j<=probs; j++){						
			double total = 0;
			double finalAverage;
			double standardDeviation = 0;
			double[] avergaeBestFitness = new double[numberOfRuns];
			double[] averageSwarmFitnesses = new double[iterations];
			double[] averageSwarmSize = new double[iterations];
			double[] evoFacts = new double[iterations];
			String dir;			
			String dir1;
			String dir2;
			String fileName = "DefaultFile";
			String fileName1 = "DefaultFile";
			String fileName2 = "DefaultFile";
			String fileName3 = "DefaultFile";
			for (int r = 0; r < numberOfRuns; r++) {
				
				//Select the suite of test functions			
				BasicTestFunc problem = new BasicTestFunc(j);				
				//TestFunc14 problem = new TestFunc14(j);
				
				functionNames[j-1] = problem.functionName;
				
				// Select the PSO type to run
				AGIDNPSO pso = new AGIDNPSO(problem, iterations);			
				//LocalPSO pso = new LocalPSO(problem, iterations);
				//GlobalPSO pso = new GlobalPSO(problem, iterations);
				//VonNeumann pso = new VonNeumann(problem, iterations);
				//GIDNPSO pso = new GIDNPSO(problem, iterations);
				//GIDNPSONEW2 pso = new GIDNPSONEW2(problem, iterations);
				pso.execute();
				
				
				for(int i = 0; i < pso.averageFitnesses.length; i++){
					averageSwarmFitnesses[i] += pso.averageFitnesses[i];
					averageSwarmSize[i] += pso.averageNeighbSize[i];
					evoFacts[i] += pso.evoFacts[i];
				}
				//System.out.println("     Average swarmValuesMAIN RUN: " + r + " "	+ Arrays.toString(averageSwarmFitnesses) + "\n");							
				psoType = pso.psoType;
				//parameters = pso.parameters;

				dir = "C:/Users/William/Documents/NUIG Masters/Year2/PSOResults/BasicFunctions";
				fileName = dir + pso.psoType +"//" + pso.psoType + problem.functionName + ".dat";
				
				dir = "C:/Users/William/Documents/NUIG Masters/Year2/PSOResults/SummaryResults";
				fileName1 = dir + "//Summary" + pso.psoType + problem.functionName + ".txt";
				
				dir = "C:/Users/William/Documents/NUIG Masters/Year2/PSOResults/GraphResults/";
				fileName2 = dir + pso.psoType + "//" + pso.psoType + problem.functionName + "SwarmSize.dat";
				fileName3 = dir + pso.psoType + "//" + pso.psoType + problem.functionName + "EvoFacts.dat";
				
				
				avergaeBestFitness[r] = pso.gBestFitness;
				total += avergaeBestFitness[r];	
			}//end of runs
			
			//time taken
			long endTime = new Date( ).getTime();
			long difference = endTime - startTime;
			
			System.out.println("     Average BestValuesMAIN: "
					+ Arrays.toString(avergaeBestFitness));
			System.out.println("     EvoFactor: "
					+ Arrays.toString(evoFacts));
			for(int i = 0; i < averageSwarmSize.length; i++){
				averageSwarmSize[i] = (int) averageSwarmSize[i] / numberOfRuns;
				evoFacts[i] = evoFacts[i] / numberOfRuns;
			}
			
			finalAverage = total / numberOfRuns;
			double temp = 0;
			for (int r = 0; r < numberOfRuns; r++) {
				//double standardDeviation = new double[r];
				
				temp += (finalAverage - avergaeBestFitness[r]) * (finalAverage - avergaeBestFitness[r]);
				
			}
			//variance = temp/(numberOfRuns-1)
			standardDeviation = Math.sqrt((temp/(numberOfRuns-1)));
			double standardDeviationPop = Math.sqrt(temp/numberOfRuns);
			
			System.out.println("     Final AverageMAIN: " + finalAverage);
			System.out.println("     Standard Deviation: " + standardDeviation);
			System.out.println("\n");
			System.out.println("Time Elapsed: " + difference);
			
			//output summary file, swarm fitnesses for convergence graphs, evofactors for graphs & swarm sizes for graphs
			/*BufferedWriter output;
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
			output1.close();*/		
			
			allBests.add(avergaeBestFitness);			
		}
		
		/*String tTestdir = "//fs2/14232817/Desktop/PSOResults/GraphResults";\\\\output wilcoxon info
		String tTestfileName = tTestdir + "//" + psoType + ".xls";
		//String tTestfileName = tTestdir + "//" + psoType + "Main" + ".xls"; //for CEC 2014 Functions
		
		BufferedWriter output;
		output = new BufferedWriter(new FileWriter(tTestfileName));
		for (int i = 0; i < allBests.size(); i++)
        {
			output.write(functionNames[i] + "\t");
			//averageSwarmSize[i] = averageSwarmSize[i] / numberOfRuns;                                
        }
		output.write("\n");
		for (int i = 0; i < numberOfRuns; i++)
        {
			for (int j = 0; j < allBests.size(); j++)
	        {          
				output.write(allBests.get(j)[i] + "\t");        
	        }
			output.write("\n");		                              
        }
		output.close();*/
		
	}
}
