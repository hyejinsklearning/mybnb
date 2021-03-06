package mybnb;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;

@Entity
@Table(name="Room_table")
public class Room {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String name;
    private Long price;
    private String address;
    private String host;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Long getPrice() {
        return price;
    }
    public void setPrice(Long price) {
        this.price = price;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getHost() {
        return host;
    }
    public void setHost(String host) {
        this.host = host;
    }

    @PostPersist
    public void onPostPersist(){
        // 비동기로 이벤트 서비스를 호출한다.
        mybnb.external.Event event = new mybnb.external.Event();
        // 이벤트 mapping 설정
        event.setHostName(getHost());
        event.setRoomId(getId());
        event.setRoomName(getName());

        RoomApplication.applicationContext.getBean(mybnb.external.EventService.class)
                .apply(event);

        // 이벤트 참여 후 숙소 등록 완료
        RoomRegistered roomRegistered = new RoomRegistered();
        BeanUtils.copyProperties(this, roomRegistered);
        roomRegistered.publishAfterCommit();
    }

    @PostUpdate
    public void onPostUpdate(){
        RoomChanged roomChanged = new RoomChanged();
        BeanUtils.copyProperties(this, roomChanged);
        roomChanged.publishAfterCommit();
    }

    @PostRemove
    public void onPostRemove(){
        RoomDeleted roomDeleted = new RoomDeleted();
        BeanUtils.copyProperties(this, roomDeleted);
        roomDeleted.publishAfterCommit();
    }

}
