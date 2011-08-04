package org.otto.web.form;

import org.joda.time.DateMidnight;

import java.util.Date;

public class GraphForm {
	public Date start;
	
	public Date end;
	
	public int stepInMinutes;
	
	public String sumColumn;

    public String splitColumn;
	
	public GraphForm() {
		DateMidnight today = new DateMidnight();
		
		start = today.minusDays(1).toDate();
		end = today.plusDays(1).toDate();
		
		stepInMinutes = 5;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public int getStepInMinutes() {
		return stepInMinutes;
	}

	public void setStepInMinutes(int stepInMinutes) {
		this.stepInMinutes = stepInMinutes;
	}

	public String getSumColumn() {
		return sumColumn;
	}

	public void setSumColumn(String sumColumn) {
		this.sumColumn = sumColumn;
	}

    public String getSplitColumn() {
        return splitColumn;
    }

    public void setSplitColumn(String splitColumn) {
        this.splitColumn = splitColumn;
    }
}
