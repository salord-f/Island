package fr.unice.polytech.si3.qgl.isg;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigInteger;
import java.nio.file.Path;
import java.nio.file.Paths;

import static eu.ace_design.island.runner.Runner.run;

public class Runner {

	// To run the code:  mvn -q exec:java -Dexec.args="../championships/week03/_map.json"
	public static void main(String[] args) throws Exception {


		if (args.length < 1) {
			throw new IllegalArgumentException("No map available, aborting");
		}

		Path mapLocation = Paths.get(args[0]);
		if (!mapLocation.toFile().exists()) {
			throw new IllegalArgumentException("Your map does not exist, aborting");
		}

		if (args.length >= 8) {
			System.setOut(new PrintStream(new ByteArrayOutputStream()));
			eu.ace_design.island.runner.Runner runner = run(Explorer.class);
			int i = 1;
			runner.exploring(mapLocation.toFile())
					.withName("isg")
					.startingAt(Integer.parseInt(args[i++]), Integer.parseInt(args[i++]), args[i++])
					.withSeed(new BigInteger(args[i++], 16).longValue())
					.backBefore(Integer.parseInt(args[i++]))
					.withCrew(Integer.parseInt(args[i++]));
			while (i < args.length) {
				runner.collecting(Integer.parseInt(args[i++]), args[i++]);
			}
			runner.storingInto(".").fire();

		} else run(Explorer.class)
				.exploring(mapLocation.toFile())
				.withName("isg")
				.withSeed(0xF8C2F759DFAC20BAL)
				.startingAt(1, 1, "EAST")
				.backBefore(10000)
				.withCrew(4)
				.collecting(7000, "WOOD")
				.collecting(15, "GLASS")
				.collecting(500, "FUR")
				.collecting(5, "FLOWER")
				.storingInto(".")
				.fire();
	}
}