package com.fastcampus.boardserver.aop;

import com.fastcampus.boardserver.utils.SessionUtil;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


@Log4j2
@Aspect
@Component
public class LoginCheckAspect {
    @Around("@annotation(com.fastcampus.boardserver.aop.LoginCheck) && @annotation(loginCheck)")
    public Object adminLoginCheck(ProceedingJoinPoint proceedingJoinPoint, LoginCheck loginCheck) throws Throwable {
        HttpSession session = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest().getSession();
        String id = switch (loginCheck.type()) {
            case ADMIN -> SessionUtil.getLoginAdminId(session);
            case USER -> SessionUtil.getLoginMemberId(session);
        };

        if (id == null) {
            log.error("id is null: {}", proceedingJoinPoint.toString());
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "로그인한 id값을 확인해주세요.");
        }

        Object[] modifiedArgs = proceedingJoinPoint.getArgs();
        if (modifiedArgs != null) modifiedArgs[0] = id;
        return proceedingJoinPoint.proceed(modifiedArgs);
    }
}