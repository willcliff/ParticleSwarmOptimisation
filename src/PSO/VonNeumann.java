package PSO;

public class VonNeumann extends PSO{

	public VonNeumann(Problem problem) {
		super(problem);	
		psoType = "vonNeumann";
		System.out.println("Commencing vonNeumann PSO!\n");
		// TODO Auto-generated constructor stub
	}
	
	public void createNeighbourhood(){
		// 12 particles: 3x4 array
		// 20 particles: 4x5 array
		// 50 particles: 5x10 array
		int rowSize = 5; // 20 or 50 particle swarm

		for (int i = 0; i < swarmSize; i++) {
			
			Particle particle = swarm.get(i);
			//System.out.println("Createing Neighbourhhod for " + particle.particleNo);
			particle.addNeighbour(particle);
			//int[] temp = new int[4];

			/*if (swarmSize==12) { //12 particle swarm
				rowSize = 4;
			}*/

			if (i < rowSize) { // top row
				particle.addNeighbour(swarm.get(swarmSize + i - rowSize)); //top addition
				particle.addNeighbour(swarm.get(i+rowSize)); //bottom addition
			} 
			else if (i + rowSize >= swarmSize) { // bottom row
				particle.addNeighbour(swarm.get(i-rowSize)); //top addition
				particle.addNeighbour(swarm.get(i + rowSize - swarmSize)); //bottom addition
			} 
			else { //middle rows
				particle.addNeighbour(swarm.get(i-rowSize)); //top addition
				particle.addNeighbour(swarm.get(i+rowSize)); //bottom addition
			}
			
			if (i % rowSize == 0) { //left column
				particle.addNeighbour(swarm.get(i+(rowSize-1))); //left addition
				particle.addNeighbour(swarm.get(i+1)); //right addition				
			} 
			else if ((i+1) % rowSize == 0) { //right column
				particle.addNeighbour(swarm.get(i-1)); //left addition
				particle.addNeighbour(swarm.get(i-(rowSize-1))); //right addition
			}
			else { //middle rows
				particle.addNeighbour(swarm.get(i-1)); //left addition
				particle.addNeighbour(swarm.get(i+1)); //right addition
			}
			//System.out.println("Printing Neighbourhood Nums for " + particle.particleNo);
			for(Particle neighbour : particle.neighbourhood){	
				///System.out.println(neighbour.particleNo);
			}			
		}
		
	}
	@Override
	public double[] calculateNeighbourhoodBest(int i) {
		Particle particle = swarm.get(i);		
		double[] nBest = new double[problem.getDimensions()];
		double nBestFitness = Double.MAX_VALUE;
		for(Particle neighbour : particle.neighbourhood){
			if (neighbour.getPBestFitness() < nBestFitness) {
				//neighbour.setnBest(neighbour.getPosition());
				nBest = neighbour.getPBest().clone();
				nBestFitness = neighbour.getPBestFitness();				
			}
		}
		// TODO Auto-generated method stub
		return nBest;
	}

	@Override
	void averageDistance() {
		// TODO Auto-generated method stub
		
	}

}
