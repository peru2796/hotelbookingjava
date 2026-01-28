package com.example.demo.mapper;

import com.example.demo.dto.BookingDTO;
import com.example.demo.entity.Billing;
import com.example.demo.entity.Booking;
import com.example.demo.entity.Client;
import com.example.demo.entity.RoomType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Component
public class BillingMapper {

    @Autowired
    MapperInterface mapperInterface;

    public List<BookingDTO> getBookingDTOFromClientBillingList(List<Billing> billingList, List<Client> clientList, List<RoomType> roomTypeList){
       List<BookingDTO> bookingDTOList = new ArrayList<>();
        billingList.forEach(billing -> {

            Optional<Client> client = clientList.stream().filter(c -> c.getId().equals(billing.getClientId())).findFirst();
            Optional<RoomType> roomType = roomTypeList.stream().filter(roomTy -> roomTy.getId().equals(billing.getRoomType())).findFirst();
            BookingDTO bookingDTO = mapperInterface.toBillingDto(billing,client.get());
            roomType.ifPresent(type -> bookingDTO.setRoomTypeName(type.getRoomType()));
            bookingDTOList.add(bookingDTO);
        });
        return bookingDTOList.stream().sorted(Comparator.comparing(BookingDTO::getBookingId).reversed())
                .toList();
    }

    public BookingDTO getBookingDTOFromClientBooking(Booking booking, Client client, List<RoomType> roomTypeList){

            BookingDTO bookingDTO = mapperInterface.toBookingDto(booking,client);
            Optional<RoomType> roomType = roomTypeList.stream().filter(roomTy -> roomTy.getId().equals(booking.getRoomType())).findFirst();
            roomType.ifPresent(type -> bookingDTO.setRoomTypeName(type.getRoomType()));

        return bookingDTO;
    }
}
