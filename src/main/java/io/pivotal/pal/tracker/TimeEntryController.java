package io.pivotal.pal.tracker;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TimeEntryController {
    private TimeEntryRepository timeEntryRepository;
    private final DistributionSummary timeEntrySummary;
    private final Counter actionCounter;

    public TimeEntryController(TimeEntryRepository timeEntryRepository,
                               MeterRegistry meterRegistry) {
        this.timeEntryRepository = timeEntryRepository;
        timeEntrySummary = meterRegistry.summary("timeEntry.summary");
        actionCounter =  meterRegistry.counter("timeEntry.actionCounter");
    }

    @RequestMapping(path ="/time-entries", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity create(@RequestBody TimeEntry timeEntry){
        actionCounter.increment();
        timeEntrySummary.record(timeEntryRepository.list().size());
        TimeEntry timeEntryCreated = timeEntryRepository.create(timeEntry);
        ResponseEntity response = new ResponseEntity(timeEntryCreated, HttpStatus.CREATED);
        return response;
    }

    @RequestMapping(path = "time-entries/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity read(@PathVariable long id){
        actionCounter.increment();
        TimeEntry timeEntry = timeEntryRepository.find(id);
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
        actionCounter.increment();
        TimeEntry timeEntry = timeEntryRepository.update(id, expected);
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
        actionCounter.increment();
        timeEntrySummary.record(timeEntryRepository.list().size());
        timeEntryRepository.delete(id);
        ResponseEntity response = new ResponseEntity(HttpStatus.NO_CONTENT);
        return response;
    }

    @RequestMapping(path = "/time-entries", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<TimeEntry>> list(){
        actionCounter.increment();
        ResponseEntity response = new ResponseEntity(timeEntryRepository.list(), HttpStatus.OK);
        return response;
    }
}
