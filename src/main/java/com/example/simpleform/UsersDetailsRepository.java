package com.example.simpleform;



import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersDetailsRepository extends CrudRepository<UsersDetailsModel,Integer> {
    UsersDetailsModel findByUsersModelUserId(Integer usersModelUserId);
}
