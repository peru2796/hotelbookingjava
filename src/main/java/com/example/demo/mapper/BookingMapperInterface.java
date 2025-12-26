package com.example.demo.mapper;

import com.example.demo.dto.BookingDTO;
import com.example.demo.entity.Booking;
import com.example.demo.entity.Client;
import com.example.demo.util.AppConstants;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring", // lets you @Autowired the mapper
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface BookingMapperInterface {

    @Mapping(target = "bookingId",source = "booking.id")
    @Mapping(target = "clientId",source = "client.id")
    @Mapping(target = "statusName",source = "booking.transactionStatus",qualifiedByName = "transactionStatusMapping")
    BookingDTO toBookingDto(Booking booking, Client client);


    @Named("transactionStatusMapping")
    default String transactionStatusMapping(Integer transactionStatus) {
        if(transactionStatus.equals(AppConstants.CHECKED_IN_CODE))
            return "Checked-in";
        if(transactionStatus.equals(AppConstants.CHECKOUT_STATUS_CODE))
            return "Checked-out";
        if(transactionStatus.equals(AppConstants.BOOKED_STATUS_CODE))
            return "Booked";
        if(transactionStatus.equals(AppConstants.CANCELLED_STATUS_CODE))
            return "Cancelled";
        return null;
    }

}
