import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// Room class
class Room {
    int roomNumber;
    String type;
    boolean isAvailable;

    Room(int roomNumber, String type) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.isAvailable = true;
    }
}

// Guest class
class Guest {
    String name;
    String contact;

    Guest(String name, String contact) {
        this.name = name;
        this.contact = contact;
    }
}

// Reservation class
class Reservation {
    Guest guest;
    Room room;

    Reservation(Guest guest, Room room) {
        this.guest = guest;
        this.room = room;
        this.room.isAvailable = false;
    }
}

// Main reservation system
class ReservationSystem {
    private ArrayList<Room> rooms = new ArrayList<>();
    private ArrayList<Reservation> reservations = new ArrayList<>();

    ReservationSystem() {
        rooms.add(new Room(101, "Single"));
        rooms.add(new Room(102, "Double"));
        rooms.add(new Room(103, "Suite"));
    }

    public ArrayList<Room> getAvailableRooms() {
        ArrayList<Room> availableRooms = new ArrayList<>();
        for (Room room : rooms) {
            if (room.isAvailable) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    public String bookRoom(String guestName, String contact, int roomNumber) {
        for (Room room : rooms) {
            if (room.roomNumber == roomNumber && room.isAvailable) {
                Guest guest = new Guest(guestName, contact);
                reservations.add(new Reservation(guest, room));
                return "Room " + roomNumber + " booked successfully!";
            }
        }
        return "Room not available!";
    }

    public String getReservations() {
        if (reservations.isEmpty()) return "No reservations yet!";
        
        StringBuilder sb = new StringBuilder();
        for (Reservation res : reservations) {
            sb.append("Guest: ").append(res.guest.name)
              .append(" | Room: ").append(res.room.roomNumber)
              .append(" (").append(res.room.type).append(")\n");
        }
        return sb.toString();
    }
}

public class HotelReservationSystem {
       private ReservationSystem reservationSystem = new ReservationSystem();

    public HotelReservationSystem() {
        JFrame frame = new JFrame("Hotel Reservation System");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(3, 1));

        JButton checkRoomsBtn = new JButton("Check Available Rooms");
        JButton bookRoomBtn = new JButton("Book a Room");
        JButton viewReservationsBtn = new JButton("View Reservations");

        // Check available rooms action
        checkRoomsBtn.addActionListener(e -> {
            ArrayList<Room> availableRooms = reservationSystem.getAvailableRooms();
            StringBuilder roomList = new StringBuilder("Available Rooms:\n");
            for (Room room : availableRooms) {
                roomList.append("Room ").append(room.roomNumber)
                        .append(" (").append(room.type).append(")\n");
            }
            JOptionPane.showMessageDialog(frame, roomList.toString());
        });

        // Book room action
        bookRoomBtn.addActionListener(e -> {
            String name = JOptionPane.showInputDialog("Enter Guest Name:");
            String contact = JOptionPane.showInputDialog("Enter Contact:");
            String roomNumStr = JOptionPane.showInputDialog("Enter Room Number to Book:");
            
            if (name != null && contact != null && roomNumStr != null) {
                int roomNum = Integer.parseInt(roomNumStr);
                String result = reservationSystem.bookRoom(name, contact, roomNum);
                JOptionPane.showMessageDialog(frame, result);
            }
        });

        // View reservations action
        viewReservationsBtn.addActionListener(e -> {
            String reservations = reservationSystem.getReservations();
            JOptionPane.showMessageDialog(frame, reservations);
        });

        frame.add(checkRoomsBtn);
        frame.add(bookRoomBtn);
        frame.add(viewReservationsBtn);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new HotelReservationSystem();
    }
}
