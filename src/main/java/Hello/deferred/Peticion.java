package Hello.deferred;

import Hello.noResponseRequest.Dato;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * Created by sgerman on 11/02/2017.
 */
public class Peticion {
    Dato dato;
    DeferredResult<Dato> deferredResult;

    public Peticion(Dato dato, DeferredResult<Dato> deferredResult) {
        this.dato = dato;
        this.deferredResult = deferredResult;
    }

    public Peticion() {
    }

    public Peticion(long l, String format, DeferredResult<Dato> deferredResult) {
        this.dato = new Dato(l, format);
        this.deferredResult = deferredResult;
    }

    public void set(Peticion bb) {
        this.dato = bb.getDato();
        this.deferredResult = bb.deferredResult;
    }


    public Dato getDato() {
        return dato;
    }

    public void setDato(Dato dato) {
        this.dato = dato;
    }

    public DeferredResult<Dato> getDeferredResult() {
        return deferredResult;
    }

    public void setDeferredResult(DeferredResult<Dato> deferredResult) {
        this.deferredResult = deferredResult;
    }
}
