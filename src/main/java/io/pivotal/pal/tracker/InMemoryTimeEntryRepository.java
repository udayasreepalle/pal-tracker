package io.pivotal.pal.tracker;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryTimeEntryRepository implements TimeEntryRepository{
    private Map<Long, TimeEntry> TimeEntryMap = new HashMap<>();
    private long id = 1L;

    public TimeEntry create(TimeEntry createEntry){
        createEntry.setId(id++);
        this.TimeEntryMap.put(((Long) createEntry.getId()), createEntry);
        return createEntry;
    }

    public TimeEntry find(long id){
        return this.TimeEntryMap.get((Long)id);
    }

    public TimeEntry update(long id, TimeEntry updateEntry){
        updateEntry.setId(id);
        if (this.find(id) == null) {
            return null;
        }
        this.TimeEntryMap.put(((Long) updateEntry.getId()), updateEntry);
        return updateEntry;
    }

    public void delete(long id){
        this.TimeEntryMap.remove((Long)id);
    }

    public List<TimeEntry>  list(){
        return this.TimeEntryMap.values().stream().collect(Collectors.toList());
    }

}
