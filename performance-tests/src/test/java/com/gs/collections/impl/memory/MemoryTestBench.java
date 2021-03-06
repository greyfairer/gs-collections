/*
 * Copyright (C) 2000-2013 Heinz Max Kabutz
 *
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.  Heinz Max Kabutz licenses
 * this file to you under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gs.collections.impl.memory;

import java.text.NumberFormat;

import com.gs.collections.api.block.function.Function0;
import com.gs.collections.api.block.procedure.primitive.IntProcedure;
import com.gs.collections.impl.list.Interval;

public class MemoryTestBench
{
    private static final GCAndSleepProcedure GC_AND_SLEEP_PROCEDURE = new GCAndSleepProcedure();
    private static final Interval GC_INTERVAL = Interval.oneTo(20);
    private final Class<?> clazz;

    private MemoryTestBench(Class<?> clazz)
    {
        this.clazz = clazz;
    }

    public static MemoryTestBench on(Class<?> clazz)
    {
        return new MemoryTestBench(clazz);
    }

    /**
     * From newsletter 193
     * (http://www.javaspecialists.eu/archive/Issue193.html).  Used
     * to estimate memory usage by objects.
     */
    public long calculateMemoryUsage(Function0<?> factory)
    {
        // Clean the slate and prep
        this.forceGCAndSleepMultipleTimes();
        Object container = factory.value();
        if (!this.clazz.isInstance(container))
        {
            throw new RuntimeException();
        }
        long memory = this.currentUsedMemory();
        //noinspection UnusedAssignment,ReuseOfLocalVariable
        container = null;
        this.forceGCAndSleepMultipleTimes();

        // Calculate memory before creation
        long memory2 = this.currentUsedMemory();
        //noinspection UnusedAssignment,ReuseOfLocalVariable
        container = factory.value();
        // Get rid of transient garbage
        this.forceGCAndSleepMultipleTimes();
        // Calculate new used memory
        return this.currentUsedMemory() - memory2;
    }

    private long currentUsedMemory()
    {
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }

    private void forceGCAndSleepMultipleTimes()
    {
        GC_INTERVAL.forEach(GC_AND_SLEEP_PROCEDURE);
    }

    public void printContainerMemoryUsage(String category, int size, Function0<?> factory)
    {
        String memoryUsedInBytes = NumberFormat.getInstance().format(this.calculateMemoryUsage(factory));
        String sizeFormatted = NumberFormat.getInstance().format(size);
        System.out.println(
                category + " " +
                        this.clazz.getName() + " " +
                        "size " + sizeFormatted + " " +
                        "bytes " + memoryUsedInBytes);
    }

    private static class GCAndSleepProcedure implements IntProcedure
    {
        public void value(int each)
        {
            System.gc();
            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
                throw new RuntimeException(e);
            }
        }
    }
}
