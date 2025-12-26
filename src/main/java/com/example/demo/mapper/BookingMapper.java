package com.example.demo.mapper;

import com.example.demo.dto.BookingDTO;
import com.example.demo.entity.Booking;
import com.example.demo.entity.Client;
import com.example.demo.entity.RoomType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Component
public class BookingMapper {

    @Autowired
    BookingMapperInterface bookingMapperInterface;

    public List<BookingDTO> getBookingDTOFromClientBookingList(List<Booking> bookingList, List<Client> clientList, List<RoomType> roomTypeList){
       List<BookingDTO> bookingDTOList = new ArrayList<>();
        bookingList.forEach(booking -> {

            Optional<Client> client = clientList.stream().filter(c -> c.getId().equals(booking.getClientId())).findFirst();
            Optional<RoomType> roomType = roomTypeList.stream().filter(roomTy -> roomTy.getId().equals(booking.getRoomType())).findFirst();
            BookingDTO bookingDTO = bookingMapperInterface.toBookingDto(booking,client.get());
            roomType.ifPresent(type -> bookingDTO.setRoomTypeName(type.getRoomType()));
            bookingDTOList.add(bookingDTO);
        });
        return bookingDTOList.stream().sorted(Comparator.comparing(BookingDTO::getBookingId))
                .toList();
    }

    public BookingDTO getBookingDTOFromClientBooking(Booking booking, Client client, List<RoomType> roomTypeList){

            BookingDTO bookingDTO = bookingMapperInterface.toBookingDto(booking,client);
            Optional<RoomType> roomType = roomTypeList.stream().filter(roomTy -> roomTy.getId().equals(booking.getRoomType())).findFirst();
            roomType.ifPresent(type -> bookingDTO.setRoomTypeName(type.getRoomType()));

        return bookingDTO;
    }
}
