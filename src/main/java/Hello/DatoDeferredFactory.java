package Hello;

import com.lmax.disruptor.EventFactory;

public class DatoDeferredFactory implements EventFactory<DatoDeferred>
{
    public DatoDeferred newInstance()
    {
        return new DatoDeferred();
    }
}