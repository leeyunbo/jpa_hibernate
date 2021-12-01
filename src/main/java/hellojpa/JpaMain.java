package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.transaction.Transaction;

public class JpaMain {

    private static  EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpa_basic_db");


    public static void main(String[] args) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Member member = new Member();
        member.setId(1L);
        member.setName("hello");

        entityManager.persist(member);

        for(int i=0; i<10; i++) {
            Member findMember = entityManager.find(Member.class, 1L);
            System.out.println(findMember);
        }

        transaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    /**
     * 동일성 보장
     * 1차 캐시에 존재하는 엔티티는 몇십, 몇백번을 읽어도 동일한 엔티티
     */
    void sameRead() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Member member = new Member();
        member.setId(1L);
        member.setName("hello");

        entityManager.persist(member);

        for(int i=0; i<10; i++) {
            Member findMember = entityManager.find(Member.class, 1L);
            System.out.println(findMember);
        }

        transaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    /**
     * 1차 캐시에서 읽어오기
     * SELECT 쿼리가 X
     */
    void readCache() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Member member = new Member();
        member.setId(1L);
        member.setName("hello");

        entityManager.persist(member);
        Member findMember = entityManager.find(Member.class, 1L);

        System.out.println("id : " + findMember.getId());
        System.out.println("name : " + findMember.getName());

        transaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    void updateMember() {
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
    }

    void deleteMember() {
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
    }

    void findMember() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {
            Member findMember = entityManager.find(Member.class, "hello");
            transaction.commit();
            System.out.println(findMember);
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    void saveMember() {
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

    }
}
