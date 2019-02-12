package com.gateway.rest.app;

import com.gateway.rest.app.domain.Gateway;
import com.gateway.rest.app.domain.PeripheralDevice;
import com.gateway.rest.app.repository.GatewayRepository;
import com.gateway.rest.app.repository.PeripheralDeviceRepository;
import com.gateway.rest.app.rest.GatewayController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class GatewayRestServiceApplicationTests {

    @Autowired
    @Qualifier("gatewayRepository")
    private GatewayRepository gatewayRepository;

    @Autowired
    @Qualifier("peripheralDeviceRepository")
    private PeripheralDeviceRepository peripheralDeviceRepository;

    private Gateway gateway;
    private PeripheralDevice peripheralDevice;


    @Before
    public void loadInfoForTest(){

        gateway = new Gateway("serial01", "readeable-name", "127.0.0.1");
        peripheralDevice = new PeripheralDevice(573L, "custom-vendor", LocalDate.now(), "online");

    }

    @Test
    public void addGateway(){

        Gateway saved = gatewayRepository.save(gateway);
        Gateway restored = gatewayRepository.getOne(saved.getId());
        assertThat(saved).isEqualTo(restored);

    }

    @Test
    public void addPeripheralDevice(){

        Gateway gateway2 = new Gateway("serial02", "readeable-name2", "127.0.0.2");
        peripheralDevice.setGateway(gateway2);

        PeripheralDevice saved = peripheralDeviceRepository.save(peripheralDevice);
        PeripheralDevice restored = peripheralDeviceRepository.getOne(peripheralDevice.getId());

        assertThat(saved).isEqualTo(peripheralDevice);

    }
}

