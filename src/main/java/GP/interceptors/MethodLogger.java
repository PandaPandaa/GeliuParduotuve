package GP.interceptors;

import GP.entities.LogException;
import GP.persistence.LoggerDAO;
import GP.usecases.CurrentUser;
import org.apache.commons.lang3.exception.ExceptionUtils;

import javax.enterprise.inject.Instance;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.servlet.http.HttpSession;
import java.io.FileWriter;
import java.io.Serializable;
import java.time.LocalDateTime;

@Interceptor
@LoggedInvocation
public class MethodLogger implements Serializable{

    @Inject
    private CurrentUser currentUser;

    @Inject
    private Instance<LoggerDAO> loggerDAO;

    @AroundInvoke
    public Object logMethodInvocation(InvocationContext context) throws Exception {
        String id = "";
        FileWriter logger = new FileWriter("log.txt");
        if(currentUser.getCurrentUser().getId() != null)
        {
            id = currentUser.getCurrentUser().getId().toString();
            logger.write("TIME = " + LocalDateTime.now() +
                    " CLASS = " + context.getMethod().getDeclaringClass().getName() +
                    " METHOD = " + context.getMethod().getName() +
                    " USER_ID = " + id);
        }
        else
        {
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession)fc.getExternalContext().getSession(false);
            id = session.getId();
            logger.write("TIME = " + LocalDateTime.now() +
                    " CLASS = " + context.getMethod().getDeclaringClass().getName() +
                    " METHOD = " + context.getMethod().getName() +
                    " SESSION_ID = " + id);
        }
        logger.close();

        Object result = null;
        try {
            result = context.proceed();
        }catch (Exception e){
            LogException log = new LogException();
            log.setDate(LocalDateTime.now());
            log.setStackTrace(ExceptionUtils.getStackTrace(e));
            log.setUser(id);
            loggerDAO.get().persist(log);
        }
        return result;
    }
}
