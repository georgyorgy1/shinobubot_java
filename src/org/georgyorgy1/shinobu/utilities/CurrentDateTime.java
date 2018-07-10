package org.georgyorgy1.shinobu.utilities;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class CurrentDateTime
{
    private LocalDateTime now;
    private DateTimeFormatter formatter;
    
    public CurrentDateTime()
    {
        this.now = LocalDateTime.now();
        this.formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    public String getDateTime()
    {
        return now.format(formatter);
    }
}