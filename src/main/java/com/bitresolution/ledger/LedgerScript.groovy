package com.bitresolution.ledger

import org.joda.time.DateTime


abstract class LedgerScript extends Script {

    public final Ledger ledger = new Ledger()

    def load(def file) {
        File data = new File(file)
        if(!data.exists()) {
            throw new FileNotFoundException(data.toString());
        }
        if (data.directory) {
            ledger.loadReports(data.listFiles(new FilenameFilter() {
                public boolean accept(File directory, String fileName) {
                    return fileName.endsWith(".txt")
                }
            }))
        }
        ledger.loadReports(data)
    }

    def summariseStock(def properties) {
        return ledger.summariseStock(properties.name)
    }

    def findLargestPositions(def properties) {
        return ledger.findLargestPositions(properties.count, new DateTime(properties.date))
    }

    def findTopNewPerformers(def properties) {
        return ledger.findTopNewPerformers(properties.count, new DateTime(properties.date))
    }
}
