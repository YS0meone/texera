/*
 * This file is generated by jOOQ.
 */
package edu.uci.ics.texera.web.model.jooq.generated.tables;


import edu.uci.ics.texera.web.model.jooq.generated.Indexes;
import edu.uci.ics.texera.web.model.jooq.generated.Keys;
import edu.uci.ics.texera.web.model.jooq.generated.TexeraDb;
import edu.uci.ics.texera.web.model.jooq.generated.tables.records.DatasetRecord;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row7;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;
import org.jooq.types.UInteger;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Dataset extends TableImpl<DatasetRecord> {

    private static final long serialVersionUID = 578477320;

    /**
     * The reference instance of <code>texera_db.dataset</code>
     */
    public static final Dataset DATASET = new Dataset();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<DatasetRecord> getRecordType() {
        return DatasetRecord.class;
    }

    /**
     * The column <code>texera_db.dataset.did</code>.
     */
    public final TableField<DatasetRecord, UInteger> DID = createField(DSL.name("did"), org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).identity(true), this, "");

    /**
     * The column <code>texera_db.dataset.owner_uid</code>.
     */
    public final TableField<DatasetRecord, UInteger> OWNER_UID = createField(DSL.name("owner_uid"), org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false), this, "");

    /**
     * The column <code>texera_db.dataset.name</code>.
     */
    public final TableField<DatasetRecord, String> NAME = createField(DSL.name("name"), org.jooq.impl.SQLDataType.VARCHAR(128).nullable(false), this, "");

    /**
     * The column <code>texera_db.dataset.is_public</code>.
     */
    public final TableField<DatasetRecord, Byte> IS_PUBLIC = createField(DSL.name("is_public"), org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.TINYINT)), this, "");

    /**
     * The column <code>texera_db.dataset.storage_path</code>.
     */
    public final TableField<DatasetRecord, String> STORAGE_PATH = createField(DSL.name("storage_path"), org.jooq.impl.SQLDataType.VARCHAR(512).nullable(false), this, "");

    /**
     * The column <code>texera_db.dataset.description</code>.
     */
    public final TableField<DatasetRecord, String> DESCRIPTION = createField(DSL.name("description"), org.jooq.impl.SQLDataType.VARCHAR(512).nullable(false), this, "");

    /**
     * The column <code>texera_db.dataset.creation_time</code>.
     */
    public final TableField<DatasetRecord, Timestamp> CREATION_TIME = createField(DSL.name("creation_time"), org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * Create a <code>texera_db.dataset</code> table reference
     */
    public Dataset() {
        this(DSL.name("dataset"), null);
    }

    /**
     * Create an aliased <code>texera_db.dataset</code> table reference
     */
    public Dataset(String alias) {
        this(DSL.name(alias), DATASET);
    }

    /**
     * Create an aliased <code>texera_db.dataset</code> table reference
     */
    public Dataset(Name alias) {
        this(alias, DATASET);
    }

    private Dataset(Name alias, Table<DatasetRecord> aliased) {
        this(alias, aliased, null);
    }

    private Dataset(Name alias, Table<DatasetRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> Dataset(Table<O> child, ForeignKey<O, DatasetRecord> key) {
        super(child, key, DATASET);
    }

    @Override
    public Schema getSchema() {
        return TexeraDb.TEXERA_DB;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.DATASET_IDX_DATASET_NAME_DESCRIPTION, Indexes.DATASET_OWNER_UID, Indexes.DATASET_PRIMARY);
    }

    @Override
    public Identity<DatasetRecord, UInteger> getIdentity() {
        return Keys.IDENTITY_DATASET;
    }

    @Override
    public UniqueKey<DatasetRecord> getPrimaryKey() {
        return Keys.KEY_DATASET_PRIMARY;
    }

    @Override
    public List<UniqueKey<DatasetRecord>> getKeys() {
        return Arrays.<UniqueKey<DatasetRecord>>asList(Keys.KEY_DATASET_PRIMARY);
    }

    @Override
    public List<ForeignKey<DatasetRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<DatasetRecord, ?>>asList(Keys.DATASET_IBFK_1);
    }

    public User user() {
        return new User(this, Keys.DATASET_IBFK_1);
    }

    @Override
    public Dataset as(String alias) {
        return new Dataset(DSL.name(alias), this);
    }

    @Override
    public Dataset as(Name alias) {
        return new Dataset(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Dataset rename(String name) {
        return new Dataset(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Dataset rename(Name name) {
        return new Dataset(name, null);
    }

    // -------------------------------------------------------------------------
    // Row7 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row7<UInteger, UInteger, String, Byte, String, String, Timestamp> fieldsRow() {
        return (Row7) super.fieldsRow();
    }
}
