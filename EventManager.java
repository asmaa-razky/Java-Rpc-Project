import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface EventManager extends Remote {
    void addEvent(int id, String name, String startTime, String endTime, int attendees, String room) throws RemoteException;
    void modifyEvent(int id, String name, String startTime, String endTime, int attendees, String room) throws RemoteException;
    void deleteEvent(int id) throws RemoteException;
    List<String> listEvents() throws RemoteException;
}
