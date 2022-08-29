package io.solo.fe;

import java.util.ArrayList;
import java.util.List;

public class CarManufacturerCache {

    public static List<Car> getCarsFromCache() {
        return new ArrayList<Car>() {
            {
                add(Car.newBuilder().setCarId(1).setManufacturerId(1).setMake("Toyota").setModel("RAV4").setPrice(51000.00f).setColor("white").setYear(2014).build());
                add(Car.newBuilder().setCarId(2).setManufacturerId(1).setMake("Toyota").setModel("Yaris").setPrice(35000.00f).setColor("black").setYear(2018).build());
                add(Car.newBuilder().setCarId(3).setManufacturerId(2).setMake("Volkswagen").setModel("Polo").setPrice(49000.00f).setColor("blue").setYear(2016).build());
                add(Car.newBuilder().setCarId(4).setManufacturerId(3).setMake("Tata").setModel("Nexon").setPrice(20000.00f).setColor("blue").setYear(2016).build());
                add(Car.newBuilder().setCarId(5).setManufacturerId(3).setMake("Tata").setModel("Altroz").setPrice(25000.00f).setColor("white").setYear(2019).build());
                add(Car.newBuilder().setCarId(6).setManufacturerId(4).setMake("Tesla").setModel("Model Y").setPrice(100023.50f).setColor("black").setYear(2020).build());
            }
        };
    }

    public static List<Manufacturer> getManufacturersFromCache() {
        return new ArrayList<Manufacturer>() {
            {
                add(Manufacturer.newBuilder().setManufacturerId(1).setCarId(1).setName("Toyota Motor Corporation").setCountry("Japan").build());
                add(Manufacturer.newBuilder().setManufacturerId(2).setName("Volkswagen").setCountry("Germany").build());
                add(Manufacturer.newBuilder().setManufacturerId(3).setName("Tata Motors Limited").setCountry("India").build());
                add(Manufacturer.newBuilder().setManufacturerId(4).setName("Tesla, Inc").setCountry("USA").build());
            }
        };
    }
}
