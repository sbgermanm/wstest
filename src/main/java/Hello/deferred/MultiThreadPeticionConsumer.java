package Hello.deferred;

import Hello.noResponseRequest.Dato;
import com.lmax.disruptor.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static Hello.Application.CONSUMER_N_THREADS;
import static Hello.Application.LONG_TASK_TIME;

public class MultiThreadPeticionConsumer implements EventHandler<Peticion> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    ExecutorService executorService = Executors.newFixedThreadPool(CONSUMER_N_THREADS);

    public void onEvent(Peticion event, long sequence, boolean endOfBatch) {


        executorService.execute(new Runnable() {
            public void run() {
                logger.info("Dato: id=" + event.dato.getId() + " content=" + event.dato.getContent());

                try {
                    Thread.sleep(LONG_TASK_TIME);
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