package hibernate_basic;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaIdentityPerformanceCheck {

    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpa_basic_db");

    public static void main(String[] args) {
        JpaIdentityPerformanceCheck jpaIdentityPerformanceCheck = new JpaIdentityPerformanceCheck();
        jpaIdentityPerformanceCheck.batch();
    }


    void batch() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        //== 측정 시작 ==//
        long startTime = System.currentTimeMillis();
        try {
            //== 트랜잭션 시작 ==//
            transaction.begin();

            //== 데이터 10만회 삽입 ==//
            System.out.println("=== INSERT START ===");
            for (int i = 0; i < 5; i++) {
                IdentityMember member = new IdentityMember();
                member.setName("yunbok");
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
}
