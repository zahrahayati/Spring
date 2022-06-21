package dotin.example.demo.controller;

import dotin.example.demo.aspect.ActivityLogAnnotation;
import dotin.example.demo.aspect.ExecutionTime;
import dotin.example.demo.model.CardPrintRequest;
import dotin.example.demo.postProcessor.ProcessedBean;
import dotin.example.demo.repository.CardPrintRequestRepository;
import dotin.example.demo.service.CardPrintRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Controller
public class CardPrintRequestMVCController implements ProcessedBean {
    @Autowired
    private CardPrintRequestRepository cardPrintRequestRepository;

    @Autowired
    private CardPrintRequestService cardPrintRequestService;

    private ApplicationContext applicationContext;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // Date - dd/MM/yyyy
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }


    @RequestMapping(value = "/list-requests", method = RequestMethod.GET)
    @ExecutionTime
    public String showRequests(ModelMap model) {
        model.put("requests", cardPrintRequestRepository.findAll());
        return "list-requests";
    }

    @RequestMapping("/new")
    public String newCardPrintRequest(Map<String, Object> model) {
        CardPrintRequest request = new CardPrintRequest();
        model.put("request", request);
        return "new_request";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ActivityLogAnnotation
    public String saveCardPrintRequest(@ModelAttribute("request") @Valid CardPrintRequest cardPrintRequest, BindingResult bindingResult, ModelMap model) {
        if (bindingResult.hasErrors()) {
            return "new_request";
        }
        CardPrintRequest cardPrintRequest1 = cardPrintRequestService.saveCardPrintRequest(cardPrintRequest);
        model.addAttribute("branchCode", cardPrintRequest1.getId().getBranchCode());
        model.addAttribute("ipAddress", cardPrintRequest1.getId().getIpAddress());
        model.addAttribute("personnelCode", cardPrintRequest1.getPersonnelCode());
        model.addAttribute("cardPAN", cardPrintRequest1.getCardPAN());
        model.addAttribute("issuedDate", cardPrintRequest1.getIssuedDate());
        return "show_request";
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
