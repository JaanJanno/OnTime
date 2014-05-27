package controllers.game.simplex.opencl;

import controllers.game.simplex.SimplexNoise_octave;

public class GpuNoiseTest {
	
public static void main(String[] args) {
		
		SimplexKernel g = new SimplexKernel(763171221, 2560, 1440);
		SimplexNoise_octave n = new SimplexNoise_octave(763171221);
		g.calculate();

		for(int i=0;i<2560;i++){
			for(int j=0;j<1440;j++){
				g.setPointNoiseCoords(i, j, i, j, 0, 0);
			}
		}

		
		
		long time = System.nanoTime();
		g.calculate();
		System.out.println(System.nanoTime() - time);
		
		time = System.nanoTime();
		for(int i=0; i<2560*1440;i++){
			n.noise(i, i, 0, 0);
		}
		System.out.println(System.nanoTime() - time);
	}

}
