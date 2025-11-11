package com.roushan.uber.services.impl;

import com.roushan.uber.dto.PassengerRequest;
import com.roushan.uber.dto.PassengerResponse;
import com.roushan.uber.entity.Passenger;
import com.roushan.uber.mapper.PassengerMapper;
import com.roushan.uber.repository.PassengerRepository;
import com.roushan.uber.services.PassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository passengerRepository;
    private final PassengerMapper passengerMapper;

    @Override
    @Transactional(readOnly = true)
    public Optional<PassengerResponse> findByEmail( String email ) {
       return passengerRepository.findByEmail(email)
               .map(passengerMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PassengerResponse> findById(Long id) {
       return passengerRepository.findById(id)
               .map(passengerMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PassengerResponse> findAll() {
       return passengerRepository.findAll().stream()
               .map(passengerMapper::toResponse)
               .collect( Collectors.toList());
    }

    @Override
    public PassengerResponse create( PassengerRequest request ) {
        if(passengerRepository.exitsByEmail(request.getEmail())){
            throw new IllegalArgumentException("Passenger with email "+request.getEmail()+" already exits");
        }
        Passenger passenger = passengerMapper.toEntity(request);
        Passenger savedPassenger = passengerRepository.save(passenger);
        return passengerMapper.toResponse(savedPassenger);
    }

    @Override
    public PassengerResponse update(Long id, PassengerRequest request) {
        Passenger passenger = passengerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Passenger not found with id: "+ id));

        if(!passenger.getEmail().equals(request.getEmail()) && passengerRepository.exitsByEmail(request.getEmail())){
            throw new IllegalArgumentException("Passenger with email "+request.getEmail()+" already exits");
        }
        passengerMapper.updateEntity(passenger, request);
        Passenger updatedPassenger = passengerRepository.save(passenger);
        return passengerMapper.toResponse(updatedPassenger);
    }

    @Override
    public void deleteById(Long id) {
       if(!passengerRepository.existsById(id)){
           throw new IllegalArgumentException("Passenger not found with id: "+id);
       }
       passengerRepository.deleteById(id);
    }

}
