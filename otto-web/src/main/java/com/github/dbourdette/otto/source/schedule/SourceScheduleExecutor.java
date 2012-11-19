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

package com.github.dbourdette.otto.source.schedule;

import com.github.dbourdette.otto.source.Source;

/**
 * Executes schedules.
 *
 * @author damien bourdette
 */

public interface SourceScheduleExecutor {
    public void execute(Source source, MailSchedule schedule);

    public String executionMessage(Source source, MailSchedule schedule);
}