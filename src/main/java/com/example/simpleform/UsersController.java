package com.example.simpleform;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService){
        this.usersService = usersService;
    }
    @GetMapping("/register")
    public String getRegisterPage(Model model){
        model.addAttribute("registerRequest",new UsersModel());// attribute"registerRequest" with value UsersModel(all the attribute in the class)
        return "register_page";
    }
    @GetMapping("/login")
    public String getLoginPage(Model model){
        model.addAttribute("loginRequest",new UsersModel());
        return"login_page";
    }
    @PostMapping("/register")
    public String register(@ModelAttribute UsersModel usersModel){ // the model Attribute's "registerRequest" values will be assigned to the userModel.
        System.out.println("register request: " + usersModel);
        UsersModel registeredUser= usersService.registerUser(usersModel.getLogin(),usersModel.getPassword(),usersModel.getEmail());
        return registeredUser==null?"error_page" :"redirect:/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute UsersModel usersModel,Model model){
        System.out.println("login request: " + usersModel);// will print the form input by the toString method
        Optional<UsersModel> authenticated=usersService.authenticate(usersModel.getLogin(),usersModel.getPassword());
        if(authenticated.isPresent()){
            model.addAttribute("userLogin",usersModel.getLogin());
            //
            return "personal_page";
        }else
            return "error_page" ;
    }
}
