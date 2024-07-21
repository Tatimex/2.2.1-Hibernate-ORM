package hiber.service;

import hiber.dao.UserDao;
import hiber.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {
   private static final Logger logger = (Logger) LogManager.getLogger(UserServiceImp.class);

   @Autowired
   private UserDao userDao;

   @Transactional
   @Override
   public void add(User user) {
      logger.info("Adding user: {}", user);
      try {
         userDao.add(user);
         logger.info("User added successfully: {}", user);
      } catch (Exception e) {
         logger.error("Error adding user: {}", user, e);
      }
   }

   @Transactional(readOnly = true)
   @Override
   public List<User> listUsers() {
      logger.info("Listing all users");
      List<User> users = null;
      try {
         users = userDao.listUsers();
         logger.info("Users listed successfully");
      } catch (Exception e) {
         logger.error("Error listing users", e);
      }
      return users;
   }

   @Transactional(readOnly = true)
   @Override
   public Optional<User> getUserByCar(String model, int series) {
      logger.info("Getting user by car model: {} and series: {}", model, series);
      Optional<User> userOptional = Optional.empty();
      try {
         userOptional = userDao.getUserByCar(model, series);
         if (userOptional.isPresent()) {
            logger.info("User found: {}", userOptional.get());
         } else {
            logger.warn("No user found with car model: {} and series: {}", model, series);
         }
      } catch (Exception e) {
         logger.error("Error getting user by car model: {} and series: {}", model, series, e);
      }
      return userOptional;
   }
}