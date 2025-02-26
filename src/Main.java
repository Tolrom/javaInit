import com.tolrom.javaInit.db.Database;
import com.tolrom.javaInit.model.User;
import com.tolrom.javaInit.repository.UserRepository;

public class Main {
    public static void main(String[] args) {
        Database.getConnection();
        User newUser = new User(
                "Maxime",
                "Morlot",
                "max@mail.fr",
                "123456"
        );
        UserRepository.save(newUser);
    }
}
