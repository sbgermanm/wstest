package Hello;

import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

public class LongEventProducer
{
    private final RingBuffer<Dato> ringBuffer;

    public LongEventProducer(RingBuffer<Dato> ringBuffer)
    {
        this.ringBuffer = ringBuffer;
    }

    public void onData(Dato bb)
    {
        long sequence = ringBuffer.next();  // Grab the next sequence
        try
        {
            Dato dato = ringBuffer.get(sequence); // Get the entry in the Disruptor
            // for the sequence
            dato.set(bb);  // Fill with data
        }
        finally
        {
            ringBuffer.publish(sequence);
        }
    }
}