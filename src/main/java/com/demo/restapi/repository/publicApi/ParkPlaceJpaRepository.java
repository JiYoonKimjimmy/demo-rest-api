package com.demo.restapi.repository.publicApi;

import com.demo.restapi.entity.ParkPlace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkPlaceJpaRepository extends JpaRepository<ParkPlace, Long> {
}
