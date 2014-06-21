package uk.co.xenonique.digital;

import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 * The type LoginControllerJV
 *
 * @author Peter Pilgrim
 */
//@ManagedBean(name = "loginControllerJV")
@Named("loginControllerJV")
@ViewScoped
public class LoginControllerJV {
    private String username;
    private String password;
    private String email;
    private String verificationId;

    public String login() {
        return "/jsf-validation/loginComplete?redirect=true";
    }

    public String cancel() {
        return "/index.xhtml?redirect=true";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVerificationId() {
        return verificationId;
    }

    public void setVerificationId(String verificationId) {
        this.verificationId = verificationId;
    }
}
