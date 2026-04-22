package koreatech.cse.domain.dto;

public class CounselingCreateRequest {
    private int studentUserId;
    private int year;
    private String place;
    private String date;
    private String contents;
    private String suggestions;

    public int getStudentUserId() { return studentUserId; }
    public void setStudentUserId(int studentUserId) { this.studentUserId = studentUserId; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public String getPlace() { return place; }
    public void setPlace(String place) { this.place = place; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getContents() { return contents; }
    public void setContents(String contents) { this.contents = contents; }

    public String getSuggestions() { return suggestions; }
    public void setSuggestions(String suggestions) { this.suggestions = suggestions; }
}
