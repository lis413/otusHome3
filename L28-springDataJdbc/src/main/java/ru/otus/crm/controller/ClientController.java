package ru.otus.crm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.ClientDTO;
import ru.otus.crm.service.DBServiceClient;

//@RequiredArgsConstructor
@Controller("/clients")
public class ClientController {
    private final DBServiceClient clientService;

    public ClientController(DBServiceClient clientService) {
        this.clientService = clientService;
    }

    @GetMapping({"/", "/clients"})
    public String getClients(Model model) {
        model.addAttribute("clients", clientService.findAll());
        return "clients";
    }

    @GetMapping({"/add-client"})
    public String addClient(Model model) {
        model.addAttribute("clientDTO", new ClientDTO());
        return "addClient";
    }

    @PostMapping({"/add-client"})
    public RedirectView  addClientPost(@ModelAttribute ClientDTO clientDTO) {
        Client client = clientDTO.getClient();
        clientService.saveClient(client);
        return new RedirectView("/");
    }


}
