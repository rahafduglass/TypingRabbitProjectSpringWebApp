package com.example.simpleform;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersService {
    private final UsersRepository usersRepository;
    private final UsersDetailsRepository usersDetailsRepository;
    public UsersService(UsersRepository usersRepository,
                        UsersDetailsRepository usersDetailsRepository){
        this.usersRepository=usersRepository;
        this.usersDetailsRepository= usersDetailsRepository;
    }

    @Transactional
    public UsersModel registerUser(String login, String password, String email){
        if(login != null && password != null){// we already checked this in the frontEnd using Required attribute
            if(usersRepository.findFirstByLogin(login).isPresent()){
                System.out.println("Duplicated login");
                return null;
            }
            UsersModel usersModel = new UsersModel();
            usersModel.setLogin(login);
            usersModel.setPassword(password);
            usersModel.setEmail(email);

            return  usersRepository.save(usersModel); // to insert a new row in the database
        }else
            return null;
    }

    public Optional<UsersModel> authenticate(String login, String password){
        return usersRepository.findByLoginAndPassword(login,password);
    }
    public UsersModel getUserModelByUserLogin(String userLogin){
        return usersRepository.findByLogin(userLogin);
    }
    public UsersDetailsModel getUserDetailsModelByUserId(int user_id){
        return usersDetailsRepository.findByUsersModelUserId(user_id);
    }

    public UsersDetailsModel updateUserDetails(String WPM, Integer user_id){
        UsersDetailsModel usersDetailsModel=usersDetailsRepository.findByUsersModelUserId(user_id);

        if(usersDetailsModel!= null){
            System.out.println("update user details:  "+usersDetailsModel.toString());
            int lastTestSpeed=Integer.parseInt(WPM);
            double currentAvgSpeed=usersDetailsModel.getAvgSpeedAllTime();
            int currentNumOfTests= usersDetailsModel.getNumOfTakenTests();
            int newNumOfTests=currentNumOfTests+1;
            double newAvgSpeed=((currentAvgSpeed*currentNumOfTests)+lastTestSpeed)/( newNumOfTests);
            usersDetailsModel.setAvgSpeedAllTime(newAvgSpeed);
            usersDetailsModel.setLastTestSpeed(lastTestSpeed);
            usersDetailsModel.setNumOfTakenTests(newNumOfTests);
            return usersDetailsRepository.save(usersDetailsModel);
        }
        else return null;
    }


    public UsersDetailsModel createUserDetails(Integer userId) {
        UsersDetailsModel usersDetailsModel=new UsersDetailsModel();
        usersDetailsModel.setUsersModelUserId(userId);
        usersDetailsModel.setLastTestSpeed(0);
        usersDetailsModel.setAvgSpeedAllTime(0.0);
        usersDetailsModel.setNumOfTakenTests(0);
        return  usersDetailsRepository.save(usersDetailsModel);
    }
}
