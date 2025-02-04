public class User {
    private String username;
    private String email;
    private String password; // Store hashed password in DB

    public User(String username, String email) {
        this.username = username;
        this.email = email;
//        this.password = password;
    }

    //Getters and Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Username: ").append(username).append("\n");
        sb.append("Email: ").append(email);
        return sb.toString();
    }
}

