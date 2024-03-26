package dev.jefferson.email.infrastructure.error;

import dev.jefferson.email.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
@RequiredArgsConstructor
public class ErrorNotificationAspect {
    private final EmailService emailService;

    @Pointcut("@within(dev.jefferson.email.infrastructure.error.ErrorNotification) || @annotation(dev.jefferson.email.infrastructure.error.ErrorNotification)")
    public void errorNotificationPointCut() {}

    @AfterThrowing(pointcut = "errorNotificationPointCut()", throwing = "e")
    public void errorNotification(final Exception e) {
        emailService.sendExceptionEmail(e);
    }
}
