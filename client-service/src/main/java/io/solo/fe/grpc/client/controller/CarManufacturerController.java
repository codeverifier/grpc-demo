package io.solo.fe.grpc.client.controller;

import io.solo.fe.grpc.client.service.CarManufacturerClientService;
import com.google.protobuf.Descriptors;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class CarManufacturerController {
    final CarManufacturerClientService carManufacturerClientService;

    @GetMapping("/manufacturer/{id}")
    public Map<Descriptors.FieldDescriptor, Object> getManufacturer(@PathVariable String id) {
        return carManufacturerClientService.getManufacturer(Integer.parseInt(id));
    }

    @GetMapping("/car/{manufacturer_id}")
    public List<Map<Descriptors.FieldDescriptor, Object>> getCarsByManufacturer(@PathVariable String manufacturer_id) throws InterruptedException {
        return carManufacturerClientService.getCarsByManufacturer(Integer.parseInt(manufacturer_id));
    }

    @GetMapping("/car/manufacturer/{country}")
    public List<Map<Descriptors.FieldDescriptor, Object>> getCarsByCountry(@PathVariable String country) throws InterruptedException {
        return carManufacturerClientService.getCarsByCountry(country);
    }

    @GetMapping("/car")
    public Map<String, Map<Descriptors.FieldDescriptor, Object>> getTheMostExpensiveCar() throws InterruptedException {
        return carManufacturerClientService.getTheMostExpensiveCar();
    }

    @GetMapping("/cars")
    public List<Map<Descriptors.FieldDescriptor, Object>> getAllCars() throws InterruptedException {
        return carManufacturerClientService.getAllCars();
    }

    @GetMapping("/manufacturers")
    public List<Map<Descriptors.FieldDescriptor, Object>> getAllManufacturers() throws InterruptedException {
        return carManufacturerClientService.getAllManufacturers();
    }
}
