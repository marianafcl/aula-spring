package pt.ulisboa.tecnico.softeng.bank.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pt.ulisboa.tecnico.softeng.bank.domain.Bank;
import pt.ulisboa.tecnico.softeng.bank.domain.Client;
import pt.ulisboa.tecnico.softeng.bank.dto.BankDto;
import pt.ulisboa.tecnico.softeng.bank.dto.ClientDto;
import pt.ulisboa.tecnico.softeng.bank.exception.BankException;
import pt.ulisboa.tecnico.softeng.bank.exception.ClientException;

import java.util.Set;

@Controller
@RequestMapping(value = "/banks/bank/{code}/clients")
public class ClientController {
    private String code;
	private static Logger logger = LoggerFactory.getLogger(BankController.class);

    @RequestMapping(method = RequestMethod.GET)
	public String clientsForm(Model model, @PathVariable String code) {
        this.code = code;
        logger.info("clientsForm code:{}", code);

        ClientDto clientDto = new ClientDto();
        clientDto.setCode(this.code);

        model.addAttribute("bank", Bank.getBankByCode(code));
        model.addAttribute("client", clientDto);

		return "bankView";
    }
    
    @RequestMapping(method = RequestMethod.POST)
	public String clientsSubmit(Model model, @ModelAttribute ClientDto clientDto) {
        logger.info("clientsSubmit code:{} id:{} name:{} age:{}", this.code, clientDto.getId(), clientDto.getName(), clientDto.getAge());
        
        Bank bank = Bank.getBankByCode(this.code);

        try{
            Client client = new Client(bank, clientDto.getId(), clientDto.getName(), clientDto.getAge());
        } catch(ClientException e) {
            clientDto = new ClientDto();
            clientDto.setCode(this.code);

            model.addAttribute("error", "Error: it was not possible to create the client");
			model.addAttribute("bank", bank);
            model.addAttribute("client", clientDto);
            
			return "bankView";
        }
		return "redirect:/banks/bank/" + this.code + "/clients";
    }
    
    @RequestMapping(value="/client/{id}", method = RequestMethod.GET)
	public String showClient(Model model, @PathVariable String id) {
        logger.info("showClient code:{} id:{}", this.code, id);
        Set<Client> clients = Bank.getBankByCode(this.code).getClients();

        Client result = null;
        for(Client client : clients) {
            if(client.getId().equals(id)) {
                result = client;
                break;
            }
        }

        model.addAttribute("client", result);
        model.addAttribute("code", this.code);
		return "clientView";
	}
}