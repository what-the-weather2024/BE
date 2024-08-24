package com.weather.the.what.whattheweather.feed.repository;

import com.weather.the.what.whattheweather.feed.entity.Feed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedRepository extends JpaRepository<Feed, Long> {
  List<Feed> findAllByCityContainsIgnoreCase(String city);
  List<Feed> findAllByCityContainsIgnoreCaseAndDistrictContainingIgnoreCase(String city, String district);
}
