package com.roushan.uber.mapper;

import com.roushan.uber.dto.PassengerRequest;
import com.roushan.uber.dto.PassengerResponse;
import com.roushan.uber.entity.Passenger;
import org.springframework.stereotype.Component;

@Component
public class PassengerMapper {

    public Passenger toEntity(PassengerRequest request){
        Passenger passenger = Passenger.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .build();
        return passenger;
    }

    public PassengerResponse toResponse(Passenger passenger){
        PassengerResponse response = PassengerResponse.builder()
                .name(passenger.getName())
                .email(passenger.getEmail())
                .phoneNumber(passenger.getPhoneNumber())
                .createdAt(passenger.getCreationAt())
                .updatedAt(passenger.getUpdateAt())
                .build();
        return response;
    }

    public void updateEntity(Passenger passenger, PassengerRequest request) {
        passenger.setName( request.getName() );
        passenger.setEmail( request.getEmail() );
        passenger.setPhoneNumber( request.getPhoneNumber() );
    }
}
