import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class RentalService {
    private HashMap<Integer, Vehicle> listOfVehicles;
    private int uniqueId;

    public RentalService() {
        this.listOfVehicles = new HashMap<>();
        uniqueId = 0;
    }

    public void addVehicle(String type) {
        listOfVehicles.put(uniqueId, new Vehicle(type, uniqueId));
        System.out.println("Added vehicle " + type + " with ID " + uniqueId);
        uniqueId++;
    }

    public void listVehicles() {
        for (int key : listOfVehicles.keySet()) {
            Vehicle v = listOfVehicles.get(key);
            System.out.println("Vehicle Type " + v.getType() + " ID : " + v.getId());

            if (v.getAvailableDates().get(0).numberOfDays == -1) {
                System.out.println("Next available date " + v.getAvailableDates().get(0).startDate);
            } else {
                System.out.println("Next available date " + v.getAvailableDates().get(0).startDate + " for " +
                        v.getAvailableDates().get(0).numberOfDays);
            }

        }
    }

    public boolean bookVehicle(LocalDate date, int numberOfDays, int id) {
        if(!listOfVehicles.containsKey(id)) {
            System.out.println("Vehicle with id " + id + " does not exist");
            return false;
        }

        boolean success = listOfVehicles.get(id).bookVehicle(date, numberOfDays);
        if(success) {
            System.out.println("Successfully booked vehicle " + id + " for time " + date.toString() + " for " + numberOfDays + " days");
            return true;
        } else {
            System.out.println("CANNOT book vehicle " + id + " + for time " + date.toString() + " for " + numberOfDays + " days");
            return false;
        }
    }
}
