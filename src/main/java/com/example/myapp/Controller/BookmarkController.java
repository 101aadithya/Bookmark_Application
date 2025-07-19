package com.example.myapp.Controller;


import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.data.repository.query.Param;
import com.example.myapp.Models.Bookmark;
import com.example.myapp.Models.User;
import com.example.myapp.Repository.BookmarkRepository;

import com.example.myapp.Repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;



@Controller
public class BookmarkController {

    @Autowired
    private BookmarkRepository bookmarkRepository;
    
    @Autowired											//new
    private UserRepository userRepository;				//new
    
    
    @Controller
    public class AboutusController {

        @GetMapping("/aboutus")
        public String aboutus() {
            return "aboutus";
        }
    }
    
    private User getCurrentUser() {							//new
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(auth.getName());
    }

    @GetMapping("/create")
    public String createAction(Model model) {
        model.addAttribute("message", "Enter The Bookmark Details");
        return "create";
    }
    
    //To add a bookmark
    @PostMapping("/create")
    public String createActionProcess(Bookmark bookmarkData, Model model) {
    	
    	 User currentUser = getCurrentUser();								//new
    	    int count = bookmarkRepository.countByUser(currentUser);
    	    if (count >= 5) {
    	        model.addAttribute("error", "You can only add up to 5 bookmarks.");
    	        return "create";
    	    }	
    	
    	bookmarkData.setUser(currentUser);//new
    	bookmarkRepository.save(bookmarkData);
        model.addAttribute("message", "The Bookmark " + bookmarkData.getTitle() + " has been created successfully");
        return "create";
    	
    }
    
    //To read bookmarks
    @GetMapping("/all")
    public String getAllBookmarks(Model model, @Param("keyword") String keyword) {
        List<Bookmark> bookmarks;
        if (keyword != null && !keyword.isEmpty()) {		//if keyword is present
            bookmarks = bookmarkRepository.findAllByKeyword(keyword);
        } else {
            bookmarks = bookmarkRepository.findAll();		//if no keyword is there it fetches everything
        }
        model.addAttribute("bookmarks", bookmarks);
        return "list";
    }
    
    //To update/edit bookmarks
    @GetMapping("/update/{id}")
    public String updateBookmark(@PathVariable Integer id, Model model) {
        Optional<Bookmark> optionalBookmarkDetails = bookmarkRepository.findById(id);
        if (optionalBookmarkDetails.isPresent()) {
            model.addAttribute("bookmarkDetails", optionalBookmarkDetails.get());
            return "update";
        }
        return "redirect:/all"; // Handle not found case
    }

    @PostMapping("/update/{id}")
    public String updateBookmark(@PathVariable Integer id, Bookmark bookmarkData) {
        Optional<Bookmark> optionalBookmarkDetails = bookmarkRepository.findById(id);
        if (optionalBookmarkDetails.isPresent()) {
        	Bookmark bookmarkDetails = optionalBookmarkDetails.get();
        	bookmarkDetails.setTitle(bookmarkData.getTitle());
        	bookmarkDetails.setUrl(bookmarkData.getUrl());
            bookmarkRepository.save(bookmarkDetails);
        }
        return "redirect:/all";
    }
    
    //To delete an entry
    @GetMapping("/delete/{id}")
    public String deleteBookmark(@PathVariable Integer id, Model model) {
        Optional<Bookmark> optionalBookmarkDetails = bookmarkRepository.findById(id);
        if (optionalBookmarkDetails.isPresent()) {
            model.addAttribute("bookmarkDetails", optionalBookmarkDetails.get());
            return "delete";
        }
        return "redirect:/all"; // Handle not found case
    }

    @PostMapping("/delete/{id}")
    public String deleteBookmark(@PathVariable Integer id) {
    	bookmarkRepository.deleteById(id);
        return "redirect:/all";
    }
}