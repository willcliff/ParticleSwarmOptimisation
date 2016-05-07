package PSO;

import java.util.Collections;

public class LocalPSO extends PSO{
	//double gBestFitness;
	
	public LocalPSO(Problem problem){
		super(problem);
		System.out.println("Commencing Local PSO!\n");
	}
	//Need to create a best neighbour and add/remove neighbour
	
	@Override
	protected Particle calculateNeighbourhoodBest(int i) {
			//System.out.println("Gbest particle from local typology!");
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
	
}
