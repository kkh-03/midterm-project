import java.io.Serializable;
import java.time.LocalTime;
import java.util.Scanner;
import java.utill.*;

public class TimeTableManager {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- 시간표 관리 프로그램 ---");
            System.out.println("1. 수업 추가");
            System.out.println("2. 수업 삭제");
            System.out.println("3. 시간표 보기");
            System.out.println("4. 저장하기");
            System.out.println("5. 종료");
            System.out.print("선택: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // 엔터 제거
            // 이 부분은 추후 switch문으로 채워 넣기
            class Lecture implements Serializable {
                String subject;         // 과목명
                String day;             // 요일 (예: MON, TUE)
                LocalTime startTime;    // 시작 시간 (HH:mm)
                LocalTime endTime;      // 종료 시간
                String room;            // 강의실

                // 생성자
                public Lecture(String subject, String day, LocalTime startTime, LocalTime endTime, String room) {
                    this.subject = subject;
                    this.day = day;
                    this.startTime = startTime;
                    this.endTime = endTime;
                    this.room = room;
                }

                // 출력용 메서드
                @Override
                public String toString() {
                    return day + " | " + subject + " | " + startTime + " ~ " + endTime + " | " + room;
                }
                class TimeTable {
                    // 수업 목록 저장할 리스트
                    List<Lecture> lectures = new ArrayList<>();

                    // 수업 추가 메서드 (겹침 확인은 Day 4에서 추가)
                    public boolean addLecture(Lecture newLecture) {
                        // 일단 무조건 추가 (내일 시간 충돌 체크 추가 예정)
                        lectures.add(newLecture);
                        return true;
                    }
                }
            }

        }
        }
    }
}