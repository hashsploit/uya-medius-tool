package net.hashsploit.ps2.uya.medius;

import picocli.CommandLine;
import picocli.CommandLine.RunAll;

public class Main {

	public static void main(String[] args) {
		CommandLine cmd = new CommandLine(new Options.ParentCommand());
        cmd.setExecutionStrategy(new RunAll());
        cmd.execute(args);

        if (args.length == 0) { cmd.usage(System.out); }
	}

}
