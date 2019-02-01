import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class testRentalService {
    String message = "Hello World";
    RentalService rentalService;

    @Before
    public void createRentalService(){
        System.out.println("Testing rental service. Today's date is " + LocalDate.now().toString());
        rentalService = new RentalService();
        rentalService.addVehicle("Truck");
        rentalService.addVehicle("Truck");
        rentalService.addVehicle("Car");
        rentalService.addVehicle("Van");
        rentalService.addVehicle("Van");
    }

    @BeforeAll
    public void runOnceBeforeClass() {
        rentalService.listVehicles();
    }

    @Test
    public void testBookYesterday() {
        assertEquals(false, rentalService.bookVehicle(LocalDate.now().minusDays(1L), 5, 1));
    }

    @Test
    public void testBookOneDate() {
        assertEquals(true, rentalService.bookVehicle(LocalDate.now(), 5, 1));
    }

    @Test
    /**
     * Book two blocks of time for one vehicle
     */
    public void testBookTwoDates() {
        assertEquals(true, rentalService.bookVehicle(LocalDate.now(), 5, 1));
        assertEquals(true, rentalService.bookVehicle(LocalDate.now().plusDays(6), 5, 1));
    }

    @Test
    /**
     * Book two blocks of time for one vehicle. Then attempt to book one time that is already reserved
     */
    public void testBookTwoDatesAndOneConflictingDate() {
        assertEquals(true, rentalService.bookVehicle(LocalDate.now(), 5, 1));
        assertEquals(true, rentalService.bookVehicle(LocalDate.now().plusDays(6), 5, 1));
        assertEquals(false, rentalService.bookVehicle(LocalDate.now().plusDays(3), 5, 1));
    }

    @Test
    /**
     * Book two blocks of time for multiple vehicles
     */
    public void testBookDatesForMultipleVehicles() {
        assertEquals(true, rentalService.bookVehicle(LocalDate.now(), 5, 1));
        assertEquals(true, rentalService.bookVehicle(LocalDate.now().plusDays(6), 5, 1));
        assertEquals(true, rentalService.bookVehicle(LocalDate.now().plusDays(3), 5, 2));
        assertEquals(true, rentalService.bookVehicle(LocalDate.now().plusDays(9), 5, 2));
    }

    @Test
    /**
     * Book time for invalid ID
     */
    public void testBookInvalidId() {
        assertEquals(false, rentalService.bookVehicle(LocalDate.now(), 5, -1));
    }

}
