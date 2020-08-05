
package mybnb.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

@FeignClient(name="Event", url="${api.url.payment}")
public interface EventService {
    // url 을 configmap에 넣기.
    @RequestMapping(method= RequestMethod.POST, path="/events")
    public void apply(@RequestBody Event event);

}