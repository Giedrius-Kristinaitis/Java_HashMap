package laborai.lab3GiedriusKristinaitis;

import java.util.Random;

/**
 * Generates House objects
 *
 * @author Giedrius Kristinaitis
 */
public class HouseFactory {

    // Random object to be used for house creation
    private static final Random random = new Random(2018);

    /**
     * Generates an array of the houses
     * @param number - number of houses to generate
     * @return array of houses
     */
    public static House[] generateHouses(int number){
        House[] houses = new House[number];

        for(int i = 0; i < number; i++){
            int numOfRooms = 5 + random.nextInt(45);
            int age = 1 + random.nextInt(49);
            double price = 1000 + random.nextInt(249000);
            double area = 10 + random.nextInt(190);

            houses[i] = new House(numOfRooms, age, price, area);
        }

        return houses;
    }

    /**
     * Generates random keys for House objects
     * @param number how many to generate
     * @return array of generated keys
     */
    public static String[] generateHouseKeys(int number) {
        char[] chars = new char[] {'a', 'b', 'c', '#', 'd', 'e', '$', 'f', 'x', 'z', 'p', ')', '(', 'i', 'u', 'g'};

        String[] keys = new String[number];

        for(int i = 0; i < number; i++) {
            String key = "";

            for(int n = 0; n < 10; n++) {
                key += chars[random.nextInt(16)];
            }

            keys[i] = key;
        }

        return keys;
    }

    /**
     * Generates a house with random field values
     * @return newly generated house
     */
    public static House generateHouse(){
        int numOfRooms = 5 + random.nextInt(45);
        int age = 1 + random.nextInt(49);
        double price = 1000 + random.nextInt(249000);
        double area = 10 + random.nextInt(190);

        return new House(numOfRooms, age, price, area);
    }
}
