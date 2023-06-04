package com.example.ecommerciallo.constants;

public interface Messages {
    String USERNAME_IS_TAKEN= "'%s' is already taken !";
    String EMAIL_IS_TAKEN= "'%s' is already taken !";
    String PHONE_IS_TAKEN= "'%s' is already taken !";
    String USER_CREATED="User successfully created !";
    String USER_BY_USERNAME_NOT_FOUND= "User with username: '%s' not found !";
    String INVALID_JWT="Invalid JWT token: {}";
    String JWT_EXPIRED="JWT token is expired: {}";
    String JWT_UNSUPPORTED="JWT token is unsupported: {}";
    String JWT_EMPTY="JWT claims string is empty: {}";
    String FAILED_SET_AUTH="Cannot set user authentication: {}";
    String LOGIN_SUCCESS= "'%s', you signed in successfully !";
    String ROLE_NOT_FOUND= "Role: \'%s\' not found !";
    String USER_IS_LOCKED= "'%s' is locked !";
    String USER_IS_DISABLED= "'%s' is disabled !";
    String BAD_CREDENTIALS= "Username or password incorrect !";
    String WENT_WRONG= "Something went wrong!";

}
