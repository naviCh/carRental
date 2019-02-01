import java.util.LinkedList;
import java.time.LocalDate;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

public class Vehicle {
    private String type;
    private String owner;
    private int id;
    private LinkedList<carAgenda> agenda;


    public String getType() {
        return type;
    }

    public String getOwner() {
        return owner;
    }

    public int getId() {
        return id;
    }

    public class carAgenda {
        public LocalDate startDate;
        public long numberOfDays;
        public LocalDate endDate;

        public carAgenda(LocalDate startDate, long numberOfDays) {
            this.startDate = startDate;
            this.numberOfDays = numberOfDays;
            this.endDate = this.startDate.plusDays(numberOfDays);
        }
    }

    public Vehicle(String type, int id) {
        this.type = type;
        this.id = id;
        owner = null;
        agenda = new LinkedList<>();
    }

    /**
     * Cases:
     * 1) If I book before today, return false
     * 2) Check if the new Date + duration is before the first appointment
     * 3) Check if the new Date fits between car's agenda blocks
     * 4) Check if the new Date is after the latest agenda
     * @param startDate
     * @param numberOfDays
     * @return
     */
    public boolean bookVehicle(LocalDate startDate, long numberOfDays) {
        LocalDate endDate = startDate.plusDays(numberOfDays);

        if(startDate.isBefore(LocalDate.now())) return false;

        if(agenda.size() == 0) {
            agenda.add(new carAgenda(startDate, numberOfDays));
            return true;
        }

        if(startDate.plusDays(numberOfDays).isBefore(agenda.get(0).startDate)) {
            agenda.add(0, new carAgenda(startDate, numberOfDays));
            return true;
        }

        if(agenda.size() > 1) {
            for (int i = 1; i < this.agenda.size(); i++) {
                if(agenda.get(i-1).endDate.isBefore(startDate) && agenda.get(i).startDate.isAfter(endDate)) {
                    agenda.add(i, new carAgenda(startDate, numberOfDays));
                    return true;
                }
            }
        }

        if(agenda.get(agenda.size()-1).endDate.isBefore(startDate)) {
            agenda.addLast(new carAgenda(startDate, numberOfDays));
            return true;
        }

        return false;
    }

    public List<carAgenda> getAvailableDates() {
        long nextAvailableDuration = 0;
        List<carAgenda> availableDates = new LinkedList<>();

        if(agenda.size() == 0) {
            availableDates.add(new carAgenda(LocalDate.now(), -1));
            return availableDates;
        }

        if (agenda.size() > 1) {
            for (int i = 1; i < agenda.size(); i++) {
                LocalDate nextAvailable = agenda.get(i - 1).startDate.plusDays(agenda.get(i - 1).numberOfDays);
                nextAvailableDuration = DAYS.between(nextAvailable, agenda.get(i).startDate);
                availableDates.add(new carAgenda(nextAvailable, nextAvailableDuration));
            }
        }

        availableDates.add(new carAgenda(agenda.getLast().startDate.plusDays(agenda.getLast().numberOfDays), -1));
        return availableDates;
    }


}
