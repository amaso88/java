package com.gateway.rest.app.repository;

import com.gateway.rest.app.domain.Gateway;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("gatewayRepository")
public interface GatewayRepository extends JpaRepository<Gateway, Long> {
}
