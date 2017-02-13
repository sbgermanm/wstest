package Hello.deferred;

import Hello.competableFuture.TaskService;
import Hello.noResponseRequest.Dato;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class AsyncDeferredController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final TaskService taskService;



    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    private static final RingBuffer<Peticion> ringBuffer = getRingBuffer();

    @Autowired
    public AsyncDeferredController(TaskService taskService) {
        this.taskService = taskService;
    }

    @RequestMapping(value = "/deferred")
    public DeferredResult<Dato> executeSlowTask(@RequestParam(value = "name", defaultValue = "World") String name) {
        logger.info("Request " + name + " received");
        DeferredResult<Dato> deferredResult = new DeferredResult<>();


        PeticionProducer producer = new PeticionProducer(ringBuffer);
        Peticion dato = new Peticion(counter.incrementAndGet(),
                String.format(template, name), deferredResult);
        producer.onData(dato);



        logger.info("Servlet thread  " + name + " released");

        return deferredResult;
    }





    private static RingBuffer<Peticion> getRingBuffer() {
        Disruptor<Peticion> disruptor = startDisruptor();

        return disruptor.getRingBuffer();
    }


    private static Disruptor<Peticion> startDisruptor() {
        // Executor that will be used to construct new threads for consumers
        Executor executor = Executors.newFixedThreadPool(100);

        // The factory for the event
        PeticionFactory factory = new PeticionFactory();

        // Specify the size of the ring buffer, must be power of 2.
        int bufferSize = 8192;

        // Construct the Disruptor
        Disruptor<Peticion> disruptor = new Disruptor<>(factory, bufferSize, executor);

        // Connect the handler
        disruptor.handleEventsWith(new MultiThreadPeticionConsumer());


        // Start the Disruptor, starts all threads running
        disruptor.start();
        return disruptor;
    }


}