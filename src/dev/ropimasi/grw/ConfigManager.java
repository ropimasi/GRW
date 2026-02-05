package dev.ropimasi.grw;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;



public class ConfigManager {

	static int waitingTime;
	static String imagesDirectory;
	static boolean running;


	private static boolean configFileExists() {
		File configFile = new File(Parameters.PATH_HOME_CONFIG_GRW_FILE_YAML_DEFAULT);
		return configFile.exists() && configFile.isFile();
	}


	/* It will create the path and file if the grw.yaml file does not exist in the default directory. */
	static void createDirFileConfigIfNotExists() {
		while (!configFileExists()) {
			try {
				File diretorioHomeConfig = new File(Parameters.PATH_HOME_CONFIG_DEFAULT);
				if (!diretorioHomeConfig.exists() && !diretorioHomeConfig.mkdirs()) {
					System.out.println("Warning: Failed to create configuration directory.");
					//return false;
				}

				File diretorioConfigGrw = new File(Parameters.PATH_HOME_CONFIG_GRW_DEFAULT);
				if (!diretorioConfigGrw.exists() && !diretorioConfigGrw.mkdirs()) {
					System.out.println("Warning: Failed to create 'grw' directory.");
					//return false;
				}

				File arquivo = new File(Parameters.PATH_HOME_CONFIG_GRW_FILE_YAML_DEFAULT);
				if (!arquivo.createNewFile()) {
					System.out.println("Warning: Failed to create configuration file [grw.yaml].");
					//return false;
				}

			} catch (IOException e) {
				System.out
						.println("Error: Exception creating file or directory. [[ Message: " + e.getMessage() + " ]]");
				//return false;
			}
		}
		//return true;
	}


	static void writeConfigToFile(String key, String value) {
		File yamlFile = new File(Parameters.PATH_HOME_CONFIG_GRW_FILE_YAML_DEFAULT);
		Map<String, Object> yamlData;

		if (yamlFile.exists()) {
			try (FileInputStream inputStream = new FileInputStream(yamlFile)) {
				Yaml yaml = new Yaml();
				yamlData = yaml.load(inputStream);
				if (yamlData == null) {
					yamlData = new HashMap<>();
				}
			} catch (YAMLException | IOException e) {
				System.out.println("Error: Exception processing configuration file [grw.yaml]. [[ Message: "
						+ e.getMessage() + " ]]");
				yamlData = new HashMap<>();
			}
		} else {
			yamlData = new HashMap<>();
		}

		yamlData.put(key, value);

		DumperOptions options = new DumperOptions();
		options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
		options.setPrettyFlow(true);

		try (FileWriter writer = new FileWriter(Parameters.PATH_HOME_CONFIG_GRW_FILE_YAML_DEFAULT)) {
			Yaml yaml = new Yaml(options);
			yaml.dump(yamlData, writer);
		} catch (IOException e) {
			System.out.println(
					"Error: Exception writing configuration file [grw.yaml]. [[ Message: " + e.getMessage() + " ]]");
		}
	}


	static void setImageDiretory(String imagesDirectory) {

		imagesDirectory = Utility.convertTildeToPath(imagesDirectory);

		File dir = new File(imagesDirectory);
		if (!dir.exists() || !dir.isDirectory()) {
			System.out.println("Error: Invalid images directory.");
		} else {
			ConfigManager.writeConfigToFile("imagesDirectory", imagesDirectory);
			System.out.println("Info: The images directory has been set [ " + imagesDirectory + " ].");
		}
	}


	static void setWaitingTime(String time) {
		try {
			int waitingTime = Integer.parseInt(time);
			if (waitingTime < Parameters.TIME_MIN) {
				System.out.println(
						"Error: Waiting time must be a positive integer greater than or equal to the minimum number ["
								+ Parameters.TIME_MIN + "].");
			} else {
				ConfigManager.writeConfigToFile("waitingTime", String.valueOf(waitingTime));
				System.out.println("Info: The waiting time has been set [" + waitingTime + "seg].");
			}
		} catch (NumberFormatException e) {
			System.out.println(
					"Error: Exception: Invalid input. Could not convert [" + time + "] to a positive integer.");
		}
	}


	static void setConfigFromDefaultValues() {
		ConfigManager.waitingTime = Parameters.TIME_DEFAULT;
		writeConfigToFile("waitingTime", String.valueOf(ConfigManager.waitingTime));
		ConfigManager.imagesDirectory = Parameters.IMAGES_DIR_DEFAULT;
		writeConfigToFile("imagesDirectory", ConfigManager.imagesDirectory);
		System.out.println("Info: Waiting time and images directory settings have been set to default value.");
		DialogUI.showStatus();
	}


	static void readConfigurationFormFile() {
		Map<String, Object> configYaml = ConfigManager.readKeyValueYamlFromFile();

		if (configYaml.containsKey("waitingTime")) {
			ConfigManager.waitingTime = Integer.parseInt(configYaml.get("waitingTime").toString());
		}
		if (configYaml.containsKey("imagesDirectory")) {
			ConfigManager.imagesDirectory = configYaml.get("imagesDirectory").toString();
		}
		if (configYaml.containsKey("running")) {
			ConfigManager.running = configYaml.get("running").toString().equals("true");
		}
	}


	static Map<String, Object> readKeyValueYamlFromFile() {
		File yamlFile = new File(Parameters.PATH_HOME_CONFIG_GRW_FILE_YAML_DEFAULT);

		if (yamlFile.exists()) {
			try (FileInputStream inputStream = new FileInputStream(yamlFile)) {
				Yaml yaml = new Yaml();
				return yaml.load(inputStream);
			} catch (IOException e) {
				System.out.println("Error: Exception reading key-value pairs from configuration file ["
						+ Parameters.PATH_HOME_CONFIG_GRW_FILE_YAML_DEFAULT + "]. [[ Message: " + e.getMessage()
						+ " ]]");
			}
		} else {
			System.out.println("Warning: Configuration file [" + Parameters.PATH_HOME_CONFIG_GRW_FILE_YAML_DEFAULT
					+ "] not found.");
		}
		return new HashMap<>();
	}

}
