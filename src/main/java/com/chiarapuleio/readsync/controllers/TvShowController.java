package com.chiarapuleio.readsync.controllers;

import com.chiarapuleio.readsync.entities.TvShow;
import com.chiarapuleio.readsync.exceptions.NotFoundException;
import com.chiarapuleio.readsync.payloads.TvShowDTO;
import com.chiarapuleio.readsync.repositories.TvShowDAO;
import com.chiarapuleio.readsync.services.TvShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tvShows")
public class TvShowController {
    @Autowired
    private TvShowService tvShowService;
    @Autowired
    private TvShowDAO tvShowDAO;

    @GetMapping
    public List<TvShow> getAllTvShows(){
        return tvShowService.getAllTvShows();
    }

    @GetMapping("/{isbnCode}")
    public List<TvShow> getTvShowsByBookIsbn(@PathVariable String isbnCode){
        return tvShowService.getTvShowsByBookIsbn(isbnCode);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public TvShow saveTvShow(@RequestBody TvShowDTO tvShow){
        return tvShowService.saveTvShow(tvShow);
    }

    @PutMapping("/{tvShowId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public TvShow findByIdAndUpdate(@PathVariable Long tvShowId, @RequestBody TvShowDTO updatedTvShow){
        return tvShowService.findByIdAndUpdate(tvShowId, updatedTvShow);
    }

    @DeleteMapping("/{tvShowId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteTvShow(@PathVariable Long tvShowId){
        TvShow found = tvShowDAO.findById(tvShowId).orElseThrow(() -> new NotFoundException(String.valueOf(tvShowId)));
        if(found != null){
            tvShowDAO.delete(found);
        }
    }
}
