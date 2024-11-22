package ma.mundia.springmvc.web;

import jakarta.validation.Valid;
import javassist.compiler.ast.Keyword;
import lombok.AllArgsConstructor;
import ma.mundia.springmvc.entities.Patient;
import ma.mundia.springmvc.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.engine.AttributeName;

import javax.naming.Binding;
import javax.print.attribute.Attribute;
import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController {
    private PatientRepository patientRepository;

    @GetMapping(path = "/index")
    public String patients(Model model,
                           @RequestParam(name = "page", defaultValue = "0") int page,
                           @RequestParam(name = "size", defaultValue = "4") int pageSize,
                           @RequestParam(name="keyword", defaultValue = "") String keyword
                           ){
        Page<Patient> pagePatients = patientRepository.findByNameContains(keyword, PageRequest.of(page, pageSize));
        model.addAttribute("ListPatients", pagePatients.getContent());
        model.addAttribute("pages", new int[pagePatients.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        return "patients";

    }

    @GetMapping(path = "/delete")
    public String delete(Long id, String keyword, int page){
        patientRepository.deleteById(id);
        return "redirect:/index?page=" + page+ "&keyword=" + keyword;
    }

    @GetMapping(path = "/")
    public String home(){
        return "redirect:/index";
    }

    @GetMapping(path = "/formPatients")
    public String formPatient(Model model){
        model.addAttribute("patient", new Patient());
        return "formPatients";
    }

    @PostMapping(path = "/save")
    public String save(Model model, @Valid Patient patient, BindingResult bindingResult, @RequestParam(defaultValue="0") int page, @RequestParam(defaultValue = "") String keyword){
        if (bindingResult.hasErrors()) {
            return "formPatients";
        }
        patientRepository.save(patient);
        return "redirect:/index?page=" + page+ "&keyword=" + keyword;
    }

    @GetMapping(path = "/editPatients")
    public String editPatients(Model model, Long id, String Keyword, int page){
        Patient patient = patientRepository.findById(id).orElse(null);
        if(patient == null) throw new RuntimeException("Patient not found");
        model.addAttribute("patient", patient);
        model.addAttribute("keyword", Keyword);
        model.addAttribute("page", page);
        return "editPatients";
    }



}
