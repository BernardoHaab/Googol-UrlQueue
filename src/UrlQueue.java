
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
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
    urls.add("http://www.yahoo.com");
  }

  @Override
  public String getNextUrl() throws RemoteException {
    return urls.poll();
  }

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

  @Override
  public void addUrlFirst(String url) throws RemoteException {
    urls.addFirst(url);
  }
}
