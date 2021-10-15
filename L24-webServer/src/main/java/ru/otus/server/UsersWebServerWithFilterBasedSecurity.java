package ru.otus.server;

import com.google.gson.Gson;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.dao.UserDao;
import ru.otus.helpers.FileSystemHelper;
import ru.otus.services.TemplateProcessor;
import ru.otus.services.UserAuthService;
import ru.otus.servlet.AddClientsServlet;
import ru.otus.servlet.AuthorizationFilter;
import ru.otus.servlet.ClientsServlet;
import ru.otus.servlet.LoginServlet;


import java.util.Arrays;

public class UsersWebServerWithFilterBasedSecurity implements UsersWebServer{
    private final UserAuthService authService;
    private static final String START_PAGE_NAME = "index.html";
    private static final String COMMON_RESOURCES_DIR = "static";

    private final UserDao userDao;
    private final Gson gson;
    protected final TemplateProcessor templateProcessor;
    protected final DBServiceClient dbServiceClient;

    private final Server server;
   // private final DBServiceClient dbServiceClient;

    public UsersWebServerWithFilterBasedSecurity(int port,
                                                 UserAuthService authService,
                                                 UserDao userDao,
                                                 Gson gson,
                                                 TemplateProcessor templateProcessor, DBServiceClient dbServiceClient) {
       // super(port, userDao, gson, templateProcessor, dbServiceClient);
        this.authService = authService;
        this.userDao = userDao;
        this.gson = gson;
        this.templateProcessor = templateProcessor;
        this.dbServiceClient = dbServiceClient;
        server = new Server(port);
       // this.dbServiceClient = dbServiceClient1;
    }


   // @Override
    protected Handler applySecurity(ServletContextHandler servletContextHandler, String... paths) {
        servletContextHandler.addServlet(new ServletHolder(new LoginServlet(templateProcessor, authService)), "/login");
        AuthorizationFilter authorizationFilter = new AuthorizationFilter();
        Arrays.stream(paths).forEachOrdered(path -> servletContextHandler.addFilter(new FilterHolder(authorizationFilter), path, null));
        return servletContextHandler;
    }



//    public UsersWebServerSimple(int port, UserDao userDao, Gson gson, TemplateProcessor templateProcessor) {
//        this.userDao = userDao;
//        this.gson = gson;
//        this.templateProcessor = templateProcessor;
//        server = new Server(port);
//    }

//    public UsersWebServerSimple(int port, UserDao userDao, Gson gson, TemplateProcessor templateProcessor, DBServiceClient dbServiceClient) {
//        this.userDao = userDao;
//        this.gson = gson;
//        this.templateProcessor = templateProcessor;
//        this.dbServiceClient = dbServiceClient;
//        server = new Server(port);
//    }

    @Override
    public void start() throws Exception {
        if (server.getHandlers().length == 0) {
            initContext();
        }
        server.start();
    }

    @Override
    public void join() throws Exception {
        server.join();
    }

    @Override
    public void stop() throws Exception {
        server.stop();
    }

    private Server initContext() {

        ResourceHandler resourceHandler = createResourceHandler();
        ServletContextHandler servletContextHandler = createServletContextHandler();

        HandlerList handlers = new HandlerList();
        handlers.addHandler(resourceHandler);
        handlers.addHandler(applySecurity(servletContextHandler, "/clients", "/clients/add"));


        server.setHandler(handlers);
        return server;
    }

//    protected Handler applySecurity(ServletContextHandler servletContextHandler, String ...paths) {
//        return servletContextHandler;
//    }

    private ResourceHandler createResourceHandler() {
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(false);
        resourceHandler.setWelcomeFiles(new String[]{START_PAGE_NAME});
        resourceHandler.setResourceBase(FileSystemHelper.localFileNameOrResourceNameToFullPath(COMMON_RESOURCES_DIR));
        return resourceHandler;
    }

    private ServletContextHandler createServletContextHandler() {
        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        //servletContextHandler.addServlet(new ServletHolder(new UsersServlet(templateProcessor, userDao)), "/users");
        servletContextHandler.addServlet(new ServletHolder(new ClientsServlet(templateProcessor, dbServiceClient)), "/clients");
        servletContextHandler.addServlet(new ServletHolder(new AddClientsServlet(templateProcessor, dbServiceClient)), "/clients/add");
        // servletContextHandler.addServlet(new ServletHolder(new UsersApiServlet(userDao, gson)), "/api/user/*");
        return servletContextHandler;
    }
}
