import java.time.LocalTime;
import java.util.*;

public class TimeTable {
    private final List<Lecture> lectures = new ArrayList<>();

    public List<Lecture> getLectures() {
        return lectures;
    }

    public boolean addLecture(Lecture newLecture) {
        for (Lecture l : lectures) {
            if (l.isOverlap(newLecture)) {
                return false;
            }
        }
        lectures.add(newLecture);
        return true;
    }

    public boolean removeLectureByDetails(String subject, String day, LocalTime startTime, LocalTime endTime) {
        return lectures.removeIf(l ->
                l.getSubject().equalsIgnoreCase(subject) &&
                        l.getDay().equalsIgnoreCase(day) &&
                        l.getStartTime().equals(startTime) &&
                        l.getEndTime().equals(endTime)
        );
    }

    public boolean editLectureBySubject(String subject, LocalTime newStart, LocalTime newEnd, String newRoom) {
        for (Lecture lec : lectures) {
            if (lec.getSubject().equalsIgnoreCase(subject)) {
                Lecture temp = new Lecture(subject, lec.getDay(), newStart, newEnd, newRoom);
                for (Lecture other : lectures) {
                    if (!other.getSubject().equalsIgnoreCase(subject) && other.isOverlap(temp)) {
                        return false;
                    }
                }
                lec.setStartTime(newStart);
                lec.setEndTime(newEnd);
                lec.setRoom(newRoom);
                return true;
            }
        }
        return false;
    }

    public void searchLectures(String keyword) {
        boolean found = false;
        System.out.println("🔍 검색 결과:");
        for (Lecture l : lectures) {
            if (l.getSubject().toLowerCase().contains(keyword.toLowerCase())
                    || l.getDay().equalsIgnoreCase(keyword)) {
                System.out.println(l);
                found = true;
            }
        }
        if (!found) {
            System.out.println("❌ 해당 과목을 찾을 수 없습니다.");
        }
    }

    public void printAllLectures() {
        if (lectures.isEmpty()) {
            System.out.println("⛔ 등록된 수업이 없습니다.");
            return;
        }

        lectures.stream()
                .sorted((a, b) -> {
                    int dayCompare = dayOrder(a.getDay()) - dayOrder(b.getDay());
                    return dayCompare != 0 ? dayCompare : a.getStartTime().compareTo(b.getStartTime());
                })
                .forEach(System.out::println);
    }

    private int dayOrder(String day) {
        return switch (day.toUpperCase()) {
            case "MON" -> 1;
            case "TUE" -> 2;
            case "WED" -> 3;
            case "THU" -> 4;
            case "FRI" -> 5;
            case "SAT" -> 6;
            case "SUN" -> 7;
            default -> 8;
        };
    }
}
