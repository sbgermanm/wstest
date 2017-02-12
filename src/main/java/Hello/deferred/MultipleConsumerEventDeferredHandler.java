package Hello.deferred;

import Hello.Dato;
import com.lmax.disruptor.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultipleConsumerEventDeferredHandler implements EventHandler<DatoDeferred> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    ExecutorService executorService = Executors.newFixedThreadPool(4);

    public void onEvent(DatoDeferred event, long sequence, boolean endOfBatch) {


        executorService.execute(new Runnable() {
            public void run() {
                logger.info("Dato: id=" + event.dato.getId() + " content=" + event.dato.getContent());

                try {
                    Thread.sleep(1000);
                    logger.info("Procesado Dato: id=" + event.dato.getId() + " content=" + event.dato.getContent());

                    Dato resultDato = new Dato(event.dato.getId(), "Ha sido procesado : " + event.getDato().getContent());
                    event.deferredResult.setResult(resultDato );

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


    }
}