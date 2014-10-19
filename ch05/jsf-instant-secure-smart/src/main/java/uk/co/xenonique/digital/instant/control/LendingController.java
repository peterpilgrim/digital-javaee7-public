package uk.co.xenonique.digital.instant.control;

import uk.co.xenonique.digital.instant.boundary.ApplicantService;
import uk.co.xenonique.digital.instant.entity.Address;
import uk.co.xenonique.digital.instant.entity.Applicant;
import uk.co.xenonique.digital.instant.entity.ContactDetail;
import uk.co.xenonique.digital.instant.util.Utility;

import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 * The type LendingController
 *
 * @author Peter Pilgrim
 */

@Named("lendingController")
@ConversationScoped
public class LendingController implements Serializable {
    public static final String STAGE_GETTING_STARTED = "gettingStarted";
    public static final String STAGE_YOUR_DETAILS = "yourDetails";
    public static final String STAGE_YOUR_RATE = "yourRate";
    public static final String STAGE_YOUR_ADDRESS = "yourAddress";
    public static final String STAGE_CONFIRM = "confirm";
    public static final String STAGE_COMPLETION = "completion";
    @EJB ApplicantService applicantService;
    @Inject Conversation conversation;
    @Inject Utility utility;

    public final static int DEFAULT_LOAN_TERM = 24;
    public final static BigDecimal DEFAULT_LOAN_AMOUNT = new BigDecimal("7000");
    public final static BigDecimal DEFAULT_LOAN_RATE = new BigDecimal("5.50");

    private int dobDay;
    private int dobMonth;
    private String dobYear;
    private BigDecimal minimumLoanAmount = new BigDecimal("3000");
    private BigDecimal maximumLoanAmount = new BigDecimal("25000");
    private BigDecimal minimumLoanRate   = new BigDecimal("3.0");
    private BigDecimal maximumLoanRate   = new BigDecimal("12.0");

    private String currencySymbol = "£";

    private BigDecimal paymentMonthlyAmount = BigDecimal.ZERO;
    private BigDecimal totalPayable = BigDecimal.ZERO;
    private Applicant applicant;

    private SmartNavigation navigation;

    public LendingController() {
        applicant = new Applicant();
        applicant.setLoanAmount( DEFAULT_LOAN_AMOUNT);
        applicant.setLoanRate( DEFAULT_LOAN_RATE );
        applicant.setLoanTermMonths( DEFAULT_LOAN_TERM );
        applicant.setAddress(new Address());
        applicant.setContactDetail(new ContactDetail());
        navigation = new SmartNavigation(
                Arrays.asList(
                        new NavElement(
                                STAGE_GETTING_STARTED, "Getting Started",
                                "", "getting-started.xhtml" ),
                        new NavElement(
                                STAGE_YOUR_DETAILS, "Your Details",
                                "", "your-details.xhtml" ),
                        new NavElement(
                                STAGE_YOUR_RATE, "Your Rate",
                                "", "your-rate.xhtml" ),
                        new NavElement(
                                STAGE_YOUR_ADDRESS, "Your Address",
                                "", "your-address.xhtml?faces-redirect=true"),
                        new NavElement(
                                STAGE_CONFIRM,"Confirm",
                                "", "confirm.xhtml?faces-redirect=true"),
                        new NavElement(
                                STAGE_COMPLETION, "Completion",
                                "", "completion.xhtml?faces-redirect=true")));
    }

    public void checkAndStart() {
        System.out.printf("checkAndStart() conversation.transient=%s\n", conversation.isTransient());
        if ( conversation.isTransient()) {
            System.out.printf("checkAndStart() conversation.begin()\n");
            conversation.begin();
        }
        recalculatePMT();
    }

    public void checkAndEnd() {
        System.out.printf("checkAndEnd() conversation.transient=%s\n", conversation.isTransient());
        if (!conversation.isTransient()) {
            System.out.printf("checkAndStart() conversation.end()\n");
            conversation.end();
        }
    }

    public BigDecimal recalculatePMT() {
        recalculateLoanRate();
        paymentMonthlyAmount =
                new BigDecimal(utility.calculateMonthlyPayment(
                        applicant.getLoanAmount().doubleValue(),
                        applicant.getLoanRate().doubleValue(),
                        applicant.getLoanTermMonths()));

        totalPayable = paymentMonthlyAmount.multiply( new BigDecimal( applicant.getLoanTermMonths()));
        return paymentMonthlyAmount;
    }

    public BigDecimal recalculateLoanRate() {
        applicant.setLoanRate(utility.getTaxRate(applicant.getLoanAmount()));
        return applicant.getLoanRate();
    }

    public String cancel() {
        checkAndEnd();
        return "index?faces-redirect=true";
    }

    public String jumpGettingStarted() {
        checkAndStart();
        return "getting-started?faces-redirect=true";
    }

    public String doGettingStarted() {
        checkAndStart();
        navigation.getElementByName(STAGE_GETTING_STARTED).setVisited(true);
        return "your-details?faces-redirect=true";
    }

    public String jumpDoYourDetails() {
        return "your-details?faces-redirect=true";
    }

    public String doYourDetails() {
        checkAndStart();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, dobDay);
        cal.set(Calendar.MONTH, dobMonth-1);
        int year = Integer.parseInt(dobYear);
        cal.set(Calendar.YEAR, year);
        navigation.getElementByName(STAGE_YOUR_DETAILS).setVisited(true);

        applicant.getContactDetail().setDob(cal.getTime());
        return "your-rate?faces-redirect=true";
    }

    public String jumpDoYourRate() {
        return "your-rate?faces-redirect=true";
    }

    public String doYourRate() {
        checkAndStart();
        navigation.getElementByName(STAGE_YOUR_RATE).setVisited(true);
        return "your-address?faces-redirect=true";
    }

    public String doYourAddress() {
        checkAndStart();
        navigation.getElementByName(STAGE_YOUR_ADDRESS).setVisited(true);
        return "confirm?faces-redirect=true";
    }


    public void validateTermsOrConditions(
        FacesContext context, UIComponent component, Object value)
    throws ValidatorException {
        Boolean selectedCheckbox = (Boolean) value;
        if ( !selectedCheckbox) {
            throw new ValidatorException(
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "You must agree to the Terms and Agreement", null));
        }
    }

    public String doConfirm() {
        checkAndStart();
        if ( applicant.isTermsAgreed()) {
            throw new IllegalStateException("terms of agreements not set to true");
        }
        recalculatePMT();
        applicant.setSubmitDate(new Date());
        applicantService.add(applicant);
        navigation.getElementByName(STAGE_CONFIRM).setVisited(true);
        return "completion?faces-redirect=true";
    }

    public String doCompletion() {
        checkAndEnd();
        navigation.getElementByName(STAGE_COMPLETION).setVisited(true);
        return "index?faces-redirect=true";
    }

    // Getters and setters omitted

    public BigDecimal getPaymentMonthlyAmount() {
        return paymentMonthlyAmount;
    }

    public BigDecimal getTotalPayable() {
        return totalPayable;
    }

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

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public SmartNavigation getNavigation() {
        return navigation;
    }
}
