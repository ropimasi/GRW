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
		File arquivo = new File(Parameters.CAMIN_PAD_ARQ_CONFIG_GRW_YAML);
		return arquivo.exists() && arquivo.isFile();
	}


	/* Se não existe o arquivo grw.yaml no diretório padrão, criará o caminho e o arquivo. */
	public static boolean criarDirArqConfigGrwSeNaoExiste() {
		while (!arquivoConfigGrwExiste()) {
			try {
				File diretorioHomeConfig = new File(Parameters.CAMIN_PAD_DIR_HOME_CONFIG);
				if (!diretorioHomeConfig.exists() && !diretorioHomeConfig.mkdirs()) {
					System.out.println("Falha ao criar diretório de configuração.");
					return false;
				}

				File diretorioConfigGrw = new File(Parameters.CAMIN_PAD_DIR_CONFIG_GRW);
				if (!diretorioConfigGrw.exists() && !diretorioConfigGrw.mkdirs()) {
					System.out.println("Falha ao criar diretório grw.");
					return false;
				}

				File arquivo = new File(Parameters.CAMIN_PAD_ARQ_CONFIG_GRW_YAML);
				if (!arquivo.createNewFile()) {
					System.out.println("Falha ao criar arquivo de configuração [grw.yaml].");
					return false;
				}

			} catch (IOException e) {
				System.out.println("Erro ao criar arquivo ou diretório: " + e.getMessage());
				return false;
			}
		}
		return true;
	}


	public static void registrarConfigEmArquivo(String chave, String valor) {
		File arquivoYaml = new File(Parameters.CAMIN_PAD_ARQ_CONFIG_GRW_YAML);
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

		try (FileWriter writer = new FileWriter(Parameters.CAMIN_PAD_ARQ_CONFIG_GRW_YAML)) {
			Yaml yaml = new Yaml(options);
			yaml.dump(dadosYaml, writer);
		} catch (IOException e) {
			System.out.println("Erro ao gravar no arquivo: " + e.getMessage());
		}
	}


	public static Map<String, Object> lerChavesValoresYamlDoArquivo() {
		File yamlFile = new File(Parameters.CAMIN_PAD_ARQ_CONFIG_GRW_YAML);

		if (yamlFile.exists()) {
			try (FileInputStream inputStream = new FileInputStream(yamlFile)) {
				Yaml yaml = new Yaml();
				return yaml.load(inputStream);
			} catch (IOException e) {
				System.out.println("Erro ao ler os chaves-valores do arquivo YAML: " + e.getMessage());
			}
		} else {
			System.out.println("Arquivo YAML não encontrado.");
		}
		return new HashMap<>();
	}


	public static void carregarConfiguracoesDoArquivo() {
		Map<String, Object> configYaml = ConfigManager.lerChavesValoresYamlDoArquivo();

		if (configYaml.containsKey("tempoEspera")) {
			ConfigManager.tempoEspera = Integer.parseInt(configYaml.get("tempoEspera").toString());
		}
		if (configYaml.containsKey("diretorioImagens")) {
			ConfigManager.diretorioImagens = configYaml.get("diretorioImagens").toString();
		}
		if (configYaml.containsKey("rodando")) {
			ConfigManager.rodando = configYaml.get("rodando").toString().equals("true");
		}
	}


	public static void definirConfiguracoesValoresPadroes() {
		ConfigManager.tempoEspera = Parameters.TEMPO_PADRAO;
		registrarConfigEmArquivo("tempoEspera", String.valueOf(ConfigManager.tempoEspera));
		ConfigManager.diretorioImagens = Parameters.DIRETORIO_PADRAO_IMAGENS;
		registrarConfigEmArquivo("diretorioImagens", ConfigManager.diretorioImagens);
		System.out.println("Configurações de tempo e diretório definidas com valor padrão.");
		
		DialogUI.mostrarStatus();
	}

}
