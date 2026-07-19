package com.apiwatch.monitoring_service.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.apiwatch.monitoring_service.enums.MonitorStatus;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "monitor_executions")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MonitorExecution extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monitor_id", nullable = false)
    private Monitor monitor;
    @Column(name = "api_id", nullable = false)
    private UUID apiId;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MonitorStatus status;

    @Column(name = "http_status")
    private Integer httpStatus;

    @Column(name = "response_time")
    private Long responseTime;

    @Column(name = "response_body", columnDefinition = "LONGTEXT")
    private String responseBody;

    @Column(name = "error_message", columnDefinition = "TEXT")
    private String errorMessage;

    @Column(name = "executed_at", nullable = false)
    private LocalDateTime executedAt;

}