package PSO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class GIDNPSONEW2 extends PSO {
	
	 int b = 2;//initial number of neighbours
     double y = 2;//rate of population increase
     
     String parameters = "b = " + b + "\ty = " + y + "\th = (1 - evoFactor) * ((iteration + 1.0) / iterations) * swarmSize + b";
     String stateFactor;
     
	
	public GIDNPSONEW2(Problem problem){
		super(problem);
		//psoType = "GIDNPSONEW";
		//psoType = "GIDNPSONEW2";
		//psoType = "GIDNPSONEW10xIts";
		//psoType = "GIDNPSONEWTest";
		//psoType = "Test";
		psoType = "GIDNPSONEWv8withEvoFactsTestevo";
		System.out.println("Commencing PSO GIDNNEW!\n");
		for(Particle particle : swarm){
			particle.neighbourhoodNumber = 0;			
		}	
	}
	public void createNeighbourhood(){
		for(int i = 0; i < swarmSize; i++){
			Particle particle = swarm.get(i);
			particle.notNeighbours = new ArrayList<Particle>(swarm);
			
			
			/*//Alternative method with changeable b
			particle.addNeighbour(particle);
			particle.removeNotNeighbours(particle);
			//System.out.println("notNeighbours:" + particle.notNeighbours.size());
			for(int j = 0; j < b; j++){
				//System.out.println("notNeighboursChanging:" + particle.notNeighbours.size());
				Random random = new Random();
				int selectedParticle = random.nextInt((particle.notNeighbours.size() - 0) + 1) + 0;
				//System.out.println("selectedParticle:" + selectedParticle);
				Particle newParticle = swarm.get(selectedParticle);
				particle.addNeighbour(newParticle);
				particle.removeNotNeighbours(newParticle);
			}
			//System.out.println("notNeighboursNew:" + particle.notNeighbours.size());
			*/
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
		////System.out.println("Particle:" + particle.particleNo + "beforNeighbourhoodSize" + particle.neighbourhood.size());
		////System.out.println("Particle:" + particle.particleNo + "beforeNotNeighbourhoodSize" + particle.notNeighbours.size());
		//if(iteration % 100 == 0){
			//updateNeighbourhoods(particle);
		//}
		updateNeighbourhoods(particle);
		////System.out.println("Particle:" + particle.particleNo + "NeighbourhoodSize" + particle.neighbourhood.size());
		////System.out.println("Particle:" + particle.particleNo + "NotNeighbourhoodSize" + particle.notNeighbours.size());		
		double[] nBest = particle.getPBest().clone();
		double nBestFitness = Double.MAX_VALUE;
		for(Particle neighbour : particle.neighbourhood){			
			if (neighbour.getPBestFitness() < nBestFitness) {
				nBest = neighbour.getPBest().clone();
				//neighbour.nBest = neighbour.getPosition().clone();
				nBestFitness = neighbour.getFitness();				
			}
		}
		return nBest;
		// TODO Auto-generated method stub		
	}
	
	/*protected double[] calculateNeighbourhoodBest(int i) {
		//System.out.println("Gbest particle from local typology!");
		
		if(stateFactor == "Exploration" ||  stateFactor == "Jump Out" || stateFactor == "Exploitation"){
		int indexBestParticle = i;
		int indexLeftNeighbour = (i > 0) ? i - 1 : swarmSize - 1;
		int indexRightNeighbour = (i < swarmSize - 1) ? i + 1 : 0;		
				
		double nBestFitness = swarm.get(i).getPBestFitness();
		double leftNeighborParticlePBestFitness = swarm.get(indexLeftNeighbour).getPBestFitness();
		double rightNeighborParticlePBestFitness = swarm.get(indexRightNeighbour).getPBestFitness();
				
		if (leftNeighborParticlePBestFitness < nBestFitness) {
			indexBestParticle = indexLeftNeighbour;
			nBestFitness = leftNeighborParticlePBestFitness;
		}
		if (rightNeighborParticlePBestFitness < nBestFitness) {
			indexBestParticle = indexRightNeighbour;
			nBestFitness = rightNeighborParticlePBestFitness;
		}
		return swarm.get(indexBestParticle).getPBest();
		}
		else{
			return gBest;
		}
				
		//return swarm.get(indexBestParticle);
		
	}*/
	
	public void updateNeighbourhoods(Particle particle){//should loop be here?
		Random random = new Random();
		
		//for(int i = 0; i < swarmSize; i++){
			
			//Particle particle  = swarm.get(i);
			double previousH = particle.neighbourhoodNumber;
			double h;
			//averageDistance(i);
			//double h = (((iteration + 1.0) / iterations) * ((iteration + 1.0) / iterations)) * swarmSize + b;
			////System.out.println("previoush = " + previousH);
			////System.out.println("evoFactor = " + evoFactor);
			
			//double h = Math.pow(((iteration + 1.0) / iterations), y) * swarmSize + b;
		/*if (evoFactor == 0) {
			h = previousH + b;
			particle.neighbourhoodNumber = h;
			System.out.println("h = " + h);
			int numberOfNeighbours = (int) (h - previousH);
			for (int j = 0; j < numberOfNeighbours; j++) {
				if (particle.notNeighbours.size() == 0)
					break;

				// add new random neighbour
				int neighbour = (int) (random.nextDouble() * particle.notNeighbours.size());
				// System.out.println("AddNotNeighbourNo: " + neighbour);
				particle.addNeighbour(particle.notNeighbours.get(neighbour));
				particle.removeNotNeighbours(particle.notNeighbours.get(neighbour));
			}
		}
		else{*/
			//h = Math.pow(((iteration + 1.0) / iterations), y) * swarmSize + b;
			//h = Math.pow(((iteration + 1.0) / iterations), (y + evoFactor)) * swarmSize + b; //v9Test
			//h = Math.pow((1/evoFactor), y) * swarmSize + b;
			////h = (1 - evoFactor) * swarmSize + b;
			////h = Math.pow((1-evoFactor), y) * swarmSize + b;
			h = (1 - evoFactor) * ((iteration + 1.0) / iterations) * swarmSize + b; //v8
			//h = (1 - evoFactor) * Math.pow(((iteration + 1.0) / iterations), y) * swarmSize + b;
			//h = (1 - evoFactor) * particle.neighbourhood.size() + b;
			//h = Math.pow((evoFactor), y) * swarmSize + b; doesn't work as adds too many too soon
			//h = (1 / evoFactor) * swarmSize + b; doesn't work unless != 0 and adds too many too soon
			

			h = Math.floor(h);
			////System.out.println("h = " + h);
			particle.neighbourhoodNumber = h;
			if (h > previousH) {
				int numberOfNeighbours = (int) (h - previousH);
				////System.out.println("Particle:" + particle.particleNo + "NumOfNeighboursToAdd" + numberOfNeighbours);
				for (int j = 0; j < numberOfNeighbours; j++) {
					if (particle.notNeighbours.size() == 0)
						break;

					// add new random neighbour
					int neighbour = (int) (random.nextDouble() * particle.notNeighbours.size());
					// System.out.println("AddNotNeighbourNo: " + neighbour);
					particle.addNeighbour(particle.notNeighbours.get(neighbour));
					particle.removeNotNeighbours(particle.notNeighbours.get(neighbour));
				}
			}
		//}
		//}				
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
			y = 2;
		}
		
		else if(evoFactor >= 0 && evoFactor <= .3){
			y = 2;
			/*if (evoFactor == 0){
				this.evoFactor = .1 * y;
			}*/
			stateFactor = "Convergence";
			
		}
		else if(evoFactor > .9 && evoFactor <= 1){
			stateFactor = "Jump Out";
			y = 2;
		}				
	}

}
