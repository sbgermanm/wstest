package Hello.deferred;

import Hello.noResponseRequest.Dato;
import com.lmax.disruptor.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadPeticionConsumer implements EventHandler<Peticion> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    ExecutorService executorService = Executors.newFixedThreadPool(8);

    public void onEvent(Peticion event, long sequence, boolean endOfBatch) {


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