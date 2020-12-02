package gr.nikolis.controller;

import gr.nikolis.service.web.EmailService;
import gr.nikolis.sql.models.User;
import gr.nikolis.sql.service.UserService;
import gr.nikolis.mappings.register.RegisterMappings;
import gr.nikolis.mappings.register.ViewNames;
import gr.nikolis.validator.UserValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.UUID;

@Slf4j
@Controller
public class RegisterController {
    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserValidator userValidator;

    // Return registration form template
    @GetMapping(RegisterMappings.REGISTER)
    public ModelAndView showRegistrationPage(ModelAndView modelAndView, User user) {
        modelAndView.addObject("user", user);
        modelAndView.setViewName(ViewNames.REGISTER);
        return modelAndView;
    }

    // Process form input data
    @PostMapping(RegisterMappings.REGISTER)
    public ModelAndView processRegistrationForm(@Valid User user, BindingResult bindingResult, HttpServletRequest request, ModelAndView modelAndView) {
        userValidator.validate(user, bindingResult);

        if (!bindingResult.hasErrors()) {
            user.setDisabled(true);             // Disable user until they click on confirmation link in email
            user.setConfirmationToken(UUID.randomUUID().toString());

            userService.initSave(user);
            emailService.constructEmail(request, user);

            modelAndView.addObject("confirmationMessage", "A confirmation e-mail has been sent to " + user.getEmail());
        }
        modelAndView.setViewName(ViewNames.REGISTER);
        return modelAndView;
    }

    @GetMapping(RegisterMappings.CONFIRM)
    public ModelAndView showConfirmationMessage(ModelAndView modelAndView, @RequestParam String token) {
        User user = userService.findByConfirmationToken(token);

        if (user == null) { // No token found in DB
            modelAndView.addObject("invalidToken", "Oops! This is an invalid confirmation link.");
        } else { // Token found
            modelAndView.addObject("successMessage", "Your Account has been confirmed");
            user.setDisabled(false);
            userService.save(user);
        }
        modelAndView.setViewName(ViewNames.CONFIRM);
        return modelAndView;
    }
}
