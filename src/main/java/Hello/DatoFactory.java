package Hello;

import com.lmax.disruptor.EventFactory;

public class DatoFactory implements EventFactory<Dato>
{
    public Dato newInstance()
    {
        return new Dato();
    }
}