package rest;

import brokers.Broker;
import brokers.DefaultBroker;
import com.google.gson.Gson;
import exceptions.BadPartitionException;
import exceptions.NoPartitionFound;
import messages.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import partitions.CityPartition;
import partitions.IdPartition;
import partitions.Partition;
import rest.errors.InvalidRequest;
import rest.errors.WrongServer;
import topics.Topic;

import java.util.List;
import java.util.Map;


@RestController
public class KafkaRestController{
    public Broker broker; //this is just for now until we have the main broker
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

        broker.addPartition(partitionDriverData);
        broker.addPartition(partitionRiderData);
        broker.addPartition(partitionRiderRequestData);
        broker.addPartition(partitionDriverRequestData);
        broker.addPartition(partitionRiderAcceptsData);

    }

    public Boolean VerifyPutInput(Map<String, Object> body) {
        return body.get("id") != null && body.get("city") != null && body.get("lat") != null && body.get("lng") != null;

    }


    public List<Message> GetIdRequest(Topic topic, int id, int offset){
        try {
            return broker.consume(topic,id,offset);
        } catch (BadPartitionException e) {
            throw new RuntimeException(e);
        } catch(NoPartitionFound e){
            throw new WrongServer("check 192.168.1.0");
        }

    }
    public List<Message> GetCityRequest(Topic topic, String city, int offset){
        try {
            return broker.consume(topic,city,offset);
        } catch (BadPartitionException e) {
            throw new RuntimeException(e);
        } catch(NoPartitionFound e){
            throw new WrongServer("check 192.168.1.0");
        }
//        if(!broker.getPartitions().contains(newpartition)){
//            throw new WrongServer("check 192.168.1.0");
//        }
    }

    public void PutRequest(Topic topic,Map<String, Object> info){
        if(!VerifyPutInput(info)){
            throw new InvalidRequest();
        }
        String value = new Gson().toJson(info);
        Message message = new Message((int)info.get("id"),topic,value);
        try {
            broker.produce(message);
        } catch (NoPartitionFound e) {
            throw new WrongServer("check 192.168.1.0");
        }

    }


    @GetMapping("/driverlocation")
    public List<Message> GetDriverLocation(@RequestParam(value="id",defaultValue = "0")int id,
                                    @RequestParam(value="city",defaultValue = "")String city,
                                    @RequestParam(value="offset",defaultValue = "0")int offset){

        return GetIdRequest(Topic.DRIVER_DATA,id,offset);

    }

    @PutMapping("/driverlocation")
    public void PutDriverLocation(@RequestBody Map<String, Object> info){

        PutRequest(Topic.DRIVER_DATA,info);

    }

    @GetMapping("/riderlocation")
    public List<Message> GetRiderLocation(@RequestParam(value="id",defaultValue = "0")int id,
                                           @RequestParam(value="city",defaultValue = "")String city,
                                           @RequestParam(value="offset",defaultValue = "0")int offset){

        return GetIdRequest(Topic.RIDER_DATA,id,offset);

    }
    @PutMapping("/riderlocation")
    public void PutRiderLocation(@RequestBody Map<String, Object> info){
        PutRequest(Topic.RIDER_DATA,info);

    }



    @GetMapping("/userrequests")
    public List<Message> GetUserRequests(@RequestParam(value="id",defaultValue = "0")int id,
                                          @RequestParam(value="city",defaultValue = "")String city,
                                          @RequestParam(value="offset",defaultValue = "0")int offset){
        return GetCityRequest(Topic.RIDER_REQUESTS_RIDE,city,offset);

    }

    @PutMapping("/userrequests")
    public void PutUserRequests(@RequestBody Map<String, Object> info){

        PutRequest(Topic.RIDER_REQUESTS_RIDE,info);

    }

    @GetMapping("/driverrequests")
    public List<Message> GetDriverRequests(@RequestParam(value="id",defaultValue = "0")int id,
                                          @RequestParam(value="city",defaultValue = "")String city,
                                          @RequestParam(value="offset",defaultValue = "0")int offset){
        return GetIdRequest(Topic.DRIVER_REQUESTS_RIDE,id,offset);

    }
    @PutMapping("/driverrequests")
    public void PutDriverRequests(@RequestBody Map<String, Object> info){

        PutRequest(Topic.DRIVER_REQUESTS_RIDE,info);

    }

    @GetMapping("/useraccepts")
    public List<Message> GetUserAccepts(@RequestParam(value="id",defaultValue = "0")int id,
                                           @RequestParam(value="city",defaultValue = "")String city,
                                           @RequestParam(value="offset",defaultValue = "0")int offset){
        return GetIdRequest(Topic.RIDER_ACCEPTS_RIDE,id,offset);

    }

    @PutMapping("/useraccepts")
    public void PutUserAccepts(@RequestBody Map<String, Object> info){
        PutRequest(Topic.RIDER_ACCEPTS_RIDE,info);

    }

}
