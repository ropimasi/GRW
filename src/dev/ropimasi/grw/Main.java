package dev.ropimasi.grw;

public class Main {
	public static void main(String[] args) {

		ConfigManager.createDirFileConfigIfNotExists();
		
		CLIHandler.processArgs(args);
		
	}
}
