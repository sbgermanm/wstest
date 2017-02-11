package Hello;

import com.lmax.disruptor.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsumerEventHandler implements EventHandler<Dato> {

    private static final Logger log = LoggerFactory.getLogger(ConsumerEventHandler.class);


    public void onEvent(Dato event, long sequence, boolean endOfBatch) {
        System.out.println("Dato: id=" + event.getId() + " content=" + event.getContent());


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Procesado Dato: id=" + event.getId() + " content=" + event.getContent());


    }
}