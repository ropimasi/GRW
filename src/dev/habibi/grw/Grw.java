package dev.habibi.grw;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;



public class Grw {

	private static final String CAMINHO_ARQUIVO_YAML = System.getProperty("user.home") + "/.config/grw/grw.yaml";

	private static final int TEMPO_MINIMO = 5;
	private static final int TEMPO_PADRAO = 80;
	private static final String DIRETORIO_PADRAO_IMAGENS = System.getProperty("user.home") + "/my-wallpapers";
	private static final boolean RODANDO_PADRAO = false;

	private static int tempoEspera = TEMPO_PADRAO;
	private static String diretorioImagens = DIRETORIO_PADRAO_IMAGENS;
	private static boolean rodando = RODANDO_PADRAO;


	public static void main(String[] args) {
		if (args.length == 0 || args[0].equals("-h")) {
			mostrarAjuda();
			return;
		}

		for (int i = 0; i < args.length; i++) {

			switch (args[i]) {

			case "-t":
				if (i + 1 < args.length) {
					try {
						int tmp = Integer.parseInt(args[++i]);
						if (tmp < TEMPO_MINIMO) {
							System.out.println(
									"Erro: O tempo de espera deve ser um número inteiro positivo maior-igual que o número mínimo: "
											+ TEMPO_MINIMO + ".");
						} else {
							tempoEspera = tmp;
							System.out.println("O tempo de espera foi definido para " + tempoEspera + " segundos.");
							registrarEmArquivo("tempoEspera", String.valueOf(tempoEspera));
						}
					} catch (NumberFormatException e) {
						System.out.println(
								"Erro: O tempo de espera deve ser um número inteiro positivo maior-igual que o número mínimo: "
										+ TEMPO_MINIMO + ".");
					}
				} else {
					System.out.println("Erro: A opção -t requer um valor de tempo.");
					return;
				}
				break;

			case "-d":
				if (i + 1 < args.length) {
					String tmp = args[++i];

					/* Verifica se o caminho começa com "~" e substitui pelo diretório home do usuário */
					if (tmp.startsWith("~")) {
						tmp = System.getProperty("user.home") + tmp.substring(1);
					}

					File dir = new File(tmp);
					if (!dir.exists() || !dir.isDirectory()) {
						System.out.println("Erro: Diretório de imagens inválido.");
					} else {
						diretorioImagens = tmp;
						System.out.println("O diretório de imagens foi definido [ " + diretorioImagens + " ].");
						registrarEmArquivo("diretorioImagens", diretorioImagens);
					}
				} else {
					System.out.println("Erro: A opção -d requer um diretório de imagens.");
					return;
				}
				break;

			case "-a":
				iniciar();
				break;

			case "-o":
				parar();
				break;

			case "-s":
				mostrarStatus();
				break;

			case "-h":
				mostrarAjuda();
				return;

			default:
				System.out.println("Opção desconhecida: " + args[i]);
				mostrarAjuda();
				return;
			}
		}
	}


	private static void iniciar() {
		// Carregar valores do arquivo YAML antes de iniciar
		carregarValoresDoArquivoYaml();

		if (diretorioImagens.isEmpty()) {
			System.out.println("Erro: Defina o diretório de imagens com a opção -d.");
			return;
		}

		if (tempoEspera < TEMPO_MINIMO) {
			System.out.println(
					"Erro: Defina o tempo de espera com a opção -t. O valor deve ser maior ou igual ao tempo mínimo: "
							+ TEMPO_MINIMO + ".");
			return;
		}

		rodando = true; // Define rodando = true para iniciar o loop.
		registrarEmArquivo("rodando", "true");

		System.out.println(
				"Trocando papel de parede a cada " + tempoEspera + " segundos, da origem " + diretorioImagens + " .");

		while (rodando) {
			trocarPapelDeParede();

			try {
				Thread.sleep(tempoEspera * 1000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// Carregar valores do arquivo YAML novamente para verificar se manteve os
			// parâmetros de tempo, diretório, e de rodando.
			carregarValoresDoArquivoYaml();
		}
	}


	private static void parar() {
		rodando = false;
		registrarEmArquivo("rodando", "false");
		System.out.println("Parando troca de papel de parede.");
	}


	private static void mostrarStatus() {
		System.out.println("Status atual:");
		System.out.println("\tTempo de espera: " + ((tempoEspera <= 0) ? "Não definido" : tempoEspera + " segundos"));
		System.out
				.println("\tDiretório de imagens: " + (diretorioImagens.isEmpty() ? "Não definido" : diretorioImagens));
		System.out.println("\tRodando: " + rodando);
	}


	private static void mostrarAjuda() {
		System.out.println("Uso do comando grw:");
		System.out.println("\t-t <tempo>    : Define o tempo de espera em segundos.");
		System.out.println("\t-d <diretório>: Define o caminho do diretório contendo imagens.");
		System.out.println("\t-a            : Inicia a troca aleatória de papel de parede.");
		System.out.println("\t-o            : Para a troca de papel de parede.");
		System.out.println("\t-s            : Mostra o status atual da configuração.");
		System.out.println("\t-h            : Mostra este manual de ajuda.");
	}


	private static void trocarPapelDeParede() {
		File dir = new File(diretorioImagens);
		File[] imagens = dir.listFiles((d, name) -> name.endsWith(".jpg") || name.endsWith(".jpeg")
				|| name.endsWith(".png") || name.endsWith(".webp") || name.endsWith(".gif"));

		if (imagens == null || imagens.length == 0) {
			System.out.println("Nenhuma imagem encontrada no diretório.");
			return;
		}

		Random random = new Random();
		File imagemAleatoria = imagens[random.nextInt(imagens.length)];
		String caminhoImagem = imagemAleatoria.getAbsolutePath();

		try {
			String comando = "gsettings set org.gnome.desktop.background picture-uri-dark file://" + caminhoImagem;
			Runtime.getRuntime().exec(comando);
			System.out.println("Papel de parede alterado para: " + caminhoImagem);
			registrarEmArquivo("papelDeParede", caminhoImagem);
		} catch (Exception e) {
			System.out.println("Erro ao trocar o papel de parede: " + e.getMessage());
		}
	}


	// Método que grava ou sobrescreve as informações no arquivo YAML
	private static void registrarEmArquivo(String chave, String valor) {

		File arquivoYaml = new File(CAMINHO_ARQUIVO_YAML);
		Map<String, Object> dadosYaml;

		// Tentativa de carregar o arquivo existente, se houver
		if (arquivoYaml.exists()) {
			try (FileInputStream inputStream = new FileInputStream(arquivoYaml)) {
				Yaml yaml = new Yaml();
				dadosYaml = yaml.load(inputStream);

				// Caso o arquivo esteja vazio ou inválido, criar um novo mapa
				if (dadosYaml == null) {
					dadosYaml = new HashMap<>();
				}
			} catch (YAMLException e) {
				System.out.println("Erro ao processar o arquivo YAML: " + e.getMessage());
				dadosYaml = new HashMap<>();
			} catch (IOException e) {
				System.out.println("Erro ao carregar o arquivo YAML: " + e.getMessage());
				dadosYaml = new HashMap<>(); // Se houver erro, iniciar um novo mapa
			}
		} else {
			dadosYaml = new HashMap<>(); // Se o arquivo não existir, criar um novo mapa
		}

		// Atualizar ou adicionar a chave-valor
		dadosYaml.put(chave, valor);

		// Opções para formatar o arquivo YAML de forma legível
		DumperOptions options = new DumperOptions();
		options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
		options.setPrettyFlow(true);

		Yaml yaml = new Yaml(options);

		// Verificar se o arquivo é gravável
		if (arquivoYaml.exists() && !arquivoYaml.canWrite()) {
			System.out.println("Erro: O arquivo YAML não pode ser gravado.");
			return;
		}

		try (FileWriter writer = new FileWriter(CAMINHO_ARQUIVO_YAML)) {
			// Gravar o conteúdo atualizado no arquivo, sobrescrevendo o anterior
			yaml.dump(dadosYaml, writer);
		} catch (IOException e) {
			System.out.println("Erro ao gravar no arquivo: " + e.getMessage());
		}
	}


	// Método que carrega os valores mais recentes do arquivo YAML
	private static void carregarValoresDoArquivoYaml() {
		File yamlFile = new File(CAMINHO_ARQUIVO_YAML);

		if (yamlFile.exists()) {
			try (FileInputStream inputStream = new FileInputStream(yamlFile)) {
				Yaml yaml = new Yaml();
				Map<String, Object> dadosYaml = yaml.load(inputStream);

				if (dadosYaml != null) {
					if (dadosYaml.containsKey("tempoEspera")) {
						try {
							tempoEspera = Integer.parseInt(dadosYaml.get("tempoEspera").toString());

						} catch (NumberFormatException e) {
							System.out
									.println("Erro: O valor de 'tempoEspera' no arquivo YAML não é um número válido.");
							tempoEspera = TEMPO_MINIMO;
							System.out.println("Aviso: O valor de 'tempoEspera' assumiu valor mínimo: " + tempoEspera
									+ " segundos.");
						} catch (NullPointerException e) {
							System.out.println("Erro: O valor de 'tempoEspera' no arquivo YAML está nulo.");
							tempoEspera = TEMPO_MINIMO;
							System.out.println("Aviso: O valor de 'tempoEspera' assumiu valor mínimo: " + tempoEspera
									+ " segundos.");
						}
					}

					if (dadosYaml.containsKey("diretorioImagens")) {
						try {
							diretorioImagens = dadosYaml.get("diretorioImagens").toString();
						} catch (NullPointerException e) {
							System.out.println("Erro: O valor de 'diretorioImagens' no arquivo YAML está nulo.");
							diretorioImagens = DIRETORIO_PADRAO_IMAGENS;
							System.out.println("Aviso: O valor de 'diretorioImagens' assumiu valor padrão: "
									+ diretorioImagens + ".");
						} catch (ClassCastException e) {
							System.out.println(
									"Erro: O valor de 'diretorioImagens' no arquivo YAML não é uma string válida.");
							diretorioImagens = DIRETORIO_PADRAO_IMAGENS;
							System.out.println("Aviso: O valor de 'diretorioImagens' assumiu valor padrão: "
									+ diretorioImagens + ".");
						}
					}

					if (dadosYaml.containsKey("rodando")) {
						try {
							rodando = (dadosYaml.get("rodando").toString().equals("true")) ? true : false;
						} catch (NullPointerException e) {
							System.out.println("Erro: O valor de 'rodando' no arquivo YAML está nulo.");

							rodando = RODANDO_PADRAO;
							System.out.println("Aviso: O valor de 'rodando' assumiu valor padrão: " + rodando + ".");

						} catch (ClassCastException e) {
							System.out.println("Erro: O valor de 'rodando' no arquivo YAML não é uma string válida.");
							rodando = RODANDO_PADRAO;
							System.out.println("Aviso: O valor de 'rodando' assumiu valor padrão: " + rodando + ".");
						}
					}

				}
			} catch (IOException | NumberFormatException e) {
				System.out.println("Erro ao carregar os valores do arquivo YAML: " + e.getMessage());
			}
		} else {
			System.out.println("Arquivo YAML não encontrado. Usando valores padrão.");
		}
	}
}
