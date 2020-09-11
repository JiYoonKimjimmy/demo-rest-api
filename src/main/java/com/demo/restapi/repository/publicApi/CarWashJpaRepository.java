package com.demo.restapi.repository.publicApi;

import com.demo.restapi.entity.CarWash;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarWashJpaRepository extends JpaRepository<CarWash, Long> {
}
