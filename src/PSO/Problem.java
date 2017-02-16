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
	public Problem(){
		System.out.println("Problem Constructor1");		
	}
	
	public abstract double getFitness(double[] position);
	
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


