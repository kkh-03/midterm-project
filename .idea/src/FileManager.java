import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    public static void save(String filename, List<Lecture> lectures) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(lectures);
            System.out.println("💾 저장 완료: " + filename);
        } catch (IOException e) {
            System.out.println("❌ 저장 실패: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Lecture> load(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            return (List<Lecture>) in.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("❗ 저장된 파일이 없습니다.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("❌ 불러오기 실패: " + e.getMessage());
        }
        return new ArrayList<>();
    }
}
