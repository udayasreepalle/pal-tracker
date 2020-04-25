package io.pivotal.pal.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TimeEntryController {
    private TimeEntryRepository timeEntryRepository;

    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository = timeEntryRepository;
    }

    @RequestMapping(path ="/time-entries", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity create(@RequestBody TimeEntry timeEntry){
        TimeEntry timeEntryCreated = this.timeEntryRepository.create(timeEntry);
        ResponseEntity response = new ResponseEntity(timeEntryCreated, HttpStatus.CREATED);
        return response;
    }

    @RequestMapping(path = "time-entries/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity read(@PathVariable long id){
        TimeEntry timeEntry = this.timeEntryRepository.find(id);
        HttpStatus httpStatus = HttpStatus.OK;

        if (timeEntry == null){
            httpStatus = HttpStatus.NOT_FOUND;
        }
        ResponseEntity response = new ResponseEntity(timeEntry, httpStatus);
        return response;
    }


    @RequestMapping(path = "/time-entries/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity update(@PathVariable long id, @RequestBody TimeEntry expected){
        TimeEntry timeEntry =this.timeEntryRepository.update(id, expected);
        HttpStatus httpStatus = HttpStatus.OK;

        if (timeEntry == null){
            httpStatus = HttpStatus.NOT_FOUND;
        }
        ResponseEntity response = new ResponseEntity(timeEntry, httpStatus);
        return response;
    }

    @RequestMapping(path = "/time-entries/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity delete(@PathVariable long id){
        this.timeEntryRepository.delete(id);
        ResponseEntity response = new ResponseEntity(HttpStatus.NO_CONTENT);
        return response;
    }

    @RequestMapping(path = "/time-entries", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<TimeEntry>> list(){
        ResponseEntity response = new ResponseEntity(this.timeEntryRepository.list(), HttpStatus.OK);
        return response;
    }
}
