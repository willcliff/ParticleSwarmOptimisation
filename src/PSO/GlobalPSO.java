package PSO;

import java.util.Collections;

public class GlobalPSO extends PSO{
	
	public GlobalPSO(Problem problem){
		super(problem);
		System.out.println(problem.getDimensions());
		psoType = "GlobalSPSO";
		System.out.println("Commencing Global PSO!\n");
	}
		
	/*@Override
	public Particle bestNeighbour(int i) {
		return gBestParticle;		
	}*/
	@Override
	protected double[] calculateNeighbourhoodBest(int i) {
		//Particle particle = Collections.min(swarm);
		//System.out.println("Gbest particle from global typology!");
		return gBest;
		
	}

	@Override
	void createNeighbourhood() {
		// TODO Auto-generated method stub
		
	}
}
