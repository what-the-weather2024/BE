package com.weather.the.what.whattheweather.umbrella.repository;

import com.weather.the.what.whattheweather.umbrella.entity.Umbrella;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UmbrellaRepository extends JpaRepository<Umbrella, Long> {

  @Query(value = "SELECT * FROM umbrellas u WHERE " +
      "(6371 * acos(cos(radians(:latitude)) * cos(radians(u.latitude)) * " +
      "cos(radians(u.longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(u.latitude)))) < :radius",
      nativeQuery = true)
  List<Umbrella> findAllWithinRadius(@Param("latitude") Double latitude,
                                     @Param("longitude") Double longitude,
                                     @Param("radius") Double radius);
}
