package tw.idv.cha102.g7.member.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tw.idv.cha102.g7.member.entity.Member;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {

    // 透過Email查詢會員的詳細資料
    public Member findByMemEmail(String memEmail);

    // 回傳值應該會是List<Member>，記得更改
    public Member findByMemNameContaining(String memName);

//    @Query(value = "SELECT mem_grade FROM members WHERE mem_id = memId for update", nativeQuery = true)
//    public Member findMemberLevelById(@Param("memId") Integer memId);
}
