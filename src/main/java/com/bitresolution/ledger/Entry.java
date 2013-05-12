package com.bitresolution.ledger;

import com.google.common.base.Objects;

public class Entry {

    private final String nameOfIssuer;
    private final String titleOfClass;
    private final String cusip;
    private final String marketValue;
    private final String amount;
    private final String call;
    private final String investmentDescretion;
    private final String otherManagers;
    private final String votingAuthoritySole;
    private final String shared;
    private final String none;

    public Entry(String nameOfIssuer, String titleOfClass, String cusip, String marketValue, String amount, String call, String investmentDescretion, String otherManagers, String votingAuthoritySole, String shared, String none) {
        this.nameOfIssuer = nameOfIssuer;
        this.titleOfClass = titleOfClass;
        this.cusip = cusip;
        this.marketValue = marketValue;
        this.amount = amount;
        this.call = call;
        this.investmentDescretion = investmentDescretion;
        this.otherManagers = otherManagers;
        this.votingAuthoritySole = votingAuthoritySole;
        this.shared = shared;
        this.none = none;
    }

    public String getNameOfIssuer() {
        return nameOfIssuer;
    }

    public String getTitleOfClass() {
        return titleOfClass;
    }

    public String getCusip() {
        return cusip;
    }

    public String getMarketValue() {
        return marketValue;
    }

    public String getAmount() {
        return amount;
    }

    public String getCall() {
        return call;
    }

    public String getInvestmentDescretion() {
        return investmentDescretion;
    }

    public String getOtherManagers() {
        return otherManagers;
    }

    public String getVotingAuthoritySole() {
        return votingAuthoritySole;
    }

    public String getShared() {
        return shared;
    }

    public String getNone() {
        return none;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(
                nameOfIssuer,
                titleOfClass,
                cusip,
                marketValue,
                call,
                investmentDescretion,
                otherManagers,
                votingAuthoritySole,
                shared,
                none,
                amount
        );
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Entry other = (Entry) obj;
        return Objects.equal(this.nameOfIssuer, other.nameOfIssuer)
                && Objects.equal(this.titleOfClass, other.titleOfClass)
                && Objects.equal(this.cusip, other.cusip)
                && Objects.equal(this.marketValue, other.marketValue)
                && Objects.equal(this.call, other.call)
                && Objects.equal(this.investmentDescretion, other.investmentDescretion)
                && Objects.equal(this.otherManagers, other.otherManagers)
                && Objects.equal(this.votingAuthoritySole, other.votingAuthoritySole)
                && Objects.equal(this.shared, other.shared)
                && Objects.equal(this.none, other.none)
                && Objects.equal(this.amount, other.amount);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("nameOfIssuer", nameOfIssuer)
                .add("titleOfClass", titleOfClass)
                .add("cusip", cusip)
                .add("marketValue", marketValue)
                .add("call", call)
                .add("investmentDescretion", investmentDescretion)
                .add("otherManagers", otherManagers)
                .add("votingAuthoritySole", votingAuthoritySole)
                .add("shared", shared)
                .add("none", none)
                .add("amount", amount)
                .toString();
    }

    public static class Builder {
        private String nameOfIssuer;
        private String titleOfClass;
        private String cusip;
        private String marketValue;
        private String amount;
        private String call;
        private String investmentDescretion;
        private String otherManagers;
        private String votingAuthoritySole;
        private String shared;
        private String none;

        public Builder setNameOfIssuer(String nameOfIssuer) {
            this.nameOfIssuer = nameOfIssuer;
            return this;
        }

        public Builder setTitleOfClass(String titleOfClass) {
            this.titleOfClass = titleOfClass;
            return this;
        }

        public Builder setCusip(String cusip) {
            this.cusip = cusip;
            return this;
        }

        public Builder setMarketValue(String marketValue) {
            this.marketValue = marketValue;
            return this;
        }

        public Builder setAmount(String amount) {
            this.amount = amount;
            return this;
        }

        public Builder setCall(String call) {
            this.call = call;
            return this;
        }

        public Builder setInvestmentDescretion(String investmentDescretion) {
            this.investmentDescretion = investmentDescretion;
            return this;
        }

        public Builder setOtherManagers(String otherManagers) {
            this.otherManagers = otherManagers;
            return this;
        }

        public Builder setVotingAuthoritySole(String votingAuthoritySole) {
            this.votingAuthoritySole = votingAuthoritySole;
            return this;
        }

        public Builder setShared(String shared) {
            this.shared = shared;
            return this;
        }

        public Builder setNone(String none) {
            this.none = none;
            return this;
        }

        public Entry build() {
            return new Entry(nameOfIssuer, titleOfClass, cusip, marketValue, amount, call, investmentDescretion, otherManagers, votingAuthoritySole, shared, none);
        }
    }
}
