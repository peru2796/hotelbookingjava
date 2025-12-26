package com.example.demo.mapper;

import com.example.demo.dto.BookingDTO;
import com.example.demo.entity.Booking;
import com.example.demo.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class BookingMapper {

    @Autowired
    BookingMapperInterface bookingMapperInterface;

    public List<BookingDTO> getBookingDTOFromClientBooking(List<Booking> bookingList, List<Client> clientList){
       List<BookingDTO> bookingDTOList = new ArrayList<>();
        bookingList.forEach(booking -> {

            Optional<Client> client = clientList.stream().filter(c -> c.getId().equals(booking.getClientId())).findFirst();

            BookingDTO bookingDTO = bookingMapperInterface.toBookingDto(booking,client.get());
            bookingDTOList.add(bookingDTO);
        });
        return bookingDTOList;
    }
}
