package com.gateway.rest.app.repository;

import com.gateway.rest.app.domain.PeripheralDevice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("peripheralDeviceRepository")
public interface PeripheralDeviceRepository extends JpaRepository<PeripheralDevice, Long>{
    Page<PeripheralDevice> findByGatewayId(Long gatewayId, Pageable pageable);
    List<PeripheralDevice> findByGatewayId(Long gatewayId);
}
