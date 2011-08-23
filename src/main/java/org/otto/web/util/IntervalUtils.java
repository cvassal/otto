/*
 * Copyright 2011 Damien Bourdette
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.otto.web.util;

import org.joda.time.DateMidnight;
import org.joda.time.Interval;

import com.mongodb.BasicDBObject;

/**
 * @author damien bourdette <a href="https://github.com/dbourdette">dbourdette on github</a>
 * @version \$Revision$
 */
public class IntervalUtils {
	public static Interval yesterday() {
		return new Interval(new DateMidnight().minusDays(1), new DateMidnight());
	}
	
	public static Interval today() {
		return new Interval(new DateMidnight(), new DateMidnight().plusDays(1));
	}
	
	public static Interval lastWeek() {
		DateMidnight monday = new DateMidnight().withDayOfWeek(1);
		
		return new Interval(monday.minusDays(7), monday);
	}

	public static BasicDBObject query(Interval interval) {
		BasicDBObject criteria = new BasicDBObject();

		criteria.append("$gt", interval.getStart().toDate());
		criteria.append("$lte", interval.getEnd().toDate());

		return new BasicDBObject("date", criteria);
	}
}
