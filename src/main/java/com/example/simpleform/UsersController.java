package com.example.simpleform;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;
import java.util.Optional;

@Controller
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("registerRequest", new UsersModel());// attribute"registerRequest" with value UsersModel(all the attribute in the class)
        return "register_page";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        model.addAttribute("loginRequest", new UsersModel());
        return "login_page";
    }

    @GetMapping("/typingSpeedPage")
    public String getTypingSpeedPage() {
        return "typingSpeed_page";
    }


    @PostMapping("/register")
    public String register(@ModelAttribute UsersModel usersModel) { // the model Attribute's "registerRequest" values will be assigned to the userModel.
        System.out.println("register request: " + usersModel);
        UsersModel registeredUser = usersService.registerUser(usersModel.getLogin(), usersModel.getPassword(), usersModel.getEmail());
        if (registeredUser != null) {
            UsersDetailsModel createdUserDetails = usersService.createUserDetails(registeredUser.getUser_id());
            return "redirect:/login";
        } else return "error_page";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute UsersModel usersModel, Model model) {
        System.out.println("login request: " + usersModel);// will print the form input by the toString method
        Optional<UsersModel> authenticated = usersService.authenticate(usersModel.getLogin(), usersModel.getPassword());
        usersModel.setUser_id(usersService.getUserModelByUserLogin(usersModel.getLogin()).getUser_id());
        UsersDetailsModel usersDetailsModel = usersService.getUserDetailsModelByUserId(usersModel.getUser_id());
        if (authenticated.isPresent()) {
            model.addAttribute("userLogin", usersModel.getLogin());
            model.addAttribute("userLastTest", usersDetailsModel.getLastTestSpeed());
            model.addAttribute("userAvgSpeed", String.format("%.1f", usersDetailsModel.getAvgSpeedAllTime()));
            model.addAttribute("userNumOfTests", usersDetailsModel.getNumOfTakenTests());
            getUserLoginAttribute(usersModel.getLogin());
            return "UserTypingSpeed_page";
        } else
            return "error_page";
    }

    @ModelAttribute("userLogin")
    public String getUserLoginAttribute(String userLogin) {
        return userLogin;
    }

    @PostMapping("/updateWPM")
    public ResponseEntity<String> saveWPM(@RequestBody Map<String, String> payload) {
        String WPM = payload.get("WPM");
        String userName = payload.get("userName");
        System.out.println("Received data from client:  " + WPM + "  " + userName);
        //we are making simple app this is absolutely more complex.
        UsersModel usersModel = usersService.getUserModelByUserLogin(userName);
        System.out.println("update request: " + usersModel);
        usersService.updateUserDetails(WPM, usersModel.getUser_id());
        return ResponseEntity.ok("Data received successfully");
    }
//    @PostMapping("/typeSpeed")
//    public String login(@ModelAttribute UsersModel usersModel, Model)

}