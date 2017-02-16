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
	int iterations;
	int swarmSize = 50;
	int iteration;
	double[] averageFitnesses;
	double[] averageNeighbSize;
	double[] evoFacts;
	double evoFactor;
	Date dateTime = new Date();
	SimpleDateFormat ft = new SimpleDateFormat ("E yyyy-MM-dd'at'hhmmss a zzz");
	String date = new String(ft.format(dateTime));
	ArrayList<Particle> swarm = new ArrayList<Particle>();
	double gBestFitness; // globally best fitness
	double[] gBest;
	Particle gBestParticle;// globally(swarms) best position
	protected String psoType = "Standard";	 // int dimensions = problem.getDimensions();
	int dimensions;	
	double standardDeviation;	
	 // globally best particle
		
	public PSO(Problem problem, int iterations) {
		this.problem = problem;
		this.iterations = iterations;
		dimensions = problem.getDimensions();
		averageFitnesses = new double[iterations];
		averageNeighbSize = new double[iterations];
		evoFacts = new double[iterations];
	}

	public PSO(String psoType, int swarmSize, Problem problem, double standardDeviation) {
		this.psoType = psoType;
		// this.swarmSize = swarmSize;
		this.problem = problem;
		this.standardDeviation = standardDeviation;
		
	}
	
	public void execute() {
		initialise();
		iterate();
	}

	private void initialise() {	
		for (int i = 0; i < swarmSize; i++) {
			Particle particle = new Particle(problem);
			swarm.add(particle);			
		}
		gBestParticle = swarm.get(0);
		gBest = gBestParticle.getPosition().clone();
		gBestFitness = swarm.get(0).getFitness();
		calculateGBest();
		createNeighbourhood();	
	}

	abstract void createNeighbourhood();
	
	public void iterate() {
		double[] swarmFitnesses = new double[swarmSize];
		
		for (int j = 0; j < iterations; j++) {
			iteration = j;
			averageDistance();
			evoFacts[j] = evoFactor;

		// calculate the pBest and gBest positions
			for (int i = 0; i < swarmSize; i++) {
				Particle particle = swarm.get(i);
				averageNeighbSize[j]  += particle.neighbourhood.size();				
				
				// step1 update pBest
				particle.updatePBest();

				// step2 update gBest
				calculateGBest();

				// step 3 update the velocity and position
				double[] nBest = calculateNeighbourhoodBest(i).clone();

				// step 4 update the position
				particle.update(nBest, gBest, evoFactor);
			}
		averageFitnesses[j] += gBestFitness;
		averageNeighbSize[j] = averageNeighbSize[j] / swarmSize;		
		}
	}
	
	abstract void averageDistance();
	abstract double[] calculateNeighbourhoodBest(int i);
	public void calculateGBest(){
		gBestParticle = Collections.min(swarm);
		gBestFitness = gBestParticle.getPBestFitness();
		gBest = gBestParticle.getPosition().clone();	
	}

}
