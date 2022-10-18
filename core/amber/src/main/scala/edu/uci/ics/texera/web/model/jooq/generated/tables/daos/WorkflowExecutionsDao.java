/*
 * This file is generated by jOOQ.
 */
package edu.uci.ics.texera.web.model.jooq.generated.tables.daos;


import edu.uci.ics.texera.web.model.jooq.generated.tables.WorkflowExecutions;
import edu.uci.ics.texera.web.model.jooq.generated.tables.records.WorkflowExecutionsRecord;

import java.sql.Timestamp;
import java.util.List;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;
import org.jooq.types.UInteger;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class WorkflowExecutionsDao extends DAOImpl<WorkflowExecutionsRecord, edu.uci.ics.texera.web.model.jooq.generated.tables.pojos.WorkflowExecutions, UInteger> {

    /**
     * Create a new WorkflowExecutionsDao without any configuration
     */
    public WorkflowExecutionsDao() {
        super(WorkflowExecutions.WORKFLOW_EXECUTIONS, edu.uci.ics.texera.web.model.jooq.generated.tables.pojos.WorkflowExecutions.class);
    }

    /**
     * Create a new WorkflowExecutionsDao with an attached configuration
     */
    public WorkflowExecutionsDao(Configuration configuration) {
        super(WorkflowExecutions.WORKFLOW_EXECUTIONS, edu.uci.ics.texera.web.model.jooq.generated.tables.pojos.WorkflowExecutions.class, configuration);
    }

    @Override
    public UInteger getId(edu.uci.ics.texera.web.model.jooq.generated.tables.pojos.WorkflowExecutions object) {
        return object.getEid();
    }

    /**
     * Fetch records that have <code>eid BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<edu.uci.ics.texera.web.model.jooq.generated.tables.pojos.WorkflowExecutions> fetchRangeOfEid(UInteger lowerInclusive, UInteger upperInclusive) {
        return fetchRange(WorkflowExecutions.WORKFLOW_EXECUTIONS.EID, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>eid IN (values)</code>
     */
    public List<edu.uci.ics.texera.web.model.jooq.generated.tables.pojos.WorkflowExecutions> fetchByEid(UInteger... values) {
        return fetch(WorkflowExecutions.WORKFLOW_EXECUTIONS.EID, values);
    }

    /**
     * Fetch a unique record that has <code>eid = value</code>
     */
    public edu.uci.ics.texera.web.model.jooq.generated.tables.pojos.WorkflowExecutions fetchOneByEid(UInteger value) {
        return fetchOne(WorkflowExecutions.WORKFLOW_EXECUTIONS.EID, value);
    }

    /**
     * Fetch records that have <code>vid BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<edu.uci.ics.texera.web.model.jooq.generated.tables.pojos.WorkflowExecutions> fetchRangeOfVid(UInteger lowerInclusive, UInteger upperInclusive) {
        return fetchRange(WorkflowExecutions.WORKFLOW_EXECUTIONS.VID, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>vid IN (values)</code>
     */
    public List<edu.uci.ics.texera.web.model.jooq.generated.tables.pojos.WorkflowExecutions> fetchByVid(UInteger... values) {
        return fetch(WorkflowExecutions.WORKFLOW_EXECUTIONS.VID, values);
    }

    /**
     * Fetch records that have <code>uid BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<edu.uci.ics.texera.web.model.jooq.generated.tables.pojos.WorkflowExecutions> fetchRangeOfUid(UInteger lowerInclusive, UInteger upperInclusive) {
        return fetchRange(WorkflowExecutions.WORKFLOW_EXECUTIONS.UID, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>uid IN (values)</code>
     */
    public List<edu.uci.ics.texera.web.model.jooq.generated.tables.pojos.WorkflowExecutions> fetchByUid(UInteger... values) {
        return fetch(WorkflowExecutions.WORKFLOW_EXECUTIONS.UID, values);
    }

    /**
     * Fetch records that have <code>status BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<edu.uci.ics.texera.web.model.jooq.generated.tables.pojos.WorkflowExecutions> fetchRangeOfStatus(Byte lowerInclusive, Byte upperInclusive) {
        return fetchRange(WorkflowExecutions.WORKFLOW_EXECUTIONS.STATUS, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>status IN (values)</code>
     */
    public List<edu.uci.ics.texera.web.model.jooq.generated.tables.pojos.WorkflowExecutions> fetchByStatus(Byte... values) {
        return fetch(WorkflowExecutions.WORKFLOW_EXECUTIONS.STATUS, values);
    }

    /**
     * Fetch records that have <code>result BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<edu.uci.ics.texera.web.model.jooq.generated.tables.pojos.WorkflowExecutions> fetchRangeOfResult(String lowerInclusive, String upperInclusive) {
        return fetchRange(WorkflowExecutions.WORKFLOW_EXECUTIONS.RESULT, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>result IN (values)</code>
     */
    public List<edu.uci.ics.texera.web.model.jooq.generated.tables.pojos.WorkflowExecutions> fetchByResult(String... values) {
        return fetch(WorkflowExecutions.WORKFLOW_EXECUTIONS.RESULT, values);
    }

    /**
     * Fetch records that have <code>starting_time BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<edu.uci.ics.texera.web.model.jooq.generated.tables.pojos.WorkflowExecutions> fetchRangeOfStartingTime(Timestamp lowerInclusive, Timestamp upperInclusive) {
        return fetchRange(WorkflowExecutions.WORKFLOW_EXECUTIONS.STARTING_TIME, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>starting_time IN (values)</code>
     */
    public List<edu.uci.ics.texera.web.model.jooq.generated.tables.pojos.WorkflowExecutions> fetchByStartingTime(Timestamp... values) {
        return fetch(WorkflowExecutions.WORKFLOW_EXECUTIONS.STARTING_TIME, values);
    }

    /**
     * Fetch records that have <code>last_update_time BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<edu.uci.ics.texera.web.model.jooq.generated.tables.pojos.WorkflowExecutions> fetchRangeOfLastUpdateTime(Timestamp lowerInclusive, Timestamp upperInclusive) {
        return fetchRange(WorkflowExecutions.WORKFLOW_EXECUTIONS.LAST_UPDATE_TIME, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>last_update_time IN (values)</code>
     */
    public List<edu.uci.ics.texera.web.model.jooq.generated.tables.pojos.WorkflowExecutions> fetchByLastUpdateTime(Timestamp... values) {
        return fetch(WorkflowExecutions.WORKFLOW_EXECUTIONS.LAST_UPDATE_TIME, values);
    }

    /**
     * Fetch records that have <code>bookmarked BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<edu.uci.ics.texera.web.model.jooq.generated.tables.pojos.WorkflowExecutions> fetchRangeOfBookmarked(Byte lowerInclusive, Byte upperInclusive) {
        return fetchRange(WorkflowExecutions.WORKFLOW_EXECUTIONS.BOOKMARKED, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>bookmarked IN (values)</code>
     */
    public List<edu.uci.ics.texera.web.model.jooq.generated.tables.pojos.WorkflowExecutions> fetchByBookmarked(Byte... values) {
        return fetch(WorkflowExecutions.WORKFLOW_EXECUTIONS.BOOKMARKED, values);
    }

    /**
     * Fetch records that have <code>name BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<edu.uci.ics.texera.web.model.jooq.generated.tables.pojos.WorkflowExecutions> fetchRangeOfName(String lowerInclusive, String upperInclusive) {
        return fetchRange(WorkflowExecutions.WORKFLOW_EXECUTIONS.NAME, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>name IN (values)</code>
     */
    public List<edu.uci.ics.texera.web.model.jooq.generated.tables.pojos.WorkflowExecutions> fetchByName(String... values) {
        return fetch(WorkflowExecutions.WORKFLOW_EXECUTIONS.NAME, values);
    }

    /**
     * Fetch records that have <code>environment_version BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<edu.uci.ics.texera.web.model.jooq.generated.tables.pojos.WorkflowExecutions> fetchRangeOfEnvironmentVersion(String lowerInclusive, String upperInclusive) {
        return fetchRange(WorkflowExecutions.WORKFLOW_EXECUTIONS.ENVIRONMENT_VERSION, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>environment_version IN (values)</code>
     */
    public List<edu.uci.ics.texera.web.model.jooq.generated.tables.pojos.WorkflowExecutions> fetchByEnvironmentVersion(String... values) {
        return fetch(WorkflowExecutions.WORKFLOW_EXECUTIONS.ENVIRONMENT_VERSION, values);
    }
}
