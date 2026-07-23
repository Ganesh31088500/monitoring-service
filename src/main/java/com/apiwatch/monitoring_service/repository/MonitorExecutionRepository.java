package com.apiwatch.monitoring_service.repository;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.apiwatch.monitoring_service.entity.Monitor;
import com.apiwatch.monitoring_service.entity.MonitorExecution;
import com.apiwatch.monitoring_service.enums.MonitorStatus;

@Repository
public interface MonitorExecutionRepository
        extends JpaRepository<MonitorExecution, UUID> {

    /**
     * Get execution history for a monitor
     */
    List<MonitorExecution> findByMonitorOrderByExecutedAtDesc(
            Monitor monitor);
    List<MonitorExecution> findByMonitorId(UUID monitorId);

    /**
     * Latest 10 executions
     */
    List<MonitorExecution> findTop10ByOrderByExecutedAtDesc();

    /**
     * Latest execution for a monitor
     */
    MonitorExecution findTopByMonitorOrderByExecutedAtDesc(
            Monitor monitor);

    /**
     * Delete execution history
     */
    void deleteByMonitor(
            Monitor monitor);

    /**
     * Average response time by API Id
     */
    @Query("""
            SELECT AVG(me.responseTime)
            FROM MonitorExecution me
            WHERE me.monitor.apiId = :apiId
              AND me.responseTime IS NOT NULL
            """)
    Double getAverageResponseTime(
            @Param("apiId") UUID apiId);

    /**
     * Latest execution by API Id
     */
    @Query("""
            SELECT me
            FROM MonitorExecution me
            WHERE me.monitor.apiId = :apiId
            ORDER BY me.executedAt DESC
            """)
    List<MonitorExecution> findLatestExecution(
            @Param("apiId") UUID apiId);

    /**
     * Success count by API Id
     */
    @Query("""
            SELECT COUNT(me)
            FROM MonitorExecution me
            WHERE me.monitor.apiId = :apiId
              AND me.status = :status
            """)
    long countByApiIdAndStatus(
            @Param("apiId") UUID apiId,
            @Param("status") MonitorStatus status);

}