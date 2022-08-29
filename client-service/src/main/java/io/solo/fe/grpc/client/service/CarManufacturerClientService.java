package io.solo.fe.grpc.client.service;

import com.google.protobuf.Empty;
import io.solo.fe.Car;
import io.solo.fe.CarCatalogServiceGrpc;
import io.solo.fe.CarManufacturerCache;
import com.google.protobuf.Descriptors;
import io.grpc.stub.StreamObserver;
import io.solo.fe.Manufacturer;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Service
public class CarManufacturerClientService {
    @GrpcClient("grpc-backend-service")
    CarCatalogServiceGrpc.CarCatalogServiceBlockingStub synchronousClient;

    @GrpcClient("grpc-backend-service")
    CarCatalogServiceGrpc.CarCatalogServiceStub asynchronousClient;

    public Map<Descriptors.FieldDescriptor, Object> getManufacturer(int manufacturerId) {
        Manufacturer request = Manufacturer.newBuilder().setManufacturerId(manufacturerId).build();
        Manufacturer response = synchronousClient.getManufacturer(request);
        return response.getAllFields();
    }

    public List<Map<Descriptors.FieldDescriptor, Object>> getCarsByManufacturer(int manufacturerId) throws InterruptedException {
        final Manufacturer manufacturerRequest = Manufacturer.newBuilder().setManufacturerId(manufacturerId).build();
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final List<Map<Descriptors.FieldDescriptor, Object>> response = new ArrayList<>();
        asynchronousClient.getCarsByManufacturer(manufacturerRequest, new StreamObserver<Car>() {
            @Override
            public void onNext(Car car) {
                response.add(car.getAllFields());
            }

            @Override
            public void onError(Throwable throwable) {
                countDownLatch.countDown();
            }

            @Override
            public void onCompleted() {
                countDownLatch.countDown();
            }
        });
        boolean await = countDownLatch.await(1, TimeUnit.MINUTES);
        return await ? response : Collections.emptyList();
    }

    public Map<String, Map<Descriptors.FieldDescriptor, Object>> getTheMostExpensiveCar() throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final Map<String, Map<Descriptors.FieldDescriptor, Object>> response = new HashMap<>();
        StreamObserver<Car> responseObserver = asynchronousClient.getTheMostExpensiveCar(new StreamObserver<Car>() {
            @Override
            public void onNext(Car car) {
                response.put("The Most Expensive Car", car.getAllFields());
            }

            @Override
            public void onError(Throwable throwable) {
                countDownLatch.countDown();
            }

            @Override
            public void onCompleted() {
                countDownLatch.countDown();
            }
        });
        CarManufacturerCache.getCarsFromCache().forEach(responseObserver::onNext);
        responseObserver.onCompleted();
        boolean await = countDownLatch.await(1, TimeUnit.MINUTES);
        return await ? response : Collections.emptyMap();
    }

    public List<Map<Descriptors.FieldDescriptor, Object>> getCarsByCountry(String country) throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final List<Map<Descriptors.FieldDescriptor, Object>> response = new ArrayList<>();
        StreamObserver<Car> responseObserver = asynchronousClient.getCarsByCountry(new StreamObserver<Car>() {
            @Override
            public void onNext(Car car) {
                response.add(car.getAllFields());
            }

            @Override
            public void onError(Throwable throwable) {
                countDownLatch.countDown();
            }

            @Override
            public void onCompleted() {
                countDownLatch.countDown();
            }
        });
        CarManufacturerCache.getManufacturersFromCache()
                .stream()
                .filter(manufacturer -> manufacturer.getCountry().equalsIgnoreCase(country))
                .forEach(manufacturer -> responseObserver.onNext(Car.newBuilder().setManufacturerId(manufacturer.getManufacturerId()).build()));
        responseObserver.onCompleted();
        boolean await = countDownLatch.await(1, TimeUnit.MINUTES);
        return await ? response : Collections.emptyList();
    }

    public List<Map<Descriptors.FieldDescriptor, Object>> getAllCars() throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final List<Map<Descriptors.FieldDescriptor, Object>> response = new ArrayList<>();
        asynchronousClient.getAllCars(Empty.getDefaultInstance(), new StreamObserver<Car>() {
            @Override
            public void onNext(Car car) {
                response.add(car.getAllFields());
            }

            @Override
            public void onError(Throwable throwable) {
                countDownLatch.countDown();
            }

            @Override
            public void onCompleted() {
                countDownLatch.countDown();
            }
        });
        boolean await = countDownLatch.await(1, TimeUnit.MINUTES);
        return await ? response : Collections.emptyList();
    }

    public List<Map<Descriptors.FieldDescriptor, Object>> getAllManufacturers() throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final List<Map<Descriptors.FieldDescriptor, Object>> response = new ArrayList<>();
        asynchronousClient.getAllManufacturers(Empty.getDefaultInstance(), new StreamObserver<Manufacturer>() {
            @Override
            public void onNext(Manufacturer manufacturer) {
                response.add(manufacturer.getAllFields());
            }

            @Override
            public void onError(Throwable throwable) {
                countDownLatch.countDown();
            }

            @Override
            public void onCompleted() {
                countDownLatch.countDown();
            }
        });
        boolean await = countDownLatch.await(1, TimeUnit.MINUTES);
        return await ? response : Collections.emptyList();
    }
}
