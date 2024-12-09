package com.project.grpcproject.exception;

public class Exception extends RuntimeException{

        public Exception(String message) {
            super(message);
        }

        // UserNotFoundException
        public static class UserNotFoundException extends Exception {
            public UserNotFoundException(String message) {
                super(message);
            }
        }

        // InvalidUserInputException
        public static class InvalidUserInputException extends Exception {
            public InvalidUserInputException(String message) {
                super(message);
            }
        }
}
