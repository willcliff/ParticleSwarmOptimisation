package PSO;

public class Problem {
	protected int dimensions;
	private double minPosition;
	private double maxPosition;
	int function;
	
	
	//private double[] minVelocity;
	//private double[] maxVelocity;
	//protected int dimensions = 30;
	public Problem(int functionNumber){
		function = functionNumber;
		switch(function){
		
		case 1: //Sphere
			System.out.println("Sphere Function: f(x) = ∑ (xi^2 -10cos(2*pi*xi) + 10)");
			dimensions = 30;
			minPosition = -5.12;
			maxPosition = 5.12;
			break;
		case 2: //Rosenbrock
			System.out.println("Rosenbrock Function: f(x) = ∑ (100(xi+1 - xi^2)^2 + (xi - 1)^2)");
			dimensions = 30;
			minPosition = -2.048;
			maxPosition = 2.048;			
			break;
		case 3: //Ackley
			System.out.println("Ackley Function: f(x) = -20*exp(-0.2*sqrt(1/n * ∑(xi^2)) - exp(1/n*∑(cos(2*pi*xi)) + 20 + exp(1)");
			dimensions = 30;
			minPosition = -32.768;
			maxPosition = 32.768;			
			break;
		case 4: //Griewank
			System.out.println("Griewank Function: f(x) = 1 + (1/4000) * ∑(xi^2) - N(cos(xi/pi))");
			dimensions = 30;
			minPosition = -600;
			maxPosition = 600; 
			break;
		case 5: //Rastrigin
			System.out.println("Rastrigin Function: f(x) = ∑ (xi^2 - 10cos(2*pi*xi) + 10)");
			dimensions = 30;	
	 		minPosition = -5.12;
	 		maxPosition = 5.12;	 
			break;
		case 6: //Schaffer2D
			System.out.println("Shaeffer2D Function: f(x) = 0.5 - ((sin(sqrt(x1^2+x2^2)))^2 - 0.5))/(1 + 0.001(x1^2+x2^2))^2");
			dimensions = 2;	
	 		minPosition = -100;
	 		maxPosition = 100;		
	 		break;
		case 7: //Griewank10D
			System.out.println("Griewank10D Function: f(x) = 1 + (1/4000) * ∑(xi^2) - N(cos(xi/pi))");
			dimensions = 10;
			minPosition = -600;
			maxPosition = 600; 
			break;
		}
		System.out.println("Min Position: " + minPosition + "\tMax POsition: " + maxPosition + "\tDimensions: " + dimensions);
	}
	public double getFitness(double[] position){
		double fitness = 0;
		switch(function){
			case 1: //Sphere
				for (int i = 0; i < dimensions; i++) {
					fitness += position[i] * position[i]; //- 10 * Math.cos(2 * Math.PI * position[i]) + 10;
				}	
				break;			
			case 2: //Rosenbrock				
				for (int i = 1; i <= (dimensions - 1); i++) {
					double xi = position[(i - 1)];
					double yi = position[i];
					fitness += (100.0 * ((yi - (xi * xi)) * (yi - (xi * xi)))) + ((1.0 - xi) * (1.0 - xi));
				}
				break;
			case 3: //Ackley
				double a = 20.0, b = 0.2, c = 2.0 * Math.PI;
				
				double sum1 = 0.0;
				double sum2 = 0.0;
				for (int i = 0; i < dimensions; i++) {
					double xi = position[i];
					sum1 += (xi * xi);
					sum2 += Math.cos(c * xi);
				}						
				fitness = (-a * Math.exp(-b * Math.sqrt(sum1 / dimensions))) - Math.exp(sum2 / dimensions) + a + Math.E;
				break;
			case 4: //Griewank
				double sum = 0.0;
				double prod = 1.0;
				for (int i = 1; i <= dimensions; i++) {
					double xi = position[(i - 1)];
					sum += (xi * xi);
					prod *= Math.cos(xi / Math.sqrt(i));
				}
				//System.out.println("Sum: " + sum);
				//System.out.println("Prod: " + prod);
		        fitness = (sum / 4000.0) - prod + 1.0;	
		       //System.out.println("Fitness: " + fitness);
		        break;
			case 5: //Rastrigin
				sum = 0.0;
				for (int i = 0; i < dimensions; i++) {
					double xi = position[i];
					sum += (xi * xi) - (10.0 * Math.cos(2.0 * Math.PI * xi));
				}
				fitness =  10.0 * dimensions + sum;
		        break;
			case 6: //Schaffer2D	
				double fact1 = (Math.sin(position[0] * position[0] - position[1] * position[1]) * Math.sin(position[0] * position[0] - position[1] * position[1]) - 0.5);
				double fact2 = ((1 + 0.001 * (position[0] * position[0] + position[1] * position[1])) * (1 + 0.001 * (position[0] * position[0] + position[1] * position[1])));
				fitness = 0.5 + fact1/fact2; // f(x) = 0.5 - ((sin(sqrt(x1^2+x2^2)))^2 - 0.5))/(1 + 0.001(x1^2+x2^2))^2

	            //fitness = 0.5 - ((Math.sin(position[0] * position[0] + position[1] * position[1])) * (Math.sin(position[0] * position[0] + position[1] * position[1])) - 0.5) / ((1 + 0.001 * (position[0] * position[0] + position[1] * position[1])) * (1 + 0.001 * (position[0] * position[0] + position[1] * position[1])));
				break;
			case 7: //Griewank10D
				sum = 0.0;
				prod = 1.0;
				for (int i = 1; i <= dimensions; i++) {
					double xi = position[(i - 1)];
					sum += (xi * xi);
					prod *= Math.cos(xi / Math.sqrt(i));
				}
		        fitness = (sum / 4000.0) - prod + 1.0;		
		        break;		
		}
		return fitness;		
	}
	/*public double ackley(double[] position){//Ackley	 		
		double fitness = 0;		
		double sum1 = 0.0;
		double sum2 = 0.0;
		for (int i = 0; i < dimensions; i++) {
			sum1 += position[i] * position[i];
			sum2 += Math.cos(2.0 * Math.PI * position[i]);
		}	
		fitness = -20 * Math.exp(-0.2 * Math.sqrt(sum1 / dimensions)) - Math.exp((sum2 / dimensions)) + 20 + Math.exp(1);
		return fitness;		
	}*/
	
	
	/*public Problem(){//Ackley
	 	System.out.println("Ackley Function: f(x) = -20*exp(-0.2*sqrt(1/n * ∑(xi^2)) - exp(1/n*∑(cos(2*pi*xi)) + 20 + exp(1)");
		dimensions = 30;
		minPosition = new double[dimensions];
		maxPosition = new double[dimensions];
		for (int i = 0; i < dimensions; i++) {
			minPosition[i] = -32.768;
			maxPosition[i] = 32.768;			
		}
	}
	public double getFitness(double[] position) {
		double fitness = 0;		
		double sum1 = 0.0;
		double sum2 = 0.0;
		for (int i = 0; i < dimensions; i++) {
			sum1 += position[i] * position[i];
			sum2 += Math.cos(2.0 * Math.PI * position[i]);
		}
		//return (-a * Math.exp(-b * Math.sqrt(sum1 / dimensions))) - Math.exp(sum2 / dimensions) + a + Math.E;
		
		fitness = -20 * Math.exp(-0.2 * Math.sqrt(sum1 / dimensions)) - Math.exp((sum2 / dimensions)) + 20 + Math.exp(1);
		return fitness;		
	}*/	
	
	
	/*public Problem(){//Rosenbrock
		System.out.println("Rosenbrock Function: f(x) = ∑ (100(xi+1 - xi^2)^2 + (xi - 1)^2)");
		dimensions = 30;
		minPosition = new double[dimensions];
		maxPosition = new double[dimensions];
		//minVelocity = new double[dimensions];
		//maxVelocity = new double[dimensions];
		for (int i = 0; i < dimensions; i++) {
			minPosition[i] = -2.048;
			maxPosition[i] = 2.048;
			//minVelocity[i] = -1;
			//maxVelocity[i] = 1;
		}
	}	
	public double getFitness(double[] position) {
		double fitness = 0.0;
		for (int i = 0; i < dimensions - 1; i++) {
			//double xi = position[(i - 1)];
			//double yi = position[i];
			//fitness = fitness + (100.0 * ((yi - (xi * xi)) * (yi - (xi * xi)))) + ((1.0 - xi) * (1.0 - xi));
			
			fitness += 100 * (position[i + 1] - position[i] * position[i]) * (position[i + 1] - position[i] * position[i]) + (position[i] - 1) * (position[i] - 1);
		}
		return fitness;
	}
	/*for (int i = 0; i < dimension - 1; i++)
    {
        Fitness += 100 * (Position[i + 1] - Position[i] * Position[i]) * (Position[i + 1] - Position[i] * Position[i]) + (Position[i] - 1) * (Position[i] - 1);
    }*/
	
	/*public Problem(){//Sphere
	 	System.out.println("Sphere Function: f(x) = ∑ (xi^2 -10cos(2*pi*xi) + 10)");
		dimensions = 30;
		minPosition = new double[dimensions];
		maxPosition = new double[dimensions];

		for (int i = 0; i < dimensions; i++) {
			minPosition[i] = -5.12; // -100
			maxPosition[i] = 5.12;	// 100
		}
	}	
	public double getFitness(double[] position) {
		double fitness = 0.0;
		for (int i = 0; i < dimensions; i++) {
			
			fitness += position[i] * position[i] - 10 * Math.cos(2 * Math.PI * position[i]) + 10;
		}		
		return fitness;				
	}*/
	
	 /*public Problem() //Griewank
     {		 
		 System.out.println("Griewank Function: f(x) = 1 + (1/4000) * ∑(xi^2) - N(cos(xi/pi))");
		 dimensions = 30;
		 minPosition = new double[dimensions];
		 maxPosition = new double[dimensions];
         
         for (int i = 0; i < dimensions; i++) {
 			minPosition[i] = -600;
 			maxPosition[i] = 600;			
 		}      
     }	 
	 public double getFitness(double[] position) {
			double fitness = 0.0;
			double sum = 0;
	        double prod = 1;
			for (int i = 0; i < dimensions; i++)
	         {
	             sum += position[i] * position[i];
	             prod = prod * Math.cos(position[i] / Math.PI);
	         }
	         fitness = 1 + (1 / 4000) * sum - prod;						
	         return fitness;	
     }*/
	
	/*public Problem() //Rastrigin
     {		 
		 System.out.println("Rastrigin Function: f(x) = ∑ (xi^2 - 10cos(2*pi*xi) + 10)");
		 dimensions = 30;
		 minPosition = new double[dimensions];
		 maxPosition = new double[dimensions];
         
         for (int i = 0; i < dimensions; i++) {
 			minPosition[i] = -600;
 			maxPosition[i] = 600;			
 		}        
     }	 
	 public double getFitness(double[] position) {
			double fitness = 0.0;
			fitness = 0; 
            for (int i = 0; i < dimensions; i++)
            {
                fitness += position[i] * position[i] - 10 * Math.cos(2 * Math.PI * position[i]) + 10;
            }					
	         return fitness;							
     }*/		
	public int getDimensions() {
		return dimensions;
	}
	public double getMinPosition() {
		return minPosition;
	}
	public double getMaxPosition() {
		return maxPosition;
	}
}


