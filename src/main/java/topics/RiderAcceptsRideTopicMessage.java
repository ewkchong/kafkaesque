package topics;

public class RiderAcceptsRideTopicMessage extends TopicMessage {
    public RiderAcceptsRideTopicMessage(int id) {
        super(Topic.RIDER_ACCEPTS_RIDE, id);
    }
}
