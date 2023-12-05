package rest;

import brokers.Broker;
import brokers.DefaultBroker;
import exceptions.BadPartitionException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.View;
import partitions.CityPartition;
import partitions.DefaultPartition;
import partitions.IdPartition;
import partitions.Partition;
import rest.errors.WrongServer;
import topics.Topic;


@RestController
public class KafkaRestController{
    Broker broker; //this is just for now until we have the main broker
    Partition partitionDriverData;
    Partition partitionRiderData;
    Partition partitionRiderRequestData;
    Partition partitionDriverRequestData;
    Partition partitionRiderAcceptsData;

    @Autowired
    public void setup(){
        this.broker = new DefaultBroker();
        try {
            partitionDriverData = new IdPartition(Topic.DRIVER_DATA,1);
            partitionRiderData = new IdPartition(Topic.RIDER_DATA,1);
            partitionRiderRequestData = new CityPartition(Topic.RIDER_REQUESTS_RIDE,"Vancouver");
            partitionDriverRequestData = new IdPartition(Topic.DRIVER_REQUESTS_RIDE,1);
            partitionRiderAcceptsData = new IdPartition(Topic.RIDER_ACCEPTS_RIDE,1);
        } catch (BadPartitionException e) {
            throw new RuntimeException(e);
        }

        //broker.addPartition(partitionDriverData);
        broker.addPartition(partitionRiderData);
        broker.addPartition(partitionRiderRequestData);
        broker.addPartition(partitionDriverRequestData);
        broker.addPartition(partitionRiderAcceptsData);

    }





    @GetMapping("/driverlocation")
    public RestInput GetDriverLocation(@RequestParam(value="id",defaultValue = "0")int id,
                                    @RequestParam(value="city",defaultValue = "Vancouver")String city,
                                    @RequestParam(value="x",defaultValue = "0")double x,
                                    @RequestParam(value="y",defaultValue = "0")double y,
                                    @RequestParam(value="offset",defaultValue = "0")long offset){
        Partition newpartition;
        try {
            newpartition = new IdPartition(Topic.DRIVER_DATA,id);
        } catch (BadPartitionException e) {
            throw new RuntimeException(e);
        }

        if(!broker.getPartitions().contains(newpartition)){
            throw new WrongServer("check 192.168.1.0");

        }


        return new RestInput(id,city,x,y,offset);

    }

    @GetMapping("/riderlocation")
    public RestInput GetRiderData(@RequestParam(value="id",defaultValue = "0")int id,
                                  @RequestParam(value="city",defaultValue = "Vancouver")String city,
                                  @RequestParam(value="x",defaultValue = "0")double x,
                                  @RequestParam(value="y",defaultValue = "0")double y,
                                  @RequestParam(value="offset",defaultValue = "0")long offset){

        Partition newpartition;
        try {
            newpartition = new IdPartition(Topic.RIDER_DATA,id);
        } catch (BadPartitionException e) {
            throw new RuntimeException(e);
        }
        if(!broker.getPartitions().contains(newpartition)){
            throw new WrongServer("check 192.168.1.0");

        }

        return new RestInput(id,city,x,y,offset);


    }

    @GetMapping("/userrequests")
    public RestInput GetUserRequests(@RequestParam(value="id",defaultValue = "0")int id,
                                  @RequestParam(value="city",defaultValue = "Vancouver")String city,
                                  @RequestParam(value="x",defaultValue = "0")double x,
                                  @RequestParam(value="y",defaultValue = "0")double y,
                                  @RequestParam(value="offset",defaultValue = "0")long offset){

        Partition newpartition;
        try {
            newpartition = new CityPartition(Topic.RIDER_REQUESTS_RIDE,city);
        } catch (BadPartitionException e) {
            throw new RuntimeException(e);
        }
        if(!broker.getPartitions().contains(newpartition)){
            throw new WrongServer("check 192.168.1.0");

        }

        return new RestInput(id,city,x,y,offset);


    }

    @GetMapping("/driverrequests")
    public RestInput GetDriverRequests(@RequestParam(value="id",defaultValue = "0")int id,
                                    @RequestParam(value="city",defaultValue = "Vancouver")String city,
                                    @RequestParam(value="x",defaultValue = "0")double x,
                                    @RequestParam(value="y",defaultValue = "0")double y,
                                    @RequestParam(value="offset",defaultValue = "0")long offset){
        Partition newpartition;
        try {
            newpartition = new IdPartition(Topic.DRIVER_REQUESTS_RIDE,id);
        } catch (BadPartitionException e) {
            throw new RuntimeException(e);
        }
        if(!broker.getPartitions().contains(newpartition)){
            throw new WrongServer("check 192.168.1.0");

        }

        return new RestInput(id,city,x,y,offset);

    }

    @GetMapping("/useraccepts")
    public RestInput GetUserAccepts(@RequestParam(value="id",defaultValue = "0")int id,
                                  @RequestParam(value="city",defaultValue = "Vancouver")String city,
                                  @RequestParam(value="x",defaultValue = "0")double x,
                                  @RequestParam(value="y",defaultValue = "0")double y,
                                  @RequestParam(value="offset",defaultValue = "0")long offset){
        Partition newpartition;
        try {
            newpartition = new IdPartition(Topic.RIDER_ACCEPTS_RIDE,id);
        } catch (BadPartitionException e) {
            throw new RuntimeException(e);
        }
        if(!broker.getPartitions().contains(newpartition)){
            throw new WrongServer("check 192.168.1.0");

        }

        return new RestInput(id,city,x,y,offset);

    }

}
