package PSO;

import java.util.Collections;

public class GlobalPSO extends PSO{
	
	public GlobalPSO(Problem problem, int numberOfRuns){		
		super(problem, numberOfRuns);
		psoType = "GlobalSPSO";
		System.out.println("Commencing Global PSO!\n");
	}
		
	/*@Override
	public Particle bestNeighbour(int i) {
		return gBestParticle;		
	}*/
	@Override
	protected Particle calculateNeighbourhoodBest(int i) {
		//Particle particle = Collections.min(swarm);
		//System.out.println("Gbest particle from global typology!");
		return gBestParticle;
		
	}
}
