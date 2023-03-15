package ru.samara.bibliotek.controllers;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.samara.bibliotek.models.Book;
import ru.samara.bibliotek.models.Person;
import ru.samara.bibliotek.services.BookService;
import ru.samara.bibliotek.services.PersonService;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final PersonService personService;


    @Autowired
    public BookController(BookService bookService, PersonService personService) {
        this.bookService = bookService;
        this.personService = personService;
    }
    //пагинация по страницам сортированных книг
    @GetMapping()//метод пагинации по страницам сортированных книг
    public String index(Model model, @RequestParam(value = "page", required = false) Integer page,
                        @RequestParam(value = "books_per_page", required = false) Integer booksPerPage,
                        @RequestParam(value = "sort_by_year", required = false) boolean sortByYearBook){
        if (page == null || booksPerPage == null)
            model.addAttribute("books", bookService.findALL(sortByYearBook));//выдать все книги сортированно
        else
            model.addAttribute("books", bookService.findWithPagination(page, booksPerPage, sortByYearBook));
        return "books/index";
    }
    //посмотреть наличие книги у читателя
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookService.findOne(id));

        Optional<Person> bookOwner = Optional.ofNullable(bookService.getBookOwner(id));

        if (bookOwner.isPresent())
            model.addAttribute("owner", bookOwner.get());
        else
            model.addAttribute("people", personService.findALL());
        return "books/show";
    }


    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";

    }
    @PostMapping
    public String create (@ModelAttribute("book") @Valid Book book,
                          BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "books/new";
        bookService.save(book);
        return "redirect:/books";
    }
    @GetMapping("/{id}/edit")
    public String edit (Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookService.findOne(id));
        return "books/edit";

    }
    @PatchMapping("/{id}")
    public String update (@ModelAttribute("book") @Valid Book book,
                          BindingResult bindingResult,
                           @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "books/edit";
        bookService.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete (@PathVariable("id") int id) {
        bookService.delete(id);
        return "redirect:/books";

    }
    //освободить книгу
    @PatchMapping("/{id}/toFree")
    public String toFree(@PathVariable("id") int id){
        bookService.toFree(id);
        return "redirect:/books/"+ id;
    }
    //назначить книгу
    @PatchMapping("/{id}/toAppoint")
    public String toAppoint(@PathVariable("id") int id, @ModelAttribute("person") Person selectedPerson){
        bookService.toAppoint(id, selectedPerson);
        return "redirect:/books/"+ id;
    }
    @GetMapping("/search")
    public String searchPage(){
        return "books/search";
    }

    @PostMapping("/search")
    public String makeSearch (Model model, @RequestParam("query") String query) {
        model.addAttribute("books", bookService.searchByQuery(query));
        return "books/search";
    }

}

