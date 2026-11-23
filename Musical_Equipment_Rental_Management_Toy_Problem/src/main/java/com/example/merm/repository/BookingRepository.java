package com.example.merm.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.merm.entity.Booking;
import com.example.merm.entity.Equipment;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    boolean existsByEquipmentAndStartDateBetween(Equipment equipment, LocalDate startDate, LocalDate endDate);
}


