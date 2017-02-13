package Hello.deferred;

import com.lmax.disruptor.RingBuffer;

public class PeticionProducer
{
    private final RingBuffer<Peticion> ringBuffer;

    public PeticionProducer(RingBuffer<Peticion> ringBuffer)
    {
        this.ringBuffer = ringBuffer;
    }

    public void onData(Peticion bb)
    {
        long sequence = ringBuffer.next();  // Grab the next sequence
        try
        {
            Peticion dato = ringBuffer.get(sequence); // Get the entry in the Disruptor
            // for the sequence
            dato.set(bb);  // Fill with data
        }
        finally
        {
            ringBuffer.publish(sequence);
        }
    }
}