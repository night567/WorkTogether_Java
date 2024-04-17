package cn.edu.szu.company.handler;

import cn.edu.szu.common.pojo.Code;
import cn.edu.szu.common.pojo.Result;
import cn.edu.szu.company.exception.CompanyNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class CompanyExceptionHandler {
    /**\
     * 处理无法找到公司
     * @param ex
     * @return
     */
    @ExceptionHandler(CompanyNotFoundException.class)
    @ResponseBody
    public Result handleNameConflictException(CompanyNotFoundException ex) {
        return new Result(Code.ID_NOT_FOUND_ERR,null,ex.getMessage());
    }

    /**\
     * 处理数据库约束错误
     * @param ex
     * @return
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    public Result DataIntegrityViolationException(DataIntegrityViolationException ex) {
        return new Result(Code.SAVE_ERR,null,ex.getMessage());
    }
}
