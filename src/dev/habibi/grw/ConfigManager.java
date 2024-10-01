package dev.habibi.grw;

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

	static int tempoEspera;
	static String diretorioImagens;
	static boolean rodando;


	private static boolean arquivoConfigGrwExiste() {
		File arquivo = new File(Parameters.CAMINHO_ARQ_CONFIG_GRW_YAML);
		return arquivo.exists() && arquivo.isFile();
	}


	public static void criarArquivoConfigGrw() {
		while (!arquivoConfigGrwExiste()) {
			try {
				File diretorioHomeConfig = new File(Parameters.CAMINHO_DIR_HOME_CONFIG);
				if (!diretorioHomeConfig.exists() && !diretorioHomeConfig.mkdirs()) {
					System.out.println("Falha ao criar diretório de configuração.");
					break;
				}

				File diretorioConfigGrw = new File(Parameters.CAMINHO_DIR_CONFIG_GRW);
				if (!diretorioConfigGrw.exists() && !diretorioConfigGrw.mkdirs()) {
					System.out.println("Falha ao criar diretório grw.");
					break;
				}

				File arquivo = new File(Parameters.CAMINHO_ARQ_CONFIG_GRW_YAML);
				if (!arquivo.createNewFile()) {
					System.out.println("Falha ao criar arquivo de configuração [grw.yaml].");
				}

			} catch (IOException e) {
				System.out.println("Erro ao criar arquivo ou diretório: " + e.getMessage());
				break;
			}
		}
	}


	public static void registrarConfigEmArquivo(String chave, String valor) {
		File arquivoYaml = new File(Parameters.CAMINHO_ARQ_CONFIG_GRW_YAML);
		Map<String, Object> dadosYaml;

		if (arquivoYaml.exists()) {
			try (FileInputStream inputStream = new FileInputStream(arquivoYaml)) {
				Yaml yaml = new Yaml();
				dadosYaml = yaml.load(inputStream);
				if (dadosYaml == null) {
					dadosYaml = new HashMap<>();
				}
			} catch (YAMLException | IOException e) {
				System.out.println("Erro ao processar o arquivo YAML: " + e.getMessage());
				dadosYaml = new HashMap<>();
			}
		} else {
			dadosYaml = new HashMap<>();
		}

		dadosYaml.put(chave, valor);

		DumperOptions options = new DumperOptions();
		options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
		options.setPrettyFlow(true);

		try (FileWriter writer = new FileWriter(Parameters.CAMINHO_ARQ_CONFIG_GRW_YAML)) {
			Yaml yaml = new Yaml(options);
			yaml.dump(dadosYaml, writer);
		} catch (IOException e) {
			System.out.println("Erro ao gravar no arquivo: " + e.getMessage());
		}
	}


	public static Map<String, Object> lerChavesValoresYaml() {
		File yamlFile = new File(Parameters.CAMINHO_ARQ_CONFIG_GRW_YAML);

		if (yamlFile.exists()) {
			try (FileInputStream inputStream = new FileInputStream(yamlFile)) {
				Yaml yaml = new Yaml();
				return yaml.load(inputStream);
			} catch (IOException e) {
				System.out.println("Erro ao ler os valores do arquivo YAML: " + e.getMessage());
			}
		} else {
			System.out.println("Arquivo YAML não encontrado. Usando valores padrão.");
		}
		return new HashMap<>();
	}


	public static void carregarConfiguracoesDoArquivo() {
		Map<String, Object> config = ConfigManager.lerChavesValoresYaml();
		if (config.containsKey("tempoEspera")) {
			tempoEspera = Integer.parseInt(config.get("tempoEspera").toString());
		}
		if (config.containsKey("diretorioImagens")) {
			diretorioImagens = config.get("diretorioImagens").toString();
		}
		if (config.containsKey("rodando")) {
			rodando = config.get("rodando").toString().equals("true");
		}
	}


	public static void mostrarStatus() {
		carregarConfiguracoesDoArquivo();

		System.out.println("Status atual:");
		System.out.println("\tTempo de espera: " + tempoEspera + " segundos");
		System.out.println("\tDiretório de imagens: " + diretorioImagens);
		System.out.println("\tRodando: " + rodando);
	}

}
