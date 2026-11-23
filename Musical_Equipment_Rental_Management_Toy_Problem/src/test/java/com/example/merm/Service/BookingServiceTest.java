package com.example.merm.Service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.merm.entity.Booking;
import com.example.merm.entity.Equipment;
import com.example.merm.repository.BookingRepository;
import com.example.merm.service.BookingService;

import static org.mockito.Mockito.*;
import java.time.LocalDate;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private BookingService bookingService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(bookingRepository.save(any(Booking.class))).thenReturn(new Booking()); // Return a non-null Booking object
    }

    @Test
    public void testSaveBookingWithOverlap() {
        // Arrange
        Equipment equipment = new Equipment(1L, "Trumpet", "It's a trumpet.");
        LocalDate startDate = LocalDate.of(2023, 10, 1);
        LocalDate endDate = LocalDate.of(2023, 10, 5);
        Booking booking = new Booking(startDate, endDate, equipment);

        when(bookingRepository.existsByEquipmentAndStartDateBetween(eq(booking.getEquipment()), eq(booking.getStartDate()), eq(booking.getEndDate()))).thenReturn(true);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            bookingService.save(booking);
        });
    }

    @Test
    public void testSaveBookingWithoutOverlap() {
        Equipment equipment = new Equipment(1L, "Trumpet", "It's a trumpet.");
        LocalDate startDate = LocalDate.of(2023, 10, 1);
        LocalDate endDate = LocalDate.of(2023, 10, 5);
        Booking booking = new Booking(startDate, endDate, equipment);
        booking.setId(1L);

        when(bookingRepository.existsByEquipmentAndStartDateBetween(eq(equipment), eq(startDate), eq(endDate))).thenReturn(false);

        // Verify that the save method was called on the mock repository
        verify(bookingRepository).save(booking);

        // Assert that the booking is not null and has an ID
        assertThat(savedBooking).isNotNull();
    }
}

