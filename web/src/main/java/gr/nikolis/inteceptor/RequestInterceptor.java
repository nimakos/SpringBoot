package gr.nikolis.inteceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class RequestInterceptor implements HandlerInterceptor {

    /**
     * The request we get from the client.
     * This method is being hit before everything
     *
     * If this method return false the code stops executing,
     * and does not return anything to the client
     *
     * @param request  current HTTP request
     * @param response current HTTP response
     * @param handler  chosen handler to execute, for type and/or instance evaluation
     * @return {@code true} if the execution chain should proceed with the
     * next interceptor or the handler itself. Else, DispatcherServlet assumes
     * that this interceptor has already dealt with the response itself.
     * @throws Exception in case of errors
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.debug("preHandle method called. handler = {}", handler);
        log.debug("URL = {}", request.getRequestURL());
        return true;
    }

    /**
     * This Methods is being hit before the view has been established (before show anything into the client)
     * and after it has been mapped from the GameController class
     *
     * @param request      current HTTP request
     * @param response     current HTTP response
     * @param handler      handler that started asynchronous
     *                     execution, for type and/or instance examination
     * @param modelAndView the {@code ModelAndView} that the handler returned
     *                     (can also be {@code null})
     * @throws Exception in case of errors
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.debug("postHandle method called. handler = {}", handler);
        log.debug("URL = {}", request.getRequestURL());
        if (modelAndView != null) {
            log.debug("model = {}", modelAndView.getModel());
            log.debug("view = {}", modelAndView.getView());
        }
    }

    /**
     * This Methods is being hit, after everything is completed
     *
     * @param request  current HTTP request
     * @param response current HTTP response
     * @param handler  handler that started asynchronous
     *                 execution, for type and/or instance examination
     * @param ex       exception thrown on handler execution, if any
     * @throws Exception in case of errors
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.debug("afterCompletion method called. handler = {}", handler);
        log.debug("URL = {}", request.getRequestURL());
    }
}
