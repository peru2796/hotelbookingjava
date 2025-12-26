package com.example.demo.mapper;

import com.example.demo.dto.BookingDTO;
import com.example.demo.entity.Booking;
import com.example.demo.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring", // lets you @Autowired the mapper
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface BookingMapperInterface {

    @Mapping(target = "bookingId",source = "booking.id")
    @Mapping(target = "clientId",source = "client.id")
    BookingDTO toBookingDto(Booking booking, Client client);


}
