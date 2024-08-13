package com.justpark.service;

import com.justpark.model.entity.Parking;
import com.justpark.model.entity.ParkingRating;
import com.justpark.repository.ParkingRatingRepository;
import com.justpark.repository.ParkingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ParkingRatingService {

    private final ParkingRatingRepository parkingRatingRepository;
    private final ParkingRepository parkingRepository;

    /**
     * Метод повертає всі оцінки по надаому паркінгу.
     *
     * @param parkingId ідентифікатор паркінгу, оцінки якого треба знайти.
     * @return список оцінок.
     */
    public List<ParkingRating> listOfRatingsByParking(Long parkingId) {
        Parking parking = parkingRepository.findById(parkingId).orElse(null);
        if (parking == null) {
            log.info("Error occurred while getting rating list in parking {}", parkingId);
            return null;
        }
        return parkingRatingRepository.findAllByParking(parking);
    }

    /**
     * Метод підраховує рейтинг паркінгу шляхом знаходження середнього значення всіх оцінок цього паркінгу.
     *
     * @param parkingId ідентифікатор паркінгу, рейтинг якого треба обрахувати.
     * @return середнє значення всіх оцінок паркінгу.
     */
    public Double getRatingByParking(Long parkingId) {
        Parking parking = parkingRepository.findById(parkingId).orElse(null);
        if (parking == null) {
            log.info("Error occurred while getting rating in parking {}", parkingId);
            return -1.0;
        }
        if (!parkingRatingRepository.existsByParking(parking)) {
            return 0.0;
        }
        return parkingRatingRepository.findAllByParking(parking).stream()
                .mapToInt(ParkingRating::getRatingNumber)
                .average()
                .orElse(0.0);
    }
}
