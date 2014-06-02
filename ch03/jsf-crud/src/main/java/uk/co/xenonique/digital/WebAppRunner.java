package uk.co.xenonique.digital;

import je7hb.common.webcontainer.embedded.glassfish.SimpleEmbeddedRunner;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.GenericArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.importer.ExplodedImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.impl.base.exporter.zip.ZipExporterImpl;

import java.io.File;
import java.util.Scanner;

public class WebAppRunner {
	public static void main(String args[]) throws Exception {
        System.out.println("=======================================================================");
        System.out.println("=======================================================================");
		System.out.println("Web application runner: jsf-crud 1.0-SNAPSHOT");

        WebArchive webArchive = ShrinkWrap.create(WebArchive.class, "demo.war")
            .addClasses(
                ContactDetail.class, ContactDetailController.class,
                ContactDetailService.class )
            .addAsWebInfResource(
                new File("src/main/resources/META-INF/persistence.xml"),
                "classes/META-INF/persistence.xml")
            .addAsWebInfResource(
                new File("src/main/webapp/WEB-INF/glassfish-resources.xml"),
                "glassfish-resources.xml")
            .setWebXML(new File("src/main/webapp/WEB-INF/web.xml"))
            .addAsWebInfResource(
                EmptyAsset.INSTANCE, "beans.xml");

        webArchive.merge(ShrinkWrap.create(GenericArchive.class).as(ExplodedImporter.class)
            .importDirectory("src/main/webapp").as(GenericArchive.class),
            "/", Filters.include(".*\\.(html|jsp|css|js|png|jpg|jpeg|gif)$"));

        System.out.println(webArchive.toString(true));

        File warFile = new File(webArchive.getName());
        new ZipExporterImpl(webArchive).exportTo(warFile, true);
        SimpleEmbeddedRunner runner =
            SimpleEmbeddedRunner.launchDeployWarFile(
                warFile, "demo", 8080);

        System.out.println("=======================================================================");
        System.out.println("=======================================================================");

        System.out.print("\nHIT THE RETURN KEY >");
        Scanner sc = new Scanner(System.in);
        while(!sc.nextLine().equals(""));
        System.out.println("\nStopping service ...");

        runner.stop();
	}
}


