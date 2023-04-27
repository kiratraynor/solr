package org.apache.solr.search;

import org.apache.lucene.search.DocIdSet;
import org.apache.lucene.util.Bits;

import java.util.Objects;

public class BitsFilteredDocIdSet extends FilteredDocIdSet {
    private final Bits acceptDocs;

    /**
     * Convenience wrapper method: If {@code acceptDocs == null} it returns the original set without wrapping.
     * @param set Underlying DocIdSet. If {@code null}, this method returns {@code null}
     * @param acceptDocs Allowed docs, all docids not in this set will not be returned by this DocIdSet.
     * If {@code null}, this method returns the original set without wrapping.
     */
    public static DocIdSet wrap(DocIdSet set, Bits acceptDocs) {
        return (set == null || acceptDocs == null) ? set : new BitsFilteredDocIdSet(set, acceptDocs);
    }

    /**
     * Constructor.
     * @param innerSet Underlying DocIdSet
     * @param acceptDocs Allowed docs, all docids not in this set will not be returned by this DocIdSet
     */
    public BitsFilteredDocIdSet(DocIdSet innerSet, Bits acceptDocs) {
        super(innerSet);
        this.acceptDocs = Objects.requireNonNull(acceptDocs, "Bits must not be null");
    }

    @Override
    protected boolean match(int docid) {
        return acceptDocs.get(docid);
    }
}
