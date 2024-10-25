package dev.habibi.grw;

import java.io.File;
import java.io.FilenameFilter;




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
		System.out.println("Syntaxe: grw [option] [argument] [[option] [argument]]");
		System.out.println("Usage: grw [-h | -s | -t <time-seconds> | -d <path-directory> | -a | -o | -f]"); //DEVELOPING: ADD --ADD-DIR AND --REM-DIR.
		System.out.println("Options:");
		System.out.println("\t-h \t\t\t\t Shows this help text.");
		System.out.println("\t-v \t\t\t\t Shows version of grw.");
		System.out.println("\t-s \t\t\t\t Shows current status of configuration.");
		System.out.println("\t-t <time> \t\t\t Sets the waiting time between wallpaper changes.");
		System.out.println("\t-d <directory> \t\t\t Sets the images directory.");
		System.out.println("\t-a \t\t\t\t Starts automatic changing of wallpaper.");
		System.out.println("\t-o \t\t\t\t Stops automatic changing of wallpaper.");
		System.out.println("\t-f \t\t\t\t Sets waiting time and images directory configurationcom to default value.");
		//System.out.println("\t--add-dir <directory> <alias> \t Register an images directory with an alias.");
		//System.out.println("\t--rem-dir <alias> \t\t Unregister an images directory with an alias.");		
		System.out.println("\n");
	}



	public static void showStatus() {
		ConfigManager.readConfigurationFormFile();

		System.out.println("\n");
		System.out.println("Current status:");
		System.out.println("\tWaiting time: " + ConfigManager.waitingTime + " seconds,");
		System.out.println("\tImages directory: " + ConfigManager.imagesDirectory + ",");
		System.out.println("\tRunning: " + ConfigManager.running + ".");
		System.out.println("\n");
	}



	public static void listImageDirFiles() {
		File diretorio = new File(Parameters.IMAGES_DIR_DEFAULT);

		// Verificar se o diretório existe e é válido
		if (diretorio.exists() && diretorio.isDirectory()) {
			// Filtrar arquivos com as extensões desejadas
			FilenameFilter filtroImagem = new FilenameFilter() {
				@Override
				public boolean accept(File dir, String nome) {
					return nome.toLowerCase().endsWith(".jpg") ||
							nome.toLowerCase().endsWith(".jpeg") ||
							nome.toLowerCase().endsWith(".gif") ||
							nome.toLowerCase().endsWith(".PNG") ||
							nome.toLowerCase().endsWith(".webp") ||
							nome.toLowerCase().endsWith(".avif");
				}
			};

			// Listar os arquivos com as extensões desejadas
			File[] arquivosImagem = diretorio.listFiles(filtroImagem);
			if (arquivosImagem != null && arquivosImagem.length > 0) {
				System.out.println("\nImages file on directory: " + Parameters.IMAGES_DIR_DEFAULT + "\n");
				for (File arquivo : arquivosImagem) {
					System.out.println(arquivo.getName());
				}
				System.out.println("\nTotal file listed: "+ arquivosImagem.length +".");
				
			} else { //DEVELOPING: ajustar layout das mensagens.
				System.out.println("No images file were found on directory.");
			}
		} else {
			System.out.println("Invalid directory, or do not exist.");
		}

	}
}
