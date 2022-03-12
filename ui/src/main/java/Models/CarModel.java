package Models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarModel {

    private String color, model, type, location;
    private int year, price, carId;

    //constructor
    public CarModel(int carId, String color, String model, String type, String location, int year, int price) {
        this.carId = carId;
        this.color = color;
        this.model = model;
        this.type = type;
        this.location = location;
        this.year = year;
        this.price = price;
    }

}
