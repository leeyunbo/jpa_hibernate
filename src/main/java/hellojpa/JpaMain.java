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

        long id = 1L;

        long startTime = System.currentTimeMillis();
        transaction.begin();
        System.out.println("=== INSERT START ===");
        for(int i=0; i<100000; i++) {
            Member member = new Member();
            member.setId(id++);
            member.setName("hello");

            entityManager.persist(member);
        }
        System.out.println("=== INSERT END ===");

        System.out.println("=== COMMIT ===");
        transaction.commit();

        System.out.println("소요 시간 : " + (System.currentTimeMillis() - startTime)/1000.0 + " milli seconds");

        entityManager.close();
        entityManagerFactory.close();
    }

    /**
     * INSERT 쿼리는 모아서 commit 시점에 한번에 보냄.
     * 배치 설정을 활용하면 버퍼링 기능 사용 가능(데이터를 모아서 한번의 쿼리로 쫙!)
     */
    void batch() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        long id = 1L;

        long startTime = System.currentTimeMillis();
        transaction.begin();
        System.out.println("=== INSERT START ===");
        for(int i=0; i<100000; i++) {
            Member member = new Member();
            member.setId(id++);
            member.setName("hello");

            entityManager.persist(member);
        }
        System.out.println("=== INSERT END ===");

        System.out.println("=== COMMIT ===");
        transaction.commit();

        System.out.println("소요 시간 : " + (System.currentTimeMillis() - startTime)/1000.0 + " milli seconds");

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
