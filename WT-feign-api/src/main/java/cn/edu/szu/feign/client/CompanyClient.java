package cn.edu.szu.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "company-service")
public interface CompanyClient {
    @GetMapping(value = "/api/company_user/selectUserIdsByCID")
    List<Long> selectUserIdsByCID(@RequestParam Long companyId);

    @GetMapping(value = "/api/company_user/selectUserIdsByCIDWithPage")
    List<Long> selectUserIdsByCIDWithPage(@RequestParam Long companyId, @RequestParam Integer pageNum, @RequestParam Integer pageSize);

    @GetMapping(value = "/api/company_user/deleteMember", headers = {"Connection=close"})
    boolean setMemberAsDeleted(@RequestParam Long memberId, @RequestParam Long companyId);

    @GetMapping(value = "/api/group/getUidByGroupUserId")
    Long selectUIDByGroupUserId(@RequestParam Long guid);
}
