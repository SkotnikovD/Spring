package com.skovdev.springlearn.error.handler;

import com.skovdev.springlearn.error.handler.model.ApiError;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorEndpointController implements ErrorController {

    private final ErrorAttributes errorAttributes;
    private final ErrorProperties errorProperties;

    /**
     * Create a new {@link ErrorEndpointController} instance.
     *
     * @param errorAttributes the error attributes
     */
    public ErrorEndpointController(ErrorAttributes errorAttributes, ServerProperties serverProperties) {
        this.errorAttributes = errorAttributes;
        this.errorProperties = serverProperties.getError();
    }

    @RequestMapping("${server.error.path:${error.path:/error}}")
    public ResponseEntity<ApiError> error(HttpServletRequest request) {
        WebRequest webRequest = new ServletWebRequest(request);
        Throwable exception = errorAttributes.getError(webRequest);
        HttpStatus status = getStatus(request);

        if (status == HttpStatus.NO_CONTENT) {
            return new ResponseEntity<>(status);
        }

        String clientMsg;

        switch (status) {
            case NOT_FOUND:
                clientMsg = "Requested address not found";
                break;
            case BAD_REQUEST:
                clientMsg = "Invalid request. Try again with another parameters";
                break;
            case INTERNAL_SERVER_ERROR:
                clientMsg = "Internal server error occurred. We know about the problem and already fixing it";
                break;
            case UNAUTHORIZED:
                clientMsg = "You don't have permissions for this action";
                break;
            default:
                clientMsg = "Error. Request can't be processed";
        }
        ApiError apiError = new ApiError(status, clientMsg, exception);
        return new ResponseEntity<>(apiError, status);
    }

    @Override
    public String getErrorPath() {
        return this.errorProperties.getPath();
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        try {
            return HttpStatus.valueOf(statusCode);
        } catch (Exception ex) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

}
