package com.gateway.rest.app.rest;

import com.gateway.rest.app.domain.Gateway;
import com.gateway.rest.app.domain.PeripheralDevice;
import com.gateway.rest.app.service.GatewayService;
import com.gateway.rest.app.service.PeripheralDeviceService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/v1")
public class PeripheralDeviceController {

    private static final Logger _log = LoggerFactory.getLogger(PeripheralDeviceController.class);

    @Autowired
    @Qualifier("peripheralDeviceService")
    private PeripheralDeviceService peripheralDeviceService;

    @Autowired
    @Qualifier("gatewayService")
    private GatewayService gatewayService;


    /*
     *Get a Peripheral Device by Id
     *
     * */
    @ApiOperation(value="Get a Peripheral Device by Id",response=PeripheralDevice.class)
    @ApiResponses(value={
            @ApiResponse(code=200,message="Peripheral Device retrieved",response=PeripheralDevice.class),
            @ApiResponse(code=500,message="Internal Server Error"),
            @ApiResponse(code=404,message="Gateway not found")
    })
    @CrossOrigin
    @GetMapping("/device/{deviceId}")
    public ResponseEntity<PeripheralDevice> getPeripheralDeviceById(@PathVariable (value = "deviceId") Long deviceId) {
        PeripheralDevice peripheralDeviceStored = new PeripheralDevice();
        try {
            peripheralDeviceStored = peripheralDeviceService.getPeripheralDevice(deviceId);
            return new ResponseEntity<PeripheralDevice>(peripheralDeviceStored, HttpStatus.OK);
        }catch (Exception e){
            _log.error(e.getMessage());
        }
        return new ResponseEntity<PeripheralDevice>(peripheralDeviceStored, HttpStatus.BAD_REQUEST);
    }

    /*
     *List all  Peripheral Devices related with a Gateway
     *
     * */
    @ApiOperation(value="get all devices related with a specific gateway",response=Collection.class)
    @ApiResponses(value={
            @ApiResponse(code=200,message="Devices list retrieved",response=Collection.class),
            @ApiResponse(code=500,message="Internal Server Error"),
            @ApiResponse(code=404,message="Gateway not found")
    })
    @CrossOrigin
    @GetMapping("/device/{gatewayId}/gateway")
    public Page<PeripheralDevice> getAllPeripheralDeviceByGatewayId(@PathVariable (value = "gatewayId") Long gatewayId,
                                                         Pageable pageable) {
        return peripheralDeviceService.findByGatewayId(gatewayId, pageable);
    }

    /*
     *List all  Peripheral Devices
     *
     * */
    @ApiOperation(value="List all Peripheral Devices",response=Collection.class)
    @ApiResponses(value={
            @ApiResponse(code=200,message="Peripheral Devices retrieved",response=Collection.class),
            @ApiResponse(code=500,message="Internal Server Error"),
            @ApiResponse(code=404,message="Gateway not found")
    })
    @CrossOrigin
    @GetMapping("/device")
    public Page<PeripheralDevice> getAllPeripheralDevice(Pageable pageable) {
        return peripheralDeviceService.findAll(pageable);
    }

    /*
     *Add a new Peripheral Device
     *
     * */
    @ApiOperation(value="add a new Peripheral Device",response=PeripheralDevice.class)
    @ApiResponses(value={
            @ApiResponse(code=200,message="Peripheral Device Details retrieved",response=PeripheralDevice.class),
            @ApiResponse(code=500,message="Internal Server Error"),
            @ApiResponse(code=404,message="Gateway not found")
    })
    @CrossOrigin
    @PostMapping("/device/{gatewayId}/gateway")
    public ResponseEntity<?> createPeripheralDevice(@PathVariable ("gatewayId") Long gatewayId,
                                 @Valid @RequestBody PeripheralDevice peripheralDevice) {

        List<PeripheralDevice> peripheralDevices = peripheralDeviceService.findByGatewayId(gatewayId);
        PeripheralDevice peripheralDeviceStored = new PeripheralDevice();
        if(peripheralDevices.size()<10){
            try {
                Gateway gateway = gatewayService.getGateway(gatewayId);
                peripheralDevice.setGateway(gateway);
                peripheralDeviceStored = peripheralDeviceService.add(peripheralDevice);
                new ResponseEntity<PeripheralDevice>(peripheralDeviceStored, HttpStatus.OK);
            }catch (Exception e){
                _log.error(e.getMessage());
            }
            return new ResponseEntity<PeripheralDevice>(peripheralDeviceStored, HttpStatus.BAD_REQUEST);
        }else
            return new ResponseEntity<String>("Limit Exceeded. Just added 10 items per gateway", HttpStatus.NOT_ACCEPTABLE); //Just added 10 items per gateway

    }

    /*
     *Delete a  Peripheral Device
     *
     * */
    @ApiOperation(value="Delete a Peripheral Device")
    @ApiResponses(value={
            @ApiResponse(code=200,message="Operation OK"),
            @ApiResponse(code=500,message="Internal Server Error"),
            @ApiResponse(code=404,message="Gateway not found")
    })
    @CrossOrigin
    @DeleteMapping("/device/{deviceId}")
    public void deleteComment(@PathVariable (value = "deviceId") Long deviceId) {
        try {
            peripheralDeviceService.delete(deviceId);
        }catch (Exception e){
            _log.error(e.getMessage());
        }
    }

    /*
    *Update a  Peripheral Device
    *
    * */
    @ApiOperation(value="Uppdate a Peripheral Device",response=PeripheralDevice.class)
    @ApiResponses(value={
            @ApiResponse(code=200,message="Peripheral Device Details retrieved",response=PeripheralDevice.class),
            @ApiResponse(code=500,message="Internal Server Error"),
            @ApiResponse(code=404,message="Gateway not found")
    })
    @CrossOrigin
    @PutMapping("/device/{deviceId}/{gatewayId}")
    public ResponseEntity<PeripheralDevice> updateGateway(@PathVariable("deviceId") Long deviceId, @PathVariable("gatewayId") Long gatewayId, @Valid @RequestBody PeripheralDevice peripheralDevice) {

        PeripheralDevice peripheralDeviceStored = new PeripheralDevice();
        try {

            Gateway gateway = gatewayService.getGateway(gatewayId);
            peripheralDeviceStored = peripheralDeviceService.getPeripheralDevice(deviceId);
            peripheralDeviceStored.setGateway(gateway);
            peripheralDeviceStored.setStatus(peripheralDevice.getStatus());
            peripheralDeviceStored.setUid(peripheralDevice.getUid());
            peripheralDeviceStored.setVendor(peripheralDevice.getVendor());
            peripheralDeviceStored = peripheralDeviceService.update(peripheralDeviceStored);
            return new ResponseEntity<PeripheralDevice>(peripheralDeviceStored, HttpStatus.OK);
        }catch (Exception e){
            _log.error(e.getMessage());
        }
        return new ResponseEntity<PeripheralDevice>(peripheralDeviceStored, HttpStatus.BAD_REQUEST);
    }
}
