package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {
        JpaMain jpaMain = new JpaMain();
        jpaMain.findMember();
    }

    void updateMember() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpa_basic_db");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {
            Member findMember = entityManager.find(Member.class, 1L);
            findMember.setName("helloJPA");
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }

        entityManagerFactory.close();
    }

    void deleteMember() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpa_basic_db");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {
            Member findMember = entityManager.find(Member.class, 1L);
            entityManager.remove(findMember);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }

        entityManagerFactory.close();
    }

    void findMember() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpa_basic_db");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {
            Member findMember = entityManager.find(Member.class, 1L);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }

        entityManagerFactory.close();
    }

    void saveMember() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpa_basic_db");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {
            Member member = new Member();
            member.setId(1L);
            member.setName("hello");
            entityManager.persist(member);

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }

        entityManagerFactory.close();
    }
}
