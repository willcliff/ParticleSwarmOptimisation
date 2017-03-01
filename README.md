# Particle Swarm Optimisation Simulator

A new variant of the Particle Swarm Optimisation algorithm called PSO AGIDN (Particle Swarm Optimisation with Adaptive Gradually Increasing Directed Neighbourhoods)
developed as part of my thesis. The PSO AGIDN significantly improves upon the performance of the standard Algorithm (SPSO) along with 
similar implementations of the algorithm. This simulator includes the my version and other versions of the algorithm against whioch it was tested. Also included are the suite of benchmark 
functions against which they are tested.


## Basic Usage
Select the version of the PSO algorithm to run along with the benchmark test suite.
The simulator will output summary results of the performance along with resuls which can be used to generate convergance graphs.

```Java
			for (int r = 0; r < numberOfRuns; r++) {
				
				//Select the suite of test functions			
				BasicTestFunc problem = new BasicTestFunc(j);				
				//TestFunc14 problem = new TestFunc14(j);
				
				functionNames[j-1] = problem.functionName;
				
				// Select the PSO type to run
				AGIDNPSO pso = new AGIDNPSO(problem, iterations);			
				//LocalPSO pso = new LocalPSO(problem, iterations);
				//GlobalPSO pso = new GlobalPSO(problem, iterations);
				//VonNeumann pso = new VonNeumann(problem, iterations);
				//GIDNPSO pso = new GIDNPSO(problem, iterations);
            
        ...
                        
```
