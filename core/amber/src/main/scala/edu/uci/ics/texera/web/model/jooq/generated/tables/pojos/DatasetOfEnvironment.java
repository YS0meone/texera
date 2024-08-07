/*
 * This file is generated by jOOQ.
 */
package edu.uci.ics.texera.web.model.jooq.generated.tables.pojos;


import edu.uci.ics.texera.web.model.jooq.generated.tables.interfaces.IDatasetOfEnvironment;

import org.jooq.types.UInteger;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DatasetOfEnvironment implements IDatasetOfEnvironment {

    private static final long serialVersionUID = 1743926591;

    private UInteger did;
    private UInteger eid;
    private UInteger dvid;

    public DatasetOfEnvironment() {}

    public DatasetOfEnvironment(IDatasetOfEnvironment value) {
        this.did = value.getDid();
        this.eid = value.getEid();
        this.dvid = value.getDvid();
    }

    public DatasetOfEnvironment(
        UInteger did,
        UInteger eid,
        UInteger dvid
    ) {
        this.did = did;
        this.eid = eid;
        this.dvid = dvid;
    }

    @Override
    public UInteger getDid() {
        return this.did;
    }

    @Override
    public void setDid(UInteger did) {
        this.did = did;
    }

    @Override
    public UInteger getEid() {
        return this.eid;
    }

    @Override
    public void setEid(UInteger eid) {
        this.eid = eid;
    }

    @Override
    public UInteger getDvid() {
        return this.dvid;
    }

    @Override
    public void setDvid(UInteger dvid) {
        this.dvid = dvid;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("DatasetOfEnvironment (");

        sb.append(did);
        sb.append(", ").append(eid);
        sb.append(", ").append(dvid);

        sb.append(")");
        return sb.toString();
    }

    // -------------------------------------------------------------------------
    // FROM and INTO
    // -------------------------------------------------------------------------

    @Override
    public void from(IDatasetOfEnvironment from) {
        setDid(from.getDid());
        setEid(from.getEid());
        setDvid(from.getDvid());
    }

    @Override
    public <E extends IDatasetOfEnvironment> E into(E into) {
        into.from(this);
        return into;
    }
}
