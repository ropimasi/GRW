package dev.habibi.grw;

public class CLIHandler {
	public static void processArgs(String[] args) {
		
		ConfigManager.createDirFileConfigGrwIfNotExists();
		
		if (args.length == 0 || args[0].equals("-h")) {
			DialogUI.showHelp();
			return;
		}
		
		for (int i = 0; i < args.length; i++) {
			
			switch (args[i]) {
			
			case "-t":
				if (i + 1 < args.length) { // There is one more arg, at least = ok.
					ConfigManager.setWaitingTime(args[++i]);
				} else {
					System.out.println("Error: The -t option requires a waiting time value (integer).");
					return;
				}
				break;
			
			case "-d":
				if (i + 1 < args.length) { // There is one more arg, at least = ok.
					ConfigManager.setImageDiretory(args[++i]);
				} else {
					System.out.println("Error: The -d option requires a path to the images directory.");
					return;
				}
				break;
			
			case "-s":
				DialogUI.showStatus();
				break;
			case "-v":
				DialogUI.showVersion();
				break;
			
			case "-a":
				WallpaperManager.startLoop();
				break;
			
			case "-o":
				WallpaperManager.stopLoop();
				break;
			
			case "-f":
				ConfigManager.setConfigFromDefaultValues();
				break;
				
			case "-l":
				DialogUI.listImageDirFiles();
				break;
			
			case "--add-dir":
				System.out.println("\n...");
				if (i + 2 < args.length) { // use two else args.
					ConfigManager.addImageDiretoryAlias(args[i + 1], args[i + 2]);
					/* Eliminate theses two args in the loop-for. They are not valids in the loop.
					 * These two args must be consumed only here.*/
					i += 2;
				} else {
					System.out.println(
							"Error: The --add-dir option requires two arguments: a path directory and an alias.");
					return;
				}
				break;

			case "--rem-dir":
				System.out.println("\n...");
				if (i + 1 < args.length) { // use two else args.
					ConfigManager.remImageDirectoryAlias(args[i + 1]);
					/* Eliminate this one arg in the loop-for. It is not valid in the loop.
					 * This one arg must be consumed only here.*/
					i += 1;
				} else {
					System.out.println(
							"Error: The --rem-dir option requires one argument: an alias.");
					return;
				}
				break;
			
			default:
				System.out.println("Error: Unknown option: " + args[i]);
				DialogUI.showHelp();
				break;
			}
		}
	}
}
