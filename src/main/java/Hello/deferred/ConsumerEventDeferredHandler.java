package Hello.deferred;

import Hello.Dato;
import com.lmax.disruptor.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsumerEventDeferredHandler implements EventHandler<DatoDeferred> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    public void onEvent(DatoDeferred event, long sequence, boolean endOfBatch) {
        logger.info("Dato: id=" + event.dato.getId() + " content=" + event.dato.getContent());


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("Procesado Dato: id=" + event.dato.getId() + " content=" + event.dato.getContent());

        Dato resultDato = new Dato(event.dato.getId(), "Ha sido procesado : " + event.getDato().getContent());
        event.deferredResult.setResult(resultDato );

    }
}