package com.example.demo.repository;

import com.example.demo.entity.States;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StatesRepository extends JpaRepository<States, Long> {

}
