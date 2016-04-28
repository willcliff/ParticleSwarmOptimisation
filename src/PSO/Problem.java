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
		case 8: //Ellipsoidal
			System.out.println("Ellipsoidal");
			dimensions = 10;
			minPosition = -100;
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
			case 8:
				    fitness = 0.0;
				    //sr_func(x,z,nx,Os,Mr,1.0,s_flag,r_flag);/*shift and rotate*/
				        
				    for (int i=0; i<dimensions; i++)
				    {
				        fitness += Math.pow(10.0,6.0*i/(dimensions-1))*position[i]*position[i];
				    }
				    	
				//f[i]=ellips_func(t,f[i],nx,OShift,M,1,1);
				//f[i]+=100.0;
				break;
		}
		return fitness;	
		
		//z=position
				/*double ellips_func (double[] x, double f, int nx, double[] Os,double[] Mr,int s_flag,int r_flag) 
				{
				    int i;
				    f = 0.0;
				    sr_func(x,z,nx,Os,Mr,1.0,s_flag,r_flag);
				        
				    for (i=0; i<nx; i++)
				    {
				        f += Math.pow(10.0,6.0*i/(nx-1))*z[i]*z[i];
				    }
				    return f;
				}*/			
	}

	/*void shiftfunc (double[] x, double[] xshift, int nx,double[] Os)
	{
		int i;
	    for (i=0; i<nx; i++)
	    {
	        xshift[i]=x[i]-Os[i];
	    }
	}
	
	
	void rotatefunc (double[] x, double[] xrot, int nx,double[] Mr)
	{
		int i,j;
	    for (i=0; i<nx; i++)
	    {
	        xrot[i]=0;
				for (j=0; j<nx; j++)
				{
					xrot[i]=xrot[i]+x[j]*Mr[i*nx+j];
				}
	    }
	}
	//sh_rate = 1
	void sr_func (double[] x, double[] sr_x, int nx, double[] Os, double[] Mr, double sh_rate, int s_flag, int r_flag)
	{
		int i,j;
		if (s_flag==1)
		{
			if (r_flag==1)
			{	
				shiftfunc(x, y, nx, Os);
				for (i=0; i<nx; i++)//shrink to the orginal search range
				{
					y[i]=y[i]*sh_rate;
				}
				rotatefunc(y, sr_x, nx, Mr);
			}
			else
			{
				shiftfunc(x, sr_x, nx, Os);
				for (i=0; i<nx; i++)//shrink to the orginal search range
				{
					sr_x[i]=sr_x[i]*sh_rate;
				}
			}
		}
		else
		{	

			if (r_flag==1)
			{	
				for (i=0; i<nx; i++)//shrink to the orginal search range
				{
					y[i]=x[i]*sh_rate;
				}
				rotatefunc(y, sr_x, nx, Mr);
			}
			else
			
			{
				for (j=0; j<nx; j++)//shrink to the orginal search range
				{
					sr_x[j]=x[j]*sh_rate;
				}
			}
		}		
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


