package io.pivotal.pal.tracker;

import java.util.List;

public interface TimeEntryRepository {

    public TimeEntry create(TimeEntry createEntry);

    public TimeEntry find(long id);

    public TimeEntry update(long id, TimeEntry updateEntry);

    public void delete(long id);

    public List<TimeEntry> list();
}
