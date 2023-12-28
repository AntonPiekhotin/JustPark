package com.parking.JustPark.service;

import com.parking.JustPark.entity.Customer;
import com.parking.JustPark.entity.Parking;
import com.parking.JustPark.entity.ParkingLot;
import com.parking.JustPark.repository.CustomerRepository;
import com.parking.JustPark.repository.ParkingLotRepository;
import com.parking.JustPark.repository.ParkingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ParkingLotService {
    private final ParkingLotRepository parkingLotRepository;
    private final ParkingRepository parkingRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public ParkingLotService(ParkingLotRepository parkingLotRepository, ParkingRepository parkingRepository, CustomerRepository customerRepository) {
        this.parkingLotRepository = parkingLotRepository;
        this.parkingRepository = parkingRepository;
        this.customerRepository = customerRepository;
    }

    public boolean createParkingLot(ParkingLot parkingLot, Long parkingId) {
        Parking parking = parkingRepository.findById(parkingId).orElse(null);
        if (parking != null) {
            parkingLot.setParking(parking);
            parkingLot.setIsEmpty(true);
            parkingLotRepository.save(parkingLot);
            log.info("Created parking lot {} in parking {}", parkingLot.getId(), parking.getId());
            return true;
        }
        log.info("Error occurred while creating parking lot {}, probably parkingId {} is wrong",
                parkingLot.getId(), parkingId);
        return false;
    }

    public List<ParkingLot> listByParking(Long parkingId) {
        Parking parking = parkingRepository.findById(parkingId).orElse(null);
        if (parking != null)
            return parkingLotRepository.findAllByParking(parking);
        log.info("No parking lots in this parking {}", parkingId);
        return null;
    }

    /**
     * Метод змінює статус vip паркомісця на протилежний (true або false).
     *
     * @param parkingLotId ідентифікатор паркомісця.
     * @return true, якщо зміна відбулась, false якщо сталась помилка.
     */
    public boolean changeLotStatus(Long parkingLotId) {
        ParkingLot parkingLot = parkingLotRepository.findById(parkingLotId).orElse(null);
        if (parkingLot != null) {
            boolean currentStatus = parkingLot.getIsVip();
            parkingLot.setIsVip(!currentStatus);
            parkingLotRepository.save(parkingLot);
            log.info("Vip status changed to {} no parkingLot {}", currentStatus, parkingLotId);
            return true;
        }
        log.info("Error occurred while changing vip status on parking lot {}, probably parkingLot {} is null",
                parkingLotId, parkingLotId);
        return false;
    }

    /**
     * Цей метод резервує паркомісце користувачем.
     *
     * @param parkingLotId ідентифікатор паркомісця для резервування.
     * @param customerId   ідентифікатор користуваача який резервує місце.
     * @return повертає true якщо операцію виконано, false - якщо операцію не виконано.
     */
    public boolean reserveParkingLot(Long parkingLotId, Long customerId) {
        ParkingLot parkingLot = parkingLotRepository.findById(parkingLotId).orElse(null);
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (parkingLot != null && customer != null) {
            parkingLot.setIsEmpty(false);
            parkingLot.setTakenBy(customer);
            parkingLotRepository.save(parkingLot);
            log.info("ParkingLot {} is taken by customer {}", parkingLotId, customerId);
            return true;
        }
        log.info("Error occurred while reserving parking lot {} by customer {}", parkingLotId, customerId);
        return false;
    }

    /**
     * Метод вивільняє паркомісце, тобто змінює поля isEmpty на true, takenBy на null.
     *
     * @param parkingLotId ідентифікатор паркомісця.
     * @return повертає true якщо операцію виконано, false - якщо операцію не виконано.
     */
    public boolean releaseParkingLot(Long parkingLotId) {
        ParkingLot parkingLot = parkingLotRepository.findById(parkingLotId).orElse(null);
        if (parkingLot == null) {
            log.info("Parking lot with given id {} is null", parkingLotId);
            return false;
        }
        parkingLot.setIsEmpty(true);
        parkingLot.setTakenBy(null);
        parkingLotRepository.save(parkingLot);
        log.info("Parking lot {} is now empty", parkingLotId);
        return true;
    }

    public boolean editLayer(Long parkingLotId, int layer) {
        ParkingLot parkingLot = parkingLotRepository.findById(parkingLotId).orElse(null);
        if (parkingLot == null) {
            log.info("Parking lot with given id {} is null", parkingLotId);
            return false;
        }
        parkingLot.setLayer(layer);
        parkingLotRepository.save(parkingLot);
        log.info("Layer changed to {} in parking lot {}", layer, parkingLotId);
        return true;
    }

    public boolean deleteParkingLot(Long parkingLotId) {
        ParkingLot parkingLot = parkingLotRepository.findById(parkingLotId).orElse(null);
        if (parkingLot == null) {
            log.info("Parking lot with given id {} is null", parkingLotId);
            return false;
        }
        parkingLotRepository.delete(parkingLot);
        log.info("Parking lot {} has been deleted", parkingLotId);
        return true;
    }
}
