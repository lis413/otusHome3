package ru.otus.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;





import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.services.TemplateProcessor;


import java.io.IOException;
import java.util.HashMap;

import java.util.Map;


    public class AddClientsServlet extends HttpServlet {

        private static final String CLIENTS_PAGE_TEMPLATE = "addClient.html";
        private static final String TEMPLATE_ATTR_RANDOM_USER = "listClients";


        private final TemplateProcessor templateProcessor;




        private DBServiceClient serviceClient;// = new UtilServiceLClient().getDBServiceClient();

        public AddClientsServlet(TemplateProcessor templateProcessor) {
            this.templateProcessor = templateProcessor;
        }

        public AddClientsServlet(TemplateProcessor templateProcessor, DBServiceClient serviceClient) {
            this.templateProcessor = templateProcessor;
            this.serviceClient = serviceClient;
        }

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
            Map<String, Object> paramsMap = new HashMap<>();
            response.setContentType("text/html");
            response.getWriter().println(templateProcessor.getPage(CLIENTS_PAGE_TEMPLATE, paramsMap));
        }

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse response) throws IOException {
            StringBuffer jb = new StringBuffer();

            Client client = new Client();
            client.setName(req.getParameter("name"));
            client.setAddress(new Address(req.getParameter("address")));

            serviceClient.saveClient(client);

            response.sendRedirect("http://localhost:8080/clients");

        }

    }

