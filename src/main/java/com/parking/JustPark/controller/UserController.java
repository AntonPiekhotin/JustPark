package com.parking.JustPark.controller;

import com.parking.JustPark.model.dto.UpdateUserDto;
import com.parking.JustPark.model.dto.UserDto;
import com.parking.JustPark.model.entity.User;
import com.parking.JustPark.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('USER')")
public class UserController {

    private final UserService userService;

    @GetMapping("/my")
    public ResponseEntity<?> getMyInfo(@RequestHeader("Authorization") String token) {
        User user = userService.getAuthenticatedUser(token);
        UserDto userToReturn = UserDto.builder()
                .email(user.getEmail())
                .lastName(user.getLastName())
                .firstName(user.getFirstName())
                .phoneNumber(user.getPhoneNumber())
                .country(user.getCountry())
                .registrationDate(user.getRegistrationDate())
                .build();
        return ResponseEntity.ok(userToReturn);
    }

    @PutMapping("/my")
    public ResponseEntity<?> updateMyInfo(@Valid @RequestBody UpdateUserDto updateUserDto, @RequestHeader("Authorization") String token) {
        User user = userService.getAuthenticatedUser(token);
        User updatedUser = userService.updateUser(user, updateUserDto);
        UserDto userToReturn = UserDto.builder()
                .email(updatedUser.getEmail())
                .lastName(updatedUser.getLastName())
                .firstName(updatedUser.getFirstName())
                .phoneNumber(updatedUser.getPhoneNumber())
                .country(updatedUser.getCountry())
                .dateOfBirth(updatedUser.getDateOfBirth())
                .registrationDate(updatedUser.getRegistrationDate())
                .build();
        return ResponseEntity.ok(userToReturn);
    }

    @DeleteMapping("/my")
    public ResponseEntity<?> deleteMyAccount(@RequestHeader("Authorization") String token) {
        User user = userService.getAuthenticatedUser(token);
        userService.deleteUser(user);
        return ResponseEntity.status(200).body("User deleted successfully");
    }


//    @GetMapping("/parkings/{userId}")
//    public String getParkingListByUser(@PathVariable("userId") long userId, Model model) {
//        List<Parking> parkingList = userService.parkingList(userId);
//        User user = userService.getUserById(userId);
//        model.addAttribute("currentUser", user);
//        model.addAttribute("parkingList", parkingList);
//        return "parkings";
//    }
//
//    @GetMapping("/parkings/create/{userId}")
//    public String createParking(@PathVariable long userId, Model model, Principal principal) {
////        User user = userService.getUserById(userId);
//        User user = userService.getUserByPrincipal(principal);
//        Parking parking = new Parking();
//        model.addAttribute("parking", parking);
//        model.addAttribute("currentUser", user);
//        return "createParking";
//    }
//
//    @PostMapping("/parkings/create/{userId}")
//    public String createParking(@ModelAttribute("parking") Parking parking, @PathVariable("userId") Long userId, Principal principal, Model model) {
//        User user = userService.getUserById(userId);
//        model.addAttribute("currentUser", user);
//        parkingService.createParking(parking, user.getId());
//        return "redirect:/parkings/" + userId;
//    }
//
//    @GetMapping("/parkings/rating/{parkingId}")
//    public String getParkingRates(@PathVariable Long parkingId, Model model, Principal principal) {
//        int rating = parkingRatingService.getRatingByParking(parkingId);
//        List<ParkingRating> listOfRates = parkingRatingService.listOfRatingsByParking(parkingId);
//        model.addAttribute("rating", rating);
//        model.addAttribute("listOfRates", listOfRates);
//
//        User user = userService.getUserByPrincipal(principal);
//        model.addAttribute("currentUser", user);
//        return "parkingRating";
//    }
//
//    @GetMapping("parkings/lots/{parkingId}")
//    public String getParkingLots(@PathVariable Long parkingId, Model model, Principal principal) {
//        User user = userService.getUserByPrincipal(principal);
//        model.addAttribute("currentUser", user);
//        List<ParkingLot> parkingLotList = parkingLotService.listByParking(parkingId);
//        model.addAttribute("parkingLotList", parkingLotList);
//        return "parkingLots";
//    }
//
//    @GetMapping("/parkings/lots/create/{parkingId}")
//    public String createParkingLot(@PathVariable Long parkingId, Model model, Principal principal) {
//        ParkingLot parkingLot = new ParkingLot();
//        model.addAttribute("parkingLot", parkingLot);
//        User user = userService.getUserByPrincipal(principal);
//        model.addAttribute("currentUser", user);
//        model.addAttribute("parkingId", parkingId);
//        return "createParkingLot";
//    }
//
//    @PostMapping("/parkings/lots/create/{parkingId}")
//    public String createParkingLot(@ModelAttribute("parkingLot") ParkingLot parkingLot,
//                                   @PathVariable Long parkingId, Model model, Principal principal) {
//
//        User user = userService.getUserByPrincipal(principal);
//        model.addAttribute("currentUser", user);
//        parkingLotService.createParkingLot(parkingLot, parkingId);
//        return "redirect:/parkings/lots/" + parkingId;
//    }
//
}
