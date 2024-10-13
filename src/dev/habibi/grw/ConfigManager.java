package dev.habibi.grw;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;




public class ConfigManager {
	static int waitingTime;
	static String imagesDirectory;
	static boolean running;
	static String pathImgDir;
	static String aliasImgDir;
	static List<Map<String, Object>> imgsDirAliasList;// = new ArrayList<Map<String, String>>();



	static void addImageDiretoryAlias(String path, String alias) {
		System.out.println("\n\tDEBUG: addImageDirectoryAlias(String " + path + ", String " + alias + ")\n");

		// Record para armazenar os atributos path e alias
		record PathAlias(String path, String alias) {
		}

		List<Map<String, String>> serializableList;

		// Tenta carregar o conteúdo existente do arquivo YAML
		Yaml yamlReader = new Yaml();
		try (FileInputStream inputStream = new FileInputStream(Parameters.PATH_CONFIG_GRW_FILE_YAML_DEFAULT)) {
			Map<String, Object> data = yamlReader.load(inputStream);

			// Verifica se existe a chave "imgsDirAliasList" no arquivo YAML
			if (data != null && data.containsKey("imgsDirAliasList")) {
				serializableList = (List<Map<String, String>>) data.get("imgsDirAliasList");
			} else {
				// Se a chave não existir, cria uma nova lista
				serializableList = new ArrayList<>();
			}
		} catch (IOException e) {
			e.printStackTrace();
			serializableList = new ArrayList<>();
		}

		// Adiciona o novo PathAlias à lista serializável
		Map<String, String> newDirectory = new HashMap<>();
		newDirectory.put("path", path);
		newDirectory.put("alias", alias);
		if (serializableList.contains(newDirectory)) {
			System.out.println(newDirectory + " já existe!");
		} else {
			serializableList.add(newDirectory);
			// Configura opções de formatação do YAML
			DumperOptions options = new DumperOptions();
			options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
			Yaml yamlWriter = new Yaml(options);

			// Escreve a lista modificada no arquivo YAML, sob a chave "imgsDirAliasList"
			try (FileWriter writer = new FileWriter(Parameters.PATH_CONFIG_GRW_FILE_YAML_DEFAULT)) {
				Map<String, Object> dataToWrite = new HashMap<>();
				dataToWrite.put("imgsDirAliasList", serializableList);  // Atualiza ou cria a chave
				yamlWriter.dump(dataToWrite, writer);
				System.out.println("Alias '" + alias + "' adicionado com sucesso.  asd");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}



	static void remImageDirectoryAlias(String alias) {
		System.out.println("\n\tDEBUG: remImageDirectoryAlias(String " + alias + ")\n");

		List<Map<String, String>> serializableList;

		// Tenta carregar o conteúdo existente do arquivo YAML
		Yaml yaml = new Yaml();
		try (FileInputStream inputStream = new FileInputStream(Parameters.PATH_CONFIG_GRW_FILE_YAML_DEFAULT)) {
			Map<String, Object> data = yaml.load(inputStream);

			// Verifica se existe a chave "imgsDirAliasList" no arquivo YAML
			if (data != null && data.containsKey("imgsDirAliasList")) {
				serializableList = (List<Map<String, String>>) data.get("imgsDirAliasList");
			} else {
				System.out.println("A lista 'imgsDirAliasList' não foi encontrada.");
				return; // Sai do método se não houver a chave
			}
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		// Remove o diretório com o alias fornecido
		boolean removed = serializableList.removeIf(dir -> alias.equals(dir.get("alias")));

		if (!removed) {
			System.out.println("Alias '" + alias + "' não encontrado na lista.");
			return; // Sai do método se o alias não foi encontrado
		}

		// Configura opções de formatação do YAML
		DumperOptions options = new DumperOptions();
		options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
		Yaml yamlWriter = new Yaml(options);

		// Escreve a lista modificada de volta no arquivo YAML
		try (FileWriter writer = new FileWriter(Parameters.PATH_CONFIG_GRW_FILE_YAML_DEFAULT)) {
			Map<String, Object> dataToWrite = new HashMap<>();
			dataToWrite.put("imgsDirAliasList", serializableList);  // Atualiza a chave

			yamlWriter.dump(dataToWrite, writer);
			System.out.println("Alias '" + alias + "' removido com sucesso.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	private static boolean configFileExists() {
		File configFile = new File(Parameters.PATH_CONFIG_GRW_FILE_YAML_DEFAULT);
		return configFile.exists() && configFile.isFile();
	}



	/* It will create the path and file if the grw.yaml file does not exist in the default directory. */
	static boolean createDirFileConfigGrwIfNotExists() {
		while (!configFileExists()) {
			try {
				File diretorioHomeConfig = new File(Parameters.PATH_HOME_CONFIG_DEFAULT);
				if (!diretorioHomeConfig.exists() && !diretorioHomeConfig.mkdirs()) {
					System.out.println("Warning: Failed to create configuration directory.");
					return false;
				}
				File diretorioConfigGrw = new File(Parameters.PATH_CONFIG_GRW_DEFAULT);
				if (!diretorioConfigGrw.exists() && !diretorioConfigGrw.mkdirs()) {
					System.out.println("Warning: Failed to create 'grw' directory.");
					return false;
				}
				File arquivo = new File(Parameters.PATH_CONFIG_GRW_FILE_YAML_DEFAULT);
				if (!arquivo.createNewFile()) {
					System.out.println("Warning: Failed to create configuration file [grw.yaml].");
					return false;
				}
			} catch (IOException e) {
				System.out
						.println("Error: Exception creating file or directory. [[ Message: " + e.getMessage() + " ]]");
				return false;
			}
		}
		return true;
	}



	static void writeConfigToFile(String key, String value) {
		File yamlFile = new File(Parameters.PATH_CONFIG_GRW_FILE_YAML_DEFAULT);
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
		try (FileWriter writer = new FileWriter(Parameters.PATH_CONFIG_GRW_FILE_YAML_DEFAULT)) {
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



	static Map<String, Object> readKeyValueYamlFromFile() {
		File yamlFile = new File(Parameters.PATH_CONFIG_GRW_FILE_YAML_DEFAULT);
		if (yamlFile.exists()) {
			try (FileInputStream inputStream = new FileInputStream(yamlFile)) {
				Yaml yaml = new Yaml();
				return yaml.load(inputStream);
			} catch (IOException e) {
				System.out.println(
						"Error: Exception reading key-value pairs from configuration file [grw.yaml]. [[ Message: "
								+ e.getMessage() + " ]]");
			}
		} else {
			System.out.println("Warning: Configuration file [grw.yaml] not found.");
		}
		return new HashMap<>();
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
		// Verifica se a chave "imgsDirAliasList" existe no configYaml
		if (configYaml.containsKey("imgsDirAliasList")) {
			ConfigManager.imgsDirAliasList = readImgsDirAliasList(configYaml);
		} else { //FURTHER: remove this ELSE.
			System.out.println("The key 'imgsDirAliasList' wass not found in the config file.");
		}
	}



	private static List<Map<String, Object>> readImgsDirAliasList(Map<String, Object> configYaml) {
		// Recupera o valor da chave e verifica se é uma lista
		Object value = configYaml.get("imgsDirAliasList");
		if (value instanceof List) {
			List<Map<String, Object>> list = (List<Map<String, Object>>) value;
			// Inicializa imgsDirAliasList com o conteúdo recuperado
			List<Map<String, Object>> imgsDirAliasList = new ArrayList<>(list);
			// Imprime para depuração
			System.out.println("Aliases directories list has been loaded successfully.");
			for (Map<String, Object> entry : imgsDirAliasList) {
				System.out.println(entry);
			}
			return imgsDirAliasList;
		} else {
			System.out.println("The key 'imgsDirAliasList' has no valid list.");
		}
		return new ArrayList<Map<String, Object>>();
	}
}
