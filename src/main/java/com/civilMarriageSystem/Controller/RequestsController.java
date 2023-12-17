package com.civilMarriageSystem.Controller;

import com.civilMarriageSystem.Domain.Requests;
import com.civilMarriageSystem.Domain.User;
import com.civilMarriageSystem.Repositories.RequestsRepository;
import com.civilMarriageSystem.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/requests/")
public class RequestsController {
    private User loggedInUser;

    private final RequestsRepository repo;
    private final UserRepository user_repo;

    @Autowired
    public RequestsController(RequestsRepository repo, UserRepository user_repo) {
        this.repo = repo;
        this.user_repo = user_repo;
    }

    @GetMapping("dashboard")
    public String showDashboard() {
        return "dashboard/index";
    }


    @GetMapping("add")
    public String showRegistrationForm(Requests requests) {
        return "./Dashboard/make-request";
    }

    @GetMapping("list")
    public String showUpdateForm(Model model, Principal principal) {
        User user = user_repo.findByEmail(principal.getName())
                .orElseThrow(() -> new IllegalArgumentException("Invalid User Id"));
        model.addAttribute("requestsList", repo.findAll());
        model.addAttribute("logged", principal.getName());
        return "./Dashboard/requestsList";
    }



    @PostMapping("register")
    public String makeRequest(@Valid Requests request, BindingResult result,
                            Model model, RedirectAttributes redirectAttributes, Principal principal) {

        try {
            if (result.hasErrors()) {
                return "./Dashboard/make-request";
            }
            User user = user_repo.findByEmail(principal.getName())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid User Id"));
            loggedInUser=user;
            request.setCitizen(loggedInUser);
            repo.save(request);
            model.addAttribute("requests", repo.findAll());
            redirectAttributes.addFlashAttribute("message", "Request successfully created!");

        } catch (Exception e) {
            redirectAttributes.addAttribute("message", e.getMessage());
        }

        return "redirect:list";
    }


    @GetMapping("edit/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Requests requests = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Request Id:" + id));
        model.addAttribute("requests", requests);
        return "./dashboard/edit-request";
    }

    @PostMapping("update/{id}")
    public String updateRequest(@PathVariable("id") Integer id, @Valid Requests request, BindingResult result,
                              Model model, RedirectAttributes redirectAttributes, Principal principal) {

        try {
            if (result.hasErrors()) {
                request.setId(id);
                return "./dashboard/edit-request";
            }
            User user = user_repo.findByEmail(principal.getName())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid User Id"));
            loggedInUser=user;
            request.setCitizen(loggedInUser);
            repo.save(request);
            model.addAttribute("requestsList", repo.findAll());
            redirectAttributes.addFlashAttribute("message", "Request updated successfully!");

        } catch (Exception e) {
            redirectAttributes.addAttribute("message", e.getMessage());
        }

        return "redirect:../list";
    }

    @GetMapping("delete/{id}")
    public String deleteRequest(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {

        try {
            Requests requests = repo.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid Request Id:" + id));
            repo.delete(requests);
            model.addAttribute("requestsList", repo.findAll());
            redirectAttributes.addFlashAttribute("message", "Request deleted successfully!");

        } catch (Exception e) {
            redirectAttributes.addAttribute("message", e.getMessage());
        }

        return "redirect:../list";
    }

    @GetMapping("approve/{id}")
    public String approveRequest(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {

        try {
            Requests requests = repo.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid Request Id:" + id));
            requests.setStatus("Approved");
            repo.save(requests);
            model.addAttribute("requestsList", repo.findAll());
            redirectAttributes.addFlashAttribute("message", "Request approved!");

        } catch (Exception e) {
            redirectAttributes.addAttribute("message", e.getMessage());
        }

        return "redirect:../list";
    }

    @GetMapping("deny/{id}")
    public String denyRequest(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {

        try {
            Requests requests = repo.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid Request Id:" + id));
            requests.setStatus("Denied");
            repo.save(requests);
            model.addAttribute("requestsList", repo.findAll());
            redirectAttributes.addFlashAttribute("message", "Request denied!");

        } catch (Exception e) {
            redirectAttributes.addAttribute("message", e.getMessage());
        }

        return "redirect:../list";
    }

}
