import java.io.Serializable;
import java.time.LocalTime;

// 하나의 수업 정보를 담는 클래스
public class Lecture implements Serializable {
    private final String subject;     // 과목명
    private final String day;         // 요일
    private LocalTime startTime;      // 시작 시간
    private LocalTime endTime;        // 종료 시간
    private String room;              // 강의실

    public Lecture(String subject, String day, LocalTime startTime, LocalTime endTime, String room) {
        this.subject = subject;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.room = room;
    }

    // getter 메서드들
    public String getSubject() { return subject; }
    public String getDay() { return day; }
    public LocalTime getStartTime() { return startTime; }
    public LocalTime getEndTime() { return endTime; }
    public String getRoom() { return room; }

    // setter 메서드들
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }
    public void setRoom(String room) { this.room = room; }

    // 두 수업 간 시간 겹침 여부 확인
    public boolean isOverlap(Lecture other) {
        if (!this.day.equalsIgnoreCase(other.day)) return false;
        return !(this.endTime.isBefore(other.startTime) || this.startTime.isAfter(other.endTime));
    }

    // 수업 정보를 문자열로 반환
    @Override
    public String toString() {
        return day + " | " + subject + " | " + startTime + " ~ " + endTime + " | " + room;
    }
}
