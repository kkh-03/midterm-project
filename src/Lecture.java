import java.io.Serializable;
import java.time.LocalTime;

public class Lecture implements Serializable {
    private final String subject;
    private final String day;
    private LocalTime startTime;
    private LocalTime endTime;
    private String room;

    public Lecture(String subject, String day, LocalTime startTime, LocalTime endTime, String room) {
        this.subject = subject;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.room = room;
    }

    public String getSubject() { return subject; }
    public String getDay() { return day; }
    public LocalTime getStartTime() { return startTime; }
    public LocalTime getEndTime() { return endTime; }
    public String getRoom() { return room; }

    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }
    public void setRoom(String room) { this.room = room; }

    public boolean isOverlap(Lecture other) {
        if (!this.day.equalsIgnoreCase(other.day)) return false;
        return !(this.endTime.isBefore(other.startTime) || this.startTime.isAfter(other.endTime));
    }

    @Override
    public String toString() {
        return day + " | " + subject + " | " + startTime + " ~ " + endTime + " | " + room;
    }
}
