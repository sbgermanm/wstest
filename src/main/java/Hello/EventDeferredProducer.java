package Hello;

import com.lmax.disruptor.RingBuffer;

public class EventDeferredProducer
{
    private final RingBuffer<DatoDeferred> ringBuffer;

    public EventDeferredProducer(RingBuffer<DatoDeferred> ringBuffer)
    {
        this.ringBuffer = ringBuffer;
    }

    public void onData(DatoDeferred bb)
    {
        long sequence = ringBuffer.next();  // Grab the next sequence
        try
        {
            DatoDeferred dato = ringBuffer.get(sequence); // Get the entry in the Disruptor
            // for the sequence
            dato.set(bb);  // Fill with data
        }
        finally
        {
            ringBuffer.publish(sequence);
        }
    }
}