package jpql;

import jpql.domain.Address;
import jpql.domain.Member;
import jpql.domain.MemberType;
import jpql.domain.Team;
import jpql.dto.MemberDto;

import javax.persistence.*;
import java.util.List;

public class main {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx=em.getTransaction();
        tx.begin();
        try{
/*

            em.createQuery("select m.username from Member m").getResultList();

            em.createQuery("select m.team from Member m").getResultList();

            em.createQuery("select m.username from Team t join t.members m").getResultList();



            //fetch 조인

            //회원과 팀을 한번에 조인
            em.createQuery("select m from Member m join fetch m.team t",Member.class).getResultList()   ;
*/

            //회원 테스트
            Team teamA = new Team();
            teamA.setName("teamA");
            Team teamB = new Team();
            teamB.setName("teamB");

            em.persist(teamA);
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("member1");
            member1.setTeam(teamA);

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.setTeam(teamA);

            Member member3 = new Member();
            member3.setUsername("member3");
            member3.setTeam(teamB);

            em.persist(member1);
            em.persist(member2);
            em.persist(member3);

            em.flush();
            em.clear();

            String sql="select m from Member m join fetch m.team";
            String membersSql="select t from Team t join fetch t.members m";
            String distinctSql="select distinct t from Team t join fetch t.members";
            List<Team> result = em.createQuery(distinctSql, Team.class).getResultList();

            System.out.println("result.size() = " + result.size());

            tx.commit();
        }catch (Exception e){
            e.printStackTrace();
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();

    }
}
