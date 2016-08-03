package PSO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class GIDNPSO extends PSO {
	
	 int b = 2;//initial number of neighbours
     int y = 2;//rate of population increase
     String stateFactor;
	
	public GIDNPSO(Problem problem){
		super(problem);
		//psoType = "GIDNPSO";
		psoType = "GIDNPSOIncreasingy";
		//psoType = "GIDNPSODecreasingy";
		System.out.println("Commencing PSO GIDN!\n");
		for(Particle particle : swarm){
			particle.neighbourhoodNumber = 0;			
		}	
	}
	public void createNeighbourhood(){
		for(int i = 0; i < swarm.size(); i++){
			swarm.get(i).notNeighbours = new ArrayList<Particle>(swarm);
			//if i>0 leftNeighbour = i-1, if i=0 leftNeighbour = last particle
			int indexLeftNeighbour = (i > 0) ? i - 1 : swarmSize - 1;
			//if particle isn't last particle rightNeighbour = i+1, if i=last particle rightNeighbour = first particle
			int indexRightNeighbour = (i < swarmSize - 1) ? i + 1 : 0;		
			
			Particle particle = swarm.get(i);
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
	
	public void updateNeighbourhoods(){
		Random random = new Random();
		
		for(Particle particle : swarm){

			double previousH = particle.neighbourhoodNumber;
			
			double h = (((iteration + 1.0) / iterations) * ((iteration + 1.0) / iterations)) * swarmSize + b;
			h = Math.floor(h);
			particle.neighbourhoodNumber = h;
			if(h > previousH){
				int numberOfNeighbours = (int) (h - previousH);
				
				for(int i = 0; i < numberOfNeighbours; i++){
					if (particle.notNeighbours.size() == 0)
                        break;
					
                    //add new random neighbour
                    int neighbour = (int) (random.nextDouble() * particle.notNeighbours.size());
                    particle.addNeighbour(particle.notNeighbours.get(neighbour));					
				}
			}						
		}				
	}

	@Override
	public double[] calculateNeighbourhoodBest(int i) {
		updateNeighbourhoods();
		Particle particle = swarm.get(i);		
		double[] nBest = particle.getPBest().clone();
		double nBestFitness = Double.MAX_VALUE;
		for(Particle neighbour : particle.neighbourhood){			
			if (neighbour.getPBestFitness() < nBestFitness) {
				nBest = neighbour.getPBest().clone();
				//neighbour.nBest = neighbour.getPosition().clone();
				nBestFitness = neighbour.getPBestFitness();				
			}
		}
		return nBest;
		// TODO Auto-generated method stub		
	}
	@Override
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
						//distance += Math.pow((currentParticle.position[d] - otherParticle.position[d]), 2);
						distance += (currentParticle.position[d] - otherParticle.position[d]) * (currentParticle.position[d] - otherParticle.position[d]);
					}	
				// look at else ending } and totalDistance formula
				totalDistance += Math.sqrt(distance);
				
				}
				
			}
			//System.out.println("Distance: " + totalDistance);
			averageDistance = totalDistance / (swarmSize -1);
			currentParticle.setDistance(averageDistance);
			distances.add(currentParticle.getDistance());
			//System.out.println("Particle No." + currentParticle.particleNo + " Average Distance " + averageDistance);
			//return averageDistance;
			
		}
		evoFactor(distances);
	
	}
	
	public double evoFactor(ArrayList<Double> distances){
		//double evoFactor;
		double minDistance = Collections.min(distances);
		double maxDistance = Collections.max(distances);
		//gBestParticle = Collections.min(swarm);
		double gBestDistance = gBestParticle.getDistance();
		//System.out.println("minDistance: " + minDistance);
		//System.out.println("maxDistance: " + maxDistance);
		//System.out.println("gBestParticle num " + gBestParticle.particleNo + "\tBestDistance: " + gBestDistance);
		
		evoFactor = ((gBestDistance - minDistance)/(maxDistance - minDistance));
		//evoFactor = ((maxDistance - gBestDistance)/(maxDistance - minDistance));
		
		//System.out.println("Evo Factor: " + evoFactor + "\n");
		evoDecision(evoFactor);
		return evoFactor;
	}
	
	public void evoDecision(double evoFactor){
		
		if(evoFactor > .6 && evoFactor <= .9){
			stateFactor = "Exploration";
			y = 2;
		}
		
		else if(evoFactor > .3 && evoFactor <= .6){
			stateFactor = "Exploitation";
			y = 3;
		}
		
		else if(evoFactor >= 0 && evoFactor <= .3){
			stateFactor = "Convergence";
			y = 4;
		}
		else if(evoFactor > .9 && evoFactor <= 1){
			stateFactor = "Jump Out";
			y = 2;
		}				
	}
}
