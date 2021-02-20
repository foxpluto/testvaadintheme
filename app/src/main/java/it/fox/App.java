package it.fox;

import java.net.MalformedURLException;
import java.net.URL;

import com.vaadin.flow.server.VaadinServlet;
import com.vaadin.flow.server.startup.ServletContextListeners;
import org.eclipse.jetty.webapp.*;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;


public final class App {

    private static Logger logger = LogManager.getLogger(App.class);
    private static final boolean IS_PRODUCTION_MODE = true;
    public static void main(String[] args) throws Exception {
    

        logger.info("Start jetty Embedded");
        // use pnpm instead of npm
        System.setProperty("vaadin.pnpm.enable", "true");
        System.setProperty("disable.automatic.servlet.registration", "false");

        if (IS_PRODUCTION_MODE) {
            System.setProperty("vaadin.productionMode", "true");
        }

        final WebAppContext context = new WebAppContext();
        context.setBaseResource(findWebRoot());
        context.setContextPath("/");
        context.addServlet(VaadinServlet.class, "/*");
        context.setAttribute("org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern", ".*\\.jar|.*/classes/.*");
        context.setConfigurationDiscovered(true);
        context.getServletContext().setExtendedListenerTypes(true);
        context.addEventListener(new ServletContextListeners());
        WebSocketServerContainerInitializer.initialize(context); // fixes IllegalStateException: Unable to configure
                                                                 // jsr356 at that stage. ServerContainer is null


        Server server = new Server(8080);
        final Configuration.ClassList classList = Configuration.ClassList.setServerDefault(server);
        classList.addBefore(JettyWebXmlConfiguration.class.getName(), AnnotationConfiguration.class.getName());
        server.setHandler(context);
        server.start();

        logger.info("\n\n=================================================\n\n"
                + "Please open http://localhost:{} in your browser\n\n"
                + "=================================================\n\n", 8080);

        server.join();

        // Here be dragons
    }

    /**
     * Find WebApp ROOT in a reliable way. This method look up the /webapp/ROOT and
     * retrieve the parent folder from that.
     * 
     * @see https://github.com/eclipse/jetty.project/issues/4173#issuecomment-539769734
     * @return the WebApp ROOT
     * @throws MalformedURLException
     */
    private static Resource findWebRoot() throws MalformedURLException {
        final URL f = App.class.getResource("/webapp/ROOT");
        if (f == null) {
            throw new IllegalStateException(
                    "Invalid state: the resource /webapp/ROOT doesn't exist, has webapp been packaged in as a resource?");
        }
        final String url = f.toString();
        if (!url.endsWith("/ROOT")) {
            throw new RuntimeException("Parameter url: invalid value " + url + ": doesn't end with /ROOT");
        }
        logger.info("/webapp/ROOT is {}", f);

        // Resolve file to directory
        URL webRoot = new URL(url.substring(0, url.length() - 5));
        logger.info("WebRoot is {}", webRoot);
        return Resource.newResource(webRoot);
    }
}
