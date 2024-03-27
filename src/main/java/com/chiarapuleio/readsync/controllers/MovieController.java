package com.chiarapuleio.readsync.controllers;

import com.chiarapuleio.readsync.entities.Movie;
import com.chiarapuleio.readsync.exceptions.NotFoundException;
import com.chiarapuleio.readsync.payloads.MovieDTO;
import com.chiarapuleio.readsync.repositories.MovieDAO;
import com.chiarapuleio.readsync.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {
    @Autowired
    private MovieService movieService;
    @Autowired
    private MovieDAO movieDAO;


    @GetMapping
    public List<Movie> getAllMovies(){
        return movieService.getAllMovies();
    }

    @GetMapping("/{isbnCode}")
    public List<Movie> getMoviesByBookIsbn(@PathVariable String isbnCode){
        return movieService.getMoviesByBookIsbn(isbnCode);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public Movie saveMovie(@RequestBody MovieDTO movie){
        return movieService.saveMovie(movie);
    }

    @PutMapping("/{movieId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Movie findByIdAndUpdate(@PathVariable Long movieId, @RequestBody MovieDTO updatedMovie){
        return movieService.findByIdAndUpdate(movieId, updatedMovie);
    }

    @DeleteMapping("/{movieId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteMovie(@PathVariable Long movieId){
        Movie found = movieDAO.findById(movieId).orElseThrow(()-> new NotFoundException(String.valueOf(movieId)));
        if(found != null){
            movieDAO.delete(found);
        }
    }
}
