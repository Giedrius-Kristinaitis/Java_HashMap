package laborai.lab3GiedriusKristinaitis;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests House objects
 *
 * @author Giedrius Kristinaitis
 */
public class IndividualHouseTest {

    /**
     * Tests House creation, field values, field validation and equality checking
     */
    @Test
    public void testHouseCreationFieldsValidationEquality(){
        House house = new House(5, 10, 180000, 120);

        assertEquals(5, house.getNumberOfRooms());
        assertEquals(10, house.getAge());
        assertEquals(180000, house.getPrice(), 0.0001);
        assertEquals(120, house.getArea(), 0.0001);
        assertEquals(0, house.validate().length());

        house.parse("10 25 500000 250");

        assertEquals(10, house.getNumberOfRooms());
        assertEquals(25, house.getAge());
        assertEquals(500000, house.getPrice(), 0.0001);
        assertEquals(250, house.getArea(), 0.0001);
        assertEquals(0, house.validate().length());

        House house2 = house.create("0 0 0 0");

        assertTrue(house2.validate().length() > 0);

        house2.parse("10 25 500000 250");

        assertTrue(house.equals(house2));
    }

    /**
     * Tests the behavior of House comparators
     */
    @Test
    public void testHouseComparators(){
        House house1 = new House(5, 10, 120000, 75);
        House house2 = new House(4, 25, 100000, 80);
        House house3 = new House(5, 25, 100000, 75);

        assertTrue(House.ROOM_COMPARATOR.compare(house1, house2) > 0);
        assertEquals(0, House.ROOM_COMPARATOR.compare(house1, house3));

        assertTrue(House.AGE_COMPARATOR.compare(house1, house3) < 0);
        assertEquals(0, House.AGE_COMPARATOR.compare(house2, house3));

        // compareTo() in the House class by default compares the price
        assertTrue(house1.compareTo(house3) > 0);
        assertEquals(0, house2.compareTo(house3));

        assertTrue(House.AREA_COMPARATOR.compare(house1, house2) < 0);
        assertEquals(0, House.AREA_COMPARATOR.compare(house1, house3));
    }
}
