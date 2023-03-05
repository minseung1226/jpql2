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

            /*for(int i=0;i<100;i++){
                Member member = new Member();
                member.setUsername("member"+i);
                member.setAge(i);
                em.persist(member);
            }*/

            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            member.setTeam(team);
            em.persist(member);

            Team team2 = new Team();
            team2.setName("aeamA");
            em.persist(team2);

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.setAge(10);
            member2.setTeam(team2);
            member2.setMemberType(MemberType.USER);
            em.persist(member2);


            em.flush();
            em.clear();

            /*String sql=
                    "select "+
                            "case when m.age<=10 then '학생요금' "+
                            " when m.age>=60 then '경로요금' "+
                            "else '일반요금' "+
                            "end from Member m";*/

            //String sql="select coalesce(m.username,'이름없는 회원') from Member m ";
            String sql="select nullif(m.username,'관리자') from Member m ";

            List<String> result = em.createQuery(sql, String.class).getResultList();


            //String sql="select m from Member m inner join m.team t";
            //String sql="select m from Member m where m.memberType='USER'";

            //String sql ="select i from Item i where type(i)=Book";

            //String sql = "select m from Member m where m.username is not null";

            //String sql = "select m from Member m where m.age between 0 and 10";

            //List<Member> members = em.createQuery(sql, Member.class).getResultList();

            //System.out.println("members.size() = " + members.size());

            /*List<Member> members = em.createQuery("select m from Member m", Member.class)
                    .setFirstResult(1)
                    .setMaxResults(10)
                    .getResultList();

            for (Member member : members) {
                System.out.println("member = " + member);
            }*/


            /*TypedQuery<Member> result1 = em.createQuery("select m from Member m", Member.class);

            List<Team> result2 = em.createQuery("select m.team from Member m", Team.class).getResultList();

            List<Team> result3 = em.createQuery("select t from Member m join m.team t", Team.class).getResultList();

            List<Address> result4 = em.createQuery("select o.address from Order o", Address.class).getResultList();

            List result5 = em.createQuery("select m.username,m.age from Member m").getResultList();

            List<MemberDto> result6 = em.createQuery("select new jpql.dto.MemberDto(m.username,m.age) from Member m", MemberDto.class).getResultList();
*/
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
