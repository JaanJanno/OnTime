package controllers.game.simplex;

public class SimplexStreamer {
	
	static private SimplexNoise_octave noise;
	static private double PI;
	
	static{
		noise = new SimplexNoise_octave(433446896);
		PI = Math.PI;
	}
	
	public static int plotOctave2D(int x, int y, double frequency, int octave, int amplitude){
		
		// Triviaalne teisendus 2d simplex mürast 2d tasandil asuvale punktile.
		
		return (int)(amplitude * (((noise.noise((x / octaveDiv(octave, frequency)), 
												(y / octaveDiv(octave, frequency)))) + 1) / 2
		));
	}
	
	public static int plotOctave4D(double xSlices, double ySlices, double x, double y, double frequency, int octave, int amplitude){
		
		// Teisendus 4-dimensioonilisest simplex mürast 2d tasandi punktile.
		
		double octaveDivisor = octaveDiv(octave, frequency) / xSlices * 2;
		
		return (int)(amplitude * (((noise.noise(
				(Math.sin((x / xSlices) * PI) / octaveDivisor),	// x - y koordinaadi tekitatud liikumine ei lõiku seetõttu iseendaga.
				(Math.cos((x / xSlices) * PI) / octaveDivisor), // y-kooridnaat - sõõri ringjoone asukoha muutumine. 
				(Math.sin((y / ySlices) * PI) / octaveDivisor),	// z  	// z, w moodustavad sõõri
				(Math.cos((y / ySlices) * PI) / octaveDivisor)	// w  	// ümbritseva ringjoone.			
			)) + 1) / 2
		));
	}
	
	public static double octaveDiv(int octave, double frequency){
		return frequency * Math.pow(2, octave);
	}
}
