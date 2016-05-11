package PSO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Scanner;

public abstract class Problem {
	public String functionName;
	protected int dimensions;
	protected double minPosition;
	protected double maxPosition;
	//int function;
	final double INF = 1.0e99;
	final double EPS = 1.0e-14;
	final double E  = 2.7182818284590452353602874713526625;
	final double PI = 3.1415926535897932384626433832795029;
	
	double[] OShift,M,y,z,x_bound; //z = sr_x; shifted and rotated position
	int ini_flag,n_flag,func_flag;
	int[] SS;
	
	
	public Problem(){
		System.out.println("Problem Constructor1");		
	}
	
	/*public Problem(int functionNumber)throws Exception{
		System.out.println("Problem Constructor2");
		function = functionNumber;
		switch(functionNumber){
		
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
			functionNumber = 1;
			dimensions = 30;
			minPosition = -100;
			maxPosition = 100;
			break;
		case 13: //happycat_func
			System.out.println("happycat_func");
			functionNumber = 13;
			dimensions = 30;
			minPosition = -100;
			maxPosition = 100;
			break;		
		}
				
		System.out.println("Min Position: " + minPosition + "\tMax POsition: " + maxPosition + "\tDimensions: " + dimensions);
		if(function >= 8 && function <= 30){	
			
		int cf_num=10,i,j;
		if (ini_flag==1) 
		{
			if ((n_flag!=dimensions)||(func_flag!=functionNumber)) //check if nx or func_num are changed, reinitialization
			{
				ini_flag=0;
			}
		}
		if (ini_flag==0) // initiailization
		{
			
			y=new double[dimensions];
			z=new double[dimensions];
			x_bound=new double[dimensions];
			for (i=0; i<dimensions; i++)
				x_bound[i]=100.0;

			if (!(dimensions==2||dimensions==10||dimensions==20||dimensions==30||dimensions==50||dimensions==100))
			{
				System.out.println("\nError: Test functions are only defined for D=2,10,20,30,50,100.");
			}
			
			if (dimensions==2&&((functionNumber>=17&&functionNumber<=22)||(functionNumber>=29&&functionNumber<=30)))
			{
				System.out.println("\nError: hf01,hf02,hf03,hf04,hf05,hf06,cf07&cf08 are NOT defined for D=2.\n");
			}

			//Load Matrix M*****************************************************
			File fpt = new File("input_data/M_"+functionNumber+"_D"+dimensions+".txt");// Load M data 
			Scanner input = new Scanner(fpt);
			if (!fpt.exists())
			{
			    System.out.println("\n Error: Cannot open input file for reading ");
			}
			
			if (functionNumber<23)
			{
				M=new double[dimensions*dimensions]; 
			
				for (i=0;i<dimensions*dimensions; i++)
				{
					M[i]=input.nextDouble();
				}
			}
			else
			{
				M=new double[cf_num*dimensions*dimensions]; 
				
				for (i=0; i<cf_num*dimensions*dimensions; i++)
				{
						M[i]=input.nextDouble();
				}
				
			}
			input.close();
			System.out.println("Matrix: " + Arrays.toString(M));
			
			//Load shift_data***************************************************
		
			
			
			if (functionNumber<23)
			{
				fpt=new File("input_data/shift_data_"+functionNumber+".txt");
				input = new Scanner(fpt);
				if (!fpt.exists())
				{
					System.out.println("\n Error: Cannot open input file for reading ");
				}
				
				OShift=new double[dimensions];
				for(i=0;i<dimensions;i++)
				{
					OShift[i]=input.nextDouble();
					if (OShift == null)
					{
						System.out.println("\nError: there is insufficient memory available!");
					}
				}
				input.close();
			}
			else
			{
			
				OShift=new double[dimensions*cf_num];
				
				fpt=new File("input_data/shift_data_"+functionNumber+".txt");								
				FileReader reader = new FileReader(fpt);
				BufferedReader br = new BufferedReader(reader);
				String[] s = new String[100];
								
				for (i=0;i<cf_num;i++){
					s[i] = br.readLine();
					String[] array = s[i].split("\\s+");
					double[] temp = new double[array.length-1];

					for ( int k = 0; k < array.length-1; k++) {
					    temp[k]= Double.parseDouble(array[k+1]);
					    
					}
					
					for (j=0;j<dimensions;j++){
						
						OShift[i*dimensions+j] = temp[j];
						
					}
				
				}
				
				br.close();
				reader.close();
				input.close();
										
				
			}
			
			input.close();
			System.out.println("Shift Data: " + Arrays.toString(OShift));
			
			
			//Load Shuffle_data*******************************************
			
			if (functionNumber>=17&&functionNumber<=22)
			{
				fpt = new File("input_data/shuffle_data_"+functionNumber+"_D"+dimensions+".txt");
				//sprintf(FileName, "input_data/shuffle_data_%d_D%d.txt", func_num, nx);
				//fpt = fopen(FileName,"r");
				input = new Scanner(fpt);
				if (!fpt.exists())
				{
				    System.out.println("\n Error: Cannot open input file for reading ");
				}
				
				//SS=(int *)malloc(nx*sizeof(int));
				SS = new int[dimensions];
				
				for(i=0;i<dimensions;i++)
				{
					//fscanf(fpt,"%d",&SS[i]);
					SS[i] = input.nextInt();
				}	
			}
			else if (functionNumber==29||functionNumber==30)
			{
				//sprintf(FileName, "input_data/shuffle_data_%d_D%d.txt", func_num, nx);
				fpt = new File("input_data/shuffle_data_"+functionNumber+"_D"+dimensions+".txt");
				//fpt = fopen(FileName,"r");
				input = new Scanner(fpt);
				if (!fpt.exists())
				{
				    System.out.println("\n Error: Cannot open input file for reading ");
				}
				
				//SS=(int *)malloc(nx*cf_num*sizeof(int));
				SS = new int[dimensions*cf_num];
				
				for(i=0;i<dimensions*cf_num;i++)
				{
					//fscanf(fpt,"%d",&SS[i]);
					SS[i] = input.nextInt();
				}
			}
			input.close();
			System.out.println("Shufffle Data: " + Arrays.toString(SS));
			n_flag=dimensions;
			func_flag=functionNumber;
			ini_flag=1;
						
						
		}
		}
	}*/
	public abstract double getFitness(double[] position);
		/*double fitness = 0;
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
				fitness = ellips_func(position, dimensions, OShift, M, 1, 1);
				break;
				
			case 13:
				fitness = happycat_func(position, dimensions, OShift, M, 1, 1);
				break;	
		}
		return fitness;	
	}
		
		double ellips_func(double[] position, int dimensions, double[] Os, double[] Mr, int s_flag, int r_flag) {
			int i;
			double f = 0.0;
			sr_func(position, z, dimensions, Os, Mr, 1.0, s_flag, r_flag);

			for (i = 0; i < dimensions; i++) {
				f += Math.pow(10.0, 6.0 * i / (dimensions - 1)) * z[i] * z[i];
			}
			return f;
		}
		
		double happycat_func(double[] position, int dimensions, double[] Os,double[] Mr,int s_flag,int r_flag) 
		
		{
			int i;
			double f = 0.0;
			double alpha,r2,sum_z;
			alpha = 1.0/8.0;
			
			sr_func(position,z,dimensions,Os,Mr,5/100.0,s_flag,r_flag);//shift and rotate
			
			r2 = 0.0;
			sum_z = 0.0;
			f = 0.0;
			for (i=0;i<dimensions;i++)
			{
				z[i] = z[i] - 1.0; //shift to orgin
				r2 += z[i]*z[i];
				sum_z += z[i];
				
			}
			f = Math.pow(Math.abs(r2-dimensions), 2*alpha) + (0.5*r2 + sum_z)/dimensions + 0.5;
			
			return f;
		}
		

		void shiftfunc (double[] x, double[] xshift, int nx,double[] Os)
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
		//sr_func(position, z, dimensions, Os, Mr, 1.0, s_flag, r_flag);
		void sr_func (double[] position, double[] sr_x, int dimensions, double[] Os, double[] Mr, double sh_rate, int s_flag, int r_flag)
		{
			int i,j;
			if (s_flag==1)
			{
				if (r_flag==1)
				{	
					shiftfunc(position, y, dimensions, Os);
					for (i=0; i<dimensions; i++)//shrink to the orginal search range
					{
						y[i]=y[i]*sh_rate;
					}
					rotatefunc(y, sr_x, dimensions, Mr);
				}
				else
				{
					shiftfunc(position, sr_x, dimensions, Os);
					for (i=0; i<dimensions; i++)//shrink to the orginal search range
					{
						sr_x[i]=sr_x[i]*sh_rate;
					}
				}
			}
			else
			{	

				if (r_flag==1)
				{	
					for (i=0; i<dimensions; i++)//shrink to the orginal search range
					{
						y[i]=position[i]*sh_rate;
					}
					rotatefunc(y, sr_x, dimensions, Mr);
				}
				else
				
				{
					for (j=0; j<dimensions; j++)//shrink to the orginal search range
					{
						sr_x[j]=position[j]*sh_rate;
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


