package dev.ropimasi.grw;

import java.io.File;
import java.util.Random;



public class WallpaperManager {

	static void startLoop() {
		ConfigManager.running = true;
		ConfigManager.writeConfigToFile("running", "true");
		ConfigManager.readConfigurationFormFile();

		System.out.println("Info: Wallpaper change every [" + ConfigManager.waitingTime + "] seconds from source ["
				+ ConfigManager.imagesDirectory + "] was started.");

		while (ConfigManager.running) {
			changeWallpaper();

			try {
				Thread.sleep(ConfigManager.waitingTime * 1000L);
			} catch (InterruptedException e) {
				System.out.println("Error: Exception on timer thread. [[ Message: " + e.getMessage() + " ]]");
			}

			ConfigManager.readConfigurationFormFile();
		}
		System.out.println("Info: Wallpaper change has been stoped!");
		System.exit(0);
	}


	static void stopLoop() {
		ConfigManager.running = false;
		ConfigManager.writeConfigToFile("running", "false");
		System.out.println("Info: Stopping wallpaper change...");
		//System.exit(0);
	}


	static void changeWallpaper() {
		File dir = new File(ConfigManager.imagesDirectory);
		File[] imagens = dir.listFiles((d, name) -> name.endsWith(".jpg") || name.endsWith(".jpeg")
				|| name.endsWith(".gif") || name.endsWith(".png") || name.endsWith(".webp"));

		if (imagens == null || imagens.length == 0) {
			System.out.println("Warning: No images found in directory [" + ConfigManager.imagesDirectory + "].");
			return;
		}

		Random random = new Random();
		File oneRandomImage = imagens[random.nextInt(imagens.length)];
		String imagePath = oneRandomImage.getAbsolutePath();

		try {
			String lightGnomeCommand = "gsettings set org.gnome.desktop.background picture-uri file://" + imagePath;
			String darkGnomeCommand = "gsettings set org.gnome.desktop.background picture-uri-dark file://" + imagePath;
			Runtime.getRuntime().exec(lightGnomeCommand);
			Runtime.getRuntime().exec(darkGnomeCommand);
		} catch (Exception e) {
			System.out.println("Error: Exception changing wallpaper. [[ Message: " + e.getMessage() + "]]");
		}
	}

}
