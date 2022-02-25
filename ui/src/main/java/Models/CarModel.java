package Models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarModel {

    private String color, model, type, location;
    private int year, price, carid;

    public CarModel(int carid, String color, String model, String type, String location, int year, int price) {
        this.carid = carid;
        this.color = color;
        this.model = model;
        this.type = type;
        this.location = location;
        this.year = year;
        this.price = price;
    }

}
