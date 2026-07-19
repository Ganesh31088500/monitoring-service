package com.apiwatch.monitoring_service.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apiwatch.monitoring_service.entity.Monitor;


@Repository
public interface MonitorRepository extends JpaRepository<Monitor, UUID> {

    /**
     * Find Monitor by API Id
     */
    List<Monitor> findByApiId(UUID apiId);

    /**
     * Find Enabled Monitors
     */
    List<Monitor> findByEnabledTrue();

    /**
     * Find Monitor by Id & API
     */
    Optional<Monitor> findByIdAndApiId(
            UUID monitorId,
            UUID apiId);

    /**
     * Check duplicate monitor name
     */
    boolean existsByApiIdAndName(
            UUID apiId,
            String name);

}