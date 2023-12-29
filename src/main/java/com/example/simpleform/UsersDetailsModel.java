package com.example.simpleform;

import jakarta.persistence.*;

@Entity
@Table(name="users_details_table" ,schema="test_db" )
public class UsersDetailsModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer user_details_id;

    private Double avgSpeedAllTime;
    private Integer usersModelUserId;// the foreign key
    private Integer lastTestSpeed;
    private Integer numOfTakenTests;


    @Override
    public String toString() {
        return "UsersDetailsModel{" +
                ", avgSpeedAllTime=" + avgSpeedAllTime +
                ", user_id=" + usersModelUserId +
                ", lastTestSpeed=" + lastTestSpeed +
                ", numOfTakenTests=" + numOfTakenTests +
                '}';
    }




    public Double getAvgSpeedAllTime() {
        return avgSpeedAllTime;
    }

    public void setAvgSpeedAllTime(Double avgSpeedAllTime) {
        this.avgSpeedAllTime = avgSpeedAllTime;
    }

    public Integer getUsersModelUserId() {
        return usersModelUserId;
    }

    public void setUsersModelUserId(Integer user_id) {
        this.usersModelUserId = user_id;
    }

    public Integer getLastTestSpeed() {
        return lastTestSpeed;
    }

    public void setLastTestSpeed(Integer lastTestSpeed) {
        this.lastTestSpeed = lastTestSpeed;
    }

    public Integer getNumOfTakenTests() {
        return numOfTakenTests;
    }

    public void setNumOfTakenTests(Integer numOfTakenTests) {
        this.numOfTakenTests = numOfTakenTests;
    }
}
