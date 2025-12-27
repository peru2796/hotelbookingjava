package com.example.demo.mapper;

import com.example.demo.dto.BookingDTO;
import com.example.demo.dto.RoomDetailsDTO;
import com.example.demo.entity.Booking;
import com.example.demo.entity.Client;
import com.example.demo.util.AppConstants;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(
        componentModel = "spring", // lets you @Autowired the mapper
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface BookingMapperInterface {

    @Mapping(target = "bookingId",source = "booking.id")
    @Mapping(target = "clientId",source = "client.id")
    @Mapping(target = "statusName",source = "booking.transactionStatus",qualifiedByName = "transactionStatusMapping")
    BookingDTO toBookingDto(Booking booking, Client client);

    @Mapping(target = "roomNumber",source = "roomDetailsDTO.roomNumber")
    @Mapping(target = "roomTypeName",source = "roomDetailsDTO.roomType")
    @Mapping(target = "roomType",source = "roomDetailsDTO.roomId")
    @Mapping(target = "roomId",source = "roomDetailsDTO.id")
    @Mapping(target = "roomRent",source = "roomDetailsDTO.amount")
    @Mapping(target = "statusName",constant = "Available")
    BookingDTO toRoomDetailsDto(RoomDetailsDTO roomDetailsDTO);

    @Mapping(target = "roomNumber",source = "roomDetailsDTO.roomNumber")
    @Mapping(target = "roomTypeName",source = "roomDetailsDTO.roomType")
    @Mapping(target = "roomType",source = "booking.roomType")
    @Mapping(target = "roomId",source = "roomDetailsDTO.id")
    @Mapping(target = "roomRent",source = "roomDetailsDTO.amount")
    @Mapping(target = "bookingId",source = "booking.id")
    @Mapping(target = "checkinDateString",source = "booking.checkinDts", qualifiedByName = "formatDate")
    @Mapping(target = "checkoutDateString",source = "booking.checkoutDts", qualifiedByName = "formatDate")
    @Mapping(target = "statusName",source = "booking.transactionStatus",qualifiedByName = "transactionStatusMapping")
    BookingDTO toRoomDetailsDto(RoomDetailsDTO roomDetailsDTO,Booking booking,Client client);

    @Named("transactionStatusMapping")
    default String transactionStatusMapping(Integer transactionStatus) {
       String result = "Available";
        if(transactionStatus.equals(AppConstants.CHECKED_IN_CODE))
            result = "Checked-in";
        if(transactionStatus.equals(AppConstants.CHECKOUT_STATUS_CODE))
            result= "Checked-out";
        if(transactionStatus.equals(AppConstants.BOOKED_STATUS_CODE))
            result = "Booked";
        if(transactionStatus.equals(AppConstants.CANCELLED_STATUS_CODE))
            result = "Cancelled";
        return result;
    }


    @Named("formatDate")
    default String formatDate(LocalDateTime dateTime) {
        if (dateTime == null) return null;
        return dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

}
