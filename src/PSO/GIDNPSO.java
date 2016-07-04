package PSO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GIDNPSO extends PSO {
	
	 int b = 2;//initial number of neighbours
     int y = 2;//rate of population increase
	
	
	public GIDNPSO(Problem problem){
		super(problem);
		psoType = "GIDNPSO";
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
				nBestFitness = neighbour.getFitness();				
			}
		}
		return nBest;
		// TODO Auto-generated method stub		
	}
	@Override
	void averageDistance() {
		// TODO Auto-generated method stub
		
	}
}
