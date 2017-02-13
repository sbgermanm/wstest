package Hello.deferred;

import com.lmax.disruptor.EventFactory;

public class PeticionFactory implements EventFactory<Peticion>
{
    public Peticion newInstance()
    {
        return new Peticion();
    }
}