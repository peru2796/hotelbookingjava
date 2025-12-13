package com.example.demo.repository;

import com.example.demo.dto.RoomDetailsDTO;
import com.example.demo.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomDetailsRepository extends JpaRepository<Room,Integer> {

    @Query("select new com.example.demo.dto.RoomDetailsDTO(r.roomNumber,r.roomName,f.floorNumber,f.floorName,r.id,rt.roomType,rt.amount) from Room r,Floor f,RoomType rt WHERE r.floorNumber = f.floorNumber and r.status = 1 and f.status = 1 and rt.id = r.roomType and rt.status =1")
    Optional<List<RoomDetailsDTO>> getRoomDetailsList();
}
