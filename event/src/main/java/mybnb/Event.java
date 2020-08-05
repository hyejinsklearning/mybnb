package mybnb;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;

@Entity
@Table(name="Event_table")
public class Event {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String hostName;
    private Long roomId;
    private String roomName;

    @PrePersist
    public void onPrePersist(){
        EventApplied eventApplied = new EventApplied();
        BeanUtils.copyProperties(this, eventApplied);
        eventApplied.publishAfterCommit();


    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }
    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }
    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }




}
