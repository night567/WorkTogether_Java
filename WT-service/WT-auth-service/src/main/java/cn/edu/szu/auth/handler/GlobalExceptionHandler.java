package cn.edu.szu.auth.handler;

import cn.edu.szu.auth.exception.NameConflictException;
import cn.edu.szu.auth.exception.RoleNotFoundException;
import cn.edu.szu.common.pojo.Code;
import cn.edu.szu.common.pojo.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
    /**\
     * 处理名字冲突
     * @param ex
     * @return
     */
    @ExceptionHandler(NameConflictException.class)
    @ResponseBody
    public Result handleNameConflictException(NameConflictException ex) {
        return new Result(Code.SAVE_ERR,null,ex.getMessage());
    }

    /**
     * 没有id异常
     * @param ex
     * @return
     */
    @ExceptionHandler(RoleNotFoundException.class)
    @ResponseBody
    public Result handleRoleNotFoundException(RoleNotFoundException ex) {
        return new Result(Code.ID_NOT_FOUND_ERR,null,ex.getMessage());
    }

}
