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
	//private static int particleCount = 1;
	public int particleNo;
	protected ArrayList<Particle> neighbourhood  = new ArrayList<Particle>();
	protected ArrayList<Particle> notNeighbours ;
	public double neighbourhoodNumber;
	int dimensions; //dimensions of problem
	private double distance;
	Problem problem;

	public Particle(Problem problem) {
		//particleCount  = 1;
		//particleNo = particleCount;
		//particleCount++;
		//System.out.println("Particle Number: " + particleNo);
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
			
			// make sure particle is still in boundaries
			/*if (position[i] < minPosition[i]) {
				position[i] = minPosition[i];
			}
			if (position[i] > maxPosition[i]) {
				position[i] = maxPosition[i];
			}*/
			
			

			// set random velocity
			//velocity[i] = Math.random();
			//double randomVelocity = minVelocity[i] + ((maxVelocity[i] - minVelocity[i]) * random.nextDouble());
			double hi = Math.abs(maxPos - minPos);			
            double lo = -1.0 * Math.abs(maxPos - minPos);
            maxVelocity = hi;
    		minVelocity = lo;
            velocity[i] = (hi - lo) * random.nextDouble() + lo;			
            //velocity[i] = randomVelocity;
		}
		//System.out.println("max velocity: " + maxVelocity);
		//System.out.println("min velocity: " + minVelocity);
		//System.out.println("particle position: " + Arrays.toString(position));		
		//System.out.println("particle velocity: " + Arrays.toString(velocity));
		//Initialise pBest
		//set pBest = to initial position
		fitness = problem.getFitness(position);
		pBest = position.clone();
		nBest = position.clone();
		pBestFitness = fitness;
		//System.out.println("particle" + particleNo + "fitness: " + fitness);		
	}

	// finish updating velocity and update position
	public void update(double[] lBest) {
		Random rand1 = new Random();
		Random rand2 = new Random();
		
		//System.out.println("Particle Number: " + particleNo + "Velocity Before: " + Arrays.toString(velocity));
		//System.out.println("Particle Number: " + particleNo + "Position Before: " + Arrays.toString(getPosition()));
		double r1 = rand1.nextDouble();
		double r2 = rand2.nextDouble();
		for (int i = 0; i < dimensions; i++) {
			
			
			newVelocity = (X*(velocity[i]
					//local best component
					+ (c1 * rand1.nextDouble() * (pBest[i] - position[i]))
					//global best component
					+ (c2 * rand2.nextDouble() * (lBest[i] - position[i]))));
			//newVelocity = X *( velocity[i] + c1 * rand1 * (pBest[i] - position[i]) + c2 * rand2 * (gBest[i] - position[i]));
			if (newVelocity < minVelocity) 
				velocity[i] = minVelocity;			
			else if (newVelocity > maxVelocity) 
				velocity[i] = maxVelocity;
			
			else velocity[i] = newVelocity;			
		}
		//System.out.println("r1: " + r1 + " r2: " + r2);
		//System.out.println("rand1.nextDouble() " + rand1.nextDouble() + " rand2.nextDouble() " + rand2.nextDouble());
		
		
		boolean calcFitness = true;
		
		//position = location;
		for (int i = 0; i < dimensions; i++) {
			position[i] += velocity[i];
			/*if(position[i] < minPos || position[i] > maxPos){
				double randomPosition = (minPos + ((maxPos - minPos) * rand1.nextDouble()));
				position[i] = randomPosition;
			}*/
			// make sure particle is still in boundaries
			/*if (position[i] < minPosition[i]) {
				position[i] = minPosition[i];
			}
			if (position[i] > maxPosition[i]) {
				position[i] = maxPosition[i];
			}*/
			/*if (Math.abs(position[i]) > maxPosition[i]){
                calcFitness = false;
			}*/
			if(position[i] < minPos || position[i] > maxPos){
				calcFitness = false;
			}
			
		}
		//System.out.println("Particle Number: " + particleNo + "New Velocity: " + newVelocity);
		//System.out.println("Particle Number: " + particleNo + "Velocity After: " + Arrays.toString(velocity));
		//System.out.println("Particle Number: " + particleNo + "Position After: " + Arrays.toString(position));
		if (calcFitness){
			fitness = problem.getFitness(position);
			//getFitness(position);
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
	@Override
	public int compareTo(Particle par) {
		// TODO Auto-generated method stub
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
