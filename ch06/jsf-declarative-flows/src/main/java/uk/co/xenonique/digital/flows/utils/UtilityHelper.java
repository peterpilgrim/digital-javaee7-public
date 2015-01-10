package uk.co.xenonique.digital.flows.utils;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import java.io.Serializable;
import java.util.Random;

/**
 * The type UtilityHelper
 *
 * @author Peter Pilgrim
 */
@ApplicationScoped
public class UtilityHelper implements Serializable{

    public String generateNumeric(  int length ) {
        Random rnd = new Random();
        StringBuilder buf = new StringBuilder();
        for (int j=0; j<length; ++j ) {
            char c = (char) (48 + rnd.nextInt(10));
            buf.append(c);
        }
        return buf.toString();
    }

    public String generateAlpha(  int length ) {
        Random rnd = new Random();
        StringBuilder buf = new StringBuilder();
        for (int j=0; j<length; ++j ) {
            char c = (char) (65 + rnd.nextInt(26));
            buf.append(c);
        }
        return buf.toString();
    }

    public String generateAlphanumeric(  int length ) {
        Random rnd = new Random();
        StringBuilder buf = new StringBuilder();
        for (int j=0; j<length; ++j ) {
            char c;
            if ( rnd.nextDouble() < 0.5 ) {
                c = (char)(65+rnd.nextInt(26));
            }
            else {
                c = (char)(48+rnd.nextInt(10));
            }
            buf.append(c);
        }

        return buf.toString();
    }
    public String getNextApplicationId() {
        return generateAlpha(2)+generateAlphanumeric(2)+"-"+
                generateAlpha(2)+generateNumeric(2)+"-"+ generateAlpha(4);
    }
}
