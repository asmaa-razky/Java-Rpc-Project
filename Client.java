import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Client extends JFrame {

    private EventManager stub;
    private JTextArea eventTextArea;

    public Client() {
        try {
            Registry registry = LocateRegistry.getRegistry("192.168.184.131",1099);
            stub = (EventManager) registry.lookup("EventManager");
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }

        // Set up the GUI
        setTitle("Event Management System");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        eventTextArea = new JTextArea();
        eventTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(eventTextArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(0, 2, 10, 10));

        JLabel idLabel = new JLabel("Event ID:");
        JTextField idField = new JTextField();
        inputPanel.add(idLabel);
        inputPanel.add(idField);

        JLabel nameLabel = new JLabel("Event Name:");
        JTextField nameField = new JTextField();
        inputPanel.add(nameLabel);
        inputPanel.add(nameField);

        JLabel startTimeLabel = new JLabel("Start Time:");
        JTextField startTimeField = new JTextField();
        inputPanel.add(startTimeLabel);
        inputPanel.add(startTimeField);

        JLabel endTimeLabel = new JLabel("End Time:");
        JTextField endTimeField = new JTextField();
        inputPanel.add(endTimeLabel);
        inputPanel.add(endTimeField);

        JLabel attendeesLabel = new JLabel("Number of Attendees:");
        JTextField attendeesField = new JTextField();
        inputPanel.add(attendeesLabel);
        inputPanel.add(attendeesField);

        JLabel roomLabel = new JLabel("Room:");
        JTextField roomField = new JTextField();
        inputPanel.add(roomLabel);
        inputPanel.add(roomField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 5, 10, 10));

        JButton addButton = new JButton("Add Event");
        buttonPanel.add(addButton);

        JButton modifyButton = new JButton("Modify Event");
        buttonPanel.add(modifyButton);

        JButton deleteButton = new JButton("Delete Event");
        buttonPanel.add(deleteButton);

        JButton listButton = new JButton("List Events");
        buttonPanel.add(listButton);

        JButton exitButton = new JButton("Exit");
        buttonPanel.add(exitButton);

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);

        // Action listeners for buttons
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(idField.getText());
                    String name = nameField.getText();
                    String startTime = startTimeField.getText();
                    String endTime = endTimeField.getText();
                    int attendees = Integer.parseInt(attendeesField.getText());
                    String room = roomField.getText();
                    stub.addEvent(id, name, startTime, endTime, attendees, room);
                    JOptionPane.showMessageDialog(Client.this, "Event added successfully.");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(Client.this, "Error adding event.");
                }
            }
        });

        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(idField.getText());
                    String name = nameField.getText();
                    String startTime = startTimeField.getText();
                    String endTime = endTimeField.getText();
                    int attendees = Integer.parseInt(attendeesField.getText());
                    String room = roomField.getText();
                    stub.modifyEvent(id, name, startTime, endTime, attendees, room);
                    JOptionPane.showMessageDialog(Client.this, "Event modified successfully.");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(Client.this, "Error modifying event.");
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(idField.getText());
                    stub.deleteEvent(id);
                    JOptionPane.showMessageDialog(Client.this, "Event deleted successfully.");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(Client.this, "Error deleting event.");
                }
            }
        });

        listButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    List<String> events = stub.listEvents();
                    eventTextArea.setText("");
                    for (String event : events) {
                        eventTextArea.append(event + "\n");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(Client.this, "Error listing events.");
                }
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Client().setVisible(true);
            }
        });
    }
}

