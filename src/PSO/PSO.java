package PSO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.text.*;

public abstract class PSO {
	Problem problem;
	//int function = 1;
	public int iterations = 10000;
	int swarmSize = 50;
	int iteration;
	int numberOfRuns = 1;
	double[] averageFitnesses = new double[iterations];
	
	// stores gBestFitness after each iteration
	Date dateTime = new Date();
	SimpleDateFormat ft = new SimpleDateFormat ("E yyyy-MM-dd'at'hhmmss a zzz");
	String date = new String(ft.format(dateTime));
	//String date = dateTime.toString();
	//date = date.replace('/', '-');
	//date = date.replace(':', '');
	//date = date.replace();
	ArrayList<Particle> swarm = new ArrayList<Particle>();
	double gBestFitness; // globally best fitness
	double[] gBest;
	Particle gBestParticle;// globally(swarms) best position
	protected String psoType = "Standard";	 // int dimensions = problem.getDimensions();
	int dimensions; 
	double standardDeviation;	
	 // globally best particle
	
	
	public PSO(Problem problem) {
		this.problem = problem;	
		dimensions = problem.getDimensions();
	}

	public PSO(String psoType, int swarmSize, Problem problem, double standardDeviation) {
		this.psoType = psoType;
		// this.swarmSize = swarmSize;
		this.problem = problem;
		this.standardDeviation = standardDeviation;
		
	}

	private void initialise() {	
		// this.dimensions = problem.getDimensions();
		//******Move into constructor and then create neighbourhoods in extended class constructors
		
		// define and initialise gBest for first iteration
		//instead of below use calculateGBest(i); method???
		for (int i = 0; i < swarmSize; i++) {
			// fitness = problem.getFitness(position);
			Particle particle = new Particle(problem);
			particle.particleNo = i+1;
			swarm.add(particle);			
		}
		gBestParticle = swarm.get(0);
		gBest = gBestParticle.getPosition().clone();
		gBestFitness = swarm.get(0).getFitness();
		calculateGBest();
		createNeighbourhood();
		
		//System.out.println("     \nGBestFitness Initialised: " + gBestParticle.getPBestFitness());
		//System.out.println("     GBestFitness Initialised: " + gBestFitness);		
	}

	abstract void createNeighbourhood();

	public void iterate() {
		double[] swarmFitnesses = new double[swarmSize];		
		for (int j = 0; j < iterations; j++) {
			iteration = j;
			System.out.println("Iteration" + j);
			averageDistance();
		
		// calculate the pBest and gBest positions
			for (int i = 0; i < swarmSize; i++) {
				Particle particle = swarm.get(i);				
				// pass the swarm to calculate the gBest
				// particle
				// and fitness

				// calculate and update pBest and gBest
				// step1 update pBest

				particle.updatePBest();

				// step2 update gBest
				// check the method to update the fitness values!!!!*****

				// for LPSO returns same for GPSO
				// gBestParticle = bestNeighbour(i);
				calculateGBest();
				
				
				
				// gBestFitness = gBestParticle.getPBestFitness();

				// update intertia weight
				/*
				 * w = W_UPPERBOUND - (((double) iteration) / maxIterations) *
				 * (W_UPPERBOUND - W_LOWERBOUND);
				 * 
				 * inertialWeight = (inertialWeight - FINAL_WEIGHT) *
				 * ((maxIterations - iteration) / (double) maxIterations) +
				 * FINAL_WEIGHT;
				 */

				// update the velocity and positions
				// step 3 update the velocity and position
				
				//change particle to double and return nBest!
				//Particle bestNeighbour = calculateNeighbourhoodBest(i);
				//particle.update(bestNeighbour.getPBest());
				double[] lBest = calculateNeighbourhoodBest(i).clone();
				
				particle.update(lBest);
				
				// step 4 update the position
				// particle.updatePosition();
				//calculateGBest();
				// update fitness
				// fitness = problem.getFitness(position);
				swarmFitnesses[i] = particle.getFitness();

			}
			//System.out.println("DistancesMain: " + distances);
			//evoFactor(distances);
			
			/*for (Particle particle : swarm){
				System.out.println("Distances getDistance()1 : " + particle.getDistance());
			}*/	
		averageFitnesses[j] += gBestFitness;
		//System.out.println("ITERATION " + iteration + ": ");
		//System.out.println("     SwarmFitnesses: " + Arrays.toString(swarmFitnesses));
		//System.out.println("     Particle: " + gBestParticle.particleNo + "\tGBestFitnessA: " + gBestParticle.getFitness());
		//System.out.println("     Particle: " + gBestParticle.particleNo + "\tDistance: " + gBestParticle.getDistance());
		//System.out.println("     GBestFitnessB: " + gBestFitness);

		/////System.out.println("DistancesMain2: " + distances);
		/*
		 * for(int i = 0; i < dimensions; i++) { System.out.println("ITERATION "
		 * + iteration + ": "); System System.out.println("     Best X: " +
		 * gBestParticle.position[0]); System.out.println("     Best Y: " +
		 * gBestParticle.position[1]); }
		 */
		// System.out.println("     Best X: "
		// + Arrays.toString(gBestParticle.position));
		// System.out.println("     Best Y: " + gBestParticle.position[1]);
		// System.out.println("     Value: " +
		// problem.getFitness(gBestParticle.position));
		// ystem.out.println("     Value: " + gBestParticle.getFitness());

		}
		/*try {
			createAvSummary(averageFitnesses, date, numberOfRuns);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// iteration++;*/
	}
	
	abstract void averageDistance();

	abstract double[] calculateNeighbourhoodBest(int i);
	//abstract Particle calculateNeighbourhoodBest(int i);

	// method to calculate the gBest particle
	public void calculateGBest(){
		//gBestParticle = Collections.min(swarm);
		//gBestFitness = gBestParticle.getPBestFitness(); // globally best fitness
		//gBest = gBestParticle.getPosition().clone();
		for(Particle particle : swarm){
			
			//System.out.print(particle.getFitness() + ", ");
			if (particle.getPBestFitness() < gBestFitness) {
				gBestParticle = particle;	
				gBestFitness = particle.getPBestFitness();							
				gBest = particle.getPBest().clone();								
			}						
		}	
	}
	
	

	//public abstract Particle bestNeighbour(int i);

	public void execute() {
		//double[] averageValues = new double[numberOfRuns];
		
		
		double total = 0;
		double finalAverage;
		double[] avergaeBestFitness = new double[numberOfRuns];
		for (int i = 0; i < numberOfRuns; i++){
			
			//System.out.println("     Pre Initialisation gBestFitness: " + gBestFitness);
			initialise();
			iterate();
			for (Particle particle : swarm){
				System.out.println("Distances getDistance()1 : " + particle.getDistance());
			}
			//System.out.println("Both Fitnesses\t"+gBestFitness + " + " + gBestParticle.getFitness());
			//calculateGBest();
			System.out.println("     gBestParticle Fitness: " + (gBestParticle.getPBestFitness()));
			System.out.println("     gBestFitness: " + gBestFitness + "\n");
			//System.out.println("\n");
			
			
			
			//avergaeBestFitness[i] = gBestFitness;
			// += avergaeBestFitness[i];
		
			/*gBestFitness = Double.MAX_VALUE;
			gBest = null;
			gBestParticle = null;//reset the gBest Fitness*/
		}
		/*System.out.println("     Average BestValues: "
				+ Arrays.toString(avergaeBestFitness));
		
			finalAverage = total / numberOfRuns;
			System.out.println("     Final Average: " + finalAverage);
			System.out.println("\n");
		try {
			createAvSummary(averageFitnesses, date, numberOfRuns);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
			/*for (int i = 0; i < iterations; i++) {
				iteration = i;
				System.out.println("\nSolution found at iteration " + (i)
						+ ", the solutions is:");
				iterate();
				fitnesses[i] = gBestFitness;
				
				System.out.println("     FitnessC: "
						+ (gBestParticle.getFitness()));
				
			}*/
			/*System.out.println("     GBestFitnesses after each iteration: "
					+ Arrays.toString(fitnesses));
			System.out.println("\nSolution found at iteration " + (iterations)
					+ ", the solutions is:");
			System.out.println("     Best Position: "
					+ Arrays.toString(gBestParticle.position));
			System.out.println("     Fitness: " + (gBestParticle.getFitness()));
			System.out.println("     gBestFitness: " + gBestFitness);
			System.out.println(j);*/
			
			//averageValues[j] = gBestFitness;			
			//total += averageValues[j];	
		
		//System.out.println("     Average Values: " + Arrays.toString(averageValues));
		//finalAverage = total/numberOfRuns;
		//System.out.println("     Final Average: " + finalAverage);
		//System.out.println("\n");
		
		//}	
	}
	
	public void createAvSummary(double averageFitnesses[], String date, int noRuns) throws IOException{
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
	}
	
	public void gnuPlot(String datFile, String date) throws IOException
    {
		//try{			
			
			//String pngFile = psoType + " " + date + ".png";
	        //string pgm = @"C:\Program Files (x86)\gnuplot\bin\gnuplot.exe";
	        //String pgm = "C:/Program Files (x86)/gnuplot/bin/wgnuplot.exe";
	        //Runtime runTime = Runtime.getRuntime();
	        //Process extPro = runTime.exec(pgm);
	        //System.out.println("Opening gnuplot");
        //String pngFile = psoType + " " + date + ".png";
        //string pgm = @"C:\Program Files (x86)\gnuplot\bin\gnuplot.exe";
      
        /*System.out.println("Opening gnuplot");
        OutputStream outPut;
        String gnuplot_cmd = "plot sin(x)" ;

		PrintWriter pw = new PrintWriter(extPro.getOutputStream());
		pw.print(gnuplot_cmd);
		pw.close();
        
        outPut = extPro.getOutputStream();
		PrintWriter gp=new PrintWriter(new BufferedWriter(new
		OutputStreamWriter(outPut)));
		gp.println("plot sin(x)\n");
		gp.close();*/
      	
      	
      	//OutputStream outPut = new OutputStream();
        //BufferedWriter gnupWri = new BufferedWriter(new PrintWriter);
        
        /*InputStream stdin = extPro.getErrorStream();
        InputStreamReader isr = new InputStreamReader(stdin);
        BufferedReader br = new BufferedReader(isr);
        String line = null;
        while ((line = br.readLine()) != null)
            System.err.println("gnuplot:"+line);
        int exitVal = extPro.waitFor();
        if (exitVal != 0)*/
            //log("gnuplot Process exitValue: " + exitVal);
       /*extPro.getInputStream().close();
        extPro.getOutputStream().close();
        extPro.getErrorStream().close();
    } catch (Exception e) {
        System.err.println("Fail: " + e);
    }*/
		String pngFile = "gBest "+ date + ".png";
        //string pgm = @"C:\Program Files (x86)\gnuplot\bin\gnuplot.exe";
        String pgm = "C:/Program Files (x86)/gnuplot/bin/wgnuplot.exe";
        Runtime runTime = Runtime.getRuntime();
        Process extPro = runTime.exec(pgm);
        System.out.println("Opening gnuplot");  
        
        BufferedReader input =
                new BufferedReader
                  (new InputStreamReader(extPro.getInputStream()));

        //OutputStream opStream = extPro.getOutputStream();
        //PrintWriter gp = new PrintWriter(new BufferedWriter(new OutputStreamWriter(opStream)));
        
        //OutputStream opStream = extPro.getOutputStream();
        //PrintWriter gp = new PrintWriter(new BufferedWriter(new OutputStreamWriter(opStream)));
        
        //PrintWriter gp;
		//gp = new PrintWriter(new BufferedWriter());
        OutputStream outstream = extPro.getOutputStream();
        PrintWriter out = new PrintWriter(outstream);
        
        //InputStream inputStream = extPro.getInputStream();
		//InputStreamReader isr = new InputStreamReader(inputStream);
              
        out.write("reset");  
        out.flush();        
        out.println("set autoscale");
        out.flush();   
        out.println("set term png");
        out.flush();   
        out.println("set output \"" + pngFile + "\"");
        out.flush();   
        out.println("set xlabel \"Iteration\"");
        out.flush();   
        out.println("set ylabel \"Fitness\"");  
        out.flush();   
        out.println("plot '"+datFile+"' with lines"); 
        out.flush();   
		
        /*StreamWriter gnupStWr = extPro.StandardInput;
        gnupStWr.WriteLine("reset");
        gnupStWr.Flush();
        gnupStWr.WriteLine("set autoscale");
        gnupStWr.Flush();
        gnupStWr.WriteLine("set term png");
        gnupStWr.Flush();
        gnupStWr.WriteLine("set output \"" + pngFile + "\"");
        gnupStWr.Flush();
        gnupStWr.WriteLine("set xlabel \"Iteration\"");
        gnupStWr.Flush();
        gnupStWr.WriteLine("set ylabel \"Fitness\"");
        gnupStWr.Flush();
        gnupStWr.WriteLine("plot '"+datFile+"' with lines");*/
    }
		
}
