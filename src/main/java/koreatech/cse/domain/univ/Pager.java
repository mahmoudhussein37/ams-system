package koreatech.cse.domain.univ;

public class Pager {
    private String text;
    private String link;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
    
    public void setFirstText() {
        text = "<i class='fa fa-angle-double-left'></i>";
    }
    
    public void setLastText() {
        text = "<i class='fa fa-angle-double-right'></i>";
    }
}
