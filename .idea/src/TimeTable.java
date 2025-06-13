import java.time.LocalTime;
import java.util.*;

// 수업들을 리스트로 관리하는 클래스
public class TimeTable {
    private final List<Lecture> lectures = new ArrayList<>();  // 수업 목록

    public List<Lecture> getLectures() {
        return lectures;
    }

    // 수업 추가 (시간 중복 검사 포함)
    public boolean addLecture(Lecture newLecture) {
        for (Lecture l : lectures) {
            if (l.isOverlap(newLecture)) {
                return false;
            }
        }
        lectures.add(newLecture);
        return true;
    }

    // 수업 삭제
    public boolean removeLecture(String subject, String day, LocalTime startTime, LocalTime endTime) {
        return lectures.removeIf(l ->
                l.getSubject().equalsIgnoreCase(subject) &&
                        l.getDay().equalsIgnoreCase(day) &&
                        l.getStartTime().equals(startTime) &&
                        l.getEndTime().equals(endTime)
        );
    }

    // 수업 수정
    public boolean editLecture(String subject, LocalTime newStart, LocalTime newEnd, String newRoom) {
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

    // 과목명 또는 요일로 수업 검색
    public void searchLecture(String keyword) {
        boolean found = false;
        System.out.println("🔍 검색 결과:");
        for (Lecture l : lectures) {
            if (l.getSubject().toLowerCase().contains(keyword.toLowerCase()) ||
                    l.getDay().equalsIgnoreCase(keyword)) {
                System.out.println(l);
                found = true;
            }
        }
        if (!found) {
            System.out.println("❌ 해당 과목을 찾을 수 없습니다.");
        }
    }

    // 시간표 출력 (요일, 시간 순 정렬)
    public void printLectures() {
        if (lectures.isEmpty()) {
            System.out.println("⛔ 등록된 수업이 없습니다.");
            return;
        }

        lectures.stream()
                .sorted((a, b) -> {
                    int dayCompare = dayOrder(a.getDay()) - dayOrder(b.getDay());
                    return (dayCompare != 0) ? dayCompare : a.getStartTime().compareTo(b.getStartTime());
                })
                .forEach(System.out::println);
    }
    //주간시간표
    public void printWeeklySchedule() {
        if (lectures.isEmpty()) {
            System.out.println("⛔ 등록된 수업이 없습니다.");
            return;
        }

        String[] days = {"MON", "TUE", "WED", "THU", "FRI"};
        LocalTime startTime = LocalTime.of(8, 0);
        LocalTime endTime = LocalTime.of(18, 0);

        // 헤더 출력
        System.out.printf("%-8s", "시간");
        for (String day : days) {
            System.out.printf("| %-12s ", day);
        }
        System.out.println();
        System.out.println("-".repeat(8 + days.length * 16));

        // 1tlrks 단위 시간 출력
        for (LocalTime time = startTime; !time.isAfter(endTime.minusHours(1)); time = time.plusHours(1)) {
            System.out.printf("%-8s", time);

            for (String day : days) {
                String classInfo = "-";
                for (Lecture lec : lectures) {
                    if (lec.getDay().equalsIgnoreCase(day) &&
                            !time.isBefore(lec.getStartTime()) &&
                            time.isBefore(lec.getEndTime())) {
                        classInfo = lec.getSubject() + "(" + lec.getRoom() + ")";
                        break;
                    }
                }
                System.out.printf("| %-12s ", classInfo);
            }
            System.out.println();
        }
    }


    // 요일을 숫자로 변환 (정렬용)
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
