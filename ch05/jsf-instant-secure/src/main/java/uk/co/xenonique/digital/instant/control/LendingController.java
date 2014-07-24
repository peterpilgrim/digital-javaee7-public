package uk.co.xenonique.digital.instant.control;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * The type LendingController
 *
 * @author Peter Pilgrim
 */

@Named("lendingController")
@ConversationScoped
public class LendingController implements Serializable {

    @Inject private Conversation    conversation;

    public void start() {
        if ( conversation.isTransient()) {
            conversation.begin();
        }
    }

    public void end() {
        if (!conversation.isTransient()) {
            conversation.end();
        }
    }

    public String gotoStart() {
        start();
        return "getting-started?faces-redirect=true";
    }

    public String doGettingStarted() {
        start();
        return "your-details?faces-redirect=true";
    }

    public String doYourDetails() {
        return "your-rate?faces-redirect=true";
    }

    public String doYourRate() {
        return "completion?faces-redirect=true";
    }

    public String doCompletion() {
        end();
        return "index?faces-redirect=true";
    }
}
