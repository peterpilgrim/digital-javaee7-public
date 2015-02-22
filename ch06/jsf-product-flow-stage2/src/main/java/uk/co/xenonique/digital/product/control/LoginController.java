package uk.co.xenonique.digital.product.control;

import uk.co.xenonique.digital.product.boundary.UserProfileService;
import uk.co.xenonique.digital.product.entity.UserProfile;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static uk.co.xenonique.digital.product.utils.AppConsts.*;

/**
 * The type LoginController
 *
 * @author Peter Pilgrim
 */
@Named("loginController")
@RequestScoped
public class LoginController {

    private String username;
    private String password;

    @Inject
    UserProfileService userProfileService;

    public boolean isLoggedIn() {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(LOGIN_KEY) != null;
    }

    public String login() {
        List<UserProfile> users = userProfileService.findByUsername(username);
        if ( users.isEmpty()) {
            throw new IllegalArgumentException("unknown user");
        }
        if ( !users.get(0).getPassword().equals(password)) {
            throw new IllegalArgumentException("invalid password");
        }

        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(LOGIN_KEY, username);
        String lastInputPath = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(LAST_INPUT_PATH);
        if ( lastInputPath != null) {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(LAST_INPUT_PATH);
            return lastInputPath;
        }
        else {
            return "/protected/index?faces-redirect=true";
        }
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(LOGIN_KEY);
        return "/index?faces-redirect=true";
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
}
