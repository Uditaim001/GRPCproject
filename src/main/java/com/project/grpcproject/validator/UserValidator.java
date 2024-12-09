package com.project.grpcproject.validator;

import com.example.grpc.UserProto;
import com.project.grpcproject.exception.Exception;

public class UserValidator {
    public void validateUserInput(UserProto.User user) {
        if (user.getId() <= 0) {
            throw new Exception.InvalidUserInputException("User ID must be a positive integer");
        }
        if (user.getName().isEmpty() || user.getName().length() > 50) {
            throw new Exception.InvalidUserInputException("User name must be non-empty and less than 50 characters");
        }
        if (!user.getEmail().contains("@") || !user.getEmail().contains(".")) {
            throw new Exception.InvalidUserInputException("Invalid email format");
        }
        if (user.getDateOfBirth().isEmpty()) {
            throw new Exception.InvalidUserInputException("Date of birth must not be empty");
        }
    }
}
