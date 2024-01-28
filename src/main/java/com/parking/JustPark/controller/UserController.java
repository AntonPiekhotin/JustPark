package com.parking.JustPark.controller;

import com.parking.JustPark.entity.Parking;
import com.parking.JustPark.entity.ParkingLot;
import com.parking.JustPark.entity.ParkingRating;
import com.parking.JustPark.entity.User;
import com.parking.JustPark.service.ParkingLotService;
import com.parking.JustPark.service.ParkingRatingService;
import com.parking.JustPark.service.ParkingService;
import com.parking.JustPark.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class UserController {

    private final UserService userService;
    private final ParkingService parkingService;
    private final ParkingRatingService parkingRatingService;
    private final ParkingLotService parkingLotService;

    @Autowired
    public UserController(UserService userService, ParkingService parkingService, ParkingRatingService parkingRatingService, ParkingLotService parkingLotService) {
        this.userService = userService;
        this.parkingService = parkingService;
        this.parkingRatingService = parkingRatingService;
        this.parkingLotService = parkingLotService;
    }

    @GetMapping("/login")
    public String loginPage(Principal principal, Model model) {
        User user = userService.getUserByPrincipal(principal);
        model.addAttribute("currentUser", user);
        return "login";
    }

    @GetMapping("/registration")
    public String registrationPage() {
        return "registration";
    }

    @PostMapping("/registration")
    public String createUser(User user, Model model) {
        if (!userService.createUser(user)) {
            model.addAttribute("errorMessage",
                    "User with this email already exists " + user.getEmail());
            return "registration";
        }
        userService.createUser(user);
        return "redirect:login";
    }

    @GetMapping
    public String mainPage(Model model, Principal principal) {
        User user = userService.getUserByPrincipal(principal);
        model.addAttribute("currentUser", user);
        return "index";
    }

    @GetMapping("/parkings/{userId}")
    public String getParkingListByUser(@PathVariable("userId") long userId, Model model) {
        List<Parking> parkingList = userService.parkingList(userId);
        User user = userService.getUserById(userId);
        model.addAttribute("currentUser", user);
        model.addAttribute("parkingList", parkingList);
        return "parkings";
    }

    @GetMapping("/parkings/create/{userId}")
    public String createParking(@PathVariable long userId, Model model, Principal principal) {
//        User user = userService.getUserById(userId);
        User user = userService.getUserByPrincipal(principal);
        Parking parking = new Parking();
        model.addAttribute("parking", parking);
        model.addAttribute("currentUser", user);
        return "createParking";
    }

    @PostMapping("/parkings/create/{userId}")
    public String createParking(@ModelAttribute("parking") Parking parking, @PathVariable("userId") Long userId, Principal principal, Model model) {
        User user = userService.getUserById(userId);
        model.addAttribute("currentUser", user);
        parkingService.createParking(parking, user.getId());
        return "redirect:/parkings/" + userId;
    }

    @GetMapping("/parkings/rating/{parkingId}")
    public String getParkingRates(@PathVariable Long parkingId, Model model, Principal principal) {
        int rating = parkingRatingService.getRatingByParking(parkingId);
        List<ParkingRating> listOfRates = parkingRatingService.listOfRatingsByParking(parkingId);
        model.addAttribute("rating", rating);
        model.addAttribute("listOfRates", listOfRates);

        User user = userService.getUserByPrincipal(principal);
        model.addAttribute("currentUser", user);
        return "parkingRating";
    }

    @GetMapping("parkings/lots/{parkingId}")
    public String getParkingLots(@PathVariable Long parkingId, Model model, Principal principal) {
        User user = userService.getUserByPrincipal(principal);
        model.addAttribute("currentUser", user);
        List<ParkingLot> parkingLotList = parkingLotService.listByParking(parkingId);
        model.addAttribute("parkingLotList", parkingLotList);
        return "parkingLots";
    }

    @GetMapping("/parkings/lots/create/{parkingId}")
    public String createParkingLot(@PathVariable Long parkingId, Model model, Principal principal) {
        ParkingLot parkingLot = new ParkingLot();
        model.addAttribute("parkingLot", parkingLot);
        User user = userService.getUserByPrincipal(principal);
        model.addAttribute("currentUser", user);
        model.addAttribute("parkingId", parkingId);
        return "createParkingLot";
    }

    @PostMapping("/parkings/lots/create/{parkingId}")
    public String createParkingLot(@ModelAttribute("parkingLot") ParkingLot parkingLot,
                                   @PathVariable Long parkingId, Model model, Principal principal) {

        User user = userService.getUserByPrincipal(principal);
        model.addAttribute("currentUser", user);
        parkingLotService.createParkingLot(parkingLot, parkingId);
        return "redirect:/parkings/lots/" + parkingId;
    }

}
