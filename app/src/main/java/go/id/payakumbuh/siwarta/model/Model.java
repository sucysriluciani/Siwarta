package go.id.payakumbuh.siwarta.model;

/**
 * Created by anggrayudi on 15/02/18.
 */

public class Model {

    public int id;
    public String title, summary;

    public Model(int id, String title, String summary) {
        this.id = id;
        update(title, summary);
    }

    public void update(String title, String summary){
        this.title = title;
        this.summary = summary;
    }
}
