package dev.ropimasi.grw;

public class DialogUI {
	
	/* FURTHER: UMA CLASSE PARA AS CONSTANTES DE MENSAGENS COM U.I. */
	static final String INF_ = "0";
	static final String WRN_ = "0";
	static final String ERR_ = "0";
	

	static void showVersion() {
		System.out.println("\n");
		System.out.println(
				Parameters.PROJECT_NAME + " - " + Parameters.PROJECT_ALIAS + " version " + Parameters.PROJECT_VERSION);
		System.out.println("\n");
	}


	static void showHelp() {
		System.out.println("\n");
		System.out.println(Parameters.PROJECT_NAME + " - " + Parameters.PROJECT_ALIAS);
		System.out.println("Syntaxe: grw [option] [argument]");
		System.out.println("Usage: grw [-h | -s | -t <time-seconds> | -d <path-directory> | -a | -o | -f]");
		System.out.println("Options:");
		System.out.println("\t-h \t\t\t Shows this help text.");
		System.out.println("\t-v \t\t\t Shows version of grw.");
		System.out.println("\t-s \t\t\t Shows current status of configuration.");
		System.out.println("\t-t <time> \t\t Sets the waiting time between wallpaper changes.");
		System.out.println("\t-d <directory> \t\t Sets the images directory.");
		System.out.println("\t-a \t\t\t Starts automatic changing of wallpaper.");
		System.out.println("\t-o \t\t\t Stops automatic changing of wallpaper.");
		System.out.println("\t-f \t\t\t Sets default values for waiting time and images directory configuration.");
		System.out.println("\n");
	}

	public static void showStatus() {
		ConfigManager.readConfigurationFormFile();

		System.out.println("\n");
		System.out.println("Current status:");
		System.out.println("\tWaiting time: " + ConfigManager.waitingTime + " seconds,");
		System.out.println("\tImagens directory: " + ConfigManager.imagesDirectory + ",");
		System.out.println("\tRunning: " + ConfigManager.running + ".");
		System.out.println("\n");
	}
}
