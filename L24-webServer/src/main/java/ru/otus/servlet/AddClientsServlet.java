package ru.otus.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;





import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.services.TemplateProcessor;
import ru.otus.services.UtilServiceLClient;


import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

import java.util.Map;


    public class AddClientsServlet extends HttpServlet {

        private static final String USERS_PAGE_TEMPLATE = "addClient.html";
        private static final String TEMPLATE_ATTR_RANDOM_USER = "listClients";


        private final TemplateProcessor templateProcessor;



        DBServiceClient serviceClient = new UtilServiceLClient().getDBServiceClient();

        public AddClientsServlet(TemplateProcessor templateProcessor) {
            this.templateProcessor = templateProcessor;
        }

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
            Map<String, Object> paramsMap = new HashMap<>();
//            List<Client> listClient = serviceClient.findAll();
//            paramsMap.put(TEMPLATE_ATTR_RANDOM_USER, listClient);
            response.setContentType("text/html");
            response.getWriter().println(templateProcessor.getPage(USERS_PAGE_TEMPLATE, paramsMap));
        }

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse response) throws IOException {
            StringBuffer jb = new StringBuffer();
            System.out.println(req.getParameter("name"));
            System.out.println(req.getParameter("address"));
            System.out.println("++++++++++++++++++++++++++++++");
            String line = null;
            try {
                BufferedReader reader = req.getReader();
                while ((line = reader.readLine()) != null)
                    jb.append(line);
            } catch (Exception e) { /*report an error*/ }
            System.out.println(jb);
            System.out.println("+++++++++++++++++++++++++++++++++");
            Client client = new Client();
            client.setName(req.getParameter("name"));
            client.setAddress(new Address(req.getParameter("address")));
            System.out.println(client + "     client");
            serviceClient.saveClient(client);
            //response.getWriter().println(templateProcessor.getPage("clients.html", new HashMap<>()));
            response.sendRedirect("http://localhost:8080/clients");
        }

    }

