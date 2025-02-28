import com.tolrom.javaInit.db.Database;
import com.tolrom.javaInit.model.Category;
import com.tolrom.javaInit.model.Role;
import com.tolrom.javaInit.model.Task;
import com.tolrom.javaInit.model.User;
import com.tolrom.javaInit.repository.CategoryRepository;
import com.tolrom.javaInit.repository.TaskRepository;
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
        Role userRole = new Role("USER");
        Task newTask = new Task(
                "Maquettage2",
                "Réalisation de la maquette",
                newUser
        );
        Category[] categories = {new Category("Cinema"), new Category("Travail")};

        // System.out.println((UserRepository.save(newUser, userRole)));
        // System.out.println(UserRepository.update(newUser, "max@mail.fr"));
        // System.out.println(UserRepository.findAll());
        // System.out.println((TaskRepository.save(newTask, categories)));
        System.out.println(TaskRepository.findAll());
    }
}
