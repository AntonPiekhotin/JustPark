package com.parking.JustPark.service;

import com.parking.JustPark.entity.Parking;
import com.parking.JustPark.entity.ParkingRating;
import com.parking.JustPark.repository.ParkingRatingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ParkingRatingService {
    private final ParkingRatingRepository parkingRatingRepository;

    @Autowired
    public ParkingRatingService(ParkingRatingRepository parkingRatingRepository) {
        this.parkingRatingRepository = parkingRatingRepository;
    }

    /**
     * Метод повертає всі оцінки по надаому паркінгу.
     * @param parking паркінг, оцінки якого треба знайти.
     * @return список оцінок.
     */
    public List<ParkingRating> listOfRatingsByParking(Parking parking){
        if(parkingRatingRepository.existsByParking(parking))
            return parkingRatingRepository.findAllByParking(parking);
        return null;
    }

    /**
     * Метод підраховує рейтинг паркінгу шляхом знаходження середнього значення всіх оцінок цього паркінгу.
     * @param parking паркінг, рейтинг якого треба обрахувати.
     * @return середнє значення всіх оцінок паркінгу.
     */
    public int getRatingByParking(Parking parking) {
        if (!parkingRatingRepository.existsByParking(parking)) {
            return 0;
        }
        List<ParkingRating> ratings = parkingRatingRepository.findAllByParking(parking);
        List<Integer> ratingValues = ratings.stream()
                .map(ParkingRating::getRating)
                .toList();

        int finalRating = -1;
        if (!ratingValues.isEmpty()) {
            for (Integer ratingValue : ratingValues) {
                finalRating += ratingValue;
            }
        }
        return finalRating;
    }
}
