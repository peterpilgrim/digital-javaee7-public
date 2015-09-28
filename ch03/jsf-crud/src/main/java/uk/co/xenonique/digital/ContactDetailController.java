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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.text.DateFormatSymbols;
import java.util.*;

/**
 * The type ContactDetailContractor
 *
 * @author Peter Pilgrim
 */
//@ManagedBean(name = "contactDetailController")
@Named("contactDetailController")
@ViewScoped
public class ContactDetailController implements Serializable{

    @EJB ContactDetailService contactDetailService;

    private ContactDetail contactDetail = new ContactDetail();

    public ContactDetail getContactDetail() {
        return contactDetail;
    }

    public void setContactDetail(ContactDetail contactDetail) {
        this.contactDetail = contactDetail;
    }

    public String createContact() {
        final Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, dobDay);
        cal.set(Calendar.MONTH, dobMonth);
        final int year = Integer.parseInt(dobYear);
        cal.set(Calendar.YEAR, year);
        contactDetail.setDob(cal.getTime());
        contactDetailService.add(contactDetail);
        System.out.println(contactDetail);
        contactDetail = new ContactDetail();

        return "index.xhtml";
    }

    public List<ContactDetail> retrieveAllContacts() {
        return contactDetailService.findAll();
    }

    private int id;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String cancel() {
        return "index.xhtml";
    }

    public void findContactById() {
        if (id <= 0) {
            final String message = "Bad request. Please use a link from within the system.";
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
            return;
        }

        ContactDetail item = contactDetailService.findById(id).get(0);
        if (item == null) {
            final String message = "Bad request. Unknown contact detail id.";
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
        }
        contactDetail = item;
        final Calendar cal = Calendar.getInstance();
        cal.setTime(contactDetail.getDob());
        dobDay = cal.get(Calendar.DAY_OF_MONTH);
        dobMonth = cal.get(Calendar.MONTH);
        dobYear = Integer.toString(cal.get(Calendar.YEAR));
        System.out.println("retrieve item: "+contactDetail);
    }

    public String editContact() {
        final Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, dobDay);
        cal.set(Calendar.MONTH, dobMonth);
        final int year = Integer.parseInt(dobYear);
        cal.set(Calendar.YEAR, year);
        contactDetail.setDob(cal.getTime());
        contactDetail.setId(id);
        contactDetailService.update(contactDetail);
        System.out.println(contactDetail);

        contactDetail = new ContactDetail();

        return "index.xhtml";
    }

    public String removeContact() {
        contactDetail = contactDetailService.findById(id).get(0);
        contactDetailService.delete(contactDetail);
        System.out.println(contactDetail);
        contactDetail = new ContactDetail();
        return "index.xhtml";
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

        final DateFormatSymbols symbols =
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
