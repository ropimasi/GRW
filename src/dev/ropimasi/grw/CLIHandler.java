package dev.ropimasi.grw;

public class CLIHandler {

	public static void processArgs(String[] args) {
		if (args.length == 0 || args[0].equals("-h")) {
			DialogUI.showHelp();
			return;
		}

		ConfigManager.createDirFileConfigGrwIfNotExists();

		for (int i = 0; i < args.length; i++) {
			switch (args[i]) {
			case "-t":
				if (i + 1 < args.length) {
					ConfigManager.setWaitingTime(args[++i]);
				} else {
					System.out.println("Error: The -t option requires a waiting time value.");
					return;
				}
				break;

			case "-d":
				if (i + 1 < args.length) {
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

			default:
				System.out.println("Error: Unknown option: " + args[i]);
				DialogUI.showHelp();
				break;
			}
		}
	}

}
