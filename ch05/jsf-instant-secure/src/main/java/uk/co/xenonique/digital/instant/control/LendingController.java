package uk.co.xenonique.digital.instant.control;

import uk.co.xenonique.digital.instant.boundary.ApplicantService;
import uk.co.xenonique.digital.instant.entity.Address;
import uk.co.xenonique.digital.instant.entity.Applicant;
import uk.co.xenonique.digital.instant.entity.ContactDetail;

import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

/**
 * The type LendingController
 *
 * @author Peter Pilgrim
 */

@Named("lendingController")
@ConversationScoped
public class LendingController implements Serializable {
    @EJB
    ApplicantService applicantService;

    @Inject
    private Conversation    conversation;

    public final static BigDecimal DEFAULT_LOAN_AMOUNT = new BigDecimal("10000");
    public final static BigDecimal DEFAULT_LOAN_RATE = new BigDecimal("5.50");

    private int dobDay;
    private int dobMonth;
    private String dobYear;
    private BigDecimal minimumLoanAmount = new BigDecimal("5000");
    private BigDecimal maximumLoanAmount = new BigDecimal("25000");
    private BigDecimal minimumLoanRate   = new BigDecimal("3.0");
    private BigDecimal maximumLoanRate   = new BigDecimal("9.0");


    private Applicant applicant;

    public LendingController() {
        applicant = new Applicant();
        applicant.setLoanAmount( DEFAULT_LOAN_AMOUNT);
        applicant.setLoanRate( DEFAULT_LOAN_RATE );

        applicant.setAddress(new Address());
        applicant.setContactDetail(new ContactDetail());
    }

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

    public String cancel() {
        end();
        return "index?faces-redirect=true";
    }

    public String jumpGettingStarted() {
        return "getting-started?faces-redirect=true";
    }

    public String doGettingStarted() {
        start();
        return "your-details?faces-redirect=true";
    }

    public String doYourDetails() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, dobDay);
        cal.set(Calendar.MONTH, dobMonth-1);
        int year = Integer.parseInt(dobYear);
        cal.set(Calendar.YEAR, year);

        applicant.getContactDetail().setDob(cal.getTime());
        System.out.printf("applicant=%s\n", applicant);
        return "your-rate?faces-redirect=true";
    }

    public String doYourRate() {
        return "confirm?faces-redirect=true";
    }

    public String doConfirm() {
        return "completion?faces-redirect=true";
    }

    public String doCompletion() {
        applicantService.add(applicant);
        end();
        return "index?faces-redirect=true";
    }

    // Getters and setters ommitted

    /**
     * Access to the conversation context - in a production application you would never provide such a getter!!
     * @return conversation
     */
    public Conversation getConversation() {
        return conversation;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }

    public int getDobDay() { return dobDay; }
    public void setDobDay(int dobDay) {
        this.dobDay = dobDay; }

    public int getDobMonth() { return dobMonth; }
    public void setDobMonth(int dobMonth) {
        this.dobMonth = dobMonth; }

    public String getDobYear() { return dobYear; }
    public void setDobYear(String dobYear) {
        this.dobYear = dobYear; }

    public BigDecimal getMinimumLoanAmount() {
        return minimumLoanAmount;
    }

    public BigDecimal getMaximumLoanAmount() {
        return maximumLoanAmount;
    }

    public BigDecimal getMinimumLoanRate() {
        return minimumLoanRate;
    }

    public void setMinimumLoanRate(BigDecimal minimumLoanRate) {
        this.minimumLoanRate = minimumLoanRate;
    }

    public BigDecimal getMaximumLoanRate() {
        return maximumLoanRate;
    }

    public void setMaximumLoanRate(BigDecimal maximumLoanRate) {
        this.maximumLoanRate = maximumLoanRate;
    }
}
