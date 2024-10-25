package dev.habibi.grw;

import java.io.File;
import java.util.Random;



public class WallpaperManager {

	static void startLoop() {
		ConfigManager.running = true;
		ConfigManager.writeConfigToFile("running", String.valueOf(ConfigManager.running));
		ConfigManager.readConfigurationFormFile();

		System.out.println("\nInfo: Wallpaper change every [" + ConfigManager.waitingTime + "] seconds from source ["
				+ ConfigManager.imagesDirectory + "] was started.\n");

		while (ConfigManager.running) {
			changeWallpaper();
			try {
				Thread.sleep(ConfigManager.waitingTime * 1000L);
			} catch (InterruptedException e) {
				System.out.println("Error: Exception on timer thread. [[ Message: " + e.getMessage() + " ]]");
			}
			ConfigManager.readConfigurationFormFile();
		}
		
		System.out.println("\nInfo: Wallpaper change has been stoped!\n");
	}


	static void stopLoop() {
		ConfigManager.running = false;
		ConfigManager.writeConfigToFile("running", "false");
		System.out.println("\nInfo: Stopping wallpaper change...\n");
	}


	static void changeWallpaper() {
		File dir = new File(ConfigManager.imagesDirectory);
		File[] imagens = dir.listFiles((d, name) -> name.endsWith(".jpg") || name.endsWith(".jpeg")
				|| name.endsWith(".gif") || name.endsWith(".png") || name.endsWith(".webp") || name.endsWith(".avif"));

		if (imagens == null || imagens.length == 0) {
			System.out.println("\nWarning: No images found in directory [" + ConfigManager.imagesDirectory + "].\n");
			return;
		}

		Random random = new Random();
		File oneRandomImage = imagens[random.nextInt(imagens.length)];
		String imagePath = oneRandomImage.getAbsolutePath();

		try {
			String darkGnomeCommand = "gsettings set org.gnome.desktop.background picture-uri-dark 'file://" + imagePath + "'";
			String lightGnomeCommand = "gsettings set org.gnome.desktop.background picture-uri 'file://" + imagePath + "'";
			Runtime.getRuntime().exec(darkGnomeCommand);
			Runtime.getRuntime().exec(lightGnomeCommand);
		} catch (Exception e) {
			System.out.println("\nError: Exception changing wallpaper. [[ Message: " + e.getMessage() + "]]\n");
		}
	}

}
