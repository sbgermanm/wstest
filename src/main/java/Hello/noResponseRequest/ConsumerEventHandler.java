package Hello.noResponseRequest;

import com.lmax.disruptor.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static Hello.Application.LONG_TASK_TIME;

public class ConsumerEventHandler implements EventHandler<Dato> {

    private static final Logger log = LoggerFactory.getLogger(ConsumerEventHandler.class);


    public void onEvent(Dato event, long sequence, boolean endOfBatch) {
        System.out.println("Dato: id=" + event.getId() + " content=" + event.getContent());


        try {
            Thread.sleep(LONG_TASK_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Procesado Dato: id=" + event.getId() + " content=" + event.getContent());


    }
}