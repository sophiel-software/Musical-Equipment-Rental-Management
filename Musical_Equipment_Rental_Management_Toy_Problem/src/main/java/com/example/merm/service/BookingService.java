package com.example.merm.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.merm.entity.Booking;
import com.example.merm.entity.Equipment;
import com.example.merm.repository.BookingRepository;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
	
	@Autowired
    private BookingRepository bookingRepository;

    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    @Transactional
    public Booking save(Booking booking) {
        if (booking.getStartDate().isAfter(booking.getEndDate())) {
            throw new IllegalArgumentException("Start date must be before end date");
        }

        Optional<Equipment> equipment = Optional.of(booking.getEquipment());
        if (equipment.isPresent() && bookingRepository.existsByEquipmentAndStartDateBetween(equipment.get(), booking.getStartDate(), booking.getEndDate())) {
            throw new IllegalArgumentException("Overlapping booking exists for the given date range");
        }

        Logger log = LogManager.getLogger();
        log.info("Booking = " + booking.toString());
        Booking output = new Booking(); 
        try {
        	output = bookingRepository.save(booking);
            if (output != null) {
                log.info("Saved Booking = " + output.toString());
            } else {
                log.error("Failed to save Booking. Output was null.");
            }
        } catch (Exception e) {
            log.error("An exception occurred while saving the booking: ", e);
            throw e;
        }
        return output;
    }

    public Optional<Booking> findById(Long id) {
        return bookingRepository.findById(id);
    }

    public void deleteById(Long id) {
        bookingRepository.deleteById(id);
    }
}

