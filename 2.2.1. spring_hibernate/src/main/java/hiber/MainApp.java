package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;




public class MainApp {
   private static final Logger logger = (Logger) LogManager.getLogger(MainApp.class);

   public static void main(String[] args) throws SQLException {
      logger.info("Starting application");
      AnnotationConfigApplicationContext context = null;
      try {
         context = new AnnotationConfigApplicationContext(AppConfig.class);
         UserService userService = context.getBean(UserService.class);

         Car car1 = new Car("gaz", 2114);
         Car car2 = new Car("uaz", 2110);
         Car car3 = new Car("autovaz", 007);
         Car car4 = new Car("volga", 3110);

         User user1 = new User("User1", "Lastname1", "user1@mail.ru", car1);
         User user2 = new User("User2", "Lastname2", "user2@mail.ru", car2);
         User user3 = new User("User3", "Lastname3", "user3@mail.ru", car3);
         User user4 = new User("User4", "Lastname4", "user4@mail.ru", car4);

         logger.info("Adding users");
         userService.add(user1);
         userService.add(user2);
         userService.add(user3);
         userService.add(user4);
         logger.info("Users added successfully");

         logger.info("Listing all users");
         List<User> users = userService.listUsers();
         for (User user : users) {
            logger.info("User details: Id = {}, First Name = {}, Last Name = {}, Email = {}",
                    user.getId(), user.getFirstName(), user.getLastName(), user.getEmail());
            System.out.println("Id = " + user.getId());
            System.out.println("First Name = " + user.getFirstName());
            System.out.println("Last Name = " + user.getLastName());
            System.out.println("Email = " + user.getEmail());
            System.out.println();

            logger.info("Getting user by car model: tazic and series: 2110");
            System.out.println("_____________________________________________");
            System.out.println(userService.getUserByCar("tazic", 2110));
         }
         logger.info("Listed all users successfully");

      } catch (Exception e) {
         logger.error("An error occurred: ", e);
      } finally {
         if (context != null) {
            context.close();
            logger.info("Application context closed");
         }
      }
      logger.info("Application finished");
   }
}