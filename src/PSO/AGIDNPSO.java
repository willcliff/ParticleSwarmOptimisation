package PSO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class AGIDNPSO extends PSO {
	
	 int b = 2;//initial number of neighbours
     double y = 2;//rate of population increase
     String stateFactor;
     String parameters = "b = " + b + "\ty = " + y + "\th = (1 - evoFactor) * Math.pow(((iteration + 1.0) / iterations), y) * swarmSize + b";
	
	public AGIDNPSO(Problem problem, int iterations){
		super(problem, iterations);
		psoType = "AGIDNPSO";
		//psoType = "AGIDNPSOIncrY";
		//psoType = "AGIDNPSODecrY";
		//psoType = "AGIDNPSOAc";
		//psoType = "AGIDNPSOAc2";

		System.out.println("Commencing PSO AGIDN!\n");
		for(Particle particle : swarm){
			particle.neighbourhoodNumber = 0;			
		}	
	}
	public void createNeighbourhood(){
		for(int i = 0; i < swarmSize; i++){
			Particle particle = swarm.get(i);
			particle.notNeighbours = new ArrayList<Particle>(swarm);
					
			//GIDN Method
			//if i>0 leftNeighbour = i-1, if i=0 leftNeighbour = last particle
			int indexLeftNeighbour = (i > 0) ? i - 1 : swarmSize - 1;
			//if particle isn't last particle rightNeighbour = i+1, if i=last particle rightNeighbour = first particle
			int indexRightNeighbour = (i < swarmSize - 1) ? i + 1 : 0;		
			
			//Particle particle = swarm.get(i);
			Particle leftNeighbour = swarm.get(indexLeftNeighbour);
			Particle rightNeighbour = swarm.get(indexRightNeighbour);
			
			particle.addNeighbour(particle);
			particle.removeNotNeighbours(particle);
			
			particle.addNeighbour(leftNeighbour);
			particle.removeNotNeighbours(leftNeighbour);
			
			particle.addNeighbour(rightNeighbour);
			particle.removeNotNeighbours(rightNeighbour);
		}	
	}
	
	@Override
	public double[] calculateNeighbourhoodBest(int i) {
		Particle particle = swarm.get(i);
		updateNeighbourhoods(particle);	
		double[] nBest = particle.getPBest().clone();
		double nBestFitness = Double.MAX_VALUE;
		for(Particle neighbour : particle.neighbourhood){			
			if (neighbour.getPBestFitness() < nBestFitness) {
				nBest = neighbour.getPBest().clone();
				nBestFitness = neighbour.getPBestFitness();				
			}
		}
		return nBest;	
	}
		
	public void updateNeighbourhoods(Particle particle){
		Random random = new Random();
			double previousH = particle.neighbourhoodNumber;
			double h;
			//averageDistance(i); Placed in PSO class to calculate EvoFactor for all PSo types
			
			h = (1 - evoFactor) * Math.pow(((iteration + 1.0) / iterations), y) * swarmSize + b; //v8			

			h = Math.floor(h);
			particle.neighbourhoodNumber = h;
			if (h > previousH) {
				int numberOfNeighbours = (int) (h - previousH);
				for (int j = 0; j < numberOfNeighbours; j++) {
					if (particle.notNeighbours.size() == 0)
						break;

					// add new random neighbour
					int neighbour = (int) (random.nextDouble() * particle.notNeighbours.size());

					particle.addNeighbour(particle.notNeighbours.get(neighbour));
					particle.removeNotNeighbours(particle.notNeighbours.get(neighbour));
				}
			}			
	}
	
	public void averageDistance(){
		ArrayList<Double> distances = new ArrayList<Double>();
		for(Particle currentParticle : swarm){
			
			double totalDistance = 0;
			double averageDistance = 0;
			for (int i = 1; i <= swarmSize; i++){
				double distance = 0;
				Particle otherParticle = swarm.get(i-1);
				if (i != currentParticle.particleNo){
					for(int d = 0; d < dimensions; d++){
						distance += (currentParticle.position[d] - otherParticle.position[d]) * (currentParticle.position[d] - otherParticle.position[d]);
					}	
				totalDistance += Math.sqrt(distance);				
				}				
			}
			averageDistance = totalDistance / (swarmSize -1);
			currentParticle.setDistance(averageDistance);
			distances.add(currentParticle.getDistance());			
		}
		evoFactor(distances);	
	}
	
	public double evoFactor(ArrayList<Double> distances){
		double minDistance = Collections.min(distances);
		double maxDistance = Collections.max(distances);
		double gBestDistance = gBestParticle.getDistance();
		
		evoFactor = ((gBestDistance - minDistance)/(maxDistance - minDistance));
		//evoDecision(evoFactor);Include this for adaptive y and c versions
		
		return evoFactor;
	}
	
	public void evoDecision(double evoFactor){
		
		if(evoFactor > .6 && evoFactor <= .9){
			stateFactor = "Exploration";
			y = 2;
		}
		
		else if(evoFactor > .3 && evoFactor <= .6){
			stateFactor = "Exploitation";
			y = 2;
		}
		
		else if(evoFactor >= 0 && evoFactor <= .3){
			stateFactor = "Convergence";
			y = 2;
		}
		else if(evoFactor > .9 && evoFactor <= 1){
			stateFactor = "Jump Out";
			y = 2;
		}				
	}

}
