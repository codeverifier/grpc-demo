package io.solo.fe.grpc.server;

import com.google.protobuf.Empty;
import io.solo.fe.Car;
import io.solo.fe.CarManufacturerCache;
import io.grpc.stub.StreamObserver;
import io.solo.fe.CarCatalogServiceGrpc;
import io.solo.fe.Manufacturer;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.ArrayList;
import java.util.List;

@GrpcService
public class CarManufacturerBackendService extends CarCatalogServiceGrpc.CarCatalogServiceImplBase {

    @Override
    public void getManufacturer(Manufacturer request, StreamObserver<Manufacturer> responseObserver) {
        CarManufacturerCache.getManufacturersFromCache()
                .stream()
                .filter(manufacturer -> manufacturer.getManufacturerId() == request.getManufacturerId())
                .findFirst()
                .ifPresent(responseObserver::onNext);
        responseObserver.onCompleted();
    }

    @Override
    public void getCarsByManufacturer(Manufacturer request, StreamObserver<Car> responseObserver) {
        CarManufacturerCache.getCarsFromCache()
                .stream()
                .filter(car -> car.getManufacturerId() == request.getManufacturerId())
                .forEach(responseObserver::onNext);
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<Car> getTheMostExpensiveCar(StreamObserver<Car> responseObserver) {
        return new StreamObserver<Car>() {
            Car expensiveCar = null;
            float trackPrice = 0;

            @Override
            public void onNext(Car car) {
                if (car.getPrice() > trackPrice) {
                    trackPrice = car.getPrice();
                    expensiveCar = car;
                }
            }

            @Override
            public void onError(Throwable throwable) {
                responseObserver.onError(throwable);
            }

            @Override
            public void onCompleted() {
                responseObserver.onNext(expensiveCar);
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public StreamObserver<Car> getCarsByCountry(StreamObserver<Car> responseObserver) {
        return new StreamObserver<Car>() {
            List<Car> carList = new ArrayList<>();

            @Override
            public void onNext(Car car) {
                CarManufacturerCache.getCarsFromCache()
                        .stream()
                        .filter(carFromCache -> carFromCache.getManufacturerId() == car.getManufacturerId())
                        .forEach(carList::add);
            }

            @Override
            public void onError(Throwable throwable) {
                responseObserver.onError(throwable);
            }

            @Override
            public void onCompleted() {
                carList.forEach(responseObserver::onNext);
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public void getAllCars(Empty empty, StreamObserver<Car> responseObserver) {
        CarManufacturerCache.getCarsFromCache()
                .stream()
                .forEach(responseObserver::onNext);
        responseObserver.onCompleted();
    }

    @Override
    public void getAllManufacturers(Empty empty, StreamObserver<Manufacturer> responseObserver) {
        CarManufacturerCache.getManufacturersFromCache()
                .stream()
                .forEach(responseObserver::onNext);
        responseObserver.onCompleted();
    }
}
