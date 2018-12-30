package laborai.lab3GiedriusKristinaitis;

import laborai.studijosktu.KTUable;
import laborai.studijosktu.Ks;

import java.util.Comparator;
import java.util.Locale;
import java.util.Objects;

/**
 * House class
 *
 * @author Giedrius Kristinaitis
 */
public class House implements KTUable, Comparable<House> {

    // some constants
    private static final double MINIMUM_PRICE = 1000;
    private static final double MINIMUM_AREA = 9;

    private int numberOfRooms; // how many rooms the house has
    private int age;           // age of the house
    private double price;      // price of the house
    private double area;       // area of the house in square meters

    /**
     * Empty class constructor
     */
    public House(){}

    /**
     * Class constructor
     * @param numberOfRooms number of rooms
     * @param age age of the house
     * @param price price of the house
     * @param area area of the house in square meters
     */
    public House(int numberOfRooms, int age, double price, double area){
        this.numberOfRooms = numberOfRooms;
        this.age = age;
        this.price = price;
        this.area = area;
    }

    /**
     * Compares this house to another house based on price
     * @param other House to compare to
     * @return 0 if prices are equal, 1 if this house is more expensive than the other, -1 if the other is more expensive
     */
    @Override
    public int compareTo(House other){
        // compare by the price
        if(price < other.getPrice()){
            return -1;
        }else if(price > other.getPrice()){
            return 1;
        }

        return 0;
    }

    /**
     * Creates a new house
     * @param line string containing data required to initialize house fields
     * @return new House object
     */
    @Override
    public House create(String line){
        House house = new House();
        house.parse(line);
        return house;
    }

    /**
     * Checks if this house is equal to another house
     * @param other house to compare to
     * @return true if both houses are equal, false otherwise
     */
    @Override
    public boolean equals(Object other){
        if(other == null){
            return false;
        }

        if(!(other instanceof House)){
            return false;
        }

        House house = (House) other;

        if(other == this){
            return true;
        }

        if(numberOfRooms == house.getNumberOfRooms() && age == house.getAge()
                && price == house.getPrice() && area == house.getArea()){
            return true;
        }

        return false;
    }

    /**
     * Gets the hash code of this house
     * @return hash code as integer
     */
    @Override
    public int hashCode(){
        return Objects.hash(numberOfRooms, age, price, area);
    }

    /**
     * Takes house data from the specified string and changes field values.
     * Line format: numberOfRooms(int) age(int) price(double) area(double)
     * @param line string containing house data
     */
    @Override
    public void parse(String line){
        String[] data = line.split(" ");

        if(data.length != 4){
            Ks.ern("Per mažai namo duomenų laukų: " + line);
        }

        try{
            numberOfRooms = Integer.parseInt(data[0]);
            age = Integer.parseInt(data[1]);
            price = Double.parseDouble(data[2]);
            area = Double.parseDouble(data[3]);
        }catch(Exception ex){
            Ks.ern("Blogas namo eilutės formatas: " + line);
        }
    }

    /**
     * Checks if all house data fields are valid
     * @return message with information about fields if there is something wrong, if everything's right, empty string is returned
     */
    @Override
    public String validate(){
        String message = "";

        if(numberOfRooms < 1){
            message = "Kambarių skaičius turi būti daugiau už 0.";
        }

        if(age < 0){
            message += " Namo amžius turi būti >= 0.";
        }

        if(price < MINIMUM_PRICE){
            message += " Per maža namo kaina (mažiausia gali būti " + MINIMUM_PRICE + ").";
        }

        if(area < MINIMUM_AREA){
            message += " Per mažas namo plotas (mažiausias gali būti " + MINIMUM_AREA + ").";
        }

        return message;
    }

    /**
     * Returns a string containing all data fields of the house
     * @return a formatted string with house data
     */
    @Override
    public String toString(){
        return String.format("Kambarių sk.: %-10d Amžius: %-10d Kaina: %-15.2f Plotas: %-10.2f %s",
                numberOfRooms, age, price, area, validate());
    }


    // ******** GETTERS AND SETTERS ******** //
    public int getNumberOfRooms() { return numberOfRooms; }
    public int getAge() { return age; }
    public double getPrice() { return price; }
    public double getArea() { return area; }

    public void setNumberOfRooms(int numberOfRooms) { this.numberOfRooms = numberOfRooms; }
    public void setAge(int age) { this.age = age; }
    public void setPrice(double price) { this.price = price; }
    public void setArea(double area) { this.area = area; }


    // ******** COMPARATORS ******** //
    /**
     * Compares two houses by their age
     */
    public static final Comparator<House> AGE_COMPARATOR = (a, b) -> {
        int ageA = a.getAge();
        int ageB = b.getAge();

        if(ageA < ageB) { return -1; }
        else if(ageA > ageB) { return 1; }

        return 0;
    };

    /**
     * Compares two houses by their number of rooms
     */
    public static final Comparator<House> ROOM_COMPARATOR = (a, b) -> {
        int roomsA = a.getNumberOfRooms();
        int roomsB = b.getNumberOfRooms();

        if(roomsA < roomsB) { return -1; }
        else if(roomsA > roomsB) { return 1; }

        return 0;
    };

    /**
     * Compares two houses by their area
     */
    public static final Comparator<House> AREA_COMPARATOR = (a, b) -> {
        double areaA = a.getArea();
        double areaB = b.getArea();

        if(areaA < areaB) { return -1; }
        else if(areaA > areaB) { return 1; }

        return 0;
    };


    // ******** HOUSE TEST ******** //
    public static void main(String[] args){
        Locale.setDefault(new Locale("LT"));

        House house1 = new House(5, 10, 45000, 60);
        House house2 = new House(6, 5, 79000, 100);
        House house3 = new House();
        House house4 = new House();

        house3.parse("3 2 25000 45");
        house4.parse("10 1 150000 150");

        Ks.oun(house1);
        Ks.oun(house2);
        Ks.oun(house3);
        Ks.oun(house4);
    }
}
