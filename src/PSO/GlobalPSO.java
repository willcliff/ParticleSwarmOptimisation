package PSO;

import java.util.Collections;

public class GlobalPSO extends PSO{
	
	public GlobalPSO(){		
		super();
		System.out.println("Commencing Global PSO!\n");
	}
		
	/*@Override
	public Particle bestNeighbour(int i) {
		return gBestParticle;		
	}*/
	
	public void calculateGBest(int i) {
		//Particle particle = Collections.min(swarm);
		//System.out.println("Gbest particle from global typology!");
		for(Particle particle : swarm){
			if (particle.getFitness() < gBestFitness) {
				gBestParticle = particle;
				gBest = particle.getPosition().clone();
				gBestFitness = particle.getFitness();				
			}
			
		}
		
	}
}
