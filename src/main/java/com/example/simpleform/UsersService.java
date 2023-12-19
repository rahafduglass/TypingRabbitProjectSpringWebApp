package com.example.simpleform;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersService {
    private final UsersRepository usersRepository;
    public UsersService(UsersRepository usersRepository){
        this.usersRepository=usersRepository;
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

}
