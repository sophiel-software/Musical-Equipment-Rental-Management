package com.example.merm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.merm.entity.Booking;
import com.example.merm.entity.Equipment;
import com.example.merm.repository.BookingRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
	
    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    public Booking save(Booking booking) {
        if (booking.getStartDate().isAfter(booking.getEndDate())) {
            throw new IllegalArgumentException("Start date must be before end date");
        }

        Optional<Equipment> equipment = Optional.of(booking.getEquipment());
        if (equipment.isPresent() && bookingRepository.existsByEquipmentAndStartDateBetween(equipment.get(), booking.getStartDate(), booking.getEndDate())) {
            throw new IllegalArgumentException("Overlapping booking exists for the given date range");
        }

        return bookingRepository.save(booking);
    }

    public Optional<Booking> findById(Long id) {
        return bookingRepository.findById(id);
    }

    public void deleteById(Long id) {
        bookingRepository.deleteById(id);
    }
}

