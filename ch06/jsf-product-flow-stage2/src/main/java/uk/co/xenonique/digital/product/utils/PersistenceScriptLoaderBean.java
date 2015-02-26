package uk.co.xenonique.digital.product.utils;

import uk.co.xenonique.digital.product.boundary.CampaignService;
import uk.co.xenonique.digital.product.boundary.PromotionService;
import uk.co.xenonique.digital.product.boundary.UserProfileService;
import uk.co.xenonique.digital.product.boundary.UserRoleService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import java.io.*;

/**
 * The type LoadDataFromScriptBean
 *
 * @author Peter Pilgrim
 */
@Stateless
public class PersistenceScriptLoaderBean {
    @PersistenceContext(unitName = "productFlow")
    private EntityManager entityManager;

    @Inject
    UserRoleService userRoleService;
    @Inject
    UserProfileService userProfileService;
    @Inject
    CampaignService campaignService;
    @Inject
    PromotionService promotionService;


    public void executeScript() {
        try {
            final InputStream is = new FileInputStream(new File("/Users/peterpilgrim/Documents/IdeaProjects/digital-javaee7/ch06/jsf-product-flow-stage2/src/main/resources/productflow.sql"));
            final LineNumberReader lnreader = new LineNumberReader(new InputStreamReader(is));
            final StringWriter swriter = new StringWriter();
            String line;
            while ( (line = lnreader.readLine()) != null ) {
                swriter.write(line);
            }

            final Query q = entityManager.createNativeQuery("BEGIN "+swriter.toString() + "END;");
            q.executeUpdate();
            entityManager.flush();
        }
        catch (IOException ioe) {
            ioe.printStackTrace(System.err);
        }
        catch (Throwable t) {
            t.printStackTrace(System.err);
            throw t;
        }
    }

}
