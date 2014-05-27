package controllers.game.simplex.opencl;

import controllers.game.simplex.SimplexNoise_octave;

public class SimplexGpuNoise {
	
	static private double PI;
	static private SimplexKernel kernel = new SimplexKernel(763171221, 450, 450);
	
	static{
		PI = Math.PI;
	}
	
	public static float[] calculateOctaveGrid(float xSlices, float ySlices, float x, float y, float w, float h, double frequency, int octave){
		
		// Teisendus 4-dimensioonilisest simplex mürast 2d tasandi punktile.
		
		float octaveDivisor = octaveDiv(octave, frequency) / xSlices;
		
		for(int i=0;i<w;i++){
			for(int j=0;j<h;j++){
				kernel.setPointNoiseCoords(i, j, 
						(float)(Math.sin(((x+i) / xSlices) * 2 * PI) / octaveDivisor), 
						(float)(Math.cos(((x+i) / xSlices) * 2 * PI) / octaveDivisor), 
						(float)(Math.sin(((y+j) / ySlices) * 2 * PI) / octaveDivisor), 
						(float)(Math.cos(((y+j) / ySlices) * 2 * PI) / octaveDivisor)
				);
			}
		}
		
		return kernel.calculate();
	}

	public static float octaveDiv(int octave, double frequency){
		return (float) (frequency / Math.pow(2, octave));
	}
}
