package PSO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Scanner;

public class TestFunc14 extends Problem{
	
	int function;
	final double INF = 1.0e99;
	final double EPS = 1.0e-14;
	final double E  = 2.7182818284590452353602874713526625;
	final double PI = 3.1415926535897932384626433832795029;
	
	
	double[] OShift,M,y,z,x_bound; //z = sr_x; shifted and rotated position
	int ini_flag,n_flag,func_flag;
	int[] SS;	
	
	//tf.test_func(x, f, dimension,population_size,func_num);
	public TestFunc14(int functionNumber)throws Exception{
		dimensions = 30;
		minPosition = -100;
		maxPosition = 100; 
		function = functionNumber;
		switch(function){
		
		case 1: //Ellipsoidal
			functionName = "f" + function;
			System.out.println("Ellipsoidal Function");			
			break;
		case 2:
			functionName = "f" + function;
			System.out.println("bent_cigar_func Function");	
			break;
		case 3:
			functionName = "f" + function;
			System.out.println("discus_func Function");	
			break;
		case 4:
			functionName = "f" + function;
			System.out.println("rosenbrock_func Function");	
			break;
		case 5:
			functionName = "f" + function;
			System.out.println("ackley_func Function");	
			break;
		case 6:
			functionName = "f" + function;
			System.out.println("weierstrass_func Function");	
			break;
		case 7:
			functionName = "f" + function;
			System.out.println("griewank_func Function");	
			break;
		case 8:
			functionName = "f" + function;
			System.out.println("rastrigin_func Function");	
			break;
		case 9:
			functionName = "f" + function;
			System.out.println("rastrigin_func non rotated Function");	
			break;
		case 10:
			functionName = "f" + function;
			System.out.println("schwefel_func Function");	
			break;
		case 11:
			functionName = "f" + function;
			System.out.println("schwefel_func non rotated Function");	
			break;
		case 12:
			functionName = "f" + function;
			System.out.println("katsuura_func Function");	
			break;
		case 13: //happycat_func
			functionName = "f" + function;
			System.out.println("happycat_func");			
			break;
		case 14:
			functionName = "f" + function;
			System.out.println("hgbat_func");
			break;
		case 15:
			functionName = "f" + function;
			System.out.println("grie_rosen_func");
			break;
		case 16:
			functionName = "f" + function;
			System.out.println("escaffer6_func");
			break;
		case 17:
			functionName = "f" + function;
			System.out.println("hf01");
			break;
		case 18:
			functionName = "f" + function;
			System.out.println("hf02");
			break;
		case 19:
			functionName = "f" + function;
			System.out.println("hf03");
			break;
		case 20:
			functionName = "f" + function;
			System.out.println("hf04");
			break;
		case 21:
			functionName = "f" + function;
			System.out.println("hf05");
			break;
		case 22:
			functionName = "f" + function;
			System.out.println("hf06");
			break;
		case 23:
			functionName = "f" + function;
			System.out.println("cf01");
			break;
		case 24:
			functionName = "f" + function;
			System.out.println("cf02");
			break;
		case 25:
			functionName = "f" + function;
			System.out.println("cf03");
			break;
		case 26:
			functionName = "f" + function;
			System.out.println("cf04");
			break;
		case 27:
			functionName = "f" + function;
			System.out.println("cf05");
			break;
		case 28:
			functionName = "f" + function;
			System.out.println("cf06");
			break;
		case 29:
			functionName = "f" + function;
			System.out.println("cf08");
			break;
		case 30:
			functionName = "f" + function;
			System.out.println("cf08");
			break;
		}
		System.out.println("Min Position: " + minPosition + "\tMax Position: " + maxPosition + "\tDimensions: " + dimensions);
		
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

	public double getFitness(double[] position) {
		double fitness = 0.0;

		switch (function) {

		case 1:
			fitness = ellips_func(position,fitness, dimensions, OShift, M, 1, 1);
			break;
		case 2:	
			fitness=bent_cigar_func(position,fitness,dimensions,OShift,M,1,1);
			break;
		case 3:	
			fitness=discus_func(position,fitness,dimensions,OShift,M,1,1);
			break;
		case 4:	
			fitness=rosenbrock_func(position,fitness,dimensions,OShift,M,1,1);
			break;
		case 5:
			fitness=ackley_func(position,fitness,dimensions,OShift,M,1,1);
			break;
		case 6:
			fitness=weierstrass_func(position,fitness,dimensions,OShift,M,1,1);
			break;
		case 7:	
			fitness=griewank_func(position,fitness,dimensions,OShift,M,1,1);
			break;
		case 8:	
			fitness=rastrigin_func(position,fitness,dimensions,OShift,M,1,0);
			break;
		case 9:	
			fitness=rastrigin_func(position,fitness,dimensions,OShift,M,1,1);
			break;
		case 10:	
			fitness=schwefel_func(position,fitness,dimensions,OShift,M,1,0);
			break;
		case 11:	
			fitness=schwefel_func(position,fitness,dimensions,OShift,M,1,1);
			break;
		case 12:	
			fitness=katsuura_func(position,fitness,dimensions,OShift,M,1,1);
			break;
		case 13:
			fitness = happycat_func(position,fitness,dimensions,OShift,M,1,1);
			break;
		case 14:	
			fitness =hgbat_func(position,fitness,dimensions,OShift,M,1,1);
			break;
		case 15:	
			fitness=grie_rosen_func(position,fitness,dimensions,OShift,M,1,1);

			break;
		case 16:	
			fitness=escaffer6_func(position,fitness,dimensions,OShift,M,1,1);

			break;
		case 17:	
			fitness=hf01(position,fitness,dimensions,OShift,M,SS,1,1);

			break;
		case 18:	
			fitness=hf02(position,fitness,dimensions,OShift,M,SS,1,1);

			break;
		case 19:	
			fitness=hf03(position,fitness,dimensions,OShift,M,SS,1,1);

			break;
		case 20:	
			fitness=hf04(position,fitness,dimensions,OShift,M,SS,1,1);

			break;
		case 21:	
			fitness=hf05(position,fitness,dimensions,OShift,M,SS,1,1);

			break;
		case 22:	
			fitness=hf06(position,fitness,dimensions,OShift,M,SS,1,1);

			break;
		case 23:	
			fitness=cf01(position,fitness,dimensions,OShift,M,1);
			break;
		case 24:	
			fitness=cf02(position,fitness,dimensions,OShift,M,1);
			break;
		case 25:	
			fitness=cf03(position,fitness,dimensions,OShift,M,1);
			break;
		case 26:
			fitness=cf04(position,fitness,dimensions,OShift,M,1);
			break;
		case 27:
			fitness=cf05(position,fitness,dimensions,OShift,M,1);
			break;
		case 28:
			fitness=cf06(position,fitness,dimensions,OShift,M,1);
			break;
		case 29:
			fitness=cf07(position,fitness,dimensions,OShift,M,SS,1);
			break;
		case 30:
			fitness=cf08(position,fitness,dimensions,OShift,M,SS,1);
			break;

		default:
			System.out.println("\nError: There are only 30 test functions in this test suite!");
			fitness = 0.0;
			break;
		}
		return fitness;
	}
	
	double ellips_func(double[] position, double f, int dimensions, double[] Os, double[] Mr, int s_flag, int r_flag) {
		int i;
		sr_func(position, z, dimensions, Os, Mr, 1.0, s_flag, r_flag);

		for (i = 0; i < dimensions; i++) {
			f += Math.pow(10.0, 6.0 * i / (dimensions - 1)) * z[i] * z[i];
		}
		return f;
	}
	
	double bent_cigar_func (double[] x, double f, int nx, double[] Os,double[] Mr,int s_flag,int r_flag) /* Bent_Cigar */
	{
		int i;
		sr_func(x,z,nx,Os,Mr,1.0,s_flag,r_flag);/*shift and rotate*/
	    
		f = z[0]*z[0];
		for (i=1; i<nx; i++)
	    {
	        f += Math.pow(10.0,6.0)*z[i]*z[i];
	    }
	    return f;
	}
		
	double discus_func (double[] x, double f, int nx, double[] Os,double[] Mr,int s_flag,int r_flag) /* Discus */
	{
	    int i;
	    sr_func(x,z,nx,Os,Mr,1.0,s_flag,r_flag);/*shift and rotate*/

		f = Math.pow(10.0,6.0)*z[0]*z[0];
	    for (i=1; i<nx; i++)
	    {
	        f += z[i]*z[i];
	    }
	    
	    return f;
	}
	
	double rosenbrock_func (double[] x, double f, int nx, double[] Os,double[] Mr,int s_flag,int r_flag) /* Rosenbrock's */
	{
	    int i;
		double tmp1,tmp2;
		f = 0.0;
	    sr_func(x,z,nx,Os,Mr,2.048/100.0,s_flag,r_flag);/*shift and rotate*/
	    z[0] +=1.0; //shift to origin
	    for (i=0; i<nx-1; i++)
	    {
			z[i+1] += 1.0; //shift to orgin
	    	tmp1=z[i]*z[i]-z[i+1];
			tmp2=z[i]-1.0;
	        f += 100.0*tmp1*tmp1 +tmp2*tmp2;
	    }
	    
	    
	    return f;
	}
	
	double ackley_func (double[] x, double f, int nx, double[] Os,double[] Mr,int s_flag,int r_flag) /* Ackley's  */
	{
	    int i;
	    double sum1, sum2;
	    sum1 = 0.0;
	    sum2 = 0.0;
	    
	    sr_func(x,z,nx,Os,Mr,1.0,s_flag,r_flag);/*shift and rotate*/ 		
	    
	    for (i=0; i<nx; i++)
	    {
	        sum1 += z[i]*z[i];
	        sum2 += Math.cos(2.0*PI*z[i]);
	    }
	    sum1 = -0.2*Math.sqrt(sum1/nx);
	    sum2 /= nx;
	    f =  E - 20.0*Math.exp(sum1) - Math.exp(sum2) +20.0;
	    
	    return f;
	}
	
	double weierstrass_func (double[] x, double f, int nx, double[] Os,double[] Mr,int s_flag,int r_flag) /* Weierstrass's  */
	{
	    int i,j,k_max;
	    double sum,sum2=0, a, b;
	    
	    sr_func(x,z,nx,Os,Mr,0.5/100.0,s_flag,r_flag);/*shift and rotate*/ 
		
			   
	    a = 0.5;
	    b = 3.0;
	    k_max = 20;
	    f = 0.0;
	    for (i=0; i<nx; i++)
	    {
	        sum = 0.0;
			sum2 = 0.0;
	        for (j=0; j<=k_max; j++)
	        {
	            sum += Math.pow(a,j)*Math.cos(2.0*PI*Math.pow(b,j)*(z[i]+0.5));
				sum2 += Math.pow(a,j)*Math.cos(2.0*PI*Math.pow(b,j)*0.5);
	        }
	        f += sum;
	    }
		f -= nx*sum2;
		
		return f;
	}
	
	double griewank_func (double[] x, double f, int nx, double[] Os,double[] Mr,int s_flag,int r_flag) /* Griewank's  */
	{
	    int i;
	    double s, p;

	    sr_func(x,z,nx,Os,Mr,600.0/100.0,s_flag,r_flag);/*shift and rotate*/ 

	    s = 0.0;
	    p = 1.0;
	    for (i=0; i<nx; i++)
	    {
	        s += z[i]*z[i];
	        p *= Math.cos(z[i]/Math.sqrt(1.0+i));
	    }
	    f = 1.0 + s/4000.0 - p;
	    
	    return f;
	}
	
	double rastrigin_func (double[] x, double f, int nx, double[] Os,double[] Mr,int s_flag,int r_flag) /* Rastrigin's  */
	{
	    int i;
		f=0.0;
	    
		sr_func(x,z,nx,Os,Mr,5.12/100.0,s_flag,r_flag);/*shift and rotate*/ 

		for(i=0;i<nx;i++)
		{
			f += (z[i]*z[i] - 10.0*Math.cos(2.0*PI*z[i]) + 10.0);
		}
	    
	    return f;
	}
		
	double schwefel_func (double[] x, double f, int nx, double[] Os,double[] Mr,int s_flag,int r_flag) /* Schwefel's  */
	{
	    int i;
		double tmp;
		
		sr_func(x,z,nx,Os,Mr,1000.0/100.0,s_flag,r_flag);/*shift and rotate*/ 
		
				
	    f=0;
	    for (i=0; i<nx; i++)
		{
	    	z[i] += 4.209687462275036e+002;
	    	if (z[i]>500)
			{
				f-=(500.0-(z[i]%500))*Math.sin(Math.pow(500.0-(z[i]%500),0.5));
				tmp=(z[i]-500.0)/100;
				f+= tmp*tmp/nx;
			}
			else if (z[i]<-500)
			{
				f-=(-500.0+(Math.abs(z[i])%500))*Math.sin(Math.pow(500.0-(Math.abs(z[i])%500),0.5));
				tmp=(z[i]+500.0)/100;
				f+= tmp*tmp/nx;
			}
			else
				f-=z[i]*Math.sin(Math.pow(Math.abs(z[i]),0.5));
	    }
	    f=4.189828872724338e+002*nx+f;
	    
	    return f;
	}
	
	double katsuura_func (double[] x, double f, int nx, double[] Os,double[] Mr,int s_flag,int r_flag) /* Katsuura  */
	{
	    int i,j;
		double temp,tmp1,tmp2,tmp3;
		tmp3=Math.pow(1.0*nx,1.2);
		
		sr_func(x,z,nx,Os,Mr,5/100.0,s_flag,r_flag);/*shift and rotate*/ 
		
	    
	    f=1.0;
	    for (i=0; i<nx; i++)
		{
			temp=0.0;
			for (j=1; j<=32; j++)
			{
				tmp1=Math.pow(2.0,j);
				tmp2=tmp1*z[i];
				temp += Math.abs(tmp2-Math.floor(tmp2+0.5))/tmp1;
			}
			f *= Math.pow(1.0+(i+1)*temp,10.0/tmp3);
	    }
		tmp1=10.0/nx/nx;
	    f=f*tmp1-tmp1;
	    
	    return f;

	}
		
	double happycat_func(double[] position, double f, int dimensions, double[] Os,double[] Mr,int s_flag,int r_flag) 	
	{
		int i;
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
	
	double hgbat_func (double[] x, double f, int nx, double[] Os,double[] Mr,int s_flag,int r_flag) 
	/*HGBat, provided by Hans-Georg Beyer (HGB)*/
	/*original global optimum: [-1,-1,...-1]*/
	{
		int i;
		double alpha,r2,sum_z;
		alpha=1.0/4.0;

		sr_func (x, z, nx, Os, Mr, 5.0/100.0, s_flag, r_flag); /* shift and rotate */

		r2 = 0.0;
		sum_z=0.0;
	    for (i=0; i<nx; i++)
	    {
			z[i]=z[i]-1.0;//shift to orgin
	        r2 += z[i]*z[i];
			sum_z += z[i];
	    }
	    f=Math.pow(Math.abs(Math.pow(r2,2.0)-Math.pow(sum_z,2.0)),2*alpha) + (0.5*r2 + sum_z)/nx + 0.5;
	    return f;

	}
	
	double grie_rosen_func (double[] x, double f, int nx, double[] Os,double[] Mr,int s_flag,int r_flag) /* Griewank-Rosenbrock  */
	{
	    int i;
	    double temp,tmp1,tmp2;
	    
	    sr_func (x, z, nx, Os, Mr, 5.0/100.0, s_flag, r_flag); /* shift and rotate */

		
	    f=0.0;
	    
	    z[0] += 1.0; //shift to orgin
	    for (i=0; i<nx-1; i++)
	    {
	    	z[i+1] += 1.0; //shift to orgin
			tmp1 = z[i]*z[i]-z[i+1];
			tmp2 = z[i]-1.0;
	        temp = 100.0*tmp1*tmp1 + tmp2*tmp2;
	         f += (temp*temp)/4000.0 - Math.cos(temp) + 1.0;
	    }
		tmp1 = z[nx-1]*z[nx-1]-z[0];
		tmp2 = z[nx-1]-1.0;
	    temp = 100.0*tmp1*tmp1 + tmp2*tmp2;;
	     f += (temp*temp)/4000.0 - Math.cos(temp) + 1.0 ;
	     
	     return f;
	}
	
	double escaffer6_func (double[] x, double f, int nx, double[] Os,double[] Mr,int s_flag,int r_flag) /* Expanded Scaffer¡¯s F6  */
	{
	    int i;
	    double temp1, temp2;
	    
	    sr_func (x, z, nx, Os, Mr, 1.0, s_flag, r_flag); /* shift and rotate */

		
	    f = 0.0;
	    for (i=0; i<nx-1; i++)
	    {
	        temp1 = Math.sin(Math.sqrt(z[i]*z[i]+z[i+1]*z[i+1]));
			temp1 =temp1*temp1;
	        temp2 = 1.0 + 0.001*(z[i]*z[i]+z[i+1]*z[i+1]);
	        f += 0.5 + (temp1-0.5)/(temp2*temp2);
	    }
	    temp1 = Math.sin(Math.sqrt(z[nx-1]*z[nx-1]+z[0]*z[0]));
		temp1 =temp1*temp1;
	    temp2 = 1.0 + 0.001*(z[nx-1]*z[nx-1]+z[0]*z[0]);
	    f += 0.5 + (temp1-0.5)/(temp2*temp2);
	    
	    return f;
	}
	
	double hf01 (double[] x, double f, int nx, double[] Os,double[] Mr,int[] S,int s_flag,int r_flag) /* Hybrid Function 1 */
	{
		int i,tmp,cf_num=3;
		double[] fit = new double[3];
		int[] G = new int[3];
		int[] G_nx = new int[3];
		double[] Gp = {0.3,0.3,0.4};

		tmp=0;
		for (i=0; i<cf_num-1; i++)
		{
			G_nx[i] = (int)Math.ceil(Gp[i]*nx);
			tmp += G_nx[i];
		}
		G_nx[cf_num-1]=nx-tmp;
		G[0]=0;
		for (i=1; i<cf_num; i++)
		{
			G[i] = G[i-1]+G_nx[i-1];
		}

		sr_func (x, z, nx, Os, Mr, 1.0, s_flag, r_flag); /* shift and rotate */

		for (i=0; i<nx; i++)
		{
			y[i]=z[S[i]-1];
		}
		
		
		
		
		double[] ty,tO,tM;
		
		i=0;
		 ty = new double[G_nx[i]];
		 tO = new double[G_nx[i]];
		 tM = new double[G_nx[i]];
		for(int ii=0;ii<G_nx[i];ii++)
		{
			ty[ii]=y[ii];
			tO[ii]=Os[ii];
			tM[ii]=Mr[i*nx+ii];
		}
		fit[i]=schwefel_func(ty,fit[i],G_nx[i],tO,tM,0,0);
		
		i=1;
		ty = new double[G_nx[i]];
		tO = new double[G_nx[i]];
		tM = new double[G_nx[i]];
		for(int ii=0;ii<G_nx[i];ii++)
		{
			ty[ii]=y[G_nx[i-1]+ii];
			tO[ii]=Os[G_nx[i-1]+ii];
			tM[ii]=Mr[i*nx+ii];
		}
		fit[i]=rastrigin_func(ty,fit[i],G_nx[i],tO,tM,0,0);
		
		i=2;
		ty = new double[G_nx[i]];
		tO = new double[G_nx[i]];
		tM = new double[G_nx[i]];
		for(int ii=0;ii<G_nx[i];ii++)
		{
			ty[ii]=y[G_nx[i-2]+G_nx[i-1]+ii];
			tO[ii]=Os[G_nx[i-2]+G_nx[i-1]+ii];
			tM[ii]=Mr[i*nx+ii];
		}
		fit[i]=ellips_func(ty,fit[i],G_nx[i],tO,tM,0,0);
		
		f=0.0;
		for(i=0;i<cf_num;i++)
		{
			f += fit[i];
		}
		return f;
	}

	double hf02 (double[] x, double f, int nx, double[] Os,double[] Mr,int[] S,int s_flag,int r_flag) /* Hybrid Function 2 */
	{
		int i,tmp,cf_num=3;
		double[] fit = new double[3];
		int[] G = new int[3];
		int[] G_nx = new int[3];
		double[] Gp={0.3,0.3,0.4};

		tmp=0;
		for (i=0; i<cf_num-1; i++)
		{
			G_nx[i] = (int)Math.ceil(Gp[i]*nx);
			tmp += G_nx[i];
		}
		G_nx[cf_num-1]=nx-tmp;

		G[0]=0;
		for (i=1; i<cf_num; i++)
		{
			G[i] = G[i-1]+G_nx[i-1];
		}

		sr_func (x, z, nx, Os, Mr, 1.0, s_flag, r_flag); /* shift and rotate */

		for (i=0; i<nx; i++)
		{
			y[i]=z[S[i]-1];
		}
		
		
		
		double[] ty,tO,tM;
		
		i=0;
		ty = new double[G_nx[i]];
		tO = new double[G_nx[i]];
		tM = new double[G_nx[i]];
		for(int ii=0;ii<G_nx[i];ii++)
		{
			ty[ii]=y[ii];
			tO[ii]=Os[ii];
			tM[ii]=Mr[i*nx+ii];
		}
		fit[i]=bent_cigar_func(ty,fit[i],G_nx[i],tO,tM,0,0);
		
		i=1;
		ty = new double[G_nx[i]];
		tO = new double[G_nx[i]];
		tM = new double[G_nx[i]];
		for(int ii=0;ii<G_nx[i];ii++)
		{
			ty[ii]=y[G_nx[i-1]+ii];
			tO[ii]=Os[G_nx[i-1]+ii];
			tM[ii]=Mr[i*nx+ii];
		}
		fit[i]=hgbat_func(ty,fit[i],G_nx[i],tO,tM,0,0);
		
		i=2;
		ty = new double[G_nx[i]];
		tO = new double[G_nx[i]];
		tM = new double[G_nx[i]];
		for(int ii=0;ii<G_nx[i];ii++)
		{
			ty[ii]=y[G_nx[i-2]+G_nx[i-1]+ii];
			tO[ii]=Os[G_nx[i-1]+G_nx[i-2]+ii];
			tM[ii]=Mr[i*nx+ii];
		}
		fit[i]=rastrigin_func(ty,fit[i],G_nx[i],tO,tM,0,0);

		f=0.0;
		for(i=0;i<cf_num;i++)
		{
			f += fit[i];
		}
		return f;

	}
	
	double hf03 (double[] x, double f, int nx, double[] Os,double[] Mr,int[] S,int s_flag,int r_flag) /* Hybrid Function 3 */
	{
		int i,tmp,cf_num=4;
		double[] fit = new double[4];
		int[] G_nx = new int[4];
		int[] G = new int[4];
		double[] Gp={0.2,0.2,0.3,0.3};

		tmp=0;
		for (i=0; i<cf_num-1; i++)
		{
			G_nx[i] = (int)Math.ceil(Gp[i]*nx);
			tmp += G_nx[i];
		}
		G_nx[cf_num-1]=nx-tmp;

		G[0]=0;
		for (i=1; i<cf_num; i++)
		{
			G[i] = G[i-1]+G_nx[i-1];
		}

		sr_func (x, z, nx, Os, Mr, 1.0, s_flag, r_flag); /* shift and rotate */

		for (i=0; i<nx; i++)
		{
			y[i]=z[S[i]-1];
		}
		
		
		double[] ty,tO,tM;
		
		i=0;
		ty = new double[G_nx[i]];
		tO = new double[G_nx[i]];
		tM = new double[G_nx[i]];
		for(int ii=0;ii<G_nx[i];ii++)
		{
			ty[ii]=y[ii];
			tO[ii]=Os[ii];
			tM[ii]=Mr[i*nx+ii];
		}
		fit[i]=griewank_func(ty,fit[i],G_nx[i],tO,tM,0,0);
		
		i=1;
		ty = new double[G_nx[i]];
		tO = new double[G_nx[i]];
		tM = new double[G_nx[i]];
		for(int ii=0;ii<G_nx[i];ii++)
		{
			ty[ii]=y[G_nx[i-1]+ii];
			tO[ii]=Os[G_nx[i-1]+ii];
			tM[ii]=Mr[i*nx+ii];
		}
		fit[i]=weierstrass_func(ty,fit[i],G_nx[i],tO,tM,0,0);
		
		i=2;
		ty = new double[G_nx[i]];
		tO = new double[G_nx[i]];
		tM = new double[G_nx[i]];
		for(int ii=0;ii<G_nx[i];ii++)
		{
			ty[ii]=y[G_nx[i-1]+G_nx[i-2]+ii];
			tO[ii]=Os[G_nx[i-1]+G_nx[i-2]+ii];
			tM[ii]=Mr[i*nx+ii];
		}
		fit[i]=rosenbrock_func(ty,fit[i],G_nx[i],tO,tM,0,0);
		
		i=3;
		ty = new double[G_nx[i]];
		tO = new double[G_nx[i]];
		tM = new double[G_nx[i]];
		for(int ii=0;ii<G_nx[i];ii++)
		{
			ty[ii]=y[G_nx[i-1]+G_nx[i-2]+G_nx[i-3]+ii];
			tO[ii]=Os[G_nx[i-1]+G_nx[i-2]+G_nx[i-3]+ii];
			tM[ii]=Mr[i*nx+ii];
		}
		fit[i]=escaffer6_func(ty,fit[i],G_nx[i],tO,tM,0,0);
		
		f=0.0;
		for(i=0;i<cf_num;i++)
		{
			f += fit[i];
		}
		return f;

	}
	
	double hf04 (double[] x, double f, int nx, double[] Os,double[] Mr,int[] S,int s_flag,int r_flag) /* Hybrid Function 4 */
	{
		int i,tmp,cf_num=4;
		double[] fit = new double[4];
		int[] G = new int[4];
		int[] G_nx = new int[4];
		double[] Gp={0.2,0.2,0.3,0.3};

		tmp=0;
		for (i=0; i<cf_num-1; i++)
		{
			G_nx[i] = (int)Math.ceil(Gp[i]*nx);
			tmp += G_nx[i];
		}
		G_nx[cf_num-1]=nx-tmp;

		G[0]=0;
		for (i=1; i<cf_num; i++)
		{
			G[i] = G[i-1]+G_nx[i-1];
		}

		sr_func (x, z, nx, Os, Mr, 1.0, s_flag, r_flag); /* shift and rotate */

		for (i=0; i<nx; i++)
		{
			y[i]=z[S[i]-1];
		}
		
		
		double[] ty,tO,tM;
		
		i=0;
		ty = new double[G_nx[i]];
		tO = new double[G_nx[i]];
		tM = new double[G_nx[i]];
		for(int ii=0;ii<G_nx[i];ii++)
		{
			ty[ii]=y[ii];
			tO[ii]=Os[ii];
			tM[ii]=Mr[i*nx+ii];
		}
		fit[i]=hgbat_func(ty,fit[i],G_nx[i],tO,tM,0,0);
		
		i=1;
		ty = new double[G_nx[i]];
		tO = new double[G_nx[i]];
		tM = new double[G_nx[i]];
		for(int ii=0;ii<G_nx[i];ii++)
		{
			ty[ii]=y[G_nx[i-1]+ii];
			tO[ii]=Os[G_nx[i-1]+ii];
			tM[ii]=Mr[i*nx+ii];
		}
		fit[i]=discus_func(ty,fit[i],G_nx[i],tO,tM,0,0);
		
		i=2;
		ty = new double[G_nx[i]];
		tO = new double[G_nx[i]];
		tM = new double[G_nx[i]];
		for(int ii=0;ii<G_nx[i];ii++)
		{
			ty[ii]=y[G_nx[i-1]+G_nx[i-2]+ii];
			tO[ii]=Os[G_nx[i-1]+G_nx[i-2]+ii];
			tM[ii]=Mr[i*nx+ii];
		}
		fit[i]=grie_rosen_func(ty,fit[i],G_nx[i],tO,tM,0,0);
		i=3;
		ty = new double[G_nx[i]];
		tO = new double[G_nx[i]];
		tM = new double[G_nx[i]];
		for(int ii=0;ii<G_nx[i];ii++)
		{
			ty[ii]=y[G_nx[i-1]+G_nx[i-2]+G_nx[i-3]+ii];
			tO[ii]=Os[G_nx[i-1]+G_nx[i-2]+G_nx[i-3]+ii];
			tM[ii]=Mr[i*nx+ii];
		}
		fit[i]=rastrigin_func(ty,fit[i],G_nx[i],tO,tM,0,0);
		
		f=0.0;
		for(i=0;i<cf_num;i++)
		{
			f += fit[i];
		}
		return f;

	}
	
	double hf05 (double[] x, double f, int nx, double[] Os,double[] Mr,int[] S,int s_flag,int r_flag) /* Hybrid Function 5 */
	{
		int i,tmp,cf_num=5;
		double[] fit = new double[5];
		int[] G = new int[5];
		int[] G_nx = new int[5];
		double[] Gp={0.1,0.2,0.2,0.2,0.3};

		tmp=0;
		for (i=0; i<cf_num-1; i++)
		{
			G_nx[i] = (int)Math.ceil(Gp[i]*nx);
			tmp += G_nx[i];
		}
		G_nx[cf_num-1]=nx-tmp;

		G[0]=0;
		for (i=1; i<cf_num; i++)
		{
			G[i] = G[i-1]+G_nx[i-1];
		}
				
		  
		sr_func (x, z, nx, Os, Mr, 1.0, s_flag, r_flag); /* shift and rotate */

		

		for (i=0; i<nx; i++)
		{
			y[i]=z[S[i]-1];
		}
		
		
		
		  
		
		
      double[] ty,tO,tM;
		
		i=0;
		ty = new double[G_nx[i]];
		tO = new double[G_nx[i]];
		tM = new double[G_nx[i]];
		for(int ii=0;ii<G_nx[i];ii++)
		{
			ty[ii]=y[ii];
			tO[ii]=Os[ii];
			tM[ii]=Mr[i*nx+ii];
		}
		fit[i]=escaffer6_func(ty,fit[i],G_nx[i],tO,tM,0,0);
		
		i=1;
		ty = new double[G_nx[i]];
		tO = new double[G_nx[i]];
		tM = new double[G_nx[i]];
		for(int ii=0;ii<G_nx[i];ii++)
		{
			ty[ii]=y[G_nx[i-1]+ii];
			tO[ii]=Os[G_nx[i-1]+ii];
			tM[ii]=Mr[i*nx+ii];
		}
		fit[i]=hgbat_func(ty,fit[i],G_nx[i],tO,tM,0,0);
		
		i=2;
		ty = new double[G_nx[i]];
		tO = new double[G_nx[i]];
		tM = new double[G_nx[i]];
		for(int ii=0;ii<G_nx[i];ii++)
		{
			ty[ii]=y[G_nx[i-1]+G_nx[i-2]+ii];
			tO[ii]=Os[G_nx[i-1]+G_nx[i-2]+ii];
			tM[ii]=Mr[i*nx+ii];
		}
		
		fit[i]=rosenbrock_func(ty,fit[i],G_nx[i],tO,tM,0,0);
		i=3;
		ty = new double[G_nx[i]];
		tO = new double[G_nx[i]];
		tM = new double[G_nx[i]];
		for(int ii=0;ii<G_nx[i];ii++)
		{
			ty[ii]=y[G_nx[i-1]+G_nx[i-2]+G_nx[i-3]+ii];
			tO[ii]=Os[G_nx[i-1]+G_nx[i-2]+G_nx[i-3]+ii];
			tM[ii]=Mr[i*nx+ii];
		}
		fit[i]=schwefel_func(ty,fit[i],G_nx[i],tO,tM,0,0);
		i=4;
		ty = new double[G_nx[i]];
		tO = new double[G_nx[i]];
		tM = new double[G_nx[i]];
		for(int ii=0;ii<G_nx[i];ii++)
		{
			ty[ii]=y[G_nx[i-1]+G_nx[i-2]+G_nx[i-3]+G_nx[i-4]+ii];
			tO[ii]=Os[G_nx[i-1]+G_nx[i-2]+G_nx[i-3]+G_nx[i-4]+ii];
			tM[ii]=Mr[i*nx+ii];
		}
		fit[i]=ellips_func(ty,fit[i],G_nx[i],tO,tM,0,0);
		
		//for(i=0;i<cf_num;i++){
		//	System.out.println("fithf05["+i+"]"+"="+fit[i]);
		//}
		
		f=0.0;
		for(i=0;i<cf_num;i++)
		{
			f += fit[i];
		}
		return f;

	}
	
	double hf06 (double[] x, double f, int nx, double[] Os,double[] Mr,int[] S,int s_flag,int r_flag) /* Hybrid Function 6 */
	{
		int i,tmp,cf_num=5;
		double[] fit = new double[5];
		int[] G = new int[5];
		int[] G_nx = new int[5];
		double[] Gp={0.1,0.2,0.2,0.2,0.3};

		tmp=0;
		for (i=0; i<cf_num-1; i++)
		{
			G_nx[i] = (int)Math.ceil(Gp[i]*nx);
			tmp += G_nx[i];
		}
		G_nx[cf_num-1]=nx-tmp;

		G[0]=0;
		for (i=1; i<cf_num; i++)
		{
			G[i] = G[i-1]+G_nx[i-1];
		}

		sr_func (x, z, nx, Os, Mr, 1.0, s_flag, r_flag); /* shift and rotate */

		for (i=0; i<nx; i++)
		{
			y[i]=z[S[i]-1];
		}
		
      double[] ty,tO,tM;
		
		i=0;
		ty = new double[G_nx[i]];
		tO = new double[G_nx[i]];
		tM = new double[G_nx[i]];
		for(int ii=0;ii<G_nx[i];ii++)
		{
			ty[ii]=y[ii];
			tO[ii]=Os[ii];
			tM[ii]=Mr[i*nx+ii];
		}
		fit[i]=katsuura_func(ty,fit[i],G_nx[i],tO,tM,0,0);
		
		i=1;
		ty = new double[G_nx[i]];
		tO = new double[G_nx[i]];
		tM = new double[G_nx[i]];
		for(int ii=0;ii<G_nx[i];ii++)
		{
			ty[ii]=y[G_nx[i-1]+ii];
			tO[ii]=Os[G_nx[i-1]+ii];
			tM[ii]=Mr[i*nx+ii];
		}
		fit[i]=happycat_func(ty,fit[i],G_nx[i],tO,tM,0,0);
		
		i=2;
		ty = new double[G_nx[i]];
		tO = new double[G_nx[i]];
		tM = new double[G_nx[i]];
		for(int ii=0;ii<G_nx[i];ii++)
		{
			ty[ii]=y[G_nx[i-1]+G_nx[i-2]+ii];
			tO[ii]=Os[G_nx[i-1]+G_nx[i-2]+ii];
			tM[ii]=Mr[i*nx+ii];
		}
		fit[i]=grie_rosen_func(ty,fit[i],G_nx[i],tO,tM,0,0);
		i=3;
		ty = new double[G_nx[i]];
		tO = new double[G_nx[i]];
		tM = new double[G_nx[i]];
		for(int ii=0;ii<G_nx[i];ii++)
		{
			ty[ii]=y[G_nx[i-1]+G_nx[i-2]+G_nx[i-3]+ii];
			tO[ii]=Os[G_nx[i-1]+G_nx[i-2]+G_nx[i-3]+ii];
			tM[ii]=Mr[i*nx+ii];
		}
		fit[i]=schwefel_func(ty,fit[i],G_nx[i],tO,tM,0,0);
		i=4;
		ty = new double[G_nx[i]];
		tO = new double[G_nx[i]];
		tM = new double[G_nx[i]];
		for(int ii=0;ii<G_nx[i];ii++)
		{
			ty[ii]=y[G_nx[i-1]+G_nx[i-2]+G_nx[i-3]+G_nx[i-4]+ii];
			tO[ii]=Os[G_nx[i-1]+G_nx[i-2]+G_nx[i-3]+G_nx[i-4]+ii];
			tM[ii]=Mr[i*nx+ii];
		}
		fit[i]=ackley_func(ty,fit[i],G_nx[i],tO,tM,0,0);

		//for(i=0;i<cf_num;i++){
		//	System.out.println("fithf06["+i+"]"+"="+fit[i]);
		//}
		
		f=0.0;
		for(i=0;i<cf_num;i++)
		{
			f += fit[i];
		}
		return f;
		
	}
	
	double cf01 (double[] x, double f, int nx, double[] Os,double[] Mr,int r_flag) /* Composition Function 1 */
	{
		int i,j,cf_num=5;
		double[] fit = new double[5];// fit[5];
		double[] delta = {10, 20, 30, 40, 50};
		double[] bias = {0, 100, 200, 300, 400};
		
		double[] tOs = new double[nx];
		double[] tMr = new double[cf_num*nx*nx];
		
		i=0;
		for(j=0;j<nx;j++){
			tOs[j] = Os[i*nx+j];
		}
		for(j=0;j<nx*nx;j++){
			tMr[j] = Mr[i*nx*nx+j];
		}
		fit[i]=rosenbrock_func(x,fit[i],nx,tOs,tMr,1,r_flag);
		fit[i]=10000*fit[i]/1e+4;
		
		i=1;
		for(j=0;j<nx;j++){
			tOs[j] = Os[i*nx+j];
		}
		for(j=0;j<nx*nx;j++){
			tMr[j] = Mr[i*nx*nx+j];
		}
		fit[i]=ellips_func(x,fit[i],nx,tOs,tMr,1,r_flag);
		fit[i]=10000*fit[i]/1e+10;
		
		i=2;
		for(j=0;j<nx;j++){
			tOs[j] = Os[i*nx+j];
		}
		for(j=0;j<nx*nx;j++){
			tMr[j] = Mr[i*nx*nx+j];
		}
		fit[i]=bent_cigar_func(x,fit[i],nx,tOs,tMr,1,r_flag);
		fit[i]=10000*fit[i]/1e+30;
		
		i=3;
		for(j=0;j<nx;j++){
			tOs[j] = Os[i*nx+j];
		}
		for(j=0;j<nx*nx;j++){
			tMr[j] = Mr[i*nx*nx+j];
		}
		fit[i]=discus_func(x,fit[i],nx,tOs,tMr,1,r_flag);
		fit[i]=10000*fit[i]/1e+10;
		
		i=4;
		for(j=0;j<nx;j++){
			tOs[j] = Os[i*nx+j];
		}
		for(j=0;j<nx*nx;j++){
			tMr[j] = Mr[i*nx*nx+j];
		}
		fit[i]=ellips_func(x,fit[i],nx,tOs,tMr,1,0);
		fit[i]=10000*fit[i]/1e+10;
	
	
		return cf_cal(x, f, nx, Os, delta,bias,fit,cf_num);
		
	}
	
	double cf02 (double[] x, double f, int nx, double[] Os,double[] Mr,int r_flag) /* Composition Function 2 */
	{
		int i,j,cf_num=3;
		double[] fit = new double[3];
		double[] delta = {20,20,20};
		double[] bias = {0, 100, 200};
		
		double[] tOs = new double[nx];
		double[] tMr = new double[cf_num*nx*nx];
		
		
		i=0;
			for(j=0;j<nx;j++){
				tOs[j] = Os[i*nx+j];
			}
			for(j=0;j<nx*nx;j++){
				tMr[j] = Mr[i*nx*nx+j];
			}
			fit[i]=schwefel_func(x,fit[i],nx,tOs,tMr,1,0);
			
		i=1;
		for(j=0;j<nx;j++){
			tOs[j] = Os[i*nx+j];
		}
		for(j=0;j<nx*nx;j++){
			tMr[j] = Mr[i*nx*nx+j];
		}
		fit[i]=rastrigin_func(x,fit[i],nx,tOs,tMr,1,r_flag);
		
		i=2;
		for(j=0;j<nx;j++){
			tOs[j] = Os[i*nx+j];
		}
		for(j=0;j<nx*nx;j++){
			tMr[j] = Mr[i*nx*nx+j];
		}
		fit[i]=hgbat_func(x,fit[i],nx,tOs,tMr,1,r_flag);
		
		
		return cf_cal(x, f, nx, Os, delta,bias,fit,cf_num);
	}
	
	double cf03 (double[] x, double f, int nx, double[] Os,double[]Mr,int r_flag) /* Composition Function 3 */
	{
		int i,j,cf_num=3;
		double[] fit=new double[3];
		double[] delta = {10,30,50};
		double[] bias = {0, 100, 200};
		
		double[] tOs = new double[nx];
		double[] tMr = new double[cf_num*nx*nx];
		
		
		i=0;
			for(j=0;j<nx;j++){
				tOs[j] = Os[i*nx+j];
			}
			for(j=0;j<nx*nx;j++){
				tMr[j] = Mr[i*nx*nx+j];
			}
			fit[i]=schwefel_func(x,fit[i],nx,tOs,tMr,1,r_flag);
			fit[i]=1000*fit[i]/4e+3;
			
		i=1;
		for(j=0;j<nx;j++){
			tOs[j] = Os[i*nx+j];
		}
		for(j=0;j<nx*nx;j++){
			tMr[j] = Mr[i*nx*nx+j];
		}
		fit[i]=rastrigin_func(x,fit[i],nx,tOs,tMr,1,r_flag);
		fit[i]=1000*fit[i]/1e+3;
		
		i=2;
		for(j=0;j<nx;j++){
			tOs[j] = Os[i*nx+j];
		}
		for(j=0;j<nx*nx;j++){
			tMr[j] = Mr[i*nx*nx+j];
		}
		fit[i]=ellips_func(x,fit[i],nx,tOs,tMr,1,r_flag);
		fit[i]=1000*fit[i]/1e+10;
		
		
		
		return cf_cal(x, f, nx, Os, delta,bias,fit,cf_num);
	}
	
	double cf04 (double[] x, double f, int nx, double[] Os,double[] Mr,int r_flag) /* Composition Function 4 */
	{
		int i,j,cf_num=5;
		double[] fit=new double[5];
		double[] delta = {10,10,10,10,10};
		double[] bias = {0, 100, 200, 300, 400};
		
		double[] tOs = new double[nx];
		double[] tMr = new double[cf_num*nx*nx];
			
		i=0;
		for(j=0;j<nx;j++){
			tOs[j] = Os[i*nx+j];
		}
		for(j=0;j<cf_num*nx*nx;j++){
			tMr[j] = Mr[i*nx*nx+j];
			
		}
		fit[i]=schwefel_func(x,fit[i],nx,tOs,tMr,1,r_flag);
		fit[i]=1000*fit[i]/(4e+3);
		
		i=1;
		for(j=0;j<nx;j++){
			tOs[j] = Os[i*nx+j];
		}
		for(j=0;j<cf_num*nx*nx;j++){
			tMr[j] = Mr[i*nx*nx+j];
		
		}
		fit[i]=happycat_func(x,fit[i],nx,tOs,tMr,1,r_flag);
		fit[i]=1000*fit[i]/(1e+3);
		
		i=2;
		for(j=0;j<nx;j++){
			tOs[j] = Os[i*nx+j];
		}
		for(j=0;j<cf_num*nx*nx;j++){
			tMr[j] = Mr[i*nx*nx+j];
		}
		fit[i]=ellips_func(x,fit[i],nx,tOs,tMr,1,r_flag);
		fit[i]=1000*fit[i]/1e+10;
		
		i=3;
		for(j=0;j<nx;j++){
			tOs[j] = Os[i*nx+j];
		}
		for(j=0;j<cf_num*nx*nx;j++){
			tMr[j] = Mr[i*nx*nx+j];
		}
		fit[i]=weierstrass_func(x,fit[i],nx,tOs,tMr,1,r_flag);
		fit[i]=1000*fit[i]/400;
		
		i=4;
		for(j=0;j<nx;j++){
			tOs[j] = Os[i*nx+j];
		}
		for(j=0;j<cf_num*nx*nx;j++){
			tMr[j] = Mr[i*nx*nx+j];
		}
		fit[i]=griewank_func(x,fit[i],nx,tOs,tMr,1,r_flag);
		fit[i]=1000*fit[i]/100;
		
		return cf_cal(x, f, nx, Os, delta,bias,fit,cf_num);
	}
	
	double cf05 (double[] x, double f, int nx, double[] Os,double[] Mr,int r_flag) /* Composition Function 5 */
	{
		int i,j,cf_num=5;
		double[] fit=new double[5];
		double[] delta = {10,10,10,20,20};
		double[] bias = {0, 100,200,300,400};
		
		double[] tOs = new double[nx];
		double[] tMr = new double[cf_num*nx*nx];
		
		i=0;
		for(j=0;j<nx;j++){
			tOs[j] = Os[i*nx+j];
		}
		for(j=0;j<cf_num*nx*nx;j++){
			tMr[j] = Mr[i*nx*nx+j];
		}
		fit[i]=hgbat_func(x,fit[i],nx,tOs,tMr,1,r_flag);
		fit[i]=10000*fit[i]/1000;
		i=1;
		for(j=0;j<nx;j++){
			tOs[j] = Os[i*nx+j];
		}
		for(j=0;j<cf_num*nx*nx;j++){
			tMr[j] = Mr[i*nx*nx+j];
		}
		fit[i]=rastrigin_func(x,fit[i],nx,tOs,tMr,1,r_flag);
		fit[i]=10000*fit[i]/1e+3;
		i=2;
		for(j=0;j<nx;j++){
			tOs[j] = Os[i*nx+j];
		}
		for(j=0;j<cf_num*nx*nx;j++){
			tMr[j] = Mr[i*nx*nx+j];
		}
		fit[i]=schwefel_func(x,fit[i],nx,tOs,tMr,1,r_flag);
		fit[i]=10000*fit[i]/4e+3;
		i=3;
		for(j=0;j<nx;j++){
			tOs[j] = Os[i*nx+j];
		}
		for(j=0;j<cf_num*nx*nx;j++){
			tMr[j] = Mr[i*nx*nx+j];
		}
		fit[i]=weierstrass_func(x,fit[i],nx,tOs,tMr,1,r_flag);
		fit[i]=10000*fit[i]/400;
		i=4;
		for(j=0;j<nx;j++){
			tOs[j] = Os[i*nx+j];
		}
		for(j=0;j<cf_num*nx*nx;j++){
			tMr[j] = Mr[i*nx*nx+j];
		}
		fit[i]=ellips_func(x,fit[i],nx,tOs,tMr,1,r_flag);
		fit[i]=10000*fit[i]/1e+10;
		
		return cf_cal(x, f, nx, Os, delta,bias,fit,cf_num);
	}
	
	double cf06 (double[] x, double f, int nx, double[] Os,double[] Mr,int r_flag) /* Composition Function 6 */
	{
		int i,j,cf_num=5;
		double[] fit=new double[5];
		double[] delta = {10,20,30,40,50};
		double[] bias = {0, 100,200,300,400};
		
		double[] tOs = new double[nx];
		double[] tMr = new double[cf_num*nx*nx];
		
		i=0;
		for(j=0;j<nx;j++){
			tOs[j] = Os[i*nx+j];
		}
		for(j=0;j<cf_num*nx*nx;j++){
			tMr[j] = Mr[i*nx*nx+j];
		}
		fit[i]=grie_rosen_func(x,fit[i],nx,tOs,tMr,1,r_flag);
		fit[i]=10000*fit[i]/4e+3;
		i=1;
		for(j=0;j<nx;j++){
			tOs[j] = Os[i*nx+j];
		}
		for(j=0;j<cf_num*nx*nx;j++){
			tMr[j] = Mr[i*nx*nx+j];
		}
		fit[i]=happycat_func(x,fit[i],nx,tOs,tMr,1,r_flag);
		fit[i]=10000*fit[i]/1e+3;
		i=2;
		for(j=0;j<nx;j++){
			tOs[j] = Os[i*nx+j];
		}
		for(j=0;j<cf_num*nx*nx;j++){
			tMr[j] = Mr[i*nx*nx+j];
		}
		fit[i]=schwefel_func(x,fit[i],nx,tOs,tMr,1,r_flag);
		fit[i]=10000*fit[i]/4e+3;
		i=3;
		for(j=0;j<nx;j++){
			tOs[j] = Os[i*nx+j];
		}
		for(j=0;j<cf_num*nx*nx;j++){
			tMr[j] = Mr[i*nx*nx+j];
		}
		fit[i]=escaffer6_func(x,fit[i],nx,tOs,tMr,1,r_flag);
		fit[i]=10000*fit[i]/2e+7;
		i=4;
		for(j=0;j<nx;j++){
			tOs[j] = Os[i*nx+j];
		}
		for(j=0;j<cf_num*nx*nx;j++){
			tMr[j] = Mr[i*nx*nx+j];
		}
		fit[i]=ellips_func(x,fit[i],nx,tOs,tMr,1,r_flag);
		fit[i]=10000*fit[i]/1e+10;
		
		return cf_cal(x, f, nx, Os, delta,bias,fit,cf_num);
	}
	
	double cf07 (double[] x, double f, int nx, double[] Os,double[] Mr,int[] SS,int r_flag) /* Composition Function 7 */
	{
		int i,j,cf_num=3;
		double[] fit=new double[3];
		double[] delta = {10,30,50};
		double[] bias = {0, 100, 200};
		
		double[] tOs = new double[nx];
		double[] tMr = new double[cf_num*nx*nx];
		int[] tSS = new int[nx];
		
		
		i=0;
			for(j=0;j<nx;j++){
				tOs[j] = Os[i*nx+j];
			}
			for(j=0;j<nx*nx;j++){
				tMr[j] = Mr[i*nx*nx+j];
			}
			for(j=0;j<nx;j++){
				tSS[j] = SS[i*nx+j];
			}
			fit[i]=hf01(x,fit[i],nx,tOs,tMr,tSS,1,r_flag);
			
			
		i=1;
		for(j=0;j<nx;j++){
			tOs[j] = Os[i*nx+j];
		}
		for(j=0;j<nx*nx;j++){
			tMr[j] = Mr[i*nx*nx+j];
		}
		for(j=0;j<nx;j++){
			tSS[j] = SS[i*nx+j];
		}
		fit[i]=hf02(x,fit[i],nx,tOs,tMr,tSS,1,r_flag);
		
		i=2;
		for(j=0;j<nx;j++){
			tOs[j] = Os[i*nx+j];
		}
		for(j=0;j<nx*nx;j++){
			tMr[j] = Mr[i*nx*nx+j];
		}
		for(j=0;j<nx;j++){
			tSS[j] = SS[i*nx+j];
		}
		fit[i]=hf03(x,fit[i],nx,tOs,tMr,tSS,1,r_flag);
		
		
		
		
		return cf_cal(x, f, nx, Os, delta,bias,fit,cf_num);
	}
	
	double cf08 (double[] x, double f, int nx, double[] Os,double[]Mr,int[] SS,int r_flag) /* Composition Function 8 */
	{
		int i,j,cf_num=3;
		double[] fit=new double[3];
		double[] delta = {10,30,50};
		double[] bias = {0, 100, 200};
		
		double[] tOs = new double[nx];
		double[] tMr = new double[cf_num*nx*nx];
		int[] tSS = new int[nx];
		
		
		i=0;
			for(j=0;j<nx;j++){
				tOs[j] = Os[i*nx+j];
			}
			for(j=0;j<nx*nx;j++){
				tMr[j] = Mr[i*nx*nx+j];
			}
			for(j=0;j<nx;j++){
				tSS[j] = SS[i*nx+j];
			}
			fit[i]=hf04(x,fit[i],nx,tOs,tMr,tSS,1,r_flag);
			
			
		i=1;
		for(j=0;j<nx;j++){
			tOs[j] = Os[i*nx+j];
		}
		for(j=0;j<nx*nx;j++){
			tMr[j] = Mr[i*nx*nx+j];
		}
		for(j=0;j<nx;j++){
			tSS[j] = SS[i*nx+j];
		}
		fit[i]=hf05(x,fit[i],nx,tOs,tMr,tSS,1,r_flag);
		
		i=2;
		for(j=0;j<nx;j++){
			tOs[j] = Os[i*nx+j];
		}
		for(j=0;j<nx*nx;j++){
			tMr[j] = Mr[i*nx*nx+j];
		}
		for(j=0;j<nx;j++){
			tSS[j] = SS[i*nx+j];
		}
		fit[i]=hf06(x,fit[i],nx,tOs,tMr,tSS,1,r_flag);
		
		
		
		
		return cf_cal(x, f, nx, Os, delta,bias,fit,cf_num);
	}

	void shiftfunc (double[] position, double[] xshift, int nx,double[] Os)
	{
		int i;
	    for (i=0; i<nx; i++)
	    {
	        xshift[i]=position[i]-Os[i];
	    }
	}
	
	
	void rotatefunc (double[] position, double[] xrot, int nx,double[] Mr)
	{
		int i,j;
	    for (i=0; i<nx; i++)
	    {
	        xrot[i]=0;
				for (j=0; j<nx; j++)
				{
					xrot[i]=xrot[i]+position[j]*Mr[i*nx+j];
				}
	    }
	}
	//sh_rate = 1
	//sr_func(position, z, dimensions, Os, Mr, 1.0, s_flag, r_flag);
	void sr_func(double[] position, double[] sr_x, int dimensions, double[] Os, double[] Mr, double sh_rate, int s_flag, int r_flag) {
		int i, j;
		if (s_flag == 1) {
			if (r_flag == 1) {
				shiftfunc(position, y, dimensions, Os);
				for (i = 0; i < dimensions; i++)// shrink to the orginal search
												// range
				{
					y[i] = y[i] * sh_rate;
				}
				rotatefunc(y, sr_x, dimensions, Mr);
			} else {
				shiftfunc(position, sr_x, dimensions, Os);
				for (i = 0; i < dimensions; i++)// shrink to the orginal search
												// range
				{
					sr_x[i] = sr_x[i] * sh_rate;
				}
			}
		} else {

			if (r_flag == 1) {
				for (i = 0; i < dimensions; i++)// shrink to the orginal search
												// range
				{
					y[i] = position[i] * sh_rate;
				}
				rotatefunc(y, sr_x, dimensions, Mr);
			} else

			{
				for (j = 0; j < dimensions; j++)// shrink to the orginal search
												// range
				{
					sr_x[j] = position[j] * sh_rate;
				}
			}
		}
	}
	
	void asyfunc (double[] positions, double[] xasy, int dimensions, double beta)
	{
		int i;
	    for (i=0; i<dimensions; i++)
	    {
			if (positions[i]>0)
	        xasy[i]=Math.pow(positions[i],1.0+beta*i/(dimensions-1)*Math.pow(positions[i],0.5));
	    }
	}
		
	void oszfunc (double[] position, double[] xosz, int dimensions)
	{
		int i,sx;
		double c1,c2,xx=0;
	    for (i=0; i<dimensions; i++)
	    {
			if (i==0||i==dimensions-1)
	        {
				if (position[i]!=0)
					xx=Math.log(Math.abs(position[i]));//xx=log(fabs(x[i]));
				if (position[i]>0)
				{	
					c1=10;
					c2=7.9;
				}
				else
				{
					c1=5.5;
					c2=3.1;
				}	
				if (position[i]>0)
					sx=1;
				else if (position[i]==0)
					sx=0;
				else
					sx=-1;
				xosz[i]=sx*Math.exp(xx+0.049*(Math.sin(c1*xx)+Math.sin(c2*xx)));
			}
			else
				xosz[i]=position[i];
	    }
	}
		
	double cf_cal(double[] position, double fitness, int dimensions, double[] Os,double[] delta,double[] bias,double[] fit, int cf_num)
	{
		int i,j;
			
		double[] w;
		double w_max=0,w_sum=0;
		w=new double[cf_num];
		for (i=0; i<cf_num; i++)
		{
			fit[i]+=bias[i];
			w[i]=0;
			for (j=0; j<dimensions; j++)
			{
				w[i]+=Math.pow(position[j]-Os[i*dimensions+j],2.0);
			}
			if (w[i]!=0)
				w[i]=Math.pow(1.0/w[i],0.5)*Math.exp(-w[i]/2.0/dimensions/Math.pow(delta[i],2.0));
			else
				w[i]=INF;
			if (w[i]>w_max)
				w_max=w[i];
		}

		for (i=0; i<cf_num; i++)
		{
			w_sum=w_sum+w[i];
		}
		if(w_max==0)
		{
			for (i=0; i<cf_num; i++)
				w[i]=1;
			w_sum=cf_num;
		}
		fitness = 0.0;
	    for (i=0; i<cf_num; i++)
	    {
			fitness=fitness+w[i]/w_sum*fit[i];
	    }
	    
	    return fitness;
		
	}
}


