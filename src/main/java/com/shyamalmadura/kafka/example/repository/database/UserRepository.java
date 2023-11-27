package com.shyamalmadura.kafka.example.repository.database;

import com.shyamalmadura.kafka.example.repository.database.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    List<UserEntity> getByFirstNameIgnoreCaseOrderByFirstNameAscLastNameAsc(String firstName);

}