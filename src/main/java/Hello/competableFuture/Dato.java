package Hello.competableFuture;

/**
 * Created by sgerman on 17/01/2017.
 */
public class Dato {

    private  long id;
    private  String content;

    public Dato(long id, String content) {
        this.id = id;
        this.content = content;
    }
    public Dato() {
        this.id = 0;
        this.content = "";
    }
    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void set(Dato dato) {
        this.id = dato.getId();
        this.content = dato.getContent();
    }
}
