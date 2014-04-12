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
				(((-Math.cos((x / xSlices) * PI) + 1)) 	/ octaveDivisor), 	// x-kooridnaat - sõõri ringjoone asukoha muutumine. 
				(Math.sin((y / ySlices) * PI)			/ octaveDivisor),	// y  	// y, z moodustavad sõõri
				(Math.cos((y / ySlices) * PI)			/ octaveDivisor),	// z  	// ümbritseva ringjoone.
				(Math.sin((x / xSlices) * PI) 			/ octaveDivisor)	// w - x koordinaadi tekitatud liikumine ei lõiku seetõttu iseendaga.
			)) + 1) / 2
		));
	}
	
	public static double octaveDiv(int octave, double frequency){
		return frequency * Math.pow(2, octave);
	}
}
