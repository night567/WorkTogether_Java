package cn.edu.szu.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "company-service")
public interface CompanyClient {
    @GetMapping("/api/company_user/selectUserIdsByCID")
    List<Long> selectUserIdsByCID(@RequestParam Long companyId);

    @GetMapping("/api/company_user/deleteMember")
   boolean setMemberAsDeleted(@RequestParam Long memberId,@RequestParam Long companyId);
}
