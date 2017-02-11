package Hello;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class AsyncDeferredController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final TaskService taskService;



    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    private static final RingBuffer<DatoDeferred> ringBuffer = getRingBuffer();

    @Autowired
    public AsyncDeferredController(TaskService taskService) {
        this.taskService = taskService;
    }

    @RequestMapping(value = "/deferred")
    public DeferredResult<Dato> executeSlowTask(@RequestParam(value = "name", defaultValue = "World") String name) {
        logger.info("Request " + name + " received");
        DeferredResult<Dato> deferredResult = new DeferredResult<>();
//        CompletableFuture.supplyAsync(taskService::execute())
//                .whenCompleteAsync((result, throwable) -> deferredResult.setResult(result));


        EventDeferredProducer producer = new EventDeferredProducer(ringBuffer);
        DatoDeferred dato = new DatoDeferred(counter.incrementAndGet(),
                String.format(template, name), deferredResult);
        producer.onData(dato);



        logger.info("Servlet thread  " + name + " released");

        return deferredResult;
    }





    private static RingBuffer<DatoDeferred> getRingBuffer() {
        Disruptor<DatoDeferred> disruptor = startDisruptor();

        return disruptor.getRingBuffer();
    }


    private static Disruptor<DatoDeferred> startDisruptor() {
        // Executor that will be used to construct new threads for consumers
        Executor executor = Executors.newFixedThreadPool(100);

        // The factory for the event
        DatoDeferredFactory factory = new DatoDeferredFactory();

        // Specify the size of the ring buffer, must be power of 2.
        int bufferSize = 8192;

        // Construct the Disruptor
        Disruptor<DatoDeferred> disruptor = new Disruptor<>(factory, bufferSize, executor);

        // Connect the handler
        disruptor.handleEventsWith(new ConsumerEventDeferredHandler());

        // Start the Disruptor, starts all threads running
        disruptor.start();
        return disruptor;
    }


}