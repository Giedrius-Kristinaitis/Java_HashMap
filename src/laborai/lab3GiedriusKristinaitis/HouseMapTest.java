package laborai.lab3GiedriusKristinaitis;

import laborai.studijosktu.MapADT;
import laborai.studijosktu.MapKTU;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests Maps of House objects
 *
 * @author Giedrius Kristinaitis
 */
public class HouseMapTest {

    // maps
    private MapADT<String, House> map1 = new MapKTUOA<>();
    private MapADT<String, House> map2 = new MapKTU<>();

    /**
     * Performs required initialization
     */
    @Before
    public void setup() {
        House[] houses = new House[] {
            new House(4, 10, 40000, 120),
            new House(3, 15, 12000, 56),
            new House(6, 30, 50000, 140),
            new House(3, 25, 10000, 60),
            new House(5, 50, 15000, 98),
            new House(7, 2, 125000, 135),
            new House(10, 1, 1000000, 250),
            new House(4, 18, 20000, 62),
            new House(1, 20, 2500, 20),
            new House(2, 10, 8000, 35)
        };

        String[] keys = new String[] {
            "#10", "#222", "#31", "#45", "#50",
            "#61", "#707", "#18", "#94", "#15"
        };

        for (int n = 0; n < houses.length; n++) {
            map1.put(keys[n], houses[n]);
            map2.put(keys[n], houses[n]);
        }
    }

    /**
     * Tests MapKTU class
     */
    @Test
    public void testMapKTU() {
        MapKTU<String, House> map = (MapKTU<String, House>) map2;

        // test containsValue()
        House house = new House(1, 5, 1000, 10);
        assertFalse(map.containsValue(house));

        house = house.create("2 10 8000 35");
        assertTrue(map.containsValue(house));

        // test averageChainSize()
        assertTrue(map.averageChainSize() > 0);

        // test numberOfEmpties()
        assertTrue(map.numberOfEmpties() > 0);

        // test replace()
        house.parse("20 20 2000000 200");

        assertTrue(map.replace("#10", new House(4, 10, 40000, 120), house));
        assertFalse(map.replace("AAA", new House(4, 10, 40000, 120), house));

        // test putIfAbsent()
        map.remove("#10");

        assertEquals(null, map.putIfAbsent("#10", house));
        assertEquals(house, map.putIfAbsent("#10", house));

        testMap(map2);
    }

    /**
     * Tests MapKTUOA class
     */
    @Test
    public void testMapKTUOA() {
        testMap(map1);
    }

    /**
     * Tests an instance of MapADT
     * @param map map to test
     */
    private void testMap(MapADT<String, House> map) {
        if (map == null || map.size() == 0) {
            throw new IllegalArgumentException("Map cannot be null or empty at tesMap(MapADT<?, ?> map)");
        }

        // test isEmpty()
        assertFalse(map.isEmpty());

        // test contains()
        assertTrue(map.contains("#10"));

        // test size()
        assertEquals(10, map.size());

        // test put()
        House house = new House(5, 50, 50000, 50);

        assertEquals(house, map.put("#753", house));

        // test get()
        assertEquals(house, map.get("#753"));

        // test remove()
        int oldSize = map.size();

        assertEquals(house, map.remove("#753"));
        assertEquals(oldSize - 1, map.size());

        // test clear()
        map.clear();

        assertEquals(0, map.size());
    }
}
