package Hello.noResponseRequest;

import Hello.noResponseRequest.ConsumerEventHandler;
import Hello.noResponseRequest.Dato;
import Hello.noResponseRequest.DatoFactory;
import Hello.noResponseRequest.EventProducer;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by sgerman on 17/01/2017.
 */
@RestController
public class ServicioController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    private static final RingBuffer<Dato> ringBuffer = getRingBuffer();
//    private static final Logger log = LoggerFactory.getLogger(ServicioController.class);

    @RequestMapping("/onlySendData")
    public Dato greeting(@RequestParam(value = "name", defaultValue = "World") String name) throws InterruptedException {
        // Get the ring buffer from the Disruptor to be used for publishing.
        System.out.println("Request recevied, name = " + name);

        EventProducer producer = new EventProducer(ringBuffer);
        Dato dato = new Dato(counter.incrementAndGet(),
                String.format(template, name));
        producer.onData(dato);
        System.out.println("Thead free, name = " + name);

        return dato;
    }

    private static RingBuffer<Dato> getRingBuffer() {
        Disruptor<Dato> disruptor = startDisruptor();

        return disruptor.getRingBuffer();
    }


    private static Disruptor<Dato> startDisruptor() {
        // Executor that will be used to construct new threads for consumers
        Executor executor = Executors.newCachedThreadPool();

        // The factory for the event
        DatoFactory factory = new DatoFactory();

        // Specify the size of the ring buffer, must be power of 2.
        int bufferSize = 8192;

        // Construct the Disruptor
        Disruptor<Dato> disruptor = new Disruptor<>(factory, bufferSize, executor);

        // Connect the handler
        disruptor.handleEventsWith(new ConsumerEventHandler());

        // Start the Disruptor, starts all threads running
        disruptor.start();
        return disruptor;
    }


}