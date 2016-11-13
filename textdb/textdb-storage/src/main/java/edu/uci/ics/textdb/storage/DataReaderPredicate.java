package edu.uci.ics.textdb.storage;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.search.Query;

import edu.uci.ics.textdb.api.common.IPredicate;
import edu.uci.ics.textdb.api.storage.IDataStore;

/**
 * @author sandeepreddy602
 * @author Zuozhi Wang
 */
public class DataReaderPredicate implements IPredicate {
    private IDataStore dataStore;
    private Query luceneQuery;
    private Analyzer luceneAnalyzer;
    private boolean payloadAdded = false;

    public DataReaderPredicate(Query luceneQuery, IDataStore dataStore, Analyzer analyzer) {
        this.dataStore = dataStore;
        this.luceneQuery = luceneQuery;
        this.luceneAnalyzer = analyzer;
    }

    public IDataStore getDataStore() {
        return dataStore;
    }

    public Query getLuceneQuery() {
        return luceneQuery;
    }

    public Analyzer getLuceneAnalyzer() {
        return luceneAnalyzer;
    }

    public void seIsPayloadAdded(boolean isPayloadAdded) {
        this.payloadAdded = isPayloadAdded;
    }
    
    public boolean isPayloadAdded() {
        return this.payloadAdded;
    }
}
