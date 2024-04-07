package googol;

import java.io.File;
import java.io.FileNotFoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class UrlQueueApp {
  private static String urlQueueName;
  private static int urlQueuePort;

  /**
   * Este método espera um argumento: o nome do arquivo de configuração
   * Ele lê o arquivo de configuração, cria uma instância de UrlQueue e a regista
   * no RMI
   *
   * @param args Os argumentos da linha de comando. O primeiro argumento deve ser
   *             o nome do arquivo de configuração
   */
  public static void main(String[] args) {

    try {
      if (args.length == 0) {
        System.out.println("Usage: java App <config file>");
        return;
      }

      String fileName = args[0];

      readFileProperties(fileName);

      UrlQueue urlQueue = new UrlQueue();
      Registry registry = LocateRegistry.createRegistry(urlQueuePort);
      registry.rebind(urlQueueName, urlQueue);

      System.out.println("Queue ready.");
    } catch (Exception e) {
      System.out.println("Error on main: " + e.getMessage());
      e.printStackTrace();
    }
  }

  /**
   * Este método lê as propriedades de configuração do arquivo especificado
   *
   * @param fileName O nome do arquivo de configuração
   */
  private static void readFileProperties(String fileName) {
    File propFile = new File(fileName);

    try {
      Scanner scanner = new Scanner(propFile);
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        System.out.println(line);

        String[] parts = line.split(";");

        switch (parts[0]) {
          case "urlQueue":
            urlQueuePort = Integer.parseInt(parts[2]);
            urlQueueName = parts[3];
            break;

          default:
            break;
        }
      }
      scanner.close();
    } catch (FileNotFoundException e) {
      System.out.println("Arquivo de propriedades não encontrado ");
      e.printStackTrace();
      ;
    } catch (IndexOutOfBoundsException e) {
      System.out.println("Arquivo de propriedades mal formatado");
      e.printStackTrace();
    }
  }
}
