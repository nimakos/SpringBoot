package gr.nikolis.validator;

import gr.nikolis.sql.models.User;
import gr.nikolis.sql.service.UserService;
import com.nulabinc.zxcvbn.Strength;
import com.nulabinc.zxcvbn.Zxcvbn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "NotEmpty", "First name cannot be empty");

        Zxcvbn passwordCheck = new Zxcvbn();
        Strength strength = passwordCheck.measure(user.getPassword());
        if (strength.getScore() < 3)
            errors.rejectValue("password", "Size.userForm.password", "Your password is too weak. Choose a stronger one.");

        if (!user.getPassword().equals(user.getPasswordConfirm()))
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm", "Passwords not match");

        if (userService.findByEmail(user.getEmail()).isPresent())
            errors.rejectValue("username", "Duplicate.userForm.username", "Someone already has that email");
    }
}