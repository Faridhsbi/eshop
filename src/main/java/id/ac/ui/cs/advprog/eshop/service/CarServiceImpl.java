package id.ac.ui.cs.advprog.eshop.service;
import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarServiceImpl extends GenericServiceImpl<Car> implements CarService {

    @Autowired
    public CarServiceImpl(CarRepository carRepository) {
        super(carRepository);
    }
}