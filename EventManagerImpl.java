import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class EventManagerImpl extends UnicastRemoteObject implements EventManager {

    private static final long serialVersionUID = 1L;
    private List<String> events;

    protected EventManagerImpl() throws RemoteException {
        super();
        events = new ArrayList<>();
    }

    public void addEvent(int id, String name, String startTime, String endTime, int attendees, String room) throws RemoteException {
        events.add("ID: " + id + ", Name: " + name + ", Start Time: " + startTime + ", End Time: " + endTime + ", Attendees: " + attendees + ", Room: " + room);
    }

    public void modifyEvent(int id, String name, String startTime, String endTime, int attendees, String room) throws RemoteException {
        deleteEvent(id);
        addEvent(id, name, startTime, endTime, attendees, room);
    }

    public void deleteEvent(int id) throws RemoteException {
        events.removeIf(event -> event.startsWith("ID: " + id + ","));
    }

    public List<String> listEvents() throws RemoteException {
        return events;
    }

    public static void main(String[] args) {
        try {
            EventManagerImpl obj = new EventManagerImpl();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("EventManager", obj);
            System.out.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
