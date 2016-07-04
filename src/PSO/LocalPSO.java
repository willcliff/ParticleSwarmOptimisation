package PSO;

import java.util.ArrayList;
import java.util.Collections;

public class LocalPSO extends PSO{
	//double gBestFitness;
	
	
	public LocalPSO(Problem problem){
		super(problem);
		psoType = "RingSPSO";
		System.out.println("Commencing Local PSO!\n");
	}
	//Need to create a best neighbour and add/remove neighbour
	
	@Override
	protected double[] calculateNeighbourhoodBest(int i) {
			//System.out.println("Gbest particle from local typology!");
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
			//return swarm.get(indexBestParticle);
			
	}

	@Override
	void createNeighbourhood() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void averageDistance() {
		// TODO Auto-generated method stub
		
	}
}
