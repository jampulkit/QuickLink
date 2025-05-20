package com.pulkitBuilds.QuickLink.Repo;

import com.pulkitBuilds.QuickLink.Entity.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlMappingRepository extends JpaRepository<UrlMapping,Long> {
    Optional<UrlMapping> findByShortCode(String shortCode);
    UrlMapping findByLongUrl(String longUrl);
    boolean existsByShortCode(String shortCode);
    boolean existsByLongUrl(String longUrl);

}
