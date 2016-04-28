package PSO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GIDNPSO extends PSO {
	
	 int b = 2;//initial number of neighbours
     int y = 2;//rate of population increase
	
	
	public GIDNPSO(){
		super();
		for(Particle particle : swarm){
			particle.neighbourhoodNumber = 0;			
		}	
	}
	public Particle createNeighbourhood(int i){
		swarm.get(i).notNeighbours = swarm;
		
		int indexBestParticle = i;
		int indexLeftNeighbour = (i > 0) ? i - 1 : swarmSize - 1;
		int indexRightNeighbour = (i < swarmSize - 1) ? i + 1 : 0;		
				
		gBestFitness = swarm.get(i).getPBestFitness();
		double leftNeighborParticlePBestFitness = swarm.get(indexLeftNeighbour).getPBestFitness();
		double rightNeighborParticlePBestFitness = swarm.get(indexRightNeighbour).getPBestFitness();
				
		if (leftNeighborParticlePBestFitness < gBestFitness) {
			indexBestParticle = indexLeftNeighbour;
			gBestFitness = leftNeighborParticlePBestFitness;
		}
		if (rightNeighborParticlePBestFitness < gBestFitness) {
			indexBestParticle = indexRightNeighbour;
			gBestFitness = rightNeighborParticlePBestFitness;
		}
		return swarm.get(indexBestParticle);
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
	public Particle calculateNeighbourhoodBest(int i) {
		for(Particle particle : neighbourhood){
			if (particle.getFitness() < nBestFitness) {
				nBest = particle.getPosition().clone();
				nBestFitness = particle.getFitness();				
			}			
		}
		return gBestParticle;
		// TODO Auto-generated method stub		
	}

}
