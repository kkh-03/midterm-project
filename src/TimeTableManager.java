import java.util.Scanner;

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
        }
    }
}