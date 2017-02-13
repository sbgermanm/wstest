package Hello.competableFuture;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class AsyncController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final TaskService taskService;



    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    public AsyncController(TaskService taskService) {
        this.taskService = taskService;
    }

    @RequestMapping(value = "/completable")
    public DeferredResult<Dato> executeSlowTask(@RequestParam(value = "name", defaultValue = "World") String name) {
        logger.info("Request " + name + " received");
        Dato dato = new Dato(counter.getAndIncrement(), name);
        DeferredResult<Dato> deferredResult = new DeferredResult<>();

        CompletableFuture.supplyAsync(() -> taskService.execute(dato)).whenCompleteAsync((result, throwable) -> deferredResult.setResult(result));



        logger.info("Servlet thread  " + name + " released");

        return deferredResult;
    }






}