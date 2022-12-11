package com.donfood.controller;

import com.donfood.domain.Feedback;
import com.donfood.domain.Order;
import com.donfood.dto.FeedbackRequestDTO;
import com.donfood.dto.OrderRequestDTO;
import com.donfood.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedbacks")
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;

    @PostMapping("/new")
    public Feedback create(@RequestBody FeedbackRequestDTO feedbackRequestDTO){
        return feedbackService.create(feedbackRequestDTO);
    }

    @GetMapping
    public List<Feedback> getAll(){
        return feedbackService.getAll();
    }

    @GetMapping("/{id}")
    public Feedback getById(@PathVariable Long id){
        return feedbackService.getById(id);
    }

    @PutMapping("/{id}")
    public Feedback update(@PathVariable Long id, @RequestBody FeedbackRequestDTO feedbackRequestDTO){
        return feedbackService.update(id, feedbackRequestDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){ feedbackService.delete(id);}
}
