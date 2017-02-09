package Hello;

import com.lmax.disruptor.EventHandler;

public class LongEventHandler implements EventHandler<Dato> {
    public void onEvent(Dato event, long sequence, boolean endOfBatch) {
        System.out.println("Dato: id=" + event.getId() + " content=" + event.getContent());


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Procesado Dato: id=" + event.getId() + " content=" + event.getContent());


    }
}