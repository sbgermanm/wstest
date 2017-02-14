package Hello;

import com.lmax.disruptor.dsl.Disruptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    public static final int CONSUMER_N_THREADS = 4;
    public static final int LONG_TASK_TIME = 1;
    public static final int RING_BUFFER_SIZE = 65536;


}