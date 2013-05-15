package com.bitresolution.ledger;

import java.util.Comparator;

class EntryMarketValueComparator implements Comparator<Entry> {
    @Override
    public int compare(Entry entry, Entry entry2) {
        return entry.getMarketValue().compareTo(entry2.getMarketValue());
    }
}
