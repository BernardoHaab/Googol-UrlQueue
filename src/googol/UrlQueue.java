package googol;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedDeque;

public class UrlQueue extends UnicastRemoteObject implements IUrlQueue {

  private ConcurrentLinkedDeque<String> urls = new ConcurrentLinkedDeque<String>();
  private Set<String> knownUrls = new HashSet<>();

  public UrlQueue() throws RemoteException {
    super();
    // urls.add("http://www.yahoo.com");
  }

  /**
   * Busca próxima url para ser consumida por Downloaders
   *
   * @return Próxima url a ser baixada
   */
  @Override
  public String getNextUrl() throws RemoteException {
    return urls.poll();
  }

  /**
   * Adiciona uma lista de página ao fim da fila caso a url não seja conhecida
   *
   * @param newUrls Lista de urls para serem adicionadas na fila
   */
  @Override
  public void addUrls(List<String> newUrls) throws RemoteException {
    for (String url : newUrls) {
      if (knownUrls.add(url)) {
        urls.add(url);
      }
    }
  }

  @Override
  public int size() throws RemoteException {
    return urls.size();
  }

  /**
   * Adiciona url no início da fila, independente se a url é conhecida
   *
   * @param url Nova url
   */
  @Override
  public void addUrlFirst(String url) throws RemoteException {
    System.out.println("Adding url first: " + url);
    urls.addFirst(url);
    knownUrls.add(url);
    System.out.println(urls);
  }

  /**
   * Adiciona url no fim da fila, independente se a url é conhecida
   *
   * @param url Nova url
   */
  @Override
  public void addUrl(String url) throws RemoteException {
    System.out.println("Adding url first: " + url);
    urls.add(url);
    knownUrls.add(url);
    System.out.println(urls);
  }
}
