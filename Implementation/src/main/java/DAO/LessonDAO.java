// LessonDAO.java
package DAO;

import Lesson.Lesson;
import Utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class LessonDAO {

    public void saveLesson(Lesson lesson) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(lesson);
            transaction.commit();
            System.out.println("Lesson saved with ID: " + lesson.getId());
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public List<Lesson> getAllLessons() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Lesson", Lesson.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void truncateLessonsTable() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createNativeQuery("TRUNCATE TABLE Lesson CASCADE").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteLesson(Long lessonId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Lesson lesson = session.get(Lesson.class, lessonId);
            if (lesson != null) {
                session.delete(lesson);
            }
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
