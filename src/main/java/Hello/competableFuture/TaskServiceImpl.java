package Hello.competableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static Hello.Application.LONG_TASK_TIME;

@Service
public class TaskServiceImpl implements TaskService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Dato execute(Dato dato) {
        logger.info("Dato: id=" + dato.getId() + " content=" + dato.getContent());
        try {

            Thread.sleep(LONG_TASK_TIME);
            logger.info("Procesado Dato: id=" + dato.getId() + " content=" + dato.getContent());

            return new Dato(dato.getId(), "Ha sido procesado : " + dato.getContent());
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
    }

}