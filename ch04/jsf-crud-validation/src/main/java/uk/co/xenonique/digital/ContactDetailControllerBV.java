/*******************************************************************************
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2014,2015 by Peter Pilgrim, Milton Keynes, P.E.A.T LTD
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU GPL v3.0
 * which accompanies this distribution, and is available at:
 * http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Developers:
 * Peter Pilgrim -- design, development and implementation
 *               -- Blog: http://www.xenonique.co.uk/blog/
 *               -- Twitter: @peter_pilgrim
 *
 * Contributors:
 *
 *******************************************************************************/

package uk.co.xenonique.digital;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.text.DateFormatSymbols;
import java.util.*;

/**
 * The type ContactDetailContractor
 *
 * @author Peter Pilgrim
 */
@ManagedBean(name = "contactDetailControllerBV")
// @Named("contactDetailControllerBV")
@ViewScoped
public class ContactDetailControllerBV {

    @EJB ContactDetailService contactDetailService;

    private ContactDetail contactDetail = new ContactDetail();

    public ContactDetail getContactDetail() {
        return contactDetail;
    }

    public void setContactDetail(ContactDetail contactDetail) {
        this.contactDetail = contactDetail;
    }

    public String createContact() {
        contactDetail.setDob(convertDateFieldsToDOB());
        try {
            contactDetailService.add(contactDetail);
            contactDetail = new ContactDetail();
            return "/bean-validation/index.xhtml?redirect=true";
        }
        catch (EJBException e ) {
//            e.printStackTrace(System.err);
            if ( e.getCause() instanceof ConstraintViolationException) {
                ConstraintViolationException cv =
                    (ConstraintViolationException)e.getCause();
                for ( ConstraintViolation<?> violation: cv.getConstraintViolations() ) {
//                    System.err.printf("violation.message = %s\n", violation.getMessage());
//                    System.err.printf("violation.messagePath = %s\n", violation.getPropertyPath());
//                    System.err.printf("violation.messageTemplate = %s\n", violation.getMessageTemplate());
//                    System.err.printf("violation.invalidValue = %s\n", violation.getInvalidValue());
                    FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(
                            FacesMessage.SEVERITY_ERROR, violation.getMessage(), null));
                }
                return "";
            }
            throw e;
        }
    }

    private Date convertDateFieldsToDOB() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, dobDay);
        cal.set(Calendar.MONTH, dobMonth-1 );
        int year = Integer.parseInt(dobYear);
        cal.set(Calendar.YEAR, year);
        return cal.getTime();
    }

    private void convertDOBToDateFields() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(contactDetail.getDob());
        dobDay = cal.get(Calendar.DAY_OF_MONTH);
        dobMonth = cal.get(Calendar.MONTH)+1;
        dobYear = Integer.toString(cal.get(Calendar.YEAR));
    }

    public List<ContactDetail> retrieveAllContacts() {
        return contactDetailService.findAll();
    }

    private int id;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String cancel() {
        return "/bean-validation/index.xhtml?redirect=true";
    }

    public void findContactById() {
        if (FacesContext.getCurrentInstance().isValidationFailed()) {
            /* Validation failed, do not try to retrieve the entity */
            return;
        }
        if (id <= 0) {
            String message =
                "Bad request. Please use a link from within " +
                "the system. id=" + id +
                " contact.id="+contactDetail.getId()+
                " FacesContext.validationFailed="+
                FacesContext.getCurrentInstance().isValidationFailed();
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, message, null));
            return;
        }

        ContactDetail item = contactDetailService.findById(id).get(0);
        if (item == null) {
            String message = "Bad request. Unknown contact detail id.";
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
        }
        contactDetail = item;
        convertDOBToDateFields();
    }

    public String editContact() {
        contactDetail.setDob(convertDateFieldsToDOB());
        contactDetail.setId(id);
        try {
            contactDetailService.update(contactDetail);
        }
        catch (EJBException e) {
            if ( e.getCause() instanceof ConstraintViolationException) {
                ConstraintViolationException cv =
                        (ConstraintViolationException)e.getCause();
                for ( ConstraintViolation<?> violation: cv.getConstraintViolations() ) {
                    FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(
                            FacesMessage.SEVERITY_ERROR, violation.getMessage(), null));
                }
                return "";
            }
            throw e;
        }

        contactDetail = new ContactDetail();
        return "/bean-validation/index.xhtml?redirect=true";
    }

    public String removeContact() {
        contactDetail = contactDetailService.findById(id).get(0);
        contactDetailService.delete(contactDetail);
        contactDetail = new ContactDetail();
        return "/bean-validation/index.xhtml?redirect=true";
    }

    private int dobDay;
    private int dobMonth;
    private String dobYear;

    public int getDobDay() { return dobDay; }
    public void setDobDay(int dobDay) {
        this.dobDay = dobDay; }

    public int getDobMonth() { return dobMonth; }
    public void setDobMonth(int dobMonth) {
        this.dobMonth = dobMonth; }

    public String getDobYear() { return dobYear; }
    public void setDobYear(String dobYear) {
        this.dobYear = dobYear; }

    private static List<Integer> daysOfTheMonth = new ArrayList<>();
    private static Map<String,Integer> monthsOfTheYear = new LinkedHashMap<>();

    static {
        for (int d=1; d<=31; ++d) {
            daysOfTheMonth.add(d);
        }

        DateFormatSymbols symbols =
                new DateFormatSymbols(Locale.getDefault());
        for (int m=1; m<=12; ++m) {
            monthsOfTheYear.put(symbols.getMonths()[m-1], m );
        }
    }

    public List<Integer> getDaysOfTheMonth() {
        return daysOfTheMonth;
    }
    public Map<String,Integer> getMonthsOfTheYear() {
        return monthsOfTheYear;
    }
}
