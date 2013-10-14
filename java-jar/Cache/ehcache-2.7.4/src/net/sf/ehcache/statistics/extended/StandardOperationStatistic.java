/**
 *  Copyright Terracotta, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package net.sf.ehcache.statistics.extended;

import static net.sf.ehcache.statistics.extended.EhcacheQueryBuilder.cache;
import static net.sf.ehcache.statistics.extended.EhcacheQueryBuilder.children;
import static net.sf.ehcache.statistics.extended.EhcacheQueryBuilder.descendants;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import net.sf.ehcache.CacheOperationOutcomes;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.store.StoreOperationOutcomes;
import net.sf.ehcache.transaction.xa.XaCommitOutcome;
import net.sf.ehcache.transaction.xa.XaRecoveryOutcome;
import net.sf.ehcache.transaction.xa.XaRollbackOutcome;

import org.terracotta.context.query.Query;

/**
 * The Enum OperationType.
 *
 * @author cdennis
 */
enum StandardOperationStatistic {

    /** The cache get. */
    CACHE_GET(true, cache(), CacheOperationOutcomes.GetOutcome.class, "get", "cache"),
    /** The cache put. */
    CACHE_PUT(true, cache(), CacheOperationOutcomes.PutOutcome.class, "put", "cache"),
    /** The cache remove. */
    CACHE_REMOVE(true, cache(), CacheOperationOutcomes.RemoveOutcome.class, "remove", "cache"),

    /** The heap get. */
    HEAP_GET(StoreOperationOutcomes.GetOutcome.class, "get", "local-heap"),

    /** The heap put. */
    HEAP_PUT(StoreOperationOutcomes.PutOutcome.class, "put", "local-heap"),

    /** The heap remove. */
    HEAP_REMOVE(StoreOperationOutcomes.RemoveOutcome.class, "remove", "local-heap"),

    /** The offheap get. */
    OFFHEAP_GET(StoreOperationOutcomes.GetOutcome.class, "get", "local-offheap"),

    /** The offheap put. */
    OFFHEAP_PUT(StoreOperationOutcomes.PutOutcome.class, "put", "local-offheap"),

    /** The offheap remove. */
    OFFHEAP_REMOVE(StoreOperationOutcomes.RemoveOutcome.class, "remove", "local-offheap"),

    /** The disk get. */
    DISK_GET(StoreOperationOutcomes.GetOutcome.class, "get", "local-disk"),

    /** The disk put. */
    DISK_PUT(StoreOperationOutcomes.PutOutcome.class, "put", "local-disk"),

    /** The disk remove. */
    DISK_REMOVE(StoreOperationOutcomes.RemoveOutcome.class, "remove", "local-disk"),

    /** The xa commit. */
    XA_COMMIT(XaCommitOutcome.class, "xa-commit", "xa-transactional"),

    /** The xa rollback. */
    XA_ROLLBACK(XaRollbackOutcome.class, "xa-rollback", "xa-transactional"),

    /** The xa recovery. */
    XA_RECOVERY(XaRecoveryOutcome.class, "xa-recovery", "xa-transactional"),

    /** The search. */
    SEARCH(true, cache(), CacheOperationOutcomes.SearchOutcome.class, "search", "cache") {
        @Override
        boolean isSearch() {
            return true;
        }
    },

    /** The evicted. */
    EVICTION(false, cache().add(children().exclude(Ehcache.class).add(descendants())), CacheOperationOutcomes.EvictionOutcome.class, "eviction"),

    /** The expired. */
    EXPIRY(true, cache().children(), CacheOperationOutcomes.ExpiredOutcome.class, "expiry"),

    /** cluster events */
    CLUSTER_EVENT(CacheOperationOutcomes.ClusterEventOutcomes.class, "cluster", "cache"),

    /** cluster events */
    NONSTOP(CacheOperationOutcomes.NonStopOperationOutcomes.class, "nonstop", "cache");

    private static final int THIRTY = 30;
    private static final int TEN = 10;
    
    private final boolean required;
    private final Query context;
    private final Class<? extends Enum> type;
    private final String name;
    private final Set<String> tags;

    private StandardOperationStatistic(Class<? extends Enum> type, String name, String ... tags) {
        this(false, type, name, tags);
    }

    private StandardOperationStatistic(boolean required, Class<? extends Enum> type, String name, String ... tags) {
        this(required, descendants(), type, name, tags);
    }

    private StandardOperationStatistic(boolean required, Query context, Class<? extends Enum> type, String name, String ... tags) {
        this.required = required;
        this.context = context;
        this.type = type;
        this.name = name;
        this.tags = Collections.unmodifiableSet(new HashSet<String>(Arrays.asList(tags)));
    }
    
    /**
     * If this statistic is required.
     * <p>
     * If required and this statistic is not present an exception will be thrown.
     *
     * @return
     */
    final boolean required() {
        return required;
    }

    /**
     * Query that select context nodes for this statistic.
     * 
     * @return context query
     */
    final Query context() {
        return context;
    }
    
    /**
     * Operation result type.
     *
     * @return operation result type
     */
    final Class<? extends Enum> type() {
        return type;
    }

    /**
     * The name of the statistic as found in the statistics context tree.
     *
     * @return the statistic name
     */
    final String operationName() {
        return name;
    }

    /**
     * A set of tags that will be on the statistic found in the statistics context tree.
     *
     * @return the statistic tags
     */
    final Set<String> tags() {
        return tags;
    }

    /**
     * Is this stat search related or not?
     * @return
     */
    boolean isSearch() {
        return false;
    }
  
}
