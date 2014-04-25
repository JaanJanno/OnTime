package controllers.game.simplex;

import controllers.game.TerrainController;

public class SimplexStreamer {
	
	static private SimplexNoise_octave noise;
	static private double PI;
	static private int defXSlices, defYSlices, defDiv, defL1, defL2, defL3;
	
	static{
		noise = new SimplexNoise_octave(763171221);
		PI = Math.PI;
		defXSlices	= TerrainController.getWorldWidth();
		defYSlices 	= TerrainController.getWorldHeight();
		defDiv		= 200;
		defL1		= 110;
		defL2		= 95;
		defL3		= 50;
	}
	
	public static int plotOctave2D(int x, int y, double frequency, int octave, int amplitude){
		
		// Triviaalne teisendus 2d simplex mürast 2d tasandil asuvale punktile.
		
		return (int)(amplitude * (((noise.noise((x / octaveDiv(octave, frequency)), 
												(y / octaveDiv(octave, frequency)))) + 1) / 2
		));
	}
	
	public static int plotOctave4D(double xSlices, double ySlices, double x, double y, double frequency, int octave, int amplitude){
		
		// Teisendus 4-dimensioonilisest simplex mürast 2d tasandi punktile.
		
		double octaveDivisor = octaveDiv(octave, frequency) / xSlices;
		
		return (int)(amplitude * (((noise.noise(
				(Math.sin((x / xSlices) * 2 * PI) / octaveDivisor),	// x - y koordinaadi tekitatud liikumine ei lõiku seetõttu iseendaga.
				(Math.cos((x / xSlices) * 2 * PI) / octaveDivisor), // y-kooridnaat - sõõri ringjoone asukoha muutumine. 
				(Math.sin((y / ySlices) * 2 * PI) / octaveDivisor),	// z  	// z, w moodustavad sõõri
				(Math.cos((y / ySlices) * 2 * PI) / octaveDivisor)	// w  	// ümbritseva ringjoone.			
			)) + 1) / 2
		));
	}
	
	public static double octaveDiv(int octave, double frequency){
		return frequency / Math.pow(2, octave);
	}
	
	public static int getPointColor(double x, double y, int w, int h) {
		int sim  = SimplexStreamer.plotOctave4D(w, h, x, y, defDiv, 0, defL1);
		sim 	+= SimplexStreamer.plotOctave4D(w, h, x, y, defDiv, 1, defL2);
		sim 	+= SimplexStreamer.plotOctave4D(w, h, x, y, defDiv, 2, defL3);
		return SimplexLeveler.levelTransformColor(sim);
	}
	
	public static int getPointTerrain(double x, double y) {
		int sim  = SimplexStreamer.plotOctave4D(defXSlices, defYSlices, x, y, defDiv, 0, defL1);
		sim 	+= SimplexStreamer.plotOctave4D(defXSlices, defYSlices, x, y, defDiv, 1, defL2);
		sim 	+= SimplexStreamer.plotOctave4D(defXSlices, defYSlices, x, y, defDiv, 2, defL3);
		return SimplexLeveler.levelTransformTerrain(sim);
	}
}
