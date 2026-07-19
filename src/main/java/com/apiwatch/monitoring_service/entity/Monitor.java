package com.apiwatch.monitoring_service.entity;

import java.util.UUID;

import com.apiwatch.monitoring_service.enums.MonitorInterval;
import com.apiwatch.monitoring_service.enums.MonitorType;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "monitors")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Monitor extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "api_id", nullable = false)
    private UUID apiId;

    @Column(nullable = false, length = 150)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "monitor_type", nullable = false)
    private MonitorType monitorType;

    @Enumerated(EnumType.STRING)
    @Column(name = "monitor_interval", nullable = false)
    private MonitorInterval interval;

    @Column(name = "timeout_seconds", nullable = false)
    private Integer timeoutSeconds;

    @Column(name = "expected_status", nullable = false)
    private Integer expectedStatus;

    @Column(name = "expected_response", columnDefinition = "TEXT")
    private String expectedResponse;

    @Column(nullable = false)
    private Boolean enabled;

}