package com.theironyard.command;

import com.theironyard.entities.Location;
import com.theironyard.entities.User;
import com.theironyard.utilities.LocalDateTimeConverter;

/**
 * Created by Nigel on 8/4/16.
 */
public class PostingCommand {
    User owner;
    String description;
    long minSalary;
    long maxSalary;
    Location location;

    public PostingCommand() {
    }

    public PostingCommand(String description, long minSalary, long maxSalary, Location location) {
        this.description = description;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
        this.location = location;
    }

    public User getOwner() {
        return owner;
    }


    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(long minSalary) {
        this.minSalary = minSalary;
    }

    public long getMaxSalary() {
        return maxSalary;
    }

    public void setMaxSalary(long maxSalary) {
        this.maxSalary = maxSalary;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
