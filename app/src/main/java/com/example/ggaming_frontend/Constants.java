package com.example.ggaming_frontend;

public class Constants {
    //All Toast messages being used
    public static class ToastMessage {
        public static final String emptyInputError = "Please enter your email and password for authentication.";
        public static final String signInSuccess = "Sign in successfully!";
        public static final String signInFailure = "Invalid email/password!";
        public static final String registerSuccess = "Successfully registered";
        public static final String registerFailure = "Authentication failed, email must be unique and has correct form!";
    }

    //Fields of FireStore 'users' collection
    public static class FSUser {
        public static final String userCollection = "users";
        public static final String usernameField = "username";
        public static final String ageField = "age";
        public static final String countryField = "country";
        public static final String emailField = "email";
    }
}
