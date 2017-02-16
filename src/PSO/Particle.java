package PSO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Particle implements Comparable<Particle> {
	private double fitness; //particles current fitness
	protected double[] position; //particles position
	private double[] velocity; //particles velocity
	private double pBestFitness; //particles best fitness
	private double[] pBest; //particles best position
	private double[] nBest;
	private double maxPos;
	private double minPos;
	int functionNumber;
	private double maxVelocity;
	private double minVelocity;
	double newVelocity;
	double c1 = 2.05;
	double c2 = 2.05;
	double w = c1 + c2;
	double X = 2 / Math.abs(2 - w - Math.sqrt(w * w - 4 * w)); // double X = .72984;
	private static int particleCount = 1;
	public int particleNo;
	protected ArrayList<Particle> neighbourhood  = new ArrayList<Particle>();
	protected ArrayList<Particle> notNeighbours ;
	public double neighbourhoodNumber;
	int dimensions; //dimensions of problem
	private double distance;
	Problem problem;

	public Particle(Problem problem) {
		particleCount  = 1;
		particleNo = particleCount;
		particleCount++;
		this.problem = problem;
		dimensions = problem.getDimensions();
		position = new double[dimensions];
		velocity = new double[dimensions];
		pBest = new double[dimensions];
		setnBest(new double[dimensions]);
		maxPos = problem.getMaxPosition();
		minPos = problem.getMinPosition();			
		for (int i = 0; i < dimensions; i++) {
			//set min and max position arrays

			Random random = new Random();
			// set random position and velocity
			double randomPosition = (minPos + ((maxPos - minPos) * random.nextDouble()));
			position[i] = randomPosition;
			// set random velocity
			//velocity[i] = Math.random();
			//double randomVelocity = minVelocity[i] + ((maxVelocity[i] - minVelocity[i]) * random.nextDouble());
			double hi = Math.abs(maxPos - minPos);			
            double lo = -1.0 * Math.abs(maxPos - minPos);
            maxVelocity = hi;
    		minVelocity = lo;
            velocity[i] = (hi - lo) * random.nextDouble() + lo;			
		}
		fitness = problem.getFitness(position);
		pBest = position.clone();
		nBest = position.clone();
		pBestFitness = fitness;	
	}

	// finish updating velocity and update position
	public void update(double[] nBest, double[] gBest, double evoFactor) {	
		Random rand1 = new Random();
		
		Random rand2 = new Random();
		
		double r1 = rand1.nextDouble();
		double r2 = rand2.nextDouble();
		double r3 = rand2.nextDouble();
		for (int i = 0; i < dimensions; i++) {
			
			
			newVelocity = (X*(velocity[i]
					//local best component
					+ (c1 * rand1.nextDouble() * (pBest[i] - position[i]))
					//global best component
					+ (c2 * rand2.nextDouble() * (nBest[i] - position[i]))));
			
			/*c1 = 4.1 * (evoFactor);
			double remainder = 4.1 - c1;
			c2 = evoFactor * remainder;
			double c3 = (1 - evoFactor) * remainder;
			newVelocity = (X*(velocity[i]
					//local best component
					+ (c1 * rand1.nextDouble() * (pBest[i] - position[i]))
					//global best component
					+ (c2*(1-evoFactor) * rand2.nextDouble() * (gBest[i] - position[i]))
					
					+ (c2*(evoFactor) * rand2.nextDouble() * (nBest[i] - position[i]))));*/
			
			/*c1 = 1.55 + (1 * (evoFactor));
			double remainder = 4.1 - c1;
			c2 = remainder;
			newVelocity = (X*(velocity[i]
					//local best component
					+ (c1 * rand1.nextDouble() * (pBest[i] - position[i]))
					//global best component				
					+ (c2 * rand2.nextDouble() * (nBest[i] - position[i]))));*///convergence=low evoFactor Ac
			
			/*c1 = 1.55 + (1 * (1-evoFactor));
			double remainder = 4.1 - c1;
			c2 = remainder;
			newVelocity = (X*(velocity[i]
					//local best component
					+ (c1 * rand1.nextDouble() * (pBest[i] - position[i]))
					//global best component				
					+ (c2 * rand2.nextDouble() * (nBest[i] - position[i]))));*///convergence=high evoFactor Ac2
			
			/*newVelocity = (X*(velocity[i]
					//local best component
					+ (c1 * rand1.nextDouble() * (pBest[i] - position[i]))
					//global best component
					+ (c2 * rand2.nextDouble() * (gBest[i] - position[i]))
					
					+ (c3 * rand2.nextDouble() * (nBest[i] - position[i]))));*/
			//newVelocity = X *( velocity[i] + c1 * rand1 * (pBest[i] - position[i]) + c2 * rand2 * (gBest[i] - position[i]));
			if (newVelocity < minVelocity) 
				velocity[i] = minVelocity;			
			else if (newVelocity > maxVelocity) 
				velocity[i] = maxVelocity;
			
			else velocity[i] = newVelocity;			
		}
		boolean calcFitness = true;
		
		//make sure particle is still in boundaries
		//below are different boundary conditions for repositioning particle if outside boundary
		for (int i = 0; i < dimensions; i++) {
			position[i] += velocity[i];
			
			//ignore particle until inside the boundary and allow particle to correct itself
			if(position[i] < minPos || position[i] > maxPos){
				calcFitness = false;
			}
			//give it a random position
			/*if(position[i] < minPos || position[i] > maxPos){
				//double randomPosition = (minPos + ((maxPos - minPos) * rand1.nextDouble()));
				//position[i] = randomPosition;
				position[i] -= velocity[i];
			}*/
			
			//re-assign to boundary edge
			/*if (position[i] < minPosition[i]) {
				position[i] = minPosition[i];
			}
			if (position[i] > maxPosition[i]) {
				position[i] = maxPosition[i];
			}*/					
		}
		if (calcFitness){
			fitness = problem.getFitness(position);
		}
	}

	/*Calculates the fitness of the current solution and updates 
	 * the particle's local best if it is better
	 */
	public void updatePBest(){
		if(fitness < pBestFitness){
			pBestFitness = fitness;
			pBest = position.clone();				
		}				
	}
	
	
	
	public double getFitness() {		
		return fitness;
	}	
	//Get the particles best position
	public double[] getPBest(){
		return pBest;
	}
	//Get the particles best fitness
	public double getPBestFitness(){
		return pBestFitness;
	}
	public double[] getVelocity(){
		return velocity;
	}
	public int getDimensions() {
		return dimensions;
	}	

	public int compareTo(Particle par) {
		return this.getFitness() < par.getFitness() ? -1:1;
	}

	public double[] getPosition() {
		return position;
	}
	
	public void addNeighbour(Particle neighbour){
		neighbourhood.add(neighbour);
	}
	public void removeNotNeighbours(Particle neighbour){
		notNeighbours.remove(neighbour);
	}
	public void setNotNeighbours(ArrayList<Particle> swarm){
		notNeighbours = swarm;
	}

	public double[] getnBest() {
		return nBest;
	}

	public void setnBest(double[] nBest) {
		this.nBest = nBest;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}
}
