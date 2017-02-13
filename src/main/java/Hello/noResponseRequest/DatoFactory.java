package Hello.noResponseRequest;

import Hello.noResponseRequest.Dato;
import com.lmax.disruptor.EventFactory;

public class DatoFactory implements EventFactory<Dato>
{
    public Dato newInstance()
    {
        return new Dato();
    }
}