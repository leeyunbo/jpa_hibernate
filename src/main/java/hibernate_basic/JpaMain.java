package hibernate_basic;

import javax.persistence.*;
import java.sql.SQLIntegrityConstraintViolationException;

public class JpaMain {
    // 5 : 22.389
    // 10 : 22.232

    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpa_basic_db");


    public static void main(String[] args) {
        JpaMain jpaMain = new JpaMain();
        jpaMain.saveMember();
        jpaMain.batch();
    }

    void jpql() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        //== 트랜잭션 시작 ==//
        transaction.begin();

        //== Member 저장 ==//
        Member member = new Member();
        member.setName("yunbok");
        entityManager.persist(member);

        //== Member 조회 ==//
        Member findMember = (Member) entityManager.createQuery(
                "select m From Member m where m.name = 'yunbok'"
        ).getSingleResult();

        //== 트랜잭션 종료 ==//
        System.out.println("=== COMMIT ===");
        transaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    void selectUpdateDataInTransaction() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        //== 측정 시작 ==//
        try {
            //== 트랜잭션 시작 ==//
            transaction.begin();

            Member member = new Member();
            member.setName("yunbok");
            member.setId(1L);
            entityManager.persist(member);

            member.setName("yunbok2");

            Member findMember = (Member) entityManager.createQuery(
                    "select m From Member m where m.name = 'yunbok'"
            ).getSingleResult();
            System.out.println("findMember : {findMember.getId() : " + findMember.getId() + "findMember.getName() : " + findMember.getName() + "}");

            System.out.println("=== INSERT END ===");

            //== 트랜잭션 종료 ==//
            System.out.println("=== COMMIT ===");
            transaction.commit();

            //== 측정 종료 ==//
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }


    /**
     * INSERT 쿼리는 모아서 commit 시점에 한번에 보냄.
     * 배치 설정을 활용하면 버퍼링 기능 사용 가능(데이터를 모아서 한번의 쿼리로 쫙!)
     *
     */
    void batch() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        //== 측정 시작 ==//
        long startTime = System.currentTimeMillis();
        long id = 1L;
        try {
            //== 트랜잭션 시작 ==//
            transaction.begin();

            //== 데이터 10만회 삽입 ==//
            System.out.println("=== INSERT START ===");
            for (int i = 0; i < 5000; i++) {
                Member member = new Member();
                member.setName("yunbok");
                member.setId(id++);
                entityManager.persist(member);
            }

            System.out.println("=== INSERT END ===");

            //== 트랜잭션 종료 ==//
            System.out.println("=== COMMIT ===");
            transaction.commit();

            //== 측정 종료 ==//
            System.out.println("소요 시간 : " + (System.currentTimeMillis() - startTime) / 1000.0 + " milli seconds");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }
    /**
     * 동일성 보장
     * 1차 캐시에 존재하는 엔티티는 몇십, 몇백번을 읽어도 동일한 엔티티
     */
    void sameRead() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        //== 트랜잭션 시작 ==//
        transaction.begin();

        //== Member 저장 ==//
        Member member = new Member();
        member.setName("yunbok");
        entityManager.persist(member);

        //== Member 10회 조회 ==//
        for(int i=0; i<10; i++) {
            Member findMember = entityManager.find(Member.class, 1L);
            System.out.println(findMember);
        }

        //== 트랜잭션 종료 ==//
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

        //== 트랜잭션 시작 ==//
        transaction.begin();

        //== Member 저장 ==//
        Member member = new Member();
        member.setId(1L);
        member.setName("hello");
        entityManager.persist(member);

        //== 조회후 출력 ==//
        Member findMember = entityManager.find(Member.class, 1L);
        System.out.println("id : " + findMember.getId());
        System.out.println("name : " + findMember.getName());

        //== 트랜잭션 종료 ==//
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
            member.setId(192L);
            member.setName("hello");
            entityManager.persist(member);

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    void dirtyCheck() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        long id = 1L;

        //== 트랜잭션 시작 ==//
        transaction.begin();

        //== Member 저장 ==//
        Member member = new Member();
        member.setName("yunbok");
        entityManager.persist(member);

        //== Member 수정 ==//
        System.out.println("=== UPDATE START ===");
        Member findMember = entityManager.find(Member.class, 1L);
        findMember.setName("yunbok2");
        findMember.setName("yunbok3");
        findMember.setName("yunbok4");
        System.out.println("=== UPDATE END ===");

        //== 트랜잭션 종료 ==//
        System.out.println("=== COMMIT ===");
        transaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }
}
