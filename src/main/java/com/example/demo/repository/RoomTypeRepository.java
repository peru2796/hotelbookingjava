package com.example.demo.repository;

import com.example.demo.dto.RoomDetailsDTO;
import com.example.demo.entity.Room;
import com.example.demo.entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomType,Integer> {


}
