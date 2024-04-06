import java.io.File;
import java.io.FileNotFoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedDeque;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class UrlQueueApp {
    private static String urlQueueHost;
    private static int urlQueuePort;

    public static void main(String[] args){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();

        try{
            if (args.length == 0){
                System.out.println("Usage: java App <config file>");
                return;
            }

            String fileName = args[0];

            readFileProperties(fileName);

            UrlQueue urlQueue = new UrlQueue();
            Registry registry = LocateRegistry.createRegistry(urlQueuePort);
            registry.rebind(urlQueueHost, urlQueue);

            System.out.println("Queue ready.");
        } catch (Exception e){
            System.out.println("Error on main: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void readFileProperties(String fileName){
        File propFile = new File(fileName);

        try{
            Scanner scanner = new Scanner(propFile);
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                System.out.println(line);

                String[] parts = line.split(";");

                switch (parts[0]){
                    case "urlQueue":
                        urlQueueHost = parts[1];
                        urlQueuePort = Integer.parseInt(parts[2]);
                        break;

                    default:
                        break;
                }
            }
            scanner.close();
        } catch (FileNotFoundException e){
            System.out.println("Arquivo de propriedades não encontrado ");
            e.printStackTrace();;
        } catch (IndexOutOfBoundsException e){
            System.out.println("Arquivo de propriedades mal formatado");
            e.printStackTrace();
        }
    }
}
