package com.excellia.wallet.dto;

public class LoginResponse {
    private String token;
    private String userId;
    private String firstName;
    private String lastName;
    
    public LoginResponse() {}
    
    public LoginResponse(String token, String userId, String firstName, String lastName) {
        this.token = token;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
}