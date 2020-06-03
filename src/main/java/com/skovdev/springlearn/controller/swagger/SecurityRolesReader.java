package com.skovdev.springlearn.controller.swagger;

import com.google.common.base.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.spring.web.DescriptionResolver;
import springfox.documentation.swagger.common.SwaggerPluginSupport;

import java.util.Arrays;

/**
 * This class configures Swagger in that way, that if endpoint has @Secured annotation, than role name, specified in that annotation, will be added to API dock
 */
@Component
@Order(SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER)
@Slf4j
public class SecurityRolesReader implements springfox.documentation.spi.service.OperationBuilderPlugin {

    private final DescriptionResolver descriptions;

    @Autowired
    public SecurityRolesReader(DescriptionResolver descriptions) {
        this.descriptions = descriptions;
    }

    @Override
    public void apply(OperationContext context) {
        try {

            String apiRoleAccessNoteText = "No roles required to access this endpoint";
            Optional<Secured> preAuthorizeAnnotation = context.findAnnotation(Secured.class);
            if (preAuthorizeAnnotation.isPresent()) {
                apiRoleAccessNoteText = "Roles required: " + Arrays.toString(preAuthorizeAnnotation.get().value());
            }
            // add the note text to the Swagger UI
            context.operationBuilder().notes(descriptions.resolve(apiRoleAccessNoteText));
        } catch (Exception e) {
            log.error("Error when creating swagger documentation for security roles: " + e);
        }
    }

    @Override
    public boolean supports(DocumentationType delimiter) {
        return SwaggerPluginSupport.pluginDoesApply(delimiter);
    }
}