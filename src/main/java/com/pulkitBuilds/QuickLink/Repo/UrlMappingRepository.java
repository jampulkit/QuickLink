package com.pulkitBuilds.QuickLink.Repo;

import com.pulkitBuilds.QuickLink.Entity.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlMappingRepository extends JpaRepository<UrlMapping,Long> {
    UrlMapping findByShortCode(String shortCode);
    UrlMapping findByLongUrl(String longUrl);
    boolean existsByShortCode(String shortCode);
    boolean existsByLongUrl(String longUrl);

}
