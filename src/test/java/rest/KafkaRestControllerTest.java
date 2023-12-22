package rest;

import brokers.Broker;
import exceptions.BadPartitionException;
import exceptions.NoPartitionFound;
import messages.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rest.errors.InvalidRequest;
import rest.errors.WrongServer;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.Map;

public class KafkaRestControllerTest {

    @Mock
    private Broker mockBroker;

    @InjectMocks
    private KafkaRestController kafkaRestController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        kafkaRestController = new KafkaRestController();
        kafkaRestController.broker = mockBroker;
    }

    @Test
    public void testGetDriverLocation_BrokerThrowsException() {
        int id = 1;
        int offset = 0;
        try {
            when(mockBroker.consume(any(), anyInt(), anyInt())).thenThrow(new BadPartitionException("BAD"));
        } catch (BadPartitionException | NoPartitionFound e) {
            throw new RuntimeException(e);
        }

        assertThrows(RuntimeException.class, () -> kafkaRestController.GetDriverLocation(id, "", offset));
    }

    // Similarly, write tests for other methods such as PutDriverLocation, GetRiderLocation, etc.

    @Test
    public void testPutDriverLocation_ValidInput() {
        // Mock data
        Map<String, Object> info = new HashMap<>();
        info.put("id", 5);
        info.put("city", "Some City");
        info.put("lat", 10.0);
        info.put("lng", 20.0);

        // Define behavior for mock objects
        try {
            doNothing().when(mockBroker).produce(any(Message.class));
        } catch (NoPartitionFound e) {
            throw new RuntimeException(e);
        } catch (BadPartitionException e) {
            throw new RuntimeException(e);
        }

        // Perform the test
        kafkaRestController.PutDriverLocation(info);

        // Assertions
        // Verify that the produce method was called on the mockBroker with the expected arguments
        try {
            verify(mockBroker, times(1)).produce(any(Message.class));
        } catch (NoPartitionFound e) {
            throw new RuntimeException(e);
        } catch (BadPartitionException e) {
            throw new RuntimeException(e);
        }
    }
    // Test PutDriverLocation with invalid input
    @Test
    public void testPutDriverLocation_InvalidInput() {
        Map<String, Object> invalidInfo = new HashMap<>();
        invalidInfo.put("id", 1);

        assertThrows(InvalidRequest.class, () -> kafkaRestController.PutDriverLocation(invalidInfo));
    }

    @Test
    public void testGetRiderLocation_NoPartitionFound() {
        int id = 1;
        int offset = 0;
        try {
            when(mockBroker.consume(any(), anyInt(), anyInt())).thenThrow(new NoPartitionFound(""));
        } catch (BadPartitionException | NoPartitionFound e) {
            throw new RuntimeException(e);
        }

        assertThrows(WrongServer.class, () -> kafkaRestController.GetRiderLocation(id, "", offset));
    }


    @Test
    public void testPutUserRequests_BrokerThrowsNoPartitionFound() {
        Map<String, Object> info = new HashMap<>();
        info.put("id", 1);
        info.put("city", "Some City");
        info.put("lat", 10.0);
        info.put("lng", 20.0);

        try {
            doThrow(new NoPartitionFound("")).when(mockBroker).produce(any(Message.class));
        } catch (NoPartitionFound e) {
            throw new RuntimeException(e);
        } catch (BadPartitionException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void testPutUserAccepts_ValidInput() {
        Map<String, Object> validInfo = new HashMap<>();
        validInfo.put("id", 1);
        validInfo.put("city", "Some City");
        validInfo.put("lat", 10.0);
        validInfo.put("lng", 20.0);

        assertDoesNotThrow(() -> kafkaRestController.PutUserAccepts(validInfo));
    }


}
