package blackbird.com.recyclerviewdemo.tv.tab;

public class TabTitleModel {

    String title;
    int id;

    public TabTitleModel(String title, int id) {
        this.title = title;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "TabTitleModel{" +
                "title='" + title + '\'' +
                ", id=" + id +
                '}';
    }
}
