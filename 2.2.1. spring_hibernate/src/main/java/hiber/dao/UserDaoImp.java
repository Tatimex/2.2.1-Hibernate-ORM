package hiber.dao;

import hiber.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImp implements UserDao {
   private static final Logger logger = (Logger) LogManager.getLogger(UserDaoImp.class);

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      logger.info("Adding user: {}", user);
      try {
         sessionFactory.getCurrentSession().save(user);
         logger.info("User added successfully: {}", user);
      } catch (Exception e) {
         logger.error("Error adding user: {}", user, e);
      }
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      logger.info("Listing all users");
      List<User> users = null;
      try {
         TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
         users = query.getResultList();
         logger.info("Users listed successfully");
      } catch (Exception e) {
         logger.error("Error listing users", e);
      }
      return users;
   }

   @Override
   public Optional<User> getUserByCar(String model, int series) {
      logger.info("Getting user by car model: {} and series: {}", model, series);
      Optional<User> userOptional = Optional.empty();
      String hql = "from User user where user.car.model = :model and user.car.series = :series";
      try {
         TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery(hql);
         query.setParameter("model", model).setParameter("series", series);
         User user = query.getSingleResult();
         userOptional = Optional.of(user);
         logger.info("User found: {}", user);
      } catch (NoResultException e) {
         logger.warn("No user found with car model: {} and series: {}", model, series);
      } catch (Exception e) {
         logger.error("Error getting user by car model: {} and series: {}", model, series, e);
      }
      return userOptional;
   }
}
